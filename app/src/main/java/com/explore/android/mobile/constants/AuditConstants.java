package com.explore.android.mobile.constants;

/**
 * 审核相关常量
 * @author ryan
 *
 */
public class AuditConstants {
	
	public static final String ISTEMPPOSITION = "ISTEMPPOSITION";

	public static final String DEV_TITLE = "DEVELOP"; 		// 开发委任
	public static final String EXP_TITLE = ""; 				// 费用报销
	public static final String LOA_TITLE = "LOAN"; 			// 借款
	public static final String LOS_TITLE = "LOANOFFSET"; 	// 还款
	public static final String PRO_TITLE = "PROJECT"; 		// 项目
	public static final String REC_TITLE = ""; 				// 招聘
	public static final String WKR_TITLE = ""; 				// 工作报告
	public static final String PLA_TITLE = ""; 				// 计划
	public static final String ACM_TITLE = ""; 				// 绩效
	public static final String MET_TITLE = ""; 				// 会议管理
	public static final String POP_TITLE = ""; 				// 采购报价
	public static final String INS_TITLE = ""; 				// 销售退货
	public static final String SOP_TITLE = ""; 				// 销售报价
	public static final String TRA_TITLE = ""; 				// 出差申请
	public static final String OUT_TITLE = ""; 				// 销售发货

	public static final String BILLTYPE_DEV = "DEV"; // 开发委任
	public static final String BILLTYPE_EXP = "EXP"; // 费用报销
	public static final String BILLTYPE_LOA = "LOA"; // 借款
	public static final String BILLTYPE_LOS = "LOS"; // 还款
	public static final String BILLTYPE_PRO = "PRO"; // 项目
	public static final String BILLTYPE_REC = "REC"; // 招聘
	public static final String BILLTYPE_WKR = "WKR"; // 工作报告
	public static final String BILLTYPE_PLA = "PLA"; // 计划
	public static final String BILLTYPE_ACM = "ACM"; // 绩效
	public static final String BILLTYPE_MET = "MET"; // 会议管理
	public static final String BILLTYPE_POP = "POP"; // 采购报价
	public static final String BILLTYPE_INS = "INS"; // 销售退货
	public static final String BILLTYPE_SOP = "SOP"; // 销售报价
	public static final String BILLTYPE_TRA = "TRA"; // 出差申请
	public static final String BILLTYPE_OUT = "OUT"; // 销售发货
	
	public static final String BILLTYPE_BLT = "BLT"; // 公告管理
	public static final String BILLTYPE_POB = "POB"; // 订单
	public static final String BILLTYPE_ADC = "ADC"; // 考勤
	public static final String BILLTYPE_VAT = "VAT"; // 开票
	public static final String BILLTYPE_BAK = "BAK"; // 银行
	public static final String BILLTYPE_ACC = "ACC"; // 账户
	public static final String BILLTYPE_BUI = "BUI"; //
	public static final String BILLTYPE_PPL = "PPL"; // 采购批次
	public static final String BILLTYPE_SPL = "SPL"; // 销售批次
	public static final String BILLTYPE_AR = "AR";   // 应收管理

	// 服务器端返回的json数据 数据名
	public static final String USER = "USER";			// Audit操作常量:不选项目组，直接选用户
	public static final String PROJ = "PROJ";			// Audit操作常量:选择项目组下的人员
	public static final String OPS = "OPS"; 			// Audit操作常量:选择所在经营单位的所有人员
	public static final String AUDITSIGN = "AUDITSIGN";	// Audit操作常量:加签人员
	public static final String MYSELF = "MYSELF"; 		// 是否可以选自己
	public static final String TYPE = "TYPE"; 			// 审核类型
	public static final String TYPE_YES = "YES";
	public static final String TYPE_NO = "NO";
	public static final String TYPE_TRANSMIT = "TRANSMIT";
	public static final String TYPE_REMARK = "REMARK";
	public static final String YES = "YES";			// Audit操作常量：通过
	public static final String NO = "NO";			// Audit操作常量：驳回

	// 隐藏信息(不显示)
	public static final String DEPTID = "DEPTID";

	// Audit info信息
	public static final String REMARK = "REMARK"; 				// 备注
	public static final String STS = "STS";						// 当前状态
	public static final String DEPT = "DEPTNAMECN"; 			// 所属部门
	public static final String OPEDEPT = "OPERATIONDEPTNAMECN"; // 所属经营单位
	public static final String CODE = "CODE";					// 单据编号
	public static final String BILLTYPE = "BILLTYPE";			// 单据类型
	public static final String CREBYNAME = "CREATEBYNAME";		// 创建人
	public static final String CREBYTIME = "CEREATEBYTIME";		// 创建时间
	public static final String COMBYNAME = "COMMITBYNAME";		// 提交人
	public static final String COMBYTIME = "COMMITBYTIME";		// 提交时间
	public static final String CONTENT = "contentOrDescription";// 摘要
	public static final String PAYTYPE = "PAYTYPE";				// 支付方式
	public static final String STARTDATE = "STARTDATE";			// 开始日期
	public static final String ENDDATE = "ENDDATE";				// 结束日期
	public static final String YEAR = "YEAR";					// 年份
	public static final String MONTH = "MONTH";					// 月份
	public static final String NAME = "name";					// 名称
	
	/**
	 * 开发审核相关
	 */
	public static final String DEV_PROJECT = "projectName";		// 所属项目
	public static final String DEV_CON = "contentOrDescription";		// 开发内容
	
