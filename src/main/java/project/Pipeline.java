package project;

import static org.apache.uima.fit.factory.CollectionReaderFactory.createReader;
import static org.apache.uima.fit.factory.AnalysisEngineFactory.createEngineDescription;
import org.apache.uima.analysis_engine.AnalysisEngineDescription;
import org.apache.uima.collection.CollectionReader;
import org.apache.uima.fit.pipeline.SimplePipeline;
import org.apache.uima.fit.component.CasDumpWriter;

import static org.apache.uima.fit.factory.CollectionReaderFactory.createReaderDescription;
import static org.apache.uima.fit.pipeline.SimplePipeline.runPipeline;

import java.util.ArrayList;

import de.tudarmstadt.ukp.dkpro.core.api.segmentation.type.Sentence;
import de.tudarmstadt.ukp.dkpro.core.io.text.TextReader;
import de.tudarmstadt.ukp.dkpro.core.opennlp.OpenNlpPosTagger;
import de.tudarmstadt.ukp.dkpro.core.opennlp.OpenNlpSegmenter;
import de.tudarmstadt.ukp.dkpro.core.snowball.SnowballStemmer;

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
    	String emotionDataFile = ".\\NRC_sentimentLexicon.txt";
    	
    	CollectionReader reader = createReader(ReviewReader.class, 
    			ReviewReader.PARAM_INPUT_FILE, fileString);
    	
        AnalysisEngineDescription seg = createEngineDescription(OpenNlpSegmenter.class);

        AnalysisEngineDescription pos = createEngineDescription(OpenNlpPosTagger.class);
        
        AnalysisEngineDescription snw = createEngineDescription(SnowballStemmer.class, SnowballStemmer.PARAM_LANGUAGE, "en");

        AnalysisEngineDescription evl = createEngineDescription(ReviewEvaluator.class, ReviewEvaluator.PARAM_INPUT_FILE, emotionDataFile);
        
        AnalysisEngineDescription writer = createEngineDescription(ProductWriter.class);
        
    	SimplePipeline.runPipeline(reader, seg, pos, snw, evl, writer);
    }
}
