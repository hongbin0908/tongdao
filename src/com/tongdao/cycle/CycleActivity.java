package com.tongdao.cycle;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONTokener;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputType;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.tongdao.MainActivity;
import com.tongdao.R;
import com.tongdao.bean.UserImgs;
import com.tongdao.http.HttpHelper;

@SuppressLint("NewApi")
public class CycleActivity extends Activity {

	private ListView mListView = null;
	public TextView mtvCommetIn = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.cycle_main);
		mListView = (ListView) findViewById(R.id.cycle_main);
		
		
		mListView.setOnScrollListener(new AbsListView.OnScrollListener() {
			
			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
				Toast.makeText(CycleActivity.this,   "onScrollStateChanged",
		                 Toast.LENGTH_SHORT).show();	
				
				mtvCommetIn.setVisibility(View.INVISIBLE);
				
			}
			
			@Override
			public void onScroll(AbsListView view, int firstVisibleItem,
					int visibleItemCount, int totalItemCount) {
				
				
				
			}
		});
		//mListView.addHeaderView(getheadView());
		mtvCommetIn = (TextView) findViewById(R.id.commet_in);
		mtvCommetIn.setFocusable(true);
		mtvCommetIn.setInputType(InputType.TYPE_TEXT_VARIATION_SHORT_MESSAGE);
		mtvCommetIn.setImeOptions(EditorInfo.IME_FLAG_NO_EXTRACT_UI|EditorInfo.IME_ACTION_DONE);
		mtvCommetIn.setOnEditorActionListener(new TextView.OnEditorActionListener() {
			
			@Override
			public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
				 if (actionId == EditorInfo.IME_ACTION_DONE) {
		             Toast.makeText(CycleActivity.this, "ÆÀÂÛ³É¹¦",
		                 Toast.LENGTH_SHORT).show();
		             //ImageView ivFeedBtn = (ImageView) v.getTag();
		             FeedInfo feed = (FeedInfo)v.getTag(R.id.tag_first);
		             TextView tvComments = (TextView)v.getTag(R.id.tag_second);
		             Map<String, String> params = new HashMap<String, String>();
		             SharedPreferences sp = v.getContext().getSharedPreferences("userinfo", Activity.MODE_PRIVATE);
		             params.put("user", sp.getString("username", ""));
		             params.put("pass", sp.getString("password", ""));
		             params.put("userfrom", sp.getString("username", ""));
		             params.put("userto", feed.getUser());
		             params.put("missionid", feed.getId());
		             params.put("content", v.getText().toString());
		             
		             String result = HttpHelper.getResultWithUser("comments.php", params);
		   
		             
		             Toast.makeText(CycleActivity.this, tvComments.getText(),
			                 Toast.LENGTH_SHORT).show();
		             v.setVisibility(View.INVISIBLE);
		             Intent intent = new Intent();  
	                 intent.setClass(CycleActivity.this, MainActivity.class);
	                 intent.putExtra("tab", 1);  
	                 intent.putExtra("pos", feed.getPos());
	                 intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
	                 CycleActivity.this.startActivity(intent);           
		         
		         } else {
		        	 Toast.makeText(CycleActivity.this, actionId + "",
			                 Toast.LENGTH_SHORT).show();
		         }

				 return false;
			}
		});
		
		mtvCommetIn.setOnFocusChangeListener(new View.OnFocusChangeListener() {
			
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if (hasFocus) {
					Toast.makeText(CycleActivity.this,   "Focused",
			                 Toast.LENGTH_SHORT).show();	
					InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);  
					imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);  
				} else {
					Toast.makeText(CycleActivity.this,  "Lose Foucus",
			                 Toast.LENGTH_SHORT).show();
					v.setVisibility(View.INVISIBLE);
					((InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(v.getWindowToken(), 0);
				}
				
			}
		});
		
	
		//mtvCommetIn.setFocusableInTouchMode(true);
		//mtvCommetIn.requestFocus();
		//InputMethodManager imm = (InputMethodManager)mtvCommetIn.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
		//imm.toggleSoftInput(0, InputMethodManager.SHOW_FORCED);
		setData();
		
		int pos = 0;
		Bundle bundle = this.getIntent().getExtras();
		if (bundle != null) {
			pos = bundle.getInt("pos");
		}
		mListView.setSelection(pos);
		
		
	}
	

	private void setData() {
		
		String strUrl = HttpHelper.surl + "cycle.php?user=hongbin&pass=hongbin";
		URL url = null;
		String result = "";
		List<FeedInfo> mList = new ArrayList<FeedInfo>();
		try {
			url = new URL(strUrl);
			HttpURLConnection urlConn = (HttpURLConnection) url
					.openConnection();
			InputStreamReader in = new InputStreamReader(
					urlConn.getInputStream());
			BufferedReader bufferReader = new BufferedReader(in);
			result = "";
			String readLine = "";
			while ((readLine = bufferReader.readLine()) != null) {
				result += readLine;
			}
			in.close();
			urlConn.disconnect();

			JSONTokener jsonParser = new JSONTokener(result);
			JSONArray jFeeds = (JSONArray) jsonParser.nextValue();
			for (int i = 0; i < jFeeds.length(); i++) {
				FeedInfo feed = new FeedInfo();
				feed.setUser(jFeeds.getJSONObject(i).getString("username"));
				feed.setId(jFeeds.getJSONObject(i).getString("id"));
				feed.setContent(jFeeds.getJSONObject(i).getString("content"));
				UserImgs m = new UserImgs();
				m.setUrls(jFeeds.getJSONObject(i).getString("image"));
				feed.getUi().add(m);
				List<String> zans = new ArrayList<String>();
				JSONArray jZans = jFeeds.getJSONObject(i).getJSONArray("zan");
				for (int j = 0; j < jZans.length(); j++) {
					zans.add(jZans.getString(j));
				}
				feed.setZans(zans);
				
				List<String> comments = new ArrayList<String>();
				JSONArray jComments = jFeeds.getJSONObject(i).getJSONArray("comments");
				for (int j = 0; j < jComments.length(); j++) {
					comments.add(jComments.getString(j));
				}
				feed.setComments(comments);
				
				feed.setLatitude(jFeeds.getJSONObject(i).getString("latitude"));
				feed.setLongitude(jFeeds.getJSONObject(i).getString("longitude"));
				feed.setCity(jFeeds.getJSONObject(i).getString("city"));
				feed.setDistrict(jFeeds.getJSONObject(i).getString("district"));
				feed.setPoi(jFeeds.getJSONObject(i).getString("poi"));
				feed.setAddress(jFeeds.getJSONObject(i).getString("address"));
				
				feed.setTitleName(jFeeds.getJSONObject(i).getString("title"));
				

				mList.add(feed);
			}
		} catch (Exception e) {
			e.printStackTrace();

		}
		CycleAdapter cycleAdapter = new CycleAdapter(this);
		cycleAdapter.setData(mList);
		mListView.setAdapter(cycleAdapter);
	}

	/*private View getheadView() {
		View view = LayoutInflater.from(CycleActivity.this).inflate(
				R.layout.cycle_head, null);
		return view;
	}*/
	
}
