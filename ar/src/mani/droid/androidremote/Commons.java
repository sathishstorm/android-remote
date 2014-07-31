package mani.droid.androidremote;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

public class Commons {

	public static Socket server;
	public static Context context;
	public static Toast toast;
	public static BufferedInputStream in;
	public static BufferedWriter out;
	public static SharedPreferences sp;
	
	public static void showToast(String msg, boolean isShort)
	{
		int dur = 0;
		if(isShort)
			dur = Toast.LENGTH_SHORT;
		else
			dur = Toast.LENGTH_LONG;
		if(toast != null)
			toast.cancel();
		toast = Toast.makeText(context, msg, dur);
		toast.show();
	}
	
	public static String sendCommand(String cmd)
	{
		String result = "Result";
		try
		{
			//byte[] recbyte = new byte[4096];
			out = new BufferedWriter(new OutputStreamWriter(server.getOutputStream()));
			out.write(cmd);
			out.flush();
			if(cmd.startsWith("FL"))
			{
				BufferedReader br = new BufferedReader(new InputStreamReader(server.getInputStream()));
				result = br.readLine();				
			}
		}
		catch(IOException e) {
			e.printStackTrace();
			try {
				Commons.server = new Socket(sp.getString("ip", "192.168.1.6"), 8001);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
				Commons.showToast("Server Stopped", true);
			}
		}
		return result;
	}
	
	public static void closeConnection()
	{
		try {
			server.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
