package com.explore.android.mobile.activity.audit;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import com.explore.android.R;
import com.explore.android.core.model.ResourceKeyValue;
import com.explore.android.mobile.constants.AuditConstants;
import com.explore.android.mobile.model.SingleAuditInfo;

public class AuditManager {

	public static final int TYPE_DEV = 1; 	// 开发委任
	public static final int TYPE_EXP = 2; 	// 费用报销
	public static final int TYPE_PRO = 5; 	// 项目
	public static final int TYPE_WKR = 7; 	// 工作报告
	public static final int TYPE_LOA = 3; 	// 借款
	public static final int TYPE_LOS = 4; 	// 还款
	public static final int TYPE_PLA = 8; 	// 计划
	public static final int TYPE_ACM = 9; 	// 绩效
	public static final int TYPE_REC = 6; 	// 招聘
	public static final int TYPE_MET = 10; 	// 会议管理
	public static final int TYPE_TRA = 11; 	// 出差申请

	//--SD相关--
	public static final int TYPE_POP = 60; 	// 采购报价
	public static final int TYPE_INS = 61; 	// 销售退货
	public static final int TYPE_SOP = 62;	// 销售报价
	public static final int TYPE_OUT = 63; 	// 销售发货
	

	private AuditManager() {
	}

	public static AuditManager instance;

	public static AuditManager getInstance() {
		if (instance == null) {
			instance = new AuditManager();
		}
		return instance;
	}

	public List<SingleAuditInfo> getAuditDetail(int type, JSONObject data) throws JSONException {
		
		List<SingleAuditInfo> list = new ArrayList<SingleAuditInfo>();
		
		switch (type) {
		case TYPE_DEV:
			list = getDevResourcesId(data.getJSONObject(AuditConstants.DEV_TITLE));
			break;
		case TYPE_EXP:
			list = getExpResourcesId(data.getJSONObject(AuditConstants.EXP_TITLE));
			break;
		case TYPE_LOA:
			list = getLoaResourcesId(data.getJSONObject(AuditConstants.LOA_TITLE));
			break;
		case TYPE_LOS:
			list = getLosResourcesId(data.getJSONObject(AuditConstants.LOS_TITLE));
			break;
		case TYPE_PRO:
			list = getProResourcesId(data.getJSONObject(AuditConstants.PRO_TITLE));
			break;
		case TYPE_REC:
			list = getRecResourcesId(data.getJSONObject(AuditConstants.REC_TITLE));
			break;
		case TYPE_WKR:
			list = getWkrResourcesId(data.getJSONObject(AuditConstants.WKR_TITLE));
			break;
		case TYPE_PLA:
			list = getPlaResourcesId(data.getJSONObject(AuditConstants.PLA_TITLE));
			break;
		case TYPE_ACM:
			list = getAcmResourcesId(data.getJSONObject(AuditConstants.ACM_TITLE));
			break;
		case TYPE_MET:
			list = getMetResourcesId(data.getJSONObject(AuditConstants.MET_TITLE));
			break;
		case TYPE_POP:
			list = getPopResourcesId(data.getJSONObject(AuditConstants.POP_TITLE));
			break;
		case TYPE_INS:
			list = getInsResourcesId(data.getJSONObject(AuditConstants.INS_TITLE));
			break;
		case TYPE_SOP:
			list = getSopResourcesId(data.getJSONObject(AuditConstants.SOP_TITLE));
			break;
		case TYPE_TRA:
			list = getTraResourcesId(data.getJSONObject(AuditConstants.TRA_TITLE));
			break;
		case TYPE_OUT:
			list = getOutResourcesId(data.getJSONObject(AuditConstants.OUT_TITLE));
			break;
		default:
			break;
		}
		
		return list;
	}

	/**
	 * 开发审核
	 * @param json
	 * @return
	 * @throws org.json.JSONException
	 */
	private List<SingleAuditInfo> getDevResourcesId(JSONObject json) throws JSONException{
		
		List<SingleAuditInfo> list = new ArrayList<SingleAuditInfo>();
		
		List<ResourceKeyValue> keyList = new ArrayList<ResourceKeyValue>();
		keyList.add(new ResourceKeyValue("DEVELOPNAME", R.string.audit_info_dev_name));
		keyList.add(new ResourceKeyValue("DEVELOPCODE", R.string.audit_info_billcode));
		keyList.add(new ResourceKeyValue("DEVELOPCONTENT", R.string.audit_info_dev_content));
		keyList.add(new ResourceKeyValue("PROJECTNAME", R.string.audit_info_dev_pro));
		keyList.add(new ResourceKeyValue("STSNAME", R.string.audit_info_sts));
		keyList.add(new ResourceKeyValue("CREATEBYNAME", R.string.audit_info_crebyname));
		keyList.add(new ResourceKeyValue("CREATEBYTIME", R.string.audit_info_crebytime));
		
		for(int i = 0; i < keyList.size(); i++){
			String key = keyList.get(i).getKey();
			if(json.has(key)){
				SingleAuditInfo info = new SingleAuditInfo();
				info.setKeyName(key);
				info.setResourceId(keyList.get(i).getValue());
				info.setContent(json.getString(key));
				list.add(info);
			}
		}
		
		return list;
	}

