package project.edge.service.facility;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import garage.origin.dao.Dao;
import garage.origin.service.GenericServiceImpl;
import project.edge.dao.facility.SiteAttachmentDao;
import project.edge.domain.entity.SiteAttachment;

@Service
public class SiteAttachmentServiceImpl extends GenericServiceImpl<SiteAttachment, String> {

	@Resource
	private SiteAttachmentDao siteAttachmentDao;
	
	@Override
	public Dao<SiteAttachment, String> getDefaultDao() {
		return this.siteAttachmentDao;
	}

	

}
