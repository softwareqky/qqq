/**
 * 
 */
package project.edge.service.process;

import java.awt.Color;
import java.awt.Font;
import java.awt.image.BufferedImage;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.annotation.Resource;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import garage.origin.common.constant.Constants;
import garage.origin.common.constant.JsonStatus;
import garage.origin.common.constant.OnOffEnum;
import garage.origin.dao.Dao;
import garage.origin.domain.business.OrderByDto;
import garage.origin.domain.filter.CommonFilter;
import garage.origin.domain.view.JsonResultBean;
import garage.origin.domain.view.SessionUserBean;
import garage.origin.service.GenericServiceImpl;
import project.edge.common.constant.FolderPath;
import project.edge.common.constant.SystemConfigKeys;
import project.edge.common.util.PrintImage;
import project.edge.dao.archive.ArchiveDao;
import project.edge.dao.process.CompletedInfoAttachmentDao;
import project.edge.dao.process.CompletedInfoDao;
import project.edge.domain.entity.Archive;
import project.edge.domain.entity.CompletedInfo;
import project.edge.domain.entity.CompletedInfoAttachment;
import project.edge.domain.entity.Project;
import project.edge.domain.entity.ProjectPerson;
import project.edge.domain.entity.Review;
import project.edge.domain.entity.ReviewExpert;
import project.edge.domain.view.CompletedInfoBean;
import project.edge.service.acceptance.ReviewExpertService;
import project.edge.service.acceptance.ReviewService;
import project.edge.service.hr.ExpertService;
import project.edge.service.project.ProjectPersonService;
import project.edge.service.project.ProjectService;
import project.edge.service.system.SystemConfigService;

/**
 * @author angel_000
 *         [t_completed_info]对应的Service。
 */
