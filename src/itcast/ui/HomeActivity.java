package itcast.ui;

import java.util.HashMap;
import java.util.List;

import weibo4j.Paging;
import weibo4j.Status;

import itcast.db.OAuthReadUtil;
import itcast.logic.IWeiboActivity;
import itcast.logic.MainService;
import itcast.logic.Task;
import itcast.ui.adapter.WeiboStatuAdapter;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class HomeActivity extends Activity implements IWeiboActivity{
    public ListView mAllStatusLV;
    public static final int UI_REFRESH_STATUS=1;//更新微博
    public static final int UI_REFRESH_STATUS_USER_ICON=2;//更新头像
    public static final int UI_REFRESH_TITLE=3;//更新标题
    public static final int UI_REFRESH_STATUS_MORE=4;//得到更多微博
	public int nowPage=1;//当前页
	public int pageSize=5;//每页条数
    public View mProcessBar;
    public Button  btnew;
    public TextView mTitletv;
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
		Log.d("HomeActivity", "--------------------onCreate");
		MainService.allActivity.add(this);//添加Activity到Service
		
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.home);
		mAllStatusLV=(ListView)this.findViewById(R.id.freelook_listview);
		mProcessBar=(View)this.findViewById(R.id.progress);
		View title=this.findViewById(R.id.freelook_title);
		 btnew=(Button)title.findViewById(R.id.title_bt_left);
		Button btrefresh=(Button)title.findViewById(R.id.title_bt_right);
	    btnew.setBackgroundResource(R.drawable.title_button_newbolg);
	    btrefresh.setBackgroundResource(R.drawable.title_button_refresh);
	    mTitletv=(TextView)title.findViewById(R.id.textView);
	    
		init();
	}

	@Override
	public void init() {
		// TODO Auto-generated method stub
		mAllStatusLV.setOnItemClickListener(new OnItemClickListener()
		{

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				Toast.makeText(HomeActivity.this, ""+arg2+","+arg3, 1000).show();
			  if(arg3==0)
			  {
				//获取用户首页信息
				    HashMap hm=new HashMap();
					hm.put("accessToken", OAuthReadUtil.readToken(HomeActivity.this));
					MainService.newTask(new Task(Task.TASK_GET_USER_HOMETIMEINLINE
							,hm));
			  }
			  else if(arg3==-1)
			  { //获取下一页
				 nowPage++;
				 HashMap hm=new HashMap();
				 hm.put("pageinfo", new Paging(nowPage,pageSize));
				 MainService.newTask(new Task(Task.TASK_GET_USER_HOMETIMEINLINE_MORE
							,hm));
			  }
			}
			
		
		});
		
		///发表微博按钮
		btnew.setOnClickListener(new OnClickListener()
		{
		   @Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
			   //启动发表微博窗口
				Intent t=new Intent(HomeActivity.this,NewWeiboActivity.class);
				HomeActivity.this.startActivityForResult(t, 0);
			}
		}
		);
	}

	@Override
	public void refresh(Object ...param) {
		// TODO Auto-generated method stub
		switch(((Integer)param[0]).intValue())
		{
		case UI_REFRESH_STATUS:// 更新微博
			mProcessBar.setVisibility(View.GONE);
			List<Status> alls=(List<Status>)param[1];
			
			WeiboStatuAdapter wsa=new WeiboStatuAdapter(this,alls);
			mAllStatusLV.setAdapter(wsa);
			wsa.notifyDataSetChanged();//刷新完成列表
			//发送任务获取所有微博发送者的头像
			for(Status status:alls)
			{
				HashMap hm=new HashMap();
				hm.put("user", status.getUser());
				//获取用户资料
				MainService.newTask(
						new Task(Task.TASK_GET_USER_IMAGE_ICON
						,hm));
			}
			break;
		case UI_REFRESH_TITLE://更新标题
			mTitletv.setText((String)param[1]);
			break;
		case UI_REFRESH_STATUS_USER_ICON://更新用户图标
			((WeiboStatuAdapter)mAllStatusLV.getAdapter())
			.notifyDataSetChanged();//刷新listView
			break;
		case UI_REFRESH_STATUS_MORE://获取到更多微博信息
			((WeiboStatuAdapter)mAllStatusLV.getAdapter())
			.addMoreData((List<Status>)param[1]);
			
		}
	}
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		Log.d("HomeActivity", "--------------------onDestroy");
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		Log.d("HomeActivity", "--------------------onPause");
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		Log.d("HomeActivity", "--------------------onResume");
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		Log.d("HomeActivity", "--------------------onStop");
	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		
		HashMap hm=new HashMap();
		hm.put("uid", OAuthReadUtil.readToken(this).getUserId());
		//获取用户资料
		MainService.newTask(
				new Task(Task.TASK_GET_USER_INFO
				,hm));
		//获取用户首页信息
		hm.put("accessToken", OAuthReadUtil.readToken(this));
		MainService.newTask(new Task(Task.TASK_GET_USER_HOMETIMEINLINE
				,hm));
		
		Log.d("HomeActivity", "--------------------onStart");
	}
}