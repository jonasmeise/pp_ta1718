package project;

import java.util.ArrayList;

import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.fit.component.JCasConsumer_ImplBase;
import org.apache.uima.fit.util.JCasUtil;
import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.cas.TOP;

import de.tudarmstadt.ukp.dkpro.core.api.segmentation.type.Token;
import de.tudarmstadt.ukp.dkpro.core.api.segmentation.type.Sentence;
import de.tudarmstadt.ukp.dkpro.core.api.lexmorph.type.pos.POS;


public class productWriter extends JCasConsumer_ImplBase {
	
	public ArrayList sentenceData = new ArrayList<Sentence>();
	
	@Override
	public void process(JCas aJCas) throws AnalysisEngineProcessException {
		
		for (Sentence sentence : JCasUtil.select(aJCas, Sentence.class)) {		
			
			sentenceData.add(sentence);
			
			for(Token token : JCasUtil.selectCovered(aJCas, Token.class, sentence)) {
						
			}
		}
	}
}
