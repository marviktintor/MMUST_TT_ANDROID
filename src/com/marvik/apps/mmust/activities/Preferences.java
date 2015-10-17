package com.marvik.apps.mmust.activities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import com.marvik.apps.mmust.R;
import com.marvik.apps.mmust.prefs.PrefsHandler;
import com.marvik.apps.mmust.prefs.PrefsOptions;
import com.marvik.apps.mmust.utils.MmustUtils;
import com.marvik.apps.mmust.utils.StudentAuthenticator;

public class Preferences extends MasterActivity implements OnCheckedChangeListener,OnItemSelectedListener{
	PrefsHandler prefsHandler;
	PreferencesReceiver receiver;
	
	View activityContentView;
	MmustUtils utils;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		activityContentView = getLayoutInflater().inflate(R.layout.preferences, null, true);
		setContentView(getCustomContentView(activityContentView,"Preferences"));
		
		
		init();
		
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		registerReceivers();
		if(!utils.accountExists()){
			btSync.setText("Add Student Account");
		}
	}
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		savePreferences();
		unregisterReceivers();
	}
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		savePreferences();
	}
	

	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
		itemSelected(parent,position);
	}

	@Override
	public void onNothingSelected(AdapterView<?> parent) {
		// TODO Auto-generated method stub
		nothingSelected(parent);
	}
	
	

	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		// TODO Auto-generated method stub
		checkChange(buttonView,isChecked);
	}
	@Override
	public void onClick(View view) {
		// TODO Auto-generated method stub
		super.onClick(view);
		onViewClicked(view);
	}
	private void readPreferences() {
		// TODO Auto-generated method stub
		
		// Seasons
		sSeason.setChecked(prefsHandler.isSeasonEnabled());
		spSeason.setSelection(prefsHandler.getSeason()); spSeason.setEnabled(prefsHandler.isSeasonEnabled());
		cbNavigateToBestSeason.setChecked(prefsHandler.isNavigateToBestSeason()); cbNavigateToBestSeason.setEnabled(prefsHandler.isSeasonEnabled());
		
		//Navigation
		cbKeepBackstack.setChecked(prefsHandler.isKeepBackStack());
		cbShowSplash.setChecked(prefsHandler.isShowSplash());
		cbNavigateToHome.setChecked(prefsHandler.isHomeEnabled());
		cbNotificationBarShortcuts.setChecked(prefsHandler.isNotificationBarShortcutsEnabled());
		
		//Sync
		sSync.setChecked(prefsHandler.isSyncEnabled());
		spSyncInterval.setSelection(prefsHandler.getSyncInterval()); spSyncInterval.setEnabled(prefsHandler.isSyncEnabled());
		cbAlertOnNewData.setChecked(prefsHandler.isAlertOnSync()); cbAlertOnNewData.setEnabled(prefsHandler.isSyncEnabled());
		btSync.setEnabled(prefsHandler.isSyncEnabled());
		
		// Reminder
		sReminder.setChecked(prefsHandler.isEnableReminders());
		cbClasses.setChecked(prefsHandler.isEnableClassesReminder());cbClasses.setEnabled(prefsHandler.isEnableReminders());
		cbAssignments.setChecked(prefsHandler.isEnableAssignmentsReminder());cbAssignments.setEnabled(prefsHandler.isEnableReminders());
		cbCats.setChecked(prefsHandler.isEnableCatsReminder());cbCats.setEnabled(prefsHandler.isEnableReminders());
		cbEOS.setChecked(prefsHandler.isEnableEOSReminder());cbEOS.setEnabled(prefsHandler.isEnableReminders());
		
		spClasses.setSelection(prefsHandler.getClassesReminderInterval());spClasses.setEnabled(prefsHandler.isEnableClassesReminder());
		spAssignments.setSelection(prefsHandler.getAssignmnetsReminderInterval());spAssignments.setEnabled(prefsHandler.isEnableAssignmentsReminder());
		spCats.setSelection(prefsHandler.getCatsReminderInterval());spCats.setEnabled(prefsHandler.isEnableCatsReminder());
		spEOS.setSelection(prefsHandler.getEOSReminderInterval());spEOS.setEnabled(prefsHandler.isEnableEOSReminder());
		
		//Notifications
		sNotifications.setChecked(prefsHandler.isAppNotificationsEnabled());
		cbNewDownloadItems.setChecked(prefsHandler.isDownloadsEnabled()); cbNewDownloadItems.setEnabled(prefsHandler.isAppNotificationsEnabled());
		cbSentNotifications.setChecked(prefsHandler.isShowSentNotificationsEnabled()); cbSentNotifications.setEnabled(prefsHandler.isAppNotificationsEnabled());
		
		//DeveloperSettings
		sDeveloperMode.setChecked(prefsHandler.isDeveloperMode());
		etHostAddress.setText(prefsHandler.getHostAddress());etHostAddress.setEnabled(prefsHandler.isDeveloperMode());
		etWifiPassword.setText(prefsHandler.getWifiPassword());etWifiPassword.setEnabled(prefsHandler.isDeveloperMode());
		etWifiSSID.setText(prefsHandler.getWifiSSID());etWifiSSID.setEnabled(prefsHandler.isDeveloperMode());
		btConnect.setEnabled(prefsHandler.isDeveloperMode());
		
	}
	private void savePreferences() { //For EditTexts that are not saved on the fly as updates on the UI happen
		// TODO Auto-generated method stub
		prefsHandler.setWifiPassword(utils.getString(etWifiPassword));
		prefsHandler.setWifiSSID(utils.getString(etWifiSSID));
		prefsHandler.setHostAddress(utils.getString(etHostAddress));
	}
	
	// Seasons
	Switch sSeason;
	Spinner spSeason;
	CheckBox cbNavigateToBestSeason;
	
	//Navigation
	CheckBox cbShowSplash,cbKeepBackstack,cbNavigateToHome,cbNotificationBarShortcuts;
	
	// Sync
	Switch sSync;
	Spinner spSyncInterval;
	CheckBox cbAlertOnNewData;
	Button btSync;

	// Reminder
	Switch sReminder;
	CheckBox cbClasses,cbAssignments,cbCats,cbEOS;
	Spinner spClasses,spAssignments,spCats,spEOS;
	
	// Notifications
	Switch sNotifications;
	CheckBox cbNewDownloadItems, cbSentNotifications;
		
	//Developer Mode
	Switch sDeveloperMode;
	Button btConnect;
	EditText etHostAddress,etWifiPassword,etWifiSSID;
	
	
	
	private void init() {
		// TODO Auto-generated method stub
		prefsHandler = new PrefsHandler(Preferences.this);
		utils = new MmustUtils(Preferences.this);
		receiver = new PreferencesReceiver();
		
		// Seasons
		sSeason = (Switch)activityContentView.findViewById(R.id.preferences_switch_season);
		spSeason = (Spinner)activityContentView.findViewById(R.id.preferences_spinner_season);
		cbNavigateToBestSeason = (CheckBox)activityContentView.findViewById(R.id.preferences_checkBox_navigate_to_best_season);
		
		//Navigation
		cbShowSplash = (CheckBox)activityContentView.findViewById(R.id.preferences_checkBox_show_splash);
		cbKeepBackstack = (CheckBox)activityContentView.findViewById(R.id.preferences_checkBox_keep_backstack);
		cbNavigateToHome= (CheckBox)activityContentView.findViewById(R.id.preferences_checkBox_navigate_to_home);
		cbNotificationBarShortcuts  = (CheckBox)activityContentView.findViewById(R.id.preferences_checkBox_notification_bar_shortcuts);
		
		// Sync
		sSync = (Switch)activityContentView.findViewById(R.id.preferences_switch_sync);
		spSyncInterval  = (Spinner)activityContentView.findViewById(R.id.preferences_spinner_sync_interval);
		cbAlertOnNewData = (CheckBox)activityContentView.findViewById(R.id.preferences_checkBox_alert_on_sync);
		btSync = (Button)activityContentView.findViewById(R.id.preferences_button_sync);
		btSync.setOnClickListener(this);
		
		if (prefsHandler.isSyncing()) {
			btSync.setText("Syncing..Click to stop");
		}

		// Reminder
		sReminder = (Switch)activityContentView.findViewById(R.id.preferences_switch_reminders);
		cbClasses= (CheckBox)activityContentView.findViewById(R.id.preferences_checkBox_reminder_classes);
		cbAssignments= (CheckBox)activityContentView.findViewById(R.id.preferences_checkBox_reminder_assignments);
		cbCats= (CheckBox)activityContentView.findViewById(R.id.preferences_checkBox_reminder_cats);
		cbEOS= (CheckBox)activityContentView.findViewById(R.id.preferences_checkBox_reminder_eos);
		
		spClasses  = (Spinner)activityContentView.findViewById(R.id.preferences_spinner_reminder_classes);
		spAssignments  = (Spinner)activityContentView.findViewById(R.id.preferences_spinner_reminder_assignments);
		spCats  = (Spinner)activityContentView.findViewById(R.id.preferences_spinner_reminder_cats);
		spEOS  = (Spinner)activityContentView.findViewById(R.id.preferences_spinner_reminder_eos);

		// Notifications
		sNotifications = (Switch)activityContentView.findViewById(R.id.preferences_switch_notifications);
		cbNewDownloadItems = (CheckBox)activityContentView.findViewById(R.id.preferences_checkBox_new_downloads);
		cbSentNotifications = (CheckBox)activityContentView.findViewById(R.id.preferences_checkBox_sent_notifications);
				
		//DeveloperMode
		sDeveloperMode = (Switch)activityContentView.findViewById(R.id.preferences_switch_developer_mode);
		btConnect = (Button)activityContentView.findViewById(R.id.preferences_button_connect);
		etHostAddress = (EditText)activityContentView.findViewById(R.id.preferences_editText_host_address);
		etWifiPassword = (EditText)activityContentView.findViewById(R.id.preferences_editText_wifi_password);
		etWifiSSID = (EditText)activityContentView.findViewById(R.id.preferences_editText_wifi_ssid);
		
		addSettingsChangeListener();
		readPreferences();
	}


	private void addSettingsChangeListener() {
		// TODO Auto-generated method stub
		// Seasons
		sSeason.setOnCheckedChangeListener(this);
		spSeason.setOnItemSelectedListener(this);
		cbNavigateToBestSeason.setOnCheckedChangeListener(this);
		
		//Navigation
		cbShowSplash.setOnCheckedChangeListener(this);
		cbKeepBackstack.setOnCheckedChangeListener(this);
		cbNavigateToHome.setOnCheckedChangeListener(this);
		cbNotificationBarShortcuts.setOnCheckedChangeListener(this);
		
		// Sync
		sSync.setOnCheckedChangeListener(this);
		spSyncInterval.setOnItemSelectedListener(this);
		cbAlertOnNewData.setOnCheckedChangeListener(this);

		// Reminder
		sReminder.setOnCheckedChangeListener(this);
		cbClasses.setOnCheckedChangeListener(this);
		cbAssignments.setOnCheckedChangeListener(this);
		cbCats.setOnCheckedChangeListener(this);
		cbEOS.setOnCheckedChangeListener(this);
		
		spClasses.setOnItemSelectedListener(this);
		spAssignments.setOnItemSelectedListener(this);
		spCats.setOnItemSelectedListener(this);
		spEOS.setOnItemSelectedListener(this);

		// Notifications
		sNotifications.setOnCheckedChangeListener(this);
		cbNewDownloadItems.setOnCheckedChangeListener(this);
		cbSentNotifications.setOnCheckedChangeListener(this);
		
		//DeveloperMode
		sDeveloperMode.setOnCheckedChangeListener(this);
		btConnect.setOnClickListener(this);
	}


	

	private void checkChange(CompoundButton buttonView, boolean isChecked) {
		// TODO Auto-generated method stub
		
		//.setEnabled(isChecked);
		
		//Seasons
		if(buttonView==sSeason){
			prefsHandler.setSeasonEnabled(isChecked);
			spSeason.setEnabled(isChecked);
			cbNavigateToBestSeason.setEnabled(isChecked);
		}
		if(buttonView==cbNavigateToBestSeason){
			prefsHandler.setNavigateToBestSeason(isChecked);
		}
		
		//Navigation
		if(buttonView==cbShowSplash){
			prefsHandler.setShowSplash(isChecked);
		}
		if(buttonView==cbKeepBackstack){
			prefsHandler.setKeepBackStack(isChecked);
		}
		if(buttonView==cbNavigateToHome){
			prefsHandler.setHomeEnabled(isChecked);
		}
		if(buttonView==cbNotificationBarShortcuts){
			prefsHandler.setNotificationBarShortcutsEnabled(isChecked);
			if(isChecked){
				utils.showNotificationBarShortcuts(PrefsOptions.EXTRA_NOTIFICATION_BAR_SHORTCUTS_ID);
			}else{utils.cancelNotificationBarShortcuts(PrefsOptions.EXTRA_NOTIFICATION_BAR_SHORTCUTS_ID);
			}
		}
		
		
		// Sync
		if(buttonView==sSync){
			prefsHandler.setSyncEnabled(isChecked);
			spSyncInterval.setEnabled(isChecked);
			cbAlertOnNewData.setEnabled(isChecked);
			btSync.setEnabled(isChecked);
		}
		if(buttonView==cbAlertOnNewData){
			prefsHandler.setAlertOnSync(isChecked);
		}
	
		// Reminder
		if(buttonView==sReminder){
			prefsHandler.setEnableReminders(isChecked);
			cbClasses.setEnabled(isChecked);
			cbAssignments.setEnabled(isChecked);
			cbCats.setEnabled(isChecked);
			cbEOS.setEnabled(isChecked);
			spClasses.setEnabled(isChecked);
			spAssignments.setEnabled(isChecked);
			spCats.setEnabled(isChecked);
			spEOS.setEnabled(isChecked);
			
			
		}
		if(buttonView==cbClasses){
			prefsHandler.setEnableClassesReminder(isChecked);
			spClasses.setEnabled(isChecked);
		}
		if(buttonView==cbAssignments){
			prefsHandler.setEnableAssignmentsReminder(isChecked);
			spAssignments.setEnabled(isChecked);
		}
		if(buttonView==cbCats){
			prefsHandler.setEnableCatsReminder(isChecked);
			spCats.setEnabled(isChecked);
		}
		if(buttonView==cbEOS){
			prefsHandler.setEnableEOSReminder(isChecked);
			spEOS.setEnabled(isChecked);
		}

		// Notifications
		if(buttonView==sNotifications){
			prefsHandler.setAppNotificationsEnabled(isChecked);
			cbNewDownloadItems.setEnabled(isChecked);
			cbSentNotifications.setEnabled(isChecked);
		}
		
		if(buttonView==cbNewDownloadItems){
			prefsHandler.setDownloadsEnabled(isChecked);
		}
		if(buttonView==cbSentNotifications){
			prefsHandler.setShowSentNotificationsEnabled(isChecked);
		}
		
		
		//DeveloperMode
		if(buttonView==sDeveloperMode){
			prefsHandler.setDeveloperMode(isChecked);
			etHostAddress.setEnabled(isChecked);
			etWifiPassword.setEnabled(isChecked);
			etWifiSSID.setEnabled(isChecked);
			btConnect.setEnabled(isChecked);
		}
		
	}
	private void onViewClicked(View view){
		if(view==btSync){
			if(utils.accountExists()){
				if (!prefsHandler.isSyncing()) {
					utils.forceSync();
					btSync.setText("Starting sync...");
				} else {
					btSync.setText("Click to Sync");
					utils.stopSync();
				}
			}else{ utils.startActivity(Authenticate.class);}
		}
		if(view==btConnect){
			if(!utils.getString(etHostAddress).equals("")&&(!utils.getString(etWifiSSID).equals("")&&!utils.getString(etWifiPassword).equals(""))){
				if(prefsHandler.isDeveloperMode()){
					if(utils.saveWifiNetwork(utils.getString(etWifiSSID), utils.getString(etWifiPassword))){
						utils.toastCustom("Connected to Developer Network(\""+utils.getString(etWifiSSID)+"\")",Toast.LENGTH_LONG,Color.green(200));
					}
				}
			}else{
				if(utils.getString(etHostAddress).equals("")){
					
					etHostAddress.setError("Host IP Address");
				}
				if(utils.getString(etWifiSSID).equals("")){
					
					etWifiSSID.setError("Wifi SSID");
				}
				if(utils.getString(etWifiPassword).equals("")){
					
					etWifiPassword.setError("Wifi password");
				}
				
			}
			
		}
	}
	private void itemSelected(AdapterView<?> parent, int position) {
		// TODO Auto-generated method stub
		
		//Seasons
		if(parent==spSeason){
			prefsHandler.setSeason(position);
		}
		
		//Sync
		if(parent==spSyncInterval){
			prefsHandler.setSyncInterval(position);
		}
		
		//Reminders
		if(parent==spClasses){
			prefsHandler.setClassesReminderInterval(position);
		}
		if(parent==spAssignments){
			prefsHandler.setAssignmnetsReminderInterval(position);
		}
		if(parent==spCats){
			prefsHandler.setCatsReminderInterval(position);
		}
		if(parent==spEOS){
			prefsHandler.setEOSReminderInterval(position);
		}
		
	}

	private void nothingSelected(AdapterView<?> parent) {
		// TODO Auto-generated method stub
		
	}

	void registerReceivers(){
		registerReceiver(receiver, new IntentFilter(StudentAuthenticator.ACTION_NO_STUDENT_ACCOUNT_FOUND));
		registerReceiver(receiver, new IntentFilter(StudentAuthenticator.ACTION_SYNC_CANCELLED));
		registerReceiver(receiver, new IntentFilter(StudentAuthenticator.ACTION_SYNC_ENDED));
		registerReceiver(receiver, new IntentFilter(StudentAuthenticator.ACTION_SYNC_ERROR));
		registerReceiver(receiver, new IntentFilter(StudentAuthenticator.ACTION_SYNC_RESULT_ALERT_TIMEOUT));
		registerReceiver(receiver, new IntentFilter(StudentAuthenticator.ACTION_SYNC_STARTED));
	}

	void unregisterReceivers(){
		unregisterReceiver(receiver);
	}

	class PreferencesReceiver extends BroadcastReceiver{

		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
			String cancelled=StudentAuthenticator.ACTION_SYNC_CANCELLED;
			String ended = StudentAuthenticator.ACTION_SYNC_ENDED;
			String error = StudentAuthenticator.ACTION_SYNC_ERROR;
			String started=StudentAuthenticator.ACTION_SYNC_STARTED;
			String timeout = StudentAuthenticator.ACTION_SYNC_RESULT_ALERT_TIMEOUT;
			String noaccount = StudentAuthenticator.ACTION_NO_STUDENT_ACCOUNT_FOUND;
			
			if(intent.getAction().equals(started)){
				//prefsHandler.setSyncing(true);
				btSync.setTextColor(Color.BLUE);
				btSync.setText("Syncing..Click to stop");
				btSync.setCompoundDrawablesWithIntrinsicBounds(R.drawable.progress, 0, 0, 0);
			}
			if (intent.getAction().equals(ended)) {
				//prefsHandler.setSyncing(false);
				btSync.setText("Sync ended");
				btSync.setTextColor(Color.green(200));
				btSync.setCompoundDrawablesWithIntrinsicBounds(R.drawable.stat_notify_sync, 0, 0, 0);
			}
			if (intent.getAction().equals(error)) {
				//prefsHandler.setSyncing(false);
				btSync.setText("Syncing failed");
				btSync.setTextColor(Color.RED);
				btSync.setCompoundDrawablesWithIntrinsicBounds(R.drawable.stat_notify_sync_error, 0, 0, 0);

			}
			if (intent.getAction().equals(cancelled)) {
				//prefsHandler.setSyncing(false);
				btSync.setText("Sync cancelled");
				btSync.setTextColor(Color.CYAN);
				btSync.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_warning, 0, 0, 0);
	
			}
			if (intent.getAction().equals(timeout)) {
				
				
			}
			if (intent.getAction().equals(noaccount)) {
				btSync.setText("You cannot sync");
				
			}
			
		}
		
	}
}
