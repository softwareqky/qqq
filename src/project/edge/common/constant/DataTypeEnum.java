package project.edge.common.constant;

/**
 * 数据类型枚举。
 * 
 */
public enum DataTypeEnum {

	/** 系统配置 */
	SYSTEM_CONFIG("SYSTEM_CONFIG", "ui.datatype.system.config"),
	
	/** 预警规则 */
	PREDEFINED_RULE("PREDEFINED_RULE", "ui.datatype.predefined.rule"),

	/** 自定义地图 */
	DEFINED_MAP_ICON("DEFINED_MAP_ICON", "ui.datatype.defined.map.icon"),

	/** 系统操作日志 */
	SYSTEM_LOG("SYSTEM_LOG", "ui.datatype.system.log"),

	/** 编码字典资源 */
	DATA_OPTION("DATA_OPTION", "ui.datatype.data.option"),

	/** 角色 */
	ROLE("ROLE", "ui.datatype.role"),

	/** 用户 */
	USER("USER", "ui.datatype.user"),

	/** 组织 */
	ORG("ORG", "ui.datatype.org"),

	/** 部门 */
	DEPT("DEPT", "ui.datatype.dept"),

	/** 职位 */
	POST("POST", "ui.datatype.post"),

	/** 组织预览 */
	ORG_PREVIEW("ORG_PREVIEW", "ui.datatype.org.preview"),

	/** 往来单位 */
	RELATED_UNIT("RELATED_UNIT", "ui.datatype.related.unit"),

	/** 人员 */
	PERSON("PERSON", "ui.datatype.person"),

	/** 项目组 */
	PROJECT_GROUP("PROJECT_GROUP", "ui.datatype.project.group"),
	
	/** 项目里程碑 */
	PROJECT_MILESTONE("PROJECT_MILESTONE", "ui.menu.item.project.milestone"),

	/** 项目集 */
	PROJECT_SET("PROJECT_SET", "ui.datatype.project.set"),

	/** 项目类别 */
	DATA_OPTION_PROJECT_KIND("DATA_OPTION_PROJECT_KIND", "ui.datatype.project.kind"),

	/** 奖罚类别 */
	DATA_OPTION_REWARDS_TYPE("DATA_OPTION_REWARDS_TYPE", "ui.menu.item.project.rewards.type"),

	/** 奖罚信息 */
	PROJECT_PERFORMANCE_AWARD("PROJECT_PERFORMANCE_AWARD", "ui.datatype.project.performance.award"),

	/** 曝光信息 */
	EXPOSURE_SETTINGS("EXPOSURE_SETTINGS", "ui.fields.project.performance.exposureBasis"),

	/** 项目信息 */
	PROJECT("PROJECT", "ui.datatype.project"),

	/** 项目虚拟组织 */
	VIRTUAL_ORG("VIRTUAL_ORG", "ui.datatype.project.virtual.org"),

	/** 项目变更申请 */
	PROJECT_CHANGE("PROJECT_CHANGE", "ui.datatype.project.change"),

	/** 项目历史版本 */
	PROJECT_HISTORY("PROJECT_HISTORY", "ui.datatype.project.history"),

	/** 项目成员 */
	PROJECT_PERSON("PROJECT_PERSON", "ui.datatype.project.person"),

	/** 项目成员变更申请 */
	PROJECT_PERSON_CHANGE("PROJECT_PERSON_CHANGE", "ui.datatype.project.person.change"),
	
	/** 项目成员弹出框 */
	PROJECT_MEMBER("PROJECT_MEMBER", "ui.datatype.project.member"),

	/** 项目成员历史版本 */
	PROJECT_PERSON_HISTORY("PROJECT_PERSON_HISTORY", "ui.datatype.project.person.history"),

	/** 项目角色 */
	PROJECT_ROLE("PROJECT_ROLE", "ui.datatype.project.role"),

	/** 计划 */
	PLAN("PLAN", "ui.datatype.plan"),

	/** 进度预警 */
	PLAN_WARNING("PLAN_WARNING", "ui.datatype.plan.warning"),

