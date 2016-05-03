import java.io.IOException;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class TestJsoup {
	
	private static final String USER_AGENT = "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/535.1 (KHTML, like Gecko) Chrome/13.0.782.112 Safari/535.1";
	
	public static void main(String[] args) {
		String html = "<html><head><title>Test Document</title></head><body><p>mean meaning means</p></body></html>";
		Document doc = Jsoup.parse(html);
		String bodyText = doc.body().text();
		String titleText = doc.title();
		System.out.println(titleText+" | "+bodyText);
		
		int count = StringUtils.countMatches(bodyText, "mean");
		System.out.println(count);
		
		double d1 = 15.45;
		double d2 = 11.50;
		
		int reval = Double.compare(d1, d2);
		System.out.println(reval);
		
		String temp = "This is the best ubuntu in history and also server";
		System.out.println(temp.contains("ubuntu server"));
		/*
		String largeText = "asdfasdfasdfasdfasdfasdfasdfas";
		int count = StringUtils.countMatches(largeText, "as");
		System.out.println(count);
		
		try {
			Connection connection = Jsoup.connect("https://www.blognone.com/").userAgent(USER_AGENT);
			Document htmlDocument = connection.get();
			String bodyText = htmlDocument.body().text();
			bodyText = bodyText.toLowerCase();
			bodyText = bodyText.replace(" ", "|");
			System.out.println(bodyText);
			
			int count = StringUtils.countMatches(bodyText, "python");
			System.out.println(count);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		double temp = 5/(double)2;
		System.out.println(temp);
		System.out.println(Math.log10(temp));
		
		Integer i = new Integer(5);
		System.out.println(i*3);*/
	}
}
