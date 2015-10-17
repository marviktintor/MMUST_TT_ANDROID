package com.marvik.apps.mmust.activities;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.marvik.apps.mmust.R;
import com.marvik.apps.mmust.database.Database;
import com.marvik.apps.mmust.database.DbConsts;
import com.marvik.apps.mmust.database.DbQueryHandler;

public class Classes  extends MasterActivity implements OnItemClickListener,OnItemSelectedListener{

	Spinner spDay;
	ListView lvClasses;
	List<ClassesList> lClasses;
	View activityContentView;
	DbQueryHandler dbQueryHandler;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		activityContentView = getLayoutInflater().inflate(R.layout.classes, null, true);
		setContentView(getCustomContentView(activityContentView,"Classes"));
		initClasses();
	}

	private void initClasses() {
		// TODO Auto-generated method stub
		dbQueryHandler = new DbQueryHandler(Classes.this);
		lvClasses = (ListView)activityContentView.findViewById(R.id.classes_listView_classes);
		lvClasses.setOnItemClickListener(this);
		
		spDay = (Spinner)activityContentView.findViewById(R.id.classes_spinner_days);
		spDay.setOnItemSelectedListener(this);
		spDay.setSelection((new Date(System.currentTimeMillis()).getDay()-1)==-1||(new Date(System.currentTimeMillis()).getDay()-1)>4?0:(new Date(System.currentTimeMillis()).getDay()-1));
		
		
		
		//populateClassesList(Calendar.DAY_OF_WEEK);
	}

	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
		populateClassesList(position+1);
	}

	

	@Override
	public void onNothingSelected(AdapterView<?> parent) {
		// TODO Auto-generated method stub
		
	}

	
	private void populateClassesList(int day) {
		// TODO Auto-generated method stub
		Log.i("Today ", " is" +day);
		
		lClasses = new ArrayList<Classes.ClassesList>();
		
		Database db = new Database(Classes.this);
		SQLiteDatabase sql = db.getReadableDatabase();
		
		Cursor cursor = sql.query(DbConsts.CLASSES_TABLE, null, DbConsts.CLASS_DAYS+"="+day, null, null,null,null);
		
		for(cursor.moveToFirst();!cursor.isAfterLast();cursor.moveToNext()){
			String lecID = cursor.getString(cursor.getColumnIndex(DbConsts.CLASS_LEC_ID));
			String classTime = cursor.getString(cursor.getColumnIndex(DbConsts.CLASS_START))+" - " +cursor.getString(cursor.getColumnIndex(DbConsts.CLASS_STOP));
			String classRoom = cursor.getString(cursor.getColumnIndex(DbConsts.CLASS_ROOM));
			String classCourse = cursor.getString(cursor.getColumnIndex(DbConsts.CLASS_COURSE_CODE));
			
			String table = DbConsts.LECS_TABLE;
			String columnKey = DbConsts.LECS_LEC_ID;
			String key = lecID;
			String resultColumn = DbConsts.LECS_LEC_NAME;
			
			lClasses.add(new ClassesList(dbQueryHandler.getColumnIndexValue(table, columnKey, key, resultColumn), classTime, classRoom, classCourse));
		}
		cursor.close();
		sql.close();
		db.close();
		
		lvClasses.setAdapter(new ClassesAdapter());
	}
	
	private class ClassesAdapter extends ArrayAdapter<ClassesList>{

		public ClassesAdapter() {
			super(Classes.this, R.layout.classes_ui, lClasses);
			// TODO Auto-generated constructor stub
		}
		
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			View classView = convertView;
			if(classView==null){
				classView = getLayoutInflater().inflate(R.layout.classes_ui, parent, false);
			}
			ClassesList classesList = lClasses.get(position);
			
			TextView tvClassLect= (TextView)classView.findViewById(R.id.classes_ui_textView_class_lec);
			TextView tvClassRoom= (TextView)classView.findViewById(R.id.classes_ui_textView_class_room);
			TextView tvClassTime = (TextView)classView.findViewById(R.id.classes_ui_textView_class_time); 
			TextView tvClassCourse = (TextView)classView.findViewById(R.id.classes_ui_textView_class_course);
			
			tvClassCourse.setText(classesList.getClassCourse());
			tvClassRoom.setText(classesList.getClassRoom());
			tvClassTime.setText(classesList.getClassTime());
			tvClassLect.setText(classesList.getLecName());
			
			return classView;
		}
		
	}
	private class ClassesList {
		String lecName,classTime,classRoom,classCourse;

		ClassesList(String lecName, String classTime, String classRoom,
				String classCourse) {
			super();
			this.lecName = lecName;
			this.classTime = classTime;
			this.classRoom = classRoom;
			this.classCourse = classCourse;
		}

		public String getLecName() {
			return lecName;
		}

		public String getClassTime() {
			return classTime;
		}

		public String getClassRoom() {
			return classRoom;
		}

		public String getClassCourse() {
			return classCourse;
		}
		
	}
}
