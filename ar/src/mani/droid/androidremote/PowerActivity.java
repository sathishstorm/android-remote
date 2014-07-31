package mani.droid.androidremote;

import android.os.Bundle;
import android.view.View;

public class PowerActivity extends RemoteActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.power_lay);
	}
	
	public void onPowerClick(View v)
	{
		String pwcmd = "PW%";
		switch(v.getId())
		{
			case R.id.pwLock:
				pwcmd += "Lock";
				break;
				
			case R.id.pwLogoff:
				pwcmd += "Logoff";
				break;
			
			case R.id.pwSleep:
				pwcmd += "Sleep";
				break;
			
			case R.id.pwHib:
				pwcmd += "Hib";
				break;
			
			case R.id.pwSwitch:
				pwcmd += "Switch";
				break;
			
			case R.id.pwRestart:
				pwcmd += "Restart";
				break;
				
			case R.id.pwShutdown:
				pwcmd += "Shutdown";
				break;	
		}
		Commons.sendCommand(pwcmd);
	}
}
