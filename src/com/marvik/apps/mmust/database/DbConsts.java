package com.marvik.apps.mmust.database;

public class DbConsts {
	/**
	 * Database
	 */

	public static final String MMUST_DATABASE="MmustTimetable";
	public static final int MMUST_DATABASE_VERSION = 1;
	public static final String COL_ID = "_id";
	
	/*	public static final String _DEPARTMENT_ID = "department_id";
	public static final String _STUDENT_ADM_YR = "student_adm_yr";
	public static final String _FACULTY_ID = "faculty_id";
	public static final String _CAMPUS_ID = "campus_id";
	public static final String _SCHOOL_COURSE_CODE = "school_course_code";*/
	
	/**
	 * Assignment
	 * 
	 * `assignments`(`course_code`, `submission_date`, `assignment_no`, `department_id`, 
	 * `student_adm_yr`, `faculty_id`, `campus_id`, `school_course_code`)
	 */
	public static final String ASSIGNMENTS_TABLE = "Assignments";
	public static final String ASSIGNMENTS_COURSE_CODE = "course_code";
	public static final String ASSIGNMENTS_SUBMISSION_DATE = "submission_date";
	public static final String ASSIGNMENTS_ASSIGNMENT_NO = "assignment_no";


	/**
	 * Campus 
	 * 
	 * `campus`(`campus_id`, `campus_name`)
	 */
	public static final String CAMPUS_TABLE = "Campus"; 
	public static final String CAMPUS_ID = "campus_id";
	public static final String CAMPUS_NAME = "campus_name";
	
	/**
	 * Classes
	 * 
	 * `classes`(`course_code`, `lec_id`, `class_room`, `class_start`, `class_stop`, `class_days`, `student_adm_yr`,
	 *  `department_id`, `faculty_id`, `campus_id`, `school_course_id`)
	 */

	public static final String CLASSES_TABLE = "Classes"; 
	public static final String CLASS_COURSE_CODE = "course_code";
	public static final String CLASS_LEC_ID = "lec_id";
	public static final String CLASS_ROOM = "class_room";
	public static final String CLASS_START = "class_start";
	public static final String CLASS_STOP = "class_stop";
	public static final String CLASS_DAYS="class_days";

	/*
	 *Commits 
	 *
	 *`commits_info`(`commits`, `assignments`, `campus`, `classes`, `courses`, `departments`, `downloads`, 
	 *`exams`, `faculty`, `lecturer`, `notifications`, `school_courses`, `students`, `uploads`)
	 */
	
	public static final String COMMITS_INFO_TABLE="commits_info";
	public static final String COMMITS_INFO_COMMITS="commits";
	public static final String COMMITS_INFO_ASSIGNMENTS="assignments";
	public static final String COMMITS_INFO_CAMPUS="campus";
	public static final String COMMITS_INFO_CLASSES="classes";
	public static final String COMMITS_INFO_COURSES="courses";
	public static final String COMMITS_INFO_DEPARTMENTS="departments";
	public static final String COMMITS_INFO_DOWNLOADS="downloads";
	public static final String COMMITS_INFO_EXAMS="exams";
	public static final String COMMITS_INFO_FACULTY="faculty";
	public static final String COMMITS_INFO_LECTURER="lecturer";
	public static final String COMMITS_INFO_NOTIFICATIONS="notifications";
	public static final String COMMITS_INFO_SCHOOL_COURSES="school_courses";
	public static final String COMMITS_INFO_STUDENTS="students";
	public static final String COMMITS_INFO_UPLOADS="uploads";
	
	/**
	 * Courses
	 * 
	 * `courses`(`course_code`, `course_name`, `lec_id`, `department_id`, `faculty_id`,
	 *  `campus_id`, `student_adm_yr`, `school_course_code`)
	 */

	public static final String COURSES_TABLE = "Courses";  
	public static final String COURSES_COURSE_CODE = "course_code";
	public static final String COURSES_COURSE_NAME = "course_name";
	public static final String COURSES_COURSE_LEC_ID = "lec_id";

	/**Departments
	 * 
	 * `departments`(`department_id`, `department_name`, `faculty_id`, `campus_id`)
	 */
	
	public static final String DEPARTMENT_TABLE = "Departments";
	public static final String DEPARTMENT_ID = "department_id";
	public static final String DEPARTMENT_NAME = "department_name";
	public static final String DEPARTMENT_FACULTY_ID = "faculty_id";
	public static final String DEPARTMENT_CAMPUS_ID = "campus_id";
	
	/**
	 * Downloads
	 * 
	 *  `downloads`(`course_code`, `fileid`, `department_id`, 
	 *  `faculty_id`, `campus_id`, `student_adm_yr`, `school_course`) 
	 * 
	 */

