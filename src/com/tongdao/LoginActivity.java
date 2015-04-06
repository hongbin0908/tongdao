package com.tongdao;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;
import org.json.JSONTokener;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.tongdao.component.EditTextWithLabel;
import com.tongdao.http.HttpHelper;

/**
 * <p>鏁翠釜娴佺▼灏卞儚浣跨敤ListView鑷畾BaseAdapter涓�鏍�</p>
 * 
 * <p>濡傛灉瑕佽嚜瀹氫箟TabHostActivity鐨凾heme锛屽苟涓斾笉鎯宠澶撮儴闃村奖
 * 涓�瀹氳娣诲姞杩欎釜android:windowContentOverlay = null</p>
 * 
 * <p>濡傛灉鎯冲湪鍒殑椤圭洰閲岄潰浣跨敤TabHostActivity
 * 鍙互椤圭洰鐨勫睘鎬ч噷闈㈡壘鍒癆ndroid锛岀劧鍚庡湪Library閮ㄥ垎娣诲姞杩欎釜椤圭洰(Api)
 * <a href="http://www.cnblogs.com/qianxudetianxia/archive/2011/05/01/2030232.html">濡備綍娣诲姞</a></p>
 * */
public class LoginActivity extends Activity {
	private EditTextWithLabel elLoginUsername = null;
	private EditTextWithLabel elLoginPasswd = null;
	private Button btLoginSubmit = null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
		elLoginUsername = (EditTextWithLabel)findViewById(R.id.login_username);
		elLoginPasswd = (EditTextWithLabel)findViewById(R.id.login_passwd);
		btLoginSubmit = (Button)findViewById(R.id.login_submmit);
		
		btLoginSubmit.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				
				Map<String,String> params = new HashMap<String, String>();
				params.put("user", elLoginUsername.getContent());
				params.put("pass", elLoginPasswd.getContent());
				
				String result = HttpHelper.getResultWithUser("login.php", params);
				if (result == null) {
					Toast.makeText(LoginActivity.this, "can not visit server" , Toast.LENGTH_LONG).show(); 
				}
				try {
					
					
					JSONTokener jsonParser = new JSONTokener(result); 
					JSONObject jresult = (JSONObject) jsonParser.nextValue(); 
					int status = jresult.getInt("status");
					if (status == 0) {
						SharedPreferences spUserinfo= getSharedPreferences("userinfo",
								Activity.MODE_PRIVATE); 
						Editor spEditor = spUserinfo.edit();
						spEditor.putString("username", elLoginUsername.getContent());
						spEditor.putString("password", elLoginPasswd.getContent());
						spEditor.commit(); 
						Intent intent = new Intent();  
		                 intent.setClass(LoginActivity.this, MainActivity.class);
		                 intent.putExtra("tab", 0);  
		                 LoginActivity.this.startActivity(intent);
						
					} else {
						
						Toast.makeText(LoginActivity.this, "用戶名不存在",
								Toast.LENGTH_LONG).show(); 
					}
					
				} catch(Exception e) {
					e.printStackTrace();
					
				}
				
				
				
			}
		});
		
	}

}
