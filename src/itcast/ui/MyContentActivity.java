package itcast.ui;

import itcast.logic.IWeiboActivity;
import itcast.logic.MainService;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;

public class MyContentActivity extends Activity implements IWeiboActivity{
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
		super.onCreate(savedInstanceState);
		MainService.allActivity.add(this);//添加Activity到Service
		
		this.setContentView(R.layout.userinfo);
		Log.d("MyContentActivity", "--------------------onCreate");
		
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
		Log.d("MyContentActivity", "--------------------onDestroy");
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		Log.d("MyContentActivity", "--------------------onPause");
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		Log.d("MyContentActivity", "--------------------onResume");
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		Log.d("MyContentActivity", "--------------------onStop");
	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		Log.d("MyContentActivity", "--------------------onStart");
	}
}
