package type;

public class ProductReview {
	private String reviewerID;
	private String asin;
	private String reviewerName;
	private String helpful;
	private String reviewText;
	private String overall;
	private String summary;
	private String unixReviewTime;
	private String reviewTime;
	
	public ProductReview() {}
	public ProductReview(String dataset) {
		setData(dataset);
	}
	
	/* liefert Werte für 1-dimensionale JSON-Werte zurück.
	 * source={"reviewerID": "A2IBPI20UZIR0U", "..."}, String="reviewerID"
	 * -> Wert "A2IBPI20UZIR0U" wird zurückgegeben
	 * Duplikate bei der Findung werden ignoriert
	 */
	public String findValues(String source, String identifier) {
		int startWert, endWert;
		
		startWert = source.indexOf(identifier) + identifier.length()+4;
		endWert = source.indexOf("\", \"", startWert);
		
		if(endWert<0) {
			return source.substring(startWert).substring(0, 11); //Letzter Eintrag, datum einlesen. Wert ist hier konstant eingespeichert
		}
		return source.substring(startWert, endWert);
	}
	
	public void setData(String dataset) {
		setReviewerID(findValues(dataset, "reviewerID"));
		setAsin(findValues(dataset, "asin"));
		setReviewerName(findValues(dataset, "reviewerName"));
		setHelpful(findValues(dataset, "helpful"));
		setReviewText(findValues(dataset, "reviewText"));
		setOverall(findValues(dataset, "overall"));
		setSummary(findValues(dataset, "summary"));
		setUnixReviewTime(findValues(dataset, "unixReviewTime"));
		setReviewTime(findValues(dataset, "reviewTime"));
	}
	
	public String getReviewerID() {
		return reviewerID;
	}
	public void setReviewerID(String reviewerID) {
		this.reviewerID = reviewerID;
	}
	public String getReviewerName() {
		return reviewerName;
	}
	public void setReviewerName(String reviewerName) {
		this.reviewerName = reviewerName;
	}
	public String getHelpful() {
		return helpful;
	}
	public void setHelpful(String helpful) {
		this.helpful = helpful;
	}
	public String getReviewText() {
		return reviewText;
	}
	public void setReviewText(String reviewText) {
		this.reviewText = reviewText;
	}
	public String getOverall() {
		return overall;
	}
	public void setOverall(String overall) {
		this.overall = overall;
	}
	public String getSummary() {
		return summary;
	}
	public void setSummary(String summary) {
		this.summary = summary;
	}
	public String getUnixReviewTime() {
		return unixReviewTime;
	}
	public void setUnixReviewTime(String unixReviewTime) {
		this.unixReviewTime = unixReviewTime;
	}
	public String getReviewTime() {
		return reviewTime;
	}
	public void setReviewTime(String reviewTime) {
		this.reviewTime = reviewTime;
	}
	public String getAsin() {
		return this.asin;
	}
	public void setAsin(String asin) {
		this.asin = asin;
	}
}
