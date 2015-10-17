package com.marvik.apps.mmust.prefs;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.Cursor;
import android.preference.PreferenceManager;

import com.marvik.apps.mmust.activities.Classes;
import com.marvik.apps.mmust.activities.Courses;
import com.marvik.apps.mmust.activities.Dashboard;
import com.marvik.apps.mmust.activities.StudentInfo;
import com.marvik.apps.mmust.activities.assessments.AssessmentType;
import com.marvik.apps.mmust.database.DbConsts;
import com.marvik.apps.mmust.provider.DataProvider;
import com.marvik.apps.mmust.utils.StudentAuthenticator;

public class PrefsHandler implements DeclaredPrefs {
	
	Editor editor;
	SharedPreferences prefs;
	Context context;
	
	public PrefsHandler(Context context){
		this.context =context;
		prefs = PreferenceManager.getDefaultSharedPreferences(context);
		editor = prefs.edit();
	}
	/**
	 * @return the seasonEnabled
	 */
	public boolean isSeasonEnabled() {
		return prefs.getBoolean(PrefsOptions.SEASON_ENABLED,true );
	}
	/**
	 * @param seasonEnabled the seasonEnabled to set
	 */
	public void setSeasonEnabled(boolean seasonEnabled) {
		editor.putBoolean(PrefsOptions.SEASON_ENABLED, seasonEnabled);
		editor.commit();
	}
	/**
	 * @return the season
	 */
	public int getSeason() {
		return prefs.getInt(PrefsOptions.SEASON, 0);
	}
	/**
	 * @param season the season to set
	 */
	public void setSeason(int season) {
		editor.putInt(PrefsOptions.SEASON, season);
		editor.commit();
	}
	/**
	 * @return the navigateToBestSeason
	 */
	public boolean isNavigateToBestSeason() {
		return prefs.getBoolean(PrefsOptions.NAVIGATE_TO_BEST_SEASON,true );
	}
	/**
	 * @param navigateToBestSeason the navigateToBestSeason to set
	 */
	public void setNavigateToBestSeason(boolean navigateToBestSeason) {
		editor.putBoolean(PrefsOptions.NAVIGATE_TO_BEST_SEASON, navigateToBestSeason);
		editor.commit();
	}
	/**
	 * @return the showSplash
	 */
	public boolean isShowSplash() {
		return prefs.getBoolean(PrefsOptions.SHOW_SPLASH,true );
	}
	/**
	 * @param showSplash the showSplash to set
	 */
	public void setShowSplash(boolean showSplash) {
		editor.putBoolean(PrefsOptions.SHOW_SPLASH,showSplash );
		editor.commit();
	}
	/**
	 * @return the keepBackStack
	 */
	public boolean isKeepBackStack() {
		return prefs.getBoolean(PrefsOptions.KEEP_BACK_STACK,true );
	}
	/**
	 * @param keepBackStack the keepBackStack to set
	 */
	public void setKeepBackStack(boolean keepBackStack) {
		editor.putBoolean(PrefsOptions.KEEP_BACK_STACK,keepBackStack );
		editor.commit();
	}
	/**
	 * @return the syncEnabled
	 */
	public boolean isSyncEnabled() {
		return prefs.getBoolean(PrefsOptions.SYNC_ENABLED, true);
	}
	/**
	 * @param syncEnabled the syncEnabled to set
	 */
	public void setSyncEnabled(boolean syncEnabled) {
		editor.putBoolean(PrefsOptions.SYNC_ENABLED,syncEnabled );
		editor.commit();
	}
	/**
	 * @return the syncInterval
	 */
	public int getSyncInterval() {
		return prefs.getInt(PrefsOptions.SYNC_INTERVAL, 0);
	}
	/**
	 * @param syncInterval the syncInterval to set
	 */
	public void setSyncInterval(int syncInterval) {
		editor.putInt(PrefsOptions.SYNC_INTERVAL, syncInterval);
		editor.commit();
	}
	/**
	 * @return the alertOnSync
	 */
	public boolean isAlertOnSync() {
		return prefs.getBoolean(PrefsOptions.ALERT_ON_SYNC,true );
	}
	/**
	 * @param alertOnSync the alertOnSync to set
	 */
	public void setAlertOnSync(boolean alertOnSync) {
		editor.putBoolean(PrefsOptions.ALERT_ON_SYNC,alertOnSync );
		editor.commit();
	}
	/**
	 * @return the enableReminders
	 */
	public boolean isEnableReminders() {
		return prefs.getBoolean(PrefsOptions.ENABLE_REMINDERS,true );
	}
	/**
	 * @param enableReminders the enableReminders to set
	 */
	public void setEnableReminders(boolean enableReminders) {
		editor.putBoolean(PrefsOptions.ENABLE_REMINDERS,enableReminders );
		editor.commit();
	}
	/**
	 * @return the enableClassesReminder
	 */
	public boolean isEnableClassesReminder() {
		return prefs.getBoolean(PrefsOptions.ENABLE_CLASSES_REMINDERS,true );
	}
	/**
	 * @param enableClassesReminder the enableClassesReminder to set
	 */
	public void setEnableClassesReminder(boolean enableClassesReminder) {
		editor.putBoolean(PrefsOptions.ENABLE_CLASSES_REMINDERS, enableClassesReminder);
		editor.commit();
	}
	/**
	 * @return the classesReminderInterval
	 */
	public int getClassesReminderInterval() {
		return prefs.getInt(PrefsOptions.CLASSES_REMINDER_INTERVAL, 0);
	}
	/**
	 * @param classesReminderInterval the classesReminderInterval to set
	 */
	public void setClassesReminderInterval(int classesReminderInterval) {
		editor.putInt(PrefsOptions.CLASSES_REMINDER_INTERVAL, classesReminderInterval);
		editor.commit();
	}
	/**
	 * @return the enableAssignmentsReminder
	 */
	public boolean isEnableAssignmentsReminder() {
		return prefs.getBoolean(PrefsOptions.ENABLE_ASSIGNMENTS_REMINDERS, true);
	}
	/**
	 * @param enableAssignmentsReminder the enableAssignmentsReminder to set
	 */
	public void setEnableAssignmentsReminder(boolean enableAssignmentsReminder) {
		editor.putBoolean(PrefsOptions.ENABLE_ASSIGNMENTS_REMINDERS, enableAssignmentsReminder);
		editor.commit();
	}
	/**
	 * @return the assignmnetsReminderInterval
	 */
	public int getAssignmnetsReminderInterval() {
		return prefs.getInt(PrefsOptions.ASSIGNMENTS_REMINDER_INTERVAL, 0);
	}
	/**
	 * @param assignmnetsReminderInterval the assignmnetsReminderInterval to set
	 */
	public void setAssignmnetsReminderInterval(int assignmnetsReminderInterval) {
		editor.putInt(PrefsOptions.ASSIGNMENTS_REMINDER_INTERVAL, assignmnetsReminderInterval);
		editor.commit();
	}
	/**
	 * @return the enableCatsReminder
	 */
	public boolean isEnableCatsReminder() {
		return prefs.getBoolean(PrefsOptions.ENABLE_CATS_REMINDERS, true);
	}
	/**
	 * @param enableCatsReminder the enableCatsReminder to set
	 */
	public void setEnableCatsReminder(boolean enableCatsReminder) {
		editor.putBoolean(PrefsOptions.ENABLE_CATS_REMINDERS,enableCatsReminder );
		editor.commit();
	}
	/**
	 * @return the catsReminderInterval
	 */
	public int getCatsReminderInterval() {
		return prefs.getInt(PrefsOptions.CATS_REMINDER_INTERVAL, 0);
	}
	/**
	 * @param catsReminderInterval the catsReminderInterval to set
	 */
	public void setCatsReminderInterval(int catsReminderInterval) {
		editor.putInt(PrefsOptions.CATS_REMINDER_INTERVAL,catsReminderInterval );
		editor.commit();
	}
	/**
	 * @return the enableEOSReminder
	 */
	public boolean isEnableEOSReminder() {
		return prefs.getBoolean(PrefsOptions.ENABLE_EOS_REMINDERS,true );
	}
	/**
	 * @param enableEOSReminder the enableEOSReminder to set
	 */
	public void setEnableEOSReminder(boolean enableEOSReminder) {
		editor.putBoolean(PrefsOptions.ENABLE_EOS_REMINDERS,enableEOSReminder );
		editor.commit();
	}
	/**
	 * @return the eOSReminderInterval
	 */
	public int getEOSReminderInterval() {
		return prefs.getInt(PrefsOptions.EOS_REMINDER_INTERVAL, 0);
	}
	/**
	 * @param eOSReminderInterval the eOSReminderInterval to set
	 */
	public void setEOSReminderInterval(int eOSReminderInterval) {
		editor.putInt(PrefsOptions.EOS_REMINDER_INTERVAL,eOSReminderInterval );
		editor.commit();
	}
	/**
	 * @return the appNotificationsEnabled
	 */
	public boolean isAppNotificationsEnabled() {
		return prefs.getBoolean(PrefsOptions.APP_NOTIFICATIONS_ENABLED,true );
	}
	/**
	 * @param appNotificationsEnabled the appNotificationsEnabled to set
	 */
	public void setAppNotificationsEnabled(boolean appNotificationsEnabled) {
		editor.putBoolean(PrefsOptions.APP_NOTIFICATIONS_ENABLED, appNotificationsEnabled);
		editor.commit();
	}
	/**
	 * @return the downloadsEnabled
	 */
	public boolean isDownloadsEnabled() {
		return prefs.getBoolean(PrefsOptions.DOWNLOADS_ENABLED, true);
	}
	/**
	 * @param downloadsEnabled the downloadsEnabled to set
	 */
	public void setDownloadsEnabled(boolean downloadsEnabled) {
		editor.putBoolean(PrefsOptions.DOWNLOADS_ENABLED, downloadsEnabled);
		editor.commit();
	}
	/**
	 * @return the showSentNotificationsEnabled
	 */
	public boolean isShowSentNotificationsEnabled() {
		return prefs.getBoolean(PrefsOptions.SHOW_SENT_NOTIFICATIONS_ENABLED, true);
	}
	/**
	 * @param showSentNotificationsEnabled the showSentNotificationsEnabled to set
	 */
	public void setShowSentNotificationsEnabled(boolean showSentNotificationsEnabled) {
		editor.putBoolean(PrefsOptions.SHOW_SENT_NOTIFICATIONS_ENABLED, showSentNotificationsEnabled);
		editor.commit();
	}
	/**
	 * @return the developerMode
	 */
	public boolean isDeveloperMode() {
		return prefs.getBoolean(PrefsOptions.DEVELOPER_MODE,false );
	}
	/**
	 * @param developerMode the developerMode to set
	 */
	public void setDeveloperMode(boolean developerMode) {
		editor.putBoolean(PrefsOptions.DEVELOPER_MODE, developerMode);
		editor.commit();
	}
	/**
	 * @return the wifiPassword
	 */
	public String getWifiPassword() {
		return prefs.getString(PrefsOptions.WIFI_PASSWORD,"" );
	}
	/**
	 * @param wifiPassword the wifiPassword to set
	 */
	public void setWifiPassword(String wifiPassword) {
		editor.putString(PrefsOptions.WIFI_PASSWORD,wifiPassword );
		editor.commit();
	}
	/**
	 * @return the wifiSSID
	 */
	public String getWifiSSID() {
		return prefs.getString(PrefsOptions.WIFI_SSID,"" );
	}
	/**
	 * @param wifiSSID the wifiSSID to set
	 */
	public void setWifiSSID(String wifiSSID) {
		editor.putString(PrefsOptions.WIFI_SSID,wifiSSID );
		editor.commit();
	}
	/**
	 * @return the hostAddress
	 */
	public String getHostAddress() {
		return prefs.getString(PrefsOptions.HOST_ADDRESS,"" );
	}
	/**
	 * @param hostAddress the hostAddress to set
	 */
	public void setHostAddress(String hostAddress) {
		editor.putString(PrefsOptions.HOST_ADDRESS,hostAddress );
		editor.commit();
	}
	public boolean isFirstrun() {
		// TODO Auto-generated method stub
		return prefs.getBoolean(PrefsOptions.APP_FIRSTRUN, true);
	}
	public void setFirstrun(boolean firstrun) {
		// TODO Auto-generated method stub
		editor.putBoolean(PrefsOptions.APP_FIRSTRUN,firstrun );
		editor.commit();
	}
	public Class<?> getLaunchClass() {
		// TODO Auto-generated method stub
		/**
		 * <ul>
		 * <li>Admission season</li>
		 * <li>Normal season</li>
		 * <li>Start of semester season</li>
		 * <li>Study season</li>
		 * <li>Assignments season</li>
		 * <li>C.A.T season</li>
		 * <li>End of Semester Exam season</li>
		 * </ul>
		 */
		List<Class<?>> mClasses = new ArrayList<Class<?>>();
		mClasses.add(StudentInfo.class);
		mClasses.add(Dashboard.class);
		mClasses.add(Courses.class);
		mClasses.add(Classes.class);
		mClasses.add(AssessmentType.class);
		mClasses.add(AssessmentType.class);
		mClasses.add(AssessmentType.class);
		return isSeasonEnabled() && isNavigateToBestSeason()? mClasses.get(getSeason()) : Dashboard.class;
	}

	
	public boolean isSyncing() {
		// TODO Auto-generated method stub
		return prefs.getBoolean(PrefsOptions.APP_SYNCING, false);
	}
	public void setSyncing(boolean syncing) {
		// TODO Auto-generated method stub
		editor.putBoolean(PrefsOptions.APP_SYNCING, syncing);
		editor.commit();
	}
	@Override
	public boolean isHomeEnabled() {
		// TODO Auto-generated method stub
		return prefs.getBoolean(PrefsOptions.ENABLE_HOME, false);
	}
	@Override
	public void setHomeEnabled(boolean enableHome) {
		// TODO Auto-generated method stub
		editor.putBoolean(PrefsOptions.ENABLE_HOME, enableHome);
		editor.commit();
	}
	public boolean isNotificationBarShortcutsEnabled() {
		// TODO Auto-generated method stub
		return prefs.getBoolean(PrefsOptions.ENABLE_NOTIFICATION_BAR_SHORTCUTS, false);
	}
	@Override
	public void setNotificationBarShortcutsEnabled(
			boolean enableNotificationBarShortcuts) {
		// TODO Auto-generated method stub
		editor.putBoolean(PrefsOptions.ENABLE_NOTIFICATION_BAR_SHORTCUTS, enableNotificationBarShortcuts);
		editor.commit();
	}
	public String getStudentCampus() {
		// TODO Auto-generated method stub
		return prefs.getString(StudentAuthenticator.STUDENT_CAMPUS, "");
	}
	public void setStudentCampus(String campus) {
		// TODO Auto-generated method stub
		editor.putString(StudentAuthenticator.STUDENT_CAMPUS, campus);
		editor.commit();
	}
	
