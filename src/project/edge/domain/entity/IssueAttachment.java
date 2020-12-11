/**
 * 
 */
package project.edge.domain.entity;

import org.hibernate.annotations.GenericGenerator;

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


/**
 * @author angel_000
 *
 */
@Entity
@Table(name = "t_issue_attachment")
public class IssueAttachment implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 7309522255568807540L;

    private String id = UUID.randomUUID().toString();

    private Issue issue;
    private Archive archive;
    //private short isImprove;
    private short isSolved;

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
    @JoinColumn(name = "issue_id", nullable = false)
    public Issue getIssue() {
        return this.issue;
    }


    public void setIssue(Issue issue) {
        this.issue = issue;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "archive_id", nullable = false)
    public Archive getArchive() {
        return this.archive;
    }

    public void setArchive(Archive archive) {
        this.archive = archive;
    }

    @Column(name = "is_solved", nullable = false)
    public short getIsSolved() {
        return this.isSolved;
    }

    
    public void setIsSolved(short isSolved) {
        this.isSolved = isSolved;
    }

//    @Column(name = "is_solved", nullable = false)
//    public short getIsImprove() {
//        return this.isImprove;
//    }
//
//
//    public void setIsImprove(short isImprove) {
//        this.isImprove = isImprove;
//    }

    
}
