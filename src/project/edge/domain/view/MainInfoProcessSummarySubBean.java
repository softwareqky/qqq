package project.edge.domain.view;

import com.alibaba.fastjson.JSONArray;

public class MainInfoProcessSummarySubBean {

	private JSONArray pendingProcess;
	private JSONArray initiatedProcess;
	private JSONArray doneProcess;

	public JSONArray getDoneProcess() {
		return doneProcess;
	}

	public void setDoneProcess(JSONArray doneProcess) {
		this.doneProcess = doneProcess;
	}

	public JSONArray getInitiatedProcess() {
		return initiatedProcess;
	}

	public void setInitiatedProcess(JSONArray initiatedProcess) {
		this.initiatedProcess = initiatedProcess;
	}

	public JSONArray getPendingProcess() {
		return pendingProcess;
	}

	public void setPendingProcess(JSONArray pendingProcess) {
		this.pendingProcess = pendingProcess;
	}
}