	/**
	 * 项目审核
	 * @param json
	 * @return
	 * @throws org.json.JSONException
	 */
	private List<SingleAuditInfo> getProResourcesId(JSONObject json) throws JSONException{
		List<SingleAuditInfo> list = new ArrayList<SingleAuditInfo>();
		
		List<ResourceKeyValue> keyList = new ArrayList<ResourceKeyValue>();
		keyList.add(new ResourceKeyValue("name", R.string.audit_info_pro_name));
		keyList.add(new ResourceKeyValue("code", R.string.audit_info_billcode));
		keyList.add(new ResourceKeyValue("contentOrDescription", R.string.audit_info_pro_des));
		keyList.add(new ResourceKeyValue("billType", R.string.audit_info_billtype));
		keyList.add(new ResourceKeyValue("sts", R.string.audit_info_sts));
		keyList.add(new ResourceKeyValue("name", R.string.audit_info_pro_name));
		keyList.add(new ResourceKeyValue("createByName", R.string.audit_info_crebyname));
		keyList.add(new ResourceKeyValue("createByTime", R.string.audit_info_crebytime));
		
		for(int i = 0; i < keyList.size(); i++){
			String key = keyList.get(i).getKey();
			if(json.has(key)){
				SingleAuditInfo info = new SingleAuditInfo();
				info.setKeyName(key);
				info.setResourceId(keyList.get(i).getValue());
				info.setContent(json.getString(key));
				list.add(info);
			}
		}
		
		return list;
	}
	
