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
@Table(name = "t_expert_professional_title")
public class ExpertProfessionalTitle implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 269198330671201733L;

    private String id = UUID.randomUUID().toString();

    private Expert expert;
    private String titleName;
    private Date titleDate;
    private String remark;
    private Archive attachment;

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

    @Column(name = "title_name", nullable = false, length = 150)
    public String getTitleName() {
        return this.titleName;
    }


    public void setTitleName(String titleName) {
        this.titleName = titleName;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "title_date", nullable = false, length = 29)
    public Date getTitleDate() {
        return this.titleDate;
    }


    public void setTitleDate(Date titleDate) {
        this.titleDate = titleDate;
    }

    @Column(name = "remark", nullable = true, length = 1024)
    public String getRemark() {
        return this.remark;
    }


    public void setRemark(String remark) {
        this.remark = remark;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "attachment", nullable = false)
    public Archive getAttachment() {
        return this.attachment;
    }


    public void setAttachment(Archive attachment) {
        this.attachment = attachment;
    }


}
