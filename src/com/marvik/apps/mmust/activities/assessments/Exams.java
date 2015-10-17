package com.marvik.apps.mmust.activities.assessments;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.marvik.apps.mmust.R;
import com.marvik.apps.mmust.database.Database;
import com.marvik.apps.mmust.database.DbConsts;

public class Exams extends Fragment {
	ListView lvAssesments;
	List<AssessmentList>lAssessmentList;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View layoutView = inflater.inflate(R.layout.assessments, container, false);
		lvAssesments = (ListView)layoutView.findViewById(R.id.assessments_listView_assessments);
		populateAssessments(2);
		return layoutView;
	}

	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
	}

	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}

	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}

	@Override
	public void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
	}

	@Override
	public void onDetach() {
		// TODO Auto-generated method stub
		super.onDetach();
	}

	@Override
	public void onDestroyView() {
		// TODO Auto-generated method stub
		super.onDestroyView();
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}
	
	private void populateAssessments(int assessment) {
		// TODO Auto-generated method stub
		
		
			lAssessmentList = new ArrayList<AssessmentList>();
			
			Database db = new Database(getActivity());
			SQLiteDatabase sql = db.getReadableDatabase();
			
			Cursor cursor = sql.query(DbConsts.EXAMS_TABLE, null, DbConsts.EXAM_TYPE+"="+assessment, null, null,null,null);
			
			for(cursor.moveToFirst();!cursor.isAfterLast();cursor.moveToNext()){
				String courseName = cursor.getString(cursor.getColumnIndex(DbConsts.EXAM_COURSE_CODE));
				String examRoom = cursor.getString(cursor.getColumnIndex(DbConsts.EXAM_ROOM));
				String examStart = cursor.getString(cursor.getColumnIndex(DbConsts.EXAM_START));
				String examDate = cursor.getString(cursor.getColumnIndex(DbConsts.EXAM_DATE));
				int examDuration = cursor.getInt(cursor.getColumnIndex(DbConsts.EXAM_DURATION));
			
				lAssessmentList.add(new AssessmentList(courseName, examRoom, examStart, examDuration));
			}
			cursor.close();
			sql.close();
			db.close();
			
			lvAssesments.setAdapter(new AssessmentsAdapter(getActivity(),R.layout.exams_ui, lAssessmentList));
		
	}
	 class AssessmentsAdapter extends ArrayAdapter<AssessmentList>{
		int resource;
		public AssessmentsAdapter(Activity activity, int resource,
				List<AssessmentList> objects) {
			super(activity, resource, objects);
			// TODO Auto-generated constructor stub
			this.resource = resource;
		}
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			View assessmentView = convertView;
			
			if(assessmentView ==null){
				assessmentView = getActivity().getLayoutInflater().inflate(resource, parent,false);
			}
			
			AssessmentList assessmentList = lAssessmentList.get(position);
			
				
				TextView tvCourseName= (TextView)assessmentView.findViewById(R.id.exams_ui_textView_exam_course);
				TextView tvInvigilator= (TextView)assessmentView.findViewById(R.id.exams_ui_textView_exam_duration);
				TextView tvExamRoom = (TextView)assessmentView.findViewById(R.id.exams_ui_textView_exam_room);
				TextView tvExamTime = (TextView)assessmentView.findViewById(R.id.exams_ui_textView_exam_time);
				
				tvCourseName.setText(assessmentList.getCourseName());
				tvInvigilator.setText(""+assessmentList.getExamDuration()+" hrs");
				tvExamRoom.setText(assessmentList.getExamRoom());
				tvExamTime.setText(assessmentList.getExamTime());
				
			return assessmentView;
		}
	}
	private class AssessmentList{
		/*Common*/
		String courseName;
		
		/* Assignments */
		String submissionDate,assignmentNo;
		
		/* C.A.T.S and MainsExam*/
		String examRoom,examTime;
		
		int examDuration;

		 AssessmentList(String courseName, String examRoom, String examTime,
				int examDuration) {
			this.courseName = courseName;
			this.examRoom = examRoom;
			this.examTime = examTime;
			this.examDuration = examDuration;
		}
		
		

		public String getCourseName() {
			return courseName;
		}


		public String getExamRoom() {
			return examRoom;
		}

		public String getExamTime() {
			return examTime;
		}

		public int getExamDuration() {
			return examDuration;
		}
		
		
	}
}
