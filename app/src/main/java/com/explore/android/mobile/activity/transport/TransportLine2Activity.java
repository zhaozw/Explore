package com.explore.android.mobile.activity.transport;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.explore.android.R;
import com.explore.android.core.base.BaseActivity;
import com.explore.android.core.http.VolleyHelper;
import com.explore.android.core.util.DateUtil;
import com.explore.android.mobile.constants.RequestConstants;
import com.explore.android.mobile.constants.ResponseConstant;
import com.explore.android.mobile.data.request.TransportLine2Request;
import com.explore.android.mobile.model.TransportLine2;
import com.explore.android.mobile.view.PullToRefreshView;
import com.explore.android.mobile.view.PullToRefreshView.OnHeaderRefreshListener;
import com.explore.android.mobile.view.PullToRefreshView.PullMode;
import com.google.gson.Gson;

public class TransportLine2Activity extends BaseActivity implements
		OnHeaderRefreshListener {

	private static final String LOC = "LOC";
	private static final String TRANSPORTID = "TRANSPORTID";

	private PullToRefreshView mPullToRefreshView;
	private ListView listView;
	private RelativeLayout loading;
	private String transportId;
	private TextView emptyView;
	private String loc;

	private List<TransportLine2> transportLine2List;
	private TransportLine2Adapter adapter;

	@Override
	protected int setLayoutId() {
		return R.layout.activity_transportline2;
	}

	@Override
	public void initViews() {
		mPullToRefreshView = (PullToRefreshView) findViewById(R.id.act_transportline2_pullrefresh_view);
		listView = (ListView) findViewById(R.id.act_transportline2_list);
		loading = (RelativeLayout) findViewById(R.id.app_loading_layout);
		emptyView = (TextView) findViewById(R.id.act_transportline2_list_empty);
		listView.setEmptyView(emptyView);
		mPullToRefreshView.setPullMode(PullMode.NOFOOT);

		getActionBar().setDisplayHomeAsUpEnabled(true);
	}

	@Override
	public void initValues() {
		Intent preIntent = getIntent();
		if (preIntent.hasExtra(LOC) && preIntent.hasExtra(TRANSPORTID)) {
			transportId = preIntent.getStringExtra(TRANSPORTID);
			loc = preIntent.getStringExtra(LOC);
			loadTransportLine2();
			mPullToRefreshView.setOnHeaderRefreshListener(this);

			transportLine2List = new ArrayList<TransportLine2>();
			adapter = new TransportLine2Adapter();
			listView.setAdapter(adapter);
			StringBuilder title = new StringBuilder();
			title.append(getString(R.string.transportline2_title));
			title.append(" [");
			title.append(loc);
			title.append(getString(R.string.transport_loc));
			title.append("]");
			getActionBar().setTitle(title);
		}
	}

	@Override
	public void initListener() {
		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View view,
					int position, long arg3) {
				CheckBox isChecked = (CheckBox) view
						.findViewById(R.id.item_transportline2_check);
				if (isChecked.isChecked()) {
					isChecked.setChecked(false);
					transportLine2List.get(position).setChecked(false);
				} else {
					isChecked.setChecked(true);
					transportLine2List.get(position).setChecked(true);
				}
			}
		});

		emptyView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				transportLine2List.clear();
				loadTransportLine2();
			}
		});
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if(item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
		return super.onOptionsItemSelected(item);
	}

	private void loadTransportLine2() {
		loading.setVisibility(View.VISIBLE);
		TransportLine2Request request = new TransportLine2Request();
		request.setUserId(getPreferences().getUserId());
		request.setToken(getPreferences().getTokenLast8());
		request.setTransportId(transportId);
		request.setLoc(loc);
		VolleyHelper volley = new VolleyHelper(this);
		volley.setOnResponseListener(new Listener<JSONObject>() {
			@Override
			public void onResponse(JSONObject response) {
				loading.setVisibility(View.GONE);
				bindTransportLine2Data(response);
				mPullToRefreshView.onHeaderRefreshComplete(DateUtil
						.dateFormatWithDayTime(new Date()));
			}
		});
		volley.setOnErrorListener(new ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError error) {
				loading.setVisibility(View.GONE);
				mPullToRefreshView
						.onHeaderRefreshComplete(getString(R.string.msg_last_request_failed));
			}
		});
		volley.httpRequest(getPreferences().getHttpUrl(),
				RequestConstants.TRANSPORT_TRANSPORTLINE2,
				request.toJsonString());

	}

	private void bindTransportLine2Data(JSONObject jsonObject) {
		try {
			if (ResponseConstant.OK.equals(jsonObject
					.get(ResponseConstant.STATUS))) {
				JSONArray array = jsonObject.getJSONArray("TRANSPORTLINE2LIST");
				int length = array.length();
				Gson gson = new Gson();
				for (int i = 0; i < length; i++) {
					transportLine2List.add(gson.fromJson(array.getString(i),
							TransportLine2.class));
				}
				adapter.notifyDataSetChanged();
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onHeaderRefresh(PullToRefreshView view) {
		mPullToRefreshView.postDelayed(new Runnable() {
			@Override
			public void run() {
				transportLine2List.clear();
				loadTransportLine2();
			}
		}, 300);
	}

	class TransportLine2Adapter extends BaseAdapter {

		private LayoutInflater mInflater;

		public TransportLine2Adapter() {
			mInflater = LayoutInflater.from(TransportLine2Activity.this);
		}

		@Override
		public int getCount() {
			return transportLine2List.size();
		}

		@Override
		public Object getItem(int position) {
			return transportLine2List.get(position);
		}

		@Override
		public long getItemId(int id) {
			return id;
		}

		@Override
		public View getView(int position, View view, ViewGroup arg2) {
			if (view == null) {
				view = mInflater.inflate(R.layout.list_transportline2_item,
						null);
			}
			((TextView) view.findViewById(R.id.item_transportline2_info1))
					.setText(transportLine2List.get(position).getNpartno());
			((TextView) view.findViewById(R.id.item_transportline2_info2))
					.setText(transportLine2List.get(position).getProductName());
			((CheckBox) view.findViewById(R.id.item_transportline2_check))
					.setChecked(transportLine2List.get(position).isChecked());
			return view;
		}

	}

}