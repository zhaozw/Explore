package com.explore.android.mobile.constants;

public class RequestConstants {
	
	public static final int SEARCH_PAGE_SIZE = 20;
	
	public static final String REQURL = "url";
	
	public static final String REQNAME = "request";
	
	public static final String REQDATA = "data";
	
	public static final int REQUEST_TIMTOUT = 8000;
	
	public static final String ENCODETYPE = "UTF-8";

	public static final String LOGIN = "login.do?command=android";// 服务器请求URL常量：登录请求

	public static final String ADRESSBOOK = "addressbook.do?command=android";// 服务器请求URL常量：获取联系人列表请求

	public static final String REGISTER = "register.do?command=android";// 服务器请求URL常量：注册设备请求

	public static final String SYNC = "sync.do?command=android";// 服务器请求URL常量：同步数据请求


	public static final String TEST_CONNECTION = "default.do?command=android";// 服务器请求URL常量：测试服务器连接请求

	public static final String P2P_INFO = "p2pinfo.do?command=android";// 服务器请求URL常量：服务器探针请求

	//================================
	// 位置信息相关 LOCATION
	//================================
	public static final String LOCATION_UPLOAD = "location.do?command=androidupload";// 服务器请求URL常量：上传位置信息请求

	public static final String LOCATION_GET = "location.do?command=androidget";// 服务器请求URL常量：上传位置信息请求

	//================================
	// 发货相关 TRANSPORT
	//================================
	public static final String TRANSPORT_SEARCH = "transport.do?command=androidlist"; // 发货查询

	public static final String TRANSPORT_DETAIL = "transport.do?command=androiddetail"; // 发货详情

	public static final String TRANSPORT_TRANSPORTLINE1 = "transport.do?command=androidtransportline1"; // 发货商品明细

	public static final String TRANSPORT_TRANSPORTLINE2LOC = "transport.do?command=androidtransportline2loc"; // 查看发货货位信息
	
	public static final String TRANSPORT_TRANSPORTLINE2 = "transport.do?command=androidtransportline2"; // 拣货商品明细

	//================================
	// 库存相关 STOCKLINE
	//================================
	public static final String STOCK_SEARCH = "stockline.do?command=androidlist"; // 库存查询
	
	public static final String STOCK_SEARCHPRE = "stockline.do?command=androidpre"; // 库存查询初始化
	
	public static final String STOCK_DETAIL = "stockline.do?command=androiddetail"; // 库存详情
	
	//================================
	// 审核相关 AUDIT
	//================================	
	public static final String AUDIT_LIST = "audit.do?command=androidlist"; // 服务器请求URL常量：获取审核列表请求
	
	public static final String AUDIT_ACTION = "audit.do?command=android"; // 审核操作请求

	public static final String AUDIT_DETAIL = "audit.do?command=androidinfo"; // 服务器请求URL常量：获取审核详情请求

	public static final String AUDIT_DEPT = "dept.do?command=androidgetsubemps"; // 服务器请求URL常量：根据项目组获取该项目组的人员信息

	public static final String AUDIT_SIGN_EMP = "dept.do?"; // 加签人员选择（*）
	
	public static final String AUDIT_TRANS_EMP = "dept.do?"; // 转交人员选择（*）
	
	//================================
	// 日程相关 SCHEDULE
	//================================	
	public static final String SCHEDULE = "schedule.do?command=android";// 服务器请求URL常量：获取日程信息请求

	public static final String SCHEDULE_UPDATE = "schedule.do?command=androidupdate";// 服务器请求URL常量：修改日程信息请求
	
	//================================
	// 客户相关 CUSTOMER
	//================================	
	public static final String CUSTOMER_SEARCH = "customer.do?command=android"; // 客户查询
	
	public static final String CUSTOMER_SEARCHBY_KEYWORD = "customer.do?command=androidkeyword"; // 根据关键字查询客户
	
	public static final String CUSTOMER_DETAIL = "customer.do?command=androiddetail"; // 客户详情
	
	public static final String CUSTOMER_ADDPRE = "customer.do?command=androidaddpre"; // 客户修改预处理

	public static final String CUSTOMER_ADD = "customer.do?command=androidadd"; // 客户新建
	
	public static final String CUSTOMER_MODIFY = "customer.do?command=androidmodify"; // 客户修改

