package com.tongdao.me;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONTokener;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.tongdao.R;
import com.tongdao.http.HttpHelper;
import com.tongdao.mission.SelectMissionActivity;
import com.tongdao.mission.diet.DailyActivity;

public class MeActivity extends Activity{
	private ArrayList<HashMap<String, Object>> listItem = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.me_main);
	ListView list = (ListView)findViewById(R.id.me_main_view2);
		
		//list.addFooterView(getBottomView());
		Button bnFindMission = (Button)findViewById(R.id.me_main_create_mission);
		bnFindMission.setOnClickListener(new View.OnClickListener() {	
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();  
	            intent.setClass(MeActivity.this, SelectMissionActivity.class);  
	            MeActivity.this.startActivity(intent);
				
			}
		});
	
		// 生成动态数组，加入数据   
        
        
        Map<String,String> params = new HashMap<String,String>();
        SharedPreferences spUserInfo = getSharedPreferences("userinfo", Activity.MODE_PRIVATE);
        params.put("user", spUserInfo.getString("username", ""));
        params.put("pass", spUserInfo.getString("password", "")); 
		String result = HttpHelper.getResultWithUser("me.php", params);
		
		listItem = new ArrayList<HashMap<String, Object>>();
		try {		 
			//result = "[{\"title\":\"diet\",\"starttime\":\"2015-02-24\",\"endtime\":\"2016-02-24\",\"cash\":\"0\",\"motto\":\"dfd\"}]";
			JSONTokener jsonParser = new JSONTokener(result); 
			JSONArray missions = (JSONArray) jsonParser.nextValue(); 
			for (int i = 0 ; i < missions.length(); i++) { 
				HashMap<String, Object> map = new HashMap<String, Object>(); 
				map.put("ItemImage", R.drawable.icon_home);
				map.put("ItemTitle", missions.getJSONObject(i).getString("title"));
				map.put("ItemText", "任务截止日期" + missions.getJSONObject(i).getString("endtime"));
				map.put("ItemSign", missions.getJSONObject(i).getString("motto"));
				map.put("missionid", missions.getJSONObject(i).getString("missionid"));
				listItem.add(map);  
			}
		} catch(Exception e) {
			e.printStackTrace();
			
		}
        
        // 生成适配器的Item和动态数组对应的元素，这里用SimpleAdapter作为ListView的数据源   
        // 如果条目布局比较复杂，可以继承BaseAdapter来定义自己的数据源。   
        // 生成一个SimpleAdapter类型的变量来填充数据   
        SimpleAdapter listItemAdapter = new SimpleAdapter(  
                this,// this是当前Activity的对象   
                listItem,// 数据源 为填充数据后的ArrayList类型的对象     
                R.layout.me_listitem,// 子项的布局.xml文件名   
                new String[] { "ItemImage", "ItemTitle", "ItemText", "ItemSign" },  
                //这个String数组中的元素就是list对象中的列，list中有几这个数组中就要写几列。                         
                new int[] { R.id.ItemImage, R.id.ItemTitle, R.id.ItemText,  
                        R.id.ItemSign });//值是对应XML布局文件中的一个ImageView,三个TextView的id    
        // 添加并显示   
        list.setAdapter(listItemAdapter);  
  
        // 添加点击   
        list.setOnItemClickListener(new OnItemClickListener() {  
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent intent = new Intent(); 
	            intent.setClass(MeActivity.this, DailyActivity.class);  
	            int missionid = Integer.parseInt((String) listItem.get(position).get("missionid"));
	            intent.putExtra( "missionid", missionid); 
	            MeActivity.this.startActivity(intent); 
				
			}  
        });  
	}
	
	private View getBottomView() {
		View view = LayoutInflater.from(MeActivity.this).inflate(
				R.layout.me_bottom, null);
		return view;
	}
}
