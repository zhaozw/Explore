package com.explore.android.mobile.data;

import com.explore.android.R;
import com.explore.android.mobile.constants.SDConstants;

public class ValueToResource {

	public static int getOutType(String outtype){
		
		if(SDConstants.OUTTYPE_SAL.equals(outtype)){
			return R.string.sd_outtype_sal;
			
		} else if(SDConstants.OUTTYPE_POL.equals(outtype)){
			return R.string.sd_outtype_pol;
			
		} else if(SDConstants.OUTTYPE_OTI.equals(outtype)){
			return R.string.sd_outtype_oti;
			
		} else if(SDConstants.OUTTYPE_REL.equals(outtype)){
			return R.string.sd_outtype_rel;
			
		} else if(SDConstants.OUTTYPE_SAF.equals(outtype)){
			return R.string.sd_outtype_saf;
			
		} else if(SDConstants.OUTTYPE_CBF.equals(outtype)){
			return R.string.sd_outtype_cbf;
			
		} else if(SDConstants.OUTTYPE_DBF.equals(outtype)){
			return R.string.sd_outtype_dbf;
			
		} else if(SDConstants.OUTTYPE2_DTC.equals(outtype)){
			return R.string.sd_outtype2_dtc;
			
		} else if(SDConstants.OUTTYPE2_TTC.equals(outtype)){
			return R.string.sd_outtype2_ttc;
			
		} else if(SDConstants.OUTTYPE2_GEN.equals(outtype)){
			return R.string.sd_outtype_sal;
			
		} else{
			return R.string.app_null;
		}
	}
	
	public static int getProductStsValue(String sts){
		if(SDConstants.GENERAL_NEW.equals(sts)){
			return R.string.gen_sts_new;
			
		} else if(SDConstants.GENERAL_LOC.equals(sts)){
			return R.string.gen_sts_clo;
			
		} else if(SDConstants.GENERAL_RUN.equals(sts)){
			return R.string.gen_sts_run;
			
		} else {
			return R.string.app_null;
		}
	}
	
	public static final int getGenStsValue(String sts){
		if(SDConstants.PRODUCT_STS_NEW.equals(sts)){
			return R.string.sd_product_new;
			
		} else if(SDConstants.PRODUCT_STS_RUN.equals(sts)){
			return R.string.sd_product_run;
			
		} else if(SDConstants.PRODUCT_STS_RUN.equals(sts)){
			return R.string.sd_product_run;
			
		} else {
			return R.string.app_null;
		}
	}
	
	public static int getYNValue(String value){
		if(SDConstants.YN_Y.equals(value)){
			return R.string.app_yes;
			
		} else if(SDConstants.YN_N.equals(value)){
			return R.string.app_no;
			
		} else {
			return R.string.app_null;
		}
	}
	
	public static int getOutStsValue(String sts){
		
		if(SDConstants.OUTSTS_NEW.equals(sts)){
			return R.string.sd_outsts_new;
			
		} else if(SDConstants.OUTSTS_FIE.equals(sts)){
			return R.string.sd_outsts_fie;
			
		} else if(SDConstants.OUTSTS_REJ.equals(sts)){
			return R.string.sd_outsts_rej;
			
		} else if(SDConstants.OUTSTS_TSM.equals(sts)){
			return R.string.sd_outsts_tsm;
			
		} else if(SDConstants.OUTSTS_RMK.equals(sts)){
			return R.string.sd_outsts_rmk;
			
		} else if(SDConstants.OUTSTS_DSH.equals(sts)){
			return R.string.sd_outsts_dsh;
			
		} else if(SDConstants.OUTSTS_DQS.equals(sts)){
			return R.string.sd_outsts_dqs;
			
		} else if(SDConstants.OUTSTS_CLO.equals(sts)){
			return R.string.sd_outsts_clo;
			
		} else {
			return R.string.app_null;
		}
	}
	
	public static int getCustomerDeptValue(String value){
		if(SDConstants.CUSTOMER_YES.equals(value)){
			return R.string.app_yes;
			
		} else if(SDConstants.CUSTOMER_NO.equals(value)){
			return R.string.app_no;
			
		} else if(SDConstants.CUSTOMER_ZQ_GDR.equals(value)){
			return R.string.customer_modify_dztype_gdr;
			
		} else if(SDConstants.CUSTOMER_ZQ_GDT.equals(value)){
			return R.string.customer_modify_dztype_gdt;
			
		} else if(SDConstants.CUSTOMER_DZ_JSD.equals(value)){
			return R.string.customer_modify_zqtype_jsd;
			
		} else if(SDConstants.CUSTOMER_DZ_ZQD.equals(value)){
			return R.string.customer_modify_zqtype_zqd;
			
		} else {
			return R.string.app_null;
		}
	}
}