	public static final String CUSTOMER_MODIFYPRE = "customer.do?command=androidmodifypre"; // 客户修改预处理
	
	public static final String CUSTOMER_COMMIT = "customer.do?command=androidcommit"; // 客户提交
	
	public static final String CUSTOMER_DELETE = "customer.do?command=androiddelete"; // 客户删除
	
	//================================
	// 销售相关 OUT
	//================================
	public static final String OUT_OUTSEARCH = "out.do?command=androidlist"; // 销售查询
	
	public static final String OUT_GET_CUSTOMERDEPT = "customerdept.do?command=android"; // 初始化经营单位下拉
	
	public static final String OUT_GET_DEPT_EXTRA = "customerdeptextend.do?command=android"; // 经营单位变化引起的联动
	
	public static final String OUT_GET_SOLOGON = ""; // shipTo的变化引起的联动
	
	public static final String OUT_GET_PROJECT_EXTRA = "customerdeptextend.do?command=androidproject"; // 经营单位变化引起的联动
	
	public static final String OUT_GET_DETAIL = "out.do?command=androiddetail"; // 销售详情查询
	
	public static final String OUT_GET_AUDITINFO = "out.do?command=androidtaskline"; // 销售审核信息

	public static final String OUT_DELETE = "out.do?command=androiddelete"; // 删除销售信息
	
	public static final String OUT_UPDATE = "out.do?command=androidupdate"; // 销售信息修改
	
	public static final String OUT_UPDATEPRE = "out.do?command=androidupdatepre"; // 销售信息修改初始化
	
	public static final String OUT_COMMIT = "out.do?command=androidcommit"; // 销售信息修改
	
	public static final String OUTSAL_CREATE_SUBMIT = "out.do?command=androidaddsal"; // 新建销售发货
	
	public static final String OUTTTC_CREATE_SUBMIT = "out.do?command=androidaddttc"; // 新建退货调货

	public static final String OUTDTC_CREATE_SUBMIT = "out.do?command=androidadddtc"; // 新建送货调货
	
	public static final String OUTDTC_CREATE_PRE = "out.do?command=androidadddtcpre"; // 新建送货调货之前先根据发货编号查询送货信息
	
	public static final String OUT_GET_SHIPTO_EXTRA = "out.do?command=androidaddpre"; // 配送到引起的联动
	
	//================================
	// 商品明细相关 OUTLINE
	//================================
	public static final String OUTLINE_GET_LIST = "outline.do?command=androidoutline1listbyoutid"; // 销售详情明细查询（查询行信息）
	
	public static final String OUTLINE_ADD = "outline.do?command=androidaddline1"; // outline1添加
	
	public static final String OUTLINE_ADD_PREDATA = "outline.do?command=androidaddpre"; // outline1添加数据初始化
	
	public static final String OUTLINE_UPDATE_PREDATA = "outline.do?command=androidupdatepre"; // outline1添加数据初始化
	
	public static final String OUTLINE_UPDATE = "outline.do?command=androidupdate"; // outline1数据修改

	public static final String OUTLINE_DELETE = "outline.do?command=androiddelete"; // outline1数据修改
	
	//================================
	// 商品相关 PRODUCT
	//================================
	public static final String PRODUCT_SEARCH = "product.do?command=androidlist"; // 商品查询
	
	public static final String PRODUCT_DETAIL = "product.do?command=androiddetail"; // 商品查询

	public static final String PRODUCT_CREATE = "product.do?command=androidadd"; // 新建商品
	
	public static final String OUT_SEARCH_PRODUCT = "product.do?command=androidsearchbykeywords"; // 在销售中根据商品信息（名称或者编码）查询商品
	
	public static final String PRODUCT_CREATE_PRE = "product.do?command=androidaddpre"; // 新建商品初始化
	
	public static final String PRODUCT_MODIFY = "product.do?command=androidmodify"; // 新建商品
	
	public static final String PRODUCT_MODIFY_PRE = "product.do?command=androidmodifypre"; // 新建商品初始化
	
	public static final String PRODUCT_DELETE = "product.do?command=androiddelete"; // 删除商品
	
	public static final String PRODUCT_COMMIT = "product.do?command=androidcommit"; // 提交商品
}
