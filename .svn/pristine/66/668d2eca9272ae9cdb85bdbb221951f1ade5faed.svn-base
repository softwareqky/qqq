package project.edge.service.notice;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import garage.origin.common.constant.Constants;
import garage.origin.common.constant.OnOffEnum;
import garage.origin.dao.Dao;
import garage.origin.domain.business.OrderByDto;
import garage.origin.domain.filter.CommonFilter;
import garage.origin.service.GenericServiceImpl;
import project.edge.common.constant.FolderPath;
import project.edge.dao.archive.ArchiveDao;
import project.edge.dao.notice.NoticeAnnouncementDao;
import project.edge.dao.notice.NoticeAttachmentDao;
import project.edge.domain.entity.Archive;
import project.edge.domain.entity.NoticeAnnouncement;
import project.edge.domain.entity.NoticeAttachment;

/**
 * @author angel_000
 *         [t_notice_announcement]对应的 Service。
 */
@Service
public class NoticeAnnouncementServiceImpl extends GenericServiceImpl<NoticeAnnouncement, String>
        implements NoticeAnnouncementService {

    @Resource
    private NoticeAnnouncementDao noticeAnnouncementDao;
    
    @Resource
    private ArchiveDao archiveDao;
    
    @Resource
    private NoticeAttachmentDao noticeAttachmentDao;

    @Override
    public Dao<NoticeAnnouncement, String> getDefaultDao() {
        return this.noticeAnnouncementDao;
    }

    @Override
    public OrderByDto getDefaultOrder() {
        return new OrderByDto("cDatetime", true);
    }
    
    /**
     * 增加处理附件文件。
     */
    @Override
    @Transactional
    public void create(NoticeAnnouncement entity) {

        if (entity == null || StringUtils.isEmpty(entity.getId())) {
            return;
        }

        // 如果项目文件夹对应的Archive不存在，则创建
        if (this.archiveDao.find(FolderPath.NOTICE) == null) {
            this.archiveDao.create(this.getProjectRootDirArchive());
        }

        // 如果Project entity对应的文件夹Archive不存在，则创建
        if (this.archiveDao.find(entity.getId()) == null) {
            this.archiveDao.create(this.getDirArchive(entity));
        }
        
    	// 设置默认编号
        if (StringUtils.isEmpty(entity.getNoticeNo())) {
        	//SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            //String noticeNo = "No." + sdf.format(new Date()) + "000" + RandomStringUtils.randomNumeric(4);
        	int count = (int)noticeAnnouncementDao.count(null) + 1;
        	String noticeNo = "";
    		noticeNo = String.format("N%09d", count);
            entity.setNoticeNo(noticeNo);
        }
        
        this.noticeAnnouncementDao.create(entity);
        for (NoticeAttachment a : entity.getNoticeAttachments()) {
            this.archiveDao.create(a.getArchive());
            this.noticeAttachmentDao.create(a);
        }
        

    }

    /**
     * 修改，并增加处理附件文件。新建时没有附件，仅修改时有附件。
     */
    @Override
    @Transactional
    public void update(NoticeAnnouncement entity) {
        if (entity == null || StringUtils.isEmpty(entity.getId())) {
            return;
        }

        // 如果项目文件夹对应的Archive不存在，则创建
        if (this.archiveDao.find(FolderPath.NOTICE) == null) {
            this.archiveDao.create(this.getProjectRootDirArchive());
        }

        // 如果Project entity对应的文件夹Archive不存在，则创建
        if (this.archiveDao.find(entity.getId()) == null) {
            this.archiveDao.create(this.getDirArchive(entity));
        }

        // 创建新增的文件
        for (NoticeAttachment attachment : entity.getNoticeAttachments()) {

            this.archiveDao.create(attachment.getArchive());
            this.noticeAttachmentDao.create(attachment);
        }

        super.update(entity);

        for (NoticeAttachment attachmentToDelete : entity.getAttachmentsToDelete()) {
            this.archiveDao.delete(attachmentToDelete.getArchive());
        }
    }
    
    /**
     * 创建实体对应的文件夹Archive。
     * 
     * @return
     */
    private Archive getDirArchive(NoticeAnnouncement entity) {

        // 实体对应的文件夹Archive
        Archive dir = new Archive();
        dir.setId(entity.getId());
        dir.setArchiveName(entity.getTittle());
        dir.setIsDir(OnOffEnum.ON.value());
        dir.setRelativePath(
                File.separator + FolderPath.NOTICE + File.separator + dir.getId());
        dir.setFileSize(null);
        dir.setPid(FolderPath.NOTICE);

        /**
         * 第零层：根
         * 第一层：FolderPath.PROJECT(DIR)
         * 第二层：Project.id(DIR)
         * 第三层：Archive文件
         */
        dir.setLevel(2);
        dir.setPath(Constants.SLASH + Constants.COMMA + FolderPath.NOTICE);
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
        dir.setId(FolderPath.NOTICE);
        dir.setArchiveName("通知公告归档");
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
