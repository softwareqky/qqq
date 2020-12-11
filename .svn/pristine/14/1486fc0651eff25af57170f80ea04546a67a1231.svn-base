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
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


/**
 * @author angel_000
 *
 */
@Entity
@Table(name = "t_knowledge_base")
public class KnowledgeBase implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 7977141792480099498L;

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
    private short isDeleted;
    private String creator;
    private Date cDatetime;
    private String modifier;
    private Date mDatetime;
    
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
