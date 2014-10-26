package com.explore.android.mobile.activity.audit;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;

import com.explore.android.R;
import com.explore.android.core.base.BaseHttpActivity;
import com.explore.android.core.model.BaseKeyValue;
import com.explore.android.mobile.adapter.SimpleSpinnerAdapter;
import com.explore.android.mobile.constants.AppStatus;
import com.explore.android.mobile.constants.AuditConstants;
import com.explore.android.mobile.constants.RequestConstants;
import com.explore.android.mobile.constants.ResponseConstant;
import com.explore.android.mobile.data.request.AuditActionRequest;
import com.explore.android.mobile.data.request.AuditDetailRequest;
import com.explore.android.mobile.data.request.AuditEmpRequest;
import com.explore.android.mobile.model.SingleAuditInfo;
import com.explore.android.mobile.view.CommonView;
import com.explore.android.mobile.view.TitleTextView;

public class AuditDetailActivity extends BaseHttpActivity{
	
	private static final int REQUEST_AUDIT_PRE = 1;
	private static final int REQUEST_AUDIT_INFO = 2;
	private static final int REQUEST_AUDIT_ACTION = 3;
	private static final int REQUEST_AUDIT_SIGNEMP = 4;
	private static final int REQUEST_AUDIT_TRANSMITEMP = 5;
	
	private int auditId = 0;
	private int auditType = 0;
	private RelativeLayout loading;
	
	private TextView nodata_text;
	private ScrollView audit_scroll;
	
	private LinearLayout actionLayout;
	private LinearLayout infoLayout;
	private Spinner spiProGroup, spiEmp, spiSign, spiTransmit;
	private Button btnPass, btnDela, btnSign, btnTransMit;
	private EditText edt_opinion;
	
	private String audit_projectId;
	private String audit_pas_empId;
	private String audit_trans_empId;
	private String audit_sign_empId;
	private boolean isTempPosition; // 是否需要选择项目组和人员进行审核操作
	
	private List<BaseKeyValue> projectList;
	private List<BaseKeyValue> empList;
	private List<BaseKeyValue> signToList;
	private List<BaseKeyValue> transmitToList;
	private SimpleSpinnerAdapter projectAdapter;
	private SimpleSpinnerAdapter empAdapter;
	private SimpleSpinnerAdapter signToAdapter;
	private SimpleSpinnerAdapter transmitToAdapter;
	private List<SingleAuditInfo> auditInfoList;
	
	
	private AuditManager auditManager;
	
	@Override
	protected int setLayoutId() {
		return R.layout.activity_audit_detail;
	}

	@Override
	public void initViews() {
		nodata_text = (TextView) findViewById(R.id.audit_info_nodate);
		audit_scroll = (ScrollView) findViewById(R.id.audit_infos_scroll_layout);
		loading = (RelativeLayout) findViewById(R.id.app_loading_layout);
		actionLayout = (LinearLayout) findViewById(R.id.audit_action_layout);
		infoLayout = (LinearLayout) findViewById(R.id.act_audit_info_layout);
		
		spiProGroup = (Spinner) findViewById(R.id.audit_despinner_progroup);
		spiEmp = (Spinner) findViewById(R.id.audit_despinner_emp);
		spiSign = (Spinner) findViewById(R.id.audit_despinner_sign);
		spiTransmit = (Spinner) findViewById(R.id.audit_despinner_trans);

		edt_opinion = (EditText) findViewById(R.id.audit_edt_opinion);
		btnDela = (Button) findViewById(R.id.audit_debtn_dela);
		btnPass = (Button) findViewById(R.id.audit_debtn_pass);
		btnSign = (Button) findViewById(R.id.audit_debtn_sign);
		btnTransMit = (Button) findViewById(R.id.audit_debtn_transmit);
	}

