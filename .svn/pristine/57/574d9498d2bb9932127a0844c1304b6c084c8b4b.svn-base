package project.edge.domain.entity;

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

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "t_notice_announcement")
public class NoticeAnnouncement implements Serializable, Cloneable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3275140442361796292L;

	private String id = UUID.randomUUID().toString();

	private String noticeNo;
	private ProjectPerson originator;
	private String tittle;
	private String content;
	private Dept recvDept;
	private ProjectPerson recvPerson;
	private Date cDatetime;
	private Short isRevoke;
	private Date rDatetime;
	private Short isDelete;
	private Date dDatetime;
	private Short level;
	private String creator;
	private String modifier;
	private Date mDatetime;
	private Short isPublish;
	private Short isForAll;
	private String recivers;

	private Set<NoticeAttachment> noticeAttachments = new HashSet<>(0);
	private List<NoticeAttachment> attachmentsToDelete = new ArrayList<>();

	@Override
	public NoticeAnnouncement clone() {
		NoticeAnnouncement p = null;
		try {
			p = (NoticeAnnouncement) super.clone();
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

	@Column(name = "notice_no", nullable = true, length = 30)
	public String getNoticeNo() {
		return this.noticeNo;
	}

	public void setNoticeNo(String noticeNo) {
		this.noticeNo = noticeNo;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "originator_id", nullable = true)
	public ProjectPerson getOriginator() {
		return this.originator;
	}

	public void setOriginator(ProjectPerson originator) {
		this.originator = originator;
	}

	@Column(name = "tittle", nullable = false, length = 50)
	public String getTittle() {
		return this.tittle;
	}

	public void setTittle(String tittle) {
		this.tittle = tittle;
	}

	@Column(name = "content", nullable = false, length = 1024)
	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "recv_dept_id", nullable = true)
	public Dept getRecvDept() {
		return this.recvDept;
	}

	public void setRecvDept(Dept recvDept) {
		this.recvDept = recvDept;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "recv_person_id", nullable = true)
	public ProjectPerson getRecvPerson() {
		return this.recvPerson;
	}

	public void setRecvPerson(ProjectPerson recvPerson) {
		this.recvPerson = recvPerson;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "c_datetime", nullable = true, length = 29)
	public Date getcDatetime() {
		return this.cDatetime;
	}

	public void setcDatetime(Date cDatetime) {
		this.cDatetime = cDatetime;
	}

	@Column(name = "is_revoke", nullable = true)
	public Short getIsRevoke() {
		return this.isRevoke;
	}

	public void setIsRevoke(Short isRevoke) {
		this.isRevoke = isRevoke;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "r_datetime", nullable = true, length = 29)
	public Date getrDatetime() {
		return this.rDatetime;
	}

	public void setrDatetime(Date rDatetime) {
		this.rDatetime = rDatetime;
	}

	@Column(name = "is_delete", nullable = true)
	public Short getIsDelete() {
		return this.isDelete;
	}

	public void setIsDelete(Short isDelete) {
		this.isDelete = isDelete;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "d_datetime", nullable = true, length = 29)
	public Date getdDatetime() {
		return this.dDatetime;
	}

	public void setdDatetime(Date dDatetime) {
		this.dDatetime = dDatetime;
	}

	@Column(name = "level", nullable = true)
	public Short getLevel() {
		return this.level;
	}

	public void setLevel(Short level) {
		this.level = level;
	}

	@Column(name = "creator", nullable = false, length = 50)
	public String getCreator() {
		return this.creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
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

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "noticeAnnouncement")
	public Set<NoticeAttachment> getNoticeAttachments() {
		return this.noticeAttachments;
	}

	public void setNoticeAttachments(Set<NoticeAttachment> noticeAttachments) {
		this.noticeAttachments = noticeAttachments;
	}

	@Transient
	public List<NoticeAttachment> getAttachmentsToDelete() {
		return this.attachmentsToDelete;
	}

	public void setAttachmentsToDelete(List<NoticeAttachment> attachmentsToDelete) {
		this.attachmentsToDelete = attachmentsToDelete;
	}

	@Column(name = "is_publish", nullable = true)
	public Short getIsPublish() {
		return isPublish;
	}

	public void setIsPublish(Short isPublish) {
		this.isPublish = isPublish;
	}

	@Column(name = "is_for_all", nullable = true)
	public Short getIsForAll() {
		return isForAll;
	}

	public void setIsForAll(Short isForAll) {
		this.isForAll = isForAll;
	}

	@Column(name = "recivers", nullable = true)
	public String getRecivers() {
		return recivers;
	}

	public void setRecivers(String recivers) {
		this.recivers = recivers;
	}

}
