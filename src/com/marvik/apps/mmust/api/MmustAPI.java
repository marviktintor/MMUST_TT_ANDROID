package com.marvik.apps.mmust.api;

import java.util.ArrayList;

import android.accounts.Account;
import android.content.ContentProviderClient;
import android.content.Context;
import android.content.SyncResult;
import android.os.Bundle;
import android.widget.Toast;

import com.marvik.apps.mmust.database.DbConsts;
import com.marvik.apps.mmust.utils.MmustUtils;
import com.marvik.apps.mmust.utils.StudentAuthenticator;

public class MmustAPI {
	
	/*Clicked Download Item */
	public static final String EXTRA_SELECTED_DOWNLOAD_FILE_ID="fileid";
	
	
	/*Clicked Lec info */
	public static final String EXTRA_SELECTED_LEC_ID="lec_id";
	
	MmustUtils utils;
	
	Context context;
	public MmustAPI(Context context) {
		// TODO Auto-generated constructor stub
		
		this.context = context;
		utils = new MmustUtils(context);
	}

	public ArrayList<String> dropTablesSQL() {
		ArrayList<String> tables = new ArrayList<String>();
		
		String assignments_table = "DROP TABLE "+DbConsts.ASSIGNMENTS_TABLE;
		tables.add(assignments_table);
		
		String campus_table="DROP TABLE "+DbConsts.CAMPUS_TABLE;
		tables.add(campus_table);
		
		String classes_table = "DROP TABLE "+DbConsts.CLASSES_TABLE;
		tables.add(classes_table);
		
		String commits_table = "DROP TABLE "+DbConsts.COMMITS_INFO_TABLE;
		tables.add(commits_table);
		
		String courses_table = "DROP TABLE "+DbConsts.COURSES_TABLE;
		tables.add(courses_table);
		
		String departments_table="DROP TABLE "+DbConsts.DEPARTMENT_TABLE;
		tables.add(departments_table);
	
		String downloads_table = "DROP TABLE "+DbConsts.DOWNLOADS_TABLE;
		tables.add(downloads_table);
		
		String exams_table = "DROP TABLE "+DbConsts.EXAMS_TABLE;
		tables.add(exams_table);
		
		String faculty_table="DROP TABLE "+DbConsts.FACULTY_TABLE;
		tables.add(faculty_table);
		
		String lec_info_table = "DROP TABLE "+DbConsts.LECS_TABLE;
		tables.add(lec_info_table);
		
		String notifications_table = "DROP TABLE "+DbConsts.NOTIFICATION_TABLE;
		tables.add(notifications_table);
		
		String school_courses_table="DROP TABLE "+DbConsts.SCHOOL_COURSES_TABLE;
		tables.add(school_courses_table);
		
		String student_info_table="DROP TABLE "+DbConsts.STUDENT_INFO_TABLE;
		tables.add(student_info_table);
		
		String uploads_table ="DROP TABLE "+DbConsts.UPLOADS_TABLE;
		tables.add(uploads_table);
		
		return tables;
	}
	public ArrayList<String> createTablesSQL() {
		// TODO Auto-generated method stub
		ArrayList<String> tables = new ArrayList<String>();
		
		
		
		String assignments_table = "CREATE TABLE " + DbConsts.ASSIGNMENTS_TABLE
				+ "(" + DbConsts.ASSIGNMENTS_COURSE_CODE + " text not null,"
				+ DbConsts.ASSIGNMENTS_SUBMISSION_DATE + " date not null,"
				+ DbConsts.ASSIGNMENTS_ASSIGNMENT_NO + " text not null);";
		tables.add(assignments_table);
		
		String campus_table = "CREATE TABLE " + DbConsts.CAMPUS_TABLE + "("
				+ DbConsts.CAMPUS_ID + " text not null," + DbConsts.CAMPUS_NAME
				+ " text not null);";
		tables.add(campus_table);
		
		
		String classes_table = "CREATE TABLE " + DbConsts.CLASSES_TABLE + "("
				+ DbConsts.CLASS_COURSE_CODE + " text not null,"
				+ DbConsts.CLASS_LEC_ID + " text not null,"
				+ DbConsts.CLASS_ROOM + " text not null,"
				+ DbConsts.CLASS_START + " time not null,"
				+ DbConsts.CLASS_STOP + " time not null," + DbConsts.CLASS_DAYS
				+ " integer not null);";
		tables.add(classes_table);
		
		String commits_table = "CREATE TABLE " + DbConsts.COMMITS_INFO_TABLE + "("
				+ DbConsts.COMMITS_INFO_COMMITS + " integer not null,"
				+ DbConsts.COMMITS_INFO_ASSIGNMENTS + " integer not null,"
				+ DbConsts.COMMITS_INFO_CAMPUS + " integer not null,"
				+ DbConsts.COMMITS_INFO_CLASSES + " integer not null,"
				+ DbConsts.COMMITS_INFO_COURSES + " integer not null,"
				+ DbConsts.COMMITS_INFO_DEPARTMENTS + " integer not null,"
				+ DbConsts.COMMITS_INFO_DOWNLOADS + " integer not null,"
				+ DbConsts.COMMITS_INFO_EXAMS + " integer not null,"
				+ DbConsts.COMMITS_INFO_FACULTY + " integer not null,"
				+ DbConsts.COMMITS_INFO_LECTURER + " integer not null,"
				+ DbConsts.COMMITS_INFO_NOTIFICATIONS + " integer not null,"
				+ DbConsts.COMMITS_INFO_SCHOOL_COURSES + " integer not null,"
				+ DbConsts.COMMITS_INFO_STUDENTS + " integer not null,"
				 + DbConsts.COMMITS_INFO_UPLOADS + " integer not null);";
		tables.add(commits_table);
		
		String courses_table = "CREATE TABLE "+DbConsts.COURSES_TABLE+"("
				+DbConsts.COURSES_COURSE_CODE+" text not null,"
				+DbConsts.COURSES_COURSE_NAME+" text not null," 
				+DbConsts.COURSES_COURSE_LEC_ID+" text not null)";
		tables.add(courses_table);
		
		String departments_table = "CREATE TABLE "+DbConsts.DEPARTMENT_TABLE+"("
				+DbConsts.DEPARTMENT_ID+" text not null,"
				+DbConsts.DEPARTMENT_NAME+" text not null," 
				+DbConsts.DEPARTMENT_FACULTY_ID+" text not null," 
				+DbConsts.DEPARTMENT_CAMPUS_ID+" text not null)";
		tables.add(departments_table);
		
		String downloads_table = "CREATE TABLE " + DbConsts.DOWNLOADS_TABLE + "("
				+ DbConsts.DOWNLOAD_FILE_ID + " text not null," + DbConsts.DOWNLOAD_COURSE_CODE
				+ " text not null);";
		tables.add(downloads_table);
		
		
		String exams_table = "CREATE TABLE " + DbConsts.EXAMS_TABLE + "("
				+ DbConsts.EXAM_COURSE_CODE + " text not null,"
				+ DbConsts.EXAM_TYPE + " integer not null,"
				+ DbConsts.EXAM_ROOM + " text not null," + DbConsts.EXAM_DATE
				+ " date not null," + DbConsts.EXAM_START + " time not null,"
				+ DbConsts.EXAM_DURATION + " integer not null)";
		tables.add(exams_table);
		
		String faculty_table = "CREATE TABLE "+DbConsts.FACULTY_TABLE+"("
				+DbConsts.FACULTY_ID+" text not null,"
				+DbConsts.FACULTY_NAME+" text not null," 
				+DbConsts.FACULTY_CAMPUS_ID+" text not null)";
		tables.add(faculty_table);
		
		
		String lec_info_table = "CREATE TABLE " + DbConsts.LECS_TABLE + "("
				+ DbConsts.LECS_LEC_ID + " text not null,"
				+ DbConsts.LECS_LEC_NAME + " text not null,"
				+ DbConsts.LEC_AVATOR_URI + " text not null,"
				+ DbConsts.LECS_LEC_EMAIL + " text not null,"
				+ DbConsts.LECS_LEC_PHONE + " text not null,"
				+ DbConsts.LEC_QUALIFICATIONS + " text not null)";
		tables.add(lec_info_table);
		
		
		String notifications_table = "CREATE TABLE "+ DbConsts.NOTIFICATION_TABLE + "(" 
				+ DbConsts.NOTIFICATION_ID + " text not null,"
				+ DbConsts.NOTIFICATION_TITLE + " text not null,"
				+ DbConsts.NOTIFICATION_MESSAGE + " text not null,"
				+ DbConsts.NOTIFICATION_SENDER + " text not null,"
				+ DbConsts.NOTIFICATION_SEND_TIME + " long not null)";
		tables.add(notifications_table);
		
		
		String school_courses_table = "CREATE TABLE " + DbConsts.SCHOOL_COURSES_TABLE + "("
				+ DbConsts.SCHOOL_COURSES_COURSE_CODE + " text not null," + DbConsts.SCHOOL_COURSES_COURSE_NAME
				+ " text not null);";
		tables.add(school_courses_table);
		
		
		String student_info_table = "CREATE TABLE " + DbConsts.STUDENT_INFO_TABLE + "("
				+ DbConsts.STUDENT_REG_NO + " text not null,"
				+ DbConsts.STUDENT_FULL_NAME + " text not null,"
				+ DbConsts.STUDENT_EMAIL + " text not null,"
				+ DbConsts.STUDENT_PHONENUMBER + " text not null,"
				+ DbConsts.STUDENT_CATEGORY + " text not null,"
				+ DbConsts.STUDENT_PURSUING_COURSE + " text not null,"
				+ DbConsts.STUDENT_STUDENT_ADM_YR + " year not null,"
				+ DbConsts.STUDENT_DEPARTMENT_ID + " text not null,"
				+ DbConsts.STUDENT_FACULTY_ID + " text not null,"
				+ DbConsts.STUDENT_CAMPUS_ID + " text not null);";
		tables.add(student_info_table);
		
		
		String uploads_table = "CREATE TABLE "+ DbConsts.UPLOADS_TABLE + "(" 
				+ DbConsts.UPLOADS_FILE_ID + " text not null,"
				+ DbConsts.UPLOADS_FILENAME + " text not null,"
				+ DbConsts.UPLOADS_FILESIZE + " long not null,"
				+ DbConsts.UPLOADS_FILE_DESC + " text not null,"
				+ DbConsts.UPLOADS_FILE_UPLOADER + " text not null,"
				+ DbConsts.UPLOADS_FILE_UPLOAD_TIME + " long not null,"
				+ DbConsts.UPLOADS_FILE_URI + " text not null)";
		tables.add(uploads_table);
		
		return tables;
	}

	public void performSync(Account account, Bundle extras, String authority,
			ContentProviderClient provider, SyncResult syncResult) {
		// TODO Auto-generated method stub
		utils.getJSONForURL(StudentAuthenticator.EXTRA_URL_COMMITS_INFO, true);
		
	}

}
