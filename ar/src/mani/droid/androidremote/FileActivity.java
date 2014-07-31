package mani.droid.androidremote;

import java.util.ArrayList;
import java.util.Stack;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

public class FileActivity extends RemoteActivity {

	ProgressDialog pd;
	Context cntx;
	ArrayList<PcFileDetail> filesInfo = new ArrayList<PcFileDetail>();
	TextView fileName;
	
	ListView lv;
	FileListAdapter ada = new FileListAdapter();
	Stack<String> pathStack = new Stack<String>();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.file_lay);
		
		cntx = this;
		
		pathStack.push("Home");
		
		lv = (ListView) findViewById(R.id.lvFiles);
		lv.setAdapter(ada);
		
		// set on item click event handler
		lv.setOnItemClickListener(new OnItemClickListener(){
			@Override
			public void onItemClick(AdapterView<?> aView, View view, int pos,
					long id) {
				// TODO Auto-generated method stub
				PcFileDetail clicked = filesInfo.get(pos);
				if(clicked.isFile())
				{
					String res = Commons.sendCommand("FL%OP%" + clicked.getPath());
					if(res.equalsIgnoreCase("Success"))
						Commons.showToast("File will be opened in PC", true);
					else
						Commons.showToast("Failed to Open file", true);
				}
				else
				{
					pathStack.push(clicked.getPath());
					ListFilesTask listTask = new ListFilesTask();
					listTask.execute(new String[]{ "LS%" + pathStack.lastElement() });
				}
			}
		});
		
		// set on item long click event handler
		lv.setOnItemLongClickListener(new OnItemLongClickListener(){
			@Override
			public boolean onItemLongClick(AdapterView<?> aView, View view,
					int pos, long id) {
				// TODO Auto-generated method stub
				final PcFileDetail clicked = filesInfo.get(pos);
				CharSequence[] items = { "Open", "Delete" };
				AlertDialog opt = new AlertDialog.Builder(cntx)
						.setTitle("File Options")
						.setItems(items, new OnClickListener(){
							@Override
							public void onClick(DialogInterface dialInter, int pos) {
								// TODO Auto-generated method stub
								switch(pos)
								{
									case 0:
										// open a file
										String res = Commons.sendCommand("FL%OP%" + clicked.getPath());
										if(res.equalsIgnoreCase("Success"))
											Commons.showToast("File was opened in PC", true);
										else
											Commons.showToast("Failed to Open file", true);
										break;
										
									case 1:
										// delete
										String res1 = Commons.sendCommand("FL%DL%" + clicked.getPath());
										if(res1.equalsIgnoreCase("Success"))
											Commons.showToast("File/Folder will be deleted in PC", true);
										else
											Commons.showToast("Failed to delete file/folder", true);
										break;
								}
							}
						})
						.create();
				opt.show();
				return true;
			}
		});
		
		ListFilesTask listTask = new ListFilesTask();
		listTask.execute(new String[]{ "LS%" + pathStack.firstElement() });
	}
	
	public void onFileClick(View v)
	{
		final ListFilesTask listTask = new ListFilesTask();
		switch(v.getId())
		{
			case R.id.imgFileHome:
				listTask.execute(new String[]{ "LS%" + pathStack.firstElement() });
				pathStack.clear();
				pathStack.push("Home");
				break;
				
			case R.id.imgFileRefresh:
				listTask.execute(new String[]{ "LS%" + pathStack.lastElement() });
				break;
				
			case R.id.imgFileGoto:
				AlertDialog ipDialog;
				AlertDialog.Builder ipBuilder;
				final EditText ip;
				ip = new EditText(this);
				ip.setSingleLine();
				ip.setHint("C:\\Program Files\\");
				ipBuilder = new AlertDialog.Builder(this);
				ipBuilder.setCancelable(false);
				ipBuilder.setTitle("Enter Path:");
				ipBuilder.setView(ip);
				ipBuilder.setPositiveButton("Go", new OnClickListener(){

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						String tmpPath = ip.getText().toString();
						pathStack.push(tmpPath);
						listTask.execute(new String[]{ "LS%" + tmpPath });
					}
				});
				ipDialog = ipBuilder.create();
				ipDialog.show();
				break;
				
			case R.id.imgFileBack:
				if(!pathStack.lastElement().equals("Home"))
				{
					pathStack.pop();
					listTask.execute(new String[]{ "LS%" + pathStack.lastElement() });
				}
				break;	
		}
	}
	
	public class ListFilesTask extends AsyncTask<String, Void, String>
	{
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			pd = new ProgressDialog(cntx);
			pd.setCancelable(false);
			pd.setMessage("Listing Files");
			pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
			
			pd.show();
		}

		@Override
		protected String doInBackground(String... paths) {
			// TODO Auto-generated method stub
			return Commons.sendCommand("FL%" + paths[0]);
		}

		@Override
		protected void onPostExecute(String strJson) {
			// TODO Auto-generated method stub
			pd.dismiss();
			try
			{
				JSONObject rootObj = new JSONObject(strJson);
				JSONArray fileList = rootObj.getJSONArray("FileArray");
				filesInfo.clear();
				for(int i = 0; i < fileList.length(); i++)
				{
					JSONObject tmpObj = fileList.getJSONObject(i);
					PcFileDetail tmpDet = new PcFileDetail(
							tmpObj.getString("name"),
							tmpObj.getString("path"),
							tmpObj.getBoolean("isFile"));
					filesInfo.add(tmpDet);
				}
			}
			catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				Commons.showToast("Invalid path", true);
				pathStack.pop();
			}
			ada.notifyDataSetChanged();
		}
	}
	
	public class FileListAdapter extends BaseAdapter
	{

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return filesInfo.size();
		}

		@Override
		public Object getItem(int index) {
			// TODO Auto-generated method stub
			return filesInfo.get(index);
		}

		@Override
		public long getItemId(int arg0) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public View getView(int pos, View cView, ViewGroup vGrp) {
			// TODO Auto-generated method stub
			if(cView == null)
				cView = getLayoutInflater().inflate(R.layout.file_item, null);
			
			PcFileDetail temp = filesInfo.get(pos);
			fileName = (TextView) cView.findViewById(R.id.fileName);
			fileName.setText(temp.getName());
			Drawable fileIcon;
			if(temp.isFile())
				fileIcon = getResources().getDrawable(R.drawable.files);
			else
				fileIcon = getResources().getDrawable(R.drawable.folders);
			fileName.setCompoundDrawablesWithIntrinsicBounds(fileIcon, null, null, null);
			return cView;
		}
	}
}