	/** 计划明细(任务) */
	PLAN_TASK("PLAN_TASK", "ui.datatype.plan.task"),

	/** 进度汇总 */
	PLAN_SUMMARY("PLAN_SUMMARY", "ui.datatype.plan.summary"),

	/** 进度汇总 */
	DELAY_ANALYSIS("DELAY_ANALYSIS", "ui.datatype.delay.analysis"),

	/** 进度明细 */
	PLAN_DETAIL("PLAN_DETAIL", "ui.datatype.plan.detail"),

	/** 计划明细(任务)评论 */
	PLAN_TASK_COMMENT("PLAN_TASK_COMMENT", "ui.datatype.plan.task.comment"),

	/** 计划调整 */
	PLAN_CHANGE("PLAN_CHANGE", "ui.datatype.plan.change"),

	/** 计划调整明细(任务) */
	PLAN_CHANGE_TASK("PLAN_CHANGE_TASK", "ui.datatype.plan.change.task"),

	/** 计划调整明细(任务)评论 */
	PLAN_CHANGE_TASK_COMMENT("PLAN_CHANGE_TASK_COMMENT", "ui.datatype.plan.change.task.comment"),

	/** 计划历史版本 */
	PLAN_HISTORY("PLAN_HISTORY", "ui.datatype.plan.history"),

	/** 计划历史版本明细(任务) */
	PLAN_HISTORY_TASK("PLAN_HISTORY_TASK", "ui.datatype.plan.history.task"),

	/** 计划历史版本明细(任务)评论 */
	PLAN_HISTORY_TASK_COMMENT("PLAN_HISTORY_TASK_COMMENT", "ui.datatype.plan.history.task.comment"),

	/** 计划 */
	PLAN_OVERALL_PROGRESS("PLAN_OVERALL_PROGRESS", "ui.menu.item.plan.overall.progress"),
	PLAN_OVERALL_PROGRESS2("PLAN_OVERALL_PROGRESS", "ui.menu.item.plan.overall.progress"),

	/** 实际进度 */
	PLAN_PROGRESS("PLAN_PROGRESS", "ui.datatype.plan.progress"),

	/** 实际进度明细(任务) */
	PLAN_PROGRESS_TASK("PLAN_PROGRESS_TASK", "ui.datatype.plan.progress.task"),

	/** 计划日历 */
	PLAN_CALENDAR("PLAN_CALENDAR", "ui.datatype.plan.calendar"),

	/** 计划日历例外 */
	PLAN_CALENDAR_EXCEPTIONS("PLAN_CALENDAR_EXCEPTIONS", "ui.datatype.plan.calendar.exceptions"),

	/** 站点和中继段相关的预设任务 */
	PRESET_TASK("PRESET_TASK", "ui.datatype.preset.task"),

	/** 站点 */
	SITE("SITE", "ui.datatype.site"),

	/** 中继段 */
	SEGMENT("SEGMENT", "ui.datatype.segment"),

	/** 链路 */
	LINK("LINK", "ui.datatype.link"),

	/** 项目检查 */
	PROJECT_CHECK("PROJECT_CHECK", "ui.datatype.project.check"),

	/** 项目检查专家 */
	PROJECTCHECK_EXPERT("PROJECTCHECK_EXPERT", "ui.datatype.project.check.expert"),

	/** 项目巡查 */
	PROJECT_INSPECT("PROJECT_INSPECT", "ui.datatype.project.inspect"),

	/** 项目巡查专家 */
	PROJECTINSPECT_EXPERT("PROJECTINSPECT_EXPERT", "ui.datatype.project.inspect.expert"),

	/** 验收信息 */
	ACCEPTANCE_CHECK("ACCEPTANCE_CHECK", "ui.datatype.acceptance.check"),

	/** 竣工信息 */
	COMPLETED_INFO("COMPLETED_INFO", "ui.datatype.process.completed"),

	/** 通知公告 */
	NOTICE_ANNOUNCEMENT("NOTICE_ANNOUNCEMENT", "ui.datatype.notice.announcement"),
	