	@Override
	public void initValues() {
		Intent intent = getIntent();
		if(intent.hasExtra("id")){
			auditId = intent.getIntExtra("id", 0);
		}
		if(intent.hasExtra("type")){
			auditType = getAuditType(intent.getStringExtra("type"));
		}
		
		if(auditId != 0 && auditType != 0){
			
			auditManager = AuditManager.getInstance();
			
			projectList = new ArrayList<BaseKeyValue>();
			projectAdapter = new SimpleSpinnerAdapter(AuditDetailActivity.this, projectList);
			spiProGroup.setAdapter(projectAdapter);

			empList = new ArrayList<BaseKeyValue>();
			empAdapter = new SimpleSpinnerAdapter(AuditDetailActivity.this, empList);
			spiEmp.setAdapter(empAdapter);
			
			signToList = new ArrayList<BaseKeyValue>();
			signToAdapter = new SimpleSpinnerAdapter(AuditDetailActivity.this, signToList);
			spiSign.setAdapter(signToAdapter);
			
			transmitToList = new ArrayList<BaseKeyValue>();
			transmitToAdapter = new SimpleSpinnerAdapter(AuditDetailActivity.this, transmitToList);
			spiTransmit.setAdapter(transmitToAdapter);
			
			isTempPosition = false;
			loadAuditDetail();
			
		} else {
			showToast(R.string.msg_audit_no_idinfo);
			nodata_text.setVisibility(View.VISIBLE);
			audit_scroll.setVisibility(View.GONE);
		}
	}

	@Override
	public void initListener() {
		spiProGroup.setOnItemSelectedListener(new OnItemSelectedListener(){
			@Override
			public void onItemSelected(AdapterView<?> arg0, View view, int position, long id) {
				audit_projectId = projectList.get(position).getValue();
				if (position > 0) {
					loadEmployeeData(AuditConstants.YES, AuditConstants.PROJ, audit_projectId);
					spiEmp.setEnabled(true);
				} else {
					spiEmp.setEnabled(false);
				}
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
			}
		});
					
		//选择人员
		spiEmp.setOnItemSelectedListener(new OnItemSelectedListener(){
			@Override
			public void onItemSelected(AdapterView<?> arg0, View view, int position, long id) {
				audit_pas_empId = empList.get(position).getValue();
			}
	
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
			}
		});

		//加签选择人员
		spiSign.setOnItemSelectedListener(new OnItemSelectedListener(){
			@Override
			public void onItemSelected(AdapterView<?> arg0, View view, int position, long id) {
				audit_sign_empId = signToList.get(position).getValue();
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				
			}
		});
		
		//转交选择人员
		spiTransmit.setOnItemSelectedListener(new OnItemSelectedListener(){
			@Override
			public void onItemSelected(AdapterView<?> arg0, View view, int position, long id) {
				audit_trans_empId = transmitToList.get(position).getValue();
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				
			}
		});
		
