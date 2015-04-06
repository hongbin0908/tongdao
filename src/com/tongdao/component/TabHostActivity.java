package com.tongdao.component;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;
import android.widget.TabWidget;
import android.widget.TextView;

import com.tongdao.R;

public abstract class TabHostActivity extends TabActivity {

	private TabHost mTabHost;
	private TabWidget mTabWidget;
	private LayoutInflater mLayoutflater;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// set theme because we do not want the shadow
		setTheme(R.style.Theme_Tabhost);
		setContentView(R.layout.api_tab_host);
		
		mLayoutflater = getLayoutInflater();

		mTabHost = getTabHost();
		mTabWidget = getTabWidget();
		//mTabWidget.setStripEnabled(false);	// need android2.2
		
		prepare();

		initTop();
		initTabSpec();
	}
	
	private void initTop() {
		View child = getTop();
		LinearLayout layout = (LinearLayout) findViewById(R.id.tab_top);
		layout.addView(child);
	}

	private void initTabSpec() {

		int count = getTabItemCount();

		for (int i = 0; i < count; i++) {
			// set text view
			View tabItem = mLayoutflater.inflate(R.layout.api_tab_item, null);
			
			TextView tvTabItem = (TextView) tabItem.findViewById(R.id.tab_item_tv);
			setTabItemTextView(tvTabItem, i);
			// set id
			String tabItemId = getTabItemId(i);
			// set tab spec
			TabSpec tabSpec = mTabHost.newTabSpec(tabItemId);
			tabSpec.setIndicator(tabItem);
			tabSpec.setContent(getTabItemIntent(i));
			
			mTabHost.addTab(tabSpec);
		}

	}

	/** 鍦ㄥ垵濮嬪寲鐣岄潰涔嬪墠璋冪敤 */
	protected void prepare() {
		// do nothing or you override it
	}

	/** 鑷畾涔夊ご閮ㄥ竷灞� */
	protected View getTop() {
		// do nothing or you override it
		return null;
	}
	
	protected int getTabCount() {
		return mTabHost.getTabWidget().getTabCount();
	}

	/** 璁剧疆TabItem鐨勫浘鏍囧拰鏍囬绛�*/
	abstract protected void setTabItemTextView(TextView textView, int position);

	abstract protected String getTabItemId(int position);
	
	abstract protected Intent getTabItemIntent(int position);

	abstract protected int getTabItemCount();
	
	public void setCurrentTab(int index) {
		mTabHost.setCurrentTab(index);
	}
	
	protected void focusCurrentTab(int index) {
		mTabWidget.focusCurrentTab(index);
	}

}
