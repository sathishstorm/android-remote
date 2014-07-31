package mani.droid.androidremote;

import java.io.BufferedWriter;
import java.net.Socket;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.ImageView;
import android.widget.TextView;

public class MouseActivity extends RemoteActivity {

	Intent navi;
	TextView mview;
	ImageView sview;
	Socket server;
	BufferedWriter out;
	float lastXMV;
	float lastYMV;
	float lastYSC;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mouse_lay);
		
		// mouse move
		mview = (TextView) findViewById(R.id.txtMouse);
		mview.setOnTouchListener(new OnTouchListener(){

			public boolean onTouch(View view, MotionEvent event) {
				// TODO Auto-generated method stub
				switch(event.getAction())
				{
					case MotionEvent.ACTION_DOWN:
						lastXMV = event.getRawX();
						lastYMV = event.getRawY();
						break;
						
					case MotionEvent.ACTION_MOVE:
						int x = (int) (lastXMV - event.getRawX());
						int y = (int) (lastYMV - event.getRawY());
						String tempcmd = "MS%MV%" + String.valueOf(x) + "," + String.valueOf(y) + "$";
						Commons.sendCommand(tempcmd);
						lastXMV = event.getRawX();
						lastYMV = event.getRawY();
						break;
						
					case MotionEvent.ACTION_UP:
						lastXMV = 0;
						lastYMV = 0;
				}
				return true;
			}
		});
		
		sview = (ImageView) findViewById(R.id.imgScroll);
		sview.setOnTouchListener(new OnTouchListener(){

			@Override
			public boolean onTouch(View view, MotionEvent event) {
				// TODO Auto-generated method stub
				switch(event.getAction())
				{
					case MotionEvent.ACTION_DOWN:
						lastYSC = event.getRawY();
						break;
						
					case MotionEvent.ACTION_MOVE:
						int y = (int) (lastYSC - event.getRawY());
						String tempcmd = "MS%SC%" + String.valueOf(y) + "$";
						Commons.sendCommand(tempcmd);
						lastYSC = event.getRawY();
						break;
						
					case MotionEvent.ACTION_UP:
						lastYSC = 0;
				}
				return true;
			}
		});
	}
	
	public void leftClick(View v)
	{
		Commons.sendCommand("MS%LC$");
	}
	
	public void rightClick(View v)
	{
		Commons.sendCommand("MS%RC$");
	}
	
	@Override
	public void onKeyboard(View v)
	{
		super.onKeyboard(v);
		finish();
	}
}
