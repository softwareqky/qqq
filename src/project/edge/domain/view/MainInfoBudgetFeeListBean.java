package project.edge.domain.view;

import java.math.BigDecimal;

/**
 * @author angel_000
 *
 */
public class MainInfoBudgetFeeListBean {

	private String id;
	private String virtualOrgName;
	private BigDecimal usedSum;
	private String time;

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public BigDecimal getUsedSum() {
		return this.usedSum;
	}

	public void setUsedSum(BigDecimal usedSum) {
		this.usedSum = usedSum;
	}

	public String getTime() {
		return this.time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getVirtualOrgName() {
		return virtualOrgName;
	}

	public void setVirtualOrgName(String virtualOrgName) {
		this.virtualOrgName = virtualOrgName;
	}

}
