package type;

import java.util.ArrayList;
import java.util.List;

public class EmotionDictionary {
	//Speichert die Daten aus der sentimentLexicon.txt datei ab
	//Ermöglicht Auslesen der Emotions-Beschreibungen
	
	private List<WordEmotionLink> myLinks;
	
	public EmotionDictionary() {
		myLinks = new ArrayList<WordEmotionLink>();
	}
	
	public void addWord(WordEmotionLink newWord) {
		myLinks.add(newWord);
	}
	
	public WordEmotionLink getWord(String wortname) {
		if(wortname == null) {
			return null;
		}
		
		for( WordEmotionLink allWords : myLinks) {
			if(allWords.getWord().startsWith(wortname)) {
				return allWords; //Wort gefunden im Dictionary, wird ausgegeben
			}
		}
		
		return null; //nichts gefunden
	}
}
