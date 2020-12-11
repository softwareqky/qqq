package project.edge.domain.entity;

import org.hibernate.annotations.GenericGenerator;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;


/**
 * 项目变更。
 *
 */
@Entity
@Table(name = "t_project_change")
public class ProjectChange implements Serializable, Cloneable {

    private static final long serialVersionUID = -8552316998932285021L;

    private String id = UUID.randomUUID().toString();

    private Project project;
    private int projectSource;
    private Site site;
    private Segment segment;
    private DataOption projectKind;
    private Person applicant;
    private String applicantName;
    private String applicantDept;
    private String applicantPost;
    private String applicantMobile;
    private RelatedUnit constructor;
    private String constructorName;
    private String constructorContact;
    private String constructorContactMobile;
    private String constructorAddress;
    private String constructorCode;
    private RelatedUnit constructorProjectDept;
    private String constructorIndustry;
    private String constructorProjectNum;
    private String projectNum;
    private String governmentProjectNum;
    private String projectName;
    private Date projectStartDate;
    private Date projectEndDate;
    private Province province;
    private City city;
    private ProjectSet projectSet;
    private String projectAddress;
    private String projectDuration;
    private String effortEstimate;
    private Double projectCost;
    private DataOption currency;
    private Double exchangeRate;
    private Double projectCostRmb;
    private Double estimateProfitRate;
    private Double estimateFieldCostRate;
    private Short isSolution;
    private DataOption projectStatus;
    private DataOption projectType;
    private Double professionalCost;
    private DataOption qualityGrade;
    private DataOption projectCategory;
    private String projectAuditStatus;
    private String biddingAgent;
    private Person projectTracker;
    private String projectTrackerName;
    private DataOption finalClient;
    private DataOption implementProjectDept;
    private Person operator;
    private String operatorName;
    private String poNum;
    private DataOption signSubject;
    private DataOption region;
    private Dept projectDept;
    private String companyCode;
    private RelatedUnit contractUnit;
    private String qualificationNum;
    private DataOption professionType;
    private String pjmReq;
    private String techLeaderReq;
    private String projectDesc;
    private String infoSource;
    private String otherReq;
    private String remark;
    private String changedContent;
    private DataOption qualificationReq;
    private String qualificationGrade;
    private DataOption contractMethod;
    private DataOption paymentMethod;
    private DataOption biddingMethod;
    private DataOption settlementMethod;
    private DataOption budgetMethod;
    private DataOption importance;
    private Person projectManager;
    private String projectManagerName;
    private String projectManagerMobile;
    private Person mainLeader;
    private String mainLeaderName;
    private String mainLeaderMobile;
    private Person businessManager;
    private String businessManagerName;
    private DataOption projectClass;
    private String projectClassGrade;
    private String projectSize;
    private String projectSizeGrade;
    private String fundSource;
    private String fundImplementStatus;
    private String buildingArea;
    private String floorArea;
    private String undergroundArea;
    private Person reader;
    private String readerName;
    private String greenArea;
    private String parkArea;
    private Double bidDeposit;
    private Double contractDeposit;
    private Double materialDeposit;
    private String otherDesc;
    private Double managementDeposit;
    private Double migrantWorkerSalaryDeposit;
    private Double managementFeeRate;
    private Double performanceDeposit;
    private Double otherDeposit;
    private Double taxRate;
    private Double costFeeRate;
    private Double qualityDepositRate;
    private Double checkedDepositDeductRate;
    private Double riskDepositDeductRate;
    private Double safeProductionFee;
    private Double bidSecurity;
    private String investor;
    private String investorLeader;
    private String investorContact;
    private String designer;
    private String designerLeader;
    private String designerContact;
    private String supervisor;
    private String supervisorLeader;
    private String supervisorContact;
    private String builder;
    private String builderLeader;
    private String builderContact;
    private String contractor;
    private String contractorLeader;
    private String contractorContact;
    private String associates;
    private String referee;
    private String subCompany;

    private String changeReason;
    private String changeRemark;
    private Date flowStartDate;
    private Date flowEndDate;
    private int flowStatus;

    private short isDeleted;
    private String creator;
    private Date cDatetime;
    private String modifier;
    private Date mDatetime;

    private Set<ProjectChangeAttachment> projectChangeAttachments = new HashSet<>(0);
    private List<ProjectChangeAttachment> attachmentsToDelete = new ArrayList<>();

