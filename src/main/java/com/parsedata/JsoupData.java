package com.parsedata;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class JsoupData {
	
	private static final String DATA_FEED = "D:/ecommGovWebsite/Public holidays in Western Australia _ Department of Mines, Industry Regulation and Safety.html";

	
	public static void main(String[] args) throws IOException {
		List<String> list = getData();
		System.out.println(list);
	}
	
	public static List<String> getData() throws IOException{
		List<String> list = new ArrayList<String>();
		
		File input = new File(DATA_FEED);
		Document doc = Jsoup.parse(input, "UTF-8", "http://example.com/");
		
		System.out.println("doc is :"+doc);

		for (Element ths : doc.select("div.field table tr")) {

			System.out.println(ths.text());

			list.add(ths.text());

		}
		return list;
	}

}