package project;

import static org.apache.uima.fit.factory.CollectionReaderFactory.createReader;
import static org.apache.uima.fit.factory.AnalysisEngineFactory.createEngineDescription;
import org.apache.uima.analysis_engine.AnalysisEngineDescription;
import org.apache.uima.collection.CollectionReader;
import org.apache.uima.fit.pipeline.SimplePipeline;
import org.apache.uima.fit.component.CasDumpWriter;

import static org.apache.uima.fit.factory.CollectionReaderFactory.createReaderDescription;
import static org.apache.uima.fit.pipeline.SimplePipeline.runPipeline;
import de.tudarmstadt.ukp.dkpro.core.io.text.TextReader;
import de.tudarmstadt.ukp.dkpro.core.opennlp.OpenNlpPosTagger;
import de.tudarmstadt.ukp.dkpro.core.opennlp.OpenNlpSegmenter;
import de.tudarmstadt.ukp.dkpro.core.snowball.SnowballStemmer;

public class Pipeline 
{

    public static void main(String[] args)
        throws Exception
    {    	
    	
    	CollectionReader reader = createReader(
    	        TextReader.class,
    	        TextReader.PARAM_SOURCE_LOCATION, ".\\src\\test\\resources\\test\\input2.txt",
    	        TextReader.PARAM_LANGUAGE, "en");
    	
        AnalysisEngineDescription seg = createEngineDescription(OpenNlpSegmenter.class);

        AnalysisEngineDescription pos = createEngineDescription(OpenNlpPosTagger.class);
        
        AnalysisEngineDescription snw = createEngineDescription(SnowballStemmer.class, SnowballStemmer.PARAM_LANGUAGE, "en");

        AnalysisEngineDescription writer = createEngineDescription(productWriter.class);
        
    	SimplePipeline.runPipeline(reader, seg, pos, snw, writer);
    }
}
