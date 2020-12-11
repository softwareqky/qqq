package project.edge.service.quality;

import java.io.File;
import java.util.Date;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import garage.origin.common.constant.Constants;
import garage.origin.common.constant.OnOffEnum;
import garage.origin.dao.Dao;
import garage.origin.domain.business.OrderByDto;
import garage.origin.service.GenericServiceImpl;
import project.edge.common.constant.FolderPath;
import project.edge.dao.archive.ArchiveDao;
import project.edge.dao.quality.QualitySpecificationAttachmentDao;
import project.edge.dao.quality.QualitySpecificationDao;
import project.edge.domain.entity.Archive;
import project.edge.domain.entity.QualitySpecification;
import project.edge.domain.entity.QualitySpecificationAttachment;

/**
 * @author angel_000
 *         [t_quality_specification]对应的 Service。
 */
@Service
public class QualitySpecificationServiceImpl extends
        GenericServiceImpl<QualitySpecification, String> implements QualitySpecificationService {

    @Resource
    private QualitySpecificationDao qualitySpecificationDao;
    
    @Resource
    private QualitySpecificationAttachmentDao attachmentDao;
    
    @Resource
    private ArchiveDao archiveDao;

    @Override
    public Dao<QualitySpecification, String> getDefaultDao() {
        return this.qualitySpecificationDao;
    }

    @Override
    public OrderByDto getDefaultOrder() {
        return new OrderByDto("cDatetime", false);
    }
    
    @Override
    @Transactional
    public void create(QualitySpecification entity) {
    	
    	if (entity == null || StringUtils.isEmpty(entity.getId())) {
    		return;
    	}
    	
        if (this.archiveDao.find(FolderPath.QUALITY_SPECIFICATION) == null) {
            this.archiveDao.create(this.getArchive(FolderPath.QUALITY_SPECIFICATION));
        }
        
        this.qualitySpecificationDao.create(entity);
        for (QualitySpecificationAttachment a : entity.getQualitySpecificationAttachments()) {
        	this.archiveDao.create(a.getArchive());
        	this.attachmentDao.create(a);
        }
    }
    
    @Override
    @Transactional
    public void update(QualitySpecification entity) {
    	if (entity == null || StringUtils.isEmpty(entity.getId())) {
    		return;
    	}
    	
    	if (this.archiveDao.find(FolderPath.QUALITY_SPECIFICATION) == null) {
            this.archiveDao.create(this.getArchive(FolderPath.QUALITY_SPECIFICATION));
        }
    	
    	for (QualitySpecificationAttachment a : entity.getQualitySpecificationAttachments()) {
        	this.archiveDao.create(a.getArchive());
        	this.attachmentDao.create(a);
        }
    	super.update(entity);
    	
    	for (QualitySpecificationAttachment a : entity.getArchivesToDelete()) {
        	this.archiveDao.delete(a.getArchive());
        }
    }
    
    /**
     * 创建对应名称的Archive。
     * 
     * @return
     */
    private Archive getArchive(String id) {

        // 实体对应的文件夹Archive
        Archive dir = new Archive();
        dir.setId(id);
        dir.setArchiveName("质量规范");
        dir.setIsDir(OnOffEnum.ON.value());
        dir.setRelativePath(File.separator + dir.getId());
        dir.setFileSize(null);
        dir.setPid(Constants.SLASH);

        /**
         * 第零层：根
         * 第一层：FolderPath.PROJECT(DIR)
         * 第二层：Project.id(DIR)
         * 第三层：Archive文件
         */
        dir.setLevel(1);
        dir.setPath(Constants.SLASH);
        dir.setIsDeleted(OnOffEnum.OFF.value());
        dir.setCreator(Constants.ADMIN_USER_ID);

        Date now = new Date();
        dir.setcDatetime(now);
        dir.setmDatetime(now);

        return dir;
    }
}
