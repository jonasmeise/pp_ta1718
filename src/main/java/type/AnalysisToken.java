package type;

import de.tudarmstadt.ukp.dkpro.core.api.segmentation.type.Token;

public class AnalysisToken extends Token {
	private int rating;

	public int getRating() {
		return rating;
	}

	public void setRating(int rating) {
		this.rating = rating;
	}
	
}
