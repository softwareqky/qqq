package project.edge.service.quality;

import javax.annotation.Resource;

import garage.origin.dao.Dao;
import garage.origin.service.GenericServiceImpl;
import project.edge.dao.quality.QualitySpecificationAttachmentDao;
import project.edge.domain.entity.QualitySpecificationAttachment;

public class QualitySpecificationAttachmentServiceImpl extends GenericServiceImpl<QualitySpecificationAttachment, String> 
	implements QualitySpecificationAttachmentService {
	
	@Resource
	private QualitySpecificationAttachmentDao qualitySpecificationAttachmentDao;

	@Override
	public Dao<QualitySpecificationAttachment, String> getDefaultDao() {
		return this.qualitySpecificationAttachmentDao;
	}

}
