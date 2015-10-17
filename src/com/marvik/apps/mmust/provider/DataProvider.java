package com.marvik.apps.mmust.provider;

import com.marvik.apps.mmust.database.Database;
import com.marvik.apps.mmust.database.DbConsts;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;

public class DataProvider extends ContentProvider {

	SQLiteDatabase sqliteDatabase;
	Database database;
	
	public static final String AUTH;
	public static final Uri MMUST_ASSIGNMENTS_CONTENT_URI;
	public static final Uri MMUST_CAMPUS_CONTENT_URI;
	public static final Uri MMUST_CLASSES_CONTENT_URI;
	public static final Uri MMUST_COMMITS_INFO_CONTENT_URI;
	public static final Uri MMUST_COURSES_CONTENT_URI;
	public static final Uri MMUST_DEPARTMENT_CONTENT_URI;
	public static final Uri MMUST_DOWNLOADS_CONTENT_URI;
	public static final Uri MMUST_EXAMS_CONTENT_URI;
	public static final Uri MMUST_FACULTY_CONTENT_URI;
	public static final Uri MMUST_LEC_INFO_CONTENT_URI;
	public static final Uri MMUST_NOTIFICATIONS_CONTENT_URI;
	public static final Uri MMUST_SCHOOL_COURSES_CONTENT_URI;
	public static final Uri MMUST_STUDENT_INFO_CONTENT_URI;
	public static final Uri MMUST_UPLOADS_CONTENT_URI;
	
	
	public static final int MMUST_ASSIGNMENTS_URI_MATCHER;
	public static final int MMUST_CAMPUS_URI_MATCHER;
	public static final int MMUST_CLASSES_URI_MATCHER;
	public static final int MMUST_COMMITS_INFO_URI_MATCHER;
	public static final int MMUST_COURSES_URI_MATCHER;
	public static final int MMUST_DEPARTMENT_URI_MATCHER;
	public static final int MMUST_DOWNLOADS_URI_MATCHER;
	public static final int MMUST_EXAMS_URI_MATCHER;
	public static final int MMUST_FACULTY_URI_MATCHER;
	public static final int MMUST_LEC_INFO_URI_MATCHER;
	public static final int MMUST_NOTIFICATIONS_URI_MATCHER;
	public static final int MMUST_SCHOOL_COURSES_URI_MATCHER;
	public static final int MMUST_STUDENT_INFO_URI_MATCHER;
	public static final int MMUST_UPLOADS_URI_MATCHER;
	
	static UriMatcher uriMatcher;
	