	/** 通知设置 */
	NOTIFY_SETTING("NOTIFY_SETTING", "ui.datatype.notice.setting"),

	/** 消息通知 */
	NOTICE_NOTIFY("NOTICE_NOTIFY", "ui.datatype.notice.notify"),

	/** 评审信息 */
	REVIEW("REVIEW", "ui.datatype.review"),

	/** 评审专家信息 */
	REVIEW_EXPERT("REVIEW_EXPERT", "ui.datatype.review.expert"),

	/** 评审专家意见 */
	REVIEW_EXPERT_COMMENT("REVIEW_EXPERT_COMMENT", "ui.datatype.review.expert.comment"),

	/** 专家 */
	EXPERT("EXPERT", "ui.datatype.expert"),

	/** 职务 */
	EXPERT_PROFESSIONAL_TITLE("EXPERT_PROFESSIONAL_TITLE", "ui.datatype.expert.professional.title"),

	/** 专家注册职业资格 */
	EXPERT_QUALIFICATION("EXPERT_QUALIFICATION", "ui.datatype.expert.qualification"),

	/** 专家成果 */
	EXPERT_ACHIEVEMENT("EXPERT_ACHIEVEMENT", "ui.datatype.expert.achievement"),

	/** 问题 */
	ISSUE("ISSUE", "ui.datatype.issue"),

	/** 问题评论 */
	ISSUE_COMMENT("ISSUE_COMMENT", "ui.datatype.issue.comment"),

	/** 档案文件 */
	ARCHIVE("ARCHIVE", "ui.datatype.archive"),

	/** 档案文件权限 */
	ARCHIVE_AUTHORITY("ARCHIVE_AUTHORITY", "ui.datatype.archive.authority"),

	/** 档案文件版本 */
	ARCHIVE_VERSION("ARCHIVE_VERSION", "ui.menu.item.archive.version"),

	/** 知识库文件 */
	KNOWLEDGE_BASE("KNOWLEDGE_BASE", "ui.datatype.knowledge.base"),

	/** 档案文件权限 */
	KNOWLEDGE_BASE_AUTHORITY("KNOWLEDGE_BASE_AUTHORITY", "ui.datatype.knowledge.base.authority"),

	/** 档案文件 */
	PAPER_LIBRARY("PAPER_LIBRARY", "ui.datatype.paper.library"),

	/** 档案文件借阅记录 */
	PAPER_LIBRARY_LEND_HISTORY("PAPER_LIBRARY_LEND_HISTORY", "ui.datatype.paper.library.lend.history"),

	/** 流程 */
	OA_FLOW_HISTORY("OA_FLOW_HISTORY", "ui.datatype.oa.flow.history"),

	/** 单位类型 */
	RELATED_UNIT_TYPE("RELATED_UNIT_TYPE", "ui.datatype.related.unit.type"),

	/** 区域 */
	REGION("REGION", "ui.datatype.region"),

	/** 单位分组 */
	RELATED_UNIT_GROUP("RELATED_UNIT_GROUP", "ui.datatype.related.unit.group"),

	/** 单位性质 */
	RELATED_UNIT_PROPERTY("RELATED_UNIT_PROPERTY", "ui.datatype.related.unit.property"),

	/** 招标类型 */
	TENDERING_TYPE("TENDERING_TYPE", "ui.fields.tendering_plan.tenderingtype"),

	/** 经营类别 */
	BUSINESS_TYPE("BUSINESS_TYPE", "ui.datatype.business.type"),

	/** 项目类别 */
	PROJECT_KIND("PROJECT_KIND", "ui.datatype.project.kind"),

	/** 币种 */
	CURRENCY("CURRENCY", "ui.datatype.currency"),

	/** 目前状态 */
	PROJECT_STATUS("PROJECT_STATUS", "ui.datatype.project.status"),
	
	/** 目前阶段 */
	PROJECT_STAGE("PROJECT_STAGE", "ui.datatype.project.stage"),

	/** 项目类型 */
	PROJECT_TYPE("PROJECT_TYPE", "ui.datatype.project.type"),

