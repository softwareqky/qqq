package project.edge.service.hr;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.io.File;
import java.util.Date;

import javax.annotation.Resource;

import garage.origin.common.constant.Constants;
import garage.origin.common.constant.OnOffEnum;
import garage.origin.dao.Dao;
import garage.origin.domain.business.OrderByDto;
import garage.origin.service.GenericServiceImpl;
import project.edge.common.constant.FolderPath;
import project.edge.dao.archive.ArchiveDao;
import project.edge.dao.hr.ExpertAttachmentDao;
import project.edge.dao.hr.ExpertDao;
import project.edge.domain.entity.Archive;
import project.edge.domain.entity.Expert;
import project.edge.domain.entity.ExpertAttachment;

/**
 * @author angel_000
 *         [t_expert]对应的 Service。
 */
@Service
public class ExpertServiceImpl extends GenericServiceImpl<Expert, String> implements ExpertService {

    @Resource
    private ExpertDao expertDao;

    @Resource
    private ArchiveDao archiveDao;

    @Resource
    private ExpertAttachmentDao expertAttachmentDao;

    @Override
    public Dao<Expert, String> getDefaultDao() {
        // TODO Auto-generated method stub
        return this.expertDao;
    }

    @Override
    public OrderByDto getDefaultOrder() {
        return new OrderByDto("expertName", false);
    }

    /**
     * 增加处理附件文件。
     */
    @Override
    @Transactional
    public void create(Expert entity) {

        if (entity == null || StringUtils.isEmpty(entity.getId())) {
            return;
        }

        // 如果项目文件夹对应的Archive不存在，则创建
        if (this.archiveDao.find(FolderPath.EXPERT) == null) {
            this.archiveDao.create(this.getProjectRootDirArchive());
        }

        // 如果Project entity对应的文件夹Archive不存在，则创建
        if (this.archiveDao.find(entity.getId()) == null) {
            this.archiveDao.create(this.getDirArchive(entity));
        }
        this.expertDao.create(entity);
        for (ExpertAttachment a : entity.getExpertAttachments()) {
            this.archiveDao.create(a.getArchive());
            this.expertAttachmentDao.create(a);
        }
    }

    /**
     * 增加处理附件文件。
     */
    @Override
    @Transactional
    public void update(Expert entity) {
        if (entity == null || StringUtils.isEmpty(entity.getId())) {
            return;
        }

        // 如果项目文件夹对应的Archive不存在，则创建
        if (this.archiveDao.find(FolderPath.EXPERT) == null) {
            this.archiveDao.create(this.getProjectRootDirArchive());
        }

        // 如果Project entity对应的文件夹Archive不存在，则创建
        if (this.archiveDao.find(entity.getId()) == null) {
            this.archiveDao.create(this.getDirArchive(entity));
        }

        // 创建新增的文件
        for (ExpertAttachment attachment : entity.getExpertAttachments()) {

            this.archiveDao.create(attachment.getArchive());
            this.expertAttachmentDao.create(attachment);
        }

        super.update(entity);


        for (ExpertAttachment attachmentToDelete : entity.getAttachmentsToDelete()) {
            this.archiveDao.delete(attachmentToDelete.getArchive());
        }
    }

    /**
     * 创建实体对应的文件夹Archive。
     * 
     * @return
     */
    private Archive getDirArchive(Expert entity) {

        // 实体对应的文件夹Archive
        Archive dir = new Archive();
        dir.setId(entity.getId());
        dir.setArchiveName(entity.getExpertName());
        dir.setIsDir(OnOffEnum.ON.value());
        dir.setRelativePath(File.separator + FolderPath.EXPERT + File.separator + dir.getId());
        dir.setFileSize(null);
        dir.setPid(FolderPath.EXPERT);

        /**
         * 第零层：根
         * 第一层：FolderPath.EXPERT(DIR)
         * 第二层：ProjectGroup.id(DIR)
         * 第三层：Archive文件
         */
        dir.setLevel(2);
        dir.setPath(Constants.SLASH + Constants.COMMA + FolderPath.EXPERT);
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
        dir.setId(FolderPath.EXPERT);
        dir.setArchiveName("专家库归档");
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