	static{
		AUTH="com.marvik.apps.mmust.provider.DataProvider";
	
		MMUST_ASSIGNMENTS_CONTENT_URI = Uri.parse("content://"+AUTH +"/" +DbConsts.ASSIGNMENTS_TABLE);
		MMUST_CAMPUS_CONTENT_URI = Uri.parse("content://"+AUTH +"/" +DbConsts.CAMPUS_TABLE);
		MMUST_CLASSES_CONTENT_URI = Uri.parse("content://"+AUTH +"/" +DbConsts.CLASSES_TABLE);
		MMUST_COMMITS_INFO_CONTENT_URI = Uri.parse("content://"+AUTH +"/" +DbConsts.COMMITS_INFO_TABLE);
		MMUST_COURSES_CONTENT_URI = Uri.parse("content://"+AUTH +"/" +DbConsts.COURSES_TABLE);
		MMUST_DEPARTMENT_CONTENT_URI = Uri.parse("content://"+AUTH +"/" +DbConsts.DEPARTMENT_TABLE);
		MMUST_DOWNLOADS_CONTENT_URI = Uri.parse("content://"+AUTH +"/" +DbConsts.DOWNLOADS_TABLE);
		MMUST_EXAMS_CONTENT_URI = Uri.parse("content://"+AUTH +"/" +DbConsts.EXAMS_TABLE);
		MMUST_FACULTY_CONTENT_URI = Uri.parse("content://"+AUTH +"/" +DbConsts.FACULTY_TABLE);
		MMUST_LEC_INFO_CONTENT_URI = Uri.parse("content://"+AUTH +"/" +DbConsts.LECS_TABLE);
		MMUST_NOTIFICATIONS_CONTENT_URI = Uri.parse("content://"+AUTH +"/" +DbConsts.NOTIFICATION_TABLE);
		MMUST_SCHOOL_COURSES_CONTENT_URI = Uri.parse("content://"+AUTH +"/" +DbConsts.SCHOOL_COURSES_TABLE);
		MMUST_STUDENT_INFO_CONTENT_URI = Uri.parse("content://"+AUTH +"/" +DbConsts.STUDENT_INFO_TABLE);
		MMUST_UPLOADS_CONTENT_URI = Uri.parse("content://"+AUTH +"/" +DbConsts.UPLOADS_TABLE);
		
		
		MMUST_ASSIGNMENTS_URI_MATCHER=1;
		MMUST_CAMPUS_URI_MATCHER=2;
		MMUST_CLASSES_URI_MATCHER=3;
		MMUST_COMMITS_INFO_URI_MATCHER=4;
		MMUST_COURSES_URI_MATCHER=5;
		MMUST_DEPARTMENT_URI_MATCHER=6;
		MMUST_DOWNLOADS_URI_MATCHER=7;
		MMUST_EXAMS_URI_MATCHER=8;
		MMUST_FACULTY_URI_MATCHER=9;
		MMUST_LEC_INFO_URI_MATCHER=10;
		MMUST_NOTIFICATIONS_URI_MATCHER=11;
		MMUST_SCHOOL_COURSES_URI_MATCHER=12;
		MMUST_STUDENT_INFO_URI_MATCHER=13;
		MMUST_UPLOADS_URI_MATCHER=14;
		
		uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
		uriMatcher.addURI(AUTH, DbConsts.ASSIGNMENTS_TABLE,MMUST_ASSIGNMENTS_URI_MATCHER );
		uriMatcher.addURI(AUTH, DbConsts.CAMPUS_TABLE, MMUST_CAMPUS_URI_MATCHER);
		uriMatcher.addURI(AUTH,DbConsts.CLASSES_TABLE ,MMUST_CLASSES_URI_MATCHER );
		uriMatcher.addURI(AUTH,DbConsts.COMMITS_INFO_TABLE , MMUST_COMMITS_INFO_URI_MATCHER);
		uriMatcher.addURI(AUTH, DbConsts.COURSES_TABLE, MMUST_COURSES_URI_MATCHER);
		uriMatcher.addURI(AUTH,DbConsts.DEPARTMENT_TABLE , MMUST_DEPARTMENT_URI_MATCHER);
		uriMatcher.addURI(AUTH,DbConsts.DOWNLOADS_TABLE ,MMUST_DOWNLOADS_URI_MATCHER );
		uriMatcher.addURI(AUTH,DbConsts.EXAMS_TABLE , MMUST_EXAMS_URI_MATCHER);
		uriMatcher.addURI(AUTH,DbConsts.FACULTY_TABLE , MMUST_FACULTY_URI_MATCHER);
		uriMatcher.addURI(AUTH,DbConsts.LECS_TABLE ,MMUST_LEC_INFO_URI_MATCHER );
		uriMatcher.addURI(AUTH,DbConsts.NOTIFICATION_TABLE ,MMUST_NOTIFICATIONS_URI_MATCHER );
		uriMatcher.addURI(AUTH,DbConsts.SCHOOL_COURSES_TABLE ,MMUST_SCHOOL_COURSES_URI_MATCHER );
		uriMatcher.addURI(AUTH,DbConsts.STUDENT_INFO_TABLE , MMUST_STUDENT_INFO_URI_MATCHER);
		uriMatcher.addURI(AUTH,DbConsts.UPLOADS_TABLE ,MMUST_UPLOADS_URI_MATCHER );
		
	}
	@Override
	public boolean onCreate() {
		// TODO Auto-generated method stub
		
		return createDatabase();
	}

	@Override
	public String getType(Uri uri) {
		// TODO Auto-generated method stub
		
		if (getWritableDatabase()) {

		}
		
		return uri.toString();
	}

