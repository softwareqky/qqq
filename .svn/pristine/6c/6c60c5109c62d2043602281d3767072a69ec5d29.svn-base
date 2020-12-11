/**
 * 
 */
package project.edge.domain.converter;

import java.io.File;
import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import org.springframework.context.MessageSource;
import org.springframework.util.StringUtils;

import garage.origin.common.constant.Constants;
import garage.origin.common.constant.OnOffEnum;
import garage.origin.common.util.DateUtils;
import garage.origin.domain.view.AbstractSessionUserBean;
import garage.origin.domain.view.converter.ViewConverter;
import project.edge.common.constant.FolderPath;
import project.edge.domain.entity.Archive;
import project.edge.domain.entity.Dept;
import project.edge.domain.entity.NoticeAnnouncement;
import project.edge.domain.entity.NoticeAttachment;
import project.edge.domain.entity.Person;
import project.edge.domain.entity.ProjectPerson;
import project.edge.domain.view.ArchiveBean;
import project.edge.domain.view.NoticeAnnouncementBean;

/**
 * @author angel_000
 *
 */
public class NoticeAnnouncementBeanConverter implements ViewConverter<NoticeAnnouncement, NoticeAnnouncementBean> {

	@Override
	public NoticeAnnouncementBean fromEntity(NoticeAnnouncement entity, MessageSource messageSource, Locale locale) {
		// TODO Auto-generated method stub

		NoticeAnnouncementBean bean = new NoticeAnnouncementBean();
		bean.setId(entity.getId());

		bean.setPublish_(entity.getIsPublish());
		bean.setPublishText(entity.getIsPublish() == null || entity.getIsPublish() == 0 ? "未发布" : "已发布");
		String recivers_ = "";
		String project_ = "";
		if (!StringUtils.isEmpty(entity.getRecivers())) {
			String[] recivers = entity.getRecivers().split("\\|\\|");
			project_ = recivers[0].replace("project:", "");
			if (recivers.length == 2) {
				recivers_ = recivers[1].replace("virtualOrg:", "");
			}
		}
		bean.setRecivers_(recivers_);
		bean.setProject_(project_);
		bean.setNoticeNo(entity.getNoticeNo());

		ProjectPerson originator = entity.getOriginator();
        if (originator != null) {
            bean.setOriginator_(originator.getId());
            bean.setOriginatorText(originator.getPerson().getPersonName());
        }

		bean.setTittle(entity.getTittle());
		bean.setContent(entity.getContent());

		Dept recvDept = entity.getRecvDept();
		if (recvDept != null) {
			bean.setRecvDept_(recvDept.getId());
			bean.setRecvDeptText(recvDept.getDeptName());
		}

		ProjectPerson recvPerson = entity.getRecvPerson();
		if (recvPerson != null) {
			bean.setRecvPerson_(recvPerson.getId());
			bean.setRecvPersonText(recvPerson.getPerson().getPersonName());
		}

		if (entity.getcDatetime() != null) {
			bean.setcDatetime(DateUtils.date2String(entity.getcDatetime(), Constants.DATE_FORMAT));
		}

		if (entity.getIsDelete() != null) {
			bean.setIsDelete(entity.getIsDelete());
			bean.setIsDeleteText(
					entity.getIsDelete() == 1 ? messageSource.getMessage(OnOffEnum.ON.resourceName(), null, locale)
							: messageSource.getMessage(OnOffEnum.OFF.resourceName(), null, locale));
		}

		if (entity.getIsRevoke() != null) {
			bean.setIsRevoke(entity.getIsRevoke());
			bean.setIsRevokeText(
					entity.getIsRevoke() == 1 ? messageSource.getMessage(OnOffEnum.ON.resourceName(), null, locale)
							: messageSource.getMessage(OnOffEnum.OFF.resourceName(), null, locale));
		}
		
		if (entity.getIsForAll() != null) {
			bean.setIsForAll(entity.getIsForAll());
			bean.setIsForAllText(
					entity.getIsForAll() == 1 ? messageSource.getMessage(OnOffEnum.ON.resourceName(), null, locale)
							: messageSource.getMessage(OnOffEnum.OFF.resourceName(), null, locale));
		}

		if (entity.getrDatetime() != null) {
			bean.setrDatetime(DateUtils.date2String(entity.getrDatetime(), Constants.DATE_FORMAT));
		}

		if (entity.getdDatetime() != null) {
			bean.setdDatetime(DateUtils.date2String(entity.getdDatetime(), Constants.DATE_FORMAT));
		}

		if (entity.getmDatetime() != null) {
			bean.setmDatetime(DateUtils.date2String(entity.getmDatetime(), Constants.DATE_FORMAT));
		}

		// bean.setLevel(entity.getLevel());

		// 附件处理
		ArchiveBeanConverter abConverter = new ArchiveBeanConverter();
		Set<NoticeAttachment> noticeAttachments = entity.getNoticeAttachments();

		for (NoticeAttachment na : noticeAttachments) {
			bean.getArchivesList().add(abConverter.fromEntity(na.getArchive(), messageSource, locale));
		}

		return bean;
	}

