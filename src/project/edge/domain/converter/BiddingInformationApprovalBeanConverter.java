/**
 * 
 */
package project.edge.domain.converter;

import org.springframework.context.MessageSource;
import org.springframework.util.StringUtils;

import java.text.ParseException;
import java.util.Date;
import java.util.Locale;

import garage.origin.common.constant.Constants;
import garage.origin.common.constant.OnOffEnum;
import garage.origin.common.util.DateUtils;
import garage.origin.domain.view.AbstractSessionUserBean;
import garage.origin.domain.view.converter.ViewConverter;
import project.edge.domain.entity.BiddingInformationApproval;
import project.edge.domain.entity.City;
import project.edge.domain.entity.DataOption;
import project.edge.domain.entity.Dept;
import project.edge.domain.entity.Person;
import project.edge.domain.entity.Province;
import project.edge.domain.entity.RelatedUnit;
import project.edge.domain.view.BiddingInformationApprovalBean;


/**
 * @author angel_000
 *
 */
public class BiddingInformationApprovalBeanConverter
        implements ViewConverter<BiddingInformationApproval, BiddingInformationApprovalBean> {

    @Override
    public BiddingInformationApprovalBean fromEntity(BiddingInformationApproval entity,
            MessageSource messageSource, Locale locale) {

        BiddingInformationApprovalBean bean = new BiddingInformationApprovalBean();
        bean.setId(entity.getId());

        Person applicant = entity.getApplicant();
        if (applicant != null) {
            bean.setApplicant_(applicant.getId());
            bean.setApplicantText(applicant.getPersonName());
        }
        bean.setApplicantName(entity.getApplicantName());
        bean.setApplicantDept(entity.getApplicantDept());
        bean.setApplicantPost(entity.getApplicantPost());
        bean.setApplicantMobile(entity.getApplicantMobile());

        RelatedUnit constructor = entity.getConstructor();
        if (constructor != null) {
            bean.setConstructor_(constructor.getUnitName());
            bean.setConstructorText(constructor.getUnitName());
        }
        bean.setConstructorName(entity.getConstructorName());
        bean.setConstructorContact(entity.getConstructorContact());
        bean.setConstructorContactMobile(entity.getConstructorContactMobile());
        bean.setConstructorAddress(entity.getConstructorAddress());
        bean.setConstructorCode(entity.getConstructorCode());

        RelatedUnit constructorProjectDept = entity.getConstructorProjectDept();
        if (constructorProjectDept != null) {
            bean.setConstructorProjectDept_(constructorProjectDept.getUnitName());
            bean.setConstructorProjectDeptText(constructorProjectDept.getUnitName());
        }
        bean.setConstructorIndustry(entity.getConstructorIndustry());
        bean.setConstructorProjectNum(entity.getConstructorProjectNum());
        bean.setProjectNum(entity.getProjectNum());
        bean.setGovernmentProjectNum(entity.getGovernmentProjectNum());
        bean.setProjectName(entity.getProjectName());

        if (entity.getProjectStartDate() != null) {
            bean.setProjectStartDate(
                    DateUtils.date2String(entity.getProjectStartDate(), Constants.DATE_FORMAT));
        }

        if (entity.getProjectEndDate() != null) {
            bean.setProjectEndDate(
                    DateUtils.date2String(entity.getProjectEndDate(), Constants.DATE_FORMAT));
        }

        Province province = entity.getProvince();
        if (province != null) {
            bean.setProvince_(province.getId());
            bean.setProvinceText(province.getProvinceName());
        }

        City city = entity.getCity();
        if (city != null) {
            bean.setCity_(city.getId());
            bean.setCityText(city.getCityName());
        }

        bean.setProjectAddress(entity.getProjectAddress());
        bean.setProjectDuration(entity.getProjectDuration());
        bean.setEffortEstimate(entity.getEffortEstimate());
        bean.setProjectCost(entity.getProjectCost());

        DataOption currency = entity.getCurrency();
        if (currency != null) {
            bean.setCurrency_(currency.getId());
            bean.setCurrencyText(currency.getOptionName());

        }

        bean.setExchangeRate(entity.getExchangeRate());
        bean.setProjectCostRmb(entity.getProjectCostRmb());
        bean.setEstimateProfitRate(entity.getEstimateProfitRate());
        bean.setEstimateFieldCostRate(entity.getEstimateFieldCostRate());

        bean.setIsSolution(entity.getIsSolution());
        if (entity.getIsSolution() != null) {
            bean.setIsSolutionText(entity.getIsSolution() == 1
                    ? messageSource.getMessage(OnOffEnum.ON.resourceName(), null, locale)
                    : messageSource.getMessage(OnOffEnum.OFF.resourceName(), null, locale));
        }

        DataOption projectStatus = entity.getProjectStatus();
        if (projectStatus != null) {
            bean.setProjectStatus_(projectStatus.getId());
            bean.setProjectStatusText(projectStatus.getOptionName());
        }

        DataOption projectType = entity.getProjectType();
        if (projectType != null) {
            bean.setProjectType_(projectType.getId());
            bean.setProjectTypeText(projectType.getOptionName());
        }

        bean.setProfessionalCost(entity.getProfessionalCost());

        DataOption qualityGrade = entity.getQualityGrade();
        if (qualityGrade != null) {
            bean.setQualityGrade_(qualityGrade.getId());
            bean.setQualityGradeText(qualityGrade.getOptionName());
        }

        DataOption projectCategory = entity.getProjectCategory();
        if (projectCategory != null) {
            bean.setProjectCategory_(projectCategory.getId());
            bean.setProjectCategoryText(projectCategory.getOptionName());
        }

        bean.setProjectAuditStatus(entity.getProjectAuditStatus());
        bean.setBiddingAgent(entity.getBiddingAgent());

        Person projectTracker = entity.getProjectTracker();
        if (projectTracker != null) {
            bean.setProjectTracker_(projectTracker.getId());
            bean.setProjectTrackerText(projectTracker.getPersonName());
        }

        bean.setProjectTrackerName(entity.getProjectTrackerName());

        DataOption finalClient = entity.getFinalClient();
        if (finalClient != null) {
            bean.setFinalClient_(finalClient.getId());
            bean.setFinalClientText(finalClient.getOptionName());
        }

        DataOption implementProjectDept = entity.getImplementProjectDept();
        if (implementProjectDept != null) {
            bean.setImplementProjectDept_(implementProjectDept.getId());
            bean.setImplementProjectDeptText(implementProjectDept.getOptionName());
        }

        Person operator = entity.getOperator();
        if (operator != null) {
            bean.setOperator_(operator.getId());
            bean.setOperatorText(operator.getPersonName());
        }

        bean.setOperatorName(entity.getOperatorName());
        bean.setPoNum(entity.getPoNum());

        DataOption signSubject = entity.getSignSubject();
        if (signSubject != null) {
            bean.setSignSubject_(signSubject.getId());
            bean.setSignSubjectText(signSubject.getOptionName());
        }

        DataOption region = entity.getRegion();
        if (region != null) {
            bean.setRegion_(region.getId());
            bean.setRegionText(region.getOptionName());
        }

        Dept projectDept = entity.getProjectDept();
        if (projectDept != null) {
            bean.setProjectDept_(projectDept.getId());
            bean.setProjectDeptText(projectDept.getDeptName());
        }

        bean.setCompanyCode(entity.getCompanyCode());

        RelatedUnit contractUnit = entity.getContractUnit();
        if (contractUnit != null) {
            bean.setContractUnit_(contractUnit.getUnitName());
            bean.setContractUnitText(contractUnit.getUnitName());
        }

        bean.setQualificationNum(entity.getQualificationNum());

        DataOption professionType = entity.getProfessionType();
        if (professionType != null) {
            bean.setProfessionType_(professionType.getId());
            bean.setProfessionTypeText(professionType.getOptionName());
        }

        bean.setPjmReq(entity.getPjmReq());
        bean.setTechLeaderReq(entity.getTechLeaderReq());
        bean.setProjectDesc(entity.getProjectDesc());
        bean.setInfoSource(entity.getInfoSource());
        bean.setOtherReq(entity.getOtherReq());
        bean.setRemark(entity.getRemark());
        bean.setChangedContent(entity.getChangedContent());

        DataOption qualificationReq = entity.getQualificationReq();
        if (qualificationReq != null) {
            bean.setQualificationReq_(qualificationReq.getId());
            bean.setQualificationReqText(qualificationReq.getOptionName());
        }

        bean.setQualificationGrade(entity.getQualificationGrade());

        DataOption contractMethod = entity.getContractMethod();
        if (contractMethod != null) {
            bean.setContractMethod_(contractMethod.getId());
            bean.setContractMethodText(contractMethod.getOptionName());
        }

        DataOption paymentMethod = entity.getPaymentMethod();
        if (paymentMethod != null) {
            bean.setPaymentMethod_(paymentMethod.getId());
            bean.setPaymentMethodText(paymentMethod.getOptionName());
        }

        DataOption biddingMethod = entity.getBiddingMethod();
        if (biddingMethod != null) {
            bean.setBiddingMethod_(biddingMethod.getId());
            bean.setBiddingMethodText(biddingMethod.getOptionName());
        }

        DataOption settlementMethod = entity.getSettlementMethod();
        if (settlementMethod != null) {
            bean.setSettlementMethod_(settlementMethod.getId());
            bean.setSettlementMethodText(settlementMethod.getOptionName());
        }

        DataOption budgetMethod = entity.getBudgetMethod();
        if (budgetMethod != null) {
            bean.setBudgetMethod_(budgetMethod.getId());
            bean.setBudgetMethodText(budgetMethod.getOptionName());
        }

        DataOption importance = entity.getImportance();
        if (importance != null) {
            bean.setImportance_(importance.getId());
            bean.setImportanceText(importance.getOptionName());
        }


        Person projectManager = entity.getProjectManager();
        if (projectManager != null) {
            bean.setProjectManager_(projectManager.getId());
            bean.setProjectManagerText(projectManager.getPersonName());
        }

        bean.setProjectManagerName(entity.getProjectManagerName());
        bean.setProjectManagerMobile(entity.getProjectManagerMobile());

        Person mainLeader = entity.getMainLeader();
        if (mainLeader != null) {
            bean.setMainLeader_(mainLeader.getId());
            bean.setMainLeaderText(mainLeader.getPersonName());
        }
        bean.setMainLeaderName(entity.getMainLeaderName());
        bean.setMainLeaderMobile(entity.getMainLeaderMobile());

        Person businessManager = entity.getBusinessManager();
        if (businessManager != null) {
            bean.setBusinessManager_(businessManager.getId());
            bean.setBusinessManagerText(businessManager.getPersonName());
        }

        bean.setBusinessManagerName(entity.getBusinessManagerName());

        DataOption projectClass = entity.getProjectClass();
        if (projectClass != null) {
            bean.setProjectClass_(projectClass.getId());
            bean.setProjectClassText(projectClass.getOptionName());
        }

        bean.setProjectClassGrade(entity.getProjectClassGrade());
        bean.setProjectSize(entity.getProjectSize());
        bean.setProjectSizeGrade(entity.getProjectSizeGrade());
        bean.setFundSource(entity.getFundSource());
        bean.setFundImplementStatus(entity.getFundImplementStatus());
        bean.setBuildingArea(entity.getBuildingArea());
        bean.setFloorArea(entity.getFloorArea());
        bean.setUndergroundArea(entity.getUndergroundArea());

        Person reader = entity.getReader();
        if (reader != null) {
            bean.setReader_(reader.getId());
            bean.setReaderText(reader.getPersonName());
        }

        bean.setReaderName(entity.getReaderName());
        bean.setGreenArea(entity.getGreenArea());
        bean.setParkArea(entity.getParkArea());
        bean.setBidDeposit(entity.getBidDeposit());
        bean.setContractDeposit(entity.getContractDeposit());
        bean.setMaterialDeposit(entity.getMaterialDeposit());
        bean.setOtherDesc(entity.getOtherDesc());
        bean.setManagementDeposit(entity.getManagementDeposit());
        bean.setMigrantWorkerSalaryDeposit(entity.getMigrantWorkerSalaryDeposit());
        bean.setManagementFeeRate(entity.getManagementFeeRate());
        bean.setPerformanceDeposit(entity.getPerformanceDeposit());
        bean.setOtherDeposit(entity.getOtherDeposit());
        bean.setTaxRate(entity.getTaxRate());
        bean.setCostFeeRate(entity.getCostFeeRate());
        bean.setQualityDepositRate(entity.getQualityDepositRate());
        bean.setCheckedDepositDeductRate(entity.getCheckedDepositDeductRate());
        bean.setRiskDepositDeductRate(entity.getRiskDepositDeductRate());
        bean.setSafeProductionFee(entity.getSafeProductionFee());
        bean.setBidSecurity(entity.getBidSecurity());
        bean.setInvestor(entity.getInfoSource());
        bean.setInvestorLeader(entity.getInvestorLeader());
        bean.setInvestorContact(entity.getInvestorContact());
        bean.setDesigner(entity.getDesigner());
        bean.setDesignerLeader(entity.getDesignerLeader());
        bean.setDesignerContact(entity.getDesignerContact());
        bean.setSupervisor(entity.getSupervisor());
        bean.setSupervisorLeader(entity.getSupervisorLeader());
        bean.setSupervisorContact(entity.getSupervisorContact());
        bean.setBuilder(entity.getBuilder());
        bean.setBuilderLeader(entity.getBuilderLeader());
        bean.setBuilderContact(entity.getBuilderContact());
        bean.setContractor(entity.getContractor());
        bean.setContractorLeader(entity.getContractorLeader());
        bean.setContractorContact(entity.getConstructorContact());
        bean.setAssociates(entity.getAssociates());
        bean.setReferee(entity.getReferee());
        bean.setSubCompany(entity.getSubCompany());

        return bean;
    }

    @Override
    public BiddingInformationApproval toEntity(BiddingInformationApprovalBean bean,
            BiddingInformationApproval oldEntity, AbstractSessionUserBean user, Date now) {

        BiddingInformationApproval entity = oldEntity;
        if (entity == null) { // 表示新建
            entity = new BiddingInformationApproval();

            entity.setCreator(user.getSessionUserId());
            entity.setcDatetime(now);

        } else { // 表示修改

            entity.setModifier(user.getSessionUserId());
        }

        bean.setId(entity.getId()); // ID必须赋值
        entity.setmDatetime(now);

        if (!StringUtils.isEmpty(bean.getApplicant_())) {
            Person applicant = new Person();
            applicant.setId(bean.getApplicant_());
            entity.setApplicant(applicant);
           
        }

        entity.setApplicantName(bean.getApplicantName());
        entity.setApplicantDept(bean.getApplicantDept());
        entity.setApplicantPost(bean.getApplicantPost());
        entity.setApplicantMobile(bean.getApplicantMobile());

        if (!StringUtils.isEmpty(bean.getConstructor_())) {
            RelatedUnit constructor = new RelatedUnit();
            constructor.setId(bean.getConstructor_());
            entity.setConstructor(constructor);
        }

        entity.setConstructorName(bean.getConstructorName());
        entity.setConstructorContact(bean.getConstructorContact());
        entity.setConstructorContactMobile(bean.getConstructorContactMobile());
        entity.setConstructorAddress(bean.getConstructorAddress());
        entity.setConstructorCode(bean.getConstructorCode());

        if (!StringUtils.isEmpty(bean.getConstructorProjectDept_())) {
            RelatedUnit constructorProjectDept = new RelatedUnit();
            constructorProjectDept.setId(bean.getConstructorProjectDept_());
            entity.setConstructorProjectDept(constructorProjectDept);
        }

        entity.setConstructorIndustry(bean.getConstructorIndustry());
        entity.setConstructorProjectNum(bean.getConstructorProjectNum());

        entity.setProjectNum(bean.getProjectNum());
        entity.setGovernmentProjectNum(bean.getGovernmentProjectNum());
        entity.setProjectName(bean.getProjectName());

        if (!StringUtils.isEmpty(bean.getProjectStartDate())) {
            try {
                entity.setProjectStartDate(
                        DateUtils.string2Date(bean.getProjectStartDate(), Constants.DATE_FORMAT));
            } catch (ParseException e) {
                // do nothing
            }
        }

        if (!StringUtils.isEmpty(bean.getProjectEndDate())) {
            try {
                entity.setProjectEndDate(
                        DateUtils.string2Date(bean.getProjectEndDate(), Constants.DATE_FORMAT));
            } catch (ParseException e) {
                // do nothing
            }
        }

        if (!StringUtils.isEmpty(bean.getProvince_())) {
            Province province = new Province();
            province.setId(bean.getProvince_());
            entity.setProvince(province);
        }

        if (!StringUtils.isEmpty(bean.getCity_())) {
            City city = new City();
            city.setId(bean.getCity_());
            entity.setCity(city);
        }

        entity.setProjectAddress(bean.getProjectAddress());
        entity.setProjectDuration(bean.getProjectDuration());
        entity.setEffortEstimate(bean.getEffortEstimate());
        entity.setProjectCost(bean.getProjectCost());

        if (!StringUtils.isEmpty(bean.getCurrency_())) {
            DataOption currency = new DataOption();
            currency.setId(bean.getCurrency_());
            entity.setCurrency(currency);
        }

        entity.setExchangeRate(bean.getExchangeRate());
        entity.setProjectCostRmb(bean.getProjectCostRmb());
        entity.setEstimateProfitRate(bean.getEstimateProfitRate());
        entity.setEstimateFieldCostRate(bean.getEstimateFieldCostRate());
        entity.setIsSolution(bean.getIsSolution());

        if (!StringUtils.isEmpty(bean.getProjectStatus_())) {
            DataOption projectStatus = new DataOption();
            projectStatus.setId(bean.getProjectStatus_());
            entity.setProjectStatus(projectStatus);
        } else {
            DataOption projectStatus = new DataOption();
            projectStatus.setId("PROJECT_STATUS_0"); // 新建后进入"建议"状态
            entity.setProjectStatus(projectStatus);
        }

        if (!StringUtils.isEmpty(bean.getProjectType_())) {
            DataOption projectType = new DataOption();
            projectType.setId(bean.getProjectType_());
            entity.setProjectType(projectType);
        }

        entity.setProfessionalCost(bean.getProfessionalCost());

        if (!StringUtils.isEmpty(bean.getQualityGrade_())) {
            DataOption qualityGrade = new DataOption();
            qualityGrade.setId(bean.getQualityGrade_());
            entity.setQualityGrade(qualityGrade);
        }

        if (!StringUtils.isEmpty(bean.getProjectCategory_())) {
            DataOption projectCategory = new DataOption();
            projectCategory.setId(bean.getProjectCategory_());
            entity.setProjectCategory(projectCategory);
        }

        entity.setProjectAuditStatus(bean.getProjectAuditStatus());
        entity.setBiddingAgent(bean.getBiddingAgent());

        if (!StringUtils.isEmpty(bean.getProjectTracker_())) {
            Person projectTracker = new Person();
            projectTracker.setId(bean.getProjectTracker_());
            entity.setProjectTracker(projectTracker);
        }

        entity.setProjectTrackerName(bean.getProjectTrackerName());

        if (!StringUtils.isEmpty(bean.getFinalClient_())) {
            DataOption finalClient = new DataOption();
            finalClient.setId(bean.getFinalClient_());
            entity.setFinalClient(finalClient);
        }

        if (!StringUtils.isEmpty(bean.getImplementProjectDept_())) {
            DataOption implementProjectDept = new DataOption();
            implementProjectDept.setId(bean.getImplementProjectDept_());
            entity.setImplementProjectDept(implementProjectDept);
        }

        if (!StringUtils.isEmpty(bean.getOperator_())) {
            Person operator = new Person();
            operator.setId(bean.getOperator_());
            entity.setOperator(operator);
        }

        entity.setOperatorName(bean.getOperatorName());
        entity.setPoNum(bean.getPoNum());

        if (!StringUtils.isEmpty(bean.getSignSubject_())) {
            DataOption signSubject = new DataOption();
            signSubject.setId(bean.getSignSubject_());
            entity.setSignSubject(signSubject);
        }

        if (!StringUtils.isEmpty(bean.getRegion_())) {
            DataOption region = new DataOption();
            region.setId(bean.getRegion_());
            entity.setRegion(region);
        }

        if (!StringUtils.isEmpty(bean.getProjectDept_())) {
            Dept projectDept = new Dept();
            projectDept.setId(bean.getProjectDept_());
            entity.setProjectDept(projectDept);
        }

        entity.setCompanyCode(bean.getCompanyCode());

        if (!StringUtils.isEmpty(bean.getContractUnit_())) {
            RelatedUnit contractUnit = new RelatedUnit();
            contractUnit.setId(bean.getContractUnit_());
            entity.setContractUnit(contractUnit);
        }

        entity.setQualificationNum(bean.getQualificationNum());

        if (!StringUtils.isEmpty(bean.getProfessionType_())) {
            DataOption professionType = new DataOption();
            professionType.setId(bean.getProfessionType_());
            entity.setProfessionType(professionType);
        }

        entity.setPjmReq(bean.getPjmReq());
        entity.setTechLeaderReq(bean.getTechLeaderReq());
        entity.setProjectDesc(bean.getProjectDesc());
        entity.setInfoSource(bean.getInfoSource());
        entity.setOtherReq(bean.getOtherReq());
        entity.setRemark(bean.getRemark());
        entity.setChangedContent(bean.getChangedContent());

        if (!StringUtils.isEmpty(bean.getQualificationReq_())) {
            DataOption qualificationReq = new DataOption();
            qualificationReq.setId(bean.getQualificationReq_());
            entity.setQualificationReq(qualificationReq);
        }

        entity.setQualificationGrade(bean.getQualificationGrade());

        if (!StringUtils.isEmpty(bean.getContractMethod_())) {
            DataOption contractMethod = new DataOption();
            contractMethod.setId(bean.getContractMethod_());
            entity.setContractMethod(contractMethod);
        }

        if (!StringUtils.isEmpty(bean.getPaymentMethod_())) {
            DataOption paymentMethod = new DataOption();
            paymentMethod.setId(bean.getPaymentMethod_());
            entity.setPaymentMethod(paymentMethod);
        }

        if (!StringUtils.isEmpty(bean.getBiddingMethod_())) {
            DataOption biddingMethod = new DataOption();
            biddingMethod.setId(bean.getBiddingMethod_());
            entity.setBiddingMethod(biddingMethod);
        }

        if (!StringUtils.isEmpty(bean.getSettlementMethod_())) {
            DataOption settlementMethod = new DataOption();
            settlementMethod.setId(bean.getSettlementMethod_());
            entity.setSettlementMethod(settlementMethod);
        }

        if (!StringUtils.isEmpty(bean.getBudgetMethod_())) {
            DataOption budgetMethod = new DataOption();
            budgetMethod.setId(bean.getBudgetMethod_());
            entity.setBudgetMethod(budgetMethod);
        }

        if (!StringUtils.isEmpty(bean.getImportance_())) {
            DataOption importance = new DataOption();
            importance.setId(bean.getImportance_());
            entity.setImportance(importance);
        }

        if (!StringUtils.isEmpty(bean.getProjectManager_())) {
            Person projectManager = new Person();
            projectManager.setId(bean.getProjectManager_());
            entity.setProjectManager(projectManager);
        }

        entity.setProjectManagerName(bean.getProjectManagerName());
        entity.setProjectManagerMobile(bean.getProjectManagerMobile());

        if (!StringUtils.isEmpty(bean.getMainLeader_())) {
            Person mainLeader = new Person();
            mainLeader.setId(bean.getMainLeader_());
            entity.setMainLeader(mainLeader);
        }
        entity.setMainLeaderName(bean.getMainLeaderName());
        entity.setMainLeaderMobile(bean.getMainLeaderMobile());

        if (!StringUtils.isEmpty(bean.getBusinessManager_())) {
            Person businessManager = new Person();
            businessManager.setId(bean.getBusinessManager_());
            entity.setBusinessManager(businessManager);
        }
        entity.setBusinessManagerName(bean.getBusinessManagerName());

        if (!StringUtils.isEmpty(bean.getProjectClass_())) {
            DataOption projectClass = new DataOption();
            projectClass.setId(bean.getProjectClass_());
            entity.setProjectClass(projectClass);
        }

        entity.setProjectClassGrade(bean.getProjectClassGrade());
        entity.setProjectSize(bean.getProjectSize());
        entity.setProjectSizeGrade(bean.getProjectSizeGrade());
        entity.setFundSource(bean.getFundSource());
        entity.setFundImplementStatus(bean.getFundImplementStatus());
        entity.setBuildingArea(bean.getBuildingArea());
        entity.setFloorArea(bean.getFloorArea());
        entity.setUndergroundArea(bean.getUndergroundArea());

        if (!StringUtils.isEmpty(bean.getReader_())) {
            Person reader = new Person();
            reader.setId(bean.getReader_());
            entity.setReader(reader);
        }

        entity.setReaderName(bean.getReaderName());
        entity.setGreenArea(bean.getGreenArea());
        entity.setParkArea(bean.getParkArea());
        entity.setBidDeposit(bean.getBidDeposit());
        entity.setContractDeposit(bean.getContractDeposit());
        entity.setMaterialDeposit(bean.getMaterialDeposit());
        entity.setOtherDesc(bean.getOtherDesc());
        entity.setManagementDeposit(bean.getManagementDeposit());
        entity.setMigrantWorkerSalaryDeposit(bean.getMigrantWorkerSalaryDeposit());
        entity.setManagementFeeRate(bean.getManagementFeeRate());
        entity.setPerformanceDeposit(bean.getPerformanceDeposit());
        entity.setOtherDeposit(bean.getOtherDeposit());
        entity.setTaxRate(bean.getTaxRate());
        entity.setCostFeeRate(bean.getCostFeeRate());
        entity.setQualityDepositRate(bean.getQualityDepositRate());
        entity.setCheckedDepositDeductRate(bean.getCheckedDepositDeductRate());
        entity.setRiskDepositDeductRate(bean.getRiskDepositDeductRate());
        entity.setSafeProductionFee(bean.getSafeProductionFee());
        entity.setBidSecurity(bean.getBidSecurity());
        entity.setInvestor(bean.getInvestor());
        entity.setInvestorLeader(bean.getInvestorLeader());
        entity.setInvestorContact(bean.getInvestorContact());
        entity.setDesigner(bean.getDesigner());
        entity.setDesignerLeader(bean.getDesignerLeader());
        entity.setDesignerContact(bean.getDesignerContact());
        entity.setSupervisor(bean.getSupervisor());
        entity.setSupervisorLeader(bean.getSupervisorLeader());
        entity.setSupervisorContact(bean.getSupervisorContact());
        entity.setBuilder(bean.getBuilder());
        entity.setBuilderLeader(bean.getBuilderLeader());
        entity.setBuilderContact(bean.getBuilderContact());
        entity.setContractor(bean.getContractor());
        entity.setContractorLeader(bean.getContractorLeader());
        entity.setContractorContact(bean.getContractorContact());
        entity.setAssociates(bean.getAssociates());
        entity.setReferee(bean.getReferee());
        entity.setSubCompany(bean.getSubCompany());

        return entity;
    }

}
