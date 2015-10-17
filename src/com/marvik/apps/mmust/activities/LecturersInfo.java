package com.marvik.apps.mmust.activities;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.marvik.apps.mmust.R;
import com.marvik.apps.mmust.api.MmustAPI;
import com.marvik.apps.mmust.database.Database;
import com.marvik.apps.mmust.database.DbConsts;
import com.marvik.apps.mmust.database.DbQueryHandler;

public class LecturersInfo extends MasterActivity {

	TextView tvLecName,tvLecCourse,tvLecQualifications,tvEmail,tvLecPhone;
	ImageView ivLecAvator,ivCallIcon,ivMessageIcon;
	View activityContentView;
	DbQueryHandler dbQueryHandler;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		activityContentView = getLayoutInflater().inflate(R.layout.lecturers_info, null, true);
		setContentView(getCustomContentView(activityContentView));
		
		init();
	}

	private void init() {
		// TODO Auto-generated method stub
		dbQueryHandler = new DbQueryHandler(LecturersInfo.this);
		
		tvLecName=(TextView)activityContentView.findViewById(R.id.lecturer_info_textView_lec_name);
		tvLecCourse = (TextView)activityContentView.findViewById(R.id.lecturer_info_textView_lec_course);
		tvLecQualifications=(TextView)activityContentView.findViewById(R.id.lecturer_info_textView_lec_qualifications);
		tvEmail=(TextView)activityContentView.findViewById(R.id.lecturer_info_textView_lec_email);
		tvLecPhone=(TextView)activityContentView.findViewById(R.id.lecturer_info_textView_lec_phone);
		

		ivLecAvator = (ImageView)activityContentView.findViewById(R.id.lecturer_info_imageView_lec_avator);
		ivCallIcon = (ImageView)activityContentView.findViewById(R.id.lecturer_info_imageView_call_icon);
		ivMessageIcon = (ImageView)activityContentView.findViewById(R.id.lecturer_info_imageView_message_icon);
		
		ivMessageIcon.setOnClickListener(this);
		ivCallIcon.setOnClickListener(this);
		tvEmail.setOnClickListener(this);
		
		fetchLecInfo();
	}
@Override
public void onClick(View view) {
	// TODO Auto-generated method stub
	super.onClick(view);
	
	switch(view.getId()){
	case R.id.lecturer_info_textView_lec_email:
		startEmailIntent();
		break;
	case R.id.lecturer_info_imageView_call_icon:
		startCallIntent();
		break;
	case R.id.lecturer_info_imageView_message_icon:
		startMessageIntent();
		break;
	}
}

	public void startEmailIntent() {
		Intent intent = new Intent(Intent.ACTION_SEND);
		intent.putExtra(Intent.EXTRA_EMAIL, new String []{tvEmail.getText().toString()});
		intent.putExtra(Intent.EXTRA_TEXT, "\n\n\n\nSent from Masinde Muliro Students Timetable Android App");
		intent.putExtra(Intent.EXTRA_CC,new String []{dbQueryHandler.getColumnIndexValue(DbConsts.STUDENT_INFO_TABLE, DbConsts.STUDENT_EMAIL)});
		intent.putExtra(Intent.EXTRA_BCC, dbQueryHandler.getDeviceRegisteredEmail());
		intent.setType("text/plain");
		startActivity(Intent.createChooser(intent, "Email "+tvLecName.getText().toString()+" using?"));
	}

	public void startCallIntent() {
		startActivity(new Intent(Intent.ACTION_DIAL,Uri.parse("tel:"+tvLecPhone.getText().toString())));
	}

	public void startMessageIntent() { 
		Intent intent = new Intent(Intent.ACTION_SEND);
		intent.putExtra(Intent.EXTRA_PHONE_NUMBER, tvLecPhone.getText().toString());
		intent.putExtra(Intent.EXTRA_TEXT, "\n\n\n\nSent from Masinde Muliro Students Timetable Android App");
		intent.setType("text/plain");
		startActivity(Intent.createChooser(intent, "Sms "+tvLecName.getText().toString()+" using?"));
	}
	private void fetchLecInfo() {
		// TODO Auto-generated method stub
		if(getIntent().getExtras().getString(MmustAPI.EXTRA_SELECTED_LEC_ID).equals("")){
			startActivity(new Intent(getApplicationContext(),Lecturers.class));
			finish();
		}
		String lec_id = getIntent().getExtras().getString(MmustAPI.EXTRA_SELECTED_LEC_ID);
		
		
		String lecName = "",  lecQualifications = "", lecEmail = "", lecPhone = "", lecAvator="";

		Database db = new Database(LecturersInfo.this);
		SQLiteDatabase sql = db.getReadableDatabase();
		
		Cursor cursor = sql.query(DbConsts.LECS_TABLE, null, DbConsts.LECS_LEC_ID +"='"+lec_id+"'", null, null,null,null);
		
		for(cursor.moveToFirst();!cursor.isAfterLast();cursor.moveToNext()){
			
			lecName =  cursor.getString(cursor.getColumnIndex(DbConsts.LECS_LEC_NAME));
			lecQualifications =  cursor.getString(cursor.getColumnIndex(DbConsts.LEC_QUALIFICATIONS));
			lecEmail =  cursor.getString(cursor.getColumnIndex(DbConsts.LECS_LEC_EMAIL));
			lecPhone =  cursor.getString(cursor.getColumnIndex(DbConsts.LECS_LEC_PHONE));
			lecAvator = cursor.getString(cursor.getColumnIndex(DbConsts.LEC_AVATOR_URI));
		}
		cursor.close();
		sql.close();
		db.close();
		
		String lecCourse =dbQueryHandler.getColumnIndexValue(DbConsts.COURSES_TABLE,DbConsts.COURSES_COURSE_LEC_ID,lec_id,DbConsts.COURSES_COURSE_NAME) ;
		
		tvLecName.setText(lecName);
		tvLecCourse.setText(lecCourse);
		tvLecQualifications.setText(lecQualifications);
		tvEmail.setText(lecEmail);
		tvLecPhone.setText(lecPhone);
		
	}
	@Override
	public void onBackPressed() {
		startActivity(new Intent(getApplicationContext(),Lecturers.class));
		finish();
	}
}