	@Override
	public NoticeAnnouncement toEntity(NoticeAnnouncementBean bean, NoticeAnnouncement oldEntity,
			AbstractSessionUserBean user, Date now) {

		// TODO Auto-generated method stub
		NoticeAnnouncement entity = oldEntity;

		if (entity == null) { // 表示新建
			entity = new NoticeAnnouncement();

			entity.setCreator(user.getSessionUserId());
			entity.setcDatetime(now);

			// 设置默认发起人
//			if (entity.getOriginator() == null) {
//				ProjectPerson originator = new ProjectPerson();
//	            Person person = new Person();
//	            person.setPersonName(user.getSessionUserName());
//	            originator.setId(user.getSessionUserId());
//	            originator.setPerson(person);
//	            entity.setOriginator(originator);
//			}
			

		} else { // 表示修改
			entity.setModifier(user.getSessionUserId());

			entity = oldEntity.clone();
			// 修改后，仍然保留的档案文件
			entity.setNoticeAttachments(new HashSet<>());

			Map<String, String> map = new HashMap<>();
			for (String aid : bean.getArchivesReservedList()) {
				map.put(aid, aid);
			}
			// 找出需要删除的Archive
			for (NoticeAttachment dbAttachment : oldEntity.getNoticeAttachments()) {

				boolean needCheck = false;
				String uploadType = bean.getUploadFileType();
				if ("edit".equals(uploadType)) {
					needCheck = true;
				}

				if (needCheck && !map.containsKey(dbAttachment.getArchive().getId())) {
					entity.getAttachmentsToDelete().add(dbAttachment);
				}
			}

			entity.setNoticeNo(bean.getNoticeNo());
		}

		bean.setId(entity.getId()); // ID必须赋值
		entity.setmDatetime(now);
		entity.setIsPublish(bean.getPublish_());
		String recivers = "";

		if (!StringUtils.isEmpty(bean.getRecivers_())) {
			recivers = "project:" + bean.getProject_() + "||virtualOrg:" + bean.getRecivers_();
		} else if (!StringUtils.isEmpty(bean.getProject_())) {
			recivers = "project:" + bean.getProject_();
		}

		entity.setRecivers(recivers);
		// private Short level;
		if (!StringUtils.isEmpty(bean.getRecvDept_())) {
			Dept recvDept = new Dept();
			recvDept.setId(bean.getRecvDept_());
			entity.setRecvDept(recvDept);
		}
		if (!StringUtils.isEmpty(bean.getRecvPerson_())) {
			ProjectPerson recvPerson = new ProjectPerson();
            Person person = new Person();
            person.setPersonName(bean.getRecvPersonText());
            recvPerson.setId(bean.getRecvPerson_());
            recvPerson.setPerson(person);
            entity.setRecvPerson(recvPerson);
		}
		
//		if (!StringUtils.isEmpty(bean.getChecker_())) {
//            ProjectPerson checker = new ProjectPerson();
//            Person person = new Person();
//            person.setPersonName(bean.getCheckerText());
//            checker.setId(bean.getChecker_());
//            checker.setPerson(person);
//            entity.setChecker(checker);
//        }

		if (!StringUtils.isEmpty(bean.getOriginator_())) {
			ProjectPerson originator = new ProjectPerson();
            Person person = new Person();
            person.setPersonName(bean.getOriginatorText());
            originator.setId(bean.getOriginator_());
            originator.setPerson(person);
            entity.setOriginator(originator);
		}
		entity.setTittle(bean.getTittle());
		entity.setContent(bean.getContent());

		if (!StringUtils.isEmpty(bean.getcDatetime())) {
			try {
				entity.setcDatetime(DateUtils.string2Date(bean.getcDatetime(), Constants.DATE_FORMAT));
			} catch (ParseException e) {
				// do nothing
			}
		}

		entity.setIsDelete(bean.getIsDelete());
		entity.setIsRevoke(bean.getIsRevoke());
		entity.setIsForAll(bean.getIsForAll());

		if (!StringUtils.isEmpty(bean.getrDatetime())) {
			try {
				entity.setrDatetime(DateUtils.string2Date(bean.getrDatetime(), Constants.DATE_FORMAT));
			} catch (ParseException e) {
				// do nothing
			}
		}

		if (!StringUtils.isEmpty(bean.getdDatetime())) {
			try {
				entity.setdDatetime(DateUtils.string2Date(bean.getdDatetime(), Constants.DATE_FORMAT));
			} catch (ParseException e) {
				// do nothing
			}
		}

		// entity.setLevel(bean.getLevel());

		// 附件处理，此处只需处理新增加的ProjectAttachment
		if (!StringUtils.isEmpty(bean.getArchivesList())) {
			Set<NoticeAttachment> noticeAttachments = new HashSet<>();

			ArchiveBeanConverter abConverter = new ArchiveBeanConverter();
			for (ArchiveBean ab : bean.getArchivesList()) {
				noticeAttachments.add(this.toAttachmentEntity(ab, abConverter, entity, user, now));
			}
			entity.setNoticeAttachments(noticeAttachments);
		}

		return entity;

	}

