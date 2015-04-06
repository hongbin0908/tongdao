package com.tongdao.mission;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.tongdao.R;
import com.tongdao.mission.diet.MissionDietActivity;
import com.tongdao.mission.diet.MissionUdefActivity;

public class SelectMissionActivity extends Activity{

	private ListView mlvSelectMissionList = null;
	private Button mbnUdef = null;
	private ArrayList<HashMap<String, Object>> listItem = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.select_mission);
		TextView tvSelectMissionText = (TextView)findViewById(R.id.select_mission_text);
		tvSelectMissionText.setText("请选择类别：");
		
		mlvSelectMissionList = (ListView)findViewById(R.id.select_mission_list);
		setData();
		
		mbnUdef = (Button)findViewById(R.id.select_mission_udef);
		mbnUdef.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();  	
					intent.setClass(SelectMissionActivity.this, MissionUdefActivity.class);
					SelectMissionActivity.this.startActivity(intent); 
			}
		});
	}
	
	private void setData() {
		List<String> mList = new ArrayList<String>();
		mList.add("减肥");
		mList.add("考研");
		mList.add("早起");
		mList.add("四六级");

		// 生成动态数组，加入数据   
		listItem = new ArrayList<HashMap<String, Object>>();  
         
        HashMap<String, Object> map = new HashMap<String, Object>();  
        map.put("ItemImage", R.drawable.icon_home);
        map.put("ItemTitle", "减肥");  
        map.put("ItemText", "减肥");    
        listItem.add(map);
        
        map = new HashMap<String, Object>();  
        map.put("ItemImage", R.drawable.icon_home);
        map.put("ItemTitle", "考研");  
        map.put("ItemText", "考研");    
        listItem.add(map);
        
        map = new HashMap<String, Object>();  
        map.put("ItemImage", R.drawable.icon_home);
        map.put("ItemTitle", "早起");  
        map.put("ItemText", "早起");    
        listItem.add(map);
        
        map = new HashMap<String, Object>();  
        map.put("ItemImage", R.drawable.icon_home);
        map.put("ItemTitle", "四六级");  
        map.put("ItemText", "四六级");    
        listItem.add(map);
        
        
        // 生成适配器的Item和动态数组对应的元素，这里用SimpleAdapter作为ListView的数据源   
        // 如果条目布局比较复杂，可以继承BaseAdapter来定义自己的数据源。   
        // 生成一个SimpleAdapter类型的变量来填充数据   
		SimpleAdapter listItemAdapter = new SimpleAdapter(
				this,//
				listItem,//
				R.layout.select_mission_listitem,//
				new String[] { "ItemImage", "ItemTitle", "ItemText" },
				new int[] { R.id.select_mission_listitem_img,
						R.id.select_mission_listitem_main,
						R.id.select_mission_listitem_desc });

        // 添加并显示   
		mlvSelectMissionList.setAdapter(listItemAdapter);  
  
        // 添加点击   
		mlvSelectMissionList.setOnItemClickListener(new OnItemClickListener() {  
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent intent = new Intent();  
				if (listItem.get(position).get("ItemTitle").equals("减肥")) {
					intent.setClass(SelectMissionActivity.this, MissionDietActivity.class);
					SelectMissionActivity.this.startActivity(intent);
				}
	            
				
			}  
        });  
	}
}