	/**
	 * 费用审核
	 * @param json
	 * @return
	 * @throws org.json.JSONException
	 */
	private List<SingleAuditInfo> getExpResourcesId(JSONObject json) throws JSONException{
		List<SingleAuditInfo> list = new ArrayList<SingleAuditInfo>();
		
		List<ResourceKeyValue> keyList = new ArrayList<ResourceKeyValue>();
		keyList.add(new ResourceKeyValue("type", R.string.audit_info_exp_type));
		keyList.add(new ResourceKeyValue("expenseType2", R.string.audit_info_exp_extype2));
		keyList.add(new ResourceKeyValue("operationDeptId", R.string.audit_info_operdept));
		keyList.add(new ResourceKeyValue("customerDeptId", R.string.audit_info_exp_cusdept));
		keyList.add(new ResourceKeyValue("code", R.string.audit_info_billcode));
		keyList.add(new ResourceKeyValue("ncCode", R.string.audit_info_exp_nccode));
		keyList.add(new ResourceKeyValue("payType", R.string.audit_info_exp_paytype));
		keyList.add(new ResourceKeyValue("account", R.string.audit_info_exp_account));
		keyList.add(new ResourceKeyValue("payDate", R.string.audit_info_exp_paydate));
		keyList.add(new ResourceKeyValue("payDate2", R.string.audit_info_exp_paydate2));
		keyList.add(new ResourceKeyValue("totalMoney", R.string.audit_info_totalmoney));
		
		if(json.has("payType")){
			if("MCB".equals(json.getString("payType"))){
				keyList.add(new ResourceKeyValue("customerId", R.string.audit_info_exp_cus));
			} else {
				keyList.add(new ResourceKeyValue("realName", R.string.audit_info_exp_realname));
				keyList.add(new ResourceKeyValue("currency", R.string.audit_info_exp_currency));
				keyList.add(new ResourceKeyValue("payType2", R.string.audit_info_exp_paytype2));
			}
		}
		
		if(json.has("payType2") && "GBT".equals(json.getString("payType2"))){
			keyList.add(new ResourceKeyValue("bankId", R.string.audit_info_exp_bank));
			keyList.add(new ResourceKeyValue("accountName", R.string.audit_info_exp_accountname));
		}
		
		if(json.has("expenseType2")){
			String exType2 = json.getString("expenseType2");
			if("CLF".equals(json.getString(exType2))){
				keyList.add(new ResourceKeyValue("deptName", R.string.audit_info_dept));
				keyList.add(new ResourceKeyValue("provinceFrom", R.string.audit_info_exp_profrom));
				keyList.add(new ResourceKeyValue("cityFrom", R.string.audit_info_exp_cityfrom));
				keyList.add(new ResourceKeyValue("provinceTo", R.string.audit_info_exp_proto));
				keyList.add(new ResourceKeyValue("cityTo", R.string.audit_info_exp_cityto));
				keyList.add(new ResourceKeyValue("clBeginDate", R.string.audit_info_exp_clbedindate));
				keyList.add(new ResourceKeyValue("clEndDate", R.string.audit_info_exp_clenddate));
				keyList.add(new ResourceKeyValue("clProvinceFrom", R.string.audit_info_exp_clprovincefrom));
				keyList.add(new ResourceKeyValue("clCityFrom", R.string.audit_info_exp_clcityfrom));
				keyList.add(new ResourceKeyValue("clProvinceTo", R.string.audit_info_exp_clprovinceto));
				keyList.add(new ResourceKeyValue("clCityTo", R.string.audit_info_exp_clcityto));
			} else if("ZCF".equals(exType2)){
				keyList.add(new ResourceKeyValue("deptName", R.string.audit_info_dept));
			}
		}
		
		keyList.add(new ResourceKeyValue("remark", R.string.audit_info_remark));
		keyList.add(new ResourceKeyValue("sts", R.string.audit_info_sts));
		keyList.add(new ResourceKeyValue("createByName", R.string.audit_info_crebyname));
		keyList.add(new ResourceKeyValue("createByTime", R.string.audit_info_crebytime));
		keyList.add(new ResourceKeyValue("commitByName", R.string.audit_info_commitname));
		keyList.add(new ResourceKeyValue("commitByTime", R.string.audit_info_committime));
		
		for(int i = 0; i < keyList.size(); i++){
			String key = keyList.get(i).getKey();
			if(json.has(key)){
				SingleAuditInfo info = new SingleAuditInfo();
				info.setKeyName(key);
				info.setResourceId(keyList.get(i).getValue());
				info.setContent(json.getString(key));
				list.add(info);
			}
		}
		
		return list;
	}
	/**
	 * 工作报告审核
	 * @param json
	 * @return
	 * @throws org.json.JSONException
	 */
	private List<SingleAuditInfo> getWkrResourcesId(JSONObject json) throws JSONException{
		List<SingleAuditInfo> list = new ArrayList<SingleAuditInfo>();
		
		List<ResourceKeyValue> keyList = new ArrayList<ResourceKeyValue>();
		keyList.add(new ResourceKeyValue("code", R.string.audit_info_wkr_code));
		keyList.add(new ResourceKeyValue("name", R.string.audit_info_wkr_name));
		keyList.add(new ResourceKeyValue("year", R.string.audit_info_year));
		keyList.add(new ResourceKeyValue("month", R.string.audit_info_month));
		keyList.add(new ResourceKeyValue("type", R.string.audit_info_wkr_type));
		keyList.add(new ResourceKeyValue("billType", R.string.audit_info_billtype));
		keyList.add(new ResourceKeyValue("operationDeptName", R.string.audit_info_operdept));
		keyList.add(new ResourceKeyValue("deptName", R.string.audit_info_dept));
		keyList.add(new ResourceKeyValue("sts", R.string.audit_info_sts));
		keyList.add(new ResourceKeyValue("createByName", R.string.audit_info_crebyname));
		keyList.add(new ResourceKeyValue("createByTime", R.string.audit_info_crebytime));
		keyList.add(new ResourceKeyValue("commitByName", R.string.audit_info_commitname));
		keyList.add(new ResourceKeyValue("commitByTime", R.string.audit_info_committime));
		
		for(int i = 0; i < keyList.size(); i++){
			String key = keyList.get(i).getKey();
			if(json.has(key)){
				SingleAuditInfo info = new SingleAuditInfo();
				info.setKeyName(key);
				info.setResourceId(keyList.get(i).getValue());
				info.setContent(json.getString(key));
				list.add(info);
			}
		}
		
		return list;
	}
	