	@Override
	public Uri insert(Uri uri, ContentValues values) {
		// TODO Auto-generated method stub
		
		if(getWritableDatabase()){
			
			if (uriMatcher.match(uri) == MMUST_ASSIGNMENTS_URI_MATCHER) {
				sqliteDatabase.insert(DbConsts.ASSIGNMENTS_TABLE, null, values);
			}
			if (uriMatcher.match(uri) == MMUST_CAMPUS_URI_MATCHER) {
				sqliteDatabase.insert(DbConsts.CAMPUS_TABLE, null, values);
			}
			if (uriMatcher.match(uri) == MMUST_CLASSES_URI_MATCHER) {
				sqliteDatabase.insert(DbConsts.CLASSES_TABLE, null, values);
			}
			if (uriMatcher.match(uri) == MMUST_COMMITS_INFO_URI_MATCHER) {
				sqliteDatabase.insert(DbConsts.COMMITS_INFO_TABLE, null, values);
			}
			if (uriMatcher.match(uri) == MMUST_COURSES_URI_MATCHER) {
				sqliteDatabase.insert(DbConsts.COURSES_TABLE, null, values);
			}
			if (uriMatcher.match(uri) == MMUST_DEPARTMENT_URI_MATCHER) {
				sqliteDatabase.insert(DbConsts.DEPARTMENT_TABLE, null, values);
			}
			if (uriMatcher.match(uri) == MMUST_DOWNLOADS_URI_MATCHER) {
				sqliteDatabase.insert(DbConsts.DOWNLOADS_TABLE, null, values);
			}
			if (uriMatcher.match(uri) == MMUST_EXAMS_URI_MATCHER) {
				sqliteDatabase.insert(DbConsts.EXAMS_TABLE, null, values);
			}
			if (uriMatcher.match(uri) == MMUST_FACULTY_URI_MATCHER) {
				sqliteDatabase.insert(DbConsts.FACULTY_TABLE, null, values);
			}
			if (uriMatcher.match(uri) == MMUST_LEC_INFO_URI_MATCHER) {
				sqliteDatabase.insert(DbConsts.LECS_TABLE, null, values);
			}
			if (uriMatcher.match(uri) == MMUST_NOTIFICATIONS_URI_MATCHER) {
				sqliteDatabase.insert(DbConsts.NOTIFICATION_TABLE, null, values);
			}
			if (uriMatcher.match(uri) == MMUST_SCHOOL_COURSES_URI_MATCHER) {
				sqliteDatabase.insert(DbConsts.SCHOOL_COURSES_TABLE, null, values);
			}
			if (uriMatcher.match(uri) == MMUST_STUDENT_INFO_URI_MATCHER) {
				sqliteDatabase.insert(DbConsts.STUDENT_INFO_TABLE, null, values);
			}
			if (uriMatcher.match(uri) == MMUST_UPLOADS_URI_MATCHER) {
				sqliteDatabase.insert(DbConsts.UPLOADS_TABLE, null, values);
			}
			Log.i("Inserting ", uri.toString());
		}
		return uri;
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection,
			String[] selectionArgs, String sortOrder) {
		// TODO Auto-generated method stub
		
		if (getReadableDatabase()) {
			if (uriMatcher.match(uri) == MMUST_ASSIGNMENTS_URI_MATCHER) {
				return sqliteDatabase.query(DbConsts.ASSIGNMENTS_TABLE, projection, selection, selectionArgs, null, null, sortOrder);
			}
			if (uriMatcher.match(uri) == MMUST_CAMPUS_URI_MATCHER) {
				return sqliteDatabase.query(DbConsts.CAMPUS_TABLE, projection, selection, selectionArgs, null, null, sortOrder);
			}
			if (uriMatcher.match(uri) == MMUST_CLASSES_URI_MATCHER) {
				return sqliteDatabase.query(DbConsts.CLASSES_TABLE, projection, selection, selectionArgs, null, null, sortOrder);
			}
			if (uriMatcher.match(uri) == MMUST_COMMITS_INFO_URI_MATCHER) {
				return sqliteDatabase.query(DbConsts.COMMITS_INFO_TABLE, projection, selection, selectionArgs, null, null, sortOrder);
			}
			if (uriMatcher.match(uri) == MMUST_COURSES_URI_MATCHER) {
				return sqliteDatabase.query(DbConsts.COURSES_TABLE, projection, selection, selectionArgs, null, null, sortOrder);
			}
			if (uriMatcher.match(uri) == MMUST_DEPARTMENT_URI_MATCHER) {
				return sqliteDatabase.query(DbConsts.DEPARTMENT_TABLE, projection, selection, selectionArgs, null, null, sortOrder);
			}
			if (uriMatcher.match(uri) == MMUST_DOWNLOADS_URI_MATCHER) {
				return sqliteDatabase.query(DbConsts.DOWNLOADS_TABLE, projection, selection, selectionArgs, null, null, sortOrder);
			}
			if (uriMatcher.match(uri) == MMUST_EXAMS_URI_MATCHER) {
				return sqliteDatabase.query(DbConsts.EXAMS_TABLE, projection, selection, selectionArgs, null, null, sortOrder);
			}
			if (uriMatcher.match(uri) == MMUST_FACULTY_URI_MATCHER) {
				return sqliteDatabase.query(DbConsts.FACULTY_TABLE, projection, selection, selectionArgs, null, null, sortOrder);
			}
			if (uriMatcher.match(uri) == MMUST_LEC_INFO_URI_MATCHER) {
				return sqliteDatabase.query(DbConsts.LECS_TABLE, projection, selection, selectionArgs, null, null, sortOrder);
			}
			if (uriMatcher.match(uri) == MMUST_NOTIFICATIONS_URI_MATCHER) {
				return sqliteDatabase.query(DbConsts.NOTIFICATION_TABLE, projection, selection, selectionArgs, null, null, sortOrder);
			}
			if (uriMatcher.match(uri) == MMUST_SCHOOL_COURSES_URI_MATCHER) {
				return sqliteDatabase.query(DbConsts.SCHOOL_COURSES_TABLE, projection, selection, selectionArgs, null, null, sortOrder);
			}
			if (uriMatcher.match(uri) == MMUST_STUDENT_INFO_URI_MATCHER) {
				return sqliteDatabase.query(DbConsts.STUDENT_INFO_TABLE, projection, selection, selectionArgs, null, null, sortOrder);
			}
			if (uriMatcher.match(uri) == MMUST_UPLOADS_URI_MATCHER) {
				return sqliteDatabase.query(DbConsts.UPLOADS_TABLE, projection, selection, selectionArgs, null, null, sortOrder);
			}
			Log.i("Querying ", uri.toString());
		}
		return null;
	}