	/**
	 * 把ArchiveBean转换成Archive，同时创建对应的ProjectAttachment并返回。
	 * 
	 * @param ab
	 * @param abConverter
	 * @param entity
	 * @param attachmentType
	 * @param user
	 * @param now
	 * @return
	 */
	private NoticeAttachment toAttachmentEntity(ArchiveBean ab, ArchiveBeanConverter abConverter,
			NoticeAnnouncement entity, AbstractSessionUserBean user, Date now) {

		NoticeAttachment attachment = new NoticeAttachment();
		attachment.setNoticeAnnouncement(entity);

		/**
		 * 第零层：根 第一层：FolderPath.PROJECT(DIR) 第二层：Project.id(DIR) 第三层：Archive文件
		 */
		ab.setLevel(3);
		ab.setPid(entity.getId());
		ab.setPath(Constants.SLASH + Constants.COMMA + FolderPath.NOTICE + Constants.COMMA + entity.getId());
		Archive aentity = abConverter.toEntity(ab, null, user, now);

		// "\project\guid\xxx.doc"
		// String extension =
		// FilenameUtils.getExtension(ab.getArchiveName()).toLowerCase();
		// String relativePath = File.separator + FolderPath.PROJECT + File.separator
		// + entity.getId() + File.separator + aentity.getId() + Constants.DOT +
		// extension;
		String relativePath = File.separator + FolderPath.NOTICE + File.separator + entity.getId() + File.separator
				+ aentity.getId();
		ab.setRelativePath(relativePath);
		aentity.setRelativePath(relativePath);

		// Archive:
		// id/level/pid/(id)path/relativePath由converter各自设置，
		// archiveName/isDir/fileSize在Controller中统一设置，
		// fileDigest暂不使用

		attachment.setArchive(aentity);
		return attachment;
	}

}
