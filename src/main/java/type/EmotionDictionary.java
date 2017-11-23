package type;

import java.util.ArrayList;
import java.util.List;

public class EmotionDictionary {
	private List<WordEmotionLink> myLinks;
	
	public EmotionDictionary() {
		myLinks = new ArrayList<WordEmotionLink>();
	}
	
	public void addWord(WordEmotionLink newWord) {
		myLinks.add(newWord);
	}
	
	public WordEmotionLink getWord(String wortname) {
		for( WordEmotionLink allWords : myLinks) {
			if(allWords.getWord().compareTo(wortname) == 0) {
				return allWords; //Wort gefunden im Dictionary, wird ausgegeben
			}
		}
		
		return null; //nichts gefunden
	}
}
