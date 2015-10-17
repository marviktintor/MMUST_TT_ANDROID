package com.marvik.apps.mmust.activities;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.marvik.apps.mmust.R;
import com.marvik.apps.mmust.api.MmustAPI;
import com.marvik.apps.mmust.database.Database;
import com.marvik.apps.mmust.database.DbConsts;
import com.marvik.apps.mmust.database.DbQueryHandler;
import com.marvik.apps.mmust.utils.MmustUtils;

public class Downloads  extends MasterActivity {

	ListView lvFileList;
	List<DownloadList>lDownloads;
	MmustUtils mmustUtils;
	View activityContentView;
	DbQueryHandler dbQueryHandler;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		activityContentView = getLayoutInflater().inflate(R.layout.downloads, null, true);
		setContentView(getCustomContentView(activityContentView,"Downloads"));
		init();
	}
	
	private void init() {
		// TODO Auto-generated method stub
		mmustUtils = new MmustUtils(Downloads.this);
		dbQueryHandler = new DbQueryHandler(Downloads.this);
		
		lvFileList = (ListView)activityContentView.findViewById(R.id.downloads_listView_file_list);
		populateDownloads();
		lvFileList.setOnItemClickListener(listener);
	}

	private void populateDownloads() {
		// TODO Auto-generated method stub
		lDownloads = new ArrayList<Downloads.DownloadList>();
		
		Database db = new Database(Downloads.this);
		SQLiteDatabase sql = db.getReadableDatabase();
		
		Cursor cursor = sql.query(DbConsts.DOWNLOADS_TABLE, null, null, null, null,null,null);
		
		for(cursor.moveToFirst();!cursor.isAfterLast();cursor.moveToNext()){
			String file_id = cursor.getString(cursor.getColumnIndex(DbConsts.DOWNLOAD_FILE_ID));
			String file_course_code = cursor.getString(cursor.getColumnIndex(DbConsts.DOWNLOAD_COURSE_CODE));
						
			lDownloads.add(new DownloadList(file_id, file_course_code));
		}
		cursor.close();
		sql.close();
		db.close();
		lvFileList.setAdapter(new DownloadsListAdapter());
	}
	
	OnItemClickListener listener = new OnItemClickListener() {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			// TODO Auto-generated method stub
			
		String  fileid = lDownloads.get(position).getFileId();
		
		Bundle bundle = new Bundle();
		bundle.putString(MmustAPI.EXTRA_SELECTED_DOWNLOAD_FILE_ID, fileid);
			
		startActivity(new Intent(getApplicationContext(), DownloadFileDetails.class).putExtras(bundle));
			
		}
	};
	private class DownloadsListAdapter extends ArrayAdapter<DownloadList>{

		public DownloadsListAdapter() {
			super(Downloads.this, R.layout.downloads_ui, lDownloads);
			// TODO Auto-generated constructor stub
		}
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			View downloadsListView = convertView;
			if(convertView==null){
				downloadsListView = getLayoutInflater().inflate(R.layout.downloads_ui, parent, false);
			}
			
			DownloadList downloadList = lDownloads.get(position);
			
			TextView tvFilename = (TextView)downloadsListView.findViewById(R.id.downloads_ui_textView_fileName);
			TextView tvCourse = (TextView)downloadsListView.findViewById(R.id.downloads_ui_textView_course);

			
			String filename = dbQueryHandler.getColumnIndexValue(DbConsts.UPLOADS_TABLE, DbConsts.UPLOADS_FILE_ID, downloadList.getFileId(), DbConsts.UPLOADS_FILENAME);
			String filecourse = dbQueryHandler.getColumnIndexValue(DbConsts.COURSES_TABLE, DbConsts.COURSES_COURSE_CODE, downloadList.getFileCourseCode(), DbConsts.COURSES_COURSE_NAME);
			
			tvFilename.setText(filename);
			tvCourse.setText(filecourse);
			
			return downloadsListView;
		}
	}
	private class DownloadList{
		String fileId;
		String fileCourseCode;
		
		public DownloadList(String fileId, String fileCourseCode) {
			super();
			this.fileId = fileId;
			this.fileCourseCode = fileCourseCode;
		}
		public String getFileId() {
			return fileId;
		}
		public String getFileCourseCode() {
			return fileCourseCode;
		}
		
		
		
	}
	
	
}
