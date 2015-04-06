package com.tongdao.component;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tongdao.R;
public class EditTextWithLabel extends LinearLayout{
 
 TextView label;
 EditText text;
 
 public EditTextWithLabel(Context context,AttributeSet attrs){
  super(context, attrs);
  setOrientation(HORIZONTAL);
  LayoutInflater.from(context).inflate(R.layout.edit_text_with_label, this, true);
  label = (TextView) findViewById(R.id.comp_le_label);
  text = (EditText) findViewById(R.id.comp_le_content);
  
 }
 
 public void setLabel(String str) {
	 label.setText(str);
 }
 public boolean setSubViewFocus(){
  return text.requestFocus();
 }
 
 public EditText getEditText(){
  return text;
 }
 
 public String getContent(){
  return text.getText().toString();
 }
 
 public void setContent(String str){
  text.setText(str);
 }
 
 public void setError(String error){
  text.setError(error);
 }
 
}