	/** 质量等级 */
	PROJECT_QUALITY_GRADE("PROJECT_QUALITY_GRADE", "ui.datatype.project.quality.grade"),

	/** 项目属性 */
	PROJECT_CATEGORY("PROJECT_CATEGORY", "ui.datatype.project.category"),

	/** 最终客户 */
	PROJECT_FINAL_CLIENT("PROJECT_FINAL_CLIENT", "ui.datatype.project.final.client"),

	/** 实施项目部 */
	PROJECT_IMPLEMENT_DEPT("PROJECT_IMPLEMENT_DEPT", "ui.datatype.project.implement.dept"),

	/** 签约主体 */
	PROJECT_SIGN_SUBJECT("PROJECT_SIGN_SUBJECT", "ui.datatype.project.sign.subject"),

	/** 专业类型 */
	PROJECT_PROFESSION_TYPE("PROJECT_PROFESSION_TYPE", "ui.datatype.project.profession.type"),

	/** 资质要求 */
	PROJECT_QUALIFICATION_REQ("PROJECT_QUALIFICATION_REQ", "ui.datatype.project.qualification.req"),

	/** 承包方式 */
	PROJECT_CONTRACT_METHOD("PROJECT_CONTRACT_METHOD", "ui.datatype.project.contract.method"),

	/** 付款方式 */
	PROJECT_PAYMENT_METHOD("PROJECT_PAYMENT_METHOD", "ui.datatype.project.payment.method"),

	/** 招标形式 */
	PROJECT_BIDDING_METHOD("PROJECT_BIDDING_METHOD", "ui.datatype.project.bidding.method"),

	/** 结算方式 */
	PROJECT_SETTLEMENT_METHOD("PROJECT_SETTLEMENT_METHOD", "ui.datatype.project.settlement.method"),

	/** 预算方式 */
	PROJECT_BUDGET_METHOD("PROJECT_BUDGET_METHOD", "ui.datatype.project.budget.method"),

	/** 重要程度 */
	PROJECT_IMPORTANCE("PROJECT_IMPORTANCE", "ui.datatype.project.importance"),

	/** 工程类型 */
	PROJECT_CLASS("PROJECT_CLASS", "ui.datatype.project.class"),

	/** 机房业主单位类型 */
	NETWORK_ROOM_OWNER_UNIT_TYPE("NETWORK_ROOM_OWNER_UNIT_TYPE", "ui.datatype.network.room.owner.unit.type"),

	/** 边缘节点类型 */
	EDGE_NODE_TYPE("EDGE_NODE_TYPE", "ui.datatype.edge.node.type"),

	/** 光纤类型 */
	FIBRE_TYPE("FIBRE_TYPE", "ui.datatype.fibre.type"),

	/** 检查类别 */
	PROJECT_CHECK_TYPE("PROJECT_CHECK_TYPE", "ui.datatype.project.check.type"),

	/** 检查类型 */
	PROJECT_CHECK_KIND("PROJECT_CHECK_KIND", "ui.datatype.project.check.kind"),

	/** 巡查类型 */
	PROJECT_INSPECT_TYPE("PROJECT_INSPECT_TYPE", "ui.datatype.project.inspect.type"),

	/** 检查结果 */
	CHECK_RESULT("CHECK_RESULT", "ui.datatype.check.result"),

	/** 验证结果 */
	VERIFY_RESULT("VERIFY_RESULT", "ui.datatype.verify.result"),

	/** 检查状态 */
	CHECK_STATUS("CHECK_STATUS", "ui.datatype.check.status"),

	/** 巡查状态 */
	INSPECT_STATUS("INSPECT_STATUS", "ui.datatype.inspect.status"),

	/** 巡查结果 */
	INSPECT_RESULT("INSPECT_RESULT", "ui.datatype.inspect.result"),

	/** 验收类别 */
	ACCEPTANCE_CHECK_TYPE("ACCEPTANCE_CHECK_TYPE", "ui.datatype.acceptance.check.type"),

