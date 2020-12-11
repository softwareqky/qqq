package project.edge.service.process;

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
import project.edge.dao.process.ProjectPerformanceAwardAttachmentDao;
import project.edge.dao.process.ProjectPerformanceAwardDao;
import project.edge.domain.entity.Archive;
import project.edge.domain.entity.ProjectPerformanceAward;
import project.edge.domain.entity.ProjectPerformanceAwardAttachment;

/**
 * @author angel_000
 *         [t_project_performance_award]对应的 Service。
 */
@Service
public class ProjectPerformanceAwardServiceImpl
        extends GenericServiceImpl<ProjectPerformanceAward, String>
        implements ProjectPerformanceAwardService {

    @Resource
    private ArchiveDao archiveDao;
    
    @Resource
    private ProjectPerformanceAwardDao projectPerformanceAwardDao;
    
    @Resource
    private ProjectPerformanceAwardAttachmentDao projectPerformanceAwardAttachmentDao;


    @Override
    public Dao<ProjectPerformanceAward, String> getDefaultDao() {
        return this.projectPerformanceAwardDao;
    }

    @Override
    public OrderByDto getDefaultOrder() {
        return new OrderByDto("cDatetime", false);
    }

    @Transactional
    public void setData(ProjectPerformanceAward entity) {
    	if (entity == null || StringUtils.isEmpty(entity.getId())) {
            return;
        }
    	super.update(entity);
    }
    
    /**
     * 增加处理附件文件。
     */
    @Override
    @Transactional
    public void create(ProjectPerformanceAward entity) {

        if (entity == null || StringUtils.isEmpty(entity.getId())) {
            return;
        }

        // 如果项目文件夹对应的Archive不存在，则创建
        if (this.archiveDao.find(FolderPath.AWARD) == null) {
            this.archiveDao.create(this.getProjectRootDirArchive());
        }

        // 如果Project entity对应的文件夹Archive不存在，则创建
        if (this.archiveDao.find(entity.getId()) == null) {
            this.archiveDao.create(this.getDirArchive(entity));
        }
        this.projectPerformanceAwardDao.create(entity);
        for (ProjectPerformanceAwardAttachment a : entity.getProjectPerformanceAwardAttachments()) {
            this.archiveDao.create(a.getArchive());
            this.projectPerformanceAwardAttachmentDao.create(a);
        }
    }

    /**
     * 增加处理附件文件。
     */
    @Override
    @Transactional
    public void update(ProjectPerformanceAward entity) {
        if (entity == null || StringUtils.isEmpty(entity.getId())) {
            return;
        }

        // 如果项目文件夹对应的Archive不存在，则创建
        if (this.archiveDao.find(FolderPath.AWARD) == null) {
            this.archiveDao.create(this.getProjectRootDirArchive());
        }

        // 如果Project entity对应的文件夹Archive不存在，则创建
        if (this.archiveDao.find(entity.getId()) == null) {
            this.archiveDao.create(this.getDirArchive(entity));
        }

        // 创建新增的文件
        for (ProjectPerformanceAwardAttachment attachment : entity.getProjectPerformanceAwardAttachments()) {

            this.archiveDao.create(attachment.getArchive());
            this.projectPerformanceAwardAttachmentDao.create(attachment);
        }

        super.update(entity);


        for (ProjectPerformanceAwardAttachment attachmentToDelete : entity.getAttachmentsToDelete()) {
            this.archiveDao.delete(attachmentToDelete.getArchive());
        }
    }

    /**
     * 创建实体对应的文件夹Archive。
     * 
     * @return
     */
    private Archive getDirArchive(ProjectPerformanceAward entity) {

        // 实体对应的文件夹Archive
        Archive dir = new Archive();
        dir.setId(entity.getId());
        dir.setArchiveName(entity.getProject().getProjectName() + "_" + entity.getPerson().getPerson().getPersonName());
        dir.setIsDir(OnOffEnum.ON.value());
        dir.setRelativePath(File.separator + FolderPath.AWARD + File.separator + dir.getId());
        dir.setFileSize(null);
        dir.setPid(FolderPath.AWARD);

        /**
         * 第零层：根
         * 第一层：FolderPath.EXPERT(DIR)
         * 第二层：ProjectGroup.id(DIR)
         * 第三层：Archive文件
         */
        dir.setLevel(2);
        dir.setPath(Constants.SLASH + Constants.COMMA + FolderPath.AWARD);
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
        dir.setId(FolderPath.AWARD);
        dir.setArchiveName("奖罚信息归档");
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
