package type;

import java.util.ArrayList;
import java.util.List;

public class WordEmotionLink {
	private List<String> emotionWords;
	private int emotionValue;
	private String word;
	
	public WordEmotionLink() {
		emotionWords = new ArrayList<String>();
		setEmotionValue(0);
		setWord("");
	}
	
	public boolean feedRaw(String rawLine) {
		String[] mySplit;
		
		mySplit = rawLine.split("\\t");
		
		if(getWord() != "") {
			if(getWord().compareTo(mySplit[0]) != 0) { //wort eingefüttert, was allerdings keine Daten für das bereits existierende Wort bietet -> false zurückgeben
				return false;
			}
			else
			{
				if(mySplit[2].compareTo("1") == 0) { //neues emotionWord gefunden
					emotionWords.add(mySplit[1]);
				}
				
				return true; //true returnen, damit nächste Kombination eingefüttert werden kann
			}
		}
		else
		{
			setWord(mySplit[0]);
			//direkt den Wert der neben dem neuen Wert steht einspeichern
			return feedRaw(rawLine);
		}
	}
	public List<String> getEmotionWords() {
		return emotionWords;
	}
	
	public int getEmotionValue() {
		return emotionValue;
	}
	
	public void setEmotionValue(int emotionValue) {
		this.emotionValue = emotionValue;
	}
	
	public int calculateEmotionValue() {
		for(String emotionWord : emotionWords) {
			if(emotionWord.compareTo("negative")==0) {
				setEmotionValue(getEmotionValue()-1);
			} else if(emotionWord.compareTo("positive")==0) {
				setEmotionValue(getEmotionValue()+1);
			}
		}
		
		return getEmotionValue();
	}
	
	public String getWord() {
		return word;
	}
	
	public void setWord(String word) {
		this.word = word;
	}
}