@Service
public class CompletedInfoServiceImpl extends GenericServiceImpl<CompletedInfo, String>
        implements CompletedInfoService {

    @Resource
    private CompletedInfoDao completedInfoDao;

    @Resource
    private CompletedInfoService completedInfoServcie;
    
    @Resource
    private ArchiveDao archiveDao;

    @Resource
    private CompletedInfoAttachmentDao completedInfoAttachmentDao;
    
    @Resource
    private CompletedInfoAttachmentService completedInfoAttachmentService;

    @Resource
    private ProjectService projectService;
    
    @Resource
    protected SystemConfigService systemConfigService;
    
    @Resource
    private ProjectPersonService projectPersonService;

    @Resource
    private ReviewExpertService reviewExpertService;
    
    @Resource
    private ReviewService reviewService;
    
    @Resource
    private ExpertService expertService;
    
    @Resource
    protected MessageSource messageSource;
    
    //private static final String TEMPLATE_CERT_IMG = "/template/projectComplete.jpg";
    private static final String TEMPLATE_CERT_IMG = "CERT_TEMPLATE.JPG";
	private final static short ARCHIVE_TYPE_COMPLETE_CERT = 20;
    
    @Override
    public Dao<CompletedInfo, String> getDefaultDao() {
        // TODO Auto-generated method stub
        return this.completedInfoDao;
    }

    @Override
    public OrderByDto getDefaultOrder() {
        return new OrderByDto("cDatetime", false);
    }

    /**
     * 增加处理附件文件。
     */
    @Override
    @Transactional
    public void create(CompletedInfo entity) {

        if (entity == null || StringUtils.isEmpty(entity.getId())) {
            return;
        }

//        // 如果项目文件夹对应的Archive不存在，则创建
//        if (this.archiveDao.find(FolderPath.PROJECT_COMPLETED) == null) {
//            this.archiveDao.create(this.getProjectRootDirArchive());
//        }
//
//        // 如果Project entity对应的文件夹Archive不存在，则创建
//        if (this.archiveDao.find(entity.getId()) == null) {
//            this.archiveDao.create(this.getDirArchive(entity));
//        }
        
        //如果年份文件夹存在，则创建
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
        Date date = new Date();
        String year = sdf.format(date);
        
        if (this.archiveDao.find(year) == null) {
            this.archiveDao.create(this.getYearRootDirArchive(year));
        }
        
        if(this.archiveDao.find(entity.getProject().getId()) == null) {
            this.archiveDao.create(this.getProjectRootDirArchive(year,entity.getProject()));
        }
        
        if(this.archiveDao.find(entity.getProject().getId() + "_" + FolderPath.SYSTEM_PLACE_ON_FILE) == null) {
            this.archiveDao.create(this.getSystemPlaceOnFileRootDirArchive(year,entity.getProject()));
        }
     
        if(this.archiveDao.find(entity.getProject().getId() + "_" + FolderPath.PROJECT_COMPLETED) == null) {
            this.archiveDao.create(this.getCompletedInfoRootDirArchive(year,entity));
        }
        
        this.completedInfoDao.create(entity);
        for (CompletedInfoAttachment a : entity.getCompletedInfoAttachments()) {
            this.archiveDao.create(a.getArchive());
            this.completedInfoAttachmentDao.create(a);
        }

    }

    /**
     * 修改，并增加处理附件文件。新建时没有附件，仅修改时有附件。
     */
    @Override
    @Transactional
    public void update(CompletedInfo entity) {
        if (entity == null || StringUtils.isEmpty(entity.getId())) {
            return;
        }

//        // 如果项目文件夹对应的Archive不存在，则创建
//        if (this.archiveDao.find(FolderPath.PROJECT_COMPLETED) == null) {
//            this.archiveDao.create(this.getProjectRootDirArchive());
//        }
//
//        // 如果Project entity对应的文件夹Archive不存在，则创建
//        if (this.archiveDao.find(entity.getId()) == null) {
//            this.archiveDao.create(this.getDirArchive(entity));
//        }
        
        //如果年份文件夹存在，则创建
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
        Date date = new Date();
        String year = sdf.format(date);
        
        if (this.archiveDao.find(year) == null) {
            this.archiveDao.create(this.getYearRootDirArchive(year));
        }
        
        if(this.archiveDao.find(entity.getProject().getId()) == null) {
            this.archiveDao.create(this.getProjectRootDirArchive(year,entity.getProject()));
        }
        
        if(this.archiveDao.find(entity.getProject().getId() + "_" + FolderPath.SYSTEM_PLACE_ON_FILE) == null) {
            this.archiveDao.create(this.getSystemPlaceOnFileRootDirArchive(year,entity.getProject()));
        }
        
        if(this.archiveDao.find(entity.getProject().getId() + "_" + FolderPath.PROJECT_COMPLETED) == null) {
            this.archiveDao.create(this.getCompletedInfoRootDirArchive(year,entity));
        }

        // 创建新增的文件
        for (CompletedInfoAttachment attachment : entity.getCompletedInfoAttachments()) {

            this.archiveDao.create(attachment.getArchive());
            this.completedInfoAttachmentDao.create(attachment);
        }

        super.update(entity);

        for (CompletedInfoAttachment attachmentToDelete : entity.getAttachmentsToDelete()) {
            this.archiveDao.delete(attachmentToDelete.getArchive());
        }
    }
    
    /**
     * 根据证书模板生成证书文件
     */
    @Transactional
    public JsonResultBean generateCert(CompletedInfoBean bean, SessionUserBean userBean, Locale locale) {
        String itemStr = "";
        String contentStr = "";
        int strlen = 0;
        CommonFilter filter = null;
        String defaultFontName = "黑体";
        Font defaultFont = new Font(defaultFontName,Font.PLAIN, 20);
        Color defaultColor = new Color(73, 90, 128);
    	JsonResultBean jsonResult = new JsonResultBean();
    	
    	// 生成条件确认
        filter = new CommonFilter().addExact("pj.id", bean.getProject_())
        							.addExact("reviewStatus.id", "CHECK_STATUS_4");
        List<OrderByDto> orders = new ArrayList<>();
        orders.add(new OrderByDto("mDatetime", true));
        List<Review> reviewList = reviewService.list(filter, orders);
        if (reviewList.size() <= 0) {
        	jsonResult.setStatus(JsonStatus.ERROR);
        	jsonResult.setMessage(this.messageSource.getMessage("message.error.generate.cert.review", null, locale));
            jsonResult.getDataMap().put(JsonResultBean.KEY_RETURN_OBJECT, "");
            return jsonResult;
        }
    	
    	String rootFolderPath = this.systemConfigService.getStringConfig(SystemConfigKeys.ARCHIVE_ROOT_FOLDER_PATH);
    	filter = new CommonFilter().addExact("archiveName", TEMPLATE_CERT_IMG)
    			.addNull("versionOf");
    	List<Archive> templateArchiveList = archiveDao.list(filter, null);
    	if (templateArchiveList.size() < 1) {
    		jsonResult.setStatus(JsonStatus.ERROR);
        	jsonResult.setMessage(this.messageSource.getMessage("message.error.without.cert.file", null, locale));
            jsonResult.getDataMap().put(JsonResultBean.KEY_RETURN_OBJECT, "");
            return jsonResult;
    	}
        String imgPath = rootFolderPath + templateArchiveList.get(0).getRelativePath();
        
        PrintImage pi = new PrintImage();
        BufferedImage bi = pi.loadImageLocal(imgPath);
                
        Project projectEntity = projectService.find(bean.getProject_());
        if (projectEntity == null) {
        	jsonResult.setStatus(JsonStatus.ERROR);
        	jsonResult.setMessage(this.messageSource.getMessage("message.error.file.generate.failed", null, locale));
            jsonResult.getDataMap().put(JsonResultBean.KEY_RETURN_OBJECT, "");
            return jsonResult;
        }
        
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddhhmmss");
        String number = projectEntity.getProjectNum();
        contentStr = "No." + number + sdf.format(new Date());
        pi.writeRightAlign(contentStr, 0, 30, 10, defaultFont, new Color(255, 0, 0));
        
        contentStr = this.messageSource.getMessage("ui.label.title.project.completed", null, locale);
        pi.setFont(new Font(defaultFontName,Font.PLAIN, 30));
        strlen = contentStr.length()*25;
        pi.modifyShapImg(bi, contentStr, (600-strlen)/2, 150, defaultColor);
        
        itemStr = this.messageSource.getMessage("ui.fields.project.set.project.name", null, locale) + ": ";
        contentStr = projectEntity.getProjectName();
        pi.setFont(defaultFont);
        strlen = contentStr.length()*10;
        pi.writeContent(itemStr, contentStr, 160, 220, defaultFont, defaultColor);
        
        itemStr = this.messageSource.getMessage("ui.datatype.project.person", null, locale) + ": ";
        contentStr = "";
        filter = new CommonFilter().addExact("project.id", bean.getProject_());
        List<ProjectPerson> dbList = this.projectPersonService.list(filter, null);
        for (ProjectPerson entity : dbList) {
        	contentStr = contentStr + entity.getPerson().getPersonName() + ", ";
        }
        if (contentStr.length()>2) contentStr = contentStr.substring(0, contentStr.length()-2);
        pi.setFont(defaultFont);
        strlen = contentStr.length()*10;
        pi.writeContent(itemStr, contentStr, 160, 0, defaultFont, defaultColor);
        
        itemStr = this.messageSource.getMessage("ui.label.content.review.expert", null, locale) + ": ";
        contentStr = "";
//        filter = new CommonFilter().addExact("pj.id", bean.getProject_())
//        							.addExact("reviewStatus.id", "CHECK_STATUS_4");
//        List<OrderByDto> orders = new ArrayList<>();
//        orders.add(new OrderByDto("mDatetime", true));
//        List<Review> reviewList = reviewService.list(filter, orders);
        Review reviewEntity = null;
        if (reviewList.size() > 0) {
        	reviewEntity = reviewList.get(0);
        	filter = new CommonFilter().addExact("review.id", reviewEntity.getId());
        	List<ReviewExpert> reviewExpertList = reviewExpertService.list(filter, null);
        	for (ReviewExpert reviewExpertEntity : reviewExpertList) {
        		contentStr = contentStr + reviewExpertEntity.getExpert().getExpertName() + ", ";
        	}
        }
        if (contentStr.length()>2) contentStr = contentStr.substring(0, contentStr.length()-2);
        pi.setFont(defaultFont);
        pi.writeContent(itemStr, contentStr, 160, 0, defaultFont, defaultColor);
        
        itemStr = this.messageSource.getMessage("ui.label.content.expert.comment", null, locale) + ": ";
        contentStr = "";
        if (reviewEntity != null && reviewEntity.getReviewResultContent() != null) {
        	contentStr = reviewEntity.getReviewResultContent();
        }
        if (contentStr.length()>2) contentStr = contentStr.substring(0, contentStr.length()-2);
        pi.writeContent(itemStr, contentStr, 160, 0, defaultFont, defaultColor);
        
        contentStr = this.messageSource.getMessage("ui.label.content.generate.date", null, locale) + ": ";
        sdf = new SimpleDateFormat("yyyy-MM-dd");
        contentStr += sdf.format(new Date());
        pi.writeRightAlign(contentStr, 0, 700, 60, defaultFont, defaultColor);
        
        String certFilePath = rootFolderPath + File.separator + FolderPath.PROJECT_COMPLETED;
        String certFileName = bean.getId() + ".jpg";
        pi.writeImageLocal(certFilePath + File.separator + certFileName);
        
        Archive archiveEntity = new Archive();
        archiveEntity.setArchiveName(certFileName);
        archiveEntity.setIsDir(OnOffEnum.OFF.value());
        File pf = new File(certFilePath + File.separator + certFileName);
        archiveEntity.setFileSize((int)pf.length());
        
        CompletedInfo completedInfoEntity = completedInfoServcie.find(bean.getId());
        CompletedInfoAttachment attachment = new CompletedInfoAttachment();
        attachment.setCompletedInfo(completedInfoEntity);
        archiveEntity.setLevel(3);
        archiveEntity.setPid(completedInfoEntity.getId());
        archiveEntity.setPath(Constants.SLASH + Constants.COMMA + FolderPath.PROJECT_COMPLETED + Constants.COMMA
                + completedInfoEntity.getId());
        archiveEntity.setRelativePath(File.separator + FolderPath.PROJECT_COMPLETED + File.separator + certFileName);
        archiveEntity.setCreator(userBean.getSessionUserId());
        archiveEntity.setcDatetime(new Date());
        archiveEntity.setmDatetime(new Date());
        attachment.setArchive(archiveEntity);
        attachment.setArchiveType(ARCHIVE_TYPE_COMPLETE_CERT);
        completedInfoEntity.setIsGeneratedCert(OnOffEnum.ON.value());
        completedInfoDao.update(completedInfoEntity);
        //filter = new CommonFilter().addExact("pid", completedInfoEntity.getId());
        //List<Archive> oldArchiveList = archiveDao.list(filter, null);
        //if (oldArchiveList.size() < 1) {
            archiveDao.create(archiveEntity);
        //} else {
        //	archiveEntity.setId(oldArchiveList.get(0).getId());
        //	archiveDao.update(archiveEntity);
        //}
        filter = new CommonFilter().addExact("completedInfo.id", completedInfoEntity.getId())
        							.addExact("archiveType", ARCHIVE_TYPE_COMPLETE_CERT);
        List<CompletedInfoAttachment> oldCompletedInfoAttachmentList = completedInfoAttachmentService.list(filter, null);
        completedInfoAttachmentDao.create(attachment);
        if (oldCompletedInfoAttachmentList.size() > 0) {
        	completedInfoAttachmentDao.delete(oldCompletedInfoAttachmentList.get(0));
        }
        
        jsonResult.setStatus(JsonStatus.OK);
    	jsonResult.setMessage(this.messageSource.getMessage("message.info.file.generate.success", null, locale));
        jsonResult.getDataMap().put(JsonResultBean.KEY_RETURN_OBJECT, "");
    	return jsonResult;
    }

    /**
     * 创建年的根文件夹Archive。
     * 
     * @return
     */
    private Archive getYearRootDirArchive(String year) {

        // 实体对应的文件夹Archive
        Archive dir = new Archive();
        dir.setId(year);
        dir.setArchiveName(year + "年");
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
    
    /**
     * 创建项目的根文件夹Archive。
     * 
     * @return
     */
    private Archive getProjectRootDirArchive(String year, Project entity) {

        // 实体对应的文件夹Archive
        Archive dir = new Archive();
        dir.setId(entity.getId());
        dir.setArchiveName(entity.getProjectName() + "_" + entity.getProjectNum());
        dir.setIsDir(OnOffEnum.ON.value());
        dir.setRelativePath(File.separator + year + File.separator + dir.getId());
        dir.setFileSize(null);
        dir.setPid(year);

        /**
         * 第零层：根
         * 第一层：FolderPath.PROJECT(DIR)
         * 第二层：Project.id(DIR)
         * 第三层：Archive文件
         */
        dir.setLevel(2);
        dir.setPath(Constants.SLASH + Constants.COMMA + year);
        dir.setIsDeleted(OnOffEnum.OFF.value());
        dir.setCreator(Constants.ADMIN_USER_ID);

        Date now = new Date();
        dir.setcDatetime(now);
        dir.setmDatetime(now);

        return dir;
    }
    
    /**
     * 创建实体对应的文件夹Archive。
     * 
     * @return
     */
    private Archive getSystemPlaceOnFileRootDirArchive(String year, Project entity) {

        // 实体对应的文件夹Archive
        Archive dir = new Archive();
        dir.setId(entity.getId() + "_" + FolderPath.SYSTEM_PLACE_ON_FILE);
        dir.setArchiveName("系统归档"); // 项目名不允许修改，所以不考虑变更项目名引起项目文件夹改名的问题
        dir.setIsDir(OnOffEnum.ON.value());
        dir.setRelativePath(File.separator + year + File.separator + entity.getId() + File.separator
                + dir.getId());
        dir.setFileSize(null);
        dir.setPid(entity.getId());

        /**
         * 第零层：根
         * 第一层：FolderPath.PROJECT(DIR)
         * 第二层：Project.id(DIR)
         * 第三层：Archive文件
         */
        dir.setLevel(3);
        dir.setPath(Constants.SLASH + Constants.COMMA + year + Constants.COMMA + entity.getId());
        dir.setIsDeleted(OnOffEnum.OFF.value());
//        Person person = entity.getCreator();
//        if (person != null) {
//            dir.setCreator(person.getId());
//        }
        
        dir.setCreator(Constants.ADMIN_USER_ID);
        
        Date now = new Date();
        dir.setcDatetime(now);
        dir.setmDatetime(now);

        return dir;
    }
    
    /**
     * 创建实体对应的文件夹Archive。
     * 
     * @return
     */
    private Archive getCompletedInfoRootDirArchive(String year, CompletedInfo entity) {

        // 实体对应的文件夹Archive
        Archive dir = new Archive();
        dir.setId(entity.getProject().getId() + "_" + FolderPath.PROJECT_COMPLETED);
        dir.setArchiveName("竣工验收"); // 项目名不允许修改，所以不考虑变更项目名引起项目文件夹改名的问题
        dir.setIsDir(OnOffEnum.ON.value());
        dir.setRelativePath(File.separator + year + File.separator + entity.getProject().getId() + File.separator
                + entity.getProject().getId() + "_" + FolderPath.SYSTEM_PLACE_ON_FILE + File.separator
                + dir.getId());
        dir.setFileSize(null);
        dir.setPid(entity.getProject().getId() + "_" + FolderPath.SYSTEM_PLACE_ON_FILE);

        /**
         * 第零层：根
         * 第一层：FolderPath.PROJECT(DIR)
         * 第二层：Project.id(DIR)
         * 第三层：Archive文件
         */
        dir.setLevel(4);
        dir.setPath(Constants.SLASH + Constants.COMMA + year + Constants.COMMA + entity.getProject().getId()
                + Constants.COMMA + entity.getProject().getId() + "_" + FolderPath.SYSTEM_PLACE_ON_FILE);
        dir.setIsDeleted(OnOffEnum.OFF.value());
//        Person person = entity.getProject().getCreator();
//        if (person != null) {
//            dir.setCreator(person.getId());
//        }

        dir.setCreator(Constants.ADMIN_USER_ID);
        dir.setcDatetime(entity.getcDatetime());
        dir.setmDatetime(entity.getmDatetime());

        return dir;
    }    
    
    /**
     * 创建实体对应的文件夹Archive。
     * 
     * @return
     */
    private Archive getDirArchive(CompletedInfo entity) {

        // 实体对应的文件夹Archive
        Archive dir = new Archive();
        dir.setId(entity.getId());
        dir.setArchiveName(entity.getCompletedContent()); // 项目名不允许修改，所以不考虑变更项目名引起项目文件夹改名的问题
        dir.setIsDir(OnOffEnum.ON.value());
        dir.setRelativePath(
                File.separator + FolderPath.PROJECT_COMPLETED + File.separator + dir.getId());
        dir.setFileSize(null);
        dir.setPid(FolderPath.PROJECT_COMPLETED);

        /**
         * 第零层：根
         * 第一层：FolderPath.PROJECT(DIR)
         * 第二层：Project.id(DIR)
         * 第三层：Archive文件
         */
        dir.setLevel(2);
        dir.setPath(Constants.SLASH + Constants.COMMA + FolderPath.PROJECT_COMPLETED);
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
        dir.setId(FolderPath.PROJECT_COMPLETED);
        dir.setArchiveName("项目验收归档");
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
