package itcast.ui;

import weibo4j.User;
import weibo4j.Weibo;
import weibo4j.http.AccessToken;
import weibo4j.http.RequestToken;
import itcast.db.OAuthReadUtil;
import itcast.logic.IWeiboActivity;
import itcast.logic.MainService;
import itcast.util.NetCheck;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends Activity implements IWeiboActivity{
	public EditText mUserET;
	public EditText mPassET;
	public Button mLoginBT;
	
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	    this.setContentView(R.layout.login);
	    View title=this.findViewById(R.id.title);
//	    ((FrameLayout)title).getChildAt(0); TextView
//	    ((FrameLayout)title).getChildAt(1); Left Button
//	    ((FrameLayout)title).getChildAt(2); Right Button
	    TextView tv=(TextView)((FrameLayout)title).getChildAt(0);
	    tv.setText(R.string.app_name);
	    title.findViewById(R.id.title_bt_left).setVisibility(View.GONE);
	    title.findViewById(R.id.title_bt_right).setVisibility(View.GONE);
	    mUserET=(EditText)this.findViewById(R.id.user);
	    mPassET=(EditText)this.findViewById(R.id.password);
	    mLoginBT=(Button)this.findViewById(R.id.loginButton);
	    mLoginBT.setOnClickListener(new View.OnClickListener()
	    {
		    @Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
			  startLogin();
			}
	    }
	    );
	    init();	
	}
	//用户登录的验证 
	public void startLogin()
	{
		//提示用户登录的进度条
	final	ProgressDialog loginDialog = new ProgressDialog(LoginActivity.this);  
		//设定进度条样式
		loginDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);  
		String msg=this.getResources().getString(R.string.state_login);
		loginDialog.setMessage(msg);
		loginDialog.show(); 
		Thread t=new Thread()
		{
			public void run()
			{  
				String us=mUserET.getText().toString();
				String ps=mPassET.getText().toString();
//				MainService.weibo.setUserId(us);
//				MainService.weibo.setPassword(ps);
				try{
				//通过OAuth方式验证登录
				//1。根据app key第三方应用向新浪获取requestToken
					System.setProperty("weibo4j.oauth.consumerKey", Weibo.CONSUMER_KEY);
		        	System.setProperty("weibo4j.oauth.consumerSecret", Weibo.CONSUMER_SECRET);
		        	RequestToken requestToken = MainService.weibo.getOAuthRequestToken();
	            //2。用户从新浪获取verifier_code
	            String vcode=MainService.weibo.getAuthorizationUserVCode(
	            		requestToken.getToken(),us,ps);
	           
	            //3.使用verifier_code和requestToken获取Access_Token
	            AccessToken accessToken = requestToken.getAccessToken(vcode);
			    //保存accessToken到磁盘
	            OAuthReadUtil.writeToken(LoginActivity.this, accessToken);
	            MainService.weibo.setToken(accessToken.getToken(), 
	            		accessToken.getTokenSecret());
				//MainService.nowuser=MainService.weibo.verifyCredentials();
	            //MainService.nowuser=new User(accessToken.getScreenName(),accessToken.getUserId());
	        	
//	            Toast.makeText(LoginActivity.this,MainService.nowuser.getName()
//						+ "ok", 2000).show();
				loginDialog.dismiss();//进度条消失
				finish();//当前LoginActivity销毁
				//跳转到Main窗口
				Intent t=new Intent(LoginActivity.this,MainActivity.class);
				LoginActivity.this.startActivity(t);
				}catch(Exception e){
					Log.d("error", e.getMessage());
				}	
			}
		};
		t.start();
	}
	
	 @Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		init();
	}


	public void init()
	 {
		 //�������
		 boolean f=NetCheck.checkNet();
		 if(!f)
		 {
			 //apn_is_wrong1
			 AlertDialog.Builder ab=new  AlertDialog.Builder(this);
			 ab.setMessage(R.string.apn_is_wrong1);
			 ab.setIcon(R.drawable.aboutweibo);
			 ab.setNegativeButton(R.string.apn_is_wrong1_setnet, 
			    new OnClickListener()
			  {
  	            @Override
				public void onClick(DialogInterface arg0, int arg1) {
					// TODO Auto-generated method stub
  	            	Intent it=new 
                      Intent(android.provider.Settings.ACTION_WIRELESS_SETTINGS);
  	            	 LoginActivity.this.startActivityForResult(it,0); 
  	   			}
			 }
			 );
			 ab.setPositiveButton(R.string.apn_is_wrong1_exit, 
					    new OnClickListener()
					  {
		  	            @Override
						public void onClick(DialogInterface arg0, int arg1) {
							// TODO Auto-generated method stub
				          	arg0.dismiss();
				          	 LoginActivity.this.finish();
						}
					 }
					 );
			 ab.create().show();
		 }else
		 {    AccessToken at=OAuthReadUtil.readToken(this);
			 if(at!=null)
			 { 
				 finish();
				 //初始化用户授权的AccessToken
					System.setProperty("weibo4j.oauth.consumerKey", Weibo.CONSUMER_KEY);
		        	System.setProperty("weibo4j.oauth.consumerSecret", Weibo.CONSUMER_SECRET);
		        	 MainService.weibo.setToken(at.getToken(), at.getTokenSecret());
		        	 MainService.nowuser=new User(at.getScreenName(),at.getUserId());
		        	 
		        	//跳转到Main 窗口
					Intent t=new Intent(LoginActivity.this,MainActivity.class);
					LoginActivity.this.startActivity(t);
			 }
		 }
	 }
	@Override
	public void refresh(Object ...param) {
		// TODO Auto-generated method stub
		
	}
	
}
