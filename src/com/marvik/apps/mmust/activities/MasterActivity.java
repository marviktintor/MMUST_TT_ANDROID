package com.marvik.apps.mmust.activities;

import java.util.ArrayList;
import java.util.List;

import android.annotation.TargetApi;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.DrawerLayout.DrawerListener;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewTreeObserver.OnWindowAttachListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.marvik.apps.mmust.R;
import com.marvik.apps.mmust.prefs.PrefsHandler;
import com.marvik.apps.mmust.utils.MmustUtils;
import com.marvik.apps.mmust.utils.StudentAuthenticator;

public class MasterActivity extends FragmentActivity implements OnClickListener, DrawerListener,OnItemClickListener{
	MasterActivityReceiver receiver;
	PrefsHandler prefsHandler;
	MmustUtils utils;
	
	//ActionBar elements
	View actionBar,parentView;
	ImageView ivOpenSlider,ivSettings,ivNotifications,ivSyncResult;
	ProgressBar pbSyncing;
	
	TextView tvActivityTitle;
	
	//MainParent
	DrawerLayout drawerMenu;
	
	ListView lvQuickPlaces;
	
	//Parent
	FrameLayout frameParent;
	
	LinearLayout linearLayout;
	
	List<QuickPlaces>lQuickPlaces;

	private String activityTitle;
	