	/**
	 * 借款审核
	 * @param json
	 * @return
	 * @throws org.json.JSONException
	 */
	private List<SingleAuditInfo> getLoaResourcesId(JSONObject json) throws JSONException{
		List<SingleAuditInfo> list = new ArrayList<SingleAuditInfo>();
		
		List<ResourceKeyValue> keyList = new ArrayList<ResourceKeyValue>();
		keyList.add(new ResourceKeyValue("LOANCODE", R.string.audit_info_billcode));
		keyList.add(new ResourceKeyValue("LOANTYPE", R.string.audit_info_loa_type));
		keyList.add(new ResourceKeyValue("PAYTYPE", R.string.audit_info_loa_paytype));
		keyList.add(new ResourceKeyValue("OPERATIONDEPTNAMECN", R.string.audit_info_loa_opradept));
		keyList.add(new ResourceKeyValue("DEPTNAMECN", R.string.audit_info_loa_dept));
		keyList.add(new ResourceKeyValue("TOTALMONEY", R.string.audit_info_totalmoney));
		keyList.add(new ResourceKeyValue("LOANREASON", R.string.audit_info_loa_reason));
		keyList.add(new ResourceKeyValue("PAYBANKACCOUNT", R.string.audit_info_loa_paybankaccount));
		keyList.add(new ResourceKeyValue("RETURENDATE", R.string.audit_info_loa_returndate));
		keyList.add(new ResourceKeyValue("REMARK", R.string.audit_info_remark));
		keyList.add(new ResourceKeyValue("STS", R.string.audit_info_sts));
		keyList.add(new ResourceKeyValue("CREATEBYNAME", R.string.audit_info_crebyname));
		keyList.add(new ResourceKeyValue("CEREATEBYTIME", R.string.audit_info_crebytime));
		keyList.add(new ResourceKeyValue("commitByName", R.string.audit_info_commitname));
		keyList.add(new ResourceKeyValue("commitByTime", R.string.audit_info_committime));
		
		for(int i = 0; i < keyList.size(); i++){
			String key = keyList.get(i).getKey();
			if(json.has(key)){
				SingleAuditInfo info = new SingleAuditInfo();
				info.setKeyName(key);
				info.setResourceId(keyList.get(i).getValue());
				info.setContent(json.getString(key));
				list.add(info);
			}
		}
		
		return list;
	}
	
	/**
	 * 还款审核
	 * @param json
	 * @return
	 * @throws org.json.JSONException
	 */
	private List<SingleAuditInfo> getLosResourcesId(JSONObject json) throws JSONException{
		List<SingleAuditInfo> list = new ArrayList<SingleAuditInfo>();
		
		List<ResourceKeyValue> keyList = new ArrayList<ResourceKeyValue>();
		keyList.add(new ResourceKeyValue("code", R.string.audit_info_los_code));
		keyList.add(new ResourceKeyValue("relationType", R.string.audit_info_los_relationtype));
		keyList.add(new ResourceKeyValue("returnMoney", R.string.audit_info_los_returnmoney));
		keyList.add(new ResourceKeyValue("remark", R.string.audit_info_remark));
		keyList.add(new ResourceKeyValue("operationDeptName", R.string.audit_info_operdept));
		keyList.add(new ResourceKeyValue("deptName", R.string.audit_info_dept));
		keyList.add(new ResourceKeyValue("sts", R.string.audit_info_sts));
		keyList.add(new ResourceKeyValue("createByName", R.string.audit_info_crebyname));
		keyList.add(new ResourceKeyValue("createByTime", R.string.audit_info_crebytime));
		keyList.add(new ResourceKeyValue("commitByName", R.string.audit_info_commitname));
		keyList.add(new ResourceKeyValue("commitByTime", R.string.audit_info_committime));
		
		for(int i = 0; i < keyList.size(); i++){
			String key = keyList.get(i).getKey();
			if(json.has(key)){
				SingleAuditInfo info = new SingleAuditInfo();
				info.setKeyName(key);
				info.setResourceId(keyList.get(i).getValue());
				info.setContent(json.getString(key));
				list.add(info);
			}
		}
		
		return list;
	}
	
	/**
	 * 工作计划审核
	 * @param json
	 * @return Audit info list
	 * @throws org.json.JSONException
	 */
	private List<SingleAuditInfo> getPlaResourcesId(JSONObject json) throws JSONException{
		List<SingleAuditInfo> list = new ArrayList<SingleAuditInfo>();
		
		List<ResourceKeyValue> keyList = new ArrayList<ResourceKeyValue>();
		keyList.add(new ResourceKeyValue("code", R.string.audit_info_pla_code));
		keyList.add(new ResourceKeyValue("name", R.string.audit_info_pla_name));
		keyList.add(new ResourceKeyValue("year", R.string.audit_info_year));
		keyList.add(new ResourceKeyValue("month", R.string.audit_info_month));
		keyList.add(new ResourceKeyValue("operationDeptName", R.string.audit_info_operdept));
		keyList.add(new ResourceKeyValue("deptName", R.string.audit_info_dept));
		keyList.add(new ResourceKeyValue("sts", R.string.audit_info_sts));
		keyList.add(new ResourceKeyValue("createByName", R.string.audit_info_crebyname));
		keyList.add(new ResourceKeyValue("createByTime", R.string.audit_info_crebytime));
		keyList.add(new ResourceKeyValue("commitByName", R.string.audit_info_commitname));
		keyList.add(new ResourceKeyValue("commitByTime", R.string.audit_info_committime));
		
		for(int i = 0; i < keyList.size(); i++){
			String key = keyList.get(i).getKey();
			if(json.has(key)){
				SingleAuditInfo info = new SingleAuditInfo();
				info.setKeyName(key);
				info.setResourceId(keyList.get(i).getValue());
				info.setContent(json.getString(key));
				list.add(info);
			}
		}
		
		return list;
	}
	
