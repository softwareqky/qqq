package project.edge.service.project;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import garage.origin.dao.Dao;
import garage.origin.domain.business.OrderByDto;
import garage.origin.service.GenericServiceImpl;
import project.edge.dao.project.ProjectPersonDao;
import project.edge.domain.entity.ProjectPerson;

/**
 * [t_project_person]对应的 Service。
 */
@Service
@Transactional
public class ProjectPersonServiceImpl extends GenericServiceImpl<ProjectPerson, String>
        implements ProjectPersonService {

    @Resource
    private ProjectPersonDao projectPersonDao;

    @Override
    public Dao<ProjectPerson, String> getDefaultDao() {
        return this.projectPersonDao;
    }

    @Override
    public OrderByDto getDefaultOrder() {
        return new OrderByDto("id");
    }
    
    
    public ProjectPerson findByUserId(String userId) {
    	ProjectPerson p =  projectPersonDao.findByUserId(userId);
    	if (p != null) {
    		return this.find(p.getId());
    	}
    	return null;
    }

	@Override
	public void setData(ProjectPerson entity) {
		if (entity == null || StringUtils.isEmpty(entity.getId())) {
            return;
        }
    	super.update(entity);
	}
}
