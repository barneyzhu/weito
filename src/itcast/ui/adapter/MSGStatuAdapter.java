package itcast.ui.adapter;

import java.util.List;

import weibo4j.Status;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import itcast.logic.MainService;
import itcast.ui.*;
public class MSGStatuAdapter extends BaseAdapter{
   public Context con;
   public List<Status> alls;
   public LayoutInflater lif;
   public MSGStatuAdapter(Context con,List<Status>  as)
   {this.con=con;
    lif=LayoutInflater.from(con);
	this.alls=as;   
   }
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return alls.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int index, View view, ViewGroup arg2) {
		// TODO Auto-generated method stub
		Log.d("getView", ""+index);
		View statusItem=null;
		if(view==null)
		{  //创建1条目
			statusItem=this.lif.inflate(R.layout.progress, null);
		}else
		{
			statusItem=view;
		}
		statusItem.findViewById(R.id.progressBar).setVisibility(View.GONE);
		
		return statusItem;
	}

}
