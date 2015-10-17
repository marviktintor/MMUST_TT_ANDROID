package com.marvik.apps.mmust.activities;

import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.marvik.apps.mmust.R;
import com.marvik.apps.mmust.activities.assessments.AssessmentType;
import com.marvik.apps.mmust.utils.MmustUtils;

public class Dashboard extends MasterActivity {
	View activityContentView;
	ListView lvActivities;
	List<DashboardItems>lDashboardItems;
	MmustUtils mmustUtils;
	
	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		activityContentView = getLayoutInflater().inflate(R.layout.dashboard,null, false);
		setContentView(getCustomContentView(activityContentView,"Home"));
		
		init();
	}
	
	
	private void init() {
		// TODO Auto-generated method stub
		mmustUtils = new MmustUtils(Dashboard.this);
		
		lvActivities = (ListView)activityContentView.findViewById(R.id.dashboard_listView_activities);
		populateActivities();
		
	}
	
	private void populateActivities() {
		// TODO Auto-generated method stub
		lDashboardItems = new ArrayList<Dashboard.DashboardItems>();
		
		
		lDashboardItems.add(new DashboardItems("", "", "Academics", 0, R.drawable.ic_launcher));
		lDashboardItems.add(new DashboardItems("Assessments", "View your assignmnets,C.A.T's and end of semester exams dates.", "", 1, R.drawable.assessments));
		lDashboardItems.add(new DashboardItems("Classes", "View your daily classes based on the current day", "", 1, R.drawable.student_classes));
		lDashboardItems.add(new DashboardItems("Courses", "View your semester courses info and the lecturers", "", 1, R.drawable.student_courses));
		
		lDashboardItems.add(new DashboardItems("", "", "Files", 0, R.drawable.ic_launcher));
		lDashboardItems.add(new DashboardItems("Downloads", "View available files for download", "", 1, R.drawable.folder_documents));
		
		lDashboardItems.add(new DashboardItems("", "", "Persons", 0, R.drawable.ic_launcher));
		lDashboardItems.add(new DashboardItems("Lecturers", "View addittional lecturer information", "", 1, R.drawable.lec));
		
		lDashboardItems.add(new DashboardItems("", "", "Profiles", 0, R.drawable.ic_launcher));
		lDashboardItems.add(new DashboardItems("My information", "View all your school information", "", 1, R.drawable.person));
		
		lDashboardItems.add(new DashboardItems("", "", "Extras", 0, R.drawable.ic_launcher));
		lDashboardItems.add(new DashboardItems("Bugreport", "Submit a bug report to the app developer", "", 1, R.drawable.bug));
		lDashboardItems.add(new DashboardItems("Contact us", "Contact developer", "", 1, R.drawable.contact_us_icon));
		lDashboardItems.add(new DashboardItems("Help", "Get an answer on the F.A.Q's", "", 1, R.drawable.help_icon));
		lDashboardItems.add(new DashboardItems("More online", "Go online to view other information that is not here.", "", 1, R.drawable.ic_launcher_browser));

		lvActivities.setAdapter(new DashboardAdapter(Dashboard.this, R.layout.dashboard_ui, lDashboardItems));
		lvActivities.setOnItemClickListener(this);
	}
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
		super.onItemClick(parent, view, position, id);
		
		if(parent.getId()==R.id.dashboard_listView_activities){
			switch(position){
			case 0: //Academics Title
				break;
			case 1: mmustUtils.startActivity(AssessmentType.class);//Assessments
				break;
			case 2:mmustUtils.startActivity(Classes.class);//Classes
				break;
			case 3:mmustUtils.startActivity(Courses.class);//Courses
				break;
			case 4: //Files Title
				break;
			case 5:mmustUtils.startActivity(Downloads.class);//Downloads
				break;
			case 6://Persons Title
				break;
			case 7:mmustUtils.startActivity(Lecturers.class);//Lecturers
				break;
			case 8://Profiles
				break;
			case 9:mmustUtils.startActivity(StudentInfo.class);//Student info
				break;
			case 10://Extras
				break;
			case 11:mmustUtils.startActivity(BugReport.class);//Bugreport
				break;
			case 12:mmustUtils.startActivity(ContactUs.class);//ContactUS
				break;
			case 13:mmustUtils.startActivity(Help.class);//Help
				break;
			case 14:mmustUtils.startActivity(More.class);//More
				break;
				
			}
		}
	}
	class DashboardAdapter extends ArrayAdapter<DashboardItems>{
			int resource;
			List<DashboardItems> dashboardItems;
			Context context;
		public DashboardAdapter(Context context, int resource,
				List<DashboardItems> dashboardItems) {
			super(context, resource, dashboardItems);
			// TODO Auto-generated constructor stub
			this.resource = resource;
			this.dashboardItems = dashboardItems;
			this.context=context;
		}
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
			View dashBoardView = convertView;
			if(dashBoardView==null){
				dashBoardView = getLayoutInflater().inflate(resource, parent, false);
			}
			
			DashboardItems dItems =  dashboardItems.get(position);
			
			RelativeLayout rlActivityInfo = (RelativeLayout)dashBoardView.findViewById(R.id.dashboard_ui_relativeLayout_activity_parent);
			TextView tvItemsTitle = (TextView)dashBoardView.findViewById(R.id.dashboard_ui_textView_items_title);
			TextView tvActivityTitle = (TextView)dashBoardView.findViewById(R.id.dashboard_ui_textView_activity_title);
			TextView tvActivityExplanation = (TextView)dashBoardView.findViewById(R.id.dashboard_ui_textView_activity_explanation);
			ImageView ivActivityIcon = (ImageView)dashBoardView.findViewById(R.id.dashboard_ui_imageView_activity_icon);
			
			tvItemsTitle.setVisibility(TextView.GONE);
			if(dashboardItems.get(position).getItemType()==0){
				tvItemsTitle.setVisibility(TextView.VISIBLE);
				rlActivityInfo.setVisibility(RelativeLayout.GONE);
			}else{
				tvItemsTitle.setVisibility(TextView.GONE);
				rlActivityInfo.setVisibility(RelativeLayout.VISIBLE);
			}
			tvItemsTitle.setText(dItems.getItemsTitle());
			tvActivityTitle.setText(dItems.getActivityName());
			tvActivityExplanation.setText(dItems.getActivityExplanation());
			ivActivityIcon.setImageResource(dItems.getDrawableId());
			
			return dashBoardView;
		}
	}
	class DashboardItems {
		String activityName,activityExplanation,itemsTitle;
		int itemType, drawableId;

		public DashboardItems(String activityName, String activityExplanation,
				String itemsTitle, int itemType, int drawableId) {
			super();
			this.activityName = activityName;
			this.activityExplanation = activityExplanation;
			this.itemsTitle = itemsTitle;
			this.itemType = itemType;
			this.drawableId = drawableId;
		}

		public String getActivityName() {
			return activityName;
		}

		public String getActivityExplanation() {
			return activityExplanation;
		}

		public String getItemsTitle() {
			return itemsTitle;
		}

		public int getItemType() {
			return itemType;
		}

		public int getDrawableId() {
			return drawableId;
		}
		
	}
}