	/**
	 * 绩效审核
	 * @param json
	 * @return
	 * @throws org.json.JSONException
	 */
	private List<SingleAuditInfo> getAcmResourcesId(JSONObject json) throws JSONException{
		List<SingleAuditInfo> list = new ArrayList<SingleAuditInfo>();
		
		List<ResourceKeyValue> keyList = new ArrayList<ResourceKeyValue>();
		keyList.add(new ResourceKeyValue("code", R.string.audit_info_acm_code));
		keyList.add(new ResourceKeyValue("name", R.string.audit_info_acm_name));
		keyList.add(new ResourceKeyValue("year", R.string.audit_info_year));
		keyList.add(new ResourceKeyValue("month", R.string.audit_info_month));
		keyList.add(new ResourceKeyValue("operationDeptName", R.string.audit_info_operdept));
		keyList.add(new ResourceKeyValue("deptName", R.string.audit_info_dept));
		keyList.add(new ResourceKeyValue("sts", R.string.audit_info_sts));
		keyList.add(new ResourceKeyValue("createByName", R.string.audit_info_crebyname));
		keyList.add(new ResourceKeyValue("createByTime", R.string.audit_info_crebytime));
		keyList.add(new ResourceKeyValue("commitByName", R.string.audit_info_commitname));
		keyList.add(new ResourceKeyValue("commitByTime", R.string.audit_info_committime));
		
		for(int i = 0; i < keyList.size(); i++){
			String key = keyList.get(i).getKey();
			if(json.has(key)){
				SingleAuditInfo info = new SingleAuditInfo();
				info.setKeyName(key);
				info.setResourceId(keyList.get(i).getValue());
				info.setContent(json.getString(key));
				list.add(info);
			}
		}
		
		return list;
	}
	
	/**
	 * 招聘审核
	 * @param json
	 * @return
	 * @throws org.json.JSONException
	 */
	private List<SingleAuditInfo> getRecResourcesId(JSONObject json) throws JSONException{
		List<SingleAuditInfo> list = new ArrayList<SingleAuditInfo>();
		
		List<ResourceKeyValue> keyList = new ArrayList<ResourceKeyValue>();
		keyList.add(new ResourceKeyValue("code", R.string.audit_info_rec_code));
		keyList.add(new ResourceKeyValue("name", R.string.audit_info_rec_name));
		keyList.add(new ResourceKeyValue("type", R.string.audit_info_rec_type));
		keyList.add(new ResourceKeyValue("billType", R.string.audit_info_billtype));
		keyList.add(new ResourceKeyValue("operationDeptName", R.string.audit_info_operdept));
		keyList.add(new ResourceKeyValue("deptName", R.string.audit_info_dept));
		keyList.add(new ResourceKeyValue("sts", R.string.audit_info_sts));
		keyList.add(new ResourceKeyValue("createByName", R.string.audit_info_crebyname));
		keyList.add(new ResourceKeyValue("createByTime", R.string.audit_info_crebytime));
		keyList.add(new ResourceKeyValue("commitByName", R.string.audit_info_commitname));
		keyList.add(new ResourceKeyValue("commitByTime", R.string.audit_info_committime));
		
		for(int i = 0; i < keyList.size(); i++){
			String key = keyList.get(i).getKey();
			if(json.has(key)){
				SingleAuditInfo info = new SingleAuditInfo();
				info.setKeyName(key);
				info.setResourceId(keyList.get(i).getValue());
				info.setContent(json.getString(key));
				list.add(info);
			}
		}
		
		return list;
	}
	
	/**
	 * 会议审核
	 * @param json
	 * @return
	 * @throws org.json.JSONException
	 */
	private List<SingleAuditInfo> getMetResourcesId(JSONObject json) throws JSONException{
		List<SingleAuditInfo> list = new ArrayList<SingleAuditInfo>();
		
		List<ResourceKeyValue> keyList = new ArrayList<ResourceKeyValue>();
		keyList.add(new ResourceKeyValue("operationDeptName", R.string.audit_info_operdept));
		keyList.add(new ResourceKeyValue("deptName", R.string.audit_info_dept));
		keyList.add(new ResourceKeyValue("code", R.string.audit_info_met_code));
		keyList.add(new ResourceKeyValue("contentOrDescription", R.string.audit_info_met_content));
		keyList.add(new ResourceKeyValue("type", R.string.audit_info_met_type));
		keyList.add(new ResourceKeyValue("billType", R.string.audit_info_billtype));
		keyList.add(new ResourceKeyValue("sts", R.string.audit_info_sts));
		keyList.add(new ResourceKeyValue("createByName", R.string.audit_info_crebyname));
		keyList.add(new ResourceKeyValue("createByTime", R.string.audit_info_crebytime));
		keyList.add(new ResourceKeyValue("commitByName", R.string.audit_info_commitname));
		keyList.add(new ResourceKeyValue("commitByTime", R.string.audit_info_committime));
		
		for(int i = 0; i < keyList.size(); i++){
			String key = keyList.get(i).getKey();
			if(json.has(key)){
				SingleAuditInfo info = new SingleAuditInfo();
				info.setKeyName(key);
				info.setResourceId(keyList.get(i).getValue());
				info.setContent(json.getString(key));
				list.add(info);
			}
		}
		
		return list;
	}
	
