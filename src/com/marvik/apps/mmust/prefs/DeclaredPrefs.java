package com.marvik.apps.mmust.prefs;


public interface DeclaredPrefs {
	/**
	 * @return the checkChange on a compundButton
	 * This is the mthod that saves the prefs
	 *//*
	
	public boolean onCheckChange(CompoundButton buttonView, boolean isChecked);*/
	/**
	 * @return the seasonEnabled
	 */
	public boolean isSeasonEnabled();
	/**
	 * @param seasonEnabled the seasonEnabled to set
	 */
	public void setSeasonEnabled(boolean seasonEnabled);
	/**
	 * @return the season
	 */
	public int getSeason();
	/**
	 * @param season the season to set
	 */
	public void setSeason(int season);
	/**
	 * @return the navigateToBestSeason
	 */
	public boolean isNavigateToBestSeason();
	/**
	 * @param navigateToBestSeason the navigateToBestSeason to set
	 */
	public void setNavigateToBestSeason(boolean navigateToBestSeason);
	/**
	 * @return the showSplash
	 */
	public boolean isShowSplash() ;
	/**
	 * @param showSplash the showSplash to set
	 */
	public void setShowSplash(boolean showSplash);
	/**
	 * @param notificationBarShortcutsEnabled the notificationBarShortcutsEnabled to set
	 */
	
	public void setNotificationBarShortcutsEnabled(boolean enableNotificationBarShortcuts);
	/**
	 * @return the notificationBarShortcutsEnabled
	 */
	public boolean isNotificationBarShortcutsEnabled();
	/**
	 * @return the keepBackStack
	 */
	public boolean isKeepBackStack() ;
	/**
	 * @param keepBackStack the keepBackStack to set
	 */
	public void setKeepBackStack(boolean keepBackStack);
	/**
	 * @return the enableHome
	 */
	public boolean isHomeEnabled() ;
	/**
	 * @param enableHome the enableHome to set
	 */
	public void setHomeEnabled(boolean enableHome);
	/**
	 * @return the syncEnabled
	 */
	public boolean isSyncEnabled();
	/**
	 * @param syncEnabled the syncEnabled to set
	 */
	public void setSyncEnabled(boolean syncEnabled);
	/**
	 * @return the syncInterval
	 */
	public int getSyncInterval();
	/**
	 * @param syncInterval the syncInterval to set
	 */
	public void setSyncInterval(int syncInterval);
	/**
	 * @return the alertOnSync
	 */
	public boolean isAlertOnSync() ;
	/**
	 * @param alertOnSync the alertOnSync to set
	 */
	public void setAlertOnSync(boolean alertOnSync);
	/**
	 * @return the enableReminders
	 */
	public boolean isEnableReminders() ;
	/**
	 * @param enableReminders the enableReminders to set
	 */
	public void setEnableReminders(boolean enableReminders);
	/**
	 * @return the enableClassesReminder
	 */
	public boolean isEnableClassesReminder();
	/**
	 * @param enableClassesReminder the enableClassesReminder to set
	 */
	public void setEnableClassesReminder(boolean enableClassesReminder);
	/**
	 * @return the classesReminderInterval
	 */
	public int getClassesReminderInterval();
	/**
	 * @param classesReminderInterval the classesReminderInterval to set
	 */
	public void setClassesReminderInterval(int classesReminderInterval);
	/**
	 * @return the enableAssignmentsReminder
	 */
	public boolean isEnableAssignmentsReminder() ;
	/**
	 * @param enableAssignmentsReminder the enableAssignmentsReminder to set
	 */
	public void setEnableAssignmentsReminder(boolean enableAssignmentsReminder) ;
	/**
	 * @return the assignmnetsReminderInterval
	 */
	public int getAssignmnetsReminderInterval() ;
	/**
	 * @param assignmnetsReminderInterval the assignmnetsReminderInterval to set
	 */
	public void setAssignmnetsReminderInterval(int assignmnetsReminderInterval);
	/**
	 * @return the enableCatsReminder
	 */
	public boolean isEnableCatsReminder();
	/**
	 * @param enableCatsReminder the enableCatsReminder to set
	 */
	public void setEnableCatsReminder(boolean enableCatsReminder);
	/**
	 * @return the catsReminderInterval
	 */
	public int getCatsReminderInterval() ;
	/**
	 * @param catsReminderInterval the catsReminderInterval to set
	 */
	public void setCatsReminderInterval(int catsReminderInterval);
	/**
	 * @return the enableEOSReminder
	 */
	public boolean isEnableEOSReminder();
	/**
	 * @param enableEOSReminder the enableEOSReminder to set
	 */
	public void setEnableEOSReminder(boolean enableEOSReminder);
	/**
	 * @return the eOSReminderInterval
	 */
	public int getEOSReminderInterval();
	/**
	 * @param eOSReminderInterval the eOSReminderInterval to set
	 */
	public void setEOSReminderInterval(int eOSReminderInterval) ;
	/**
	 * @return the appNotificationsEnabled
	 */
	public boolean isAppNotificationsEnabled() ;
	/**
	 * @param appNotificationsEnabled the appNotificationsEnabled to set
	 */
	public void setAppNotificationsEnabled(boolean appNotificationsEnabled);
	/**
	 * @return the downloadsEnabled
	 */
	public boolean isDownloadsEnabled();
	/**
	 * @param downloadsEnabled the downloadsEnabled to set
	 */
	public void setDownloadsEnabled(boolean downloadsEnabled);
	/**
	 * @return the showSentNotificationsEnabled
	 */
	public boolean isShowSentNotificationsEnabled();
	/**
	 * @param showSentNotificationsEnabled the showSentNotificationsEnabled to set
	 */
	public void setShowSentNotificationsEnabled(boolean showSentNotificationsEnabled) ;
	/**
	 * @return the developerMode
	 */
	public boolean isDeveloperMode() ;
	/**
	 * @param developerMode the developerMode to set
	 */
	public void setDeveloperMode(boolean developerMode) ;
	/**
	 * @return the wifiPassword
	 */
	public String getWifiPassword() ;
	/**
	 * @param wifiPassword the wifiPassword to set
	 */
	public void setWifiPassword(String wifiPassword) ;
	/**
	 * @return the wifiSSID
	 */
	public String getWifiSSID() ;
	/**
	 * @param wifiSSID the wifiSSID to set
	 */
	public void setWifiSSID(String wifiSSID) ;
	/**
	 * @return the hostAddress
	 */
	public String getHostAddress();
	/**
	 * @param hostAddress the hostAddress to set
	 */
	public void setHostAddress(String hostAddress) ;
	
	
	
}
