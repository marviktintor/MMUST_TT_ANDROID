package com.marvik.apps.mmust.utils;

public class StudentAuthenticator {
	public static final String STUDENT_CAMPUS = "student_campus";
	public static final String STUDENT_FACULTY= "student_faculty";
	public static final String STUDENT_DEPARTMENT= "student_department";
	public static final String STUDENT_ADM_YR = "student_adm_yr";
	public static final String STUDENT_PURSUING_COURSE = "student_pursuing_course";
	
	public static final String STUDENT_REG_NO = "student_reg_no";
	public static final String STUDENT_PASSWORD = "student_password";

	public static final String ACTION_SYNC_CANCELLED = "com.marvik.apps.mmust.ACTION_SYNC_CANCELLED";
	public static final String ACTION_SYNC_ENDED = "com.marvik.apps.mmust.ACTION_SYNC_ENDED";
	public static final String ACTION_SYNC_ERROR = "com.marvik.apps.mmust.ACTION_SYNC_ERROR";
	public static final String ACTION_SYNC_RESULT_ALERT_TIMEOUT = "com.marvik.apps.mmust.ACTION_SYNC_RESULT_ALERT_TIMEOUT";
	public static final String ACTION_SYNC_STARTED = "com.marvik.apps.mmust.ACTION_SYNC_STARTED";

	// Student Login
	protected static final String LOGIN_POST_LOGIN_STUDENT = "login_student";
	protected static final String LOGIN_POST_REG_NO = "reg_no";
	protected static final String LOGIN_POST_PASSWORD = "email";
	public static final String ACTION_STUDENT_LOGIN_SUCCESSFUL = "com.marvik.apps.mmust.ACTION_LOGIN_SUCCESSFUL";
	public static final String ACTION_STUDENT_LOGIN_FAILED = "com.marvik.apps.mmust.ACTION_LOGIN_FAILED";
	public static final String ACTION_NO_STUDENT_ACCOUNT_FOUND = "com.marvik.apps.mmust.ACTION_NO_STUDENT_ACCOUNT_FOUND";
	
	
	//URLS
	public static final String ACTION_HTTP_REQUEST_ERROR = "com.marvik.apps.mmust.ACTION_HTTP_REQUEST_ERROR";
	public static final String EXTRA_HTTP_REQUEST_URL = "com.marvik.apps.mmust.EXTRA_HTTP_REQUEST_URL";
	public static final String EXTRA_HTTP_CONN_STATUS_CODE = "status_code";
	public static final String EXTRA_URL_BASIC_STUDENT_INFO = "getstudentinfo.php";
	public static final String EXTRA_URL_ADVANCED_SCHOOL_INFO = "advancedschoolinfo.php";
	public static final String EXTRA_URL_COMMITS_INFO = "getcommits.php";
	
	public static final String EXTRA_URL_READ_ASSESSMENTS = "read_assessments.php";
	public static final String EXTRA_URL_READ_ASSESSMENTS_TYPE_ASSIGNMENTS = "read_assessments.php";
	public static final String EXTRA_URL_READ_ASSESSMENTS_TYPE_CATS = "read_assessments.php";
	public static final String EXTRA_URL_READ_ASSESSMENTS_TYPE_EXAMS = "read_assessments.php";
	public static final String EXTRA_URL_READ_CLASSES = "read_classes.php";
	public static final String EXTRA_URL_READ_COURSES = "read_courses.php";
	public static final String EXTRA_URL_READ_LECTURER = "read_lecturer.php";
	public static final String EXTRA_URL_READ_NOTIFICATIONS = "read_notifications.php";
	public static final String EXTRA_URL_READ_DOWNLOADS_AND_UPLOADS="read_downloads.php";
	
	// POST PARAMS
	public static final String POST_REG_NO = "reg_no";
	public static final String POST_CLIENT = "client";
	public static final String POST_CLIENT_MOBILE = "android_mobile";

	public static final String POST_ADVANCED_SCHOOL_INFO = "advancedschoolinfo";

	public static final String POST_READ_DOWNLOADS ="read_downloads";
	
	public static final String POST_PURSUING_COURSE = "pursuing_course";
	public static final String POST_DEPARTMENT_ID = "department_id";
	public static final String POST_FACULTY_ID = "faculty_id";
	public static final String POST_CAMPUS_ID = "campus_id";
	public static final String POST_ADM_YR = "adm_yr";
	
	public static final String POST_ASSESSMENT_TYPE = "assessment";
	public static final int POST_ASSESSMENT_TYPE_ASSIGNMENTS = 0;
	public static final int POST_ASSESSMENT_TYPE_CAT = 1;// ANY INTEGER
	public static final int POST_ASSESSMENT_TYPE_EXAMS = 2;// ANY INTEGER
	

}
