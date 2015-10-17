package com.marvik.apps.mmust.syncer;

import com.marvik.apps.mmust.api.MmustAPI;
import com.marvik.apps.mmust.utils.StudentAuthenticator;

import android.accounts.Account;
import android.content.AbstractThreadedSyncAdapter;
import android.content.ContentProviderClient;
import android.content.Context;
import android.content.Intent;
import android.content.SyncResult;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

public class SyncAdapter extends AbstractThreadedSyncAdapter {
	String TAG = "SyncAdapter";
	MmustAPI mmustAPI;
	public SyncAdapter(Context context, boolean autoInitialize) {
		super(context, autoInitialize);
		// TODO Auto-generated constructor stub
		Intent intent = new Intent(StudentAuthenticator.ACTION_SYNC_STARTED);
		context.sendBroadcast(intent);
		Log.i(TAG, "CONSTRUCTOR");
		mmustAPI = new MmustAPI(context);
		
	}

	@Override
	public void onPerformSync(Account account, Bundle extras, String authority,
			ContentProviderClient provider, SyncResult syncResult) {
		// TODO Auto-generated method stub
		Log.i(TAG, "onPerformSync");
		mmustAPI.performSync(account,extras,authority,provider,syncResult);
		
	}

}
