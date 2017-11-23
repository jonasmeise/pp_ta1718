package project;

import java.util.ArrayList;

import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.fit.component.JCasConsumer_ImplBase;
import org.apache.uima.fit.descriptor.ConfigurationParameter;
import org.apache.uima.fit.util.JCasUtil;
import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.cas.TOP;

import de.tudarmstadt.ukp.dkpro.core.api.segmentation.type.Token;
import de.tudarmstadt.ukp.dkpro.core.api.segmentation.type.Sentence;
import de.tudarmstadt.ukp.dkpro.core.api.lexmorph.type.pos.POS;


public class ProductWriter extends JCasConsumer_ImplBase {
	
	@Override
	public void process(JCas aJCas) throws AnalysisEngineProcessException {
		
		for (Sentence sentence : JCasUtil.select(aJCas, Sentence.class)) {		
			
			//System.out.println(sentence.getCoveredText());

			/*for(Token token : JCasUtil.selectCovered(aJCas, Token.class, sentence)) {
				System.out.println(token.getStem().getValue() + token.getPos().getPosValue());		
			}*/
		}
	}
}
