package mani.droid.androidremote;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

public class SlideActivity extends RemoteActivity {

	EditText sno;
	AlertDialog snoDialog;
	Builder snoBuider;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.slide_lay);
	}
	
	public void onSlideClick(View v)
	{
		switch(v.getId())
		{
			case R.id.slideGoto:
				final EditText sno = new EditText(this);
				sno.setSingleLine();
				sno.setInputType(InputType.TYPE_CLASS_PHONE);
				sno.setHint("Slide no");
				
				AlertDialog snoDialog = new AlertDialog.Builder(this)
						.setTitle("Enter Slide No:")
						.setView(sno)
						.setPositiveButton("Go", new OnClickListener(){
							@Override
							public void onClick(DialogInterface arg0, int arg1) {
								// TODO Auto-generated method stub
								String temp = sno.getText().toString();
								Log.v("String", temp);
								try{
									Integer.parseInt(temp);
									char[] charpno = temp.toCharArray();
									for(char tchar : charpno)
									{
										Commons.sendCommand("KB%" + String.valueOf(tchar) + "$");
									}
									Commons.sendCommand("KB%ENTER");
								}
								catch(Exception io)
								{
									Commons.showToast("Invalid slide no", true);
								}
								
							}
						}).create();
				snoDialog.show();
				
				break;
			
			case R.id.slideFull:
				Commons.sendCommand("KB%F11");
				break;

			case R.id.slideStart:
				Commons.sendCommand("KB%F5");
				break;

			case R.id.slideStop:
				Commons.sendCommand("KB%ESC");
				break;

			case R.id.slidePrev:
				Commons.sendCommand("KB%Up");
				break;

			case R.id.slideNext:
				Commons.sendCommand("KB%Down");
				break;


		}
	}
}
