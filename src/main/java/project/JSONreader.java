package project;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class JSONreader{
	
	public String file;
	public ArrayList<productReview> reviewList = new ArrayList<productReview>();
	
	public JSONreader(String file) {
		this.file = file;
	}
	
	public static void main(String[] args) throws IOException {
		JSONreader myJSONreader = new JSONreader("C:\\Users\\Jonas\\git\\Musical_Instruments_5.json");
		
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
		productReview currentReview = new productReview();

		while ((strLine = br.readLine()) != null)   {
		  
		  currentReview.setData(strLine);
		  
		  if(currentReview.getAsin()!=asin || asin == "") {
			  //Objekt ist unser gesuchter ASIN-Wert oder wir lesen alle Objekte ein
			  reviewList.add(currentReview);
		  }
		}

		//Input-Stream schlieﬂen
		br.close();
	}
	
	public ArrayList<productReview> getReviewList(){
		return reviewList;
	}
}