	/**
	 * 采购报价审核
	 * @param json
	 * @return
	 * @throws org.json.JSONException
	 */
	private List<SingleAuditInfo> getPopResourcesId(JSONObject json) throws JSONException{
		List<SingleAuditInfo> list = new ArrayList<SingleAuditInfo>();
		
		List<ResourceKeyValue> keyList = new ArrayList<ResourceKeyValue>();
		keyList.add(new ResourceKeyValue("CODE", R.string.audit_info_pop_code));
		keyList.add(new ResourceKeyValue("POPRICELOGONCODE", R.string.audit_info_pop_popricelognocode));
		keyList.add(new ResourceKeyValue("POPRICELOGON", R.string.audit_info_pop_popricelogno));
		keyList.add(new ResourceKeyValue("CUSTOMERNAME", R.string.audit_info_pop_cusdept));
		keyList.add(new ResourceKeyValue("PROJECTCUSTOMERNAME", R.string.audit_info_pop_cusproject));
		keyList.add(new ResourceKeyValue("STS", R.string.audit_info_sts));
		keyList.add(new ResourceKeyValue("CREATEBYNAME", R.string.audit_info_crebyname));
		keyList.add(new ResourceKeyValue("CREATEBYTIME", R.string.audit_info_crebytime));
		keyList.add(new ResourceKeyValue("COMMITBYNAME", R.string.audit_info_commitname));
		keyList.add(new ResourceKeyValue("COMMITBYTIME", R.string.audit_info_committime));
		
		for(int i = 0; i < keyList.size(); i++){
			String key = keyList.get(i).getKey();
			if(json.has(key)){
				SingleAuditInfo info = new SingleAuditInfo();
				info.setKeyName(key);
				info.setResourceId(keyList.get(i).getValue());
				info.setContent(json.getString(key));
				list.add(info);
			}
		}
		
		return list;
	}
	
	/**
	 * 销售退货
	 * @param json
	 * @return
	 * @throws org.json.JSONException
	 */
	private List<SingleAuditInfo> getInsResourcesId(JSONObject json) throws JSONException{
		List<SingleAuditInfo> list = new ArrayList<SingleAuditInfo>();
		
		List<ResourceKeyValue> keyList = new ArrayList<ResourceKeyValue>();
		keyList.add(new ResourceKeyValue("INCODE1", R.string.audit_info_ins_incode1));
		keyList.add(new ResourceKeyValue("INCODE2", R.string.audit_info_ins_incode2));
		keyList.add(new ResourceKeyValue("CUSTOMERNAME", R.string.audit_info_ins_customer));
		keyList.add(new ResourceKeyValue("WAREHOUSECUSTOMERNAME", R.string.audit_info_ins_warehouse));
		keyList.add(new ResourceKeyValue("INQTY1", R.string.audit_info_ins_inqty1));
		keyList.add(new ResourceKeyValue("INQTY2", R.string.audit_info_ins_inqty2));
		keyList.add(new ResourceKeyValue("INMONEY1", R.string.audit_info_ins_inmoney1));
		keyList.add(new ResourceKeyValue("INNOTAXMONEY1", R.string.audit_info_ins_innotaxmoney1));
		keyList.add(new ResourceKeyValue("INMONEY2", R.string.audit_info_ins_inmoney2));
		keyList.add(new ResourceKeyValue("INNOTAXMONEY2", R.string.audit_info_ins_innotaxmoney2));
		keyList.add(new ResourceKeyValue("STS", R.string.audit_info_sts));
		keyList.add(new ResourceKeyValue("REMARK", R.string.audit_info_remark));
		keyList.add(new ResourceKeyValue("CREATEBYNAME", R.string.audit_info_crebyname));
		keyList.add(new ResourceKeyValue("CREATEBYTIME", R.string.audit_info_crebytime));
		keyList.add(new ResourceKeyValue("COMMITBYNAME", R.string.audit_info_commitname));
		keyList.add(new ResourceKeyValue("COMMITBYTIME", R.string.audit_info_committime));
		
		for(int i = 0; i < keyList.size(); i++){
			String key = keyList.get(i).getKey();
			if(json.has(key)){
				SingleAuditInfo info = new SingleAuditInfo();
				info.setKeyName(key);
				info.setResourceId(keyList.get(i).getValue());
				info.setContent(json.getString(key));
				list.add(info);
			}
		}
		
		return list;
	}
	