	public String getStudentFaculty() {
		// TODO Auto-generated method stub
		return prefs.getString(StudentAuthenticator.STUDENT_FACULTY, "");
	}
	public void setStudentFaculty(String faculty) {
		// TODO Auto-generated method stub
		editor.putString(StudentAuthenticator.STUDENT_FACULTY, faculty);
		editor.commit();
	}
	
	public String getStudentDepartment() {
		// TODO Auto-generated method stub
		return prefs.getString(StudentAuthenticator.STUDENT_DEPARTMENT, "");
	}
	public void setStudentDepartment(String department) {
		// TODO Auto-generated method stub
		editor.putString(StudentAuthenticator.STUDENT_DEPARTMENT, department);
		editor.commit();
	}
	
	public int getStudentAdmYear() {
		// TODO Auto-generated method stub
		return prefs.getInt(StudentAuthenticator.STUDENT_ADM_YR, getCurrentYear());
	}
	private int getCurrentYear() {
		// TODO Auto-generated method stub
		return Integer.parseInt(new SimpleDateFormat("YYYY").format(new Date(System.currentTimeMillis())));
	}
	public void setStudentAdmYear(int adm_yr) {
		// TODO Auto-generated method stub
		editor.putInt(StudentAuthenticator.STUDENT_ADM_YR, adm_yr);
		editor.commit();
	}
	
