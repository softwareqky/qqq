package project.edge.domain.entity;

import java.io.Serializable;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "t_project_check_expert")
public class ProjectCheckExpert implements Serializable {
	private static final long serialVersionUID = -1263330424849380561L;

    private String id = UUID.randomUUID().toString();

    private ProjectCheck projectCheck;
    private Expert expert;

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
    @JoinColumn(name = "project_check_id", nullable = false)
    public ProjectCheck getProjectCheck() {
        return this.projectCheck;
    }


    public void setProjectCheck(ProjectCheck projectCheck) {
        this.projectCheck = projectCheck;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "expert_id", nullable = false)
    public Expert getExpert() {
        return this.expert;
    }


    public void setExpert(Expert expert) {
        this.expert = expert;
    }

}
