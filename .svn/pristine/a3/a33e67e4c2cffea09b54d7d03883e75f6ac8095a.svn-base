package project.edge.service.facility;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import garage.origin.dao.Dao;
import garage.origin.domain.business.OrderByDto;
import garage.origin.service.GenericServiceImpl;
import project.edge.dao.facility.FacilityHistoryDao;
import project.edge.domain.entity.FacilityHistory;

@Service
public class FacilityHistoryServiceImpl extends GenericServiceImpl<FacilityHistory, String> implements FacilityHistoryService {

	@Resource
	private FacilityHistoryDao dao;
	
	@Override
	public OrderByDto getDefaultOrder() {
		return new OrderByDto("createAt", false);
	}
	
	@Override
	public Dao<FacilityHistory, String> getDefaultDao() {
		return this.dao;
	}
	
	@Override
	@Transactional
	public void create(FacilityHistory entity) {
		
		if (entity == null) {
			return;
		}
		
		this.dao.create(entity);
	}
	
}
