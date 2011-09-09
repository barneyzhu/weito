package itcast.weibo;

import itcast.ui.R;

import java.util.List;

import weibo4j.Status;
import weibo4j.User;
import weibo4j.Weibo;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class TestApp extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        TextView tv=(TextView)this.findViewById(R.id.TextView01);
        setContentView(tv);
        //Base认证
        Weibo weibo=new Weibo();
        weibo.setUserId("sdhjob@hotmail.com");
        weibo.setPassword("123456");
        try{
        	Thread.sleep(1000);
        User u=weibo.verifyCredentials();
        tv.setText(u.getName()+","+u.getId());
        List<Status> alls= weibo.getHomeTimeline();
        for(Status statu:alls)
        {
        	Log.d("status:", statu.getText());	
        }
        }catch(Exception e){
        	Log.d("login err", e.getMessage());
        }
    }
}