	/** 验收类型 */
	ACCEPTANCE_CHECK_KIND("ACCEPTANCE_CHECK_KIND", "ui.datatype.acceptance.check.kind"),

	/** 验收结果 */
	ACCEPTANCE_CHECK_RESULT("ACCEPTANCE_CHECK_RESULT", "ui.datatype.acceptance.check.result"),

	/** 评审类别 */
	REVIEW_TYPE("REVIEW_TYPE", "ui.datatype.review.type"),

	/** 评审结果 */
	REVIEW_RESULT("REVIEW_RESULT", "ui.datatype.review.result"),

	/** 专家类型 */
	EXPERT_DOMAIN("EXPERT_DOMAIN", "ui.datatype.expert.domain"),

	/** 专家成就等级 */
	EXPERT_ACHIEVEMENT_GRADE("EXPERT_ACHIEVEMENT_GRADE", "ui.datatype.expert.achievement.grade"),

	/** 问题类别 */
	ISSUE_TYPE("ISSUE_TYPE", "ui.datatype.issue.type"),

	/** 问题优先级 */
	ISSUE_PRIORITY("ISSUE_PRIORITY", "ui.datatype.issue.priority"),

	/** 问题状态 */
	ISSUE_STATUS("ISSUE_STATUS", "ui.datatype.issue.status"),

	/** 质量规范 */
	QUALITY_SPECIFICATION("QUALITY_SPECIFICATION", "ui.datatype.quality.specification"),

	/** 质量目标 */
	QUALITY_OBJECTIVE("QUALITY_OBJECTIVE", "ui.datatype.quality.objective"),

	/** 质量上报 */
	QUALITY_REPORT("QUALITY_REPORT", "ui.datatype.quality.report"),

	/** 质量考核 */
	QUALITY_EXAMINE("QUALITY_EXAMINE", "ui.datatype.quality.examine"),

	/** 质量事故 */
	QUALITY_ACCIDENT("QUALITY_ACCIDENT", "ui.datatype.quality.accident"),

	/** 采购订单 */
	PURCHASE_ORDER("PURCHASE_ORDER", "ui.datatype.purchase.order"),

	/** 采购订单明细 */
	PURCHASE_ORDER_DETAIL("PURCHASE_ORDER_DETAIL", "ui.datatype.purchase.order.detail"),

	/** 招标计划明细 */
	TENDERING_PLAN("TENDERING_PLAN", "ui.datatype.tendering.plan"),

	/** 投标单位 */
	TENDERING("TENDEREE", "ui.datatype.tendering"),

	/** 投标单位计划关联 */
	TENDERING_PLAN_RELATEDUNIT("TENDERING_PLAN_RELATEDUNIT", "ui.datatype.tendering.plan.relatedunit"),

	/** 投标立项 */
	BIDDING_INFORMATION_APPROVAL("BIDDING_INFORMATION_APPROVAL", "ui.datatype.bidding.information.approval"),

	/** 项目报名 */
	PROJECT_REGISTRATION("PROJECT_REGISTRATION", "ui.datatype.project.registration"),

	/** 项目预审 */
	PREQUALIFICATION("PREQUALIFICATION", "ui.datatype.prequalification"),

	/** 投标基本资料 */
	BIDDING_BASIC_INFO("BIDDING_BASIC_INFO", "ui.datatype.bidding.basic.info"),

	/** 中标信息管理 */
	ACCEPTANCE_INFO("ACCEPTANCE_INFO", "ui.datatype.acceptance.info"),

	/** 落标信息管理 */
	OUTBIDDING_INFO("OUTBIDDING_INFO", "ui.datatype.outbidding.info"),

	/** 支出合同 */
	PAYMENT_CONTRACT("PAYMENT_CONTRACT", "ui.datatype.payment.contract"),

	/** 收入合同 */
	INCOME_CONTRACT("INCOME_CONTRACT", "ui.datatype.income.contract"),

	/** 支出合同结算 */
	PAYMENT_CONTRACT_STATEMENT("PAYMENT_CONTRACT_STATEMENT", "ui.datatype.payment.contract.statement"),

