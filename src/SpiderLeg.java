import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.helper.StringUtil;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import org.apache.commons.*;
import org.apache.commons.lang3.StringUtils;

public class SpiderLeg {

	private static final String USER_AGENT = "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/535.1 (KHTML, like Gecko) Chrome/13.0.782.112 Safari/535.1";

	private List<String> links = new LinkedList<String>();
	private Document htmlDocument;

	public boolean crawl(String url) {
		try {
			Connection connection = Jsoup.connect(url).userAgent(USER_AGENT);
			Document htmlDocument = connection.get();
			this.htmlDocument = htmlDocument;

			if (connection.response().statusCode() == 200) {
				System.out.println("**Visiting** Received web page at " + url);
			}

			if (!connection.response().contentType().contains("text/html")) {
				System.out.println("**Failure** Retrieved something other than HTML");
				return false;
			}

			Elements linksOnPage = htmlDocument.select("a[href]");
			System.out.println("Found (" + linksOnPage.size() + ") links");

			for (Element link : linksOnPage) {
				this.links.add(link.absUrl("href"));
			}
			return true;
		} catch (IOException e) {
			// TODO: handle exception
			System.out.println("Error in out HTTP request " + e);
			return false;
		}
	}

	public boolean searchForWord(String query) {

		if (this.htmlDocument == null) {
			System.out.println("ERROR! Call crawl() before performing analysis on the document");
			return false;
		}
		System.out.println("Searching for the word " + query + "...");

		String bodyText = this.htmlDocument.body().text();
		String words[] = query.split(" ");
		boolean isFound = false;
		int countFound = 0;
		for (String word : words) {
			isFound = bodyText.toLowerCase().contains(word.toLowerCase());
			if (isFound) {
				countFound++;
			}
		}
		if (countFound > 0) {
			return true;
		} else {
			return isFound;
		}

	}

	public void countForWord(PageFound pageFound, String query) {
		try {
			Connection connection = Jsoup.connect(pageFound.getUrl()).userAgent(USER_AGENT);
			Document htmlDocument = connection.get();
			String bodyText = htmlDocument.body().text();
			bodyText = bodyText.toLowerCase();

			String words[] = query.split(" ");

			for (String word : words) {
				int count = StringUtils.countMatches(bodyText, word);
				Word w = new Word(word, count);
				pageFound.pageWord.add(w);
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public List<String> getLinks() {
		return this.links;
	}
}
