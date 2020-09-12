package com.configdb;

import java.sql.SQLException;

import javax.sql.DataSource;

import org.h2.tools.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

@Profile("hsqldB")
@Configuration
public class HsqlDataSrc {

	@Bean
	public DataSource dataSource() {

		EmbeddedDatabaseBuilder builder = new EmbeddedDatabaseBuilder();
		EmbeddedDatabase db = builder.setType(EmbeddedDatabaseType.H2).addScript("db/sql/create-db.sql")
				.addScript("db/sql/insert-data.sql").build();
		return db;

	}

	// Create the web Server
	@Bean(initMethod = "start", destroyMethod = "stop")
	public Server startDBManager() throws SQLException {
		return Server.createWebServer();
	}

}