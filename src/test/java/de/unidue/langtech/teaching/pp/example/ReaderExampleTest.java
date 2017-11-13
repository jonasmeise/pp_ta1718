package de.unidue.langtech.teaching.pp.example;

import static org.junit.Assert.assertEquals;

import org.apache.uima.collection.CollectionReaderDescription;
import org.apache.uima.fit.factory.CollectionReaderFactory;
import org.apache.uima.fit.pipeline.JCasIterable;
import org.apache.uima.fit.util.JCasUtil;
import org.apache.uima.jcas.JCas;
import org.junit.Test;

import de.unidue.langtech.teaching.pp.type.GoldLanguage;

public class ReaderExampleTest {

	@Test
	public void testReaderExample() throws Exception {
		CollectionReaderDescription reader = CollectionReaderFactory
				.createReaderDescription(ReaderExample.class,
						ReaderExample.PARAM_INPUT_FILE,
						"src/test/resources/test/input.txt");

		int i = 0;
		for (JCas jcas : new JCasIterable(reader)) {
			// we know that the first line should be English, the second French
			// and the third German
			GoldLanguage languageAnno = JCasUtil.selectSingle(jcas,
					GoldLanguage.class);
			if (i == 0) {
				assertEquals("EN", languageAnno.getLanguage());
			} else if (i == 1) {
				assertEquals("FR", languageAnno.getLanguage());
			} else if (i == 2) {
				assertEquals("DE", languageAnno.getLanguage());
			}

			i++;
		}

		// there are 3 lines in the file, so we should get 10 documents here
		assertEquals(3, i);
	}
}