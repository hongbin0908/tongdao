package com.tongdao.mission.diet;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.MediaStore.Images.ImageColumns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.tongdao.MainActivity;
import com.tongdao.R;
import com.tongdao.http.HttpHelper;

public class DailyActivity extends Activity {
    private static final int NONE = 0;
    private static final int PHOTO_GRAPH = 1;// 拍照
    private static final int PHOTO_ZOOM = 2; // 缩放
    private static final int PHOTO_RESOULT = 3;// 结果
    private static final String IMAGE_UNSPECIFIED = "image/*";
    private ImageView imageView = null;
    private EditText etContent = null;
    private Button btnPhone = null;
    private Button btnTakePicture = null;
    private Button btnUpload = null;
    
    private Uri originalUri = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mission_diet_daily);
        
        imageView = (ImageView) findViewById(R.id.imageView);
        etContent = (EditText) findViewById(R.id.mission_diet_daily_content);
        btnPhone = (Button) findViewById(R.id.btnPhone);
        btnPhone.setOnClickListener(onClickListener);
        btnTakePicture = (Button) findViewById(R.id.btnTakePicture);
        btnTakePicture.setOnClickListener(onClickListener);
        btnUpload = (Button) findViewById(R.id.mission_diet_upload);
        btnUpload.setOnClickListener(onClickListener);
        
        Bundle bundle = this .getIntent().getExtras();
        if (bundle != null) {
        	Toast.makeText (DailyActivity.this, "" + bundle.getInt("missionid"),
                    Toast. LENGTH_LONG).show();

        	
        } 
    }
    
    private final View.OnClickListener onClickListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            if(v==btnPhone){ //从相册获取图片
                Intent intent = new Intent(Intent.ACTION_PICK, null);
                intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, IMAGE_UNSPECIFIED);
                startActivityForResult(intent, PHOTO_RESOULT);
            }else if(v==btnTakePicture){ //从拍照获取图片
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(Environment
                    .getExternalStorageDirectory(),"temp.jpg")));
                startActivityForResult(intent, PHOTO_RESOULT);
            } else if (v == btnUpload) {
            	 String end = "\r\n";  
            	 String twoHyphens = "--";  
            	 String boundary = "******";
            	 
            	 try  
            	    {  
            		 Map<String,String> params = new HashMap<String,String>();
            		 SharedPreferences sp = getSharedPreferences("userinfo", Activity.MODE_PRIVATE);
            		 params.put("latitude", sp.getString("latitude", ""));
            		 params.put("longitude", sp.getString("longitude", ""));
                     params.put("city", sp.getString("city", ""));
                     params.put("district", sp.getString("district", ""));
                     params.put("poi", sp.getString("poi", ""));
                     params.put("address", sp.getString("address", ""));
            		 params.put("user", sp.getString("username", ""));
            		 params.put("pass", sp.getString("password", ""));
            		 params.put("content", etContent.getText().toString());
            		 
            		
					
            	      URL url = new URL( HttpHelper.getUrlWithGet("upload.php", params));  
            	      HttpURLConnection httpURLConnection = (HttpURLConnection) url  
            	          .openConnection();  
            
            	      // 允许输入输出流  
            	      httpURLConnection.setDoInput(true);  
            	      httpURLConnection.setDoOutput(true);  
            	      httpURLConnection.setUseCaches(false);  
            	      // 使用POST方法  
            	      httpURLConnection.setRequestMethod("POST");  
            	      httpURLConnection.setRequestProperty("Connection", "Keep-Alive");  
            	      httpURLConnection.setRequestProperty("Charset", "UTF-8");  
            	      httpURLConnection.setRequestProperty("Content-Type",  
            	          "multipart/form-data;boundary=" + boundary);  
            	  
            	      DataOutputStream dos = new DataOutputStream(  
            	          httpURLConnection.getOutputStream());  
            	      dos.writeBytes(twoHyphens + boundary + end);  
            	      
            	      String srcPath = getRealFilePath(DailyActivity.this,originalUri);
            	      dos.writeBytes("Content-Disposition: form-data; name=\"uploadedfile\"; filename=\""  
            	          + srcPath.substring(srcPath.lastIndexOf("/") + 1)  
            	          + "\""  
            	          + end);  
            	      dos.writeBytes(end);  
            	  
            	      FileInputStream fis = new FileInputStream(srcPath);  
            	      byte[] buffer = new byte[8192]; // 8k  
            	      int count = 0;  
            	      // 读取文件  
            	      while ((count = fis.read(buffer)) != -1)  
            	      {  
            	        dos.write(buffer, 0, count);  
            	      }  
            	      fis.close();  
            	  
            	      dos.writeBytes(end);  
            	      dos.writeBytes(twoHyphens + boundary + twoHyphens + end);  
            	      dos.flush();  
            	  
            	      InputStream is = httpURLConnection.getInputStream();  
            	      InputStreamReader isr = new InputStreamReader(is, "utf-8");  
            	      BufferedReader br = new BufferedReader(isr);  
            	      String result = br.readLine();  
            	  
            	      Toast.makeText(DailyActivity.this, result, Toast.LENGTH_LONG).show();  
            	      dos.close();  
            	      is.close();  
            	      Toast.makeText(DailyActivity.this, result,
          					Toast.LENGTH_LONG).show(); 
            	      
            	  
            	    } catch (Exception e)  
            	    {  
            	      e.printStackTrace();  
            	      setTitle(e.getMessage());  
            	    }  
            	 Intent intent = new Intent();  
                 intent.setClass(DailyActivity.this, MainActivity.class);
                 intent.putExtra("tab", 1);  
                 DailyActivity.this.startActivity(intent);
            }
            
           

        }

    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == NONE) {
        	Toast.makeText(DailyActivity.this, "error",
					Toast.LENGTH_LONG).show(); 
            return;
        } 
        // 拍照
        if (requestCode == PHOTO_GRAPH) {
        	Toast.makeText(DailyActivity.this, "",
					Toast.LENGTH_LONG).show(); 
            // 设置文件保存路径
            File picture = new File(Environment.getExternalStorageDirectory()
                    + "/temp.jpg");
            startPhotoZoom(Uri.fromFile(picture));
        }

       

        // 读取相册缩放图片
        if (requestCode == PHOTO_ZOOM) {
            startPhotoZoom(data.getData());
        }
        // 处理结果
        if (requestCode == PHOTO_RESOULT) {
        	 if (data != null) {
                 
        		 originalUri = data.getData();
        	 }
        	originalUri = Uri.fromFile(new File(Environment
                    .getExternalStorageDirectory(),"temp.jpg"));
        	
            if (originalUri != null) {
            	Toast.makeText(DailyActivity.this, getRealFilePath(DailyActivity.this, originalUri),
						Toast.LENGTH_LONG).show(); 
            	ContentResolver resolver = getContentResolver();
                Bitmap photo = null;
				try {
					photo = MediaStore.Images.Media.getBitmap(resolver, originalUri);
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
                if (photo != null) {
                	ByteArrayOutputStream stream = new ByteArrayOutputStream();
                	photo.compress(Bitmap.CompressFormat.JPEG, 0, stream);// (0-100)压缩文件
                	//此处可以把Bitmap保存到sd卡中，具体请看：http://www.cnblogs.com/linjiqin/archive/2011/12/28/2304940.html
                	imageView.setImageBitmap(photo); //把图片显示在ImageView控件上
                	imageView.setMaxHeight(300);
                	imageView.setMaxWidth(300);
                }
            } else {
            	Toast.makeText(DailyActivity.this, "照片不存在",
						Toast.LENGTH_LONG).show(); 
            }

        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * 收缩图片
     * 
     * @param uri
     */
    public void startPhotoZoom(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, IMAGE_UNSPECIFIED);
        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", 300);
        intent.putExtra("outputY", 500);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, PHOTO_RESOULT);
    }
    
    public static String getRealFilePath( final Context context, final Uri uri ) {
        if ( null == uri ) return null;
        final String scheme = uri.getScheme();
        String data = null;
        if ( scheme == null )
            data = uri.getPath();
        else if ( ContentResolver.SCHEME_FILE.equals( scheme ) ) {
            data = uri.getPath();
        } else if ( ContentResolver.SCHEME_CONTENT.equals( scheme ) ) {
            Cursor cursor = context.getContentResolver().query( uri, new String[] { ImageColumns.DATA }, null, null, null );
            if ( null != cursor ) {
                if ( cursor.moveToFirst() ) {
                    int index = cursor.getColumnIndex( ImageColumns.DATA );
                    if ( index > -1 ) {
                        data = cursor.getString( index );
                    }
                }
                cursor.close();
            }
        }
        return data;
    }

}