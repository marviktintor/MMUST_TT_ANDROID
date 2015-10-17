package com.marvik.apps.mmust.activities;

import com.marvik.apps.mmust.R;

import android.os.Bundle;
import android.view.View;

public class BugReport  extends MasterActivity{

	View activityContentView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		activityContentView = getLayoutInflater().inflate(R.layout.bugreport, null, true);
		setContentView(getCustomContentView(activityContentView,"Bug report"));
	}
}
