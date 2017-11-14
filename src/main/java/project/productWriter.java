package project;

import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.fit.component.JCasConsumer_ImplBase;
import org.apache.uima.fit.util.JCasUtil;
import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.cas.TOP;

import de.tudarmstadt.ukp.dkpro.core.api.ner.type.NamedEntity;
import de.tudarmstadt.ukp.dkpro.core.api.segmentation.type.Sentence;


public class productWriter extends JCasConsumer_ImplBase {
	
	@Override
	public void process(JCas aJCas) throws AnalysisEngineProcessException {
		
		for (Sentence sentence : JCasUtil.select(aJCas, Sentence.class)) {
			System.out.println("Neuer Satz");
			for(TOP word : JCasUtil.selectAll(aJCas)) {
				
				System.out.println(word.toString() + ": " +  word.getType().toString());
			}
		}
	}
}