package type;

import java.util.ArrayList;
import java.util.List;

public class emotionDictionary {
	private List<wordEmotionLink> myLinks;
	
	public emotionDictionary() {
		myLinks = new ArrayList<wordEmotionLink>();
	}
	
	public void addWord(wordEmotionLink newWord) {
		myLinks.add(newWord);
	}
	
	public wordEmotionLink getWord(String wortname) {
		for( wordEmotionLink allWords : myLinks) {
			if(allWords.getWord().compareTo(wortname) == 0) {
				return allWords; //Wort gefunden im Dictionary, wird ausgegeben
			}
		}
		
		return null; //nichts gefunden
	}
}
