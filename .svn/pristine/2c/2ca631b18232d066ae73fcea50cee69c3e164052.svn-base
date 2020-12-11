package project.edge.service.acceptance;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import garage.origin.dao.Dao;
import garage.origin.domain.business.OrderByDto;
import garage.origin.service.GenericServiceImpl;
import project.edge.dao.acceptance.ReviewExpertDao;
import project.edge.domain.entity.ReviewExpert;

@Service
public class ReviewExpertServiceImpl extends GenericServiceImpl<ReviewExpert, String>
		implements ReviewExpertService {
	
	@Resource
	private ReviewExpertDao reviewExpertDao;
	
	@Override
	public Dao<ReviewExpert, String> getDefaultDao() {
	// TODO Auto-generated method stub
		return this.reviewExpertDao;
	}
	
	@Override
	public OrderByDto getDefaultOrder() {
		return new OrderByDto("id", false);
	}
}