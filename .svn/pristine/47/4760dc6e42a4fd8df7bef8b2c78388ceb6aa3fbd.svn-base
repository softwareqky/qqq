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
@Table(name = "t_knowledge_base_authority")
public class KnowledgeBaseAuthority implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -13546004099645484L;

    private String id = UUID.randomUUID().toString();

    private KnowledgeBase knowledgeBase;
    private Person person;
    private int accountType;
    private int authType;
    private Short isInherit;
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
    @JoinColumn(name = "knowledge_base_id", nullable = false)
    public KnowledgeBase getKnowledgeBase() {
        return this.knowledgeBase;
    }

    
    public void setKnowledgeBase(KnowledgeBase knowledgeBase) {
        this.knowledgeBase = knowledgeBase;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "person_id", nullable = true)
    public Person getPerson() {
        return this.person;
    }


    public void setPerson(Person person) {
        this.person = person;
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

    @Column(name = "is_inherit", nullable = true)
    public Short getIsInherit() {
        return this.isInherit;
    }


    public void setIsInherit(Short isInherit) {
        this.isInherit = isInherit;
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
