package type;

import java.util.ArrayList;
import java.util.List;

public class WordEmotionLink {
	//Wird von dem EmotionDictionary benutzt, um interne Wortrepräsentationen abzuspeichern
	//Speichert alle EmotionValues ab
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
		if(getEmotionValue()==0) {
			for(String emotionWord : emotionWords) {
				if(emotionWord.compareTo("negative")==0) {
					setEmotionValue(getEmotionValue() - 5);
				} else if(emotionWord.compareTo("positive")==0) {
					setEmotionValue(getEmotionValue() + 5);
				} else if(emotionWord.compareTo("anger")==0) {
					setEmotionValue(getEmotionValue() - 2);
				} else if(emotionWord.compareTo("anticipation")==0) {
					setEmotionValue(getEmotionValue() + 3);
				} else if(emotionWord.compareTo("disgust")==0) {
					setEmotionValue(getEmotionValue() - 3);
				} else if(emotionWord.compareTo("fear")==0) {
					setEmotionValue(getEmotionValue() - 2);
				} else if(emotionWord.compareTo("joy")==0) {
					setEmotionValue(getEmotionValue() + 3);
				} else if(emotionWord.compareTo("sadness")==0) {
					setEmotionValue(getEmotionValue() - 4);
				} else if(emotionWord.compareTo("surprise")==0) {
					setEmotionValue(getEmotionValue() + 1);
				} else if(emotionWord.compareTo("trust")==0) {
					setEmotionValue(getEmotionValue() + 3);
				}
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