    @Override
    public ProjectChange clone() {
        ProjectChange p = null;
        try {
            p = (ProjectChange) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return p;
    }

    @GenericGenerator(name = "generator", strategy = "assigned")
    @Id
    @GeneratedValue(generator = "generator")
    @Column(name = "id", unique = true, nullable = false, length = 36)
    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id", nullable = false)
    public Project getProject() {
        return this.project;
    }


    public void setProject(Project project) {
        this.project = project;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "site_id", nullable = true)
    public Site getSite() {
        return this.site;
    }


    public void setSite(Site site) {
        this.site = site;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "segment_id", nullable = true)
    public Segment getSegment() {
        return this.segment;
    }


    public void setSegment(Segment segment) {
        this.segment = segment;
    }

    @Column(name = "project_source", nullable = false)
    public int getProjectSource() {
        return this.projectSource;
    }


    public void setProjectSource(int projectSource) {
        this.projectSource = projectSource;
    }


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_kind", nullable = true)
    public DataOption getProjectKind() {
        return this.projectKind;
    }


    public void setProjectKind(DataOption projectKind) {
        this.projectKind = projectKind;
    }


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "applicant", nullable = true)
    public Person getApplicant() {
        return this.applicant;
    }


    public void setApplicant(Person applicant) {
        this.applicant = applicant;
    }

    @Column(name = "applicant_name", nullable = false, length = 20)
    public String getApplicantName() {
        return this.applicantName;
    }


    public void setApplicantName(String applicantName) {
        this.applicantName = applicantName;
    }

    @Column(name = "applicant_dept", nullable = true, length = 50)
    public String getApplicantDept() {
        return this.applicantDept;
    }


    public void setApplicantDept(String applicantDept) {
        this.applicantDept = applicantDept;
    }

    @Column(name = "applicant_post", nullable = true, length = 50)
    public String getApplicantPost() {
        return this.applicantPost;
    }


    public void setApplicantPost(String applicantPost) {
        this.applicantPost = applicantPost;
    }

    @Column(name = "applicant_mobile", nullable = true, length = 50)
    public String getApplicantMobile() {
        return this.applicantMobile;
    }


    public void setApplicantMobile(String applicantMobile) {
        this.applicantMobile = applicantMobile;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "constructor", nullable = true)
    public RelatedUnit getConstructor() {
        return this.constructor;
    }


    public void setConstructor(RelatedUnit constructor) {
        this.constructor = constructor;
    }

    @Column(name = "constructor_name", nullable = true, length = 50)
    public String getConstructorName() {
        return this.constructorName;
    }


    public void setConstructorName(String constructorName) {
        this.constructorName = constructorName;
    }

    @Column(name = "constructor_contact", nullable = true, length = 50)
    public String getConstructorContact() {
        return this.constructorContact;
    }


    public void setConstructorContact(String constructorContact) {
        this.constructorContact = constructorContact;
    }

    @Column(name = "constructor_contact_mobile", nullable = true, length = 50)
    public String getConstructorContactMobile() {
        return this.constructorContactMobile;
    }


    public void setConstructorContactMobile(String constructorContactMobile) {
        this.constructorContactMobile = constructorContactMobile;
    }

    @Column(name = "constructor_address", nullable = true, length = 50)
    public String getConstructorAddress() {
        return this.constructorAddress;
    }


    public void setConstructorAddress(String constructorAddress) {
        this.constructorAddress = constructorAddress;
    }

    @Column(name = "constructor_code", nullable = true, length = 50)
    public String getConstructorCode() {
        return this.constructorCode;
    }


    public void setConstructorCode(String constructorCode) {
        this.constructorCode = constructorCode;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "constructor_project_dept", nullable = true)
    public RelatedUnit getConstructorProjectDept() {
        return this.constructorProjectDept;
    }


    public void setConstructorProjectDept(RelatedUnit constructorProjectDept) {
        this.constructorProjectDept = constructorProjectDept;
    }

    @Column(name = "constructor_industry", nullable = true, length = 50)
    public String getConstructorIndustry() {
        return this.constructorIndustry;
    }


    public void setConstructorIndustry(String constructorIndustry) {
        this.constructorIndustry = constructorIndustry;
    }

    @Column(name = "constructor_project_num", nullable = true, length = 50)
    public String getConstructorProjectNum() {
        return this.constructorProjectNum;
    }


    public void setConstructorProjectNum(String constructorProjectNum) {
        this.constructorProjectNum = constructorProjectNum;
    }

    @Column(name = "project_num", nullable = false, length = 50)
    public String getProjectNum() {
        return this.projectNum;
    }