	/**
	 * 销售报价审核
	 * @param json
	 * @return
	 * @throws org.json.JSONException
	 */
	private List<SingleAuditInfo> getSopResourcesId(JSONObject json) throws JSONException{
		List<SingleAuditInfo> list = new ArrayList<SingleAuditInfo>();
		
		List<ResourceKeyValue> keyList = new ArrayList<ResourceKeyValue>();
		keyList.add(new ResourceKeyValue("SOPRICECODE", R.string.audit_info_sop_code));
		keyList.add(new ResourceKeyValue("SOPRICENAME", R.string.audit_info_sop_name));
		keyList.add(new ResourceKeyValue("SOPRICELOGONCODE", R.string.audit_info_sop_sopricelognocode));
		keyList.add(new ResourceKeyValue("SOPRICELOGON", R.string.audit_info_sop_sopricelogno));
		keyList.add(new ResourceKeyValue("CUSTOMERNAME", R.string.audit_info_sop_customer));
		keyList.add(new ResourceKeyValue("PROJECTCUSTOMERNAME", R.string.audit_info_sop_cusproject));
		keyList.add(new ResourceKeyValue("STS", R.string.audit_info_sts));
		keyList.add(new ResourceKeyValue("REMARK", R.string.audit_info_remark));
		keyList.add(new ResourceKeyValue("CREATEBYNAME", R.string.audit_info_crebyname));
		keyList.add(new ResourceKeyValue("CREATEBYTIME", R.string.audit_info_crebytime));
		keyList.add(new ResourceKeyValue("COMMITBYNAME", R.string.audit_info_commitname));
		keyList.add(new ResourceKeyValue("COMMITBYTIME", R.string.audit_info_committime));
		
		for(int i = 0; i < keyList.size(); i++){
			String key = keyList.get(i).getKey();
			if(json.has(key)){
				SingleAuditInfo info = new SingleAuditInfo();
				info.setKeyName(key);
				info.setResourceId(keyList.get(i).getValue());
				info.setContent(json.getString(key));
				list.add(info);
			}
		}
		
		return list;
	}
	
	/**
	 * 出差申请审核
	 * @param json
	 * @return
	 * @throws org.json.JSONException
	 */
	private List<SingleAuditInfo> getTraResourcesId(JSONObject json) throws JSONException{
		List<SingleAuditInfo> list = new ArrayList<SingleAuditInfo>();
		
		List<ResourceKeyValue> keyList = new ArrayList<ResourceKeyValue>();
		keyList.add(new ResourceKeyValue("code", R.string.audit_info_tra_code));
		keyList.add(new ResourceKeyValue("transport", R.string.audit_info_tra_transport));
		keyList.add(new ResourceKeyValue("traPurpose", R.string.audit_info_tra_purpose));
		keyList.add(new ResourceKeyValue("traTask", R.string.audit_info_tra_task));
		keyList.add(new ResourceKeyValue("traIsLoan", R.string.audit_info_tra_isloan));
		keyList.add(new ResourceKeyValue("traMoney", R.string.audit_info_tra_tramoney));
		keyList.add(new ResourceKeyValue("operationDeptName", R.string.audit_info_tra_task));
		keyList.add(new ResourceKeyValue("deptName", R.string.audit_info_tra_task));
		keyList.add(new ResourceKeyValue("traBeginDate", R.string.audit_info_tra_begindate));
		keyList.add(new ResourceKeyValue("traEndDate", R.string.audit_info_tra_enddate));
		keyList.add(new ResourceKeyValue("sts", R.string.audit_info_sts));
		keyList.add(new ResourceKeyValue("remark", R.string.audit_info_remark));
		keyList.add(new ResourceKeyValue("createByName", R.string.audit_info_crebyname));
		keyList.add(new ResourceKeyValue("createByTime", R.string.audit_info_crebytime));
		keyList.add(new ResourceKeyValue("commitByName", R.string.audit_info_commitname));
		keyList.add(new ResourceKeyValue("commitByTime", R.string.audit_info_committime));
		
		for(int i = 0; i < keyList.size(); i++){
			String key = keyList.get(i).getKey();
			if(json.has(key)){
				SingleAuditInfo info = new SingleAuditInfo();
				info.setKeyName(key);
				info.setResourceId(keyList.get(i).getValue());
				info.setContent(json.getString(key));
				list.add(info);
			}
		}
		
		return list;
	}
	
