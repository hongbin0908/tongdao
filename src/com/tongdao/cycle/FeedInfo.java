package com.tongdao.cycle;


import java.util.ArrayList;
import java.util.List;

import com.tongdao.bean.UserImgs;

public class FeedInfo {

	private String user;
	private String titleName;
	private List<UserImgs> ui = new ArrayList<UserImgs>();
	private String content;
	private List<String> zans = new ArrayList<String>();
	private List<String> comments = new ArrayList<String>();
	private String id;
	private int pos = 0;
	private String latitude = null;
	private String longitude = null;
	private String city = null;
	private String district =null;
	private String poi = null;
	private String address = null;

	public List<UserImgs> getUi() {
		return ui;
	}

	public void setUi(List<UserImgs> ui) {
		this.ui = ui;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public List<String> getZans() {
		return zans;
	}

	public void setZans(List<String> zans) {
		this.zans = zans;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public List<String> getComments() {
		return comments;
	}

	public void setComments(List<String> comments) {
		this.comments = comments;
	}

	public int getPos() {
		return pos;
	}

	public void setPos(int pos) {
		this.pos = pos;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public String getPoi() {
		return poi;
	}

	public void setPoi(String poi) {
		this.poi = poi;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getTitleName() {
		return titleName;
	}

	public void setTitleName(String titleName) {
		this.titleName = titleName;
	}

}
