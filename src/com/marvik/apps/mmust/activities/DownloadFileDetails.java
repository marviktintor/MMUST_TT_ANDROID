package com.marvik.apps.mmust.activities;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.TextView;

import com.marvik.apps.mmust.R;
import com.marvik.apps.mmust.api.MmustAPI;
import com.marvik.apps.mmust.database.Database;
import com.marvik.apps.mmust.database.DbConsts;
import com.marvik.apps.mmust.database.DbQueryHandler;
import com.marvik.apps.mmust.utils.MmustUtils;

public class DownloadFileDetails extends MasterActivity{//SHOULD NOT EXTEND MASTER ACTIVITY

	TextView tvFilename,tvUploader,tvFileSize,tvFileDesc,tvCourse,tvUploadTime,tvMoreAboutFile;
	MmustUtils mmustUtils;
	View activityContentView;
	DbQueryHandler queryHandler;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		activityContentView = getLayoutInflater().inflate(R.layout.file_details, null, true);
		setContentView(getCustomContentView(activityContentView));
		
		init();
		
	}

	private void init() {
		// TODO Auto-generated method stub
		mmustUtils = new MmustUtils(DownloadFileDetails.this);
		queryHandler = new DbQueryHandler(DownloadFileDetails.this);
	
		tvFilename= (TextView)activityContentView.findViewById(R.id.file_details_textView_filename);
		tvUploader = (TextView)activityContentView.findViewById(R.id.file_details_textView_uploader);
		tvUploadTime = (TextView)activityContentView.findViewById(R.id.file_details_textView_upload_time);
		tvFileSize = (TextView)activityContentView.findViewById(R.id.file_details_textView_filesize);
		tvFileDesc = (TextView)activityContentView.findViewById(R.id.file_details_textView_file_desc);
		tvCourse = (TextView)activityContentView.findViewById(R.id.file_details_textView_course);
		tvMoreAboutFile = (TextView)activityContentView.findViewById(R.id.file_details_textView_more_about_filename_label);
		
		try {
			fetchSelectedDownloadItemInfo();
		} catch (NullPointerException e) {
			startActivity(new Intent(getApplicationContext(), Downloads.class));
			finish();
			// return; <- Maybe needed to CTRL + C further procedural Execution
		}
	}

	private void fetchSelectedDownloadItemInfo() throws NullPointerException{
		// TODO Auto-generated method stub
		
		
		String  fileid = getIntent().getExtras().getString(MmustAPI.EXTRA_SELECTED_DOWNLOAD_FILE_ID);

		
		Database db = new Database(DownloadFileDetails.this);
		SQLiteDatabase sql = db.getReadableDatabase();
		
		Cursor cursor = sql.query(DbConsts.UPLOADS_TABLE, null, DbConsts.UPLOADS_FILE_ID +"='"+fileid+"'" , null, null,null,null);
		
		String fileName = "";
		String uploader = "";
		String filesize =  "";
		String filedesc =  "";
		String filecourse = "";
		long uploadTime =0;
		String fileDownloadUri="";
		String file_id ="";
		
		for(cursor.moveToFirst();!cursor.isAfterLast();cursor.moveToNext()){
			file_id= cursor.getString(cursor.getColumnIndex(DbConsts.UPLOADS_FILE_ID));
			fileName = cursor.getString(cursor.getColumnIndex(DbConsts.UPLOADS_FILENAME));
			uploader = cursor.getString(cursor.getColumnIndex(DbConsts.UPLOADS_FILE_UPLOADER));
			filesize = cursor.getString(cursor.getColumnIndex(DbConsts.UPLOADS_FILESIZE));
			filedesc = cursor.getString(cursor.getColumnIndex(DbConsts.UPLOADS_FILE_DESC));
			uploadTime = cursor.getLong(cursor.getColumnIndex(DbConsts.UPLOADS_FILE_UPLOAD_TIME));
			fileDownloadUri = cursor.getString(cursor.getColumnIndex(DbConsts.UPLOADS_FILE_URI));
		}
		cursor.close();
		sql.close();
		db.close();
		
		tvFilename.setText(fileName);
		tvUploader.setText(uploader);
		tvUploadTime.setText(mmustUtils.getFormattedTime(uploadTime));
		tvFileSize.setText(mmustUtils.getFileSize(Double.valueOf(filesize)));
		tvFileDesc.setText(filedesc);
		
		filecourse = queryHandler.getColumnIndexValue(DbConsts.DOWNLOADS_TABLE, DbConsts.DOWNLOAD_FILE_ID, file_id, DbConsts.DOWNLOAD_COURSE_CODE);
		
		tvCourse.setText(queryHandler.getColumnIndexValue(DbConsts.COURSES_TABLE, DbConsts.COURSES_COURSE_CODE, filecourse, DbConsts.COURSES_COURSE_NAME));
		tvMoreAboutFile.setText(Html.fromHtml("<strong>"+fileName+"</strong>"));
	}
	@Override
	public void onBackPressed() {
		finish();
		startActivity(new Intent(getApplicationContext(), Downloads.class));
	}
}