	/**
	 * 销售发货
	 * @param json
	 * @return
	 * @throws org.json.JSONException
	 */
	private List<SingleAuditInfo> getOutResourcesId(JSONObject json) throws JSONException{
		List<SingleAuditInfo> list = new ArrayList<SingleAuditInfo>();
		
		List<ResourceKeyValue> keyList = new ArrayList<ResourceKeyValue>();
		keyList.add(new ResourceKeyValue("CODE", R.string.audit_info_out_code));
		keyList.add(new ResourceKeyValue("OUTCODE2", R.string.audit_info_out_code2));
		keyList.add(new ResourceKeyValue("OUTTIME", R.string.audit_info_out_time));
		keyList.add(new ResourceKeyValue("OUTTYPE", R.string.audit_info_out_type));
		keyList.add(new ResourceKeyValue("WAREHOUSECUSTOMERNAME", R.string.audit_info_out_warehouse));
		keyList.add(new ResourceKeyValue("CUSTOMERDEPTNAME", R.string.audit_info_out_cusdept));
		keyList.add(new ResourceKeyValue("OUTQTY1", R.string.audit_info_out_qty1));
		keyList.add(new ResourceKeyValue("OUTMONEY1", R.string.audit_info_out_money1));
		keyList.add(new ResourceKeyValue("OUTNOTAXMONEY1", R.string.audit_info_out_notaxmoney1));
		keyList.add(new ResourceKeyValue("TRANSPORTQTY1", R.string.audit_info_out_traqty1));
		keyList.add(new ResourceKeyValue("TRANSPORTMONEY1", R.string.audit_info_out_tramoney1));
		keyList.add(new ResourceKeyValue("TRANSPORTNOTAXMONEY1", R.string.audit_info_out_tranotaxmoney1));
		keyList.add(new ResourceKeyValue("TRANSPORTQTY2", R.string.audit_info_out_traqty2));
		keyList.add(new ResourceKeyValue("TRANSPORTMONEY2", R.string.audit_info_out_tramoney2));
		keyList.add(new ResourceKeyValue("TRANSPORTNOTAXMONEY2", R.string.audit_info_out_tranotaxmoney2));
		
		if(json.has("OUTTYPE")){
			String tempType = json.getString("OUTTYPE");
			if("SAL".equals(tempType)){
				//销售发货
				keyList.add(new ResourceKeyValue("AMOUNTMONEY", R.string.audit_info_out_amountmoney));
				keyList.add(new ResourceKeyValue("PAYMONEY", R.string.audit_info_out_paymoney));
				keyList.add(new ResourceKeyValue("SHIPTOCUSTOMERNAME", R.string.audit_info_out_shipto));
				keyList.add(new ResourceKeyValue("VATTOCUSTOMERNAME", R.string.audit_info_out_vatto));
			} else if("POL".equals(tempType)){
				//采购退货
				keyList.add(new ResourceKeyValue("VENDORCUSTOMERNAME", R.string.audit_info_out_paymoney));
				keyList.add(new ResourceKeyValue("PROJECTCUSTOMERNAME", R.string.audit_info_out_project));
			} else if("OTI".equals(tempType)){
				//调拨发货
				keyList.add(new ResourceKeyValue("WAREHOUSETO", R.string.audit_info_out_warehouseto));
			} else if("REL".equals(tempType)){
				//报损
				keyList.add(new ResourceKeyValue("SHIPTOCUSTOMERNAME", R.string.audit_info_out_shipto));
				keyList.add(new ResourceKeyValue("VATTOCUSTOMERNAME", R.string.audit_info_out_vatto));
			}
		}
		
		keyList.add(new ResourceKeyValue("REMARK", R.string.audit_info_remark));
		keyList.add(new ResourceKeyValue("STS", R.string.audit_info_sts));
		keyList.add(new ResourceKeyValue("CREATEBYNAME", R.string.audit_info_crebyname));
		keyList.add(new ResourceKeyValue("CREATEBYTIME", R.string.audit_info_crebytime));
		keyList.add(new ResourceKeyValue("COMMITBYNAME", R.string.audit_info_commitname));
		keyList.add(new ResourceKeyValue("COMMITBYTIME", R.string.audit_info_committime));
		
		for(int i = 0; i < keyList.size(); i++){
			String key = keyList.get(i).getKey();
			if(json.has(key)){
				SingleAuditInfo info = new SingleAuditInfo();
				info.setKeyName(key);
				info.setResourceId(keyList.get(i).getValue());
				info.setContent(json.getString(key));
				list.add(info);
			}
		}
		
		return list;
	}
	
}
