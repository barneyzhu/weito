package itcast.util;

import java.net.HttpURLConnection;
import java.net.URL;

import android.graphics.drawable.BitmapDrawable;
import android.util.Log;

public class GetImage {
   //从一个URl 获取图片
	public static BitmapDrawable getImageFromURL(URL url)
	{  BitmapDrawable bd=null;
		try{
		
	    HttpURLConnection hc=(HttpURLConnection)url.openConnection();
		Log.d("pic", "----------------"+hc.getResponseCode());
	    if(hc.getResponseCode()==200)
		{  //使用输入流构建BitmapDrawable
			bd=new BitmapDrawable(hc.getInputStream());
		}
	   }catch(Exception e){
		   return null;
	   }
	   return bd;
	}
}
