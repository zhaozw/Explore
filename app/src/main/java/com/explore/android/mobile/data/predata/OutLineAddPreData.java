package com.explore.android.mobile.data.predata;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.explore.android.R;
import com.explore.android.core.model.BaseKeyValue;
import com.explore.android.mobile.constants.SDConstants;

/**
 * Get Data for adapter of Spinner.
 * 
 * @author Ryan
 */
public class OutLineAddPreData {
	
	private int priceUnitPosition;
	private int qtyUnitPosition;
	private String outQty;
	private String soPriceLineId;
	private String soPrice;
	private String logNo;
	private String remarks;

	private List<BaseKeyValue> wmsList;
	private List<BaseKeyValue> priceUnitList;
	private List<BaseKeyValue> qtyUnitList;
	
	public List<BaseKeyValue> getDefaultWmsList(){
		if(wmsList == null){
			wmsList = new ArrayList<BaseKeyValue>();
		} else if(wmsList.size() > 0 ){
			wmsList.clear();
		}
		
		wmsList.add(new BaseKeyValue("", ""));
		wmsList.add(new BaseKeyValue(R.string.sd_whs_good, SDConstants.WHS_01));
		wmsList.add(new BaseKeyValue(R.string.sd_whs_overday, SDConstants.WHS_02));
		wmsList.add(new BaseKeyValue(R.string.sd_whs_back, SDConstants.WHS_50));
		wmsList.add(new BaseKeyValue(R.string.sd_whs_scrap, SDConstants.WHS_99));
		wmsList.add(new BaseKeyValue(R.string.sd_whs_package, SDConstants.WHS_49));
        wmsList.add(new BaseKeyValue(R.string.sd_whs_tj, SDConstants.WHS_TJ));
        wmsList.add(new BaseKeyValue(R.string.sd_whs_zp, SDConstants.WHS_ZP));
        wmsList.add(new BaseKeyValue(R.string.sd_whs_broken, SDConstants.WHS_89));
		
		return wmsList;
	}
	
	public void setPriceUnitList(JSONArray jsonArray) throws JSONException {
		
		if(priceUnitList == null){
			priceUnitList = new ArrayList<BaseKeyValue>();
		} else if(priceUnitList.size() > 0){
			priceUnitList.clear();
		}
		
		BaseKeyValue blankValue = new BaseKeyValue("", "");
		priceUnitList.add(blankValue);
		
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
		
		BaseKeyValue blankValue = new BaseKeyValue("", "");
		qtyUnitList.add(blankValue);
		
		for (int i = 0; i < jsonArray.length(); i++) {
			JSONObject json = jsonArray.getJSONObject(i);
			BaseKeyValue value = new BaseKeyValue();
			value.setKey(json.getString("UNIT"));
			value.setValue(json.getString("PRODUCTUNITID"));
			
			qtyUnitList.add(value);
		}
	}
	
	public void setDataInit(JSONObject json) throws JSONException{
		if(qtyUnitList == null || priceUnitList == null){
			return;
		}else{
			
			String priceUnit = json.getString("PRICEUNIT");
			String qtyUnit = json.getString("PRICEUNIT");
			
			for (int i = 0; i < qtyUnitList.size(); i++) {
				if(qtyUnit.endsWith(qtyUnitList.get(i).getKey())){
					qtyUnitPosition = i;
				}
			}
			
			for (int i = 0; i < priceUnitList.size(); i++) {
				if(priceUnit.endsWith(priceUnitList.get(i).getKey())){
					priceUnitPosition = i;
				}
			}
			
			soPriceLineId = json.getString("SOPRICELINEID");
			logNo = json.getString("LOGNO");
			soPrice = json.getString("SOPRICE");
			outQty = json.getString("QTY");
		}
	}
	
	public List<BaseKeyValue> getWmsList() {
		return wmsList;
	}

	public void setWmsList(List<BaseKeyValue> wmsList) {
		this.wmsList = wmsList;
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

	public int getPriceUnitPosition() {
		return priceUnitPosition;
	}

	public void setPriceUnitPosition(int priceUnitPosition) {
		this.priceUnitPosition = priceUnitPosition;
	}

	public int getQtyUnitPosition() {
		return qtyUnitPosition;
	}

	public void setQtyUnitPosition(int qtyUnitPosition) {
		this.qtyUnitPosition = qtyUnitPosition;
	}

	public String getSoPriceLineId() {
		return soPriceLineId;
	}

	public void setSoPriceLineId(String soPriceLineId) {
		this.soPriceLineId = soPriceLineId;
	}

	public String getSoPrice() {
		return soPrice;
	}

	public void setSoPrice(String soPrice) {
		this.soPrice = soPrice;
	}

	public String getLogNo() {
		return logNo;
	}

	public void setLogNo(String logNo) {
		this.logNo = logNo;
	}

	public String getOutQty() {
		return outQty;
	}

	public void setOutQty(String outQty) {
		this.outQty = outQty;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	
}
