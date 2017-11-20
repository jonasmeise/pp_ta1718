package project;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class JSONreader{
	
	public String file;
	public ArrayList<ProductReview> reviewList = new ArrayList<ProductReview>();
	
	public JSONreader(String file) {
		this.file = file;
	}
	
	//Testweise Main-Methode
	public static void main(String[] args) throws IOException {
		JSONreader myJSONreader = new JSONreader(args[0]);
		
		myJSONreader.readObjects();
	}	
	
	public void readObjects() throws IOException {
		readObjects(""); 
	}
	
	//liest Objekte einer JSON ein und speichert sie in Liste von productReview-Objekten ab
	public void readObjects(String asin) throws IOException {
		FileInputStream fstream = new FileInputStream(file);
		BufferedReader br = new BufferedReader(new InputStreamReader(fstream));

		String strLine;

		while ((strLine = br.readLine()) != null)   {
		  
		  ProductReview currentReview = new ProductReview();
		  currentReview.setData(strLine);
		  
		  if(currentReview.getAsin()!=asin || asin == "") {
			  //Objekt ist unser gesuchter ASIN-Wert oder wir lesen alle Objekte ein
			  reviewList.add(currentReview);
		  }
		}
		
		System.out.println(reviewList.size() + " zutreffende Werte eingelesen");
		
		//Input-Stream schlieﬂen
		br.close();
	}
	
	public ArrayList<ProductReview> getReviewList(){
		return reviewList;
	}
}
