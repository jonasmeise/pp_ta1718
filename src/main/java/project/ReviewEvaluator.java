package project;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.uima.UimaContext;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.collection.CollectionException;
import org.apache.uima.fit.component.JCasConsumer_ImplBase;
import org.apache.uima.fit.descriptor.ConfigurationParameter;
import org.apache.uima.fit.util.JCasUtil;
import org.apache.uima.jcas.JCas;
import org.apache.uima.resource.ResourceInitializationException;

import de.tudarmstadt.ukp.dkpro.core.api.segmentation.type.Sentence;
import type.emotionDictionary;
import type.wordEmotionLink;

public class ReviewEvaluator extends JCasConsumer_ImplBase {

	public static final String PARAM_INPUT_FILE = "emotionDataFile";
    @ConfigurationParameter(name = PARAM_INPUT_FILE, mandatory = true)
    private File emotionDataFile;
    private List<String> lines;
    private emotionDictionary completeDictionary;
    private int currentLine;
    private boolean returnFeed;
	
	@Override
    public void initialize(UimaContext context) throws ResourceInitializationException
    {        
			super.initialize(context);
		
			completeDictionary = new emotionDictionary();
			
			try {
				lines = FileUtils.readLines(emotionDataFile);
				currentLine = 0;
			} catch (IOException e) {
	            throw new ResourceInitializationException(e);
			}
			
			while(currentLine<lines.size()) {
				wordEmotionLink currentWord = new wordEmotionLink();
				
				System.out.println(lines.get(currentLine));
				
				do{ //während gerade Werte gefüttert werden und zu demselben Wort gehören, weitermachen
					returnFeed = currentWord.feedRaw(lines.get(currentLine));
					currentLine++;
				} while (returnFeed && currentLine<lines.size());
				
				//Wort komplett eingelesen, abspeichern und dann evtl. nächstes Wort einlesen
				completeDictionary.addWord(currentWord);
			}
    }
	
	@Override
	public void process(JCas aJCas) throws AnalysisEngineProcessException {
		for (Sentence sentence : JCasUtil.select(aJCas, Sentence.class)) {		
			
			System.out.println(sentence.getCoveredText());

			/*for(Token token : JCasUtil.selectCovered(aJCas, Token.class, sentence)) {
				System.out.println(token.getStem().getValue() + token.getPos().getPosValue());		
			}*/
		}
		
	}

	
}
