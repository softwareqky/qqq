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

@Entity
@Table(name = "t_password_change_log")
public class PasswordChangeLog implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -2764394068797407720L;
    
    private String id = UUID.randomUUID().toString();
    private User user;
    private Date mDatetime;
    private String oldPassword;
    private String newPassword;

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
    @JoinColumn(name = "user_id", nullable = false)
    public User getUser() {
        return this.user;
    }

    
    public void setUser(User user) {
        this.user = user;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "m_datetime", nullable = false, length = 29)
    public Date getmDatetime() {
        return this.mDatetime;
    }

    
    public void setmDatetime(Date mDatetime) {
        this.mDatetime = mDatetime;
    }

    @Column(name = "old_password", nullable = false, length = 255)
    public String getOldPassword() {
        return this.oldPassword;
    }

    
    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    @Column(name = "new_password", nullable = false, length = 255)
    public String getNewPassword() {
        return this.newPassword;
    }

    
    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
    
}
