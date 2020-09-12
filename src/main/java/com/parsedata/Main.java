/**
 * 
 */
package com.parsedata;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class Main {

	private static final String DATA_FEED = "file:///D:/ecommGovWebsite/Public%20holidays%20in%20Western%20Australia%20_%20Department%20of%20Mines,%20Industry%20Regulation%20and%20Safety.html";

	public static void main(String[] args) throws IOException {
		InputStream stream = null;
		BufferedInputStream buffer = null;
		try {
			URL url = new URL(DATA_FEED);

			stream = url.openStream();

			buffer = new BufferedInputStream(stream);

			StringBuilder sb = new StringBuilder();

			while (true) {
				int data = buffer.read();
				if (data == -1) {
					break;
				} else {
					sb.append((char) data);
				}
			}

			System.out.println(sb);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			stream.close();
			buffer.close();

		}

	}
}
