package project.edge.service.facility;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import garage.origin.dao.Dao;
import garage.origin.service.GenericServiceImpl;
import project.edge.dao.facility.SegmentSystemNameDao;
import project.edge.domain.entity.SegmentSystemName;

@Service
public class SegmentSystemNameServiceImpl extends GenericServiceImpl<SegmentSystemName, String> implements SegmentSystemNameService {

	@Resource
	private SegmentSystemNameDao dao;
	
	@Override
	public Dao<SegmentSystemName, String> getDefaultDao() {
		return this.dao;
	}
	
	@Override
	@Transactional
	public void update(List<SegmentSystemName> nameList) {
		
		List<SegmentSystemName> systemNameList = dao.list(null, null);
		for (SegmentSystemName systemName: systemNameList) {
			dao.delete(systemName);
		}
		
		for (SegmentSystemName sName: nameList) {
			SegmentSystemName systemName = new SegmentSystemName();
			systemName.setId(sName.getId());
			systemName.setName(sName.getName());
			systemName.setHidden(sName.getHidden());
			dao.create(systemName);
		}
	}
}
