import java.util.ArrayList;

public class PageFound implements Comparable {
	private String url;
	private double pagePoint;
	ArrayList<Word> pageWord = new ArrayList<Word>();

	public PageFound(String url) {
		this.url = url;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public double getPagePoint() {
		return pagePoint;
	}

	public void setPagePoint(double pagePoint) {
		this.pagePoint = pagePoint;
	}

	@Override
	public int compareTo(Object page) {
		// TODO Auto-generated method stub
		int comparePoint = (int) ((PageFound) page).getPagePoint();
		return (int) (this.pagePoint - comparePoint);
	}

}
