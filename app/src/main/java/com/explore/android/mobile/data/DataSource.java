package com.explore.android.mobile.data;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.explore.android.mobile.activity.common.AsynDataLoaderActivity;
import com.explore.android.mobile.constants.DataConstants;
import com.explore.android.mobile.data.predata.AsynPreData;


public class DataSource {

	public static Intent getAsynDataIntent(Context context, AsynPreData preData){
		Intent intent = new Intent(context, AsynDataLoaderActivity.class);
		Bundle bundle = new Bundle();
		bundle.putString(DataConstants.DATA_URL, preData.getUrl());
		bundle.putInt(DataConstants.DATA_ACTIVITY,preData.getTitleRes());
		bundle.putString(DataConstants.DATA_JSONTITLE, preData.getJsonTitle());
		bundle.putString(DataConstants.DATA_JSONNAME, preData.getJsonName());
		bundle.putString(DataConstants.DATA_JSONVALUE, preData.getJsonValue());
		bundle.putString(DataConstants.DATA_TYPE, preData.getType());
		if(preData.getExtraParams() != null && preData.getExtraParams().length > 0) {
			bundle.putStringArray(DataConstants.DATA_EXTRA_PARAMS, preData.getExtraParams());
		}
		if(preData.getExtraDatas() != null && preData.getExtraDatas().length > 0) {
			bundle.putStringArray(DataConstants.DATA_EXTRA_DATAS, preData.getExtraDatas());
		}
		intent.putExtra(DataConstants.DATA_TITLE, bundle);
		return intent;
	}
}