	/** 收入合同结算 */
	INCOME_CONTRACT_STATEMENT("INCOME_CONTRACT_STATEMENT", "ui.datatype.income.contract.statement"),

	/** 支出发票 */
	PAYMENT_INVOICE("PAYMENT_INVOICE", "ui.datatype.payment.invoice"),

	/** 收入发票 */
	INCOME_INVOICE("INCOME_INVOICE", "ui.datatype.income.invoice"),

	/** 合同台账 */
	CONTRACT_ACCOUNT("CONTRACT_ACCOUNT", "ui.datatype.contract.account"),

	/** 城市 */
	CITY("CITY", "ui.datatype.city"),

	/** 资金计划总表 */
	CAPITAL_PLAN_SUM("CAPITAL_PLAN_SUM", "ui.datatype.capital_plan_sum"),
	BUDGET_CAPITALPLANSUM("BUDGET_CAPITALPLANSUM", "ui.datatype.budget_estimate"),

	/** 资金计划构成表 */
	CAPITAL_PLAN("CAPITAL_PLAN", "ui.datatype.capital_plan"),
	BUDGET_CAPITALPLAN("BUDGET_CAPITALPLAN", "ui.datatype.budget_estimate"),

	/** 资金计划版本 */
	CAPITAL_PLAN_VERSION("CAPITAL_PLAN_VERSION", "ui.datatype.capital_plan_version"),

	/** 资金下达 */
	CAPITAL_RECEIVE("CAPITAL_RECEIVE", "ui.datatype.capital_receive"),
	
	/** 资金申请 */
	CAPITAL_APPLY("CAPITAL_APPLY", "ui.datatype.capital_apply"),
	
	/** 预算规划主表 */
	BUDGET_ESTIMATE_MAIN("BUDGET_ESTIMATE_MAIN", "ui.datatype.budget_estimate_main"),

	/** 预算规划版本 */
	BUDGET_ESTIMATE_VERSION("BUDGET_ESTIMATE_VERSION", "ui.datatype.budget_estimate_version"),

	/** 预算规划 */
	BUDGET_ESTIMATE("BUDGET_ESTIMATE", "ui.datatype.budget_estimate"),

	/** 资金计划统计 */
	BUDGET_STATISTICS("BUDGET_STATISTICS", "ui.datatype.budget_estimate"),

	/** 预算规划纯页面 */
	BUDGET_ESTIMATE_PAGE("BUDGET_ESTIMATE_PAGE", "ui.datatype.budget_estimate"),

	/** 预算规划变更 */
	BUDGET_ESTIMATE_CHANGE("BUDGET_ESTIMATE_CHANGE", "ui.datatype.budget_estimate_change"),

	/** 预算规划总表 */
	BUDGET_ESTIMATE_SUM("BUDGET_ESTIMATE_SUM", "ui.datatype.budget_estimate_sum"),

	/** 经费决算 */
	BUDGET_FINAL("BUDGET_ESTIMATE_FINAL", "ui.datatype.budget_estimate"),
	BUDGET_FINAL_PAGE("BUDGET_FINAL_PAGE", "ui.datatype.budget_final"),

	/** 经费决算版本 */
	BUDGET_FINAL_VERSION("BUDGET_FINAL_VERSION", "ui.datatype.budget_final_version"),

	/** 预算执行 */
	BUDGET_IMPLEMENTATION("BUDGET_IMPLEMENTATION", "ui.datatype.budget_implementation"),

	/** 账户信息 */
	ACCOUNT_INFO("ACCOUNT_INFO", "ui.datatype.account_info"),

	/** 预算模板 */
	BUDGET_TEMPLATE("BUDGET_TEMPLATE", "ui.datatype.budget_template"),

	/** 预算费用报销记录 */
	BUDGET_FEE("BUDGET_FEE", "ui.datatype.budget_fee"),
	
	/** 预算费用报销记录主表 */
	BUDGET_MAINFEE("BUDGET_MAINFEE", "ui.datatype.budget_mainfee"),

