package project;

import static org.apache.uima.fit.factory.CollectionReaderFactory.createReader;
import static org.apache.uima.fit.factory.AnalysisEngineFactory.createEngineDescription;
import org.apache.uima.analysis_engine.AnalysisEngineDescription;
import org.apache.uima.collection.CollectionReader;
import org.apache.uima.fit.pipeline.SimplePipeline;
import org.apache.uima.fit.component.CasDumpWriter;

import de.tudarmstadt.ukp.dkpro.core.io.text.TextReader;
import de.tudarmstadt.ukp.dkpro.core.stanfordnlp.StanfordNamedEntityRecognizer;
import de.tudarmstadt.ukp.dkpro.core.tokit.BreakIteratorSegmenter;
import de.tudarmstadt.ukp.dkpro.core.stanfordnlp.StanfordParser;
import de.tudarmstadt.ukp.dkpro.core.stanfordnlp.StanfordSegmenter;
import de.unidue.langtech.teaching.pp.example.BaselineExample;
import de.unidue.langtech.teaching.pp.example.EvaluatorExample;
import de.unidue.langtech.teaching.pp.example.ReaderExample;

public class Pipeline 
{

    public static void main(String[] args)
        throws Exception
    {    	
    	
    	CollectionReader reader = createReader(
    	        TextReader.class,
    	        TextReader.PARAM_SOURCE_LOCATION, ".\\src\\test\\resources\\test\\input2.txt",
    	        TextReader.PARAM_LANGUAGE, "en");
    	
        AnalysisEngineDescription seg = createEngineDescription(BreakIteratorSegmenter.class);

        AnalysisEngineDescription ner = createEngineDescription(StanfordNamedEntityRecognizer.class);

        AnalysisEngineDescription parser = createEngineDescription(StanfordParser.class);

        AnalysisEngineDescription writer = createEngineDescription(CasDumpWriter.class);
    	
    	SimplePipeline.runPipeline(reader, seg, ner, parser, writer);
    }
}