	public void initMasterActivityUI() {
		// TODO Auto-generated method stub
		parentView = getLayoutInflater().inflate(R.layout.activity_layout, null, true);
		
		drawerMenu = (DrawerLayout)parentView.findViewById(R.id.activity_layout_drawerLayout_quick_menu);
		drawerMenu.setClickable(false);
		frameParent = (FrameLayout)parentView.findViewById(R.id.activity_layout_frameLayout_parent);
		
		lvQuickPlaces = (ListView)parentView.findViewById(R.id.activity_layout_listView_quick_places);
		setUpQuickPlaces();
		lvQuickPlaces.setAdapter(new QuickPlacesAdapter(this, R.layout.quick_places, lQuickPlaces));
		lvQuickPlaces.setItemsCanFocus(true);
		lvQuickPlaces.setOnItemClickListener(this);
		
		drawerMenu.setDrawerListener(this);
		
		ivOpenSlider = (ImageView) actionBar.findViewById(R.id.action_bar_imageView_drawer_handle);
		ivSettings = (ImageView) actionBar.findViewById(R.id.action_bar_imageView_preferences);
		ivNotifications = (ImageView) actionBar.findViewById(R.id.action_bar_imageView_notifications);
		ivSyncResult = (ImageView)actionBar.findViewById(R.id.action_bar_imageView_sync_result);
		ivSyncResult.setVisibility(ImageView.GONE);
		ivSyncResult.setOnClickListener(this);
		
		pbSyncing = (ProgressBar)actionBar.findViewById(R.id.action_bar_progressBar_syncing);
		pbSyncing.setOnClickListener(this);
		if (prefsHandler.isSyncing()) {
			pbSyncing.setVisibility(ProgressBar.VISIBLE);
		} else {
			pbSyncing.setVisibility(ProgressBar.GONE);
		}
		
		tvActivityTitle = (TextView) actionBar.findViewById(R.id.action_bar_textView_activity_name);
		
		
		//Add click listeners
		ivOpenSlider.setOnClickListener(this);
		ivSettings.setOnClickListener(this);
		ivNotifications.setOnClickListener(this);

		tvActivityTitle.setOnClickListener(this);
	}
	@TargetApi(Build.VERSION_CODES.LOLLIPOP) public void setCustomActionBar(){
		actionBar = getLayoutInflater().inflate(R.layout.action_bar, null, false);
		getActionBar().setCustomView(actionBar);
		getActionBar().setDisplayShowCustomEnabled(true);
		getActionBar().setDisplayShowHomeEnabled(false);
		getActionBar().setHomeButtonEnabled(false);
		//getActionBar().setDisplayOptions(Window.FEATURE_ACTION_MODE_OVERLAY,Window.FEATURE_ACTION_BAR_OVERLAY);
		
		if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP){
			//getActionBar().setDisplayOptions(Window.FEATURE_ACTION_BAR_OVERLAY);
			//getActionBar().setHideOnContentScrollEnabled(true);
		}
		
	}
	
	@Override
	public void onClick(View view) {
		// TODO Auto-generated method stub
		switch(view.getId()){
		case R.id.action_bar_progressBar_syncing:
		case R.id.action_bar_imageView_sync_result:
			if (prefsHandler.isSyncing()) {
				utils.stopSync();
				
			} else {
				utils.forceSync();
			}
			break;
		case R.id.action_bar_imageView_drawer_handle:
			int drawerGravity = Gravity.LEFT;
			if(drawerMenu.isDrawerOpen(drawerGravity)){
				drawerMenu.closeDrawer(drawerGravity);
				return;
			}
			drawerMenu.openDrawer(drawerGravity);
			
			break;
		case R.id.action_bar_imageView_preferences:
			utils.startActivity(Preferences.class);  
			break;
		case R.id.action_bar_imageView_notifications: 
			utils.startActivity(Notifications.class );
			break;
		case R.id.action_bar_textView_activity_name:
			if(prefsHandler.isHomeEnabled()){
				utils.startActivity(Dashboard.class );
			}
			break;
		}
	}
	
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
		if(parent.getId()==R.id.activity_layout_listView_quick_places){
		
			try {
				Class <?> mClass= null;
				if(position==0){
					mClass = Class.forName("com.marvik.apps.mmust.activities.assessments.AssessmentType");
				}else{
				mClass = Class.forName("com.marvik.apps.mmust.activities." +lQuickPlaces.get(position).getQuickPlace());
				}
				utils.startActivity(mClass);
				//startActivity(new Intent(getApplicationContext(), mClass));
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				Toast.makeText(getApplicationContext(), "Could not find Activity "+lQuickPlaces.get(position).getQuickPlace(), Toast.LENGTH_SHORT).show();
			}
		}
			
		
	}
	
	@Override
	public void onDrawerClosed(View arg0) {
		// TODO Auto-generated method stub
		//frameParent.setVisibility(FrameLayout.VISIBLE);
	}

	@Override
	public void onDrawerOpened(View arg0) {
		// TODO Auto-generated method stub
		//frameParent.setVisibility(FrameLayout.GONE);
	}

	@Override
	public void onDrawerSlide(View arg0, float arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onDrawerStateChanged(int arg0) {
		// TODO Auto-generated method stub
		
	}

	public View getCustomContentView(View activityContentView) {
		setCustomActionBar();
		initMasterActivityUI();
		frameParent.addView(activityContentView);
		return parentView;

	}
	public View getCustomContentView(View activityContentView,String title) {
		setCustomActionBar();
		initMasterActivityUI();
		frameParent.addView(activityContentView);
		tvActivityTitle.setText(title.equalsIgnoreCase("")?getActivityTitle():title);
		return parentView;

	}
	@Override
	public void onBackPressed() {
		if(drawerMenu.isDrawerOpen(Gravity.LEFT)){
			frameParent.setVisibility(FrameLayout.VISIBLE);
			drawerMenu.closeDrawer(Gravity.LEFT);
		}else{ finish(); }
	}
	public void setUpQuickPlaces(){
		lQuickPlaces = new ArrayList<MasterActivity.QuickPlaces>();
		String [] quickPlace ={"Assessments","Classes","Courses","Downloads","Lecturers"};
		String [] quickPlaceExplanation ={"Assignments,Cats,Main exams","Your daily classes","Your semester courses"
				,"Available downloads","Your lecturers"};
		for (int x = 0; x < quickPlace.length; x++) {
			lQuickPlaces.add(new QuickPlaces(quickPlace[x],quickPlaceExplanation[x]));
		}
	}
	private String getActivityTitle() {
		return activityTitle;
	}
	protected void setActivityTitle(String activityTitle) {
		this.activityTitle = activityTitle;
		if(findViewById(R.id.action_bar_textView_activity_name)!=null){//View is initialised
			tvActivityTitle.setText(getActivityTitle());
		}
	}
	private class QuickPlacesAdapter extends ArrayAdapter<QuickPlaces>{
		int resource;
		public QuickPlacesAdapter(Context context, int resource,
				List<QuickPlaces> objects) {
			super(context, resource, objects);
			// TODO Auto-generated constructor stub
			this.resource = resource;
		}
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			View quickPlaceView = convertView;
			if(quickPlaceView==null){
				quickPlaceView = getLayoutInflater().inflate(resource, parent, false);
			}
			TextView tvQuickPlace = (TextView)quickPlaceView.findViewById(R.id.quic_places_textView_quick_place);
			TextView tvQuickPlaceExplanation = (TextView)quickPlaceView.findViewById(R.id.quic_places_textView_quick_place_explanation);
			
			QuickPlaces quickPlaces = lQuickPlaces.get(position);
			
			tvQuickPlace.setText(quickPlaces.getQuickPlace());
			tvQuickPlaceExplanation.setText(quickPlaces.getQuickPlaceExplanation());
			
			return quickPlaceView;
		}
	}
	
	public class QuickPlaces{
		String quickPlace, quickPlaceExplanation;
		
		public QuickPlaces(String quickPlace, String quickPlaceExplanation) {
			super();
			this.quickPlace = quickPlace;
			this.quickPlaceExplanation = quickPlaceExplanation;
		}

		public String getQuickPlace() {
			return quickPlace;
		}

		public void setQuickPlace(String quickPlace) {
			this.quickPlace = quickPlace;
		}

		public String getQuickPlaceExplanation() {
			return quickPlaceExplanation;
		}

		public void setQuickPlaceExplanation(String quickPlaceExplanation) {
			this.quickPlaceExplanation = quickPlaceExplanation;
		}
		
	}

	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		receiver = new MasterActivityReceiver();
		prefsHandler = new PrefsHandler(this);
		utils = new MmustUtils(this);
		
		
	}
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		registerReceiver(receiver, new IntentFilter(StudentAuthenticator.ACTION_SYNC_CANCELLED));
		registerReceiver(receiver, new IntentFilter(StudentAuthenticator.ACTION_SYNC_ENDED));
		registerReceiver(receiver, new IntentFilter(StudentAuthenticator.ACTION_SYNC_ERROR));
		registerReceiver(receiver, new IntentFilter(StudentAuthenticator.ACTION_SYNC_RESULT_ALERT_TIMEOUT));
		registerReceiver(receiver, new IntentFilter(StudentAuthenticator.ACTION_SYNC_STARTED));
		
		if (prefsHandler.isSyncing()) {
			pbSyncing.setVisibility(ProgressBar.VISIBLE);
			ivSyncResult.setVisibility(ProgressBar.GONE);
		} else {
			pbSyncing.setVisibility(ProgressBar.GONE);
			ivSyncResult.setVisibility(ProgressBar.GONE);
		}
		
		
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		unregisterReceiver(receiver);
	}
	

	class MasterActivityReceiver extends BroadcastReceiver{

		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
			String cancelled=StudentAuthenticator.ACTION_SYNC_CANCELLED;
			String ended = StudentAuthenticator.ACTION_SYNC_ENDED;
			String error = StudentAuthenticator.ACTION_SYNC_ERROR;
			String started=StudentAuthenticator.ACTION_SYNC_STARTED;
			String timeout = StudentAuthenticator.ACTION_SYNC_RESULT_ALERT_TIMEOUT;
			
			AlarmManager alarmManager = (AlarmManager)context.getSystemService(ALARM_SERVICE);
			PendingIntent operation = PendingIntent.getBroadcast(context, 1, new Intent(timeout), PendingIntent.FLAG_UPDATE_CURRENT);
			
			
			if(intent.getAction().equals(started)){
				pbSyncing.setVisibility(ImageView.VISIBLE);
				ivSyncResult.setVisibility(ImageView.GONE);
				prefsHandler.setSyncing(true);
			}
			if (intent.getAction().equals(ended)) {
				pbSyncing.setVisibility(ImageView.GONE);
				ivSyncResult.setVisibility(ImageView.VISIBLE);
				ivSyncResult.setImageResource(R.drawable.ic_complete);
				prefsHandler.setSyncing(false);
				alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, SystemClock.elapsedRealtime()+20000, operation);
			}
			if (intent.getAction().equals(error)) {
				pbSyncing.setVisibility(ImageView.GONE);
				ivSyncResult.setVisibility(ImageView.VISIBLE);
				ivSyncResult.setImageResource(R.drawable.indicator_input_error);
				prefsHandler.setSyncing(false);
				alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, SystemClock.elapsedRealtime()+30000, operation);
			}
			if (intent.getAction().equals(cancelled)) {
				pbSyncing.setVisibility(ImageView.GONE);
				ivSyncResult.setVisibility(ImageView.VISIBLE);
				ivSyncResult.setImageResource(R.drawable.error_icon);
				prefsHandler.setSyncing(false);
				alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, SystemClock.elapsedRealtime()+30000, operation);
			}
			if (intent.getAction().equals(timeout)) {
				pbSyncing.setVisibility(ImageView.GONE);
				ivSyncResult.setVisibility(ImageView.GONE);
				
			}
			
		}
		
	}
	
}
