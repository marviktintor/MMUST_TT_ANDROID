package com.marvik.apps.mmust;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;


public class AppActivities extends Activity{
	
	ListView lvActivities;
	String [] activities;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.app_activities);
		
		lvActivities = (ListView)findViewById(R.id.app_activities_list);
		activities = new String []{"PopulateDatabase","Authenticate","BugReport","Classes",
				"ContactUs","Courses","Downloads","DownloadFileDetails","Help","Lecturers","More",
				"Notifications","Splash","MasterActivity","StudentInfo"};
		lvActivities.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, activities));
		lvActivities.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				try {
					Class <?> mClass = null;
					
					if(position==0){
						mClass = Class.forName("com.marvik.apps.mmust.test.PopulateDatabase");
					}else{mClass = Class.forName("com.marvik.apps.mmust.activities." +activities[position]);}
					
					startActivity(new Intent(getApplicationContext(), mClass));
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
		});
	}
	
	
}
