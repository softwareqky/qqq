package project.edge.service.quality;

import javax.annotation.Resource;

import garage.origin.dao.Dao;
import garage.origin.service.GenericServiceImpl;
import project.edge.dao.quality.QualityAccidentAttachmentDao;
import project.edge.domain.entity.QualityAccidentAttachment;

public class QualityAccidentAttachmentServiceImpl 
	extends GenericServiceImpl<QualityAccidentAttachment, String>
	implements QualityAccidentAttachmentService {

	@Resource
	private QualityAccidentAttachmentDao qualityAccidentAttachmentDao;
	
	@Override
	public Dao<QualityAccidentAttachment, String> getDefaultDao() {
		return this.qualityAccidentAttachmentDao;
	}

}
