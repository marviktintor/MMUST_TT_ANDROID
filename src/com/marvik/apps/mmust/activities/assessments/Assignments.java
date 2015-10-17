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

public class Assignments extends Fragment {
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
		populateAssessments();
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
	
	private void populateAssessments() {
		// TODO Auto-generated method stub
		lAssessmentList = new ArrayList<AssessmentList>();
		
		Database db = new Database(getActivity());
		SQLiteDatabase sql = db.getReadableDatabase();
		
		Cursor cursor = sql.query(DbConsts.ASSIGNMENTS_TABLE, null, null, null, null,null,null);
		
		for(cursor.moveToFirst();!cursor.isAfterLast();cursor.moveToNext()){
			String courseName = cursor.getString(cursor.getColumnIndex(DbConsts.ASSIGNMENTS_COURSE_CODE));
			String submissionDate = cursor.getString(cursor.getColumnIndex(DbConsts.ASSIGNMENTS_SUBMISSION_DATE));
			String assignmentNo = cursor.getString(cursor.getColumnIndex(DbConsts.ASSIGNMENTS_ASSIGNMENT_NO));
			
			lAssessmentList.add(new AssessmentList(courseName, submissionDate, assignmentNo));
		}
		cursor.close();
		sql.close();
		db.close();
		
		lvAssesments.setAdapter(new AssessmentsAdapter(getActivity(),R.layout.assignments_ui, lAssessmentList));
		
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
			
			TextView tvCourse= (TextView)assessmentView.findViewById(R.id.assignment_textView_assignment_course);
			TextView tvSubmissionDate= (TextView)assessmentView.findViewById(R.id.assignment_textView_assignment_submission_date);
			TextView tvAssignmentNo = (TextView)assessmentView.findViewById(R.id.assignment_textView_assignment_no);
			
			tvCourse.setText(assessmentList.getCourseName());
			tvSubmissionDate.setText(assessmentList.getSubmissionDate());
			tvAssignmentNo.setText(assessmentList.getAssignmentNo());
				
			return assessmentView;
		}
	}
	private class AssessmentList{
		/*Common*/
		String courseName;
		
		/* Assignments */
		String submissionDate,assignmentNo;
		 AssessmentList(String courseName, String submissionDate,
					String assignmentNo) {
				this.courseName = courseName;
				this.submissionDate = submissionDate;
				this.assignmentNo = assignmentNo;

			}

		public String getCourseName() {
			return courseName;
		}

		public String getSubmissionDate() {
			return submissionDate;
		}

		public String getAssignmentNo() {
			return assignmentNo;
		}
		
	}
}
