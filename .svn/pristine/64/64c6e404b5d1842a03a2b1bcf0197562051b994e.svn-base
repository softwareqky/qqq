package project.edge.domain.converter;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import org.springframework.context.MessageSource;
import org.springframework.util.StringUtils;

import garage.origin.common.constant.Constants;
import garage.origin.common.util.DateUtils;
import garage.origin.domain.view.AbstractSessionUserBean;
import garage.origin.domain.view.converter.ViewConverter;
import project.edge.common.constant.FlowStatusEnum;
import project.edge.common.constant.FolderPath;
import project.edge.common.constant.InvestorEnum;
import project.edge.common.constant.ProjectAttachmentTypeEnum;
import project.edge.common.constant.ReportTypeEnum;
import project.edge.domain.entity.Archive;
import project.edge.domain.entity.Person;
import project.edge.domain.entity.Plan;
import project.edge.domain.entity.PlanProgress;
import project.edge.domain.entity.PlanProgressAttachment;
import project.edge.domain.entity.Project;
import project.edge.domain.entity.VirtualOrg;
import project.edge.domain.view.ArchiveBean;
import project.edge.domain.view.PlanProgressBean;

/**
 * @author angel_000 转换实际进度对应的view和entity的转换器。
 */
public class PlanProgressBeanConverter implements ViewConverter<PlanProgress, PlanProgressBean> {

	@Override
	public PlanProgressBean fromEntity(PlanProgress entity, MessageSource messageSource, Locale locale) {

		PlanProgressBean bean = new PlanProgressBean();
		bean.setId(entity.getId());

		Plan plan = entity.getPlan();
		if (plan != null) {
			bean.setPlan_(plan.getId());
			bean.setPlanText(plan.getPlanName());
		}

		Project project = entity.getProject();
		if (project != null) {
			bean.setProject_(project.getId());
			bean.setProjectText(project.getProjectName());
		}

		VirtualOrg virtualOrg = entity.getVirtualOrg();
		if (virtualOrg != null) {
			bean.setVirtualOrg_(virtualOrg.getId());
			try {
				bean.setVirtualOrgText(virtualOrg.getVirtualOrgName());
			} catch (Exception e) {
			}
		}

		if (entity.getProgressDate() != null) {
			bean.setProgressDate(DateUtils.date2String(entity.getProgressDate(), Constants.DATE_FORMAT));
		}

		Person applicant = entity.getApplicant();
		if (applicant != null) {
			bean.setApplicant_(applicant.getId());
			bean.setApplicantText(applicant.getPersonName());
		}

		bean.setProgressName(entity.getProgressName());

		bean.setReportType(entity.getReportType());
		bean.setReportTypeText(
				messageSource.getMessage(ReportTypeEnum.getResouceName(entity.getReportType()), null, locale));

		bean.setSubTitle(entity.getSubTitle());
		bean.setRecipientUnit(entity.getRecipientUnit());
		if (entity.getPostDate() != null) {
			bean.setPostDate(DateUtils.date2String(entity.getPostDate(), Constants.DATE_FORMAT));
		}
		bean.setOverview(entity.getOverview());
		bean.setRecipient(entity.getRecipient());
		bean.setApplicantMobile(entity.getApplicantMobile());
		bean.setTotalAmount(entity.getTotalAmount());
		bean.setInvestedAmount(entity.getInvestedAmount());
		bean.setCurrentProgress(entity.getCurrentProgress());
		bean.setAppearanceProgress(entity.getAppearanceProgress());

		bean.setInvestor(entity.getInvestor());
		bean.setInvestorText(messageSource.getMessage(InvestorEnum.getResouceName(entity.getInvestor()), null, locale));

		bean.setAnnualTarget(entity.getAnnualTarget());
		bean.setCompletionStatement(entity.getCompletionStatement());

		bean.setDescription(entity.getDescription());
		bean.setRemark(entity.getRemark());

		if (entity.getFlowStartDate() != null) {
			bean.setFlowStartDate(DateUtils.date2String(entity.getFlowStartDate(), Constants.DATE_FORMAT));
		}

		if (entity.getFlowEndDate() != null) {
			bean.setFlowEndDate(DateUtils.date2String(entity.getFlowEndDate(), Constants.DATE_FORMAT));
		}
		bean.setFlowStatus(entity.getFlowStatus());
		bean.setFlowStatusText(
				messageSource.getMessage(FlowStatusEnum.getResouceName(entity.getFlowStatus()), null, locale));

		// 负责人
		if(entity.getPlan() != null) {
			if (entity.getPlan().getSite() != null && entity.getPlan().getSite().getPersonInCharge() != null) {
				if(entity.getPlan().getVirtualOrg() != null) {//专业组是可编程或sdn，就取对应负责人
					 if(entity.getPlan().getVirtualOrg().getId().equals("74526f1e-b232-4c9c-8b61-a95172496cf4")) {
						 if(entity.getPlan().getSite().getProgrammablePersonInCharge() != null) {
							 bean.setPersonInCharge_(entity.getPlan().getSite().getProgrammablePersonInCharge().getId());
							 bean.setPersonInChargeText(entity.getPlan().getSite().getProgrammablePersonInCharge().getPersonName()); 
						 }
					 }
					 else if(entity.getPlan().getVirtualOrg().getId().equals("710a9408-ac98-456a-b731-332dd91f69a9")) {
						 if(entity.getPlan().getSite().getSdnPersonInCharge() != null) {
							 bean.setPersonInCharge_(entity.getPlan().getSite().getSdnPersonInCharge().getId());
							 bean.setPersonInChargeText(entity.getPlan().getSite().getSdnPersonInCharge().getPersonName());
						 }
					 }
					 else if(entity.getPlan().getVirtualOrg().getId().equals("ae26cdfd-ac69-4de4-a44e-8a2b4cee6282")) {
						 if(entity.getPlan().getSite().getPersonInCharge() != null) {
							bean.setPersonInCharge_(entity.getPlan().getSite().getPersonInCharge().getId());
							bean.setPersonInChargeText(entity.getPlan().getSite().getPersonInCharge().getPersonName());
						 }
					 }
				}
	/*			else {
					bean.setPersonInCharge_(entity.getSite().getPersonInCharge().getId());
					bean.setPersonInChargeText(entity.getSite().getPersonInCharge().getPersonName());
				}*/
			}
			if (entity.getPlan().getSegment() != null && entity.getPlan().getSegment().getPersonInCharge() != null) {
				bean.setPersonInCharge_(entity.getPlan().getSegment().getPersonInCharge().getId());
				bean.setPersonInChargeText(entity.getPlan().getSegment().getPersonInCharge().getPersonName());
			}
			if(entity.getPlan().getIsYear() == 1) {
				bean.setPersonInCharge_(entity.getPlan().getCreator().getId());
				bean.setPersonInChargeText(entity.getPlan().getCreator().getPersonName());
			}
		}

		
		
		// 附件处理
		ArchiveBeanConverter abConverter = new ArchiveBeanConverter();
		Set<PlanProgressAttachment> planProgressAttachments = entity.getPlanProgressAttachments();
		for (PlanProgressAttachment pa : planProgressAttachments) {

			bean.getArchivesList().add(abConverter.fromEntity(pa.getArchive(), messageSource, locale));

		}
		return bean;
	}