	/** 合同预算 */
	CONTRACT_BUDGET("CONTRACT_BUDGET", "ui.datatype.contract_budget"),

	/** 一级分类 */
	FIRST_CLASSIFY_TYPE("FIRST_CLASSIFY_TYPE", "ui.fields.capital_plan.first_classify"),

	/** 二级分类 */
	SECOND_CLASSIFY_TYPE("SECOND_CLASSIFY_TYPE", "ui.fields.capital_plan.second_classify"),

	/** 三级分类 */
	THIRD_CLASSIFY_TYPE("THIRD_CLASSIFY_TYPE", "ui.fields.capital_plan.third_classify"),

	/** 分类一 */
	CLASSIFY_FIRST("CLASSIFY_FIRST", "ui.fields.budget_estimate.classify1"),

	/** 分类二 */
	CLASSIFY_SECOND("CLASSIFY_SECOND", "ui.fields.budget_estimate.classify2"),

	/** 分类三 */
	CLASSIFY_THIRD("CLASSIFY_THIRD", "ui.fields.budget_estimate.classify3"),

	/** 分类四 */
	CLASSIFY_FOUR("CLASSIFY_FOUR", "ui.fields.budget_estimate.classify4"),

	/** 分类五 */
	CLASSIFY_FIVE("CLASSIFY_FIVE", "ui.fields.budget_estimate.classify5"),

	/** 流程设置 */
	FLOWABLE_SETTING("FLOWABLE_SETTING", "ui.menu.group.flowable.setting"),

	/** 流程监控 */
	FLOWABLE_MONITOR("FLOWABLE_MONITOR", "ui.menu.group.flowable.monitor"),

	/** 流程监控-我发起的流程 */
	FLOWABLE_INITIATED_PROCESS("FLOWABLE_INITIATED_PROCESS", "ui.fields.flowable.monitor.initiated.process"),

	/** 流程监控-待审核的流程 */
	FLOWABLE_PENDING_PROCESS("FLOWABLE_PENDING_PROCESS", "ui.fields.flowable.monitor.pending.process"),

	/** 流程监控-已办的流程 */
	FLOWABLE_DONE_PROCESS("FLOWABLE_DONE_PROCESS", "ui.fields.flowable.monitor.done.process"),

	/** 付款类型 */
	PAYMENT_TYPE("PAYMENT_TYPE", "ui.fields.purchase.order.payment.type"),

	/** 申购类型 */
	PURCHASE_TYPE("PURCHASE_TYPE", "ui.fields.purchase.order.purchase.type"),

	/** 申购类型： 设备、服务 **/
	PURCHASE_KIND("PURCHASE_KIND", "ui.fields.purchase.order.purchase.kind"),

	/** 任务问题类型 */
	PLAN_TASK_ISSUES_TYPE("PLAN_TASK_ISSUES_TYPE", "ui.fields.plan.task.issueType"),

	/** 用章类型 */
	SEAL_TYPE("SEAL_TYPE", "ui.fields.contract.seal.type"),

	/** 资金来源 */
	CAPITAL_SOURCE("CAPITAL_SOURCE", "ui.fields.capital_receive.source"),
	
	/** 预算类型 */
	BUDGET_TYPE("BUDGET_TYPE", "ui.fields.budget_estimate.budgetType"),

	/** 招标采购关联 */
	TENDERING_PURCHASE("TENDERING_PURCHASE", "ui.datatype.tendering.purchase");

	private String value;

	private String resourceName;

	private DataTypeEnum(String value, String resourceName) {
		this.value = value;
		this.resourceName = resourceName;
	}

	/**
	 * 根据枚举的值获取对应的资源名。
	 * 
	 * @param value 枚举值
	 * @return 资源名
	 */
	public static String getResouceName(String value) {
		for (DataTypeEnum s : DataTypeEnum.values()) {
			if (s.value().equals(value)) {
				return s.resourceName;
			}
		}
		return null;
	}

	public String value() {
		return this.value;
	}

	public String resourceName() {
		return this.resourceName;
	}
}
