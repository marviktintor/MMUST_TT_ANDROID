package com.marvik.apps.mmust.utils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.net.Uri;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.widget.EditText;
import android.widget.RemoteViews;
import android.widget.TextView;
import android.widget.Toast;

import com.marvik.apps.mmust.R;
import com.marvik.apps.mmust.activities.Authenticate;
import com.marvik.apps.mmust.activities.StudentInfo;
import com.marvik.apps.mmust.database.Database;
import com.marvik.apps.mmust.database.DbConsts;
import com.marvik.apps.mmust.prefs.PrefsHandler;
import com.marvik.apps.mmust.provider.DataProvider;

public class MmustUtils {

	PrefsHandler prefsHandler;
	
	Context context;

	String TAG = "MMUSTUTILS";
	
	JSONObject jsonObject;
	
	public MmustUtils(Context context) {
		this.context = context;
		prefsHandler = new PrefsHandler(context);
	}
	
	public Context getContext(){
		return context;
	}
	public void onFirstRun(){
		insertDummyData();
		addStudentAccount("SIT/0050/12","vmwenda.vm@gmail.com");
	}
	private void addStudentAccount(String reg_no,String password) {
		// TODO Auto-generated method stub
		String ACCOUNT_TYPE="student.mmust";
		String ACCOUNT_NAME="MmustStudentTimetable";
		
		
		AccountManager accountManager = (AccountManager)context.getSystemService(Context.ACCOUNT_SERVICE);
		Account account = new Account(ACCOUNT_NAME, ACCOUNT_TYPE);
		Bundle userdata = new Bundle();
		userdata.putString(StudentAuthenticator.STUDENT_REG_NO, reg_no);
		userdata.putString(StudentAuthenticator.STUDENT_PASSWORD, reg_no);
		accountManager.addAccountExplicitly(account, password, userdata);
		
	}
	public boolean accountExists(){
		AccountManager manager = (AccountManager)context.getSystemService(Context.ACCOUNT_SERVICE);
		Account [] accounts = manager.getAccountsByType("student.mmust");
		return accounts.length>0;
	}
	public void forceSync(){
		
		if(accountExists()){
			String ACCOUNT_TYPE="student.mmust";
			String ACCOUNT_NAME="MmustStudentTimetable";
			String AUTHORITY = "com.marvik.apps.mmust.provider.DataProvider";
			
			Account account = new Account(ACCOUNT_NAME, ACCOUNT_TYPE);
			
			Bundle extras = new Bundle();
			extras.putBoolean(ContentResolver.SYNC_EXTRAS_MANUAL, true);//Force a manual sync
			extras.putBoolean(ContentResolver.SYNC_EXTRAS_EXPEDITED, true);//Make Sync Start Immediatley
			ContentResolver.requestSync(account, AUTHORITY, extras);
		} else {
			toastCustom("No Student account exists ", Toast.LENGTH_SHORT,Color.RED, 24f);
			broadcast(StudentAuthenticator.ACTION_NO_STUDENT_ACCOUNT_FOUND);
			startActivity(Authenticate.class, Intent.FLAG_ACTIVITY_NO_HISTORY);
		}
	}
	
	public void stopSync() {
		// TODO Auto-generated method stub
		
		String ACCOUNT_TYPE="student.mmust";
		String ACCOUNT_NAME="MmustStudentTimetable";
		String AUTHORITY = "com.marvik.apps.mmust.provider.DataProvider";
		
		Account account = new Account(ACCOUNT_NAME, ACCOUNT_TYPE);
		
		Bundle extras = new Bundle();
		extras.putBoolean(ContentResolver.SYNC_EXTRAS_MANUAL, true);//Force a manual sync
		extras.putBoolean(ContentResolver.SYNC_EXTRAS_EXPEDITED, true);//Make Sync Start Immediatley
		ContentResolver.cancelSync(account, AUTHORITY);
		
		prefsHandler.setSyncing(false);
		broadcast(StudentAuthenticator.ACTION_SYNC_CANCELLED);
	};
	/**
	 * Insert the specified data into the specified table according to the uri
	 */
	public void insertIntoTable(String table,ContentValues values){
		Database database = new Database(getContext());
		SQLiteDatabase sql = database.getWritableDatabase();
		sql.insert(table,null, values);
		sql.close();
		database.close();
	}
	public void insertValuesIntoTable(Uri url,ContentValues values){

		context.getContentResolver().insert(url, values);
	}
	public void showSuccessToast(String text, int duration) {
		// TODO Auto-generated method stub
		Toast toast = new Toast(context);
		TextView view = new TextView(context);
		view.setBackgroundColor(Color.TRANSPARENT);
		view.setTextColor(Color.GREEN);
		view.setText(text);
		toast.setView(view);
		toast.setDuration(duration);
		toast.show();
	}

