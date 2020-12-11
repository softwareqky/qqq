package project.edge.domain.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "t_archive")
public class Archive implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -6174785264092333651L;

    private String id = UUID.randomUUID().toString();

    private String archiveName;
    private short isDir;
    private String relativePath;
    private Integer fileSize;
    private String fileDigest;
    private String pid;
    private int level;
    private String path;
    private String remark;
    private short isKnowledgeBase;
    private Date previewVersion;
    private String versionOf;
    private short isDeleted;
    private String creator;
    private Date cDatetime;
    private String modifier;
    private Date mDatetime;

    private Set<ProjectPerson> projectPersons = new HashSet<ProjectPerson>(0);
    private Set<ProjectPersonChange> projectPersonChanges = new HashSet<ProjectPersonChange>(0);
    private Set<ProjectPersonChangeDetail> projectPersonChangeDetails =
            new HashSet<ProjectPersonChangeDetail>(0);
    private Set<ProjectPersonHistoryDetail> projectPersonHistoryDetails =
            new HashSet<ProjectPersonHistoryDetail>(0);
    private Set<ProjectGroup> projectGroups = new HashSet<ProjectGroup>(0);
    // private Set<Project> projects = new HashSet<Project>(0);
    // private Set<Project> projectChanges = new HashSet<Project>(0);
    // private Set<ProjectHistory> projectHistories = new HashSet<ProjectHistory>(0);

    private Set<Expert> experts = new HashSet<Expert>(0);
    // private Set<ProjectInspect> projectInspects = new HashSet<ProjectInspect>(0);

    private Set<Site> sites = new HashSet<Site>(0);
    private Set<Plan> plans = new HashSet<Plan>(0);
    private Set<PlanChange> planChanges = new HashSet<PlanChange>(0);
    private Set<PlanHistory> planHistories = new HashSet<PlanHistory>(0);
    private Set<PlanProgress> planProgresses = new HashSet<PlanProgress>(0);
    private Set<PlanProgressTask> planProgressTasks = new HashSet<PlanProgressTask>(0);

    private Set<ProjectPerformanceAward> projectPerformanceAwards =
            new HashSet<ProjectPerformanceAward>(0);
    private Set<ExposureSettings> exposureSettings = new HashSet<ExposureSettings>(0);

    private Set<QualitySpecification> qualitySpecifications = new HashSet<QualitySpecification>(0);
    private Set<QualityExamine> qualityExamines = new HashSet<QualityExamine>(0);
    private Set<QualityAccident> qualityAccidents = new HashSet<QualityAccident>(0);
    private Set<QualityObjective> qualityObjectives = new HashSet<QualityObjective>(0);
    private Set<NoticeAnnouncement> noticeAnnouncements = new HashSet<NoticeAnnouncement>(0);

    private Set<TenderingPlan> tenderingPlans = new HashSet<TenderingPlan>(0);
    private Set<Tenderee> tenderees = new HashSet<Tenderee>(0);

    private Set<BiddingBasicInfo> biddingBasicInfos = new HashSet<BiddingBasicInfo>(0);
    private Set<AcceptanceInfo> acceptanceInfos = new HashSet<AcceptanceInfo>(0);

    @GenericGenerator(name = "generator", strategy = "assigned")
    @Id
    @GeneratedValue(generator = "generator")
    @Column(name = "id", unique = true, nullable = false, length = 100)
    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Column(name = "archive_name", nullable = false, length = 50)
    public String getArchiveName() {
        return this.archiveName;
    }


    public void setArchiveName(String archiveName) {
        this.archiveName = archiveName;
    }

    @Column(name = "is_dir", nullable = false)
    public short getIsDir() {
        return this.isDir;
    }


    public void setIsDir(short isDir) {
        this.isDir = isDir;
    }

    @Column(name = "relative_path", nullable = false, length = 1024)
    public String getRelativePath() {
        return this.relativePath;
    }


    public void setRelativePath(String relativePath) {
        this.relativePath = relativePath;
    }

    @Column(name = "file_size", nullable = true)
    public Integer getFileSize() {
        return this.fileSize;
    }


    public void setFileSize(Integer fileSize) {
        this.fileSize = fileSize;
    }

    @Column(name = "file_digest", nullable = true, length = 255)
    public String getFileDigest() {
        return this.fileDigest;
    }


    public void setFileDigest(String fileDigest) {
        this.fileDigest = fileDigest;
    }

    @Column(name = "pid", nullable = true, length = 100)
    public String getPid() {
        return this.pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    @Column(name = "version_of", nullable = true, length = 100)
    public String getVersionOf() {
        return this.versionOf;
    }

    public void setVersionOf(String versionOf) {
        this.versionOf = versionOf;
    }

    @Column(name = "level", nullable = false)
    public int getLevel() {
        return this.level;
    }


    public void setLevel(int level) {
        this.level = level;
    }

    @Column(name = "path", nullable = true, length = 1024)
    public String getPath() {
        return this.path;
    }


    public void setPath(String path) {
        this.path = path;
    }

    @Column(name = "remark", nullable = true, length = 500)
    public String getRemark() {
        return this.remark;
    }


    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Column(name = "is_deleted", nullable = false)
    public short getIsDeleted() {
        return this.isDeleted;
    }


    public void setIsDeleted(short isDeleted) {
        this.isDeleted = isDeleted;
    }

    @Column(name = "is_knowledge_base", nullable = false)
    public short getIsKnowledgeBase() {
        return this.isKnowledgeBase;
    }


    public void setIsKnowledgeBase(short isKnowledgeBase) {
        this.isKnowledgeBase = isKnowledgeBase;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "preview_version", length = 29)
    public Date getPreviewVersion() {
        return this.previewVersion;
    }


    public void setPreviewVersion(Date previewVersion) {
        this.previewVersion = previewVersion;
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

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "t_project_person_attachment",
            joinColumns = {@JoinColumn(name = "archive_id", nullable = false, updatable = false)},
            inverseJoinColumns = {
                    @JoinColumn(name = "project_person_id", nullable = false, updatable = false)})
    public Set<ProjectPerson> getProjectPersons() {
        return this.projectPersons;
    }


    public void setProjectPersons(Set<ProjectPerson> projectPersons) {
        this.projectPersons = projectPersons;
    }

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "t_project_person_change_attachment",
            joinColumns = {@JoinColumn(name = "archive_id", nullable = false, updatable = false)},
            inverseJoinColumns = {@JoinColumn(name = "project_person_change_id", nullable = false,
                    updatable = false)})
    public Set<ProjectPersonChange> getProjectPersonChanges() {
        return this.projectPersonChanges;
    }


    public void setProjectPersonChanges(Set<ProjectPersonChange> projectPersonChanges) {
        this.projectPersonChanges = projectPersonChanges;
    }



    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "t_project_group_attachment",
            joinColumns = {@JoinColumn(name = "archive_id", nullable = false, updatable = false)},
            inverseJoinColumns = {
                    @JoinColumn(name = "group_id", nullable = false, updatable = false)})
    public Set<ProjectGroup> getProjectGroups() {
        return this.projectGroups;
    }


    public void setProjectGroups(Set<ProjectGroup> projectGroups) {
        this.projectGroups = projectGroups;
    }

    /*
     * @ManyToMany(fetch = FetchType.LAZY)
     * 
     * @JoinTable(name = "t_project_inspect_attachment",
     * joinColumns = {@JoinColumn(name = "archive_id", nullable = false, updatable = false)},
     * inverseJoinColumns = {
     * 
     * @JoinColumn(name = "project_inspect_id", nullable = false, updatable = false)})
     * public Set<ProjectInspect> getProjectInspects() {
     * return this.projectInspects;
     * }
     * 
     * 
     * public void setProjectInspects(Set<ProjectInspect> projectInspects) {
     * this.projectInspects = projectInspects;
     * }
     */

    // @ManyToMany(fetch = FetchType.LAZY)
    // @JoinTable(name = "t_project_attachment",
    // joinColumns = {@JoinColumn(name = "archive_id", nullable = false, updatable = false)},
    // inverseJoinColumns = {
    // @JoinColumn(name = "project_id", nullable = false, updatable = false)})
    // public Set<Project> getProjects() {
    // return this.projects;
    // }
    //
    //
    // public void setProjects(Set<Project> projects) {
    // this.projects = projects;
    // }



    // @ManyToMany(fetch = FetchType.LAZY)
    // @JoinTable(name = "t_project_change_attachment",
    // joinColumns = {@JoinColumn(name = "archive_id", nullable = false, updatable = false)},
    // inverseJoinColumns = {
    // @JoinColumn(name = "project_change_id", nullable = false, updatable = false)})
    // public Set<Project> getProjectChanges() {
    // return this.projectChanges;
    // }
    //
    //
    // public void setProjectChanges(Set<Project> projectChanges) {
    // this.projectChanges = projectChanges;
    // }

    // @ManyToMany(fetch = FetchType.LAZY)
    // @JoinTable(name = "t_project_history_attachment",
    // joinColumns = {@JoinColumn(name = "archive_id", nullable = false, updatable = false)},
    // inverseJoinColumns = {
    // @JoinColumn(name = "project_history_id", nullable = false, updatable = false)})
    // public Set<ProjectHistory> getProjectHistories() {
    // return this.projectHistories;
    // }
    //
    //
    // public void setProjectHistories(Set<ProjectHistory> projectHistories) {
    // this.projectHistories = projectHistories;
    // }

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "t_project_person_change_detail_attachment",
            joinColumns = {@JoinColumn(name = "archive_id", nullable = false, updatable = false)},
            inverseJoinColumns = {@JoinColumn(name = "project_person_change_detail_id",
                    nullable = false, updatable = false)})
    public Set<ProjectPersonChangeDetail> getProjectPersonChangeDetails() {
        return this.projectPersonChangeDetails;
    }


    public void setProjectPersonChangeDetails(
            Set<ProjectPersonChangeDetail> projectPersonChangeDetails) {
        this.projectPersonChangeDetails = projectPersonChangeDetails;
    }

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "t_project_person_history_detail_attachment",
            joinColumns = {@JoinColumn(name = "archive_id", nullable = false, updatable = false)},
            inverseJoinColumns = {@JoinColumn(name = "project_person_history_detail_id",
                    nullable = false, updatable = false)})
    public Set<ProjectPersonHistoryDetail> getProjectPersonHistoryDetails() {
        return this.projectPersonHistoryDetails;
    }


    public void setProjectPersonHistoryDetails(
            Set<ProjectPersonHistoryDetail> projectPersonHistoryDetails) {
        this.projectPersonHistoryDetails = projectPersonHistoryDetails;
    }

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "t_expert_attachment",
            joinColumns = {@JoinColumn(name = "archive_id", nullable = false, updatable = false)},
            inverseJoinColumns = {
                    @JoinColumn(name = "expert_id", nullable = false, updatable = false)})
    public Set<Expert> getExperts() {
        return this.experts;
    }


    public void setExperts(Set<Expert> experts) {
        this.experts = experts;
    }

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "t_plan_attachment",
            joinColumns = {@JoinColumn(name = "archive_id", nullable = false, updatable = false)},
            inverseJoinColumns = {
                    @JoinColumn(name = "plan_id", nullable = false, updatable = false)})
    public Set<Plan> getPlans() {
        return this.plans;
    }


    public void setPlans(Set<Plan> plans) {
        this.plans = plans;
    }

    
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "t_site_attachment",
            joinColumns = {@JoinColumn(name = "archive_id", nullable = false, updatable = false)},
            inverseJoinColumns = {
                    @JoinColumn(name = "site_id", nullable = false, updatable = false)})
    public Set<Site> getSites() {
		return sites;
	}

	public void setSites(Set<Site> sites) {
		this.sites = sites;
	}

	@ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "t_plan_change_attachment",
            joinColumns = {@JoinColumn(name = "archive_id", nullable = false, updatable = false)},
            inverseJoinColumns = {
                    @JoinColumn(name = "plan_change_id", nullable = false, updatable = false)})
    public Set<PlanChange> getPlanChanges() {
        return this.planChanges;
    }


    public void setPlanChanges(Set<PlanChange> planChanges) {
        this.planChanges = planChanges;
    }

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "t_plan_history_attachment",
            joinColumns = {@JoinColumn(name = "archive_id", nullable = false, updatable = false)},
            inverseJoinColumns = {
                    @JoinColumn(name = "plan_history_id", nullable = false, updatable = false)})
    public Set<PlanHistory> getPlanHistories() {
        return this.planHistories;
    }


    public void setPlanHistories(Set<PlanHistory> planHistories) {
        this.planHistories = planHistories;
    }

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "t_plan_progress_attachment",
            joinColumns = {@JoinColumn(name = "archive_id", nullable = false, updatable = false)},
            inverseJoinColumns = {
                    @JoinColumn(name = "plan_progress_id", nullable = false, updatable = false)})
    public Set<PlanProgress> getPlanProgresses() {
        return this.planProgresses;
    }


    public void setPlanProgresses(Set<PlanProgress> planProgresses) {
        this.planProgresses = planProgresses;
    }

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "t_plan_progress_task_attachment",
            joinColumns = {@JoinColumn(name = "archive_id", nullable = false, updatable = false)},
            inverseJoinColumns = {@JoinColumn(name = "plan_progress_task_id", nullable = false,
                    updatable = false)})
    public Set<PlanProgressTask> getPlanProgressTasks() {
        return this.planProgressTasks;
    }


    public void setPlanProgressTasks(Set<PlanProgressTask> planProgressTasks) {
        this.planProgressTasks = planProgressTasks;
    }

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "t_project_performance_attachment",
            joinColumns = {@JoinColumn(name = "archive_id", nullable = false, updatable = false)},
            inverseJoinColumns = {
                    @JoinColumn(name = "award_id", nullable = false, updatable = false)})
    public Set<ProjectPerformanceAward> getProjectPerformanceAwards() {
        return this.projectPerformanceAwards;
    }


    public void setProjectPerformanceAwards(Set<ProjectPerformanceAward> projectPerformanceAwards) {
        this.projectPerformanceAwards = projectPerformanceAwards;
    }

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "t_exposure_settings_attachment",
            joinColumns = {@JoinColumn(name = "archive_id", nullable = false, updatable = false)},
            inverseJoinColumns = {
                    @JoinColumn(name = "exposure_id", nullable = false, updatable = false)})
    public Set<ExposureSettings> getExposureSettings() {
        return this.exposureSettings;
    }


    public void setExposureSettings(Set<ExposureSettings> exposureSettings) {
        this.exposureSettings = exposureSettings;
    }

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "t_quality_specification_attachment",
            joinColumns = {@JoinColumn(name = "archive_id", nullable = false, updatable = false)},
            inverseJoinColumns = {@JoinColumn(name = "quality_specification_id", nullable = false,
                    updatable = false)})
    public Set<QualitySpecification> getQualitySpecifications() {
        return this.qualitySpecifications;
    }


    public void setQualitySpecifications(Set<QualitySpecification> qualitySpecifications) {
        this.qualitySpecifications = qualitySpecifications;
    }

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "t_quality_examine_attachment",
            joinColumns = {@JoinColumn(name = "archive_id", nullable = false, updatable = false)},
            inverseJoinColumns = {
                    @JoinColumn(name = "quality_examine_id", nullable = false, updatable = false)})
    public Set<QualityExamine> getQualityExamines() {
        return this.qualityExamines;
    }


    public void setQualityExamines(Set<QualityExamine> qualityExamines) {
        this.qualityExamines = qualityExamines;
    }

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "t_quality_accident_attachment",
            joinColumns = {@JoinColumn(name = "archive_id", nullable = false, updatable = false)},
            inverseJoinColumns = {
                    @JoinColumn(name = "quality_accident_id", nullable = false, updatable = false)})
    public Set<QualityAccident> getQualityAccidents() {
        return this.qualityAccidents;
    }


    public void setQualityAccidents(Set<QualityAccident> qualityAccidents) {
        this.qualityAccidents = qualityAccidents;
    }
    
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "t_quality_objective_attachment",
            joinColumns = {@JoinColumn(name = "archive_id", nullable = false, updatable = false)},
            inverseJoinColumns = {
                    @JoinColumn(name = "quality_objective_id", nullable = false, updatable = false)})
    public Set<QualityObjective> getQualityObjectives() {
		return qualityObjectives;
	}

	public void setQualityObjectives(Set<QualityObjective> qualityObjectives) {
		this.qualityObjectives = qualityObjectives;
	}

	@ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "t_notice_attachment",
            joinColumns = {@JoinColumn(name = "archive_id", nullable = false, updatable = false)},
            inverseJoinColumns = {
                    @JoinColumn(name = "notice_id", nullable = false, updatable = false)})
    public Set<NoticeAnnouncement> getNoticeAnnouncements() {
        return this.noticeAnnouncements;
    }


    public void setNoticeAnnouncements(Set<NoticeAnnouncement> noticeAnnouncements) {
        this.noticeAnnouncements = noticeAnnouncements;
    }

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "t_tendering_plan_attachment",
            joinColumns = {@JoinColumn(name = "archive_id", nullable = false, updatable = false)},
            inverseJoinColumns = {
                    @JoinColumn(name = "tendering_plan_id", nullable = false, updatable = false)})
    public Set<TenderingPlan> getTenderingPlans() {
        return this.tenderingPlans;
    }


    public void setTenderingPlans(Set<TenderingPlan> tenderingPlans) {
        this.tenderingPlans = tenderingPlans;
    }

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "t_tenderee_attachment",
            joinColumns = {@JoinColumn(name = "archive_id", nullable = false, updatable = false)},
            inverseJoinColumns = {
                    @JoinColumn(name = "tenderee_id", nullable = false, updatable = false)})
    public Set<Tenderee> getTenderees() {
        return this.tenderees;
    }


    public void setTenderees(Set<Tenderee> tenderees) {
        this.tenderees = tenderees;
    }

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "t_bidding_basic_info_attachment",
            joinColumns = {@JoinColumn(name = "archive_id", nullable = false, updatable = false)},
            inverseJoinColumns = {@JoinColumn(name = "bidding_basic_info_id", nullable = false,
                    updatable = false)})
    public Set<BiddingBasicInfo> getBiddingBasicInfos() {
        return this.biddingBasicInfos;
    }


    public void setBiddingBasicInfos(Set<BiddingBasicInfo> biddingBasicInfos) {
        this.biddingBasicInfos = biddingBasicInfos;
    }

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "t_acceptance_info_attachment",
            joinColumns = {@JoinColumn(name = "archive_id", nullable = false, updatable = false)},
            inverseJoinColumns = {
                    @JoinColumn(name = "acceptance_info_id", nullable = false, updatable = false)})
    public Set<AcceptanceInfo> getAcceptanceInfos() {
        return this.acceptanceInfos;
    }


    public void setAcceptanceInfos(Set<AcceptanceInfo> acceptanceInfos) {
        this.acceptanceInfos = acceptanceInfos;
    }



}
