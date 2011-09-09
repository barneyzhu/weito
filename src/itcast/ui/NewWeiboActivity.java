package itcast.ui;

import java.util.HashMap;

import itcast.logic.IWeiboActivity;
import itcast.logic.MainService;
import itcast.logic.Task;
import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class NewWeiboActivity extends Activity implements IWeiboActivity{
    public EditText mBlogET;
    public Button mSendWeibobt;
    public Button mBackbt;
    public ProgressDialog loginDialog ;
    public static final int UI_REFRESH_NEW_WEIBO_OK=1;//发表成功
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.newblog);
		mBlogET=(EditText)this.findViewById(R.id.etBlog);
		View view=this.findViewById(R.id.title);
		mBackbt=(Button)view.findViewById(R.id.title_bt_left);
		mBackbt.setText("返回");
		mSendWeibobt=(Button)view.findViewById(R.id.title_bt_right);
		mSendWeibobt.setText("发表");
		init();
	    MainService.allActivity.add(this);
	}
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		 MainService.allActivity.remove(this);
	}

	@Override
	public void init() {
		// TODO Auto-generated method stub
		//返回主窗口
		mBackbt.setOnClickListener(new OnClickListener()
		{
  	       @Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
			finish();	
			}
		});
		//发表微博
		mSendWeibobt.setOnClickListener(new OnClickListener()
		{
  	       @Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
			 newWeibo();
			}
		});
	}
	@Override
	public void refresh(Object... param) {
		// TODO Auto-generated method stub
		switch((Integer)param[0])
		{case UI_REFRESH_NEW_WEIBO_OK:
			
		loginDialog.dismiss();
		Toast.makeText(this, "发表成功", 1000).show();
		this.finish();
		  break;
		}
	}
	//发表微博
    public void newWeibo()	
    {  //得到编辑的微博信息
    	String blogstr=mBlogET.getText().toString();
       //发送发表微博任务
    	HashMap hm=new HashMap();
		hm.put("weibotext", blogstr);
		//获取用户资料
		MainService.newTask(
				new Task(Task.TASK_NEW_WEIBO
				,hm));
		//弹出进度条
		loginDialog= new ProgressDialog(this);  
		loginDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);  
		String msg="正在发表请稍后";
		loginDialog.setMessage(msg);
		loginDialog.show(); 
		
    }
}
