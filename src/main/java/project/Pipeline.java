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
    	
    	/*CollectionReader reader = createReader(
    	        TextReader.class,
    	        TextReader.PARAM_SOURCE_LOCATION, ".\\src\\test\\resources\\test\\input2.txt",
    	        TextReader.PARAM_LANGUAGE, "en");*/
    	
    	String fileString =  ".\\Musical_Instruments_5.json";
    	fileString = "https://www.amazon.com/Haibane-Renmei-Complete-Anime-Classics/dp/B008D1RCS6/ref=sr_1_1?ie=UTF8&qid=1515604615&sr=8-1&keywords=haibane+renmei";
    	String emotionDataFile = ".\\NRC_sentimentLexicon.txt";
    	String outputFile = ".\\output.txt";
    	
    	CollectionReader reader = createReader(ReviewReader.class, 
    			ReviewReader.PARAM_INPUT_FILE, fileString, //oder amazonURL
    			ReviewReader.PARAM_INPUT_TYPE, "amazonURL",//file oder amazonURL
    			ReviewReader.PARAM_FILTER_ASIN, "B0002E1G5C"); //nur nötig für file; 
    	
        AnalysisEngineDescription seg = createEngineDescription(OpenNlpSegmenter.class);

        AnalysisEngineDescription pos = createEngineDescription(OpenNlpPosTagger.class);
        
        AnalysisEngineDescription snw = createEngineDescription(SnowballStemmer.class, SnowballStemmer.PARAM_LANGUAGE, "en");
        
        AnalysisEngineDescription dpd = createEngineDescription(MaltParser.class);

        AnalysisEngineDescription evl = createEngineDescription(ReviewEvaluator.class, ReviewEvaluator.PARAM_INPUT_FILE, emotionDataFile, ReviewEvaluator.PARAM_OUTPUT_FILE, outputFile);
        
        //AnalysisEngineDescription writer = createEngineDescription(ProductWriter.class);
        
    	SimplePipeline.runPipeline(reader, seg, pos, snw, dpd, evl);
    	
    	System.out.println("Pipeline finished.");
    	
    	//Einlesen der Daten die durch die Pipeline generiert wurden, anschließendes
    	//Sortieren und Einordnen in Hashtabelle
    	
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
		
		for(String str: allInput) {
			String[] split = str.split("///");
			String[] wordSplit = null;
			
			if(split.length >= 3) {
				wordSplit = split[2].split(";");
			}
			
			WordInfo myWordInfo = new WordInfo();
			myWordInfo.setWord(split[0]);
			myWordInfo.setCounter(1);
			myWordInfo.setEmotionValue(Integer.parseInt(split[1]));
			
			if(wordSplit!=null) {
				for(String description : wordSplit) {
					myWordInfo.addDescriptionWords(description);
				}
			}
			
			myTable.addWord(myWordInfo);
		}
		
		myTable.sortBy("emotion");
		myTable.printStack();
    }
}
