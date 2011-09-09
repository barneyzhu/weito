package itcast.ui;

import itcast.logic.IWeiboActivity;
import itcast.logic.MainService;
import itcast.ui.adapter.MSGStatuAdapter;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.ListView;
import java.util.*;

import weibo4j.Status;
public class MSGActivity extends Activity implements IWeiboActivity{
    
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
		MainService.allActivity.add(this);//添加Activity到Service
		
		Log.d("MSGActivity", "--------------------onCreate");
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.mes);
		ListView lv=(ListView)this.findViewById(R.id.listView);
		List as=new ArrayList();
		as.add(new Object());
		as.add(new Object());
		lv.setAdapter(new MSGStatuAdapter(this,as));
	}

	@Override
	public void init() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void refresh(Object ...param) {
		// TODO Auto-generated method stub
		
	}
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		Log.d("MSGActivity", "--------------------onDestroy");
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		Log.d("MSGActivity", "--------------------onPause");
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		Log.d("MSGActivity", "--------------------onResume");
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		Log.d("MSGActivity", "--------------------onStop");
	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		Log.d("MSGActivity", "--------------------onStart");
	}
}
