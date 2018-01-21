package project;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.LinkedList;
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
import de.tudarmstadt.ukp.dkpro.core.api.syntax.type.dependency.Dependency;
import type.EmotionDictionary;
import type.Output;
import type.Tree;
import type.WordEmotionLink;

public class ReviewEvaluator extends JCasConsumer_ImplBase {

	//Klasse zum Zuordnen der POS-Values, Erstellen der Satz-Dependencies und
	//anschließendem Ermitteln der sinnvollen Zusammenhänge
	//Erzeugt die Raw-Data output datei
	
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
    
    public static final String PARAM_OUTPUT_FILE = "outputFile";
    @ConfigurationParameter(name = PARAM_OUTPUT_FILE, mandatory = true)
    private File outputFile;

    private List<String> lines;
    private EmotionDictionary completeDictionary;
    private int currentLine;
    private boolean returnFeed;
    private LinkedList<Output> completeOutput;

    private PrintWriter outputPrinter;
	
	@Override
    public void initialize(UimaContext context) throws ResourceInitializationException
    {        
			super.initialize(context);
		
			try {
				outputPrinter = new PrintWriter(outputFile, "UTF-8");
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			
			completeDictionary = new EmotionDictionary();
			completeOutput = new LinkedList<Output>();
			
			try {
				lines = FileUtils.readLines(emotionDataFile);
				currentLine = 0;
			} catch (IOException e) {
	            throw new ResourceInitializationException(e);
			}
			
			while(currentLine<lines.size()) {
				WordEmotionLink currentWord = new WordEmotionLink();
				
				System.out.println(lines.get(currentLine));
				
				do { //während gerade Werte gefüttert werden und zu demselben Wort gehören, weitermachen
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
			System.out.println("***" + sentence.getCoveredText() + "***");

			List<Dependency> dependencyList = new ArrayList<Dependency>();
			Token myRoot = null;
			
			for(Dependency dp : JCasUtil.selectCovered(Dependency.class, sentence)){
				dependencyList.add(dp);
				System.out.println(dp.getGovernor().getCoveredText() + " (" + dp.getGovernor().getPos().getPosValue() + ") -> " + dp.getDependent().getCoveredText() + " (" + dp.getDependent().getPos().getPosValue() + "): " + dp.getDependencyType());
			}
			
			for(Dependency dpElement : dependencyList) {
				if(dpElement.getDependencyType().compareTo("ROOT") == 0) {
					myRoot = dpElement.getGovernor();
				}
			}
			
			Tree<Token> sentenceTree = new Tree<Token>(myRoot);
			sentenceTree.generateTreeOfDependency(dependencyList, myRoot);
			sentenceTree.setParentDependencyType("ROOT");
			
			System.out.println(sentenceTree.printTree(0));
			
			LinkedList<LinkedList<Token>> myOutput = new LinkedList<LinkedList<Token>>();
			LinkedList<String[]> filterInfo = new LinkedList<String[]>();
			filterInfo.add(new String[] {"amod", "acomp", "JJ", "JJR", "JJS"});
			filterInfo.add(new String[] {"CD", "NN", "NNP", "NNS", "nsubj"});	
			sentenceTree.getRelevantInformation(filterInfo, myOutput, new LinkedList<Token>(), 0, true);		
		
			for(LinkedList<Token> tkList : myOutput) {
				if(!tkList.isEmpty() && tkList!=null && !myOutput.isEmpty()) {
					Output newOutput = new Output(tkList);
					completeOutput.add(newOutput);
					
					if(newOutput.getRoot()!=null) {
						outputPrinter.println(newOutput.getRoot().getCoveredText() + "///" + newOutput.calculateEmotionLevel(completeDictionary)+ "///" + newOutput.allTokensToString());
					}
				}
			}
		}
	}
	
	public void collectionProcessComplete() throws AnalysisEngineProcessException {
	        super.collectionProcessComplete();
	        
			System.out.println(completeOutput.size() + " gültige Beschreibungen eingelesen");
			outputPrinter.close();
	}
}
