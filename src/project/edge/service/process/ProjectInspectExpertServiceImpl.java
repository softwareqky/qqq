package project.edge.service.process;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import garage.origin.dao.Dao;
import garage.origin.domain.business.OrderByDto;
import garage.origin.service.GenericServiceImpl;
import project.edge.dao.process.ProjectInspectExpertDao;
import project.edge.domain.entity.ProjectInspectExpert;

@Service
public class ProjectInspectExpertServiceImpl extends GenericServiceImpl<ProjectInspectExpert, String>
		implements ProjectInspectExpertService {
	
	@Resource
	private ProjectInspectExpertDao projectInspectExpertDao;
	
	@Override
	public Dao<ProjectInspectExpert, String> getDefaultDao() {
	// TODO Auto-generated method stub
		return this.projectInspectExpertDao;
	}
	
	@Override
	public OrderByDto getDefaultOrder() {
		return new OrderByDto("id", false);
	}
}