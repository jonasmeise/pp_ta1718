package type;

import java.util.ArrayList;
import java.util.List;

public class wordEmotionLink {
	private List<String> raw;
	private List<String> emotionWords;
	private int emotionValue;
	private String word;
	
	public wordEmotionLink() {
		raw = new ArrayList<String>();
		emotionWords = new ArrayList<String>();
		emotionValue = 0;
		setWord("");
	}
	
	public List<String> getRaw() {
		return raw;
	}
	
	public boolean feedRaw(String rawLine) {
		String[] mySplit;
		
		raw.add(rawLine);
		mySplit = rawLine.split("\\t");
		
		if(getWord() != "") {
			if(getWord().compareTo(mySplit[0]) != '0') { //wort eingef�ttert, was allerdings keine Daten f�r das bereits existierende Wort bietet -> false zur�ckgeben
				return false;
			}
			else
			{
				if(mySplit[2] == "1") { //neues emotionWord gefunden
					emotionWords.add(mySplit[1]);
				}
				
				return true; //true returnen, damit n�chste Kombination eingef�ttert werden kann
			}
		}
		else
		{
			setWord(mySplit[0]);
			return true;
		}
	}
	public List<String> getEmotionWords() {
		return emotionWords;
	}
	public int getEmotionValue() {
		return emotionValue;
	}
	public void calculateEmotionValue() {
		//TODO
	}
	public String getWord() {
		return word;
	}
	public void setWord(String word) {
		this.word = word;
	}
}
