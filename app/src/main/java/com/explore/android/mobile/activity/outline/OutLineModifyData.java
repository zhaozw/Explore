package com.explore.android.mobile.activity.outline;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.explore.android.R;
import com.explore.android.core.model.BaseKeyValue;
import com.explore.android.mobile.constants.SDConstants;

public class OutLineModifyData {

	private String partNo;
	private String npartNo;
	private String productName;
	private String outQty;
	private String price;
	private String logNo;
	private String whs;
	private String remarks;
	private int whsSelection;
	private List<BaseKeyValue> whsList;
	private List<BaseKeyValue> priceUnitList;
	private List<BaseKeyValue> qtyUnitList;
	
	public List<BaseKeyValue> getDefaultWhsList(){
		if(whsList == null){
			whsList = new ArrayList<BaseKeyValue>();
		} else if(whsList.size() > 0 ){
			whsList.clear();
		}
		
		whsList.add(new BaseKeyValue("", ""));
		whsList.add(new BaseKeyValue(R.string.sd_whs_good, SDConstants.WHS_01));
		whsList.add(new BaseKeyValue(R.string.sd_whs_overday, SDConstants.WHS_02));
		whsList.add(new BaseKeyValue(R.string.sd_whs_back, SDConstants.WHS_50));
		whsList.add(new BaseKeyValue(R.string.sd_whs_scrap, SDConstants.WHS_99));
        whsList.add(new BaseKeyValue(R.string.sd_whs_tj, SDConstants.WHS_TJ));
        whsList.add(new BaseKeyValue(R.string.sd_whs_zp, SDConstants.WHS_ZP));
        whsList.add(new BaseKeyValue(R.string.sd_whs_broken, SDConstants.WHS_89));

		return whsList;
	}
	
	public void setPriceUnitList(JSONArray jsonArray) throws JSONException {
		
		if(priceUnitList == null){
			priceUnitList = new ArrayList<BaseKeyValue>();
		} else if(priceUnitList.size() > 0){
			priceUnitList.clear();
		}
		
//		BaseKeyValue blankValue = new BaseKeyValue("", "");
//		priceUnitList.add(blankValue);
		
		for (int i = 0; i < jsonArray.length(); i++) {
			JSONObject json = jsonArray.getJSONObject(i);
			BaseKeyValue value = new BaseKeyValue();
			value.setKey(json.getString("UNIT"));
			value.setValue(json.getString("PRODUCTUNITID"));
			
			priceUnitList.add(value);
		}
	}
	
	public void setQtyUnitList(JSONArray jsonArray) throws JSONException{
		
		if(qtyUnitList == null){
			qtyUnitList = new ArrayList<BaseKeyValue>();
		} else if(qtyUnitList.size() > 0){
			qtyUnitList.clear();
		}
		
//		BaseKeyValue blankValue = new BaseKeyValue("", "");
//		qtyUnitList.add(blankValue);
		
		for (int i = 0; i < jsonArray.length(); i++) {
			JSONObject json = jsonArray.getJSONObject(i);
			BaseKeyValue value = new BaseKeyValue();
			value.setKey(json.getString("UNIT"));
			value.setValue(json.getString("PRODUCTUNITID"));
			
			qtyUnitList.add(value);
		}
	}
	
	public void initData(JSONObject json) throws JSONException{
		if(qtyUnitList == null || priceUnitList == null || whsList == null){
			return;
		} else {
			whsSelection = 0;
			partNo = json.getString("PARTNO");
			npartNo = json.getString("NPARTNO");
			productName = json.getString("PRODUCTNAME");
			outQty = json.getString("OUTQTY");
			price = json.getString("SOPRICE");
			logNo = json.getString("LOGNO");
			whs = json.getString("WHS");
			remarks = json.getString("REMARKS");
			
			for (int i = 0; i < whsList.size(); i++) {
				if (whs.equals(whsList.get(i).getValue())) {
					whsSelection = i;
				}
			}
		}
	}

	public int getWhsSelection() {
		return whsSelection;
	}

	public void setWhsSelection(int whsSelection) {
		this.whsSelection = whsSelection;
	}

	public List<BaseKeyValue> getWhsList() {
		return whsList;
	}

	public void setWhsList(List<BaseKeyValue> whsList) {
		this.whsList = whsList;
	}

	public List<BaseKeyValue> getPriceUnitList() {
		return priceUnitList;
	}

	public void setPriceUnitList(List<BaseKeyValue> priceUnitList) {
		this.priceUnitList = priceUnitList;
	}

	public List<BaseKeyValue> getQtyUnitList() {
		return qtyUnitList;
	}

	public void setQtyUnitList(List<BaseKeyValue> qtyUnitList) {
		this.qtyUnitList = qtyUnitList;
	}

	public String getPartNo() {
		return partNo;
	}

	public void setPartNo(String partNo) {
		this.partNo = partNo;
	}

	public String getNpartNo() {
		return npartNo;
	}

	public void setNpartNo(String npartNo) {
		this.npartNo = npartNo;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getOutQty() {
		return outQty;
	}

	public void setOutQty(String outQty) {
		this.outQty = outQty;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getLogNo() {
		return logNo;
	}

	public void setLogNo(String logNo) {
		this.logNo = logNo;
	}

	public String getWhs() {
		return whs;
	}

	public void setWhs(String whs) {
		this.whs = whs;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

}
