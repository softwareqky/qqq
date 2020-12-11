package project.edge.service.quality;

import javax.annotation.Resource;

import garage.origin.dao.Dao;
import garage.origin.service.GenericServiceImpl;
import project.edge.dao.quality.QualityObjectiveAttachmentDao;
import project.edge.domain.entity.QualityObjectiveAttachment;

public class QualityObjectiveAttachmentServiceImpl extends GenericServiceImpl<QualityObjectiveAttachment, String> 
	implements QualityObjectiveAttachmentService {
	
	@Resource
	private QualityObjectiveAttachmentDao qualityObjectiveAttachmentDao;

	@Override
	public Dao<QualityObjectiveAttachment, String> getDefaultDao() {
		return this.qualityObjectiveAttachmentDao;
	}

}
