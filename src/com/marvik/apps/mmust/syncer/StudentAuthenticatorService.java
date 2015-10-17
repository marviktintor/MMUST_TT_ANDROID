package com.marvik.apps.mmust.syncer;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class StudentAuthenticatorService extends Service {
	SyncAuthenticator authenticator;
	

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		authenticator = new SyncAuthenticator(getApplicationContext());
	}

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return authenticator.getIBinder();
	}

}
