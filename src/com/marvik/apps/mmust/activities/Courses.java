package com.marvik.apps.mmust.activities;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.marvik.apps.mmust.R;
import com.marvik.apps.mmust.database.Database;
import com.marvik.apps.mmust.database.DbConsts;
import com.marvik.apps.mmust.database.DbQueryHandler;
import com.marvik.apps.mmust.provider.DataProvider;

public class Courses  extends MasterActivity{

	List<CourseList>courseList;
	ListView lvCourses;
	View activityContentView;
	DbQueryHandler dbQueryHandler;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		activityContentView = getLayoutInflater().inflate(R.layout.courses, null, true);
		setContentView(getCustomContentView(activityContentView,"Courses"));
		
		init();
	}
	
	private void init() {
		// TODO Auto-generated method stub
		dbQueryHandler = new DbQueryHandler(Courses.this);
		
		lvCourses = (ListView)activityContentView.findViewById(R.id.courses_listView_courseList);
		populateCourses();
	}

	private void populateCourses() {
		// TODO Auto-generated method stub
		courseList = new ArrayList<CourseList>();
		Database db = new Database(Courses.this);
		SQLiteDatabase sql = db.getReadableDatabase();
		
		Cursor cursor = sql.query(DbConsts.COURSES_TABLE, null, null, null, null,null,null);
		
		for(cursor.moveToFirst();!cursor.isAfterLast();cursor.moveToNext()){
			String courseName = cursor.getString(cursor.getColumnIndex(DbConsts.COURSES_COURSE_NAME));
			String courseCode = cursor.getString(cursor.getColumnIndex(DbConsts.COURSES_COURSE_CODE));
			String courseLecID = cursor.getString(cursor.getColumnIndex(DbConsts.COURSES_COURSE_LEC_ID));
			
			courseList.add(new CourseList(courseName, courseCode, dbQueryHandler.getColumnIndexValue(DbConsts.LECS_TABLE, DbConsts.LECS_LEC_ID, courseLecID,DbConsts.LECS_LEC_NAME)));
		}
		cursor.close();
		sql.close();
		db.close();
		lvCourses.setAdapter(new CourseAdapter());
	}

	

	private class CourseAdapter extends ArrayAdapter<CourseList>{

		public CourseAdapter() {
			super(Courses.this, R.layout.courses_ui, courseList);
			// TODO Auto-generated constructor stub
		}
		
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			View courseView = convertView;
			if(courseView==null){
				courseView = getLayoutInflater().inflate(R.layout.courses_ui, parent, false);
			}
			
			TextView tvCourseName= (TextView)courseView.findViewById(R.id.courses_ui_textView_course_name);
			TextView tvCourseCode= (TextView)courseView.findViewById(R.id.courses_ui_textView_course_code);
			TextView tvCourseDays = (TextView)courseView.findViewById(R.id.courses_ui_textView_course_lec);
			
			CourseList cList = courseList.get(position);
			
			tvCourseName.setText(cList.getCourseName());
			tvCourseCode.setText(cList.getCourseCode());
			tvCourseDays.setText(cList.getCourseLec());
			
			return courseView;
		}
	}
	 class CourseList{
		String courseName,courseCode,courseLec;

		public CourseList(String courseName, String courseCode,
				String courseDays) {
			super();
			this.courseName = courseName;
			this.courseCode = courseCode;
			this.courseLec = courseDays;
		}

		public String getCourseName() {
			return courseName;
		}

		public void setCourseName(String courseName) {
			this.courseName = courseName;
		}

		public String getCourseCode() {
			return courseCode;
		}

		public void setCourseCode(String courseCode) {
			this.courseCode = courseCode;
		}

		public String getCourseLec() {
			return courseLec;
		}

		public void setCourseLec(String courseLec) {
			this.courseLec = courseLec;
		}
		
	}
}
