package mani.droid.androidremote;

import android.app.Activity;
import android.content.Intent;
import android.view.View;

public class RemoteActivity extends Activity {

	Intent navigate;
	
	public void onMouse(View v)
	{
		navigate = new Intent(this, MouseActivity.class);
		startActivity(navigate);
	}
	
	public void onKeyboard(View v)
	{
		navigate = new Intent(this, KeyboardActivity.class);
		startActivity(navigate);
	}
	
	public void onHome(View v)
	{
		this.finish();
	}
	
}
