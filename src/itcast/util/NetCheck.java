package itcast.util;

import java.net.HttpURLConnection;
import java.net.URL;

public class NetCheck {

	public static boolean checkNet()
	{
		boolean res=true;
		try{
		URL url=new URL("http://www.sina.com");
		HttpURLConnection hc=(HttpURLConnection)url.openConnection();
		if(hc.getResponseCode()!=200){
			res=false;
		}
		}catch(Exception e){
			res=false;
		}
		return res;
	}
}
