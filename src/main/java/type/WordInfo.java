package type;

import java.util.ArrayList;

public class WordInfo {
	private String word;
	private ArrayList<String> descriptionWords;
	private int counter;
	private int emotionValue;
	
	public WordInfo() {
		descriptionWords = new ArrayList<String>();
	}
	
	public String getWord() {
		return word;
	}
	public void setWord(String word) {
		this.word = word;
	}

	public ArrayList<String> getDescriptionWords() {
		return descriptionWords;
	}
	
	public void addDescriptionWords(ArrayList<String> descriptionWord) {
		this.descriptionWords.addAll(descriptionWord);
	}
	
	public void addDescriptionWords(String descriptionWord) {
		this.descriptionWords.add(descriptionWord);
	}
	
	public int getCounter() {
		return counter;
	}
	
	public void setCounter(int counter) {
		this.counter = counter;
	}
	
	public int getEmotionValue() {
		return emotionValue;
	}
	
	public void setEmotionValue(int emotionValue) {
		this.emotionValue = emotionValue;
	}
}
