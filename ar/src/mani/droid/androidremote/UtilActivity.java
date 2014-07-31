package mani.droid.androidremote;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class UtilActivity extends RemoteActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.utility);
	}
	
	public void onUtil(View v)
	{
		String cmd = "";
		String item = ((TextView) v).getText().toString();
		if(item.equalsIgnoreCase("Calculator"))
			cmd = "calc";
		else if(item.equalsIgnoreCase("Control Panel"))
			cmd = "control";
		else if(item.equalsIgnoreCase("Command Prompt"))
			cmd = "cmd";
		else if(item.equalsIgnoreCase("Utility Manager"))
			cmd = "utilman";
		else if(item.equalsIgnoreCase("Notepad"))
			cmd = "notepad";
		else if(item.equalsIgnoreCase("MS Paint"))
			cmd = "mspaint";
		else if(item.equalsIgnoreCase("Registry Editor"))
			cmd = "regedit";
		else if(item.equalsIgnoreCase("Remote Desktop"))
			cmd = "mstsc";
		else if(item.equalsIgnoreCase("Window Services"))
			cmd = "services.msc";
		else if(item.equalsIgnoreCase("Task Manager"))
			cmd = "taskmgr";
		Commons.sendCommand("UL%" + cmd);
	}
}