	@Override
	public int update(Uri uri, ContentValues values, String selection,
			String[] selectionArgs) {
		// TODO Auto-generated method stub

		if (getWritableDatabase()) {

			if (uriMatcher.match(uri) == MMUST_ASSIGNMENTS_URI_MATCHER) {
				sqliteDatabase.update(DbConsts.ASSIGNMENTS_TABLE, values, selection, selectionArgs);
			}
			if (uriMatcher.match(uri) == MMUST_CAMPUS_URI_MATCHER) {
				sqliteDatabase.update(DbConsts.CAMPUS_TABLE, values, selection, selectionArgs);
			}
			if (uriMatcher.match(uri) == MMUST_CLASSES_URI_MATCHER) {
				sqliteDatabase.update(DbConsts.CLASSES_TABLE, values, selection, selectionArgs);
			}
			if (uriMatcher.match(uri) == MMUST_COMMITS_INFO_URI_MATCHER) {
				sqliteDatabase.update(DbConsts.COMMITS_INFO_TABLE, values, selection, selectionArgs);
			}
			if (uriMatcher.match(uri) == MMUST_COURSES_URI_MATCHER) {
				sqliteDatabase.update(DbConsts.COURSES_TABLE, values, selection, selectionArgs);
			}
			if (uriMatcher.match(uri) == MMUST_DEPARTMENT_URI_MATCHER) {
				sqliteDatabase.update(DbConsts.DEPARTMENT_TABLE, values, selection, selectionArgs);
			}
			if (uriMatcher.match(uri) == MMUST_DOWNLOADS_URI_MATCHER) {
				sqliteDatabase.update(DbConsts.DOWNLOADS_TABLE, values, selection, selectionArgs);
			}
			if (uriMatcher.match(uri) == MMUST_EXAMS_URI_MATCHER) {
				sqliteDatabase.update(DbConsts.EXAMS_TABLE, values, selection, selectionArgs);
			}
			if (uriMatcher.match(uri) == MMUST_FACULTY_URI_MATCHER) {
				sqliteDatabase.update(DbConsts.FACULTY_TABLE, values, selection, selectionArgs);
			}
			if (uriMatcher.match(uri) == MMUST_LEC_INFO_URI_MATCHER) {
				sqliteDatabase.update(DbConsts.LECS_TABLE, values, selection, selectionArgs);
			}
			if (uriMatcher.match(uri) == MMUST_NOTIFICATIONS_URI_MATCHER) {
				sqliteDatabase.update(DbConsts.NOTIFICATION_TABLE, values, selection, selectionArgs);
			}
			if (uriMatcher.match(uri) == MMUST_SCHOOL_COURSES_URI_MATCHER) {
				sqliteDatabase.update(DbConsts.SCHOOL_COURSES_TABLE, values, selection, selectionArgs);
			}
			if (uriMatcher.match(uri) == MMUST_STUDENT_INFO_URI_MATCHER) {
				sqliteDatabase.update(DbConsts.STUDENT_INFO_TABLE, values, selection, selectionArgs);
			}
			if (uriMatcher.match(uri) == MMUST_UPLOADS_URI_MATCHER) {
				sqliteDatabase.update(DbConsts.UPLOADS_TABLE, values, selection, selectionArgs);
			}
			Log.i("Updating ", uri.toString());
		}
		return 0;
	}

	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		// TODO Auto-generated method stub
	
