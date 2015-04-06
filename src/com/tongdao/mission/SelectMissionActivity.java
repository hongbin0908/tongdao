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
		tvSelectMissionText.setText("��ѡ�����");
		
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
		mList.add("����");
		mList.add("����");
		mList.add("����");
		mList.add("������");

		// ���ɶ�̬���飬��������   
		listItem = new ArrayList<HashMap<String, Object>>();  
         
        HashMap<String, Object> map = new HashMap<String, Object>();  
        map.put("ItemImage", R.drawable.icon_home);
        map.put("ItemTitle", "����");  
        map.put("ItemText", "����");    
        listItem.add(map);
        
        map = new HashMap<String, Object>();  
        map.put("ItemImage", R.drawable.icon_home);
        map.put("ItemTitle", "����");  
        map.put("ItemText", "����");    
        listItem.add(map);
        
        map = new HashMap<String, Object>();  
        map.put("ItemImage", R.drawable.icon_home);
        map.put("ItemTitle", "����");  
        map.put("ItemText", "����");    
        listItem.add(map);
        
        map = new HashMap<String, Object>();  
        map.put("ItemImage", R.drawable.icon_home);
        map.put("ItemTitle", "������");  
        map.put("ItemText", "������");    
        listItem.add(map);
        
        
        // ������������Item�Ͷ�̬�����Ӧ��Ԫ�أ�������SimpleAdapter��ΪListView������Դ   
        // �����Ŀ���ֱȽϸ��ӣ����Լ̳�BaseAdapter�������Լ�������Դ��   
        // ����һ��SimpleAdapter���͵ı������������   
		SimpleAdapter listItemAdapter = new SimpleAdapter(
				this,//
				listItem,//
				R.layout.select_mission_listitem,//
				new String[] { "ItemImage", "ItemTitle", "ItemText" },
				new int[] { R.id.select_mission_listitem_img,
						R.id.select_mission_listitem_main,
						R.id.select_mission_listitem_desc });

        // ��Ӳ���ʾ   
		mlvSelectMissionList.setAdapter(listItemAdapter);  
  
        // ��ӵ��   
		mlvSelectMissionList.setOnItemClickListener(new OnItemClickListener() {  
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent intent = new Intent();  
				if (listItem.get(position).get("ItemTitle").equals("����")) {
					intent.setClass(SelectMissionActivity.this, MissionDietActivity.class);
					SelectMissionActivity.this.startActivity(intent);
				}
	            
				
			}  
        });  
	}
}
