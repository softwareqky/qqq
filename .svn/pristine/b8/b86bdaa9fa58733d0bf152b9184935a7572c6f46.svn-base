/**
 * 
 */
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
@Table(name = "t_segment_authority")
public class SegmentAuthority implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -580878512334995049L;

    private String id = UUID.randomUUID().toString();

    private Segment segment;
    private String accountId;
    private int accountType;
    private int authType;
    private String creator;
    private Date cDatetime;
    private String modifier;
    private Date mDatetime;


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
    @JoinColumn(name = "segment_id", nullable = false)
    public Segment getSegment() {
        return this.segment;
    }


    public void setSegment(Segment segment) {
        this.segment = segment;
    }

    @Column(name = "account_id", nullable = false, length = 36)
    public String getAccountId() {
        return this.accountId;
    }


    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    @Column(name = "account_type", nullable = false)
    public int getAccountType() {
        return this.accountType;
    }


    public void setAccountType(int accountType) {
        this.accountType = accountType;
    }

    @Column(name = "auth_type", nullable = false)
    public int getAuthType() {
        return this.authType;
    }


    public void setAuthType(int authType) {
        this.authType = authType;
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

}
