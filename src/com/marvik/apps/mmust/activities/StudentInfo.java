package com.marvik.apps.mmust.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.marvik.apps.mmust.R;
import com.marvik.apps.mmust.database.DbConsts;
import com.marvik.apps.mmust.database.DbQueryHandler;

public class StudentInfo extends MasterActivity{
	View activityContentView;
	ImageView ivStudentAvator;
	DbQueryHandler dbQueryHandler;
	
	TextView tvStudentName,tvStudentRegNo,tvPursuingCourse,tvEmail,tvPhonenumber,tvStudentCampus,tvStudentFaculty,tvStudentDepartment,tvAdmYr;
	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		activityContentView = getLayoutInflater().inflate(R.layout.student_info, null, false);
		setContentView(getCustomContentView(activityContentView,"My Profile"));
		init();
	}

	private void init() {
		// TODO Auto-generated method stub
		dbQueryHandler = new DbQueryHandler(StudentInfo.this);
		
		ivStudentAvator = (ImageView)activityContentView.findViewById(R.id.student_info_imageView_student_avator);
		
		tvStudentName= (TextView)activityContentView.findViewById(R.id.student_info_textView_student_name);
		tvStudentRegNo= (TextView)activityContentView.findViewById(R.id.student_info_textView_student_reg_no);
		tvPursuingCourse= (TextView)activityContentView.findViewById(R.id.student_info_textView_pursuing_course);
		tvEmail= (TextView)activityContentView.findViewById(R.id.student_info_textView_student_email);
		tvPhonenumber= (TextView)activityContentView.findViewById(R.id.student_info_textView_phonenumber);
		tvStudentCampus= (TextView)activityContentView.findViewById(R.id.student_info_textView_student_campus);
		tvStudentFaculty= (TextView)activityContentView.findViewById(R.id.student_info_textView_student_faculty);
		tvStudentDepartment= (TextView)activityContentView.findViewById(R.id.student_info_textView_student_department);
		tvAdmYr= (TextView)activityContentView.findViewById(R.id.student_info_textView_adm_yr);
		
		setStudentInfo();
	}

	private void setStudentInfo() {
		// TODO Auto-generated method stub
		
		String mStudentName = dbQueryHandler.getColumnIndexValue(DbConsts.STUDENT_INFO_TABLE,DbConsts.STUDENT_FULL_NAME );
		String mStudentRegNo= dbQueryHandler.getColumnIndexValue(DbConsts.STUDENT_INFO_TABLE,DbConsts.STUDENT_REG_NO );
		String mPursuingCourse= dbQueryHandler.getColumnIndexValue(DbConsts.SCHOOL_COURSES_TABLE, DbConsts.SCHOOL_COURSES_COURSE_CODE, dbQueryHandler.getColumnIndexValue(DbConsts.STUDENT_INFO_TABLE,DbConsts.STUDENT_PURSUING_COURSE ), DbConsts.SCHOOL_COURSES_COURSE_NAME);
		String mEmail= dbQueryHandler.getColumnIndexValue(DbConsts.STUDENT_INFO_TABLE,DbConsts.STUDENT_EMAIL );
		String mPhonenumber= dbQueryHandler.getColumnIndexValue(DbConsts.STUDENT_INFO_TABLE,DbConsts.STUDENT_PHONENUMBER );
		
		String mStudentCampus= dbQueryHandler.getColumnIndexValue(DbConsts.CAMPUS_TABLE,DbConsts.CAMPUS_NAME );
		String mStudentFaculty= dbQueryHandler.getColumnIndexValue(DbConsts.FACULTY_TABLE,DbConsts.FACULTY_NAME );
		String mStudentDepartment= dbQueryHandler.getColumnIndexValue(DbConsts.DEPARTMENT_TABLE,DbConsts.DEPARTMENT_NAME );
		String mAdmYr= dbQueryHandler.getColumnIndexValue(DbConsts.STUDENT_INFO_TABLE,DbConsts.STUDENT_STUDENT_ADM_YR );
		
		tvStudentName.setText(mStudentName);
		tvStudentRegNo.setText(mStudentRegNo);
		tvPursuingCourse.setText(mPursuingCourse);
		tvEmail.setText(mEmail);
		tvPhonenumber.setText(mPhonenumber);
		tvStudentCampus.setText(mStudentCampus);
		tvStudentFaculty.setText(mStudentFaculty);
		tvStudentDepartment.setText(mStudentDepartment);
		tvAdmYr.setText(mAdmYr);
		
	}
}
