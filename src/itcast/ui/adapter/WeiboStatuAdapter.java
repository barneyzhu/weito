package itcast.ui.adapter;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import weibo4j.Status;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import itcast.logic.MainService;
import itcast.ui.*;
public class WeiboStatuAdapter extends BaseAdapter{
   public Context con;
   public List<Status> alls;
   public LayoutInflater lif;
   public WeiboStatuAdapter(Context con,List<Status>  as)
   {this.con=con;
    lif=LayoutInflater.from(con);
	this.alls=as;   
   }
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return alls.size()+2;
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int index) {
		// TODO Auto-generated method stub
		if(index==0)//选中第一项
		{
		  return 0;	
		}else if(index>0&&(index<this.getCount()-1))
		{
		  return alls.get(index-1).getId();//如果用户选中了中间项 	
		}else{
		  return -1;//表示用户选中最后一项
		}
	}
	//增加更多的数据
	public void addMoreData(List<Status> moreStatus)
	{
		this.alls.addAll(moreStatus);//把新数据增加到原有集合
		this.notifyDataSetChanged();
	}
	private class ViewHolder{
		ImageView ivItemPortrait;//头像 有默认值
		TextView tvItemName;//昵称
		ImageView ivItemV;//新浪认证 默认gone
		TextView tvItemDate;//时间
		ImageView ivItemPic;//时间图片 不用修改
		TextView tvItemContent;//内容
		ImageView contentPic;//自己增加的内容图片显示的imgView
		View subLayout;//回复默认gone
		TextView tvItemSubContent;//回复内容 subLayout显示才可以显示
		ImageView subContentPic;//自己增加的主要显示回复内容的图片。subLayout显示才可以显示
	}
	@Override
	public View getView(int indexID, View view, ViewGroup arg2) {
		// TODO Auto-generated method stub
		Log.d("getView", ""+indexID);
		if((indexID>0)&&(indexID<this.getCount()-1))
		{View statusItem=null;
		 ViewHolder holder = null;
		 indexID--;
		//if(view==null||(!(view instanceof LinearLayout)))
		{  //创建1条目
			statusItem=this.lif.inflate(R.layout.itemview, null);
			holder = new ViewHolder();
			holder.ivItemPortrait = (ImageView)statusItem.findViewById(R.id.ivItemPortrait);
			holder.tvItemName = (TextView)statusItem.findViewById(R.id.tvItemName);
			holder.ivItemV = (ImageView)statusItem.findViewById(R.id.ivItemV);
			holder.tvItemDate = (TextView)statusItem.findViewById(R.id.tvItemDate);
			holder.ivItemPic = (ImageView)statusItem.findViewById(R.id.ivItemPic);
			holder.tvItemContent = (TextView)statusItem.findViewById(R.id.tvItemContent);
			holder.contentPic = (ImageView)statusItem.findViewById(R.id.contentPic);
			holder.subLayout = statusItem.findViewById(R.id.subLayout);
			holder.tvItemSubContent = (TextView)holder.subLayout.findViewById(R.id.tvItemSubContent);
			holder.subContentPic = (ImageView)holder.subLayout.findViewById(R.id.subContentPic);
			holder.ivItemV =(ImageView)holder.subLayout.findViewById(R.id.ivItemV);
			statusItem.setTag(holder);
		}
//		}else
//		{
//			statusItem=view;
//			holder = (ViewHolder) statusItem.getTag();
//		}
		if(alls.get(indexID).getRetweeted_status()!=null){
			holder.subLayout.setVisibility(View.VISIBLE);
			holder.tvItemSubContent.setText(alls.get(indexID).getRetweeted_status().getText());
		}else{
			holder.subLayout.setVisibility(View.GONE);
		}
		
		if(alls.get(indexID).getUser().isVerified()){//显示会员标志
			holder.ivItemV.setVisibility(View.VISIBLE);
		}
		holder.tvItemName.setText(alls.get(indexID).getUser().getName());
		holder.tvItemDate.setText(alls.get(indexID).getUser().getCreatedAt().toString());
		holder.tvItemContent.setText(alls.get(indexID).getText());
		
		//设定用户头像
		//如果获取到了用户头像
		BitmapDrawable bg=(BitmapDrawable)MainService
		.alluserIcon.get(alls.get(indexID).getUser().getId());
		Log.d("getView", ""+alls.get(indexID).getUser().getId());
		if(bg!=null)
		{ //设定用户头像
			holder.ivItemPortrait.setImageDrawable(
				  bg);	
		}else
		{
			holder.ivItemPortrait.setImageResource(R.drawable.portrait);
		}
		return statusItem;
		}else
			if(indexID==0)
			{//实现刷新按钮
				View itemView=null;
//				if(view!=null&& (view instanceof FrameLayout))
//				{
//					itemView=view;
//				}else
//				{
				 itemView=this.lif.inflate(R.layout.listrefreshitem, null);
//				}
//			    //得到进度条 隐藏
				itemView.findViewById(R.id.progressBar).setVisibility(View.GONE);
				//得到文本对象
				TextView tv=(TextView)itemView.findViewById(R.id.progress_tv);
				tv.setText("刷新");
				return itemView;
			}
			else //if(index==this.getCount()-1)
		{
			View itemView=null;
//			if(view!=null&& (view instanceof FrameLayout))
//			{
//				itemView=view;
//			}else
//			{
				itemView=this.lif.inflate(R.layout.listrefreshitem, null);
//			}
//		    //得到进度条 隐藏
			itemView.findViewById(R.id.progressBar).setVisibility(View.GONE);
			//得到文本对象
			TextView tv=(TextView)itemView.findViewById(R.id.progress_tv);
			tv.setText("更多内容");
		    return itemView;
		}
		
	}
	

}
