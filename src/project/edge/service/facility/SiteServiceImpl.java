/**
 * 
 */
package project.edge.service.facility;

import java.io.File;
import java.util.Date;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import garage.origin.common.constant.Constants;
import garage.origin.common.constant.OnOffEnum;
import garage.origin.dao.Dao;
import garage.origin.domain.business.OrderByDto;
import garage.origin.service.GenericServiceImpl;
import project.edge.common.constant.FolderPath;
import project.edge.dao.archive.ArchiveDao;
import project.edge.dao.facility.SiteAttachmentDao;
import project.edge.dao.facility.SiteDao;
import project.edge.domain.entity.Archive;
import project.edge.domain.entity.Site;
import project.edge.domain.entity.SiteAttachment;


/**
 * @author angel_000
 *         [t_site]对应的 Service。
 */
@Service
public class SiteServiceImpl extends GenericServiceImpl<Site, String> implements SiteService {

    @Resource
    private SiteDao siteDao;
    
    @Resource
    private SiteAttachmentDao attachmentDao;
    
    @Resource
    private ArchiveDao archiveDao;

    @Override
    public Dao<Site, String> getDefaultDao() {
        // TODO Auto-generated method stub
        return this.siteDao;
    }

    @Override
    public OrderByDto getDefaultOrder() {
        return new OrderByDto("cDatetime", false);
    }

    @Override
    @Transactional
    public void create(Site entity) {
    	
    	if (entity == null) {
    		return;
    	}
    	
    	if (this.archiveDao.find(FolderPath.SITE) == null) {
    		this.archiveDao.create(this.getArchive(FolderPath.SITE));
    	}
    	
    	this.siteDao.create(entity);
    	for (SiteAttachment a: entity.getAttachments()) {
    		this.archiveDao.create(a.getArchive());
    		this.attachmentDao.create(a);
    	}
    }
    
    @Override
    @Transactional
    public void update(Site entity) {
    	
    	if (entity == null || StringUtils.isEmpty(entity.getId())) {
    		return;
    	}
    	
    	if (this.archiveDao.find(FolderPath.SITE) == null) {
    		this.archiveDao.create(this.getArchive(FolderPath.SITE));
    	}
    	
    	for (SiteAttachment a: entity.getAttachments()) {
    		this.archiveDao.create(a.getArchive());
    		this.attachmentDao.create(a);
    	}
    	super.update(entity);
    	
    	for (SiteAttachment a: entity.getAttachmentsToDelete()) {
    		this.archiveDao.delete(a.getArchive());
    	}
    }
    
    private Archive getArchive(String id) {
    	
    	// 实体对应的文件夹Archive
        Archive dir = new Archive();
        dir.setId(id);
        dir.setArchiveName("站点");
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
