package mani.droid.androidremote;

import java.io.IOException;
import java.net.Socket;
import java.util.regex.Pattern;

import android.net.ConnectivityManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.text.InputType;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;

public class HomeActivity extends Activity {
	
	Intent navi;
	AlertDialog ipDialog;
	AlertDialog.Builder ipBuilder;
	EditText ip;
	
	SharedPreferences sp;
	Editor spedi;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);
		
		Commons.context = this;
		if(!isNetworkConnected())
		{
			Commons.showToast("No network Access, please check your connection then try again", true);
			finish();
		}
		
		sp = PreferenceManager.getDefaultSharedPreferences(this);
		Commons.sp = PreferenceManager.getDefaultSharedPreferences(this);
		spedi = sp.edit();
		
		// preparing dialog for getting server ip
		ip = new EditText(this);
		ip.setSingleLine();
		ip.setHint("eg: 192.168.1.4");
		ip.setInputType(InputType.TYPE_CLASS_PHONE);
		ipBuilder = new AlertDialog.Builder(this);
		ipBuilder.setCancelable(false);
		ipBuilder.setTitle("Ip Address");
		ipBuilder.setMessage("Enter IP address of the Server PC");
		ipBuilder.setView(ip);
		ipBuilder.setPositiveButton("Confirm", new OnClickListener(){

			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				String ipadd = ip.getText().toString();
				if(Pattern.matches(Patterns.IP_ADDRESS.toString(), ipadd))
				{
					try {
						Commons.server = new Socket(ipadd, 8001);
						spedi.putString("ip", ipadd);
						spedi.commit();
					}
					catch(IOException io)
					{
						io.printStackTrace();
						Commons.showToast("Invalid Server IP", true);
						finish();
					}
				}
				else
					Commons.showToast("Invalid IP Address, application may misbehave", true);
			}
		});
		ipDialog = ipBuilder.create();
		ipDialog.show();
	}
	
	public void onNavi(View v)
	{
		switch(v.getId())
		{
			case R.id.imgInput:
				// when input icon clicked
				navi = new Intent(this, MouseActivity.class);
				break;
				
			case R.id.imgFile:
				// when file icon clicked
				navi = new Intent(this, FileActivity.class);
				break;
				
			case R.id.imgPower:
				// when power icon clicked
				navi = new Intent(this, PowerActivity.class);
				break;
				
			case R.id.imgSlide:
				// when slide icon clicked
				navi = new Intent(this, SlideActivity.class);
				break;
				
			case R.id.imgUtil:
				// when utility icon clicked
				navi = new Intent(this, UtilActivity.class);
				break;
				
			case R.id.imgExit:
				// when exit icon clicked
				onExit();
				return;
		}
		startActivity(navi);
	}
	
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		onExit();
	}

	public void onExit()
	{
		AlertDialog.Builder builder = new AlertDialog.Builder(this);    	
    	builder.setTitle("Exit")
    		.setMessage("Are you sure do you want to Exit")
    		.setPositiveButton("Yes", newdialog)
    		.setNegativeButton("No", null);
    	AlertDialog dialog = builder.create();
    	dialog.show();
	}
	DialogInterface.OnClickListener newdialog = new DialogInterface.OnClickListener() {

		public void onClick(DialogInterface dialog, int which) {
			// TODO Auto-generated method stub
			Commons.closeConnection();
			finish();
		}		
	};
	
	public boolean isNetworkConnected() 
	{
	    ConnectivityManager connec = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
	    android.net.NetworkInfo wifi = connec.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
	    android.net.NetworkInfo mobile = connec.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

	    if (wifi.isConnected()) {
	        return true;
	    } else if (mobile.isConnected()) {
	        return true;
	    }
	    return false;
	}
}