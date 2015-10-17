package com.marvik.apps.mmust.activities;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.marvik.apps.mmust.R;
import com.marvik.apps.mmust.api.MmustAPI;
import com.marvik.apps.mmust.database.Database;
import com.marvik.apps.mmust.database.DbConsts;
import com.marvik.apps.mmust.utils.MmustUtils;

public class Lecturers  extends MasterActivity implements OnItemClickListener{

	ListView lvLecturers;
	List<LecList>lLectList;
	View activityContentView;
	MmustUtils utils;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		activityContentView = getLayoutInflater().inflate(R.layout.lecturers, null, true);
		setContentView(getCustomContentView(activityContentView,"Lecturers"));
		
		init();
		
	}

	private void init() {
		// TODO Auto-generated method stub
		utils = new MmustUtils(Lecturers.this);
		
		lvLecturers = (ListView)activityContentView.findViewById(R.id.lecturers_listView_lec_list);
		populateLecList();
		lvLecturers.setOnItemClickListener(lecListClickListener);
	}
OnItemClickListener lecListClickListener = new OnItemClickListener() {
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
		
			String lecID = lLectList.get(position).getLecID();
		
			Bundle bundle = new Bundle();
			bundle.putString(MmustAPI.EXTRA_SELECTED_LEC_ID, lecID);
			utils.startActivity(LecturersInfo.class,bundle);
		
	}
};
	

	private void populateLecList() {
		// TODO Auto-generated method stub
		lLectList = new ArrayList<Lecturers.LecList>();
		
		Database db = new Database(Lecturers.this);
		SQLiteDatabase sql = db.getReadableDatabase();
		
		Cursor cursor = sql.query(DbConsts.LECS_TABLE, null, null, null, null,null,null);
		
		for(cursor.moveToFirst();!cursor.isAfterLast();cursor.moveToNext()){
			String lecName = cursor.getString(cursor.getColumnIndex(DbConsts.LECS_LEC_NAME));
			String lecAvator = cursor.getString(cursor.getColumnIndex(DbConsts.LEC_AVATOR_URI));
			String lecID = cursor.getString(cursor.getColumnIndex(DbConsts.LECS_LEC_ID));
			lLectList.add(new LecList(lecName, lecAvator,lecID));
		}
		cursor.close();
		sql.close();
		db.close();
		lvLecturers.setAdapter(new LecListAdapter());
	}
	private class LecListAdapter extends ArrayAdapter<LecList>{

		public LecListAdapter() {
			super(Lecturers.this, R.layout.lecturers_ui, lLectList);
			// TODO Auto-generated constructor stub
		}
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			View lecView = convertView;
			if(lecView==null){
				lecView = getLayoutInflater().inflate(R.layout.lecturers_ui, parent, false);
			}
			
			TextView tvLecName = (TextView)lecView.findViewById(R.id.lecturers_ui_textView_lec_name);
			ImageView ivLecAvator = (ImageView)lecView.findViewById(R.id.lecturers_ui_imageView_avator);
			
			LecList lecList = lLectList.get(position);
			tvLecName.setText(lecList.getLecName());
			ivLecAvator.setImageResource(R.drawable.person_1);
			return lecView;
		}
	}
	private class LecList{
		String lecName,lecAvator,lecID;

		public LecList(String lecName,  String lecAvator,String lecID) {
			super();
			this.lecName = lecName;
			this.lecID = lecID;
			this.lecAvator = lecAvator;
		}

		public String getLecName() {
			return lecName;
		}

		
		public String getLecID() {
			return lecID;
		}

		
		public String getLecAvator() {
			return lecAvator;
		}

		
		
	}
}
