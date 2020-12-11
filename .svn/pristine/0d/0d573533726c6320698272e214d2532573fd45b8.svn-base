package project.edge.service.bidding;

import java.io.File;
import java.util.Date;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import garage.origin.common.constant.Constants;
import garage.origin.common.constant.OnOffEnum;
import garage.origin.dao.Dao;
import garage.origin.domain.business.OrderByDto;
import garage.origin.service.GenericServiceImpl;
import project.edge.common.constant.FolderPath;
import project.edge.dao.archive.ArchiveDao;
import project.edge.dao.bidding.TendereeAttachmentDao;
import project.edge.dao.bidding.TendereeDao;
import project.edge.domain.entity.Archive;
import project.edge.domain.entity.Tenderee;
import project.edge.domain.entity.TendereeAttachment;

/**
 * [t_tenderee]对应的 Service。
 */
@Service
public class TendereeServiceImpl extends GenericServiceImpl<Tenderee, String>
        implements TendereeService {

    @Resource
    private TendereeDao tendereeDao;
    
    @Resource
    private ArchiveDao archiveDao;
    
    @Resource
    private TendereeAttachmentDao tendereeAttachmentDao;

    @Override
    public Dao<Tenderee, String> getDefaultDao() {
        return this.tendereeDao;
    }

    @Override
    public OrderByDto getDefaultOrder() {
        return new OrderByDto("cDatetime");
    }
    
    /**
     * 增加处理附件文件。
     */
    @Override
    @Transactional
    public void create(Tenderee entity) {

        if (entity == null || StringUtils.isEmpty(entity.getId())) {
            return;
        }
        
        // 如果项目文件夹对应的Archive不存在，则创建
        if (this.archiveDao.find(FolderPath.TENDERRE) == null) {
            this.archiveDao.create(this.getProjectRootDirArchive());
        }

        // 如果实体对应的文件夹Archive不存在，则创建
        if (this.archiveDao.find(entity.getId()) == null) {
            this.archiveDao.create(this.getDirArchive(entity));
        }
        
        this.tendereeDao.create(entity);
        for (TendereeAttachment a : entity.getArchives()) {
            this.archiveDao.create(a.getArchive());
            this.tendereeAttachmentDao.create(a);
        }
    }

    /**
     * 增加处理附件文件。
     */
    @Override
    @Transactional
    public void update(Tenderee entity) {
        if (entity == null || StringUtils.isEmpty(entity.getId())) {
            return;
        }
        
        // 如果项目文件夹对应的Archive不存在，则创建
        if (this.archiveDao.find(FolderPath.TENDERRE) == null) {
            this.archiveDao.create(this.getProjectRootDirArchive());
        }

        // 如果实体对应的文件夹Archive不存在，则创建
        if (this.archiveDao.find(entity.getId()) == null) {
            this.archiveDao.create(this.getDirArchive(entity));
        }

        // 创建新增的文件，同时将修改后保留的文件id存入map
        for (TendereeAttachment a : entity.getArchives()) {
        	this.archiveDao.create(a.getArchive());
            this.tendereeAttachmentDao.create(a);
        }

        super.update(entity);

        for (TendereeAttachment attachmentToDelete : entity.getArchivesToDelete()) {
            this.archiveDao.delete(attachmentToDelete.getArchive());
        }
    }
    
    /**
     * 创建实体对应的文件夹Archive。
     * 
     * @return
     */
    private Archive getDirArchive(Tenderee entity) {

        // 实体对应的文件夹Archive
        Archive dir = new Archive();
        dir.setId(entity.getId());
        dir.setArchiveName(entity.getCompanyName());
        dir.setIsDir(OnOffEnum.ON.value());
        dir.setRelativePath(
                File.separator + FolderPath.TENDERRE + File.separator + dir.getId());
        dir.setFileSize(null);
        dir.setPid(FolderPath.TENDERRE);

        /**
         * 第一层：FolderPath.EXPERT(DIR)
         * 第二层：tenderee.id(DIR)
         * 第三层：Archive文件
         */
        dir.setLevel(2);
        dir.setPath(Constants.SLASH + Constants.COMMA + FolderPath.TENDERRE);
        dir.setIsDeleted(OnOffEnum.OFF.value());
        dir.setCreator(entity.getCreator());
        dir.setcDatetime(entity.getcDatetime());
        dir.setmDatetime(entity.getmDatetime());

        return dir;
    }
    
    /**
     * 创建项目的根文件夹Archive。
     * 
     * @return
     */
    private Archive getProjectRootDirArchive() {

        // 实体对应的文件夹Archive
        Archive dir = new Archive();
        dir.setId(FolderPath.TENDERRE);
        dir.setArchiveName("投标单位附件");
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
