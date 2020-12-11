package project.edge.service.process;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;

import garage.origin.dao.Dao;
import garage.origin.domain.business.OrderByDto;
import garage.origin.service.GenericServiceImpl;
import project.edge.dao.process.ProjectCheckExpertDao;
import project.edge.domain.entity.ProjectCheckExpert;

@Service
public class ProjectCheckExpertServiceImpl extends GenericServiceImpl<ProjectCheckExpert, String>
		implements ProjectCheckExpertService {
	
	@Resource
	private ProjectCheckExpertDao projectCheckExpertDao;
	
	@Override
	public Dao<ProjectCheckExpert, String> getDefaultDao() {
	// TODO Auto-generated method stub
		return this.projectCheckExpertDao;
	}
	
	@Override
	public OrderByDto getDefaultOrder() {
		return new OrderByDto("id", false);
	}
}