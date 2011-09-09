package itcast.db;

import android.content.Context;
import android.content.SharedPreferences;
import weibo4j.http.AccessToken;

public class OAuthReadUtil {

	//写入当前用户的OAuth_Token
	public static void writeToken(Context con,AccessToken at)
	{
		SharedPreferences sp=con.getSharedPreferences("ustoken",Context.MODE_PRIVATE);
		sp.edit().putString("token", at.getToken())
		.putString("secret", at.getTokenSecret())
		.putInt("uid", at.getUserId())
		.putString("screenName", at.getScreenName())
		.commit();
	}
	//读取当前拥护的OAuth_Token
	public static AccessToken readToken(Context con)
	{   AccessToken at=null;
		SharedPreferences sp=con.getSharedPreferences("ustoken",Context.MODE_PRIVATE);
		String token=sp.getString("token",null);
		String secret=sp.getString("secret", null);
		String screen=sp.getString("screenName", null);
		int uid=sp.getInt("uid", 0);
		if(token!=null)
		{
			at=new AccessToken(token,secret);
			at.setScreenName(screen);
			at.setUserId(uid);
		}
		return at;
	}
}
