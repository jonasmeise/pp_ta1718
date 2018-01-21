package project;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.LinkedList;

import type.ProductReview;
import type.ReaderClass;

public class ReviewCrawler implements ReaderClass{
	private String urlToCrawl;
	private LinkedList<ProductReview> reviewList;
	private CharSequence replacementChar;
	private int cutBorder = 300;
	//<span data-hook="review-body" class="a-size-base review-text"> </span>
	//https://www.amazon.com/Fate-Stay-Night-Unlimited-Blu-ray/product-reviews/B007K7IC8I/ref=cm_cr_arp_d_paging_btm_2?ie=UTF8&pageNumber=2&reviewerType=all_reviews
	
	public ReviewCrawler() {
		reviewList = new LinkedList<ProductReview>();
	}
	
	public ReviewCrawler(String urlToCrawl) {
		reviewList = new LinkedList<ProductReview>();
		setReplacementChar("***");
		setUrlToCrawl(getReviewURL(urlToCrawl));
	}
	public ReviewCrawler(String urlToCrawl, CharSequence replacementChar) {
		reviewList = new LinkedList<ProductReview>();
		setUrlToCrawl(getReviewURL(urlToCrawl));
		setReplacementChar(replacementChar);
	}
	
	public int getCutBorder() {
		return cutBorder;
	}
	
	public void setCutBorder(int cutBorder) {
		this.cutBorder = cutBorder;
	}
	
	public String getUrlToCrawl() {
		return urlToCrawl;
	}
	public CharSequence getReplacementChar() {
		return replacementChar;
	}
	public void setReplacementChar(CharSequence replacementChar) {
		this.replacementChar = replacementChar;
	}
	public void setUrlToCrawl(String urlToCrawl) {
		this.urlToCrawl = urlToCrawl;
	}
	public LinkedList<ProductReview> getReviewList() {
		return reviewList;
	}
	public void addReviewData(ProductReview review) {
		reviewList.add(review);
	}
	
	public String getReviewURL(String productURL) {
		String output = productURL;
		output = output.replace("/gp/video/detail/", "/product-reviews/");
		output = output.replace("/product/", "/product-reviews/");
		output = output.replace("/dp/", "/product-reviews/");
		output += "&pageNumber=" + getReplacementChar() + "&reviewerType=all_reviews";
		System.out.println(output);
		return output;
	}

	public boolean getReviews(String allText) {
		int nextReview = 0;
		int objectsFound = 0;
		int start = 1;
		int end = 1;
		
		while(nextReview>=0 && start>0) { //während noch Objekte gefunden wurden
			start = allText.indexOf("<span data-hook=\"review-body\" class=\"a-size-base review-text\">", nextReview);
			end = allText.indexOf("</span>", start);
			
			if(start>=0 && end>=0) { //nur bei gültigen Werten und ohne Wiederholung
				ProductReview product = new ProductReview();
				System.out.println(start + " " + end);
				product.setReviewText(allText.substring(start+62, end).replace("<br />", " ").replace("&#34", "\""));
				System.out.println(product.getReviewText());
				
				addReviewData(product);
				objectsFound++;
			}
			
			nextReview = end; //nächste Review absuchen; falls nichts gefunden wurde, wird nextReview=0 gesetzt 
								//und die Schleife beendet
		}
		
		return (objectsFound!=0);
	}
	
	public void getData() throws IOException {
		  
		  int counter = 1;
		  boolean returnOutput = true;
		  
		  while(returnOutput) {
			  String totalData = "";
			  URL url = new URL(getUrlToCrawl().replace(getReplacementChar(), String.valueOf(counter)));
			  URLConnection myConnection = url.openConnection();
			 
			  myConnection.setRequestProperty( "User-Agent", "Mozilla/4.0 (compatible; MSIE 5.5; Windows NT 5.0; H010818)" );
			  BufferedReader in = new BufferedReader(new InputStreamReader(myConnection.getInputStream()));
			  String strLine = "";
			 
			  while ((strLine = in.readLine()) != null){
				  totalData += strLine;
			  }
			  
			  returnOutput = getReviews(totalData);
			  counter++;
			  
			  if(reviewList.size() > getCutBorder()) {
				  break;
			  }
		  }
	}
	
}