		//审核驳回
		btnDela.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				if (submitCheck()) {
					auditAction(AuditConstants.TYPE_NO, " ", ""+auditId, edt_opinion.getText().toString().trim());
				}
			}
		});
		
		//审核通过
		btnPass.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				if (submitCheck()) {
					if (spiEmp.getSelectedItemPosition() != 0
							&& spiProGroup.getSelectedItemPosition() != 0) {
						if (isTempPosition == false) {
							audit_pas_empId = "";
						}
						auditAction(AuditConstants.TYPE_YES, audit_pas_empId, ""+auditId, edt_opinion.getText().toString().trim());
					}
				}
			}
		});
		
		//审核加签
		btnSign.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				if (submitCheck()) {
					if (spiSign.getSelectedItemPosition() != 0) {
						auditAction(AuditConstants.TYPE_REMARK, audit_sign_empId, ""+auditId, edt_opinion.getText().toString().trim());
					}
				}
			}
		});
		
		//审核转交
		btnTransMit.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				if (submitCheck()) {
					if (spiTransmit.getSelectedItemPosition() != 0) {
						auditAction(AuditConstants.TYPE_TRANSMIT, audit_trans_empId, ""+auditId, edt_opinion.getText().toString().trim());
					}
				}
			}
		});
	}

	@Override
	public void showLoading() {
		loading.setVisibility(View.VISIBLE);
	}

	@Override
	public void dismissLoading() {
		return;
	}

	@Override
	public void handlerResponse(String response, int what) {
		switch (what) {
		case REQUEST_AUDIT_PRE:
			bindEmployeesData(response, empList);
			empAdapter.notifyDataSetChanged();
			auditHandler.sendEmptyMessage(4);
			break;
			
		case REQUEST_AUDIT_INFO:
			bindAuditDetailData(response, false);
			auditHandler.sendEmptyMessage(2);
			break;
			
		case REQUEST_AUDIT_ACTION:
			handlerSubmit(response);
			auditHandler.sendEmptyMessage(4);
			break;
			
		case REQUEST_AUDIT_SIGNEMP:
			bindEmployeesData(response, signToList);
			signToAdapter.notifyDataSetChanged();
			auditHandler.sendEmptyMessage(3);
			break;
			
		case REQUEST_AUDIT_TRANSMITEMP:
			bindEmployeesData(response, transmitToList);
			transmitToAdapter.notifyDataSetChanged();
			auditHandler.sendEmptyMessage(4);
			break;

		default:
			break;
		}
	}
	
	private void loadEmployeeData(String myself, String type, String deid){
		AuditEmpRequest request = new AuditEmpRequest();
		request.setUserId(getPreferences().getUserId());
		request.setToken(getPreferences().getTokenLast8());
		request.setMyself(myself);
		request.setType(type);
		request.setDepeId(deid);
		asynDataRequest(RequestConstants.AUDIT_DEPT, request.toJsonString(), REQUEST_AUDIT_PRE);
	}
	
	private void loadSignToEmployees(String myself){
		AuditEmpRequest request = new AuditEmpRequest();
		request.setUserId(getPreferences().getUserId());
		request.setToken(getPreferences().getTokenLast8());
		request.setType(AuditConstants.AUDITSIGN);
		request.setMyself(myself);
		request.setDepeId("");
		asynDataRequest(RequestConstants.AUDIT_DEPT, request.toJsonString(), REQUEST_AUDIT_SIGNEMP);
	}
	
	private void loadTransmitToEmployees(String myself){
		AuditEmpRequest request = new AuditEmpRequest();
		request.setUserId(getPreferences().getUserId());
		request.setToken(getPreferences().getTokenLast8());
		request.setType(AuditConstants.OPS);
		request.setMyself(myself);
		request.setDepeId("");
		asynDataRequest(RequestConstants.AUDIT_DEPT, request.toJsonString(), REQUEST_AUDIT_TRANSMITEMP);
	}
	
	private void loadAuditDetail(){
		AuditDetailRequest request = new AuditDetailRequest();
		request.setUserId(getPreferences().getUserId());
		request.setToken(getPreferences().getTokenLast8());
		request.setAuditId(String.valueOf(auditId));
		asynDataRequest(RequestConstants.AUDIT_DETAIL, request.toJsonString(), REQUEST_AUDIT_INFO);
	}
	
	private void auditAction(String type, String eid, String aid, String acon){
		loading.setVisibility(View.VISIBLE);
		AuditActionRequest request = new AuditActionRequest();
		request.setUserId(getPreferences().getUserId());
		request.setToken(getPreferences().getTokenLast8());
		request.setType(type);
		request.setContent(acon);
		request.setEmpId(eid);
		request.setAuditId(aid);
		submitRequest(RequestConstants.AUDIT_ACTION, request.toJsonString(), REQUEST_AUDIT_ACTION);
	}
	
	
	private void bindAuditDetailData(String resStr, boolean fromCache) {
		try {
			JSONObject json = new JSONObject(resStr);
			if(ResponseConstant.OK.equals(json.get(ResponseConstant.STATUS))){
				auditInfoList = auditManager.getAuditDetail(auditType, json);
				int length = auditInfoList.size();
				infoLayout.addView(CommonView.getSingleLine(1, AuditDetailActivity.this, R.color.audit_sepline_color));
				for (int i = 0; i < length; i++) {
					TitleTextView textView = new TitleTextView(AuditDetailActivity.this, null);
					textView.setTitle(auditInfoList.get(i).getResourceId());
					textView.setContent(auditInfoList.get(i).getContent());
					infoLayout.addView(textView);
					if(i != length-1){
						infoLayout.addView(CommonView.getSingleLine(1, AuditDetailActivity.this, R.color.audit_sepline_color));
					}
				}
				
				if("Y".equals(json.getString(AuditConstants.ISTEMPPOSITION))){
					isTempPosition =  true;
					actionLayout.setVisibility(View.VISIBLE);
				}  else {
					actionLayout.setVisibility(View.GONE);
				}
				
				if(json.has("PROJECTTEAM")){
					projectList.clear();
					JSONArray jsonArray = json.getJSONArray("PROJECTTEAM");
					//插入一个空白的选项
					projectList.add(new BaseKeyValue("", ""));
					for (int i = 0; i < jsonArray.length(); i++) {
						JSONObject jsonObject = jsonArray.getJSONObject(i);
						projectList.add(new BaseKeyValue(jsonObject.getString("DEPTNAME"),jsonObject.getString("DEPTID")));
					}
					projectAdapter.notifyDataSetChanged();
				}
			}
		} catch (JSONException e) {
			showToast(R.string.msg_response_failed);
			e.printStackTrace();
		}
	}
	
	private void bindEmployeesData(String dataStr, List<BaseKeyValue> list){
		try {
			JSONObject json = new JSONObject(dataStr);
			if(ResponseConstant.OK.equals(json.get(ResponseConstant.STATUS))){
				list.clear();
				list.add(new BaseKeyValue("", ""));
				JSONArray jsonArray = json.getJSONArray("EMPLOYEES");
				for (int i = 0; i < jsonArray.length(); i++) {
					JSONObject jsonObject = (JSONObject) jsonArray.get(i);
					list.add(new BaseKeyValue(jsonObject.getString("EMPLOYEENAME"),jsonObject.getString("EMPLOYEEID")));
				}
			}
		} catch (JSONException e) {
			showToast(R.string.msg_audit_loademp_failed);
		}
	}
	
	private void handlerSubmit(String dataStr){
		try {
			JSONObject json = new JSONObject(dataStr);
			if(ResponseConstant.OK.equals(json.get(ResponseConstant.STATUS))){
				showToast(R.string.msg_audit_action_success);
				AppStatus.AUDIT_LIST_UPDATE = true;
				AuditDetailActivity.this.finish();
			} else {
				showToast(R.string.msg_audit_action_failed);
			}
		} catch (JSONException e) {
			e.printStackTrace();
			showToast(R.string.msg_audit_action_failed);
		}
	}
	
	private boolean submitCheck() {
		if ("".equals(edt_opinion.getText().toString().trim())) {
			showToast(R.string.msg_audit_no_opinion);
			return false;
		} else {
			return true;
		}
	}
	
	@SuppressLint("HandlerLeak")
	private Handler auditHandler = new Handler(){

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch(msg.what){
			case 1:
				loadEmployeeData(AuditConstants.YES, AuditConstants.PROJ, audit_projectId);
				break;
				
			case 2:
				loadSignToEmployees(AuditConstants.NO);
				break;
				
			case 3:
				loadTransmitToEmployees(AuditConstants.NO);
				break;
				
			case 4:
				loading.setVisibility(View.GONE);
				break;
				
			default:
				break;
			}
		}
		
	};
	
	private int getAuditType(String type){
		
		if(AuditConstants.BILLTYPE_DEV.equals(type)){
			return AuditManager.TYPE_DEV;
			
		}else if(AuditConstants.BILLTYPE_EXP.equals(type)){
			return AuditManager.TYPE_EXP;
			
		}else if(AuditConstants.BILLTYPE_LOA.equals(type)){
			return AuditManager.TYPE_LOA;
			
		}else if(AuditConstants.BILLTYPE_LOS.equals(type)){
			return AuditManager.TYPE_LOS;
			
		}else if(AuditConstants.BILLTYPE_PRO.equals(type)){
			return AuditManager.TYPE_PRO;
			
		}else if(AuditConstants.BILLTYPE_REC.equals(type)){
			return AuditManager.TYPE_REC;
			
		}else if(AuditConstants.BILLTYPE_WKR.equals(type)){
			return AuditManager.TYPE_WKR;
			
		}else if(AuditConstants.BILLTYPE_PLA.equals(type)){
			return AuditManager.TYPE_PLA;
			
		}else if(AuditConstants.BILLTYPE_ACM.equals(type)){
			return AuditManager.TYPE_ACM;
			
		}else if(AuditConstants.BILLTYPE_MET.equals(type)){
			return AuditManager.TYPE_MET;
			
		}else if(AuditConstants.BILLTYPE_POP.equals(type)){
			return AuditManager.TYPE_POP;
			
		}else if(AuditConstants.BILLTYPE_INS.equals(type)){
			return AuditManager.TYPE_INS;
			
		}else if(AuditConstants.BILLTYPE_SOP.equals(type)){
			return AuditManager.TYPE_SOP;
			
		}else if(AuditConstants.BILLTYPE_TRA.equals(type)){
			return AuditManager.TYPE_TRA;
			
		}else if(AuditConstants.BILLTYPE_OUT.equals(type)){
			return AuditManager.TYPE_OUT;
			
		}else{
			return -1;
		}
	}

}
