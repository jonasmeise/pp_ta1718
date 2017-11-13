package de.unidue.langtech.teaching.pp.example;

import static org.apache.uima.fit.factory.AnalysisEngineFactory.createEngine;
import static org.apache.uima.fit.factory.AnalysisEngineFactory.createEngineDescription;
import static org.junit.Assert.assertEquals;

import org.apache.uima.UIMAException;
import org.apache.uima.analysis_engine.AnalysisEngine;
import org.apache.uima.analysis_engine.AnalysisEngineDescription;
import org.apache.uima.fit.factory.JCasFactory;
import org.apache.uima.fit.util.JCasUtil;
import org.apache.uima.jcas.JCas;
import org.junit.Test;

import de.tudarmstadt.ukp.dkpro.core.tokit.BreakIteratorSegmenter;
import de.unidue.langtech.teaching.pp.type.DetectedLanguage;

public class BaselineExampleTest {
	@Test
	public void testBaselineAnnotationEnglish() throws UIMAException {
		String text = "Peter Piper picked a peck of pickled peppers, "
				+ "A peck of pickled peppers Peter Piper picked. "
				+ "If Peter Piper picked a peck of pickled peppers, "
				+ "Where's the peck of pickled peppers Peter Piper picked?";

		// We don't have a pipeline here,
		// thus we create an empty document by hand,
		// the following utility-method call helps us
		JCas jcas = JCasFactory.createJCas();
		// We set the text to our empty document
		jcas.setDocumentText(text);
		jcas.setDocumentLanguage("en");

		// We just instantiate our annotator by hand and
		// call process() by-hand (in a pipeline, the framework does that for
		// us)

		// Do you remember, our baseline need tokens - we have to do the
		// segmentation, too
		AnalysisEngineDescription segmenter = createEngineDescription(BreakIteratorSegmenter.class);
		AnalysisEngine segEngine = createEngine(segmenter);
		segEngine.process(jcas);

		// Now we can let run our language detection
		AnalysisEngineDescription languageDetector = createEngineDescription(BaselineExample.class);
		AnalysisEngine langDetectEngine = createEngine(languageDetector);
		langDetectEngine.process(jcas);

		// Get the detected language - we know that there is only one annotation
		// of the type
		// 'DetectedLanguage', thus we can use selectSingle()
		// this will throw an exception if there is more than just one
		// annotation of this type!
		DetectedLanguage detectedLang = JCasUtil.selectSingle(jcas,
				DetectedLanguage.class);

		// The essential part of a test - the test itself - are expected and
		// actual result the same?
		assertEquals("EN", detectedLang.getLanguage());
	}

}
