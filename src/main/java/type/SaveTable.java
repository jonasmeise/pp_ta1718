package type;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.LinkedList;

public class SaveTable{
	//Konvertierung der Raw-Data Datei in die endgültige HTML-Tabelle
	//Sortieren + Zuordnen aller Rohdaten
	
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
			
			//Verhälltnisbewertung
			/*for(WordInfo wordIf : completeList) {
				wordIf.setEmotionValue(Math.round(2*wordIf.getEmotionValue()/wordIf.getCounter()));
			}*/

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
						else if(by.compareTo("quantity")==0) {
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
				System.out.print(description + ",");
			}
			System.out.println("");
		}
	}

	public void printOutput(String outputFile) throws FileNotFoundException, UnsupportedEncodingException {
		PrintWriter outputPrinter;
		outputPrinter = new PrintWriter(outputFile, "UTF-8");
		outputPrinter.println("<html><link rel=\"stylesheet\" href=\"styles.css\"><body><table id=\"name\"><tr><th>#</th><th>Word</th><th>Emotional Rating</th><th>Emotional Adjectives</th></tr>");
		//number; word; rating; adjectives
		
		for(WordInfo wordInfo : completeList) {
			outputPrinter.print("<tr><td>" + wordInfo.getCounter() + "</td><td>" + wordInfo.getWord() + "</td><td>" + wordInfo.getEmotionValue() + "</td><td>");
			
			for(String description : wordInfo.getDescriptionWords()) {
				outputPrinter.print(description + ", ");
			}
			outputPrinter.println("</td></tr>");
		}
		
		outputPrinter.println("</table></body></html>");
		outputPrinter.close();
	}
}