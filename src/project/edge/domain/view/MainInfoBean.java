/**
 * 
 */
package project.edge.domain.view;

import java.util.ArrayList;
import java.util.List;

/**
 * @author angel_000
 *
 */
public class MainInfoBean {

	private MainInfoProjectSummarySubBean projectSummary;
	private MainInfoProcessSummarySubBean processSummary;
	private List<MainInfoProjectInfoListBean> projectInfo = new ArrayList<>();
	private List<MainInfoNoticeInfoListBean> noticeInfo = new ArrayList<>();
	private List<MainInfoProjectInfoListBean> favoriteInfo = new ArrayList<>();
	private List<MainInfoTaskListBean> taskFavoriteInfo = new ArrayList<>();
	private List<String> years = new ArrayList<>();

	public MainInfoProjectSummarySubBean getProjectSummary() {
		return this.projectSummary;
	}

	public void setProjectSummary(MainInfoProjectSummarySubBean projectSummary) {
		this.projectSummary = projectSummary;
	}

	public List<MainInfoProjectInfoListBean> getProjectInfo() {
		return this.projectInfo;
	}

	public void setProjectInfo(List<MainInfoProjectInfoListBean> projectInfo) {
		this.projectInfo = projectInfo;
	}

	public List<MainInfoNoticeInfoListBean> getNoticeInfo() {
		return this.noticeInfo;
	}

	public void setNoticeInfo(List<MainInfoNoticeInfoListBean> noticeInfo) {
		this.noticeInfo = noticeInfo;
	}

	public List<MainInfoProjectInfoListBean> getFavoriteInfo() {
		return this.favoriteInfo;
	}

	public void setFavoriteInfo(List<MainInfoProjectInfoListBean> favoriteInfo) {
		this.favoriteInfo = favoriteInfo;
	}

	public List<String> getYears() {
		return years;
	}

	public void setYears(List<String> years) {
		this.years = years;
	}

	public List<MainInfoTaskListBean> getTaskFavoriteInfo() {
		return taskFavoriteInfo;
	}

	public void setTaskFavoriteInfo(List<MainInfoTaskListBean> taskFavoriteInfo) {
		this.taskFavoriteInfo = taskFavoriteInfo;
	}

	public MainInfoProcessSummarySubBean getProcessSummary() {
		return processSummary;
	}

	public void setProcessSummary(MainInfoProcessSummarySubBean processSummary) {
		this.processSummary = processSummary;
	}

}