	public String getStudentPursuingCourse() {
		// TODO Auto-generated method stub
		return prefs.getString(StudentAuthenticator.STUDENT_PURSUING_COURSE, "");
	}
	public void setStudentPursuingCourse(String pursuingCourse) {
		// TODO Auto-generated method stub
		editor.putString(StudentAuthenticator.STUDENT_PURSUING_COURSE, pursuingCourse);
		editor.commit();
	}
	public String getStudentRegistrationNumber() {
		// TODO Auto-generated method stub
		return prefs.getString(StudentAuthenticator.STUDENT_REG_NO, "");
	}
	public void setStudentRegistrationNo(String reg_no) {
		// TODO Auto-generated method stub
		editor.putString(StudentAuthenticator.STUDENT_REG_NO, reg_no);
		editor.commit();
	}

	public void setStudentPassword(String password) {
		// TODO Auto-generated method stub
		editor.putString(StudentAuthenticator.STUDENT_PASSWORD, password);
		editor.commit();
	}
	
	/**
	 * @return the campusId
	 */
	public String getCampusId() {
		return prefs.getString(StudentAuthenticator.STUDENT_CAMPUS, "");
	}
	/**
	 * @param campusId the campusId to set
	 */
	public void setCampusId(String campusId) {
		editor.putString(StudentAuthenticator.STUDENT_CAMPUS, campusId);
		editor.commit();
	}
	/**
	 * @return the facultyId
	 */
	public String getFacultyId() {
		return prefs.getString(StudentAuthenticator.STUDENT_FACULTY, "");
	}
	/**
	 * @param facultyId the facultyId to set
	 */
	public void setFacultyId(String facultyId) {
		editor.putString(StudentAuthenticator.STUDENT_FACULTY, facultyId);
		editor.commit();
	}
	/**
	 * @return the departmentId
	 */
	public String getDepartmentId() {
		return prefs.getString(StudentAuthenticator.STUDENT_DEPARTMENT, "");
	}
	/**
	 * @param departmentId the departmentId to set
	 */
	public void setDepartmentId(String departmentId) {
		editor.putString(StudentAuthenticator.STUDENT_DEPARTMENT, departmentId);
		editor.commit();
	}
	/**
	 * @return the admYr
	 */
	public String getAdmYr() {
		return prefs.getString(StudentAuthenticator.STUDENT_ADM_YR, "");
	}
	/**
	 * @param admYr the admYr to set
	 */
	public void setAdmYr(String admYr) {
		editor.putString(StudentAuthenticator.STUDENT_ADM_YR, admYr);
		editor.commit();
	}
	/**
	 * @return the pursuingCourse
	 */
	public String getPursuingCourse() {
		return prefs.getString(StudentAuthenticator.STUDENT_PURSUING_COURSE, "");
	}
	/**
	 * @param pursuingCourse the pursuingCourse to set
	 */
	public void setPursuingCourse(String pursuingCourse) {
		editor.putString(StudentAuthenticator.STUDENT_PURSUING_COURSE, pursuingCourse);
		editor.commit();
	}
	
	

