package com.explore.android.mobile.data.predata;

import java.util.ArrayList;
import java.util.List;

import com.explore.android.R;
import com.explore.android.core.model.BaseKeyValue;
import com.explore.android.mobile.constants.SDConstants;

public class SDSearchPreData {

	public static List<BaseKeyValue> outtypeList;
	public static List<BaseKeyValue> stsList;
	public static List<BaseKeyValue> whsList;
	public static List<BaseKeyValue> isDdList;
	public static List<BaseKeyValue> transportStsList;
	
	public static void init(){
		
		if(outtypeList == null){
			outtypeList = new ArrayList<BaseKeyValue>();
		} else {
			outtypeList.clear();
		}
		
		outtypeList.add(new BaseKeyValue(R.string.sd_outtype_title, ""));
		outtypeList.add(new BaseKeyValue(R.string.sd_outtype_sal, SDConstants.OUTTYPE_SAL));
		outtypeList.add(new BaseKeyValue(R.string.sd_outtype_pol, SDConstants.OUTTYPE_POL));
		outtypeList.add(new BaseKeyValue(R.string.sd_outtype_oti, SDConstants.OUTTYPE_OTI));
		outtypeList.add(new BaseKeyValue(R.string.sd_outtype_rel, SDConstants.OUTTYPE_REL));
		outtypeList.add(new BaseKeyValue(R.string.sd_outtype_cbf, SDConstants.OUTTYPE_CBF));
		outtypeList.add(new BaseKeyValue(R.string.sd_outtype_dbf, SDConstants.OUTTYPE_DBF));
		
		if(stsList == null){
			stsList = new ArrayList<BaseKeyValue>();
		} else {
			stsList.clear();
		}
		
		stsList.add(new BaseKeyValue(R.string.sd_outsts_title, ""));
		stsList.add(new BaseKeyValue(R.string.sd_outsts_new, SDConstants.OUTSTS_NEW));
		stsList.add(new BaseKeyValue(R.string.sd_outsts_fie, SDConstants.OUTSTS_FIE));
		stsList.add(new BaseKeyValue(R.string.sd_outsts_rej, SDConstants.OUTSTS_REJ));
		stsList.add(new BaseKeyValue(R.string.sd_outsts_tsm, SDConstants.OUTSTS_TSM));
		stsList.add(new BaseKeyValue(R.string.sd_outsts_rmk, SDConstants.OUTSTS_RMK));
		
		if(transportStsList == null){
			transportStsList = new ArrayList<BaseKeyValue>();
		} else {
			transportStsList.clear();
		}
		
		stsList.add(new BaseKeyValue(R.string.sd_transsts_title, ""));
		transportStsList.add(new BaseKeyValue(R.string.sd_outsts_dsh, SDConstants.OUTSTS_DSH));
		transportStsList.add(new BaseKeyValue(R.string.sd_outsts_dqs, SDConstants.OUTSTS_DQS));
		transportStsList.add(new BaseKeyValue(R.string.sd_outsts_clo, SDConstants.OUTSTS_CLO));
		
		
		if(whsList == null){
			whsList = new ArrayList<BaseKeyValue>();
		} else {
			whsList.clear();
		}
		
		whsList.add(new BaseKeyValue(R.string.sd_whs_title, ""));
		whsList.add(new BaseKeyValue(R.string.sd_whs_good, SDConstants.WHS_01));
		whsList.add(new BaseKeyValue(R.string.sd_whs_overday, SDConstants.WHS_02));
		whsList.add(new BaseKeyValue(R.string.sd_whs_back, SDConstants.WHS_50));
		whsList.add(new BaseKeyValue(R.string.sd_whs_scrap, SDConstants.WHS_99));
		whsList.add(new BaseKeyValue(R.string.sd_whs_package, SDConstants.WHS_49));
		
		if(isDdList == null){
			isDdList = new ArrayList<BaseKeyValue>();
		} else {
			isDdList.clear();
		}
		
		isDdList.add(new BaseKeyValue(R.string.sd_isdd_title, ""));
		isDdList.add(new BaseKeyValue(R.string.app_yes, "Y"));
		isDdList.add(new BaseKeyValue(R.string.app_no, "N"));
		
	}
	
	public static void clear(){
		outtypeList.clear();
		stsList.clear();
		whsList.clear();
		isDdList.clear();
		transportStsList.clear();
		outtypeList = null;
		stsList = null;
		whsList = null;
		isDdList = null;
		transportStsList = null;
	}
}