	public static final String DOWNLOADS_TABLE = "Downloads";
	public static final String DOWNLOAD_COURSE_CODE = "course_code";
	public static final String DOWNLOAD_FILE_ID = "fileid";
	
	

	/**
	 * Exams
	 * 
	 * `exams`(`course_code`, `department_id`, `exam_type`, `exam_room`, `exam_date`, `exam_start`,
	 *  `exam_duration`, `faculty_id`, `campus_id`, `student_adm_yr`, `school_course_code`)
	 */

	public static final String EXAMS_TABLE = "Exams"; 
	public static final String EXAM_COURSE_CODE = "course_code";
	public static final String EXAM_TYPE= "exam_type";
	public static final String EXAM_ROOM = "exam_room";
	public static final String EXAM_DATE = "exam_date";
	public static final String EXAM_START = "exam_start";
	public static final String EXAM_DURATION = "exam_duration";
	
	
	/**
	 * Faculty
	 * 
	 * `faculty`(`faculty_id`, `faculty_name`, `campus_id`) 
	 */
	public static final String FACULTY_TABLE = "Faculty";
	public static final String FACULTY_ID = "faculty_id";
	public static final String FACULTY_NAME = "faculty_name";
	public static final String FACULTY_CAMPUS_ID = "campus_id";
	
	/**
	 * Lec Info
	 * 
	 * `lecturer`(`lec_id`, `lec_name`, `lec_email`, `lec_phone`, `lec_avator_uri`, `lec_qualification`)
	 */
	public static final String LECS_TABLE = "Lecturers";
	public static final String LECS_LEC_ID = "lec_id";
	public static final String LECS_LEC_NAME = "lec_name";
	public static final String LEC_AVATOR_URI = "lec_avator_uri";
	public static final String LECS_LEC_EMAIL = "lec_email";
	public static final String LECS_LEC_PHONE = "lec_phone";
	public static final String LEC_QUALIFICATIONS = "lec_qualification";
	
	/**
	 * Notifications Table
	 * 
	 * `notifications`(`notification_id`, `notification_title`, `notification_message`, `notification_send_time`,
	 *  `notification_sender`,`department_id`, `faculty_id`, `campus_id`, `student_adm_yr`, `school_course`)
	 */
	public static final String NOTIFICATION_TABLE = "Notifications";
	public static final String NOTIFICATION_ID = "notification_id";
	public static final String NOTIFICATION_TITLE = "notification_title";
	public static final String NOTIFICATION_MESSAGE = "notification_message";
	public static final String NOTIFICATION_SEND_TIME = "notification_send_time";
	public static final String NOTIFICATION_SENDER = "notification_sender";
	
	/**
	 * School courses
	 * 
	 * `school_courses`(`course_code`, `course_name`, `department_id`, `faculty_id`, `campus_id`)
	 */
	
	public static final String SCHOOL_COURSES_TABLE = "SchoolCourses";
	public static final String SCHOOL_COURSES_COURSE_CODE = "course_code";
	public static final String SCHOOL_COURSES_COURSE_NAME = "course_name";
	
	/**
	 * Student info
	 * 
	 * `students`(`reg_no`, `full_name`, `pursuing_course`, `department_id`, 
	 * `phonenumber`, `email`, `student_category`, `adm_yr`, `faculty_id`, `campus_id`)
	 * 
	 */
	public static final String STUDENT_INFO_TABLE = "StudentInfo";
	public static final String STUDENT_REG_NO = "reg_no";
	public static final String STUDENT_FULL_NAME = "full_name";
	public static final String STUDENT_EMAIL = "email_address";
	public static final String STUDENT_PHONENUMBER = "phonenumber";
	public static final String STUDENT_CATEGORY = "student_category";
	public static final String STUDENT_PURSUING_COURSE = "pursuing_course";
	public static final String STUDENT_DEPARTMENT_ID = "department_id";
	public static final String STUDENT_STUDENT_ADM_YR = "student_adm_yr";
	public static final String STUDENT_FACULTY_ID = "faculty_id";
	public static final String STUDENT_CAMPUS_ID = "campus_id";
	
	/**
	 * Uploads
	 * 
	 * `fileid`, `filename`, `filesize`, `filedesc`, `fileuploader`, `file_upload_time`, `fileuri`
	 * 
	 */
	public static final String UPLOADS_TABLE = "Uploads";
	public static final String UPLOADS_FILE_ID = "fileid";
	public static final String UPLOADS_FILENAME = "filename";
	public static final String UPLOADS_FILESIZE = "filesize";
	public static final String UPLOADS_FILE_DESC = "filedesc";
	public static final String UPLOADS_FILE_UPLOADER = "fileuploader";
	public static final String UPLOADS_FILE_UPLOAD_TIME = "file_upload_time";
	public static final String UPLOADS_FILE_URI = "fileuri";
	
}
