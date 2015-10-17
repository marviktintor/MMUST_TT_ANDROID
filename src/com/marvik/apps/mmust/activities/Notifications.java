package com.marvik.apps.mmust.activities;

import java.util.ArrayList;
import java.util.List;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.marvik.apps.mmust.R;
import com.marvik.apps.mmust.database.Database;
import com.marvik.apps.mmust.database.DbConsts;
import com.marvik.apps.mmust.utils.MmustUtils;

public class Notifications  extends MasterActivity{

	ListView lvNotifications;
	List<NotificationList>lNotifications;
	MmustUtils mmustUtils;
	View activityContentView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		activityContentView = getLayoutInflater().inflate(R.layout.notifications,null, true);
		setContentView(getCustomContentView(activityContentView,"Notifications"));
		init();
	}
	
	private void init() {
		// TODO Auto-generated method stub
		mmustUtils = new MmustUtils(Notifications.this);
		lvNotifications = (ListView)activityContentView.findViewById(R.id.notifications_listView_notifications);
		populateNotifications();
	}

	private void populateNotifications() {
		// TODO Auto-generated method stub
		lNotifications = new ArrayList<Notifications.NotificationList>();
		
		Database db = new Database(Notifications.this);
		SQLiteDatabase sql = db.getReadableDatabase();
		
		Cursor cursor = sql.query(DbConsts.NOTIFICATION_TABLE, null, null, null, null,null,null);
		
		for(cursor.moveToFirst();!cursor.isAfterLast();cursor.moveToNext()){
			String sender = cursor.getString(cursor.getColumnIndex(DbConsts.NOTIFICATION_SENDER));
			long sendTime = cursor.getLong(cursor.getColumnIndex(DbConsts.NOTIFICATION_SEND_TIME));
			String title = cursor.getString(cursor.getColumnIndex(DbConsts.NOTIFICATION_TITLE));
			String message = cursor.getString(cursor.getColumnIndex(DbConsts.NOTIFICATION_MESSAGE));
			
			lNotifications.add(new NotificationList(sender, sendTime, title, message));
		}
		cursor.close();
		sql.close();
		db.close();
		
		lvNotifications.setAdapter(new NotificationsListAdapter());
	}

	private class NotificationsListAdapter extends ArrayAdapter<NotificationList>{

		public NotificationsListAdapter() {
			super(Notifications.this, R.layout.notifications_ui, lNotifications);
			// TODO Auto-generated constructor stub
		}
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			View notificationView = convertView;
			if(notificationView==null){
				notificationView = getLayoutInflater().inflate(R.layout.notifications_ui, parent, false);
			}
			
			NotificationList notificationList = lNotifications.get(position);
			
			TextView tvSender = (TextView)notificationView.findViewById(R.id.notification_ui_textView_sender);
			TextView tvSendTime = (TextView)notificationView.findViewById(R.id.notification_ui_textView_sent_time);
			TextView tvMessage = (TextView)notificationView.findViewById(R.id.notification_ui_textView_message);
			TextView tvTitle = (TextView)notificationView.findViewById(R.id.notification_ui_textView_title);
			
			tvSender.setText(notificationList.getSender());
			tvSendTime.setText(mmustUtils.getFormattedTime(notificationList.getSendTime()));
			tvMessage.setText(notificationList.getMessage());
			tvTitle.setText(notificationList.getTitle());
			return notificationView;
		}
	}
	private class NotificationList{
		String sender,title,message;
		long sendTime;

		public NotificationList(String sender, long sendTime, String title,
				String message) {
			super();
			this.sender = sender;
			this.sendTime = sendTime;
			this.title = title;
			this.message = message;
		}

		public String getSender() {
			return sender;
		}

		public long getSendTime() {
			return sendTime;
		}

		public String getTitle() {
			return title;
		}

		public String getMessage() {
			return message;
		}
		
	}
}
