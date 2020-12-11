/**
 * 
 */
package project.edge.service.acceptance;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;

import garage.origin.dao.Dao;
import garage.origin.domain.business.OrderByDto;
import garage.origin.service.GenericServiceImpl;
import project.edge.dao.acceptance.ReviewDao;
import project.edge.domain.entity.Review;


/**
 * @author angel_000
 *         [t_review]对应的Service。
 */
@Service
public class ReviewServiceImpl extends GenericServiceImpl<Review, String> implements ReviewService {

    @Resource
    private ReviewDao reviewDao;

    @Override
    public Dao<Review, String> getDefaultDao() {
        // TODO Auto-generated method stub
        return this.reviewDao;
    }

    @Override
    public OrderByDto getDefaultOrder() {
        return new OrderByDto("cDatetime", false);
    }
    
    @Transactional
    public void setData(Review entity) {
    	if (entity == null || StringUtils.isEmpty(entity.getId())) {
            return;
        }
    	super.update(entity);
    }
}