	public boolean turnWifiOn() {
		WifiManager wifiManager = (WifiManager)context.getSystemService(Context.WIFI_SERVICE);
		if(!wifiManager.isWifiEnabled())
		wifiManager.setWifiEnabled(true);
		return wifiManager.isWifiEnabled();
	}
	public boolean saveWifiNetwork(String wifiSSID,String wifiPassword) {
		WifiManager wifiManager = (WifiManager)context.getSystemService(Context.WIFI_SERVICE);
		if(turnWifiOn()){
			addWifiNetwork(wifiManager,wifiSSID,wifiPassword);
			
			List<WifiConfiguration>wifiConfigs = wifiManager.getConfiguredNetworks();
			for(WifiConfiguration wifiConfiguration : wifiConfigs){
				if(wifiConfiguration.SSID!=null&&wifiConfiguration.SSID.equals("\""+wifiSSID+"\"")){
					connectToWifiNetwork(wifiSSID,wifiPassword);
					return true;
				}
			}
		}return false;		
	}
	private void addWifiNetwork(WifiManager wifiManager,String wifiSSID,String wifiPassword) {
		// TODO Auto-generated method stub
		wifiManager.disconnect();//Disconnect from any connected network
		WifiConfiguration config = new WifiConfiguration();
		config.SSID="\""+wifiSSID+"\"";
		config.preSharedKey="\""+wifiPassword+"\"";
		config.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.WPA_PSK);
		wifiManager.addNetwork(config);
		wifiManager.saveConfiguration();
	}

	public boolean connectToWifiNetwork(String wifiSSID,String wifiPassword) {
		WifiManager wifiManager = (WifiManager)context.getSystemService(Context.WIFI_SERVICE);
		
		List<WifiConfiguration>wifiConfigs = wifiManager.getConfiguredNetworks();
		
		for(WifiConfiguration wifiConfiguration : wifiConfigs){
			if(wifiConfiguration.SSID!=null&&wifiConfiguration.SSID.equals("\""+wifiSSID+"\"")){
				wifiManager.disconnect();//Disconnect from any connected network
				wifiManager.enableNetwork(wifiConfiguration.networkId, true);
				wifiManager.reconnect();
			}
		}
	
		return wifiManager.getConnectionInfo().getSSID().equalsIgnoreCase(wifiSSID);
		
	}

	public String getFileSize(double fileSize) {
		// TODO Auto-generated method stub
		
		if(fileSize>1048576)
			return String.format("%.2f", fileSize/1048576) +" MB";
		if(fileSize==1048576)
			return String.format("%d", 1) +" MB";
		
		if(fileSize>1024)
			return String.format("%.2f", fileSize/1024) +" Kb";
		
		if(fileSize==1024)
			return String.format("%d", 1) +" Kb";
		
		if(fileSize<1024)
			return String.format("%.2f", fileSize) +" Bytes";

		return "Unknown";
	}

	public String getFormattedTime(long uploadTime) {
		// TODO Auto-generated method stub
		return new SimpleDateFormat("hh:mm:ss dd-MM-yy",Locale.getDefault()).format(new Date(uploadTime)).toString();
	}

	public CharSequence formatDownloadStatus(int downloadStatus) {
		// TODO Auto-generated method stub
		/** 1 - Downloaded, 0 - Not Downloaded , 2 - Downloading, -1 Unknown */
		switch(downloadStatus){
		case -1: 
			return "Unknown";
		case 0:
			return "Please download";
		case 1:
			return "Downloaded";
		case 2:
			return "Downloading";
			
		}
		return "Status unknown";
	}
	public void startActivity(Class<?>cls){
		if (prefsHandler.isKeepBackStack()) {
			
			context.startActivity(new Intent(context, cls).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
		}else{
			context.startActivity(new Intent(context, cls).setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY));
			
		}
	}
	private void broadcast(String action, Bundle extras) {
		// TODO Auto-generated method stub
		context.sendBroadcast(new Intent(action).putExtras(extras));
	}
	public void broadcast(String action) {
		context.sendBroadcast(new Intent(action));
	}
	public void startActivity(Class<?> cls, Bundle extras) {
		// TODO Auto-generated method stub
if (prefsHandler.isKeepBackStack()) {
			
			context.startActivity(new Intent(context, cls).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP).putExtras(extras));
		}else{
			context.startActivity(new Intent(context, cls).setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY).putExtras(extras));
		}
	}
	
	public void startActivity(Class<?> cls, int flags) {
		// TODO Auto-generated method stub
if (prefsHandler.isKeepBackStack()) {
			
			context.startActivity(new Intent(context, cls).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP).setFlags(flags));
		}else{
			context.startActivity(new Intent(context, cls).setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY).setFlags(flags));
		}
	}
	public void startActivity(Class<?> cls, Bundle extras,int flags) {
		// TODO Auto-generated method stub
if (prefsHandler.isKeepBackStack()) {
			
			context.startActivity(new Intent(context, cls).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP).putExtras(extras).setFlags(flags));
		}else{
			context.startActivity(new Intent(context, cls).setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY).putExtras(extras).setFlags(flags));
		}
	}

	public void showNotificationBarShortcuts(int notificationBarShortcutsId) {
		// TODO Auto-generated method stub
		NotificationManager nm = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
		Notification n = new Notification(R.drawable.ic_launcher,"Mmust student timetable",System.currentTimeMillis());
		n.flags=Notification.FLAG_NO_CLEAR;
		RemoteViews views = new RemoteViews("com.marvik.apps.mmust", R.layout.notification_icons);
		n.contentView = views;
		nm.notify(notificationBarShortcutsId, n);
	
	}
	public void cancelNotificationBarShortcuts(int notificationBarShortcutsId) {
		// TODO Auto-generated method stub
		NotificationManager nm = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
		nm.cancel(notificationBarShortcutsId);
		
	}
	public void insertDummyData(){
		insertAssignments(null);
		insertCampus(null);
		insertClasses(null);
		insertCommits(null);
		insertCourses(null);
		insertDepartments(null);
		insertDownloads(null);
		insertExams(null);
		insertFaculty(null);
		insertLecInfo(null);
		insertNotifications(null);
		insertSchoolCourse(null);
		insertStudentsInfo(null);//EDITED
		insertUploads(null);
	}
	private void dropTableData(Uri url,String where,String[] selectionArgs) {
		// TODO Auto-generated method stub
		context.getContentResolver().delete(url, where, selectionArgs);
	}
	
	public void syncLatestCommits(JSONObject commitsInfo) {
		// TODO Auto-generated method stub
		 try {
			
			
			
			int iCommits=commitsInfo.getInt("commits");
			int iAssignments=commitsInfo.getInt("assignments");
			int iCampus=commitsInfo.getInt("campus");
			int iClasses=commitsInfo.getInt("classes");
			int iCourses=commitsInfo.getInt("courses");
			int iDepartments=commitsInfo.getInt("departments");
			int iDownloads=commitsInfo.getInt("downloads");
			int iExams=commitsInfo.getInt("exams");
			int iFaculty=commitsInfo.getInt("faculty");
			int iLecturer=commitsInfo.getInt("lecturer");
			int iNotifications=commitsInfo.getInt("notifications");
			int iSchool_courses=commitsInfo.getInt("school_courses");
			int iStudents=commitsInfo.getInt("students");
			int iUploads=commitsInfo.getInt("uploads");
			

			
			Cursor cursor = context.getContentResolver().query(DataProvider.MMUST_COMMITS_INFO_CONTENT_URI, null, null, null, null);
			
			int oCommits=0;
			int oAssignments=0;
			int oCampus=0;
			int oClasses=0;
			int oCourses=0;
			int oDepartments=0;
			int oDownloads=0;
			int oExams=0;
			int oFaculty=0;
			int oLecturer=0;
			int oNotifications=0;
			int oSchool_courses=0;
			int oStudents=0;
			int oUploads=0;
			
			for(cursor.moveToFirst();!cursor.isAfterLast();cursor.moveToNext()){
				
				oCommits = cursor.getInt(cursor.getColumnIndex(DbConsts.COMMITS_INFO_COMMITS));
				oAssignments = cursor.getInt(cursor.getColumnIndex(DbConsts.COMMITS_INFO_ASSIGNMENTS));
				oCampus = cursor.getInt(cursor.getColumnIndex(DbConsts.COMMITS_INFO_CAMPUS));
				oClasses = cursor.getInt(cursor.getColumnIndex(DbConsts.COMMITS_INFO_CLASSES));
				oCourses = cursor.getInt(cursor.getColumnIndex(DbConsts.COMMITS_INFO_COURSES));
				oDepartments = cursor.getInt(cursor.getColumnIndex(DbConsts.COMMITS_INFO_DEPARTMENTS));
				oDownloads = cursor.getInt(cursor.getColumnIndex(DbConsts.COMMITS_INFO_DOWNLOADS));
				oExams = cursor.getInt(cursor.getColumnIndex(DbConsts.COMMITS_INFO_EXAMS));
				oFaculty = cursor.getInt(cursor.getColumnIndex(DbConsts.COMMITS_INFO_FACULTY));
				oLecturer = cursor.getInt(cursor.getColumnIndex(DbConsts.COMMITS_INFO_LECTURER));
				oNotifications = cursor.getInt(cursor.getColumnIndex(DbConsts.COMMITS_INFO_NOTIFICATIONS));
				oSchool_courses = cursor.getInt(cursor.getColumnIndex(DbConsts.COMMITS_INFO_SCHOOL_COURSES));
				oStudents = cursor.getInt(cursor.getColumnIndex(DbConsts.COMMITS_INFO_STUDENTS));
				oUploads = cursor.getInt(cursor.getColumnIndex(DbConsts.COMMITS_INFO_UPLOADS));
			
			
			}
			// ALL COMMITS
			
			Toast.makeText(context, "iCommits == "+iCommits+" -> oCommits == "+oCommits, Toast.LENGTH_SHORT).show();
			
			if (iCommits >= oCommits) {
				Log.i("ALL COMMITS", "NOT UP TO DATE");

				// ASSIGNMENTS COMMITS
				if (iAssignments > oAssignments) {
					Log.i("ASSIGNMENTS COMMITS", "NOT UP TO DATE");
					getJSONForURL(StudentAuthenticator.EXTRA_URL_READ_ASSESSMENTS_TYPE_ASSIGNMENTS, false);
				} else {
					if (iAssignments == oAssignments) {
						Log.i("ASSIGNMENTS COMMITS", "UP TO DATE");
					}
				}

				// CAMPUS COMMITS
				if (iCampus > oCampus) {
					Log.i("CAMPUS COMMITS", "NOT UP TO DATE");
				} else {
					if (iCampus == oCampus) {
						Log.i("CAMPUS COMMITS", "UP TO DATE");
					}
				}

				// CLASSES COMMITS
				if (iClasses > oClasses) {
					Log.i("CLASSES COMMITS", "NOT UP TO DATE");
					getJSONForURL(StudentAuthenticator.EXTRA_URL_READ_CLASSES, false);
				} else {
					if (iClasses == oClasses) {
						Log.i("CLASSES COMMITS", "UP TO DATE");
					}
				}

				// COURSES COMMITS
				if (iCourses > oCourses) {
					Log.i("COURSES COMMITS", "NOT UP TO DATE");
					getJSONForURL(StudentAuthenticator.EXTRA_URL_READ_COURSES, false);
				} else {
					if (iCourses == oCourses) {
						Log.i("COURSES COMMITS", "UP TO DATE");
					}
				}

				// DEPARTMENTS COMMITS
				if (iDepartments > oDepartments) {
					Log.i("DEPARTMENTS COMMITS", "NOT UP TO DATE");
				} else {
					if (iDepartments == oDepartments) {
						Log.i("DEPARTMENTS COMMITS", "UP TO DATE");
					}
				}

				// DOWNLOADS COMMITS
				if (iDownloads > oDownloads) {
					Log.i("DOWNLOADS COMMITS", "NOT UP TO DATE");
					getJSONForURL(StudentAuthenticator.EXTRA_URL_READ_DOWNLOADS_AND_UPLOADS, false);
				} else {
					if (iDownloads == oDownloads) {
						Log.i("DOWNLOADS COMMITS", "UP TO DATE");
					}
				}

				// EXAMS COMMITS
				if (iExams > oExams) {
					Log.i("EXAMS COMMITS", "NOT UP TO DATE");
					getJSONForURL(StudentAuthenticator.EXTRA_URL_READ_ASSESSMENTS_TYPE_EXAMS, false);
					getJSONForURL(StudentAuthenticator.EXTRA_URL_READ_ASSESSMENTS_TYPE_CATS, false);
				} else {
					if (iExams == oExams) {
						Log.i("EXAMS COMMITS", "UP TO DATE");
					}
				}

				// FACULTY COMMITS
				if (iFaculty > oFaculty) {
					Log.i("FACULTY COMMITS", "NOT UP TO DATE");
					
				} else {
					if (iFaculty == oFaculty) {
						Log.i("FACULTY COMMITS", "UP TO DATE");
					}
				}

				// LECTURER COMMITS
				if (iLecturer > oLecturer) {
					Log.i("LECTURER COMMITS", "NOT UP TO DATE");
					getJSONForURL(StudentAuthenticator.EXTRA_URL_READ_LECTURER, false);
				} else {
					if (iLecturer == oLecturer) {
						Log.i("LECTURER COMMITS", "UP TO DATE");
					}
				}

				// NOTIFICATION COMMITS
				if (iNotifications > oNotifications) {
					Log.i("NOTIFICATION COMMITS", "NOT UP TO DATE");
					getJSONForURL(StudentAuthenticator.EXTRA_URL_READ_NOTIFICATIONS, false);
				} else {
					if (iNotifications == oNotifications) {
						Log.i("NOTIFICATION COMMITS", "UP TO DATE");
					}
				}

				// SCHOOL COURSES COMMITS
				if (iSchool_courses > oSchool_courses) {
					Log.i("SCHOOL COURSES COMMITS", "NOT UP TO DATE");
					getJSONForURL(StudentAuthenticator.EXTRA_URL_ADVANCED_SCHOOL_INFO, false);
				} else {
					if (iSchool_courses == oSchool_courses) {
						Log.i("SCHOOL COURSES COMMITS", "UP TO DATE");
					}
				}

				// STUDENT COMMITS
				if (iStudents > oStudents) {
					Log.i("STUDENT COMMITS", "NOT UP TO DATE");
					
				} else {
					if (iStudents == oStudents) {
						Log.i("STUDENT COMMITS", "UP TO DATE");
					}
				}

				// UPLOADS COMMITS
				if (iUploads > oUploads) {
					Log.i("UPLOADS COMMITS", "NOT UP TO DATE");
					getJSONForURL(StudentAuthenticator.EXTRA_URL_READ_DOWNLOADS_AND_UPLOADS, false);
				} else {
					if (iUploads == oUploads) {
						Log.i("UPLOADS COMMITS", "UP TO DATE");
					}
				}
				//Save the commits info to preferences
				prefsHandler.saveCommitsUpdates(commitsInfo);
				
			} else {
				if (iCommits == oCommits) {
					Log.i("ALL COMMITS", "UP TO DATE");
					Toast.makeText(context, "UP TO DATE", Toast.LENGTH_SHORT).show();
				}
			}
					
			prefsHandler.saveCommitsUpdates(commitsInfo);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
			Toast.makeText(context, "CATCH", Toast.LENGTH_SHORT).show();
		}
	}

	
	public void insertAssignments(ContentValues values) {
		insertValuesIntoTable(DataProvider.MMUST_ASSIGNMENTS_CONTENT_URI, values);
		showSuccessToast("Assignments added successfully",Toast.LENGTH_SHORT);
	}

	public void insertCampus(ContentValues values){
		insertValuesIntoTable(DataProvider.MMUST_CAMPUS_CONTENT_URI, values);
		showSuccessToast("Campus added successfully",Toast.LENGTH_SHORT);
		
	}
	
	public void insertClasses(ContentValues values) {
	
		insertValuesIntoTable(DataProvider.MMUST_CLASSES_CONTENT_URI, values);
		showSuccessToast("Classes added successfully", Toast.LENGTH_SHORT);
	}
	public void insertCommits(ContentValues values){
		
		insertValuesIntoTable(DataProvider.MMUST_COMMITS_INFO_CONTENT_URI, values);
		showSuccessToast("Commits added successfully", Toast.LENGTH_SHORT);
	}

	public void insertCourses(ContentValues values) {

		insertValuesIntoTable(DataProvider.MMUST_COURSES_CONTENT_URI, values);
		showSuccessToast("Courses added successfully", Toast.LENGTH_SHORT);
	}

	public void insertDepartments(ContentValues values){		
		insertValuesIntoTable(DataProvider.MMUST_DEPARTMENT_CONTENT_URI, values);
		showSuccessToast("Departments added successfully",Toast.LENGTH_SHORT);
	}
	public void insertDownloads(ContentValues values){
		
		insertValuesIntoTable(DataProvider.MMUST_DOWNLOADS_CONTENT_URI, values);
		showSuccessToast("Downloads added successfully",Toast.LENGTH_SHORT);
	}
	public void insertExams(ContentValues values) {
		insertValuesIntoTable(DataProvider.MMUST_EXAMS_CONTENT_URI, values);
		showSuccessToast("Exams added successfully",Toast.LENGTH_SHORT);
	}

	public void insertFaculty(ContentValues values){		
		insertValuesIntoTable(DataProvider.MMUST_FACULTY_CONTENT_URI, values);
		showSuccessToast("Faculty added successfully",Toast.LENGTH_SHORT);
	}
	
	public void insertLecInfo(ContentValues values) {
		insertValuesIntoTable(DataProvider.MMUST_LEC_INFO_CONTENT_URI, values);
		showSuccessToast("Lecturer info added successfully",Toast.LENGTH_SHORT);
	}
	public void insertNotifications(ContentValues values) {
		insertValuesIntoTable(DataProvider.MMUST_NOTIFICATIONS_CONTENT_URI, values);
		showSuccessToast("Notifications added successfully",Toast.LENGTH_SHORT);
	}

	public void insertSchoolCourse(ContentValues values) {
		insertValuesIntoTable(DataProvider.MMUST_SCHOOL_COURSES_CONTENT_URI, values);
		showSuccessToast("School course added successfully",Toast.LENGTH_SHORT);
	}
	
	public void insertStudentsInfo(ContentValues values){
		
		dropTableData(DataProvider.MMUST_STUDENT_INFO_CONTENT_URI,null,null);
		insertValuesIntoTable(DataProvider.MMUST_STUDENT_INFO_CONTENT_URI, values);
		showSuccessToast("Student added successfully",Toast.LENGTH_SHORT);
	}
	
	

	public void insertUploads(ContentValues values) {
		insertValuesIntoTable(DataProvider.MMUST_UPLOADS_CONTENT_URI, values);
		showSuccessToast("Uploads added successfully", Toast.LENGTH_SHORT);
	}

	public String getString(EditText editText) {
		// TODO Auto-generated method stub
		return editText.getText().toString();
	}

	public void toastCustom(String text,int length,int color) {
		// TODO Auto-generated method stub
		Toast t = new Toast(context);
		t.setDuration(length);
		TextView view = new TextView(context);
		view.setText(text);
		view.setBackgroundColor(Color.TRANSPARENT);
		view.setTextColor(color);
		t.setView(view);
		t.show();
	}
	public void toastCustom(String text,int length,int color,float textSize) {
		// TODO Auto-generated method stub
		Toast t = new Toast(context);
		t.setDuration(length);
		TextView view = new TextView(context);
		view.setText(text);
		view.setTextSize(textSize);
		view.setBackgroundColor(Color.TRANSPARENT);
		view.setTextColor(color);
		t.setView(view);
		t.show();
	}

	public void authStudent(String reg_no, String password) {
		// TODO Auto-generated method stub
		addStudentAccount(reg_no,password);
		
		setStudentAccount(reg_no,password);
	}

	private void setStudentAccount(final String reg_no,final String password) {
		// TODO Auto-generated method stub
		new AsyncTask<Void, Void, Integer>() {
			protected void onPreExecute() {
			};

			@Override
			protected Integer doInBackground(Void... params) {
				// TODO Auto-generated method stub
				
				try{
				String login_url="http://192.168.43.47/MMUST_TT/login.php";
				HttpClient client = new DefaultHttpClient();
				HttpPost post = new HttpPost(prefsHandler.isDeveloperMode()?"http://"+prefsHandler.getHostAddress()+"/MMUST_TT/login.php":login_url);
				List<NameValuePair>formParams = new ArrayList<NameValuePair>();
				formParams.add(new BasicNameValuePair(StudentAuthenticator.LOGIN_POST_LOGIN_STUDENT, "login_student"));
				formParams.add(new BasicNameValuePair(StudentAuthenticator.LOGIN_POST_REG_NO, reg_no));
				formParams.add(new BasicNameValuePair(StudentAuthenticator.LOGIN_POST_PASSWORD, password));
				post.setEntity(new UrlEncodedFormEntity(formParams));
				
				HttpResponse response = client.execute(post);
				StatusLine statusLine = response.getStatusLine();
				int statusCode = statusLine.getStatusCode();
				if(statusCode==200){
					HttpEntity entity = response.getEntity();
					String loginResponse = EntityUtils.toString(entity);
					Log.i("RESPONSE", loginResponse);
					return Integer.parseInt(response.equals("")?"0":loginResponse);
				}else{
					broadcast(StudentAuthenticator.ACTION_SYNC_ERROR);
				}
				}catch(IOException e){
					broadcast(StudentAuthenticator.ACTION_SYNC_ERROR);
				}
				catch(Exception e){
					Log.i("LOGIN_FAILED",e.toString());
				}
				return 0;
			}
			protected void onPostExecute(Integer result) {
				switch(result){
				case 0:
					broadcast(StudentAuthenticator.ACTION_STUDENT_LOGIN_FAILED);
					toastCustom("Could not log in, Please contact the admin",Toast.LENGTH_LONG,Color.RED);
					break;
				case 1:
					prefsHandler.setFirstrun(false);
					broadcast(StudentAuthenticator.ACTION_STUDENT_LOGIN_SUCCESSFUL);
					prefsHandler.setStudentRegistrationNo(reg_no);
					prefsHandler.setStudentPassword(password);
					showAccountSetupNotification();
					getBasicStudentInfo(reg_no,StudentAuthenticator.EXTRA_URL_BASIC_STUDENT_INFO);
					break;
				}
			}

			
		}.execute();
			
	}

	
	
	private void showAccountSetupNotification() {
		// TODO Auto-generated method stub
		Notification notification = new Notification(R.drawable.ic_launcher, "Student logged in successfully", System.currentTimeMillis());
		PendingIntent contentIntent = PendingIntent.getActivity(context, 0, new Intent(context, StudentInfo.class), PendingIntent.FLAG_UPDATE_CURRENT);
		notification.setLatestEventInfo(context, "MMUST Student Timetable", "Your account is being set up in a short while", contentIntent );
		notification.flags=Notification.FLAG_AUTO_CANCEL;
		notification.defaults=Notification.DEFAULT_ALL;
		NotificationManager manager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
		manager.notify(10, notification);
	}
	
	protected void getBasicStudentInfo(String reg_no, String extraUrlBasicStudentInfo) {
		// TODO Auto-generated method stub
		getJSONForURL(extraUrlBasicStudentInfo,false);
		getJSONForURL(StudentAuthenticator.EXTRA_URL_COMMITS_INFO, true);
	}

	public JSONObject getJSONForURL(final String url,final boolean syncingCommits) {
		// TODO Auto-generated method stub
		
		
		new AsyncTask<String, Integer, String>(){
			
			protected void onPreExecute() {
				
			};


			protected String doInBackground(String... params) {
				// TODO Auto-generated method stub
				
				try {
					String protocol ="http://";
					
					String host = "192.168.43.47/MMUST_TT/";
					
					if(prefsHandler.isDeveloperMode()){
						host = prefsHandler.getHostAddress()+"/MMUST_TT/";
					}
					
					HttpPost post = new HttpPost(protocol+host+url);
					post.setEntity(getHttpEntityForRequestedUrl(url));
					HttpResponse response = new DefaultHttpClient().execute(post);
					
					if(response.getStatusLine().getStatusCode()==200){
						String jsonResponse = EntityUtils.toString(response.getEntity());
						Log.i(url, jsonResponse);
						return jsonResponse;
					}else{
						Bundle extras = new Bundle();
						extras.putInt(StudentAuthenticator.EXTRA_HTTP_CONN_STATUS_CODE, response.getStatusLine().getStatusCode());
						extras.putString(StudentAuthenticator.EXTRA_HTTP_REQUEST_URL, StudentAuthenticator.EXTRA_URL_BASIC_STUDENT_INFO);
						broadcast(StudentAuthenticator.ACTION_HTTP_REQUEST_ERROR,extras);
					}
				} catch (ClientProtocolException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return null;
			}
			
			


			protected void onProgressUpdate(Integer[] values) {
				
			};
			
			protected void onPostExecute(String result) {
				ContentValues values = null;
				
				Log.i(TAG, "::onPostExecute");
				
				
				
				if(url.equals(StudentAuthenticator.EXTRA_URL_BASIC_STUDENT_INFO)){
					
					try {
						JSONObject studentInfo = new JSONObject(result);
						String full_name = studentInfo.getString("full_name");
						String pursuing_course = studentInfo.getString("pursuing_course");
						String department_id = studentInfo.getString("department_id");
						String phonenumber = studentInfo.getString("phonenumber");
						String email = studentInfo.getString("email");
						String student_category = studentInfo.getString("student_category");
						String adm_yr = studentInfo.getString("adm_yr");
						String faculty_id = studentInfo.getString("faculty_id");
						String campus_id = studentInfo.getString("campus_id");
						
						//SaveStudentInfo
						values = new ContentValues();
						values.put(DbConsts.STUDENT_REG_NO,prefsHandler.getStudentRegistrationNumber());
						values.put(DbConsts.STUDENT_FULL_NAME, full_name);
						values.put(DbConsts.STUDENT_CATEGORY, student_category);
						values.put(DbConsts.STUDENT_PHONENUMBER, phonenumber);
						values.put(DbConsts.STUDENT_EMAIL, email);
						values.put(DbConsts.STUDENT_PURSUING_COURSE,pursuing_course);
						values.put(DbConsts.STUDENT_DEPARTMENT_ID, department_id);
						values.put(DbConsts.STUDENT_STUDENT_ADM_YR, adm_yr);
						values.put(DbConsts.STUDENT_FACULTY_ID, faculty_id);
						values.put(DbConsts.STUDENT_CAMPUS_ID, campus_id);
						insertStudentsInfo(values);
						
						prefsHandler.setAdmYr(adm_yr);
						
						
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}


				}

				if(url.equals(StudentAuthenticator.EXTRA_URL_ADVANCED_SCHOOL_INFO)){
					
					
					//StartGettingAllOtherData -> Get CommitsDataFirst
					try {
						JSONObject schoolInfo = new JSONObject(result);
						
						String pursuing_course_name = schoolInfo.getString("course_name");
						String pursuing_course_code = schoolInfo.getString("course_code");
						
						String department_id = schoolInfo.getString("department_id");
						String department_name = schoolInfo.getString("department_name");
						
						String faculty_id = schoolInfo.getString("faculty_id");
						String faculty_name = schoolInfo.getString("faculty_name");
						
						String campus_id = schoolInfo.getString("campus_id");
						String campus_name = schoolInfo.getString("campus_name");
						
						
						
						
						//Insert SchoolCourses
						prefsHandler.setPursuingCourse(pursuing_course_code);
						values = new ContentValues();
						values.put(DbConsts.SCHOOL_COURSES_COURSE_CODE, pursuing_course_code);
						values.put(DbConsts.SCHOOL_COURSES_COURSE_NAME, pursuing_course_name);
						insertSchoolCourse(values);
						
						//SaveDeparmentInfo
						prefsHandler.setDepartmentId(department_id);
						values = new ContentValues();
						values.put(DbConsts.DEPARTMENT_ID, department_id);
						values.put(DbConsts.DEPARTMENT_NAME, department_name);
						values.put(DbConsts.DEPARTMENT_FACULTY_ID, faculty_id);
						values.put(DbConsts.DEPARTMENT_CAMPUS_ID, campus_id);
						insertDepartments(values);
						
						//SaveFacultyInfo
						prefsHandler.setFacultyId(faculty_id);
						values = new ContentValues();
						values.put(DbConsts.FACULTY_ID, faculty_id);
						values.put(DbConsts.FACULTY_NAME, faculty_name);
						values.put(DbConsts.FACULTY_CAMPUS_ID, campus_id);
						insertFaculty(values);
						
						//SaveCampusInfo
						prefsHandler.setCampusId(campus_id);
						values = new ContentValues();
						values.put(DbConsts.CAMPUS_ID, campus_id);
						values.put(DbConsts.CAMPUS_NAME, campus_name);
						insertCampus(values);
						
						
						
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}
				
				if(url.equals(StudentAuthenticator.EXTRA_URL_COMMITS_INFO)){
					
					
					//Get CommitsDataFirst
					try {
						JSONObject commitsInfo = new JSONArray(result).getJSONObject(0);
						
						if(syncingCommits){
							syncLatestCommits(commitsInfo);
						}
												
						int commits=commitsInfo.getInt("commits");
						int assignments=commitsInfo.getInt("assignments");
						int campus=commitsInfo.getInt("campus");
						int classes=commitsInfo.getInt("classes");
						int courses=commitsInfo.getInt("courses");
						int departments=commitsInfo.getInt("departments");
						int downloads=commitsInfo.getInt("downloads");
						int exams=commitsInfo.getInt("exams");
						int faculty=commitsInfo.getInt("faculty");
						int lecturer=commitsInfo.getInt("lecturer");
						int notifications=commitsInfo.getInt("notifications");
						int school_courses=commitsInfo.getInt("school_courses");
						int students=commitsInfo.getInt("students");
						int uploads=commitsInfo.getInt("uploads");
								
						values = new ContentValues();
						values.put(DbConsts.COMMITS_INFO_COMMITS, commits);
						values.put(DbConsts.COMMITS_INFO_ASSIGNMENTS, assignments);
						values.put(DbConsts.COMMITS_INFO_CAMPUS, campus);
						values.put(DbConsts.COMMITS_INFO_CLASSES, classes);
						values.put(DbConsts.COMMITS_INFO_COURSES, courses);
						values.put(DbConsts.COMMITS_INFO_DEPARTMENTS, departments);
						values.put(DbConsts.COMMITS_INFO_DOWNLOADS, downloads);
						values.put(DbConsts.COMMITS_INFO_EXAMS, exams);
						values.put(DbConsts.COMMITS_INFO_FACULTY, faculty);
						values.put(DbConsts.COMMITS_INFO_LECTURER, lecturer);
						values.put(DbConsts.COMMITS_INFO_NOTIFICATIONS, notifications);
						values.put(DbConsts.COMMITS_INFO_SCHOOL_COURSES, school_courses);
						values.put(DbConsts.COMMITS_INFO_UPLOADS, uploads);
						values.put(DbConsts.COMMITS_INFO_STUDENTS,students);
						
						insertCommits(values);
						
						
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}
				
				if (url.equals(StudentAuthenticator.EXTRA_URL_READ_ASSESSMENTS_TYPE_ASSIGNMENTS)) {

					try {
						if(result.endsWith(",")){
							result = result.substring(0,result.length()-1);
						}
						
						JSONArray responseJson = new JSONArray("["+result+"]");
						
						for (int courses = 0; courses < responseJson.length(); courses++) {
							JSONObject assessmentInfo = responseJson.getJSONObject(courses);
							
							String assignmentNo = assessmentInfo.getString("assignment_no");
							String submissionDate = assessmentInfo.getString("submission_date");
							String assignmentCourseCode = assessmentInfo.getString("course_code");
							
							values = new ContentValues();
							values.put(DbConsts.ASSIGNMENTS_ASSIGNMENT_NO, assignmentNo);
							values.put(DbConsts.ASSIGNMENTS_SUBMISSION_DATE, submissionDate);
							values.put(DbConsts.ASSIGNMENTS_COURSE_CODE, assignmentCourseCode);
							
							insertAssignments(values);
							}

					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}
				
				if (url.equals(StudentAuthenticator.EXTRA_URL_READ_ASSESSMENTS_TYPE_EXAMS)) {

					try {
						if(result.endsWith(",")){
							result = result.substring(0,result.length()-1);
						}
						
						JSONArray responseJson = new JSONArray("["+result+"]");
						Log.i("SIZE",""+responseJson.length());
						for (int courses = 0; courses < responseJson.length(); courses++) {
							JSONObject assessmentInfo = responseJson.getJSONObject(courses);
							
							String courseCode= assessmentInfo.getString("course_code");
							int examType = assessmentInfo.getInt("exam_type");
							String examRoom= assessmentInfo.getString("exam_room");
							String examDate= assessmentInfo.getString("exam_date");
							String examStart= assessmentInfo.getString("exam_start");
							int examDuration = assessmentInfo.getInt("exam_duration");
							
							values = new ContentValues();
							values.put(DbConsts.EXAM_COURSE_CODE,courseCode);
							values.put(DbConsts.EXAM_TYPE,examType);
							values.put(DbConsts.EXAM_ROOM, examRoom);
							values.put(DbConsts.EXAM_DATE, examDate);
							values.put(DbConsts.EXAM_START, examStart);
							values.put(DbConsts.EXAM_DURATION, examDuration);
							
							insertExams(values);
							}

					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}
				if (url.equals(StudentAuthenticator.EXTRA_URL_READ_ASSESSMENTS_TYPE_CATS)) {

					try {
						if(result.endsWith(",")){
							result = result.substring(0,result.length()-1);
						}
						
						JSONArray responseJson = new JSONArray("["+result+"]");
						Log.i("SIZE",""+responseJson.length());
						for (int courses = 0; courses < responseJson.length(); courses++) {
							JSONObject assessmentInfo = responseJson.getJSONObject(courses);
							
							String courseCode= assessmentInfo.getString("course_code");
							int examType = assessmentInfo.getInt("exam_type");
							String examRoom= assessmentInfo.getString("exam_room");
							String examDate= assessmentInfo.getString("exam_date");
							String examStart= assessmentInfo.getString("exam_start");
							int examDuration = assessmentInfo.getInt("exam_duration");
							
							values = new ContentValues();
							values.put(DbConsts.EXAM_COURSE_CODE,courseCode);
							values.put(DbConsts.EXAM_TYPE,examType);
							values.put(DbConsts.EXAM_ROOM, examRoom);
							values.put(DbConsts.EXAM_DATE, examDate);
							values.put(DbConsts.EXAM_START, examStart);
							values.put(DbConsts.EXAM_DURATION, examDuration);
							
							insertExams(values);
							}

					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}
				
				if (url.equals(StudentAuthenticator.EXTRA_URL_READ_COURSES)) {

					try {
						if(result.endsWith(",")){
							result = result.substring(0,result.length()-1);
						}
						
						JSONArray responseJson = new JSONArray("["+result+"]");
						
						for (int courses = 0; courses < responseJson.length(); courses++) {
							JSONObject coursesInfo = responseJson.getJSONObject(courses);
							String courseCode = coursesInfo.getString("course_code");
							String courseName = coursesInfo.getString("course_name");
							String lec_id = coursesInfo.getString("lec_id");
							
							values = new ContentValues();
							values.put(DbConsts.COURSES_COURSE_CODE,courseCode);
							values.put(DbConsts.COURSES_COURSE_NAME,courseName);
							values.put(DbConsts.COURSES_COURSE_LEC_ID, lec_id);
							
							insertCourses(values);
						}

					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}
				
				if (url.equals(StudentAuthenticator.EXTRA_URL_READ_CLASSES)) {

					try {
						if(result.endsWith(",")){
							result = result.substring(0,result.length()-1);
						}
						
						JSONArray responseJson = new JSONArray("["+result+"]");
						
						for (int classes = 0; classes < responseJson.length(); classes++) {
							JSONObject classesInfo = responseJson.getJSONObject(classes);
							
							String class_course_code = classesInfo.getString("course_code");
							String class_lec_id = classesInfo.getString("lec_id");
							String class_room = classesInfo.getString("class_room");
							String class_start = classesInfo.getString("class_start");
							String class_stop = classesInfo.getString("class_stop");
							String class_days = classesInfo.getString("class_days");
							
							values = new ContentValues();
							values.put(DbConsts.CLASS_COURSE_CODE, class_course_code);
							values.put(DbConsts.CLASS_LEC_ID, class_lec_id);
							values.put(DbConsts.CLASS_ROOM, class_room);
							values.put(DbConsts.CLASS_START, class_start);
							values.put(DbConsts.CLASS_STOP, class_stop);
							values.put(DbConsts.CLASS_DAYS, class_days);
							
							insertClasses(values);
							}

					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}
				
				
				
				if (url.equals(StudentAuthenticator.EXTRA_URL_READ_LECTURER)) {

					try {
						if(result.endsWith(",")){
							result = result.substring(0,result.length()-1);
						}
						
						JSONArray responseJson = new JSONArray("["+result+"]");
						
						for (int classes = 0; classes < responseJson.length(); classes++) {
							JSONObject lecInfo = responseJson.getJSONObject(classes);
							
							String lecId = lecInfo.getString("lec_id");
							String lecName = lecInfo.getString("lec_name");
							String lecAvator = lecInfo.getString("lec_avator_uri");
							String lecEmail = lecInfo.getString("lec_email");
							String lecPhone = lecInfo.getString("lec_phone");
							String lecQualifications = lecInfo.getString("lec_qualification");
							
							values  = new ContentValues();
							values.put(DbConsts.LECS_LEC_ID, lecId);
							values.put(DbConsts.LECS_LEC_NAME, lecName);
							values.put(DbConsts.LEC_AVATOR_URI, Environment.getExternalStorageDirectory().getAbsolutePath()+"/MarvikApps/MMUST_TT/"+lecAvator);
							values.put(DbConsts.LECS_LEC_EMAIL, lecEmail);
							values.put(DbConsts.LECS_LEC_PHONE,lecPhone);
							values.put(DbConsts.LEC_QUALIFICATIONS,lecQualifications);
							
							insertLecInfo(values);
						}

					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}
				
				if (url.equals(StudentAuthenticator.EXTRA_URL_READ_NOTIFICATIONS)) {

					try {
						if(result.endsWith(",")){
							result = result.substring(0,result.length()-1);
						}
						
						JSONArray responseJson = new JSONArray("["+result+"]");
						
						for (int notifications = 0; notifications < responseJson.length(); notifications++) {
							JSONObject notificationsInfo = responseJson.getJSONObject(notifications);
							
							String notification_id = notificationsInfo.getString("notification_id");
							String notification_title = notificationsInfo.getString("notification_title");
							String notification_message = notificationsInfo.getString("notification_message");
							String notification_sender = notificationsInfo.getString("notification_sender");
							String notification_send_time = notificationsInfo.getString("notification_send_time");
							
							values = new ContentValues();
							values.put(DbConsts.NOTIFICATION_ID,notification_id);
							values.put(DbConsts.NOTIFICATION_TITLE,notification_title);
							values.put(DbConsts.NOTIFICATION_MESSAGE,notification_message);
							values.put(DbConsts.NOTIFICATION_SENDER,notification_sender);
							values.put(DbConsts.NOTIFICATION_SEND_TIME,notification_send_time);
							insertNotifications(values);
							
						}

					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}
				
				
				if (url.equals(StudentAuthenticator.EXTRA_URL_READ_DOWNLOADS_AND_UPLOADS)) {

					try {
						if(result.endsWith(",")){
							result = result.substring(0,result.length()-1);
						}
						
						JSONArray responseJson = new JSONArray("["+result+"]");
						
						for (int notifications = 0; notifications < responseJson.length(); notifications++) {
							JSONObject filesInfo = responseJson.getJSONObject(notifications);
							
							String course_code = filesInfo.getString("course_code");
							String fileid = filesInfo.getString("fileid");
							String filename = filesInfo.getString("filename");
							long  filesize = filesInfo.getLong("filesize");
							String filedesc = filesInfo.getString("filedesc");
							String fileuploader= filesInfo.getString("fileuploader");
							long fileuploadtime =  filesInfo.getLong("file_upload_time");
							String fileuri=filesInfo.getString("fileuri");
							
							
							
							values = new ContentValues();
							
							values.put(DbConsts.UPLOADS_FILE_ID, fileid);
							values.put(DbConsts.UPLOADS_FILENAME,filename);
							values.put(DbConsts.UPLOADS_FILESIZE, filesize);
							values.put(DbConsts.UPLOADS_FILE_DESC, filedesc);
							values.put(DbConsts.UPLOADS_FILE_UPLOADER, fileuploader);
							values.put(DbConsts.UPLOADS_FILE_UPLOAD_TIME, fileuploadtime);
							values.put(DbConsts.UPLOADS_FILE_URI, fileuri);
							insertUploads(values);
							
							values = new ContentValues();
							values.put(DbConsts.DOWNLOAD_COURSE_CODE, course_code);
							values.put(DbConsts.DOWNLOAD_FILE_ID, fileid);
							insertDownloads(values);
							
						}

					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}
			};
			
		}.execute();
		return jsonObject;
	}

	private HttpEntity getHttpEntityForRequestedUrl(String url) {
		// TODO Auto-generated method stub
		
		List<NameValuePair>lFormParams = new ArrayList<NameValuePair>();
		
		//These form params are  A MUST BE THERE 
		lFormParams.add(setParam(StudentAuthenticator.POST_REG_NO,prefsHandler.getStudentRegistrationNumber()));
		lFormParams.add(setParam(StudentAuthenticator.POST_CLIENT, StudentAuthenticator.POST_CLIENT_MOBILE));
		
		//url to get basic student info
		if(url.equalsIgnoreCase(StudentAuthenticator.EXTRA_URL_BASIC_STUDENT_INFO)){

			
		}
		
		//URL to get pursuing course and advanced school info
		if(url.equals(StudentAuthenticator.EXTRA_URL_ADVANCED_SCHOOL_INFO)){
			lFormParams.add(setParam(StudentAuthenticator.POST_ADVANCED_SCHOOL_INFO,StudentAuthenticator.POST_ADVANCED_SCHOOL_INFO));
		}
		
		//URLS to get study details...all share common params	
		if(url.equals(StudentAuthenticator.EXTRA_URL_READ_ASSESSMENTS_TYPE_ASSIGNMENTS)
				||url.equals(StudentAuthenticator.EXTRA_URL_READ_ASSESSMENTS_TYPE_CATS)
				||url.equals(StudentAuthenticator.EXTRA_URL_READ_ASSESSMENTS_TYPE_EXAMS)
				||url.equals(StudentAuthenticator.EXTRA_URL_READ_CLASSES)
				||url.equals(StudentAuthenticator.EXTRA_URL_READ_COURSES)
				||url.equals(StudentAuthenticator.EXTRA_URL_READ_LECTURER)
				||url.equals(StudentAuthenticator.EXTRA_URL_READ_NOTIFICATIONS)
				||url.equals(StudentAuthenticator.EXTRA_URL_READ_DOWNLOADS_AND_UPLOADS)){
			
			lFormParams.add(setParam(StudentAuthenticator.POST_CAMPUS_ID,prefsHandler.getCampusId()));
			lFormParams.add(setParam(StudentAuthenticator.POST_FACULTY_ID,prefsHandler.getFacultyId()));
			lFormParams.add(setParam(StudentAuthenticator.POST_DEPARTMENT_ID,prefsHandler.getDepartmentId()));
			lFormParams.add(setParam(StudentAuthenticator.POST_ADM_YR,prefsHandler.getAdmYr()));
			lFormParams.add(setParam(StudentAuthenticator.POST_PURSUING_COURSE,prefsHandler.getPursuingCourse()));
			
			
		}
		
		//URLS to get assignments
		if (url.equals(StudentAuthenticator.EXTRA_URL_READ_ASSESSMENTS_TYPE_ASSIGNMENTS)) {
			lFormParams.add(setParam(StudentAuthenticator.POST_ASSESSMENT_TYPE,""+StudentAuthenticator.POST_ASSESSMENT_TYPE_ASSIGNMENTS));
		}
		
		//URLS to get C.A.T.S
		if (url.equals(StudentAuthenticator.EXTRA_URL_READ_ASSESSMENTS_TYPE_CATS)) {
			lFormParams.add(setParam(StudentAuthenticator.POST_ASSESSMENT_TYPE,""+StudentAuthenticator.POST_ASSESSMENT_TYPE_CAT));
		}
		
		//URLS to get end of semester exams
		if (url.equals(StudentAuthenticator.EXTRA_URL_READ_ASSESSMENTS_TYPE_EXAMS)) {
			lFormParams.add(setParam(StudentAuthenticator.POST_ASSESSMENT_TYPE,""+StudentAuthenticator.POST_ASSESSMENT_TYPE_EXAMS));
		}
		
		//URLS to get classes
		if (url.equals(StudentAuthenticator.EXTRA_URL_READ_CLASSES)) {
		}
		
		//URLS to get student courses
		if (url.equals(StudentAuthenticator.EXTRA_URL_READ_COURSES)) {
		}
		
		//URLS to get student lecturers
		if (url.equals(url.equals(StudentAuthenticator.EXTRA_URL_READ_LECTURER))) {
		}
		
		//URLS to get notifications
		if (url.equals(StudentAuthenticator.EXTRA_URL_READ_NOTIFICATIONS)) {
			
		}
		
		//URLS to get file info(uploads and downloads)
		if(url.equals(StudentAuthenticator.EXTRA_URL_READ_DOWNLOADS_AND_UPLOADS)){
			lFormParams.add(setParam(StudentAuthenticator.POST_READ_DOWNLOADS,StudentAuthenticator.POST_READ_DOWNLOADS));
		}
		try {
			return new UrlEncodedFormEntity(lFormParams);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	private BasicNameValuePair setParam(String name,
			String value) {
		// TODO Auto-generated method stub
		return new BasicNameValuePair(name,value);
	}

	
	
}
