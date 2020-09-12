package com.parsedata.test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Iterator;
import java.util.List;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.parsedata.JsoupData;

public class jSoupDataTest {

	@BeforeClass
	public static void init() throws SQLException, ClassNotFoundException, IOException {
		Class.forName("org.hsqldb.jdbc.JDBCDriver");
		initDatabase();
	}

	@AfterClass
	public static void destroy() throws SQLException, ClassNotFoundException, IOException {
		try {
			Connection connection = getConnection();
			Statement statement = connection.createStatement();
			statement.executeUpdate("DROP TABLE dataTable");
			connection.commit();
		} catch (Exception ex) {

		}
	}

	private static void initDatabase() throws SQLException {
		try {
			Connection connection = getConnection();
			Statement statement = connection.createStatement();
			statement.execute("CREATE TABLE dataTable (id INT NOT NULL, name VARCHAR(500), PRIMARY KEY (id))");
			connection.commit();
			JsoupData jsoupData = new JsoupData();
			List<String> list = jsoupData.getData();
			int idVal = 0;
			for (Iterator iterator = list.iterator(); iterator.hasNext();) {
				idVal++;
				String string = (String) iterator.next();
				String query = "INSERT INTO dataTable" + "(id, name) VALUES" + "(?,?)";
				PreparedStatement preparedStatement = connection.prepareStatement(query);
				preparedStatement.setInt(1, idVal);
				preparedStatement.setString(2, string);
				preparedStatement.executeUpdate();
				System.out.println("id Val " +idVal);
				System.out.println("string " +string);
			}
			
			connection.commit();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	private static Connection getConnection() throws SQLException {
		return DriverManager.getConnection("jdbc:hsqldb:mem:employees", "", "");
	}

	private int getTotalRecords() {
		try {
			Connection connection = getConnection();
			Statement statement = connection.createStatement();
			ResultSet result = statement.executeQuery("SELECT count(*) as total FROM dataTable");
			if (result.next()) {
				return result.getInt("total");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	@Test
	public void getTotalRecordsTest() {
		assertThat(11, is(getTotalRecords()));
	}

	@Test
	public void checkNameExistsTest() {
		try {
			Connection connection = getConnection();
			Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			ResultSet result = statement.executeQuery("SELECT name FROM dataTable");
			
			ResultSetMetaData rsmd = result.getMetaData();
			int columnsNumber = rsmd.getColumnCount();
			
			//THIS BY COLUMN
/*			   while (result.next()) {
			       for (int i = 1; i <= columnsNumber; i++) {
			           if (i > 1) System.out.print(",  ");
			           String columnValue = result.getString(i);
			           System.out.print("Column Value is : " +columnValue + " |||||| String in column is : " + rsmd.getColumnName(i));
			           assertThat(columnValue,is(result.getString("name")));
			       }
			       System.out.println("");
			   }*/
			
		    //THIS BY ROWS
			boolean status = false;
			   while (result.next()) {
			       for (int i = 1; i <= result.getRow(); i++) {
			           if (i > 1) System.out.print(",  ");
			           String columnValue = result.getString("name");
			           
			           System.out.print("Column Value is : " +columnValue);
			           
			           if(columnValue.equals(result.getString("name"))){
							status = true;
					   }
			       }
			       System.out.println("");
				   assertThat(status,is(true));
			   }
		

			
			if (result.first()) {
				assertThat("2018 2019 2020", is(result.getString("name")));
			}
			while (result.next()) {
				System.out.println(result.getString("name"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
