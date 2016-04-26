import java.util.Scanner;

public class SpiderTest {
	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		System.out.print("Input Url you want to search :");
		String url = in.next();
		in.nextLine();
		// url = "http://" + url;

		System.out.print("Input Search Word :");
		String query = in.nextLine();

		Spider spider = new Spider();
		spider.search(url, query);

		System.out.println("---------------------------------------------------");
		spider.countPageFound(query);
		spider.printPageFound();

		System.out.println("---------------------------------------------------");
		spider.calculateSimilarity();
		spider.printPageFound();

		System.out.println("---------------------------------------------------");
		spider.printPagePoint();
		System.out.println("---------------------------------------------------");

		spider.sortPage();
		spider.printResultPage();
		System.out.println("---------------------------------------------------");
	}
}
