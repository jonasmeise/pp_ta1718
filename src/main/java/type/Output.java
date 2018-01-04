package type;

import java.util.LinkedList;

import de.tudarmstadt.ukp.dkpro.core.api.segmentation.type.Token;

public class Output {
	private LinkedList<Token> allTokens;
	private Token root;
	private int emotionLevel;
	
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
				System.out.println("---------" + root.getCoveredText());
				
				for(Token tk : tkList) {
					if(root != tk) {
						allTokens.add(tk);
						System.out.println("+++++++" + tk.getCoveredText());
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
	
	public int calculateEmotionLevel(EmotionDictionary dic) {
		int emotionLevel = 0;
		WordEmotionLink currentLink = null;
		
		for(Token tk : allTokens) {
			WordEmotionLink read = dic.getWord(tk.getCoveredText());
			
			if(read!=null) {
				currentLink = dic.getWord(tk.getCoveredText());
			}
			else {
				if(tk.getStem() != null) {
					currentLink = dic.getWord(tk.getStem().getValue());
				}
			}
			
			if(currentLink!=null) {
				emotionLevel += currentLink.getEmotionValue();
			}
		}
		
		setEmotionLevel(emotionLevel);
		return getEmotionLevel();
	}
}
