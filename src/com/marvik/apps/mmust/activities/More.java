package com.marvik.apps.mmust.activities;

import java.util.regex.Pattern;

import android.os.Bundle;
import android.util.Patterns;
import android.view.View;

import com.marvik.apps.mmust.R;

public class More extends MasterActivity {
	View activityContentView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		activityContentView = getLayoutInflater().inflate(R.layout.more,null, true);
		setContentView(getCustomContentView(activityContentView,"More"));
		
		Pattern pattern = Patterns.EMAIL_ADDRESS;
		
		
	}
}
