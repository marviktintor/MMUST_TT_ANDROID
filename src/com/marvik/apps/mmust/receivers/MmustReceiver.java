package com.marvik.apps.mmust.receivers;

import com.marvik.apps.mmust.prefs.PrefsHandler;
import com.marvik.apps.mmust.prefs.PrefsOptions;
import com.marvik.apps.mmust.utils.MmustUtils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.wifi.WifiManager;
import android.widget.Toast;

public class MmustReceiver extends BroadcastReceiver {
	MmustUtils utils;
	PrefsHandler prefsHandler;
	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		utils = new MmustUtils(context);
		prefsHandler = new PrefsHandler(context);
		
		if(intent.getAction().equals(WifiManager.WIFI_STATE_CHANGED_ACTION)){
		
			WifiManager manager = (WifiManager)context.getSystemService(Context.WIFI_SERVICE);
			switch(manager.getWifiState()){
			case WifiManager.WIFI_STATE_ENABLED: 
				if(prefsHandler.isDeveloperMode()){
					if(utils.saveWifiNetwork(prefsHandler.getWifiSSID(), prefsHandler.getWifiPassword())){
						utils.toastCustom("Saved Developer Network(\""+prefsHandler.getWifiSSID()+"\")",Toast.LENGTH_LONG,Color.green(200));
					}
				}
				break;
			case WifiManager.WIFI_STATE_ENABLING:  
				break;
			case WifiManager.WIFI_STATE_DISABLED:  
				break;
			case WifiManager.WIFI_STATE_DISABLING:  
				break;
			}
			
		}
		if(intent.getAction().equals(Intent.ACTION_BOOT_COMPLETED)){
			//Set Reminders
			//Maybe do a sync
			
			//Check status bar notification status
			checkNotificationBarShortcutsSettings();
		}
	}
	private void checkNotificationBarShortcutsSettings() {
		// TODO Auto-generated method stub
		if(prefsHandler.isNotificationBarShortcutsEnabled()){
			utils.showNotificationBarShortcuts(PrefsOptions.EXTRA_NOTIFICATION_BAR_SHORTCUTS_ID);
		}
	}
	
}
