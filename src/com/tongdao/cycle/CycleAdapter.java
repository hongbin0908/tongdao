package com.tongdao.cycle;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tongdao.R;
import com.tongdao.component.NoScrollGridView;


public class CycleAdapter extends BaseAdapter {

	private List<FeedInfo> mList;
	private Context mContext;

	public CycleAdapter(Context _context) {
		this.mContext = _context;
	}

	public void setData(List<FeedInfo> _list) {
		this.mList = _list;
	}

	@Override
	public int getCount() {
		return mList.size();
	}

	@Override
	public FeedInfo getItem(int position) {
		return mList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = LayoutInflater.from(mContext).inflate(
					R.layout.cycle_item, parent, false);
			holder.gridView = (NoScrollGridView) convertView
					.findViewById(R.id.gridView);
			convertView.setTag(holder);
		} else
			holder = (ViewHolder) convertView.getTag();

		FeedInfo feed = getItem(position);
		feed.setPos(position);
		
		TextView tvCycleItemUsername = (TextView)convertView.findViewById(R.id.cycle_item_username);
		tvCycleItemUsername.setText(feed.getUser());
		
		TextView tvZanuser = (TextView)convertView.findViewById(R.id.cycle_item_zanuser);
		tvZanuser.setText(feed.getZans().toString());
		
		TextView tvComments = (TextView)convertView.findViewById(R.id.tv_comment_name);
		
		TextView tvLoc = (TextView)convertView.findViewById(R.id.cycle_item_loc);
		
		TextView tvTitle = (TextView)convertView.findViewById(R.id.cycle_item_title);
		tvTitle.setText("今天更新了\"" + feed.getTitleName() + "\"任务");
		
		List<String> lComments = feed.getComments();
		String strComments = "";
		for (int i = 0 ; i < lComments.size(); i ++) {
			strComments += lComments.get(i) + "<br/>";
		}
		tvComments.setText(Html.fromHtml(strComments));
		
		TextView tvCycleItemContent = (TextView)convertView.findViewById(R.id.cycle_item_content);
		tvCycleItemContent.setText(feed.getContent());
		
		tvLoc.setText(feed.getAddress());
		
		ImageView ivZanimg = (ImageView)convertView.findViewById(R.id.cycle_item_zanimg);
		ivZanimg.setTag(R.id.tag_first, feed);
		ivZanimg.setTag(R.id.tag_second, tvZanuser);
		ivZanimg.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				FeedInfo feed = (FeedInfo)v.getTag(R.id.tag_first);
				TextView tvzanuser = (TextView)v.getTag(R.id.tag_second);
				String strUrl = "http://192.168.1.101/zan.php?user=hongbin&pass=hongbin" 
						+ "&id=" + feed.getId()
						+ "&userto=" + "hongbin";
				URL url = null;
				String result = "";
				
				try {
					url = new URL(strUrl);
					HttpURLConnection urlConn = (HttpURLConnection) url.openConnection();
					InputStreamReader in = new InputStreamReader(urlConn.getInputStream());
					BufferedReader bufferReader = new BufferedReader(in);
					result = ""; 
					String readLine = "";
					while ((readLine = bufferReader.readLine()) != null) {
						result += readLine;
					}
					in.close();
					urlConn.disconnect();
				} catch(Exception e) {
					e.printStackTrace();
					
				}
				
				feed.getZans().add("hongbin");
				tvzanuser.setText(feed.getZans().toString());
				
			}
		});
		
		final TextView tvCommetIn = ((CycleActivity)convertView.getContext()).mtvCommetIn;
		tvCommetIn.setFocusable(true);
		//tvCommetIn.requestFocus();
		ImageView ivFeedBtn = (ImageView)convertView.findViewById(R.id.feedbtn);
		ivFeedBtn.setTag(R.id.tag_first,feed);
		ivFeedBtn.setTag(R.id.tag_second, tvComments);
		
		ivFeedBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				/*FeedInfo feed = (FeedInfo)v.getTag();
				Map<String, String> params = new HashMap<String,String>();
				params.put("user",v.getContext().getSharedPreferences("userinfo", Activity.MODE_PRIVATE).getString("username", ""));
				params.put("pass",v.getContext().getSharedPreferences("userinfo", Activity.MODE_PRIVATE).getString("password", ""));
				params.put("userfrom", params.get("user"));
				params.put("userto", feed.getName());
				params.put("missionid", feed.getId());
				params.put("content", )*/
				
				CycleActivity ca = (CycleActivity)v.getContext();
				
				
				ca.mtvCommetIn.setTag(R.id.tag_first,v.getTag(R.id.tag_first));
				ca.mtvCommetIn.setTag(R.id.tag_second, v.getTag(R.id.tag_second));
				ca.mtvCommetIn.setVisibility(View.VISIBLE);
				ca.mtvCommetIn.requestFocus();
				
				
				
			}
		});
		
		//TextView tvCommentName = (TextView) convertView.findViewById(R.id.tv_comment_name);
		//tvCommentName.setText(Html.fromHtml("<b>text3:</b> Text<br/><b>text4:</b>text4<br/><b>text4:</b>text5"));
		if (mList != null && mList.size() > 0) {
			holder.gridView.setVisibility(View.VISIBLE);
			holder.gridView.setAdapter(new CycleGridAdapter(feed.getUi(), mContext));
			holder.gridView
					.setOnItemClickListener(new AdapterView.OnItemClickListener() {
						@Override
						public void onItemClick(AdapterView<?> parent,
								View view, int position, long id) {
							// imageBrower(position,bean.urls);
						}
					});
		}
		return convertView;
	}

	public class ViewHolder {
		LinearLayout mContentimg;
		NoScrollGridView gridView;
	}

}
