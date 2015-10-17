package com.marvik.apps.mmust.activities;

import com.marvik.apps.mmust.R;

import android.os.Bundle;
import android.view.View;

public class Help extends MasterActivity {
	View activityContentView;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		activityContentView = getLayoutInflater().inflate(R.layout.help, null, true);
		setContentView(getCustomContentView(activityContentView,"Help"));

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
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}

}
