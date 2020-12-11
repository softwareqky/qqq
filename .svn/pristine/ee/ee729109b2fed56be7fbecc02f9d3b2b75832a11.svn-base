package project.edge.service.archive;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import garage.origin.dao.Dao;
import garage.origin.domain.business.OrderByDto;
import garage.origin.service.GenericServiceImpl;
import project.edge.common.constant.PaperLibraryStatusEnum;
import project.edge.dao.archive.PaperLibraryDao;
import project.edge.dao.archive.PaperLibraryLendHistoryDao;
import project.edge.domain.entity.PaperLibrary;
import project.edge.domain.entity.PaperLibraryLendHistory;

@Service
public class PaperLibraryLendHistoryServiceImpl extends GenericServiceImpl<PaperLibraryLendHistory, String>
		implements PaperLibraryLendHistoryService {

	@Resource
	private PaperLibraryLendHistoryDao paperLibraryLendHistoryDao;
	
	@Resource
	private PaperLibraryDao paperLibraryDao;
	
	@Override
	public Dao<PaperLibraryLendHistory, String> getDefaultDao() {
		// TODO Auto-generated method stub
		return this.paperLibraryLendHistoryDao;
	}
	
	@Override
    public OrderByDto getDefaultOrder() {
        return new OrderByDto("cDatetime", false);
    }
	
	@Override
    @Transactional
    public void create(PaperLibraryLendHistory entity) {
		PaperLibrary paperLibrary = entity.getPaperLibrary();
		if (paperLibrary != null) {
			PaperLibrary newPaperLibrary = paperLibraryDao.find(paperLibrary.getId());
			if (newPaperLibrary != null) {
				newPaperLibrary.setStatus(paperLibrary.getStatus());
				paperLibraryDao.update(newPaperLibrary);
			}
			
		}
		
        this.paperLibraryLendHistoryDao.create(entity);
    }
	
	@Override
    @Transactional
    public void update(PaperLibraryLendHistory entity) {
		PaperLibrary paperLibrary = entity.getPaperLibrary();
		if (paperLibrary != null) {
			PaperLibrary newPaperLibrary = paperLibraryDao.find(paperLibrary.getId());
			if (newPaperLibrary != null) {
				newPaperLibrary.setStatus(paperLibrary.getStatus());
				paperLibraryDao.update(newPaperLibrary);
			}
			
		}
		super.update(entity);
	}
	
	@Transactional
    public void setData(PaperLibraryLendHistory entity) {
    	if (entity == null || StringUtils.isEmpty(entity.getId())) {
            return;
        }
    	super.update(entity);
    }
}
