package project;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.apache.uima.UimaContext;
import org.apache.uima.cas.CAS;
import org.apache.uima.cas.CASException;
import org.apache.uima.collection.CollectionException;
import org.apache.uima.fit.component.JCasCollectionReader_ImplBase;
import org.apache.uima.fit.descriptor.ConfigurationParameter;
import org.apache.uima.fit.descriptor.ResourceMetaData;
import org.apache.uima.fit.descriptor.TypeCapability;
import org.apache.uima.jcas.JCas;
import org.apache.uima.resource.ResourceInitializationException;
import org.apache.uima.util.Progress;
import org.apache.uima.util.ProgressImpl;

import de.tudarmstadt.ukp.dkpro.core.api.io.ResourceCollectionReaderBase;
import de.tudarmstadt.ukp.dkpro.core.api.metadata.type.DocumentMetaData;
import de.tudarmstadt.ukp.dkpro.core.api.parameter.ComponentParameters;
import de.tudarmstadt.ukp.dkpro.core.api.resources.CompressionUtils;

import project.JSONreader;
import project.ProductReview;

public class ReviewReader
    extends JCasCollectionReader_ImplBase
{

	//ResourceCollectionReaderBase
	
    public static final String PARAM_INPUT_FILE = "InputFile";
    @ConfigurationParameter(name = PARAM_INPUT_FILE, mandatory = true)
    private String inputFile;    
    
    public static final String PARAM_FILTER_ASIN = "filterASIN";
    @ConfigurationParameter(name = PARAM_FILTER_ASIN, mandatory = false)
    private String filterASIN;    
    
    JSONreader myReader;
    private int currentObject;
    
    /* 
     * initializes the reader
     */
    @Override
    public void initialize(UimaContext context)
        throws ResourceInitializationException
    {        
    	super.initialize(context);
    	
    	currentObject = 0;
    	
        myReader = new JSONreader(inputFile);
        
        try {
			myReader.readObjects(filterASIN);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    public boolean hasNext()
        throws IOException, CollectionException
    {
        return currentObject < myReader.getReviewList().size();
    }
    
    @Override
    public void getNext(JCas jcas)
        throws IOException, CollectionException
    {        
    	jcas.setDocumentLanguage("en");
    	
        ProductReview current = myReader.getReviewList().get(currentObject);
        
        if (current.getReviewerID() == null) {
            throw new IOException("Objekte hat ungültige Daten");
        }
        
        jcas.setDocumentText(current.getReviewText());
        
        currentObject++;
    }

    public Progress[] getProgress()
    {
        return new Progress[] { new ProgressImpl(currentObject, myReader.getReviewList().size(), "data") };
    }
}