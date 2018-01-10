package type;

import java.io.IOException;
import java.util.LinkedList;

public interface ReaderClass {
	public void getData() throws IOException;
	public LinkedList<ProductReview> getReviewList();
	public void addReviewData(ProductReview review);
	public void setCutBorder(int cutBorder);
}
