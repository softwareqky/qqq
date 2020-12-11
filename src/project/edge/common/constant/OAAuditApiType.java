package project.edge.common.constant;

public enum OAAuditApiType {

	OA_TYPE_PROJECTINIT(1, "oa.apitype.projectinit"),
	
	OA_TYPE_PROJECTAUDIT(2, "oa.apitype.projectaudit"),
	
	OA_TYPE_PROJECT_PERSON_ADD(3, "oa.apitype.project.person.add"),
	
	OA_TYPE_PROJECT_PERSON_CHANGE(4, "oa,apitype.project.person.change"), 
	
	OA_TYPE_PURCHASEORDER(5, "oa.apitype.purchaseorder"), 
	
	OA_TYPE_TENDINGPLAN(6, "oa.apitype.tendingplan"), 
	
	OA_TYPE_PREQUAF(7,"oa.apitype.prequaf"),
	
	OA_TYPE_CONTRACT(8, "oa.apitype.contract"),
	
	OA_TYPE_PLAN(9, "oa.apitype.plan"),

	OA_TYPE_PLANCHANGE(10, "oa.apitype.planchange"),

	OA_TYPE_PROJECTCHANGE(11, "oa.apitype.projectchange"),
	
	OA_TYPE_PROJECT_CHECK(12, "oa.apitype.project.check"),
	
	OA_TYPE_PROJECT_INSPECT(13, "oa.apitype.project.inspect"),
	
	OA_TYPE_PROJECT_PERFORMANCE_AWARD(14, "oa.apitype.project.performance.award"),
	
	OA_TYPE_PROJECT_ACCEPTANCE(15, "oa.apitype.project.acceptance"),
	
	OA_TYPE_PROJECT_REVIEW(16, "oa.apitype.project.review"),
	
	OA_TYPE_QUALITY_REPORT(17, "oa.apitype.quality.report"),
	
	OA_TYPE_QUALITY_EXAMINE(18, "oa.apitype.quality.examine"),
	
	OA_TYPE_ARCHIVE_LEND(19, "oa.apitype.archive.lend"),
	
	OA_TYPE_CAPITALPLAN(20, "oa.apitype.capitalPlan"),

	OA_TYPE_BUDGET(21, "oa.apitype.budget"),

	OA_TYPE_BUDGETCHANGE(22, "oa.apitype.budgetCHANGE"),

	OA_TYPE_BUDGETFINAL(23, "oa.apitype.budgetFinal");

	private Integer value;

	private String resourceName;

	private OAAuditApiType(Integer value, String resourceName) {
		this.value = value;
		this.resourceName = resourceName;
	}

	/**
	 * 根据枚举的值获取对应的资源名。
	 * 
	 * @param value
	 *            枚举值
	 * @return 资源名
	 */
	public static String getResouceName(Integer value) {
		for (OAAuditApiType s : OAAuditApiType.values()) {
			if (s.value().equals(value)) {
				return s.resourceName;
			}
		}
		return null;
	}

	public Integer value() {
		return this.value;
	}

	public String resourceName() {
		return this.resourceName;
	}
}
