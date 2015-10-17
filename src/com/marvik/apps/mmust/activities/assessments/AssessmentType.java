package com.marvik.apps.mmust.activities.assessments;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.ActionBar.TabListener;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.support.v4.widget.DrawerLayout.DrawerListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView.OnItemClickListener;

import com.marvik.apps.mmust.R;
import com.marvik.apps.mmust.activities.MasterActivity;
import com.marvik.apps.mmust.prefs.PrefsHandler;

public class AssessmentType extends MasterActivity implements OnPageChangeListener, TabListener{
	String [] activityTitle = new String []{"Assignments","C.A.T's","End of Semester"};
	ViewPager vpAssessments;
	View activityContentView;
	PrefsHandler prefsHandler;
	
	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		activityContentView = getLayoutInflater().inflate(R.layout.assessments_view, null, true);
		setContentView(getCustomContentView(activityContentView,"Assessments test"));
		initAssessments();
		
		
	}

	private void initAssessments() {
		// TODO Auto-generated method stub
		prefsHandler = new PrefsHandler(AssessmentType.this);
		
		vpAssessments = (ViewPager)activityContentView.findViewById(R.id.assessments_view_viewPager_assessment_types);
		
		getActionBar().setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		getActionBar().addTab(getActionBar().newTab().setText("Assignments").setTabListener(this));
		getActionBar().addTab(getActionBar().newTab().setText("C.A.T's").setTabListener(this));
		getActionBar().addTab(getActionBar().newTab().setText("End of Sem").setTabListener(this));
		
		vpAssessments.setAdapter(new AssessmentsPagerAdapter(getSupportFragmentManager()));
		vpAssessments.setOnPageChangeListener(this);
		
		if(prefsHandler.isSeasonEnabled()){
			switch(prefsHandler.getSeason()){
			case 4:vpAssessments.setCurrentItem(0);
				break;
			case 5:vpAssessments.setCurrentItem(1);
				break;
			case 6:vpAssessments.setCurrentItem(2);
				break;
				default:vpAssessments.setCurrentItem(0);
			}
		}
		
	}

	@Override
	public void onPageScrollStateChanged(int arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onPageSelected(int position) {
		// TODO Auto-generated method stub
		getActionBar().setSelectedNavigationItem(position);
		
		setActivityTitle(activityTitle[position]);
	}
	
	private class AssessmentsPagerAdapter extends FragmentPagerAdapter{

		public AssessmentsPagerAdapter(FragmentManager fm) {
			super(fm);
			// TODO Auto-generated constructor stub
		}

		@Override
		public Fragment getItem(int arg0) {
			// TODO Auto-generated method stub
			switch(arg0){
			case 0:return new Assignments();
			case 1:return new Cats();
			case 2: return new Exams();
			}
			return null;
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return 3;
		}

		
		
	}

	@Override
	public void onTabSelected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub
		vpAssessments.setCurrentItem(tab.getPosition());
		setActivityTitle(activityTitle[tab.getPosition()]);
	}

	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onTabReselected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub
		
	}
	
	
}
