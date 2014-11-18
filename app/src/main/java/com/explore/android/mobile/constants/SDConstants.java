package com.explore.android.mobile.constants;

/**
 * 
 * The constants of SD system in explore-app.
 * @author Ryan
 *
 */
public class SDConstants {

	public static final String WHS_01 = "01"; // 良品

	public static final String WHS_02 = "02"; // 超期

	public static final String WHS_50 = "50"; // 返厂

	public static final String WHS_99 = "99"; // 报废
	
	public static final String WHS_49 = "49"; // 打包

    public static final String WHS_89 = "89"; // 残损

    public static final String WHS_ZP = "ZP"; // 赠品

    public static final String WHS_TJ = "59"; //  特价
	
	public static final String QTYUNIT_BOX = ""; // 箱包装
	
	public static final String QTYUNIT_Bottle = ""; // 瓶包装
	
	public static final String YN_Y = "Y";	// 是
	
	public static final String YN_N = "N"; 	// 否
	
	//====================================================
	// OUTTYPE
	//====================================================
	
	public static final String OUTTYPE_SAL = "SAL"; // 销售发货

	public static final String OUTTYPE_POL = "POL"; // 采购退货

	public static final String OUTTYPE_OTI = "OTI"; // 调拨发货

	public static final String OUTTYPE_REL = "REL"; // 报损

	public static final String OUTTYPE_SAF = "SAF"; // 飞单发货

	public static final String OUTTYPE_CBF = "CBF"; // 拆包发货

	public static final String OUTTYPE_DBF = "DBF"; // 打包发货

	public static final String OUTTYPE2_DTC = "DTC"; // 发货调货

	public static final String OUTTYPE2_TTC = "TTC"; // 退货调货

	public static final String OUTTYPE2_GEN = "GEN"; // 通用调货
	
	//====================================================
	// OUTSTS 销售状态
	//====================================================
	
	public static final String OUTSTS_NEW = "NEW";	// 待提交（新建）
	
	public static final String OUTSTS_FIE = "FIE";	// 待财务审核
	
	public static final String OUTSTS_REJ = "REJ";	// 已驳回
	
	public static final String OUTSTS_TSM = "TSM";	// 转交
	
	public static final String OUTSTS_RMK = "RMK";	// 加签
	
	public static final String OUTSTS_DSH = "DSH";	// 待送货
	
	public static final String OUTSTS_DQS = "DQS";	// 待签收
	
	public static final String OUTSTS_CLO = "CLO";	// 签收完成
	
	//====================================================
	// OUTSTS 销售状态
	//====================================================
	public static final String PRODUCT_STS_NEW = "NEW";	// 可用
	
	public static final String PRODUCT_STS_RUN = "RUN";	// 可用
	
	//====================================================
	// CUSTOMER DETAIL DEPT 客户经营单位相关常量转换
	//====================================================
	public static final String CUSTOMER_YES = "Y";
	
	public static final String CUSTOMER_NO = "N";
	
	public static final String CUSTOMER_ZQ_GDR = "GDR"; // 账期类型-固定日
	
	public static final String CUSTOMER_ZQ_GDT = "GDT"; // 账期类型-固定天
	
	public static final String CUSTOMER_DZ_JSD = "JSD";	// 对账类型-及时对
	
	public static final String CUSTOMER_DZ_ZQD = "ZQD";	// 队长类型-周期对
	
	//====================================================
	// GENSTS 状态常量转换
	//====================================================
	public static final String GENERAL_NEW = "NEW";		// 新建状态
	
	public static final String GENERAL_LOC = "LOC";		// 锁定状态
	
	public static final String GENERAL_RUN = "RUN";		// 可用状态
	
}
