package mani.droid.androidremote;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ViewFlipper;

public class KeyboardActivity extends RemoteActivity {

	Intent navi;
	ViewFlipper vf;
	int curView;
	
	EditText alpha;
	InputMethodManager im;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.keyboard_lay);
		
		vf = (ViewFlipper) findViewById(R.id.vfKeyboard);
		alpha = (EditText) this.findViewById(R.id.txtAlpha);
		alpha.addTextChangedListener(AlphaTextWatcher);
		
		im = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
	}
	
	@Override
	public void onMouse(View v)
	{
		super.onMouse(v);
		finish();
	}
	
	public void onPrev(View v)
	{
		vf.showPrevious();
		if(vf.getDisplayedChild() == 0)
		{
			alpha.requestFocus();
			im.showSoftInput(vf.getChildAt(0), 0);
		}
	}
	
	public void onNext(View v)
	{
		vf.showNext();
		im.hideSoftInputFromInputMethod(alpha.getWindowToken(), 0);
	}
	
	// alphabetic keyboard functions
	TextWatcher AlphaTextWatcher = new TextWatcher(){

		public void afterTextChanged(Editable s) {
			if(s.toString().length() != 0 )
				alpha.setText("");
			}

		public void beforeTextChanged(CharSequence s, int start, int count,
				int after) {}

		public void onTextChanged(CharSequence s, int start, int before,
				int count) {
			// TODO Auto-generated method stub
			if(s.toString().length() != 0 )
				Commons.sendCommand("KB%" + s.toString() + "$");
		}
	};
	
	// numeric keyboard
	public void onNumClick(View v)
	{
		Button btnNum = (Button) v;
		Commons.sendCommand("KB%" + btnNum.getText().toString());
	}
	
	public void onNaviClick(View v)
	{
		Button btnNavi;
		try {
			 btnNavi = (Button) v;
			 Commons.sendCommand("KB%" + btnNavi.getText().toString());
		}
		catch(Exception ex)
		{
			String val = "";
			switch(v.getId())
			{
				case R.id.imgNaviDown:
					val = "Down";
					break;
					
				case R.id.imgNaviUp:
					val = "Up";
					break;
					
				case R.id.imgNaviLeft:
					val = "Left";
					break;
					
				case R.id.imgNaviRight:
					val = "Right";
					break;
			}
			Commons.sendCommand("KB%" + val);
		}
		
	}
	
	public void onFunClick(View v)
	{
		Button btnFun = (Button) v;
		Commons.sendCommand("KB%" + btnFun.getText().toString());
	}
}