	@Override
	public PlanProgress toEntity(PlanProgressBean bean, PlanProgress oldEntity, AbstractSessionUserBean user,
			Date now) {
		PlanProgress entity = oldEntity;

		if (entity == null) { // 表示新建
			entity = new PlanProgress();

			entity.setCreator(user.getSessionUserId());
			entity.setcDatetime(now);
		} else { // 表示修改
			entity = oldEntity.clone();
			entity.setModifier(user.getSessionUserId());
			entity.setPlanProgressAttachments(new HashSet<>());
			// 修改后，仍然保留的档案文件
			Map<String, String> map = new HashMap<>();
			for (String aid : bean.getArchivesReservedList()) {
				map.put(aid, aid);
			}

			// 找出需要删除的Archive
			for (PlanProgressAttachment dbAttachment : oldEntity.getPlanProgressAttachments()) {
				if (!map.containsKey(dbAttachment.getArchive().getId())) {
					entity.getAttachmentsToDelete().add(dbAttachment);
				}

			}
		}

		bean.setId(entity.getId()); // ID必须赋值
		entity.setmDatetime(now);

		if (!StringUtils.isEmpty(bean.getPlan_())) {
			Plan plan = new Plan();
			plan.setId(bean.getPlan_());
			entity.setPlan(plan);
		}

		if (!StringUtils.isEmpty(bean.getProject_())) {
			Project project = new Project();
			project.setId(bean.getProject_());
			project.setProjectName(bean.getProjectText());
			entity.setProject(project);
		}

		if (!StringUtils.isEmpty(bean.getVirtualOrg_())) {
			VirtualOrg virtualOrg = new VirtualOrg();
			virtualOrg.setId(bean.getVirtualOrg_());
			entity.setVirtualOrg(virtualOrg);
		}

		if (!StringUtils.isEmpty(bean.getProgressDate())) {
			try {
				entity.setProgressDate(DateUtils.string2Date(bean.getProgressDate(), Constants.DATE_FORMAT));
			} catch (ParseException e) {
				// do nothing
			}
		}

		if (!StringUtils.isEmpty(bean.getApplicant_())) {
			Person applicant = new Person();
			applicant.setId(bean.getApplicant_());
			entity.setApplicant(applicant);
		}

		entity.setProgressName(bean.getProgressName());

		entity.setReportType(bean.getReportType());
		entity.setSubTitle(bean.getSubTitle());
		entity.setRecipientUnit(bean.getRecipientUnit());

		if (!StringUtils.isEmpty(bean.getPostDate())) {
			try {
				entity.setPostDate(DateUtils.string2Date(bean.getPostDate(), Constants.DATE_FORMAT));
			} catch (ParseException e) {
				// do nothing
			}
		}

		entity.setOverview(bean.getOverview());
		entity.setRecipient(bean.getRecipient());
		entity.setApplicantMobile(bean.getApplicantMobile());
		entity.setTotalAmount(bean.getTotalAmount());
		entity.setInvestedAmount(bean.getInvestedAmount());
		entity.setCurrentProgress(bean.getCurrentProgress());
		entity.setAppearanceProgress(bean.getAppearanceProgress());
		entity.setInvestor(bean.getInvestor());
		bean.setAnnualTarget(bean.getAnnualTarget());
		bean.setCompletionStatement(bean.getCompletionStatement());

		entity.setDescription(bean.getDescription());
		entity.setRemark(bean.getRemark());

		if (!StringUtils.isEmpty(bean.getFlowStartDate())) {
			try {
				entity.setFlowStartDate(DateUtils.string2Date(bean.getFlowStartDate(), Constants.DATE_FORMAT));
			} catch (ParseException e) {
				// do nothing
			}
		}

		if (!StringUtils.isEmpty(bean.getFlowEndDate())) {
			try {
				entity.setFlowEndDate(DateUtils.string2Date(bean.getFlowEndDate(), Constants.DATE_FORMAT));
			} catch (ParseException e) {
				// do nothing
			}
		}

		entity.setFlowStatus(bean.getFlowStatus());

		// 附件处理，此处只需处理新增加的ProjectAttachment
		Set<PlanProgressAttachment> PlanProgressAttachments = new HashSet<>();

		ArchiveBeanConverter abConverter = new ArchiveBeanConverter();
		for (ArchiveBean ab : bean.getArchivesList()) {
			PlanProgressAttachments.add(this.toAttachmentEntity(ab, abConverter, entity,
					ProjectAttachmentTypeEnum.ARCHIVE.value(), user, now));
		}

		entity.setPlanProgressAttachments(PlanProgressAttachments);

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
	private PlanProgressAttachment toAttachmentEntity(ArchiveBean ab, ArchiveBeanConverter abConverter,
			PlanProgress entity, int attachmentType, AbstractSessionUserBean user, Date now) {

		PlanProgressAttachment attachment = new PlanProgressAttachment();
		attachment.setPlanProgress(entity);

//changed start by huang 20200405
//        /**
//         * 第零层：根
//         * 第一层：FolderPath.PROJECT(DIR)
//         * 第二层：Project.id(DIR)
//         * 第三层：Archive文件
//         */
//        ab.setLevel(3);
//        ab.setPid(entity.getId());
//        ab.setPath(Constants.SLASH + Constants.COMMA + FolderPath.PLAN_PROGRESS + Constants.COMMA
//                + entity.getId());

		/**
		 * 第零层：根 第一层：年份 第二层：项目名称+项目编号 FolderPath.PROJECT(DIR) 第三层: 系统归档 第四层：文件夹名
		 * 第五层：Archive文件
		 */
		ab.setLevel(5);

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
		Date date = new Date();
		String year = sdf.format(date);

		ab.setPid(entity.getProject().getId() + "_" + FolderPath.PLAN_PROGRESS);

		ab.setPath(Constants.SLASH + Constants.COMMA + year + Constants.COMMA + entity.getProject().getId()
				+ Constants.COMMA + entity.getProject().getId() + "_" + FolderPath.SYSTEM_PLACE_ON_FILE
				+ Constants.COMMA + entity.getProject().getId() + "_" + FolderPath.PLAN_PROGRESS);
		// chagned end by huang 20200404

		Archive aentity = abConverter.toEntity(ab, null, user, now);

		// "\project\guid\xxx.doc"
		// String extension =
		// FilenameUtils.getExtension(ab.getArchiveName()).toLowerCase();
		// String relativePath = File.separator + FolderPath.PROJECT + File.separator
		// + entity.getId() + File.separator + aentity.getId() + Constants.DOT +
		// extension;

		// changed start by huang
//        String relativePath = File.separator + FolderPath.PLAN_PROGRESS + File.separator
//                + entity.getId() + File.separator + aentity.getId();

		String relativePath = File.separator + year + File.separator + entity.getProject().getId() + File.separator
				+ entity.getProject().getId() + "_" + FolderPath.SYSTEM_PLACE_ON_FILE + File.separator
				+ entity.getProject().getId() + "_" + FolderPath.PLAN_PROGRESS + File.separator + aentity.getId();
		// changed end by huang
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
