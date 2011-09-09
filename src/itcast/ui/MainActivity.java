package itcast.ui;

import itcast.logic.IWeiboActivity;
import itcast.logic.MainService;
import android.app.Activity;
import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TabHost;
import android.widget.Toast;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TabHost.TabSpec;

public class MainActivity extends TabActivity implements IWeiboActivity{
    public RadioGroup mMainrg;
    public RadioButton mRB01;//home
    public RadioButton mRB02;//信息
    public RadioButton mRB03;//个人资料
    public RadioButton mRB04;//搜索
    public RadioButton mRB05;//更多
    public View msgTitle;
    public static final String TS_HOME="home";
    public static final String TS_MESSAGE="msg";
    public static final String TS_MyCONTENT="mc";
    public TabHost th;
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if(keyCode==KeyEvent.KEYCODE_BACK)
		{
		  MainService.promptExit(this);	
		}
		return true;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		Log.d("MainActivity", "--------------------onCreate");
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.maintabs);
		
		mMainrg=(RadioGroup)this.findViewById(R.id.main_radio);
		mRB01=(RadioButton)this.findViewById(R.id.radio_button0);
		mRB01.setId(0);
		mRB02=(RadioButton)this.findViewById(R.id.radio_button1);
		mRB02.setId(1);
		mRB03=(RadioButton)this.findViewById(R.id.radio_button2);
		mRB03.setId(2);
		mRB04=(RadioButton)this.findViewById(R.id.radio_button3);
		mRB04.setId(3);
		mRB05=(RadioButton)this.findViewById(R.id.radio_button4);
		mRB05.setId(4);
		msgTitle=this.findViewById(R.id.msg_title);
		//添加子页
		 th=this.getTabHost();
		TabSpec tsHome=th.newTabSpec(TS_HOME);
		tsHome.setIndicator(TS_HOME);
		tsHome.setContent(new Intent(this,HomeActivity.class));
		th.addTab(tsHome);//添加用户微博首页
		//--------------
		TabSpec tsMSG=th.newTabSpec(TS_MESSAGE);
		tsMSG.setIndicator(TS_MESSAGE);
		tsMSG.setContent(new Intent(this,MSGActivity.class));
		th.addTab(tsMSG);//添加用户信息窗口
		//---------------
		TabSpec tsMyContent=th.newTabSpec(TS_MyCONTENT);
		tsMyContent.setIndicator(TS_MyCONTENT);
		tsMyContent.setContent(new Intent(this,MyContentActivity.class));
		th.addTab(tsMyContent);//添加用户信息窗口
	    init();
	    MainService.allActivity.add(this);//添加MainActivity到Service
		Intent it=new Intent(this,MainService.class);
		this.startService(it); //启动weibo服务组件
		
	}

	@Override
	public void init() {
		// TODO Auto-generated method stub
		
		mMainrg.setOnCheckedChangeListener(new OnCheckedChangeListener()
		{
			public void onCheckedChanged(RadioGroup group, int checkedId) {
             switch(checkedId)  
             {case 0://首页
            	 msgTitle.setVisibility(View.GONE);
            	  th.setCurrentTab(0);
            	 break;
              case 1://信息
            	  msgTitle.setVisibility(View.VISIBLE);
            	  th.setCurrentTab(1);
             	 break;
              case 2://个人资料
            	  msgTitle.setVisibility(View.GONE);
            	  th.setCurrentTab(2);
             	 break;
              case 3://搜索
              case 4://更多
             }
			}
		}
		);
	}

	@Override
	public void refresh(Object ...param) {
		// TODO Auto-generated method stub
		
	}
	public void exitApp()
	{
		this.finish();
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		MainService.allActivity.clear();
		Intent it=new Intent(this,MainService.class);
		this.stopService(it); //停止weibo服务组件
		Log.d("MainActivity", "--------------------onDestroy");
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		Log.d("MainActivity", "--------------------onPause");
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		Log.d("MainActivity", "--------------------onResume");
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		Log.d("MainActivity", "--------------------onStop");
	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		Log.d("MainActivity", "--------------------onStart");
	}
	

}