		if (getWritableDatabase()) {

			if (uriMatcher.match(uri) == MMUST_ASSIGNMENTS_URI_MATCHER) {
				sqliteDatabase.delete(DbConsts.ASSIGNMENTS_TABLE, selection,selectionArgs);
			}
			if (uriMatcher.match(uri) == MMUST_CAMPUS_URI_MATCHER) {
				sqliteDatabase.delete(DbConsts.CAMPUS_TABLE, selection,selectionArgs);
			}
			if (uriMatcher.match(uri) == MMUST_CLASSES_URI_MATCHER) {
				sqliteDatabase.delete(DbConsts.CLASSES_TABLE, selection,selectionArgs);
			}
			if (uriMatcher.match(uri) == MMUST_COMMITS_INFO_URI_MATCHER) {
				sqliteDatabase.delete(DbConsts.COMMITS_INFO_TABLE, selection,selectionArgs);
			}
			if (uriMatcher.match(uri) == MMUST_COURSES_URI_MATCHER) {
				sqliteDatabase.delete(DbConsts.COURSES_TABLE, selection,selectionArgs);
			}
			if (uriMatcher.match(uri) == MMUST_DEPARTMENT_URI_MATCHER) {
				sqliteDatabase.delete(DbConsts.DEPARTMENT_TABLE, selection,selectionArgs);
			}
			if (uriMatcher.match(uri) == MMUST_DOWNLOADS_URI_MATCHER) {
				sqliteDatabase.delete(DbConsts.DOWNLOADS_TABLE, selection,selectionArgs);
			}
			if (uriMatcher.match(uri) == MMUST_EXAMS_URI_MATCHER) {
				sqliteDatabase.delete(DbConsts.EXAMS_TABLE, selection,selectionArgs);
			}
			if (uriMatcher.match(uri) == MMUST_FACULTY_URI_MATCHER) {
				sqliteDatabase.delete(DbConsts.FACULTY_TABLE, selection,selectionArgs);
			}
			if (uriMatcher.match(uri) == MMUST_LEC_INFO_URI_MATCHER) {
				sqliteDatabase.delete(DbConsts.LECS_TABLE, selection,selectionArgs);
			}
			if (uriMatcher.match(uri) == MMUST_NOTIFICATIONS_URI_MATCHER) {
				sqliteDatabase.delete(DbConsts.NOTIFICATION_TABLE, selection,selectionArgs);
			}
			if (uriMatcher.match(uri) == MMUST_SCHOOL_COURSES_URI_MATCHER) {
				sqliteDatabase.delete(DbConsts.SCHOOL_COURSES_TABLE, selection,selectionArgs);
			}
			if (uriMatcher.match(uri) == MMUST_STUDENT_INFO_URI_MATCHER) {
				sqliteDatabase.delete(DbConsts.STUDENT_INFO_TABLE, selection,selectionArgs);
			}
			if (uriMatcher.match(uri) == MMUST_UPLOADS_URI_MATCHER) {
				sqliteDatabase.delete(DbConsts.UPLOADS_TABLE, selection,selectionArgs);
			}
			Log.i("Deleting ", uri.toString());
		}
		return 0;
	}

	private boolean getReadableDatabase() {
		if(database==null){
			createDatabase();
		}
		sqliteDatabase = database.getReadableDatabase();
		return true;
	}

	private boolean getWritableDatabase() {
		if(database==null){
			createDatabase();
		}
		sqliteDatabase = database.getWritableDatabase();
		return true;
	}

	private boolean createDatabase() {
		database = new Database(getContext());
		return true;
	}
}
