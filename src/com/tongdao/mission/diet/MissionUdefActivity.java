package com.tongdao.mission.diet;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import com.tongdao.MainActivity;
import com.tongdao.R;
import com.tongdao.component.EditTextWithLabel;
import com.tongdao.http.HttpHelper;

public class MissionUdefActivity extends Activity{
	private EditTextWithLabel elMissionDietMainName;
	private EditTextWithLabel elMissionDietMainStart;
	private EditTextWithLabel elMissionDietMainEnd;
	private EditTextWithLabel elMissionDietMainCash;
	private EditTextWithLabel elMissionDietMainMotto;
	
	private static final int SHOW_DATAPICK_START = 0;
	private static final int DATE_DIALOG_ID_START = 1;
	private static final int SHOW_DATAPICK_END = 2;
	private static final int DATE_DIALOG_ID_END = 3;
	
	private int mStartYear;
	private int mStartMonth;
	private int mStartDay;
	
	private int mEndYear;
	private int mEndMonth;
	private int mEndDay;
	
	/**
	 * �������ں�ʱ��ؼ���Handler
	 */
	Handler dateandtimeHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case MissionUdefActivity.SHOW_DATAPICK_START:
				showDialog(DATE_DIALOG_ID_START);
				break;
			case MissionUdefActivity.SHOW_DATAPICK_END:
				showDialog(DATE_DIALOG_ID_END);
				break;
			}
		}
	};
	
	/**
	 * ���ڿؼ����¼�
	 */
	private DatePickerDialog.OnDateSetListener mDateSetListenerStart = new DatePickerDialog.OnDateSetListener() {

		public void onDateSet(DatePicker view, int year, int monthOfYear,
				int dayOfMonth) {
			mStartYear = year;
			mStartMonth = monthOfYear;
			mStartDay = dayOfMonth;

			updateDateDisplay(elMissionDietMainStart.getEditText(), mStartYear, mStartMonth, mStartDay);
		}
	};
	
	/**
	 * ���ڿؼ����¼�
	 */
	private DatePickerDialog.OnDateSetListener mDateSetListenerEnd = new DatePickerDialog.OnDateSetListener() {

		public void onDateSet(DatePicker view, int year, int monthOfYear,
				int dayOfMonth) {
			mEndYear = year;
			mEndMonth = monthOfYear;
			mEndDay = dayOfMonth;

			updateDateDisplay(elMissionDietMainEnd.getEditText(), mEndYear, mEndMonth, mEndDay);
		}
	};
	
	
	/**
	 * ����������ʾ
	 */
	private void updateDateDisplay(EditText et, int y, int m, int d) {
		et.setText(getDateDisplay(y, m, d));
	}
	
	private String getDateDisplay(int y, int m, int d) {
		
		return new StringBuilder().append(y).append("-")
				.append((m + 1) < 10 ? "0" + (m + 1) : (m + 1))
				.append("-").append((d < 10) ? "0" + d : d).toString();
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mission_udef_main);
		final Calendar c = Calendar.getInstance();
		mStartYear = c.get(Calendar.YEAR);
		mStartMonth =c.get(Calendar.MONTH);
		mStartDay =  c.get(Calendar.DAY_OF_MONTH);
		
		c.add(Calendar.MONTH, 12);
		mEndYear = c.get(Calendar.YEAR);
		mEndMonth =c.get(Calendar.MONTH);
		mEndDay =  c.get(Calendar.DAY_OF_MONTH);

		elMissionDietMainName = (EditTextWithLabel)findViewById(R.id.mission_diet_main_name);
		elMissionDietMainName.setLabel("��������");
		elMissionDietMainStart = (EditTextWithLabel)findViewById(R.id.mission_diet_main_start);
		elMissionDietMainStart.setLabel("��ʼʱ��:");
		elMissionDietMainStart.getEditText().setInputType(InputType.TYPE_NULL);
		elMissionDietMainStart.getEditText().setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {	
					Message msg = new Message();
					if (elMissionDietMainStart.getEditText().equals((EditText) v)) {
						msg.what = MissionUdefActivity.SHOW_DATAPICK_START;
					}
					MissionUdefActivity.this.dateandtimeHandler.sendMessage(msg);
			}
		});
		
		
		
		
		elMissionDietMainEnd = (EditTextWithLabel)findViewById(R.id.mission_diet_main_end);
		elMissionDietMainEnd.setLabel("����ʱ��:");
		elMissionDietMainEnd.getEditText().setInputType(InputType.TYPE_NULL);
		elMissionDietMainEnd.getEditText().setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {	
					Message msg = new Message();
					if (elMissionDietMainEnd.getEditText().equals((EditText) v)) {
						msg.what = MissionUdefActivity.SHOW_DATAPICK_END;
					}
					MissionUdefActivity.this.dateandtimeHandler.sendMessage(msg);
			}
		});
		
		updateDateDisplay(elMissionDietMainStart.getEditText(), mStartYear, mStartMonth,mStartDay);
		updateDateDisplay(elMissionDietMainEnd.getEditText(), mEndYear, mEndMonth,mEndDay);
		
		elMissionDietMainMotto = (EditTextWithLabel)findViewById(R.id.mission_diet_main_motto);
		elMissionDietMainMotto.setLabel("����:");
		
		elMissionDietMainCash = (EditTextWithLabel)findViewById(R.id.mission_diet_main_cash);
		elMissionDietMainCash.setLabel("��֤��:");
		elMissionDietMainCash.getEditText().setInputType(InputType.TYPE_CLASS_NUMBER);
		
		Button bnMissionDietMainSubbmit = (Button)findViewById(R.id.mission_diet_mian_submmit);
		bnMissionDietMainSubbmit.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				new AlertDialog.Builder(MissionUdefActivity.this)  
				.setTitle("��������")  
				.setMessage("��������" 
						+ elMissionDietMainName.getContent() 
						+"���񣬿�ʼʱ��Ϊ" + getDateDisplay(mStartYear, mStartMonth, mStartDay) + ",����ʱ��Ϊ"
						+ getDateDisplay(mEndYear, mEndMonth, mEndDay) + "," +
						"��Ը�Ᵽ֤��Ϊ" + elMissionDietMainCash.getContent() + "\n"
						+ "��������Ҫ��" + "\n"
						+ "ÿ�췢�����������ռ��������ޣ�") 
				.setPositiveButton("ȷ��", new DialogInterface.OnClickListener(){

					@Override
					public void onClick(DialogInterface dialog, int which) {
						
						
						
						Map<String,String> params = new HashMap<String, String>();
						SharedPreferences spUserInfo = getSharedPreferences("userinfo", Activity.MODE_PRIVATE);
						params.put("name", elMissionDietMainName.getContent());
						params.put("user", spUserInfo.getString("username", ""));
						params.put("pass", spUserInfo.getString("password", ""));
						//params.put("cat", "diet");
						params.put("start", elMissionDietMainStart.getContent());
						params.put("end", elMissionDietMainEnd.getContent());
						params.put("motto", elMissionDietMainMotto.getContent());
						
						String result = HttpHelper.getResultWithUser("createmission.php", params);
			
						Intent intent = new Intent();  
			            intent.setClass(MissionUdefActivity.this, MainActivity.class);
			            intent.putExtra("tab", 0);  
			            MissionUdefActivity.this.startActivity(intent);
						
					}})  
				.setNegativeButton("ȡ��", null)  
				.show();  
				
			}
		});
		
	}
	
	
	@Override
	protected Dialog onCreateDialog(int id) {
		switch (id) {
		case DATE_DIALOG_ID_START:
			return new DatePickerDialog(this, mDateSetListenerStart, mStartYear, mStartMonth,
					mStartDay);
		case DATE_DIALOG_ID_END:
			return new DatePickerDialog(this, mDateSetListenerEnd, mEndYear, mEndMonth, 
					mEndDay);
		}

		return null;
	}

	@Override
	protected void onPrepareDialog(int id, Dialog dialog) {
		switch (id) {
		case DATE_DIALOG_ID_START:
			((DatePickerDialog) dialog).updateDate(mStartYear, mStartMonth, mStartDay);
			break;
			
		case DATE_DIALOG_ID_END:
			((DatePickerDialog) dialog).updateDate(mEndYear, mEndMonth, mEndDay);
			break;
		}
	}
}
