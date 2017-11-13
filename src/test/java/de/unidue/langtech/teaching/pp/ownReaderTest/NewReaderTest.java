package de.unidue.langtech.teaching.pp.ownReaderTest;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.uima.collection.CollectionReaderDescription;
import org.apache.uima.fit.factory.CollectionReaderFactory;
import org.apache.uima.fit.pipeline.JCasIterable;
import org.apache.uima.fit.util.JCasUtil;
import org.apache.uima.jcas.JCas;
import org.junit.Test;

import de.tudarmstadt.ukp.dkpro.core.api.segmentation.type.Token;
import de.unidue.langtech.teaching.pp.type.GoldLanguage;

public class NewReaderTest
{

    @Test
    public void testDocumentText()
        throws Exception
    {

        CollectionReaderDescription reader = getReader();

        List<JCas> casObjects = getCasObjects(reader);
        for (JCas jcas : casObjects) {
            assertNotNull(jcas.getDocumentText());
            assertFalse(jcas.getDocumentText().isEmpty());
        }
    }

    @Test
    public void testExistenceOfGoldAnnotation()
        throws Exception
    {
        CollectionReaderDescription reader = getReader();
        List<JCas> casObjects = getCasObjects(reader);

        for (JCas jcas : casObjects) {
            try {
                GoldLanguage goldLanguage = getGoldLanguage(jcas);
                assertFalse(goldLanguage.getLanguage().isEmpty());
            }
            catch (IllegalArgumentException e) {
                fail("The annotation [GoldLanguage] was expected, but was not found");
            }
        }

    }

    @Test
    public void testDocumentTextPerLanguage()
        throws Exception
    {
        CollectionReaderDescription reader = getReader();

        for (JCas jCas : new JCasIterable(reader)) {
            if (languageEquals(jCas, "EN")) {
                assertEquals("Peter went into the office before they arrived there",
                        jCas.getDocumentText());
            }
            else if (languageEquals(jCas, "DE")) {
                assertEquals("Das ist ein schon recht langes deutsches Beispiel",
                        jCas.getDocumentText());
            }
            else if (languageEquals(jCas, "FR")) {
                assertEquals("Si tu finis tes devoirs , je te donnerai des bonbons",
                        jCas.getDocumentText());
            }
        }
    }

    @Test
    public void testTokenCountPerDocument()
        throws Exception
    {

        CollectionReaderDescription reader = getReader();

        for (JCas jCas : new JCasIterable(reader)) {

            List<Token> tokens = getTokens(jCas);
            int countTokens = tokens.size();
            if (languageEquals(jCas, "EN")) {
                assertEquals(9, countTokens);
                // we just test the text of the first and last word
                assertEquals("Peter", getText(tokens, 0));
                assertEquals("there", getText(tokens, 8));
                return;
            }
            else if (languageEquals(jCas, "DE")) {
                assertEquals(8, countTokens);
                assertEquals("Das", getText(tokens, 0));
                assertEquals("Beispiel", getText(tokens, 7));
                return;
            }
            else if (languageEquals(jCas, "FR")) {
                assertEquals(11, countTokens);
                assertEquals("Si", getText(tokens, 0));
                assertEquals("bonbons", getText(tokens, 10));
                return;
            }
            else {
                // It should be either of the three languages
                fail();
            }
        }
        fail();
    }

    @Test
    public void testNothingJustPrintStuffFromTheCas()
        throws Exception
    {
        // this is merely a help for people that prefer sysouts - it tries to print information that
        // are supposed to be contained in the cas
        // note: a test without assert-statement will always pass

        CollectionReaderDescription reader = getReader();
        for (JCas jCas : new JCasIterable(reader)) {
            String documentText = jCas.getDocumentText();
            System.out.println("Document text: [" + documentText + "]");

            List<GoldLanguage> gold = new ArrayList<GoldLanguage>(JCasUtil.select(jCas,
                    GoldLanguage.class));
            System.out.print("Language: [");
            if (gold.size() == 1) {
                System.out.print(gold.get(0).getLanguage());
            }
            System.out.println("]");
            
            System.out.println("Tokens:[");
            List<Token> tokens = new ArrayList<Token>(JCasUtil.select(jCas, Token.class));
            for (int i=0; i < tokens.size(); i++){
                Token token = tokens.get(i);
                System.out.println("\t"+token.getCoveredText());
            }
            System.out.println("]");
            System.out.println();
        }

    }

    private String getText(List<Token> aTokens, int idx)
    {
        return aTokens.get(idx).getCoveredText();
    }

    private boolean languageEquals(JCas aJCas, String expectedLanguage)
    {
        try {
            GoldLanguage goldLanguage = getGoldLanguage(aJCas);
            return goldLanguage.getLanguage().equals(expectedLanguage);
        }
        catch (IllegalArgumentException e) {
            fail("The annotation [GoldLanguage] was expected, but was not found");
        }
        return false;
    }

    private GoldLanguage getGoldLanguage(JCas aJCas)
    {
        return JCasUtil.selectSingle(aJCas, GoldLanguage.class);
    }

    private List<Token> getTokens(JCas aJCas)
    {
        Collection<Token> tokens = JCasUtil.select(aJCas, Token.class);
        return new ArrayList<Token>(tokens);
    }

    private List<JCas> getCasObjects(CollectionReaderDescription aReader)
    {
        List<JCas> casObjects = new ArrayList<JCas>();

        for (JCas jcas : new JCasIterable(aReader)) {
            casObjects.add(jcas);
        }

        return casObjects;
    }

    private CollectionReaderDescription getReader()
        throws Exception
    {
        return CollectionReaderFactory.createReaderDescription(NewReader.class,
                NewReader.PARAM_INPUT_FILE, "src/test/resources/test/inputNewFormat.txt");
    }

}
