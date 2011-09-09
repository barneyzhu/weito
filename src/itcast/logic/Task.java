package itcast.logic;

import java.util.Map;

public class Task {
   public static final int TASK_GET_USER_INFO=0;//获取用户详细信息	
   public static final int TASK_GET_USER_HOMETIMEINLINE=1;//获取用户首页博客	
   public static final int TASK_GET_USER_IMAGE_ICON=2;//获取用户头象图片	
   public static final int TASK_GET_USER_FRIEND=3;//获取用户所有好友	
   public static final int TASK_GET_USER_HOMETIMEINLINE_MORE=4;//获取用户首页博客下一页	
   public static final int TASK_NEW_WEIBO=5;//发表新微博	
   
   //...	
  private int taskID;//任务编号
   private Map taskParam;//任务参数
   public Task(int id,Map param)
   {
	   this.taskID=id;
	   this.taskParam=param;
   }
public int getTaskID() {
	return taskID;
}
public void setTaskID(int taskID) {
	this.taskID = taskID;
}
public Map getTaskParam() {
	return taskParam;
}
public void setTaskParam(Map taskParam) {
	this.taskParam = taskParam;
}
}
