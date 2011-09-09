package itcast.logic;

import java.util.ArrayList;
import java.util.List;

import itcast.ui.HomeActivity;
import itcast.ui.MainActivity;
import itcast.ui.NewWeiboActivity;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
import android.graphics.drawable.BitmapDrawable;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.*;
import android.view.*;
import weibo4j.Paging;
import weibo4j.Status;
import weibo4j.User;
import weibo4j.Weibo;
import weibo4j.WeiboException;
import weibo4j.http.AccessToken;
import itcast.ui.R;
import itcast.util.GetImage;

import java.util.*;
public class MainService extends Service implements Runnable{

	public static Weibo weibo=new Weibo();
	public static User nowuser;
	public static ArrayList<Activity> allActivity=new ArrayList<Activity>();//保存所有Activity
	public static int lastActivityId;//保存前端Activity编号
	//保存用户头像
	public static HashMap<Integer,BitmapDrawable> alluserIcon=new HashMap<Integer,BitmapDrawable>();
	
	
	//从集合中通过name获取Activity对象
	public static Activity getActivityByName(String name)
	{   Activity getac=null;
		for(Activity ac:allActivity)
		{
			if(ac.getClass().getName().indexOf(name)>=0)
			{
				getac=ac;
			}
		}
		return getac;
	}
	public static ArrayList<Task> allTask=new ArrayList<Task>();
	public static void newTask(Task task)
	{
		synchronized(allTask)
		{
			allTask.add(task);
		}
		
	}
	public static void promptExit(Context con)
	{
		//创建对话框
		LayoutInflater li=LayoutInflater.from(con);
		View exitV=li.inflate(R.layout.exitdialog, null);
		AlertDialog.Builder ab=new AlertDialog.Builder(con);
		ab.setView(exitV);//设定对话框显示的View对象
		ab.setPositiveButton(R.string.app_exit_ok, new 
		  OnClickListener()		
		{public void onClick(DialogInterface arg0, int arg1) {
				// TODO Auto-generated method stub
				 exitApp();
			}
		});
		ab.setNegativeButton(R.string.app_exit_cancle, null
		);
		//显示对话框
		ab.show();
	}
    public boolean isrun=true;
	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		Thread t=new Thread(this);
		t.start();//启动服务子线程完成所有费时操作
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		isrun=false;
	}

	@Override
	public void onStart(Intent intent, int startId) {
		// TODO Auto-generated method stub
		super.onStart(intent, startId);
		isrun=true;
		Log.d("service", "service is started");
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		System.setProperty("weibo4j.oauth.consumerKey", Weibo.CONSUMER_KEY);
    	System.setProperty("weibo4j.oauth.consumerSecret", Weibo.CONSUMER_SECRET);
    
		try{
		while(isrun)
		{   Task lastTask;
			
			synchronized(allTask)
			{//接任务
			 if(allTask.size()>0)
			 {lastTask=allTask.get(0);	
			 //执行任务
			  doTask(lastTask);
			 }
			}
			try{Thread.sleep(1000);}catch(Exception e){}
			
		}}catch(Exception e){
			isrun=false;
			Log.d("error", e.getMessage());
//			isrun=true;
//			Thread t=new Thread(this);
//			t.start();
		}
	}
	////执行任务 发消息更新Ui
	public void doTask(Task task) throws WeiboException
	{Message mess=hand.obtainMessage();//产生Ui更新消息对象
 	 mess.what=task.getTaskID();
	 switch(task.getTaskID())	
	 {case Task.TASK_GET_USER_INFO://获取用户信息
		 nowuser=weibo.showUser(((Integer)task.getTaskParam().get("uid")).toString());
		 break;
	  case Task.TASK_GET_USER_HOMETIMEINLINE://获取所有关注微博信息
		   //                             第一页 5条每页	 
		   List<Status> alls=weibo.getHomeTimeline(new Paging(1,5));
		   mess.obj=alls;
		  break;
	  case Task.TASK_GET_USER_IMAGE_ICON://获取用户头象
		 User u=(User)task.getTaskParam().get("user");
		 //判断用户的头像是否获取过
		 BitmapDrawable bd=alluserIcon.get(new Integer(u.getId()));
		 if(bd==null)
		 { //从网络获取
			 bd=GetImage.getImageFromURL(u.getProfileImageURL());
		     Log.d("put image", "uid"+u.getId());
			 alluserIcon.put(u.getId(), bd);
		 }else
		 {
			 mess.obj=bd; 
		 }
		 Log.d("获取图片","--------------------------------"+bd);
		 break;
	  case Task.TASK_GET_USER_HOMETIMEINLINE_MORE://获取更多微博首页信息
		  Paging pageinfo=(Paging)task.getTaskParam().get("pageinfo");
		  List<Status> allsm=weibo.getHomeTimeline(pageinfo);
		   mess.obj=allsm;
		   break;
	  case Task.TASK_NEW_WEIBO://发布新微博
		  String weibotxt=(String)task.getTaskParam().get("weibotext");
	      weibo.updateStatus(weibotxt);
	 }
	   hand.sendMessage(mess);//发送UI刷新的消息
	   allTask.remove(0);//移除除任务
	}
	//定义Handle对象负责完成Ui的更新
	public  Handler hand=new Handler()
	{ //接收消息 刷新数据到UI
     	@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			switch(msg.what)
			{case Task.TASK_GET_USER_HOMETIMEINLINE://更新首页
			 Activity activity=MainService.getActivityByName("HomeActivity");
			 IWeiboActivity ia=(HomeActivity)activity;
			 //更新微博请求
			 Integer type=((HomeActivity)activity).UI_REFRESH_STATUS;
			 List<Status> alls=(List<Status>)msg.obj;
			 ia.refresh(type,alls);
			 break;
			case Task.TASK_GET_USER_INFO://获取用户资料
			 Activity activity02=MainService.getActivityByName("HomeActivity");
			 IWeiboActivity ia02=(HomeActivity)activity02;
			 Integer type02=((HomeActivity)activity02).UI_REFRESH_TITLE;
			 ia02.refresh(type02,nowuser.getScreenName());
			 break;
			case Task.TASK_GET_USER_IMAGE_ICON://获取用户头像
				 Activity activity03=MainService.getActivityByName("HomeActivity");
				 IWeiboActivity ia03=(HomeActivity)activity03;
				 Integer type03=((HomeActivity)activity03).UI_REFRESH_STATUS_USER_ICON;
				 ia03.refresh(type03,msg.obj);
					break;
			case Task.TASK_GET_USER_HOMETIMEINLINE_MORE://获取到了更多微博主页信息
				 Activity activity04=MainService.getActivityByName("HomeActivity");
				 IWeiboActivity ia04=(HomeActivity)activity04;
				 Integer type04=((HomeActivity)activity04).UI_REFRESH_STATUS_MORE;
				 ia04.refresh(type04,msg.obj);
				 break;
			case Task.TASK_NEW_WEIBO://发表新微博成功
				Activity activity05=MainService.getActivityByName("NewWeiboActivity");
				 IWeiboActivity ia05=(NewWeiboActivity)activity05;
				 Integer type05=((NewWeiboActivity)activity05).UI_REFRESH_NEW_WEIBO_OK;
				 ia05.refresh(type05,msg.obj);
			}
		}
	};
	public static void exitApp()
	{
		((MainActivity)MainService.getActivityByName("MainActivity")).exitApp();
			
	}
	
}