	/**
	 * @return the commits
	 */
	public int getCommits() {
		return prefs.getInt(DbConsts.COMMITS_INFO_COMMITS, 0);
	}
	/**
	 * @param commits the commits to set
	 */
	public void setCommits(int commits) {
		editor.putInt(DbConsts.COMMITS_INFO_COMMITS,commits );
		editor.commit();
	}
	/**
	 * @return the assignments
	 */
	public int getAssignments() {
		return prefs.getInt(DbConsts.COMMITS_INFO_ASSIGNMENTS, 0);
	}
	/**
	 * @param assignments the assignments to set
	 */
	public void setAssignments(int assignments) {
		editor.putInt(DbConsts.COMMITS_INFO_ASSIGNMENTS, assignments);
		editor.commit();
	}
	/**
	 * @return the campus
	 */
	public int getCampus() {
		return prefs.getInt(DbConsts.COMMITS_INFO_CAMPUS, 0);
	}
	/**
	 * @param campus the campus to set
	 */
	public void setCampus(int campus) {
		editor.putInt(DbConsts.COMMITS_INFO_CAMPUS,campus );
		editor.commit();
	}
	/**
	 * @return the classes
	 */
	public int getClasses() {
		return prefs.getInt(DbConsts.COMMITS_INFO_CLASSES, 0);
	}
	/**
	 * @param classes the classes to set
	 */
	public void setClasses(int classes) {
		editor.putInt(DbConsts.COMMITS_INFO_CLASSES,classes );
		editor.commit();
	}
	/**
	 * @return the courses
	 */
	public int getCourses() {
		return prefs.getInt(DbConsts.COMMITS_INFO_COURSES, 0);
	}
	/**
	 * @param courses the courses to set
	 */
	public void setCourses(int courses) {
		editor.putInt(DbConsts.COMMITS_INFO_COURSES,courses );
		editor.commit();
	}
	/**
	 * @return the departments
	 */
	public int getDepartments() {
		return prefs.getInt(DbConsts.COMMITS_INFO_DEPARTMENTS, 0);
	}
	/**
	 * @param departments the departments to set
	 */
	public void setDepartments(int departments) {
		editor.putInt(DbConsts.COMMITS_INFO_DEPARTMENTS, departments);
		editor.commit();
	}
	/**
	 * @return the downloads
	 */
	public int getDownloads() {
		return prefs.getInt(DbConsts.COMMITS_INFO_DOWNLOADS, 0);
	}
	/**
	 * @param downloads the downloads to set
	 */
	public void setDownloads(int downloads) {
		editor.putInt(DbConsts.COMMITS_INFO_DOWNLOADS,downloads );
		editor.commit();
	}
	/**
	 * @return the exams
	 */
	public int getExams() {
		return prefs.getInt(DbConsts.COMMITS_INFO_EXAMS, 0);
	}
	/**
	 * @param exams the exams to set
	 */
	public void setExams(int exams) {
		editor.putInt(DbConsts.COMMITS_INFO_EXAMS, exams);
		editor.commit();
	}
	/**
	 * @return the faculty
	 */
	public int getFaculty() {
		return prefs.getInt(DbConsts.COMMITS_INFO_FACULTY, 0);
	}
	/**
	 * @param faculty the faculty to set
	 */
	public void setFaculty(int faculty) {
		editor.putInt(DbConsts.COMMITS_INFO_FACULTY, faculty);
		editor.commit();
	}
	/**
	 * @return the lecturer
	 */
	public int getLecturer() {
		return prefs.getInt(DbConsts.COMMITS_INFO_LECTURER, 0);
	}
	/**
	 * @param lecturer the lecturer to set
	 */
	public void setLecturer(int lecturer) {
		editor.putInt(DbConsts.COMMITS_INFO_LECTURER,lecturer );
		editor.commit();
	}
	/**
	 * @return the notifications
	 */
	public int getNotifications() {
		return prefs.getInt(DbConsts.COMMITS_INFO_NOTIFICATIONS, 0);
	}
	/**
	 * @param notifications the notifications to set
	 */
	public void setNotifications(int notifications) {
		editor.putInt(DbConsts.COMMITS_INFO_NOTIFICATIONS,notifications );
		editor.commit();
	}
	/**
	 * @return the school_courses
	 */
	public int getSchoolCourses() {
		return prefs.getInt(DbConsts.COMMITS_INFO_SCHOOL_COURSES, 0);
	}
	/**
	 * @param school_courses the school_courses to set
	 */
	public void setSchoolCourses(int school_courses) {
		editor.putInt(DbConsts.COMMITS_INFO_SCHOOL_COURSES,school_courses );
		editor.commit();
	}
	/**
	 * @return the students
	 */
	public int getStudents() {
		return prefs.getInt(DbConsts.COMMITS_INFO_STUDENTS, 0);
	}
	/**
	 * @param students the students to set
	 */
	public void setStudents(int students) {
		editor.putInt(DbConsts.COMMITS_INFO_STUDENTS,students );
		editor.commit();
	}
	/**
	 * @return the uploads
	 */
	public int getUploads() {
		return prefs.getInt(DbConsts.COMMITS_INFO_UPLOADS, 0);
	}
	/**
	 * @param uploads the uploads to set
	 */
	public void setUploads(int uploads) {
		editor.putInt(DbConsts.COMMITS_INFO_UPLOADS,uploads );
		editor.commit();
	}
	public void saveCommitsUpdates(JSONObject commitsInfo) throws JSONException {
		// TODO Auto-generated method stub
		
	
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
					
			editor.putInt(DbConsts.COMMITS_INFO_COMMITS, iCommits);
			editor.putInt(DbConsts.COMMITS_INFO_ASSIGNMENTS, iAssignments);
			editor.putInt(DbConsts.COMMITS_INFO_CAMPUS, iCampus);
			editor.putInt(DbConsts.COMMITS_INFO_CLASSES, iClasses);
			editor.putInt(DbConsts.COMMITS_INFO_COURSES, iCourses);
			editor.putInt(DbConsts.COMMITS_INFO_DEPARTMENTS, iDepartments);
			editor.putInt(DbConsts.COMMITS_INFO_DOWNLOADS, iDownloads);
			editor.putInt(DbConsts.COMMITS_INFO_EXAMS, iExams);
			editor.putInt(DbConsts.COMMITS_INFO_FACULTY, iFaculty);
			editor.putInt(DbConsts.COMMITS_INFO_LECTURER, iLecturer);
			editor.putInt(DbConsts.COMMITS_INFO_NOTIFICATIONS, iNotifications);
			editor.putInt(DbConsts.COMMITS_INFO_SCHOOL_COURSES, iSchool_courses);
			editor.putInt(DbConsts.COMMITS_INFO_UPLOADS, iUploads);
			editor.putInt(DbConsts.COMMITS_INFO_STUDENTS,iStudents);
			
			editor.commit();
		
		
	}
	
	
			
}
