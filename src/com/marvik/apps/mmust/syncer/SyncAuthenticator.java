package com.marvik.apps.mmust.syncer;

import com.marvik.apps.mmust.activities.Authenticate;
import com.marvik.apps.mmust.utils.MmustUtils;

import android.accounts.AbstractAccountAuthenticator;
import android.accounts.Account;
import android.accounts.AccountAuthenticatorResponse;
import android.accounts.NetworkErrorException;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

public class SyncAuthenticator extends AbstractAccountAuthenticator {
	String TAG = "SyncAuthenticator";
	MmustUtils utils;

	public SyncAuthenticator(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		Log.i(TAG, "CONSTRUCTOR");
		utils = new MmustUtils(context);
	}

	@Override
	public Bundle editProperties(AccountAuthenticatorResponse response,
			String accountType) {
		// TODO Auto-generated method stub
		Log.i(TAG, "editProperties");
		return null;
	}

	@Override
	public Bundle addAccount(AccountAuthenticatorResponse response,
			String accountType, String authTokenType,
			String[] requiredFeatures, Bundle options)
			throws NetworkErrorException {
		// TODO Auto-generated method stub
		Log.i(TAG, "addAccount");
		utils.startActivity(Authenticate.class,Intent.FLAG_ACTIVITY_NEW_TASK);

		return null;
	}

	@Override
	public Bundle confirmCredentials(AccountAuthenticatorResponse response,
			Account account, Bundle options) throws NetworkErrorException {
		// TODO Auto-generated method stub
		Log.i(TAG, "confirmCredentials");
		return null;
	}

	@Override
	public Bundle getAuthToken(AccountAuthenticatorResponse response,
			Account account, String authTokenType, Bundle options)
			throws NetworkErrorException {
		// TODO Auto-generated method stub
		Log.i(TAG, "getAuthToken");
		return null;
	}

	@Override
	public String getAuthTokenLabel(String authTokenType) {
		// TODO Auto-generated method stub
		Log.i(TAG, "getAuthTokenLabel");
		return null;
	}

	@Override
	public Bundle updateCredentials(AccountAuthenticatorResponse response,
			Account account, String authTokenType, Bundle options)
			throws NetworkErrorException {
		// TODO Auto-generated method stub
		Log.i(TAG, "updateCredentials");
		return null;
	}

	@Override
	public Bundle hasFeatures(AccountAuthenticatorResponse response,
			Account account, String[] features) throws NetworkErrorException {
		// TODO Auto-generated method stub
		Log.i(TAG, "hasFeatures");
		return null;
	}

}
