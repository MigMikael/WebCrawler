import java.awt.TextArea;
import java.util.*;

public class Spider {
	private static final int MAX_PAGES_TO_SEARCH = 100;
	private Set<String> pagesVisited = new HashSet<String>();
	private List<String> pagesToVisit = new LinkedList<String>();
	private List<PageFound> pagesFoundList = new LinkedList<PageFound>();
	private ArrayList<Integer> n = new ArrayList<>();
	private ArrayList<Double> length = new ArrayList<>();
	private ArrayList<PageFound> resultPage = new ArrayList<PageFound>();

	private String nextUrl() {
		String nextUrl;
		do {
			nextUrl = this.pagesToVisit.remove(0);
		} while (this.pagesVisited.contains(nextUrl));

		this.pagesVisited.add(nextUrl);
		return nextUrl;
	}

	public void search(String url, String query) {
		int i = 1;
		while (this.pagesVisited.size() < MAX_PAGES_TO_SEARCH) {
			System.out.println("\n#### Page " + i + " ####");
			String currentUrl;
			SpiderLeg leg = new SpiderLeg();
			if (this.pagesToVisit.isEmpty()) {
				currentUrl = url;
			} else {
				currentUrl = this.nextUrl();
			}
			
			boolean isSuccess = false;
			if(leg.crawl(currentUrl)){
				isSuccess = leg.searchForWord(query);
			} else {
				isSuccess = false;
			}

			if (isSuccess) {
				System.out.println(String.format("**Success** Word %s found at %s", query, currentUrl));
				pagesFoundList.add(new PageFound(currentUrl));
			} else {
				System.out.println(String.format("**Unsuccess** Not Found Word at %s", currentUrl));
			}
			this.pagesToVisit.addAll(leg.getLinks());
			i++;
		}
		System.out.println(String.format("**Done** Visited %s web page(s)", this.pagesVisited.size()));
	}

	public void countPageFound(String query) {
		for (int i = 0; i < pagesFoundList.size(); i++) {
			SpiderLeg spiderLeg = new SpiderLeg();
			spiderLeg.countForWord(pagesFoundList.get(i), query);
		}
	}

	public void printPageFound() {
		int i = 1;
		for (PageFound pageFound : pagesFoundList) {
			System.out.println("page" + i);
			for (Word word : pageFound.pageWord) {
				System.out.print(word.getWord() + " " + word.getAmountWord() + " | ");
			}
			i++;
			System.out.println();
		}
		System.out.println(pagesFoundList.size());
	}

	public void countn() {
		int querySize = pagesFoundList.get(0).pageWord.size();
		for (int i = 0; i < querySize; i++) {
			int count = 0;
			for (int j = 0; j < pagesFoundList.size(); j++) {
				if (pagesFoundList.get(j).pageWord.get(i).getAmountWord() > 0) {
					count++;
				}
			}
			Integer temp = new Integer(count);
			n.add(temp);
		}
	}

	public void countLength() {
		for (PageFound page : pagesFoundList) {
			double sum = 0;
			for (int i = 0; i < page.pageWord.size(); i++) {
				double w = page.pageWord.get(i).getAmountWord();
				w = w * w;
				sum += w;
			}
			sum = Math.sqrt(sum);
			Double d = new Double(sum);
			length.add(d);
		}
	}

	public void calculateSimilarity() {
		countn(); // IDF
		for (PageFound page : pagesFoundList) {
			for (int i = 0; i < page.pageWord.size(); i++) {
				double nTemp = ((double) n.get(i));
				double IDF = Math.log10(MAX_PAGES_TO_SEARCH / nTemp);

				double TF = page.pageWord.get(i).getAmountWord();

				page.pageWord.get(i).setAmountWord(TF * IDF);
			}
		}
		countLength(); // Normalize
		for (int i = 0; i < pagesFoundList.size(); i++) {
			PageFound page = pagesFoundList.get(i);
			for (int j = 0; j < page.pageWord.size(); j++) {
				double w = page.pageWord.get(j).getAmountWord();
				double l = length.get(i);

				page.pageWord.get(j).setAmountWord(w * l);
			}
		} // Similarity Score
		for (int i = 0; i < pagesFoundList.size(); i++) {
			PageFound page = pagesFoundList.get(i);
			double sum = 0;
			for (int j = 0; j < page.pageWord.size(); j++) {
				sum += page.pageWord.get(j).getAmountWord();
			}
			page.setPagePoint(sum);
		}
	}

	public void sortPage() {
		int maxIndex = 0;
		int size = pagesFoundList.size();
		for (int i = 0; i < size; i++) {
			int max = Integer.MIN_VALUE;
			double maxD = (double) max;
			for (int j = 0; j < pagesFoundList.size(); j++) {
				if (pagesFoundList.get(j).getPagePoint() > maxD) {
					maxD = (int) (pagesFoundList.get(j).getPagePoint());
					maxIndex = j;
				}
			}
			resultPage.add(pagesFoundList.get(maxIndex));
			pagesFoundList.remove(maxIndex);
		}
	}

	public void printPagePoint() {
		for (int i = 0; i < pagesFoundList.size(); i++) {
			System.out.println(pagesFoundList.get(i).getUrl());
			System.out.println("Page " + (i + 1) + ": " + pagesFoundList.get(i).getPagePoint());
		}
	}

	public void printResultPage() {
		for (int i = 0; i < resultPage.size(); i++) {
			System.out.println("Page " + (i + 1) + ": " + resultPage.get(i).getPagePoint());
			System.out.println(resultPage.get(i).getUrl());
		}
	}

	public void showResult(TextArea resultTextArea) {
		resultTextArea.setText("");
		for (int i = 0; i < resultPage.size(); i++) {
			double point = resultPage.get(i).getPagePoint();
			point = Math.round(point * 100.0) / 100.0;
			resultTextArea.append("Page " + (i + 1) + ": " + point + "\n");
			resultTextArea.append(resultPage.get(i).getUrl() + "\n\n");
		}
	}
}
