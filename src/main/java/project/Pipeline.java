package project;

import static org.apache.uima.fit.factory.CollectionReaderFactory.createReader;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.LinkedList;

import static org.apache.uima.fit.factory.AnalysisEngineFactory.createEngineDescription;
import org.apache.uima.analysis_engine.AnalysisEngineDescription;
import org.apache.uima.collection.CollectionReader;
import org.apache.uima.fit.pipeline.SimplePipeline;
import de.tudarmstadt.ukp.dkpro.core.maltparser.MaltParser;
import de.tudarmstadt.ukp.dkpro.core.opennlp.OpenNlpPosTagger;
import de.tudarmstadt.ukp.dkpro.core.opennlp.OpenNlpSegmenter;
import de.tudarmstadt.ukp.dkpro.core.snowball.SnowballStemmer;
import type.ProductReview;
import type.SaveTable;
import type.WordInfo;
import de.tudarmstadt.ukp.dkpro.core.api.syntax.type.dependency.Dependency;

public class Pipeline 
{

    public static void main(String[] args)
        throws Exception
    {    	
    	String fileString =  ".\\Musical_Instruments_5.json";
    	fileString = "https://www.amazon.com/Princess-Mononoke-Bluray-Combo-Blu-ray/dp/B073ZWK3FK/ref=sr_1_1?ie=UTF8&qid=1516203048&sr=8-1&keywords=princess+mononoke";
    	String emotionDataFile = ".\\NRC_sentimentLexicon.txt";
    	String outputFile = ".\\output.html";
    	String outputFilePos = ".\\positive.html";
    	String outputFileNeg = ".\\negative.html";
    	String[] ignore = {"i"};
    	String inputType = "amazonURL";
    	String filterASIN = "B0002E1G5C";
    	String sortBy="quantity";
    	int maxCut = 150;
    	
    	//Arguments: file,ASIN/amazon filePath/URL maxCut emotion/quantity;
    	if(args.length == 4) {
    		if(args[0].contains("file")) {
    			String[] mySplit = args[0].split(",");
    			if(mySplit.length==2) {
    				inputType = mySplit[0];
    				filterASIN = mySplit[1];
    			}
    		}
    		else {
    			inputType = args[0];
    		}
    		
    		fileString = args[1];
    		maxCut = Integer.valueOf(args[2]);
    		sortBy = args[3];
    	} 
    	
    	CollectionReader reader = createReader(ReviewReader.class, 
    			ReviewReader.PARAM_INPUT_FILE, fileString, //oder amazonURL
    			ReviewReader.PARAM_INPUT_TYPE, inputType,//file oder amazonURL
    			ReviewReader.PARAM_FILTER_ASIN, filterASIN, //nur n�tig f�r file; 
    			ReviewReader.PARAM_MAX_CUT, maxCut); 
    	
        AnalysisEngineDescription seg = createEngineDescription(OpenNlpSegmenter.class);

        AnalysisEngineDescription pos = createEngineDescription(OpenNlpPosTagger.class);
        
        AnalysisEngineDescription snw = createEngineDescription(SnowballStemmer.class, SnowballStemmer.PARAM_LANGUAGE, "en");
        
        AnalysisEngineDescription dpd = createEngineDescription(MaltParser.class);

        AnalysisEngineDescription evl = createEngineDescription(ReviewEvaluator.class, ReviewEvaluator.PARAM_INPUT_FILE, emotionDataFile, ReviewEvaluator.PARAM_OUTPUT_FILE, outputFile);
        
        //AnalysisEngineDescription writer = createEngineDescription(ProductWriter.class);
        
    	SimplePipeline.runPipeline(reader, seg, pos, snw, dpd, evl);
    	
    	System.out.println("Pipeline finished.");
    	
    	//Einlesen der Daten die durch die Pipeline generiert wurden, anschlie�endes
    	//Sortieren und Einordnen in SaveTable
    	
    	FileInputStream fstream = new FileInputStream(outputFile);
		BufferedReader br = new BufferedReader(new InputStreamReader(fstream));

		LinkedList<String> allInput = new LinkedList<String>();
		boolean finished = false;
		
		while (!finished)   {
			String currentObject = br.readLine();
			finished = (currentObject==null);
			
			if(!finished) {
				allInput.add(currentObject);
			}
		}
		
		SaveTable myTable = new SaveTable();
		
		for(String str: allInput) { //Raw Output wieder einlesen um HTML table zu erzeugen
			String[] split = str.split("///");
			String[] wordSplit = null;
			
			if(split.length >= 3) {
				wordSplit = split[2].split(";");
			}
			
			WordInfo myWordInfo = new WordInfo();
			myWordInfo.setWord(split[0].toLowerCase());
			myWordInfo.setCounter(1);
			myWordInfo.setEmotionValue(Integer.parseInt(split[1]));
			
			if(wordSplit!=null && !contains(myWordInfo.getWord(), ignore)) {
				for(String description : wordSplit) {
					myWordInfo.addDescriptionWords(description);
				}
				
				myTable.addWord(myWordInfo);
			}
		}
		
		myTable.sortBy(sortBy);
		myTable.printStack();
		//myTable.printOutput(outputFile); //Endg�ltiger Output
		myTable.printOutput(outputFilePos, 1);
		myTable.printOutput(outputFileNeg, -1);
    }
    
    public static boolean contains(String str, String[] array) {
    	for(String zeichenkette : array) {
    		if(zeichenkette.compareTo(str)==0) {
    			return true;
    		}
    	}
    	return false;
    }
}
