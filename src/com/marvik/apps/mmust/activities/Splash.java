package com.marvik.apps.mmust.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Html;
import android.text.Spanned;
import android.widget.Toast;

import com.marvik.apps.mmust.R;
import com.marvik.apps.mmust.prefs.PrefsHandler;
import com.marvik.apps.mmust.prefs.PrefsOptions;
import com.marvik.apps.mmust.utils.MmustUtils;

public class Splash extends Activity{
	MmustUtils utils;
	PrefsHandler prefsHandler;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splash);
		
		init();
		
		if(prefsHandler.isFirstrun()){ //insertData();//ADDEDD FOR DEVELOPER DEVELOPMENT PURPOSES
			utils.startActivity(Authenticate.class);
			finish();
		}else{
			if(utils.accountExists()){
				if(prefsHandler.isShowSplash()){
					showSplash();
				}else{ final Class<?> mClass = prefsHandler.getLaunchClass();
					utils.startActivity(mClass);
					finish();
				}
			}else{
				alertNoAccount();
			}
		}
	}
	
	private void init() {
		// TODO Auto-generated method stub
		utils = new MmustUtils(Splash.this);
		prefsHandler = new PrefsHandler(Splash.this);
	}
	private void showSplash() {
		// TODO Auto-generated method stub
		final Class<?> mClass = prefsHandler.getLaunchClass();
		
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				try {
					
					Thread.sleep(2000);
					utils.startActivity( mClass);
					finish();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			
			}

			
		}).start();
	}
	private void insertData() {
		// TODO Auto-generated method stub
		if (prefsHandler.isFirstrun()) {
			utils.onFirstRun();
		}
		
	}
private void alertNoAccount(){
	AlertDialog.Builder alert = new AlertDialog.Builder(Splash.this, AlertDialog.THEME_HOLO_LIGHT);
	alert.setTitle("Missing account");
	Spanned message = Html.fromHtml("<p>Your student account was <b style='color:FF0000;'>deleted</b> from the device but we saved all your app data. Please <u>reconnect</u> your student account " +
			"to continue using a live version of this app.<p style='color:CECECE;'> You are prone to outdated information<p></p>");
	alert.setMessage(message);
	alert.setIcon(R.drawable.ic_warning);
	alert.setPositiveButton("Reconnect", new DialogInterface.OnClickListener() {
		
		@Override
		public void onClick(DialogInterface dialog, int which) {
			// TODO Auto-generated method stub
			utils.startActivity(Authenticate.class);
			
		}
	});
	alert.setNegativeButton("Not now", new DialogInterface.OnClickListener() {
		
		@Override
		public void onClick(DialogInterface dialog, int which) {
			// TODO Auto-generated method stub
			utils.toastCustom("You will not be able to receive further updates", Toast.LENGTH_LONG, Color.green(160), 22f);
			if(prefsHandler.isShowSplash()){
				showSplash();
			}else{ final Class<?> mClass = prefsHandler.getLaunchClass();
				utils.startActivity(mClass);
				finish();
			}
		}
	});
	alert.create();
	alert.show();
}
}
