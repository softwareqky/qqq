package project.edge.service.schedule;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import garage.origin.dao.Dao;
import garage.origin.service.GenericServiceImpl;
import project.edge.dao.schedule.PlanTaskFavoriteDao;
import project.edge.domain.entity.PlanTaskFavorite;

@Service
public class PlanTaskFavoriteServiceImpl extends GenericServiceImpl<PlanTaskFavorite, String>
		implements PlanTaskFavoriteService {

	@Resource
	private PlanTaskFavoriteDao planTaskFavoriteDao;

	@Override
	public Dao<PlanTaskFavorite, String> getDefaultDao() {
		return this.planTaskFavoriteDao;
	}

}