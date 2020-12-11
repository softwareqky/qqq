package project.edge.service.archive;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import garage.origin.dao.Dao;
import garage.origin.domain.business.OrderByDto;
import garage.origin.service.GenericServiceImpl;
import project.edge.dao.archive.PaperLibraryDao;
import project.edge.domain.entity.PaperLibrary;

@Service
public class PaperLibraryServiceImpl extends GenericServiceImpl<PaperLibrary, String> implements PaperLibraryService {

	@Resource
	private PaperLibraryDao paperLibraryDao;
	
	@Override
	public Dao<PaperLibrary, String> getDefaultDao() {
		// TODO Auto-generated method stub
		return this.paperLibraryDao;
	}
	
	@Override
    public OrderByDto getDefaultOrder() {
        return new OrderByDto("cDatetime", false);
    }
	
	@Override
    @Transactional
    public void create(PaperLibrary entity) {
        this.paperLibraryDao.create(entity);
    }

}
