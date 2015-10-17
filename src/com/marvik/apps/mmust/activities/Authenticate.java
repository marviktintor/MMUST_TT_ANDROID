package com.marvik.apps.mmust.activities;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnHoverListener;
import android.view.View.OnTouchListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.marvik.apps.mmust.R;
import com.marvik.apps.mmust.utils.MmustUtils;
import com.marvik.apps.mmust.utils.StudentAuthenticator;

public class Authenticate extends Activity implements  OnClickListener, TextWatcher {
	EditText etRegno, etPassword;
	Button btAuth;
	ImageView ivPeek;
	MmustUtils utils;
	ProgressBar pbAuthenticating;
	TextView tvLoginFail;
	
	AuthReceiver receiver;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		setContentView(R.layout.authenticate);
		
		init();
		
	}

	private void init() {
		// TODO Auto-generated method stub
		utils = new MmustUtils(Authenticate.this);
		receiver = new AuthReceiver();
		
		etRegno = (EditText)findViewById(R.id.authenticate_editText_reg_no);
		etPassword = (EditText)findViewById(R.id.authenticate_editText_password);
		etPassword.addTextChangedListener(this);
		
		btAuth = (Button)findViewById(R.id.authenticate_button_auth);
		btAuth.setOnClickListener(this);
		
		ivPeek = (ImageView)findViewById(R.id.authenticate_imageView_view_password);
		ivPeek.setVisibility(ImageView.GONE);
		ivPeek.setOnClickListener(this);
		
		
		pbAuthenticating = (ProgressBar)findViewById(R.id.authenticate_progressBar_authenticating);
		pbAuthenticating.setVisibility(ProgressBar.GONE);
		
		tvLoginFail = (TextView)findViewById(R.id.authenticate_textView_login_fail);
		tvLoginFail.setVisibility(TextView.GONE);
		
		getRegisteredEmails();
		
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		unregisterReceiver(receiver);
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();

		registerReceiver(receiver, new IntentFilter(StudentAuthenticator.ACTION_STUDENT_LOGIN_SUCCESSFUL));
		registerReceiver(receiver, new IntentFilter(StudentAuthenticator.ACTION_STUDENT_LOGIN_FAILED));
	}
	
	
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if(v==btAuth){
			authStudent(utils.getString(etRegno),utils.getString(etPassword));
		}
		if(v==ivPeek){
			if(etPassword.getText().length()>0)
			{
				Toast.makeText(Authenticate.this, etPassword.getText().toString(), Toast.LENGTH_SHORT).show();
			}
		}
	}

	
	@Override
	public void beforeTextChanged(CharSequence s, int start, int count,
			int after) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onTextChanged(CharSequence s, int start, int before, int count) {
		// TODO Auto-generated method stub
		if(etPassword.getText().length()>1){
			ivPeek.setVisibility(ImageView.VISIBLE);
		}else{ ivPeek.setVisibility(ImageView.GONE); }
	}

	@Override
	public void afterTextChanged(Editable s) {
		// TODO Auto-generated method stub
		
	}
	private void authStudent(String reg_no, String password) {
		// TODO Auto-generated method stub
		if(!reg_no.equalsIgnoreCase("")&&!password.equalsIgnoreCase("")){
			btAuth.setVisibility(Button.GONE);
			pbAuthenticating.setVisibility(ProgressBar.VISIBLE);
			utils.authStudent(reg_no,password);
		}else{
			if(password.equalsIgnoreCase("")){
				etPassword.setError("Enter password");
			}
			if(reg_no.equalsIgnoreCase("")){
				etRegno.setError("Enter registation number");
			}
		}
	}
	private void getRegisteredEmails() {
		// TODO Auto-generated method stub
		AccountManager manager = (AccountManager)getSystemService(ACCOUNT_SERVICE);
		Account [] account = manager.getAccounts();
		final String [] emails = new String [account.length];
		int x=0;
		for(Account ac : account){
			emails[x]=ac.name;
			x++;
		}
		
		if(emails.length==0){
			return;
		}
		final Dialog alert = new Dialog(Authenticate.this, AlertDialog.THEME_HOLO_LIGHT);
		ListView lvEmails = new ListView(Authenticate.this);
		lvEmails.setAdapter(new ArrayAdapter<String>(Authenticate.this, R.layout.device_email_list, R.id.device_email_list_textView_email, emails));
		alert.setContentView(lvEmails);
		
		lvEmails.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				etPassword.setText(emails[position]);
				alert.dismiss();
			}
		});
		
		alert.show();
		alert.setCancelable(true);
		alert.setCanceledOnTouchOutside(true);
		alert.setTitle("Choose an email");
	}

	private class AuthReceiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
			String successful = StudentAuthenticator.ACTION_STUDENT_LOGIN_SUCCESSFUL;
			String failed = StudentAuthenticator.ACTION_STUDENT_LOGIN_FAILED;
			
			if(intent.getAction().equals(successful)){
				pbAuthenticating.setVisibility(ProgressBar.GONE);
				utils.startActivity(Dashboard.class);
				tvLoginFail.setVisibility(TextView.GONE);
				finish();
			}
			if(intent.getAction().equals(failed)){
				etPassword.setTextColor(Color.RED);
				etRegno.setTextColor(Color.RED);
				pbAuthenticating.setVisibility(ProgressBar.GONE);
				btAuth.setVisibility(Button.VISIBLE);
				tvLoginFail.setVisibility(TextView.VISIBLE);
			}
		}

}

	
}
