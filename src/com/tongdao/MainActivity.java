package com.tongdao;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TabWidget;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.location.LocationManagerProxy;
import com.amap.api.location.LocationProviderProxy;
import com.tongdao.component.TabHostActivity;
import com.tongdao.component.TabItem;
import com.tongdao.cycle.CycleActivity;
import com.tongdao.loc.LocationUtil;
import com.tongdao.me.MeActivity;

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
public class MainActivity extends TabHostActivity implements AMapLocationListener {

	List<TabItem> mItems;
	private LayoutInflater mLayoutInflater;
	private LocationManagerProxy mLocationManagerProxy;
	
	/**鍦ㄥ垵濮嬪寲TabWidget鍓嶈皟鐢�
	 * 鍜孴abWidget鏈夊叧鐨勫繀椤诲湪杩欓噷鍒濆鍖�*/
	@Override
	protected void prepare() {
		TabItem me = new TabItem(
				"我的任务",									// title
				R.drawable.icon_selfinfo,               // icon
				R.drawable.example_tab_item_bg,			// background
				new Intent(this, MeActivity.class));	// intent
		
		TabItem cycle = new TabItem(
				"任务圈",
				R.drawable.icon_selfinfo,
				R.drawable.example_tab_item_bg,
				new Intent(this, CycleActivity.class));
		Bundle bundle = this.getIntent().getExtras();
		int pos = 0;
		if (bundle != null) {
			pos = bundle.getInt("pos");
		}
		cycle.getIntent().putExtra("pos", pos);
		Toast.makeText(MainActivity.this, "" + pos,
				Toast.LENGTH_LONG).show(); 
		TabItem hot = new TabItem(
				"热门任务",
				R.drawable.icon_square,
				R.drawable.example_tab_item_bg,
				new Intent(this, MeActivity.class));
		
		TabItem more = new TabItem(
				"其他",
				R.drawable.icon_more,
				R.drawable.example_tab_item_bg,
				new Intent(this, MeActivity.class));
		
	
		
		mItems = new ArrayList<TabItem>();
		mItems.add(me);
		mItems.add(cycle);
		mItems.add(hot);
		mItems.add(more);

		// 璁剧疆鍒嗗壊绾�
		TabWidget tabWidget = getTabWidget();
		tabWidget.setDividerDrawable(R.drawable.tab_divider);
		
		mLayoutInflater = getLayoutInflater();
	} 
	
	/**
	 *      * 初始化定位      
	 */
	private void initLoc() {
		mLocationManagerProxy = LocationManagerProxy.getInstance(this);
		// 此方法为每隔固定时间会发起一次定位请求，为了减少电量消耗或网络流量消耗，
		// 注意设置合适的定位时间的间隔，并且在合适时间调用removeUpdates()方法来取消定位请求
		// 在定位结束后，在合适的生命周期调用destroy()方法     
		// 其中如果间隔时间为-1，则定位只定一次
		mLocationManagerProxy.requestLocationData(
				LocationProviderProxy.AMapNetwork, 60 * 1000, 15, this);
		mLocationManagerProxy.setGpsEnable(false);
	}

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		SharedPreferences spUser= getSharedPreferences("userinfo",
				Activity.MODE_PRIVATE);
		
		String username = spUser.getString("username", "");
		if (username == "") {
			Intent intent = new Intent();  
            intent.setClass(MainActivity.this, LoginActivity.class);
            intent.putExtra("tab", 1);  
            MainActivity.this.startActivity(intent);
            return;
		}
		
		
		int tab = 0;
		Bundle bundle = this.getIntent().getExtras();
		if (bundle != null) {
			tab = bundle.getInt("tab");
		
		}
		setCurrentTab(tab);
		initLoc();
		
		
	}
	
	/**tab鐨則itle锛宨con锛岃竟璺濊瀹氱瓑绛�*/
	@Override
	protected void setTabItemTextView(TextView textView, int position) {
		textView.setPadding(3, 3, 3, 3);
		textView.setText(mItems.get(position).getTitle());
		textView.setBackgroundResource(mItems.get(position).getBg());
		textView.setCompoundDrawablesWithIntrinsicBounds(0, mItems.get(position).getIcon(), 0, 0);
		
	}
	
	/**tab鍞竴鐨刬d*/
	@Override
	protected String getTabItemId(int position) {
		return mItems.get(position).getTitle();	// 鎴戜滑浣跨敤title鏉ヤ綔涓篿d锛屼綘涔熷彲浠ヨ嚜瀹�
	}

	/**鐐瑰嚮tab鏃惰Е鍙戠殑浜嬩欢*/
	@Override
	protected Intent getTabItemIntent(int position) {
		return mItems.get(position).getIntent();
	}

	@Override
	protected int getTabItemCount() {
		return mItems.size();
	}
	
	/**鑷畾涔夊ご閮ㄦ枃浠�*/
	@Override
	protected View getTop() {
		return mLayoutInflater.inflate(R.layout.example_top, null);
	}

	@Override
	public void onLocationChanged(Location location) {
		
		
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		Toast.makeText(MainActivity.this, 
        		"loc:" + "onStatusChanged", Toast.LENGTH_LONG).show();
		
	}

	@Override
	public void onProviderEnabled(String provider) {
		Toast.makeText(MainActivity.this, 
        		"loc:" + "onStatusChanged", Toast.LENGTH_LONG).show();
		
	}

	@Override
	public void onProviderDisabled(String provider) {
		Toast.makeText(MainActivity.this, 
        		"loc:" + "onStatusChanged", Toast.LENGTH_LONG).show();
		
	}

	@Override
	public void onLocationChanged(AMapLocation amapLocation) {
		if(amapLocation != null && amapLocation.getAMapException().getErrorCode() == 0){
            //获取位置信息
            Double geoLat = amapLocation.getLatitude();
            Double geoLng = amapLocation.getLongitude();   
            
            String city = amapLocation.getCity();
            String district = amapLocation.getDistrict();
            String poi = amapLocation.getPoiName();
            String address = amapLocation.getAddress();
            SharedPreferences sp = getSharedPreferences("userinfo", Activity.MODE_PRIVATE);
            Editor spEditor = sp.edit();
            spEditor.putString("latitude", geoLat + "");
            spEditor.putString("longitude", geoLng + "");
            spEditor.putString("city", city);
            spEditor.putString("district", district);
            spEditor.putString("poi", poi);
            spEditor.putString("address", address);
           
            spEditor.commit();
            //Toast.makeText(MainActivity.this, 
            //		"address:" + address + "poi:" + poi + "city:"+ city + "district:" +district+  "loc:" + geoLat + "," + geoLng, Toast.LENGTH_LONG).show();
            
        }
	}  
}
