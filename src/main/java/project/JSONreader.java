package project;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;

import type.ProductReview;
import type.ReaderClass;

public class JSONreader implements ReaderClass{
	
	public String file;
	public String filterASIN;
	public LinkedList<ProductReview> reviewList = new LinkedList<ProductReview>();
	
	public JSONreader(String file) {
		this.file = file;
	}
	
	public void setFilterASIN(String filterASIN) {
		this.filterASIN = filterASIN;
	}
	
	public String getFilterASIN() {
		return this.filterASIN;
	}

	//liest Objekte einer JSON ein und speichert sie in Liste von productReview-Objekten ab
	public void getData() throws IOException {
		FileInputStream fstream = new FileInputStream(file);
		BufferedReader br = new BufferedReader(new InputStreamReader(fstream));

		String strLine;

		while ((strLine = br.readLine()) != null)   {
		  
		  ProductReview currentReview = new ProductReview();
		  currentReview.setData(strLine);
		  
		  if(currentReview.getAsin().compareTo(getFilterASIN())==0 || getFilterASIN()==null) {
			  //Objekt ist unser gesuchter ASIN-Wert oder wir lesen alle Objekte ein
			  reviewList.add(currentReview);
		  }
		}
		
		//Input-Stream schlieﬂen
		br.close();
	}
	
	public void addReviewData(ProductReview review) {
		reviewList.add(review);
	}
	
	public LinkedList<ProductReview> getReviewList(){
		return reviewList;
	}
}
