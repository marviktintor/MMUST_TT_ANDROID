package com.marvik.apps.mmust.activities;

import android.os.Bundle;
import android.view.View;

import com.marvik.apps.mmust.R;

public class ContactUs  extends MasterActivity{

	View activityContentView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		activityContentView = getLayoutInflater().inflate(R.layout.contactus, null, true);
		setContentView(getCustomContentView(activityContentView,"Contact us"));
	}
}