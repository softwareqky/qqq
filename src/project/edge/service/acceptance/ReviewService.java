/**
 * 
 */
package project.edge.service.acceptance;

import garage.origin.service.Service;
import project.edge.domain.entity.Review;


/**
 * @author angel_000
 *         [t_review]对应的Service。
 */
public interface ReviewService extends Service<Review, String> {
	public void setData(Review entity);
}
