/**
 * 
 */
package project.edge.domain.view;

import java.util.Date;

import garage.origin.domain.view.ViewBean;


/**
 * @author wwy
 *         预算规划版本表信息的表现层DTO。
 */
public class CapitalPlanVersionBean implements ViewBean {

	private String id;
	private String project_;
	private String projectText;
	private String version;
	private String creator;
	private String cDatetime;
	private String modifier;
	private Date mDatetime;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	public String getProject_() {
		return project_;
	}

	public void setProject_(String project_) {
		this.project_ = project_;
	}

	public String getProjectText() {
		return projectText;
	}
	public void setProjectText(String projectText) {
		this.projectText = projectText;
	}
	
	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public String getcDatetime() {
		return cDatetime;
	}

	public void setcDatetime(String cDatetime) {
		this.cDatetime = cDatetime;
	}

	public String getModifier() {
		return modifier;
	}

	public void setModifier(String modifier) {
		this.modifier = modifier;
	}

	public Date getmDatetime() {
		return mDatetime;
	}

	public void setmDatetime(Date mDatetime) {
		this.mDatetime = mDatetime;
	}

}
