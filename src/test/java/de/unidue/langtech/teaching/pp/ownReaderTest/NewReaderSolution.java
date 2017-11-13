package de.unidue.langtech.teaching.pp.ownReaderTest;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.uima.UimaContext;
import org.apache.uima.collection.CollectionException;
import org.apache.uima.fit.component.JCasCollectionReader_ImplBase;
import org.apache.uima.fit.descriptor.ConfigurationParameter;
import org.apache.uima.jcas.JCas;
import org.apache.uima.resource.ResourceInitializationException;
import org.apache.uima.util.Progress;

import de.tudarmstadt.ukp.dkpro.core.api.segmentation.type.Token;
import de.unidue.langtech.teaching.pp.type.GoldLanguage;

public class NewReaderSolution
    extends JCasCollectionReader_ImplBase
{
    public static final String PARAM_INPUT_FILE = "InputFile";
    @ConfigurationParameter(name = PARAM_INPUT_FILE, mandatory = true)
    private File inputFile;

    private List<String> lines;
    private int currentLine;

    @Override
    public void initialize(UimaContext context)
        throws ResourceInitializationException
    {
        super.initialize(context);

        try {
            lines = FileUtils.readLines(inputFile);
            currentLine = 0;
        }
        catch (IOException e) {
            throw new ResourceInitializationException(e);
        }
    }

    public boolean hasNext()
        throws IOException, CollectionException
    {
        return currentLine < lines.size();
    }

    public Progress[] getProgress()
    {
        return null;
    }

    public void getNext(JCas aJCas)
        throws IOException, CollectionException
    {
        List<String> entry = new ArrayList<String>();

        String nextLine = null;
        for (; currentLine < lines.size(); currentLine++) {

            // get the current line
            nextLine = lines.get(currentLine);

            // empty line = end of entry
            if (nextLine.isEmpty()) {
                currentLine++;
                break;
            }

            entry.add(nextLine);
        }

        // 'entry' contains now one language code with all tokens of a sentence
        // position 0: language code
        // position 1 -> N: tokens

        // add gold standard value as annotation
        // the first line is the language code
        GoldLanguage goldLanguage = new GoldLanguage(aJCas);
        goldLanguage.setLanguage(entry.get(0));
        goldLanguage.addToIndexes();

        String documentText = "";
        for (int i = 1; i < entry.size(); i++) {
            String word = entry.get(i);
            documentText += word;

            // add the token annotated as own type
            int start = documentText.length() - word.length();
            int end = documentText.length();
            Token t = new Token(aJCas, start, end);
            t.addToIndexes();

            // append space as separator for next token
            documentText += " ";
        }

        aJCas.setDocumentText(documentText.trim());

    }

}
