package type;

import java.util.ArrayList;
import java.util.LinkedList;

public class SaveTable{
	private ArrayList<WordInfo> completeList;
	
	public SaveTable() {
		completeList = new ArrayList<WordInfo>();
	}
	
	public void addWord(WordInfo word) {
		//wenn bisher nicht vorhanden
		
		if(contains(word.getWord())) {
			WordInfo myWord = getWord(word.getWord());
			myWord.setCounter(myWord.getCounter()+1);
			myWord.addDescriptionWords(word.getDescriptionWords());
			myWord.setEmotionValue(myWord.getEmotionValue() + word.getEmotionValue());
		}
		else
		{
			completeList.add(word);
		}
	}
	
	public WordInfo getWord(String comparisonWord) {
		if(!completeList.isEmpty()) {
			for(WordInfo word: completeList) {
				if(word.getWord().compareTo(comparisonWord) == 0) {
					return word;
				}
			}
		}
		
		return null;
	}
	
	public boolean contains(String comparisonWord) {
		return (getWord(comparisonWord)!=null);
	}
	
	public void sortBy(String by) {
		if(!completeList.isEmpty()) {
			LinkedList<WordInfo> proxyList = new LinkedList<WordInfo>();
			int counting;
			
System.out.println("Complete list:" + completeList.size());
			
			for(WordInfo wordInfo : completeList) {
				counting = 0;
				
				if(proxyList.isEmpty()) {
					proxyList.add(wordInfo);
				}
				else 
				{
					//richtige Position auslesen
					//secondProxy für Umgehen von java.util.ConcurrentModificationException
					LinkedList<WordInfo> secondProxy = new LinkedList<WordInfo>();
					secondProxy.addAll(proxyList);
					
					for(WordInfo item : secondProxy) {
						if(by.compareTo("emotion")==0) {
							if(wordInfo.getEmotionValue() >= item.getEmotionValue() ) {
								proxyList.add(counting, wordInfo);
								break;
							} else if (secondProxy.getLast() == item) {
								proxyList.addLast(wordInfo);
								break;
							}
							counting++;
						}
						else if(by.compareTo("counter")==0) {
							if(wordInfo.getCounter() >= item.getCounter()) {
								proxyList.add(counting, wordInfo);
								break;
							} else if (secondProxy.getLast() == item) {
								proxyList.addLast(wordInfo);
								break;
							}
							counting++;
						}
						else {
							System.out.println("Ungültiger Sortierparameter");
						}
					}
				}
			}
			
			completeList.clear();
			completeList.addAll(proxyList);
		}
	}
	
	public void printStack() {
		for(WordInfo wordInfo : completeList) {
			System.out.print(wordInfo.getCounter() + "* " + wordInfo.getWord() + " (" + wordInfo.getEmotionValue() + "): ");
			
			for(String description : wordInfo.getDescriptionWords()) {
				System.out.print(", " + description);
			}
			System.out.println("");
		}
	}
}