package itcast.ui;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.view.WindowManager;
import android.view.animation.*;
import android.view.animation.Animation.AnimationListener;
import android.widget.ImageView;
public class LogoActivity extends Activity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.log);
		this.getWindow().setFlags(
				WindowManager.LayoutParams.FLAG_FULLSCREEN
				, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
		ImageView iv=(ImageView)this.findViewById(R.id.logo_bg);
		AlphaAnimation am=new AlphaAnimation(0.1f,1.0f);
		am.setDuration(3000);
		iv.startAnimation(am);
		am.setAnimationListener(new AnimationListener()
		{

			@Override
			public void onAnimationEnd(Animation arg0) {
				// TODO Auto-generated method stub
				finish();
				Intent it=new Intent(LogoActivity.this,LoginActivity.class);
				LogoActivity.this.startActivity(it);
			}

			@Override
			public void onAnimationRepeat(Animation arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onAnimationStart(Animation arg0) {
				// TODO Auto-generated method stub
				
			}
			
		}
		);
	}

	
}
