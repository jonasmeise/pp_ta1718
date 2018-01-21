package type;

import java.util.LinkedList;

import de.tudarmstadt.ukp.dkpro.core.api.segmentation.type.Token;

public class Output {
	private LinkedList<Token> allTokens;
	private Token root;
	private int emotionLevel;
	//Fasst Rohdaten zusammen und liefert den Output der Raw-Data Datei
	
	public Output() {
		allTokens = new LinkedList<Token>();
	}
	
	public Output(LinkedList<Token> tkList) {
		allTokens = new LinkedList<Token>();
		feed(tkList);
	}
	
	public void feed(LinkedList<Token> tkList) {
		if(!tkList.isEmpty()) {
			if(tkList.size()>1) {
				root = tkList.getLast();
				
				for(Token tk : tkList) {
					if(root != tk) {
						allTokens.add(tk);
					}
				}
			}
		}
	}
	
	public Token getRoot() {
		return root;
	}
	
	public void setEmotionLevel(int wert) {
		emotionLevel = wert;
	}
	
	public int getEmotionLevel() {
		return emotionLevel;
	}
	
	public String allTokensToString() {
		String output = new String();
		
		for(Token tk : allTokens) {
			output += tk.getCoveredText()+";";
		}
		
		return output;
	}
	
	public int calculateEmotionLevel(EmotionDictionary dic) {
		int emotionalLevel = 0;
		
		for(Token tk : allTokens) {
			WordEmotionLink read = dic.getWord(tk.getCoveredText());
			
			if(read==null) {
				read = dic.getWord(tk.getStem().getValue());
			}
			
			if(read!=null) {
				emotionalLevel += read.calculateEmotionValue();
			}
		}
		
		setEmotionLevel(emotionalLevel);
		return getEmotionLevel();
	}
}