    public void setProjectNum(String projectNum) {
        this.projectNum = projectNum;
    }

    @Column(name = "government_project_num", nullable = true, length = 50)
    public String getGovernmentProjectNum() {
        return this.governmentProjectNum;
    }


    public void setGovernmentProjectNum(String governmentProjectNum) {
        this.governmentProjectNum = governmentProjectNum;
    }

    @Column(name = "project_name", nullable = false, length = 50)
    public String getProjectName() {
        return this.projectName;
    }


    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "project_start_date", nullable = true, length = 29)
    public Date getProjectStartDate() {
        return this.projectStartDate;
    }


    public void setProjectStartDate(Date projectStartDate) {
        this.projectStartDate = projectStartDate;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "project_end_date", nullable = true, length = 29)
    public Date getProjectEndDate() {
        return this.projectEndDate;
    }


    public void setProjectEndDate(Date projectEndDate) {
        this.projectEndDate = projectEndDate;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "province_id", nullable = true)
    public Province getProvince() {
        return this.province;
    }


    public void setProvince(Province province) {
        this.province = province;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "city_id", nullable = true)
    public City getCity() {
        return this.city;
    }


    public void setCity(City city) {
        this.city = city;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_set_id", nullable = true)
    public ProjectSet getProjectSet() {
        return this.projectSet;
    }

    public void setProjectSet(ProjectSet projectSet) {
        this.projectSet = projectSet;
    }

    @Column(name = "project_address", nullable = true, length = 50)
    public String getProjectAddress() {
        return this.projectAddress;
    }


    public void setProjectAddress(String projectAddress) {
        this.projectAddress = projectAddress;
    }

    @Column(name = "project_duration", nullable = true, length = 50)
    public String getProjectDuration() {
        return this.projectDuration;
    }


    public void setProjectDuration(String projectDuration) {
        this.projectDuration = projectDuration;
    }

    @Column(name = "effort_estimate", nullable = true, length = 50)
    public String getEffortEstimate() {
        return this.effortEstimate;
    }


    public void setEffortEstimate(String effortEstimate) {
        this.effortEstimate = effortEstimate;
    }

    @Column(name = "project_cost", nullable = true)
    public Double getProjectCost() {
        return this.projectCost;
    }


    public void setProjectCost(Double projectCost) {
        this.projectCost = projectCost;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "currency", nullable = true)
    public DataOption getCurrency() {
        return this.currency;
    }


    public void setCurrency(DataOption currency) {
        this.currency = currency;
    }

    @Column(name = "exchange_rate", nullable = true)
    public Double getExchangeRate() {
        return this.exchangeRate;
    }


    public void setExchangeRate(Double exchangeRate) {
        this.exchangeRate = exchangeRate;
    }

    @Column(name = "project_cost_rmb", nullable = true)
    public Double getProjectCostRmb() {
        return this.projectCostRmb;
    }


    public void setProjectCostRmb(Double projectCostRmb) {
        this.projectCostRmb = projectCostRmb;
    }

    @Column(name = "estimate_profit_rate", nullable = true)
    public Double getEstimateProfitRate() {
        return this.estimateProfitRate;
    }


    public void setEstimateProfitRate(Double estimateProfitRate) {
        this.estimateProfitRate = estimateProfitRate;
    }

    @Column(name = "estimate_field_cost_rate", nullable = true)
    public Double getEstimateFieldCostRate() {
        return this.estimateFieldCostRate;
    }


    public void setEstimateFieldCostRate(Double estimateFieldCostRate) {
        this.estimateFieldCostRate = estimateFieldCostRate;
    }

    @Column(name = "is_solution", nullable = true)
    public Short getIsSolution() {
        return this.isSolution;
    }


    public void setIsSolution(Short isSolution) {
        this.isSolution = isSolution;
    }



    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_status", nullable = true)
    public DataOption getProjectStatus() {
        return this.projectStatus;
    }


    public void setProjectStatus(DataOption projectStatus) {
        this.projectStatus = projectStatus;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_type", nullable = true)
    public DataOption getProjectType() {
        return this.projectType;
    }


    public void setProjectType(DataOption projectType) {
        this.projectType = projectType;
    }

    @Column(name = "professional_cost", nullable = true)
    public Double getProfessionalCost() {
        return this.professionalCost;
    }


    public void setProfessionalCost(Double professionalCost) {
        this.professionalCost = professionalCost;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "quality_grade", nullable = true)
    public DataOption getQualityGrade() {
        return this.qualityGrade;
    }


    public void setQualityGrade(DataOption qualityGrade) {
        this.qualityGrade = qualityGrade;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_category", nullable = true)
    public DataOption getProjectCategory() {
        return this.projectCategory;
    }


    public void setProjectCategory(DataOption projectCategory) {
        this.projectCategory = projectCategory;
    }

    @Column(name = "project_audit_status", nullable = true, length = 50)
    public String getProjectAuditStatus() {
        return this.projectAuditStatus;
    }


    public void setProjectAuditStatus(String projectAuditStatus) {
        this.projectAuditStatus = projectAuditStatus;
    }

    @Column(name = "bidding_agent", nullable = true, length = 50)
    public String getBiddingAgent() {
        return this.biddingAgent;
    }


    public void setBiddingAgent(String biddingAgent) {
        this.biddingAgent = biddingAgent;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_tracker", nullable = true)
    public Person getProjectTracker() {
        return this.projectTracker;
    }


    public void setProjectTracker(Person projectTracker) {
        this.projectTracker = projectTracker;
    }

    @Column(name = "project_tracker_name", nullable = true, length = 20)
    public String getProjectTrackerName() {
        return this.projectTrackerName;
    }


    public void setProjectTrackerName(String projectTrackerName) {
        this.projectTrackerName = projectTrackerName;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "final_client", nullable = true)
    public DataOption getFinalClient() {
        return this.finalClient;
    }


    public void setFinalClient(DataOption finalClient) {
        this.finalClient = finalClient;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "implement_project_dept", nullable = true)
    public DataOption getImplementProjectDept() {
        return this.implementProjectDept;
    }


    public void setImplementProjectDept(DataOption implementProjectDept) {
        this.implementProjectDept = implementProjectDept;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "operator", nullable = true)
    public Person getOperator() {
        return this.operator;
    }


    public void setOperator(Person operator) {
        this.operator = operator;
    }

    @Column(name = "operator_name", nullable = true, length = 20)
    public String getOperatorName() {
        return this.operatorName;
    }


    public void setOperatorName(String operatorName) {
        this.operatorName = operatorName;
    }

    @Column(name = "po_num", nullable = true, length = 50)
    public String getPoNum() {
        return this.poNum;
    }


    public void setPoNum(String poNum) {
        this.poNum = poNum;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sign_subject", nullable = true)
    public DataOption getSignSubject() {
        return this.signSubject;
    }


    public void setSignSubject(DataOption signSubject) {
        this.signSubject = signSubject;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "region", nullable = true)
    public DataOption getRegion() {
        return this.region;
    }


    public void setRegion(DataOption region) {
        this.region = region;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_dept", nullable = true)
    public Dept getProjectDept() {
        return this.projectDept;
    }


    public void setProjectDept(Dept projectDept) {
        this.projectDept = projectDept;
    }

    @Column(name = "company_code", nullable = true, length = 50)
    public String getCompanyCode() {
        return this.companyCode;
    }


    public void setCompanyCode(String companyCode) {
        this.companyCode = companyCode;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "contract_unit", nullable = true)
    public RelatedUnit getContractUnit() {
        return this.contractUnit;
    }


    public void setContractUnit(RelatedUnit contractUnit) {
        this.contractUnit = contractUnit;
    }

    @Column(name = "qualification_num", nullable = true, length = 50)
    public String getQualificationNum() {
        return this.qualificationNum;
    }


    public void setQualificationNum(String qualificationNum) {
        this.qualificationNum = qualificationNum;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profession_type", nullable = true)
    public DataOption getProfessionType() {
        return this.professionType;
    }


    public void setProfessionType(DataOption professionType) {
        this.professionType = professionType;
    }

    @Column(name = "pjm_req", nullable = true, length = 1024)
    public String getPjmReq() {
        return this.pjmReq;
    }


    public void setPjmReq(String pjmReq) {
        this.pjmReq = pjmReq;
    }

    @Column(name = "tech_leader_req", nullable = true, length = 1024)
    public String getTechLeaderReq() {
        return this.techLeaderReq;
    }


    public void setTechLeaderReq(String techLeaderReq) {
        this.techLeaderReq = techLeaderReq;
    }

    @Column(name = "project_desc", nullable = true, length = 1024)
    public String getProjectDesc() {
        return this.projectDesc;
    }


    public void setProjectDesc(String projectDesc) {
        this.projectDesc = projectDesc;
    }

    @Column(name = "info_source", nullable = true, length = 1024)
    public String getInfoSource() {
        return this.infoSource;
    }


    public void setInfoSource(String infoSource) {
        this.infoSource = infoSource;
    }

    @Column(name = "other_req", nullable = true, length = 1024)
    public String getOtherReq() {
        return this.otherReq;
    }


    public void setOtherReq(String otherReq) {
        this.otherReq = otherReq;
    }

    @Column(name = "remark", nullable = true, length = 1024)
    public String getRemark() {
        return this.remark;
    }


    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Column(name = "changed_content", nullable = true, length = 1024)
    public String getChangedContent() {
        return this.changedContent;
    }


    public void setChangedContent(String changedContent) {
        this.changedContent = changedContent;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "qualification_req", nullable = true)
    public DataOption getQualificationReq() {
        return this.qualificationReq;
    }


    public void setQualificationReq(DataOption qualificationReq) {
        this.qualificationReq = qualificationReq;
    }

    @Column(name = "qualification_grade", nullable = true, length = 50)
    public String getQualificationGrade() {
        return this.qualificationGrade;
    }


    public void setQualificationGrade(String qualificationGrade) {
        this.qualificationGrade = qualificationGrade;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "contract_method", nullable = true)
    public DataOption getContractMethod() {
        return this.contractMethod;
    }


    public void setContractMethod(DataOption contractMethod) {
        this.contractMethod = contractMethod;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "payment_method", nullable = true)
    public DataOption getPaymentMethod() {
        return this.paymentMethod;
    }


    public void setPaymentMethod(DataOption paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bidding_method", nullable = true)
    public DataOption getBiddingMethod() {
        return this.biddingMethod;
    }


    public void setBiddingMethod(DataOption biddingMethod) {
        this.biddingMethod = biddingMethod;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "settlement_method", nullable = true)
    public DataOption getSettlementMethod() {
        return this.settlementMethod;
    }


    public void setSettlementMethod(DataOption settlementMethod) {
        this.settlementMethod = settlementMethod;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "budget_method", nullable = true)
    public DataOption getBudgetMethod() {
        return this.budgetMethod;
    }


    public void setBudgetMethod(DataOption budgetMethod) {
        this.budgetMethod = budgetMethod;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "importance", nullable = true)
    public DataOption getImportance() {
        return this.importance;
    }


    public void setImportance(DataOption importance) {
        this.importance = importance;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_manager", nullable = true)
    public Person getProjectManager() {
        return this.projectManager;
    }


    public void setProjectManager(Person projectManager) {
        this.projectManager = projectManager;
    }

    @Column(name = "project_manager_name", nullable = true, length = 20)
    public String getProjectManagerName() {
        return this.projectManagerName;
    }


    public void setProjectManagerName(String projectManagerName) {
        this.projectManagerName = projectManagerName;
    }

    @Column(name = "project_manager_mobile", nullable = true, length = 50)
    public String getProjectManagerMobile() {
        return this.projectManagerMobile;
    }


    public void setProjectManagerMobile(String projectManagerMobile) {
        this.projectManagerMobile = projectManagerMobile;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "main_leader", nullable = true)
    public Person getMainLeader() {
        return this.mainLeader;
    }


    public void setMainLeader(Person mainLeader) {
        this.mainLeader = mainLeader;
    }

    @Column(name = "main_leader_name", nullable = true, length = 20)
    public String getMainLeaderName() {
        return this.mainLeaderName;
    }


    public void setMainLeaderName(String mainLeaderName) {
        this.mainLeaderName = mainLeaderName;
    }

    @Column(name = "main_leader_mobile", nullable = true, length = 50)
    public String getMainLeaderMobile() {
        return this.mainLeaderMobile;
    }


    public void setMainLeaderMobile(String mainLeaderMobile) {
        this.mainLeaderMobile = mainLeaderMobile;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "business_manager", nullable = true)
    public Person getBusinessManager() {
        return this.businessManager;
    }


    public void setBusinessManager(Person businessManager) {
        this.businessManager = businessManager;
    }

    @Column(name = "business_manager_name", nullable = true, length = 20)
    public String getBusinessManagerName() {
        return this.businessManagerName;
    }


    public void setBusinessManagerName(String businessManagerName) {
        this.businessManagerName = businessManagerName;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_class", nullable = true)
    public DataOption getProjectClass() {
        return this.projectClass;
    }


    public void setProjectClass(DataOption projectClass) {
        this.projectClass = projectClass;
    }

    @Column(name = "project_class_grade", nullable = true, length = 50)
    public String getProjectClassGrade() {
        return this.projectClassGrade;
    }


    public void setProjectClassGrade(String projectClassGrade) {
        this.projectClassGrade = projectClassGrade;
    }

    @Column(name = "project_size", nullable = true, length = 50)
    public String getProjectSize() {
        return this.projectSize;
    }


    public void setProjectSize(String projectSize) {
        this.projectSize = projectSize;
    }

    @Column(name = "project_size_grade", nullable = true, length = 50)
    public String getProjectSizeGrade() {
        return this.projectSizeGrade;
    }


    public void setProjectSizeGrade(String projectSizeGrade) {
        this.projectSizeGrade = projectSizeGrade;
    }

    @Column(name = "fund_source", nullable = true, length = 50)
    public String getFundSource() {
        return this.fundSource;
    }


    public void setFundSource(String fundSource) {
        this.fundSource = fundSource;
    }

    @Column(name = "fund_implement_status", nullable = true, length = 50)
    public String getFundImplementStatus() {
        return this.fundImplementStatus;
    }


    public void setFundImplementStatus(String fundImplementStatus) {
        this.fundImplementStatus = fundImplementStatus;
    }

    @Column(name = "building_area", nullable = true, length = 50)
    public String getBuildingArea() {
        return this.buildingArea;
    }


    public void setBuildingArea(String buildingArea) {
        this.buildingArea = buildingArea;
    }

    @Column(name = "floor_area", nullable = true, length = 50)
    public String getFloorArea() {
        return this.floorArea;
    }


    public void setFloorArea(String floorArea) {
        this.floorArea = floorArea;
    }

    @Column(name = "underground_area", nullable = true, length = 50)
    public String getUndergroundArea() {
        return this.undergroundArea;
    }


    public void setUndergroundArea(String undergroundArea) {
        this.undergroundArea = undergroundArea;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reader", nullable = true)
    public Person getReader() {
        return this.reader;
    }


    public void setReader(Person reader) {
        this.reader = reader;
    }

    @Column(name = "reader_name", nullable = true, length = 20)
    public String getReaderName() {
        return this.readerName;
    }


    public void setReaderName(String readerName) {
        this.readerName = readerName;
    }

    @Column(name = "green_area", nullable = true, length = 50)
    public String getGreenArea() {
        return this.greenArea;
    }


    public void setGreenArea(String greenArea) {
        this.greenArea = greenArea;
    }

    @Column(name = "park_area", nullable = true, length = 50)
    public String getParkArea() {
        return this.parkArea;
    }


    public void setParkArea(String parkArea) {
        this.parkArea = parkArea;
    }

    @Column(name = "bid_deposit", nullable = true)
    public Double getBidDeposit() {
        return this.bidDeposit;
    }


    public void setBidDeposit(Double bidDeposit) {
        this.bidDeposit = bidDeposit;
    }

    @Column(name = "contract_deposit", nullable = true)
    public Double getContractDeposit() {
        return this.contractDeposit;
    }


    public void setContractDeposit(Double contractDeposit) {
        this.contractDeposit = contractDeposit;
    }

    @Column(name = "material_deposit", nullable = true)
    public Double getMaterialDeposit() {
        return this.materialDeposit;
    }


    public void setMaterialDeposit(Double materialDeposit) {
        this.materialDeposit = materialDeposit;
    }

    @Column(name = "other_desc", nullable = true, length = 1024)
    public String getOtherDesc() {
        return this.otherDesc;
    }


    public void setOtherDesc(String otherDesc) {
        this.otherDesc = otherDesc;
    }

    @Column(name = "management_deposit", nullable = true)
    public Double getManagementDeposit() {
        return this.managementDeposit;
    }


    public void setManagementDeposit(Double managementDeposit) {
        this.managementDeposit = managementDeposit;
    }

    @Column(name = "migrant_worker_salary_deposit", nullable = true)
    public Double getMigrantWorkerSalaryDeposit() {
        return this.migrantWorkerSalaryDeposit;
    }


    public void setMigrantWorkerSalaryDeposit(Double migrantWorkerSalaryDeposit) {
        this.migrantWorkerSalaryDeposit = migrantWorkerSalaryDeposit;
    }

    @Column(name = "management_fee_rate", nullable = true)
    public Double getManagementFeeRate() {
        return this.managementFeeRate;
    }


    public void setManagementFeeRate(Double managementFeeRate) {
        this.managementFeeRate = managementFeeRate;
    }

    @Column(name = "performance_deposit", nullable = true)
    public Double getPerformanceDeposit() {
        return this.performanceDeposit;
    }


    public void setPerformanceDeposit(Double performanceDeposit) {
        this.performanceDeposit = performanceDeposit;
    }

    @Column(name = "other_deposit", nullable = true)
    public Double getOtherDeposit() {
        return this.otherDeposit;
    }


    public void setOtherDeposit(Double otherDeposit) {
        this.otherDeposit = otherDeposit;
    }

    @Column(name = "tax_rate", nullable = true)
    public Double getTaxRate() {
        return this.taxRate;
    }


    public void setTaxRate(Double taxRate) {
        this.taxRate = taxRate;
    }

    @Column(name = "cost_fee_rate", nullable = true)
    public Double getCostFeeRate() {
        return this.costFeeRate;
    }


    public void setCostFeeRate(Double costFeeRate) {
        this.costFeeRate = costFeeRate;
    }

    @Column(name = "quality_deposit_rate", nullable = true)
    public Double getQualityDepositRate() {
        return this.qualityDepositRate;
    }


    public void setQualityDepositRate(Double qualityDepositRate) {
        this.qualityDepositRate = qualityDepositRate;
    }

    @Column(name = "checked_deposit_deduct_rate", nullable = true)
    public Double getCheckedDepositDeductRate() {
        return this.checkedDepositDeductRate;
    }


    public void setCheckedDepositDeductRate(Double checkedDepositDeductRate) {
        this.checkedDepositDeductRate = checkedDepositDeductRate;
    }

    @Column(name = "risk_deposit_deduct_rate", nullable = true)
    public Double getRiskDepositDeductRate() {
        return this.riskDepositDeductRate;
    }


    public void setRiskDepositDeductRate(Double riskDepositDeductRate) {
        this.riskDepositDeductRate = riskDepositDeductRate;
    }

    @Column(name = "safe_production_fee", nullable = true)
    public Double getSafeProductionFee() {
        return this.safeProductionFee;
    }


    public void setSafeProductionFee(Double safeProductionFee) {
        this.safeProductionFee = safeProductionFee;
    }

    @Column(name = "bid_security", nullable = true)
    public Double getBidSecurity() {
        return this.bidSecurity;
    }


    public void setBidSecurity(Double bidSecurity) {
        this.bidSecurity = bidSecurity;
    }

    @Column(name = "investor", nullable = true, length = 50)
    public String getInvestor() {
        return this.investor;
    }


    public void setInvestor(String investor) {
        this.investor = investor;
    }

    @Column(name = "investor_leader", nullable = true, length = 50)
    public String getInvestorLeader() {
        return this.investorLeader;
    }


    public void setInvestorLeader(String investorLeader) {
        this.investorLeader = investorLeader;
    }

    @Column(name = "investor_contact", nullable = true, length = 50)
    public String getInvestorContact() {
        return this.investorContact;
    }


    public void setInvestorContact(String investorContact) {
        this.investorContact = investorContact;
    }

    @Column(name = "designer", nullable = true, length = 50)
    public String getDesigner() {
        return this.designer;
    }


    public void setDesigner(String designer) {
        this.designer = designer;
    }

    @Column(name = "designer_leader", nullable = true, length = 50)
    public String getDesignerLeader() {
        return this.designerLeader;
    }


    public void setDesignerLeader(String designerLeader) {
        this.designerLeader = designerLeader;
    }

    @Column(name = "designer_contact", nullable = true, length = 50)
    public String getDesignerContact() {
        return this.designerContact;
    }


    public void setDesignerContact(String designerContact) {
        this.designerContact = designerContact;
    }

    @Column(name = "supervisor", nullable = true, length = 50)
    public String getSupervisor() {
        return this.supervisor;
    }


    public void setSupervisor(String supervisor) {
        this.supervisor = supervisor;
    }

    @Column(name = "supervisor_leader", nullable = true, length = 50)
    public String getSupervisorLeader() {
        return this.supervisorLeader;
    }


    public void setSupervisorLeader(String supervisorLeader) {
        this.supervisorLeader = supervisorLeader;
    }

    @Column(name = "supervisor_contact", nullable = true, length = 50)
    public String getSupervisorContact() {
        return this.supervisorContact;
    }


    public void setSupervisorContact(String supervisorContact) {
        this.supervisorContact = supervisorContact;
    }

    @Column(name = "builder", nullable = true, length = 50)
    public String getBuilder() {
        return this.builder;
    }


    public void setBuilder(String builder) {
        this.builder = builder;
    }

    @Column(name = "builder_leader", nullable = true, length = 50)
    public String getBuilderLeader() {
        return this.builderLeader;
    }


    public void setBuilderLeader(String builderLeader) {
        this.builderLeader = builderLeader;
    }

    @Column(name = "builder_contact", nullable = true, length = 50)
    public String getBuilderContact() {
        return this.builderContact;
    }


    public void setBuilderContact(String builderContact) {
        this.builderContact = builderContact;
    }

    @Column(name = "contractor", nullable = true, length = 50)
    public String getContractor() {
        return this.contractor;
    }


    public void setContractor(String contractor) {
        this.contractor = contractor;
    }

    @Column(name = "contractor_leader", nullable = true, length = 50)
    public String getContractorLeader() {
        return this.contractorLeader;
    }


    public void setContractorLeader(String contractorLeader) {
        this.contractorLeader = contractorLeader;
    }

    @Column(name = "contractor_contact", nullable = true, length = 50)
    public String getContractorContact() {
        return this.contractorContact;
    }


    public void setContractorContact(String contractorContact) {
        this.contractorContact = contractorContact;
    }

    @Column(name = "associates", nullable = true, length = 50)
    public String getAssociates() {
        return this.associates;
    }


    public void setAssociates(String associates) {
        this.associates = associates;
    }

    @Column(name = "referee", nullable = true, length = 50)
    public String getReferee() {
        return this.referee;
    }


    public void setReferee(String referee) {
        this.referee = referee;
    }

    @Column(name = "sub_company", nullable = true, length = 50)
    public String getSubCompany() {
        return this.subCompany;
    }


    public void setSubCompany(String subCompany) {
        this.subCompany = subCompany;
    }


    @Column(name = "change_reason", nullable = false, length = 1024)
    public String getChangeReason() {
        return this.changeReason;
    }


    public void setChangeReason(String changeReason) {
        this.changeReason = changeReason;
    }

    @Column(name = "change_remark", nullable = true, length = 1024)
    public String getChangeRemark() {
        return this.changeRemark;
    }


    public void setChangeRemark(String changeRemark) {
        this.changeRemark = changeRemark;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "flow_start_date", nullable = true, length = 29)
    public Date getFlowStartDate() {
        return this.flowStartDate;
    }


    public void setFlowStartDate(Date flowStartDate) {
        this.flowStartDate = flowStartDate;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "flow_end_date", nullable = true, length = 29)
    public Date getFlowEndDate() {
        return this.flowEndDate;
    }


    public void setFlowEndDate(Date flowEndDate) {
        this.flowEndDate = flowEndDate;
    }

    @Column(name = "flow_status", nullable = false)
    public int getFlowStatus() {
        return this.flowStatus;
    }


    public void setFlowStatus(int flowStatus) {
        this.flowStatus = flowStatus;
    }

    @Column(name = "is_deleted", nullable = false)
    public short getIsDeleted() {
        return this.isDeleted;
    }


    public void setIsDeleted(short isDeleted) {
        this.isDeleted = isDeleted;
    }

    @Column(name = "creator", nullable = false, length = 50)
    public String getCreator() {
        return this.creator;
    }


    public void setCreator(String creator) {
        this.creator = creator;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "c_datetime", nullable = false, length = 29)
    public Date getcDatetime() {
        return this.cDatetime;
    }


    public void setcDatetime(Date cDatetime) {
        this.cDatetime = cDatetime;
    }

    @Column(name = "modifier", nullable = true, length = 50)
    public String getModifier() {
        return this.modifier;
    }


    public void setModifier(String modifier) {
        this.modifier = modifier;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "m_datetime", nullable = false, length = 29)
    public Date getmDatetime() {
        return this.mDatetime;
    }


    public void setmDatetime(Date mDatetime) {
        this.mDatetime = mDatetime;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "projectChange")
    public Set<ProjectChangeAttachment> getProjectChangeAttachments() {
        return this.projectChangeAttachments;
    }

    public void setProjectChangeAttachments(Set<ProjectChangeAttachment> projectChangeAttachments) {
        this.projectChangeAttachments = projectChangeAttachments;
    }

    @Transient
    public List<ProjectChangeAttachment> getAttachmentsToDelete() {
        return this.attachmentsToDelete;
    }

    public void setAttachmentsToDelete(List<ProjectChangeAttachment> attachmentsToDelete) {
        this.attachmentsToDelete = attachmentsToDelete;
    }


}
