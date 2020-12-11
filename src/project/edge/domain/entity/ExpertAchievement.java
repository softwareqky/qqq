package project.edge.domain.entity;

import org.hibernate.annotations.GenericGenerator;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * @author angel_000
 *
 */
@Entity
@Table(name = "t_expert_achievement")
public class ExpertAchievement implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 4049441987431117805L;

    private String id = UUID.randomUUID().toString();

    private Expert expert;
    private String achievementName;
    private Date startDatetime;
    private Date endDatetime;
    private DataOption grade;
    private String topicContent;
    private String topicRole;
    private String jobDesc;
    private String achievementContent;
    private String referenceProject;


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
    @JoinColumn(name = "expert_id", nullable = false)
    public Expert getExpert() {
        return this.expert;
    }


    public void setExpert(Expert expert) {
        this.expert = expert;
    }

    @Column(name = "achievement_name", nullable = false, length = 150)
    public String getAchievementName() {
        return this.achievementName;
    }


    public void setAchievementName(String achievementName) {
        this.achievementName = achievementName;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "start_datetime", nullable = false, length = 29)
    public Date getStartDatetime() {
        return this.startDatetime;
    }


    public void setStartDatetime(Date startDatetime) {
        this.startDatetime = startDatetime;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "end_datetime", nullable = false, length = 29)
    public Date getEndDatetime() {
        return this.endDatetime;
    }


    public void setEndDatetime(Date endDatetime) {
        this.endDatetime = endDatetime;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "grade", nullable = false)
    public DataOption getGrade() {
        return this.grade;
    }


    public void setGrade(DataOption grade) {
        this.grade = grade;
    }

    @Column(name = "topic_content", nullable = true, length = 1024)
    public String getTopicContent() {
        return this.topicContent;
    }


    public void setTopicContent(String topicContent) {
        this.topicContent = topicContent;
    }

    @Column(name = "topic_role", nullable = true, length = 1024)
    public String getTopicRole() {
        return this.topicRole;
    }


    public void setTopicRole(String topicRole) {
        this.topicRole = topicRole;
    }

    @Column(name = "job_desc", nullable = true, length = 1024)
    public String getJobDesc() {
        return this.jobDesc;
    }


    public void setJobDesc(String jobDesc) {
        this.jobDesc = jobDesc;
    }

    @Column(name = "achievement_content", nullable = true, length = 1024)
    public String getAchievementContent() {
        return this.achievementContent;
    }


    public void setAchievementContent(String achievementContent) {
        this.achievementContent = achievementContent;
    }

    @Column(name = "reference_project", nullable = true, length = 1024)
    public String getReferenceProject() {
        return this.referenceProject;
    }


    public void setReferenceProject(String referenceProject) {
        this.referenceProject = referenceProject;
    }



}
