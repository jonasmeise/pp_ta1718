package project;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.uima.UimaContext;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.fit.component.JCasConsumer_ImplBase;
import org.apache.uima.fit.descriptor.ConfigurationParameter;
import org.apache.uima.fit.util.JCasUtil;
import org.apache.uima.jcas.JCas;
import org.apache.uima.resource.ResourceInitializationException;

import de.tudarmstadt.ukp.dkpro.core.api.segmentation.type.Sentence;
import de.tudarmstadt.ukp.dkpro.core.api.segmentation.type.Token;
import type.EmotionDictionary;
import type.WordEmotionLink;

public class ReviewEvaluator extends JCasConsumer_ImplBase {

	/*POS VALUES: 	1.	CC	Coordinating conjunction
	2.	CD	Cardinal number
	3.	DT	Determiner
	4.	EX	Existential there
	5.	FW	Foreign word
	6.	IN	Preposition or subordinating conjunction
	7.	JJ	Adjective
	8.	JJR	Adjective, comparative
	9.	JJS	Adjective, superlative
	10.	LS	List item marker
	11.	MD	Modal
	12.	NN	Noun, singular or mass
	13.	NNS	Noun, plural
	14.	NNP	Proper noun, singular
	15.	NNPS	Proper noun, plural
	16.	PDT	Predeterminer
	17.	POS	Possessive ending
	18.	PRP	Personal pronoun
	19.	PRP$	Possessive pronoun
	20.	RB	Adverb
	21.	RBR	Adverb, comparative
	22.	RBS	Adverb, superlative
	23.	RP	Particle
	24.	SYM	Symbol
	25.	TO	to
	26.	UH	Interjection
	27.	VB	Verb, base form
	28.	VBD	Verb, past tense
	29.	VBG	Verb, gerund or present participle
	30.	VBN	Verb, past participle
	31.	VBP	Verb, non-3rd person singular present
	32.	VBZ	Verb, 3rd person singular present
	33.	WDT	Wh-determiner
	34.	WP	Wh-pronoun
	35.	WP$	Possessive wh-pronoun
	36.	WRB	Wh-adverb
	*/

	
	public static final String PARAM_INPUT_FILE = "emotionDataFile";
    @ConfigurationParameter(name = PARAM_INPUT_FILE, mandatory = true)
    private File emotionDataFile;
    private List<String> lines;
    private EmotionDictionary completeDictionary, reviewEmotionWords;
    private int currentLine;
    private boolean returnFeed;
	
	@Override
    public void initialize(UimaContext context) throws ResourceInitializationException
    {        
			super.initialize(context);
		
			completeDictionary = new EmotionDictionary();
			reviewEmotionWords = new EmotionDictionary();
			
			try {
				lines = FileUtils.readLines(emotionDataFile);
				currentLine = 0;
			} catch (IOException e) {
	            throw new ResourceInitializationException(e);
			}
			
			while(currentLine<lines.size()) {
				WordEmotionLink currentWord = new WordEmotionLink();
				
				System.out.println(lines.get(currentLine));
				
				do{ //w�hrend gerade Werte gef�ttert werden und zu demselben Wort geh�ren, weitermachen
					returnFeed = currentWord.feedRaw(lines.get(currentLine));
					currentLine++;
				} while (returnFeed && currentLine<lines.size());
				
				//Wort komplett eingelesen, abspeichern und dann evtl. n�chstes Wort einlesen
				completeDictionary.addWord(currentWord);
			}
    }
	
	@Override
	public void process(JCas aJCas) throws AnalysisEngineProcessException {
		for (Sentence sentence : JCasUtil.select(aJCas, Sentence.class)) {
			System.out.println(sentence.getCoveredText());
			

			
			for(Token token : JCasUtil.selectCovered(aJCas, Token.class, sentence)) {			
					//System.out.println(token.getCoveredText() + ": " + token.getPos().getPosValue());
			}
		}
	}
}