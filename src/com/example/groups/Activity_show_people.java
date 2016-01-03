package com.example.groups;

import java.util.ArrayList;

import com.example.project_practise.R;

import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

public class Activity_show_people extends Activity {

	Handler handler;
	ListView listview;
	Adapter_groups adapter;
	ProgressBar progressbar;
	String var_username;
	String var_phone;
	String var_group_id;
	String var_group_name;
	Boolean admin_or_not;
	Thread updating_thread;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		Intent intent_recieved= getIntent();
		Bundle bundle=null ;
		if(intent_recieved!=null)
		{
			bundle=intent_recieved.getExtras();
		}
		
		if(bundle==null || intent_recieved==null)
		{
			Toast.makeText(getApplicationContext(), "Illeagal opening of activity",Toast.LENGTH_SHORT).show();
			finish();
		}
		else
			
		{
			setContentView(R.layout.activity_show_people);
			
			var_username=bundle.getString("username");
			var_phone=bundle.getString("phone");
			var_group_id=bundle.getString("group_id");
			var_group_name=bundle.getString("group_name");
			
			setTitle(var_group_name+" on Map");
			progressbar=(ProgressBar)findViewById(R.id.progressbar_show_group_people);
			progressbar.setVisibility(View.INVISIBLE);
			
			handler=new Handler();
			
			start_refresh_thread(7000);
			
		}
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_show_people, menu);
		
		return true;
	}
	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		if(item.getItemId()==R.id.settings_show_people)
		{
			Intent intent=new Intent(getApplicationContext(),Activity_group_people.class);
			Bundle bundle=new Bundle();
			bundle.putString("username", var_username);
			bundle.putString("phone", var_phone);
			bundle.putString("group_name",var_group_name);
			bundle.putString("group_id",var_group_id);
			intent.putExtras(bundle);
			startActivity(intent);
		}
		else if(item.getItemId()==R.id.settings_group_exit)
		{
			Thread t=new Thread(new Runnable() {
				
				@Override
				public void run() {
					exit_group();
				}
			});
			t.start();
		}
		return super.onMenuItemSelected(featureId, item);
	}
	private void exit_group()
	{
		handler.post(new Runnable() {
			
			@Override
			public void run() {
				progressbar.setVisibility(View.VISIBLE);
			}
		});
		Class_exit_group.exit(var_username, var_phone, var_group_id);
		handler.post(new Runnable() {
			
			@Override
			public void run() {
				progressbar.setVisibility(View.INVISIBLE);
				finish();
			}
		});
	}
	private void start_refresh_thread(final int time)
	{
		
		 updating_thread=new Thread(new Runnable() {
			
			@Override
			public void run() {
				
				
				
				while(is_removed()==false)
				{
					ArrayList<Class_locations> locations=new ArrayList<Class_locations>();
					if(!Activity_show_people.this.isFinishing())
					{
						locations=Class_locations_provider.provide_locations(locations);
						handler.post(new Runnable() {
							
							@Override
							public void run() {
								Toast.makeText(getApplicationContext(), "people's locations are updated now",Toast.LENGTH_SHORT).show();	
							}
						});
						
						try {
							Thread.sleep(time);
						} catch (InterruptedException e) {
							
						}
					}
					else
					{
						finish();
					}
					
					
				}
				// make the user to exit the group
				if(is_removed()==true)
				{
					handler.post(new Runnable() {
						
						@Override
						public void run() {
							Toast.makeText(getApplicationContext(), "You are removed from group",Toast.LENGTH_SHORT).show();
							finish();
						}
					});
				}
				
				
			}
		});
		updating_thread.start();
	}
	
	
	
	private boolean is_removed()
	{
		boolean result=false;
		return result;
	}

}