	/**
	 * 费用报销审核相关
	 */
	public static final String EXP_TYPE2 = "expenseType2"; 		// 费用类别
	public static final String EXP_NCCODE = "ncCode"; 			// NC凭证
	public static final String EXP_REALNAME = "realName"; 		// 支付人员
	public static final String EXP_CURRENCY = "currency";  		// 支付币种
	public static final String EXP_PAYTYPE2 = "payType2";  		// 支付方式
	public static final String EXP_BANK = "bankId"; 			// 支付银行
	public static final String EXP_ACCOUNTNAME = "accountName"; // 支付帐号P1
	public static final String EXP_ACCOUNT = "account"; 		// 支付帐号P2
	public static final String EXP_PAYDATE = "payDate"; 		// 预计支付日期
	public static final String EXP_PAYDATE2 = "payDate2"; 		// 实际支付日期
	public static final String EXP_PROFROM = "provinceFrom"; 	// 起始省份
	public static final String EXP_CITYFROM = "cityFrom"; 		// 起始城市
	public static final String EXP_PROTO = "provinceTo"; 		// 到达省份
	public static final String EXP_CITYTO = "cityTo"; 			// 到达城市
	public static final String EXP_CLBEGINDATE = "clBeginDate"; // 出发日期
	public static final String EXP_CLENDDATE = "clEndDate"; 	// 返回日期
	
	public static final String EXP_CLPROFROM = "clProvinceFrom"; // 出发地 P1
	public static final String EXP_CLCITYFROM = "clCityFrom"; 	// 出发地P2
	public static final String EXP_CLPROTO = "clProvinceTo"; 	// 到达地P1
	public static final String EXP_CLCITYTO = "clCityTo"; 		// 到达地P2
	
	/**
	 * 借款审核相关
	 */
	public static final String LOA_ACCOUNT = "PAYBANKACCOUNT"; // 支付账号
	public static final String LOA_TYPE = "LOANTYPE"; // 借款类型
	public static final String LOA_REASON = "LOANREASON";// 借款原因
	public static final String LOA_RETIME = "RETURENDATE";// 预计还款日期
	public static final String LOA_MONEY = "TOTALMONEY";// 借款金额
	
	/**
	 * 还款审核相关
	 */
	public static final String LOS_RELATIONTYPE = ""; // 冲抵类型
	public static final String LOS_RETURNMONEY = ""; // 已还金额
	
	/**
	 * 项目审核相关
	 */
	
	/**
	 * 招聘审核相关
	 */
	public static final String REC_NAME = ""; // 招聘名称
	
	/**
	 * 工作报告审核相关
	 */
	public static final String WKR_NAME = ""; // 工作报告名称
	
	/**
	 * 计划审核相关
	 */
	public static final String PLA_NAME = ""; // 工作计划名称
	
	/**
	 * 绩效审核相关
	 */
	public static final String ACM_NAME = ""; // 绩效名称
	public static final String ACM_TYPE = ""; // 绩效类型
	
	/**
	 * 会议审核相关
	 */
	public static final String MET_CON = ""; // 会议内容
	
	/**
	 * 采购报价审核相关
	 */
	public static final String POP_LOGONCODE = ""; // 批次P1
	public static final String POP_LOGON = ""; // 批次P2
	public static final String POP_PROJECT = ""; // 项目
	public static final String POP_VENDOR = ""; // 供应商
	
	/**
	 * 销售退货审核相关
	 */
	public static final String INS_INCODE1 = ""; // 收货编号
	public static final String INS_INCODE2 = ""; // 收获附属编号
	public static final String INS_WAREHOUSE = ""; // 仓库
	public static final String INS_INQTY1 = ""; // 收获数量
	public static final String INS_INQTY2 = ""; // 理货数量
	public static final String INS_INMONEY1 = ""; // 收货金额
	public static final String INS_INMONEY2 = ""; // 理货金额
	
	/**
	 * 销售报价审核相关
	 */
	public static final String SOP_LOGONCODE = ""; // 批次P1
	public static final String SOP_LOGON = ""; // 批次P2
	
	/**
	 * 出差审核相关
	 */
	public static final String TRA_TYPE = ""; // 出差类型
	public static final String TRA_TRANSPORT = ""; // 交通工具
	public static final String TRA_PURPOSE = ""; // 出差目的
	public static final String TRA_TASK = ""; // 出差任务
	public static final String TRA_MONEY = ""; // 出差金额
	public static final String TRA_ISLOAN = ""; // 是否借款
	
	/**
	 * 销售发货审核相关
	 */
	public static final String OUT_CODE2 = ""; // 销售附属编号 
	public static final String OUT_TYPE = ""; // 销售类型
	public static final String OUT_TIME = ""; // 发货时间
	public static final String OUT_QTY1 = ""; // 销售数量
	public static final String OUT_MONEY1 = ""; // 销售金额
	public static final String OUT_TRAQTY1 = ""; // 发货数量
	public static final String OUT_TRAQTY2 = ""; // 捡货数量
	public static final String OUT_TRAMONEY1 = ""; // 发货金额
	public static final String OUT_TRAMONEY2 = ""; // 捡货金额
	public static final String OUT_CUSDEPT = ""; // 经营单位
	public static final String OUT_SHIPTO = ""; // 配送到
	public static final String OUT_VATTO = ""; // 开票到
	public static final String OUT_WAREHOUSE = ""; // 调拨仓库
	public static final String OUT_AMOUNTMONEY = ""; // 额度
	public static final String OUT_PAYMONEY = ""; // 超帐期
}
