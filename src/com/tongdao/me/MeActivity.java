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
	
		// ���ɶ�̬���飬��������   
        
        
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
				map.put("ItemText", "�����ֹ����" + missions.getJSONObject(i).getString("endtime"));
				map.put("ItemSign", missions.getJSONObject(i).getString("motto"));
				map.put("missionid", missions.getJSONObject(i).getString("missionid"));
				listItem.add(map);  
			}
		} catch(Exception e) {
			e.printStackTrace();
			
		}
        
        // ������������Item�Ͷ�̬�����Ӧ��Ԫ�أ�������SimpleAdapter��ΪListView������Դ   
        // �����Ŀ���ֱȽϸ��ӣ����Լ̳�BaseAdapter�������Լ�������Դ��   
        // ����һ��SimpleAdapter���͵ı������������   
        SimpleAdapter listItemAdapter = new SimpleAdapter(  
                this,// this�ǵ�ǰActivity�Ķ���   
                listItem,// ����Դ Ϊ������ݺ��ArrayList���͵Ķ���     
                R.layout.me_listitem,// ����Ĳ���.xml�ļ���   
                new String[] { "ItemImage", "ItemTitle", "ItemText", "ItemSign" },  
                //���String�����е�Ԫ�ؾ���list�����е��У�list���м���������о�Ҫд���С�                         
                new int[] { R.id.ItemImage, R.id.ItemTitle, R.id.ItemText,  
                        R.id.ItemSign });//ֵ�Ƕ�ӦXML�����ļ��е�һ��ImageView,����TextView��id    
        // ��Ӳ���ʾ   
        list.setAdapter(listItemAdapter);  
  
        // ��ӵ��   
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
