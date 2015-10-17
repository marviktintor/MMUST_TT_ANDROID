package com.marvik.apps.mmust.test;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.marvik.apps.mmust.R;
import com.marvik.apps.mmust.api.MmustAPI;
import com.marvik.apps.mmust.utils.MmustUtils;
import com.marvik.apps.mmust.utils.StudentAuthenticator;

public class PopulateDatabase extends Activity {
	MmustAPI mmustAPI;
	MmustUtils utils;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.populate_database);
		
		init();
	}

	private void init() {
		// TODO Auto-generated method stub
		mmustAPI = new MmustAPI(PopulateDatabase.this);
		utils = new MmustUtils(PopulateDatabase.this);
		
		
		
	}

	public void exec(View view){
		utils.getJSONForURL(StudentAuthenticator.EXTRA_URL_READ_DOWNLOADS_AND_UPLOADS, false);
		//Toast.makeText(getApplicationContext(), "Not set", Toast.LENGTH_SHORT).show();
	}
}
