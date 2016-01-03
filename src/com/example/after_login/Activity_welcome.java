package com.example.after_login;

import com.example.databases.Db_functions;

import com.example.friends.Activity_showfriends;
import com.example.groups.Activity_show_groups;
import com.example.helpers.Activity_search_helper;
import com.example.login.Activity_login;
import com.example.project_practise.R;
import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.content.Intent;
import android.content.IntentFilter;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;
import android.location.*;

public class Activity_welcome extends Activity {

	
	String var_username;
	String var_phone;
	ToggleButton toggle;
	ProgressBar pb;
	Handler handler;
	Button var_button_showfriends;
	Button var_button_groups;
	Button var_button_search;
	
	@Override
	protected void onCreate(Bundle b) {
		super.onCreate(b);
		Intent var_intent_recieved=getIntent();
		
		if(var_intent_recieved==null)
		{
			var_username="";
			var_phone="";
			Toast.makeText(getApplicationContext(),"welcome page should not be loaded without login",Toast.LENGTH_SHORT).show();
			Intent i=new Intent(getApplicationContext(),Activity_login.class);
			startActivity(i);
			finish();
		}
		else 
		{
			
			setContentView(R.layout.activity_welcome);
			String userName =var_intent_recieved.getStringExtra("userName");
			String phone=var_intent_recieved.getStringExtra("phone");
			
			var_username=userName;
			var_phone=phone;
			
			
			TextView var_textView_welcomeNote=(TextView)findViewById(R.id.textView_welcomeNote);
			var_textView_welcomeNote.setText("WELCOME "+userName);
			
			pb=(ProgressBar)findViewById(R.id.progressbar_welcome);
			pb.setVisibility(View.INVISIBLE);
			
			handler=new Handler();
			
			databasework();
			
		}
		register_for_gps_updates();
		update_gps();
		toggle=(ToggleButton)findViewById(R.id.toggle_location);
		toggle.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				
				startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
				update_gps();
				
				
			}
		});
		
		button_clicks();	
	}
	private void button_clicks()
	{
		var_button_showfriends=(Button)findViewById(R.id.button_showfriends);
		var_button_showfriends.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Intent intent=new Intent(Activity_welcome.this.getApplicationContext(),Activity_showfriends.class);
				startActivity(intent);
				
			}
		});
		var_button_groups=(Button)findViewById(R.id.button_groups);
		var_button_groups.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Intent i=new Intent(Activity_welcome.this.getApplicationContext(),Activity_show_groups.class);
				Bundle bundle=new Bundle();
				bundle.putString("username",var_username);
				bundle.putString("phone", var_phone);
				i.putExtras(bundle);
				startActivity(i);
				
			}
		});
		var_button_search=(Button)findViewById(R.id.button_search_helper);
		var_button_search.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Intent i=new Intent(Activity_welcome.this.getApplicationContext(),Activity_search_helper.class);
				Bundle bundle=new Bundle();
				bundle.putString("username",var_username);
				bundle.putString("phone", var_phone);
				i.putExtras(bundle);
				startActivity(i);
				
			}
		});
		
	}
	private void register_for_gps_updates()
	{
		BroadcastReciever_gps_updates broadcast_reciever=new BroadcastReciever_gps_updates();
		this.registerReceiver(broadcast_reciever, new IntentFilter(LocationManager.PROVIDERS_CHANGED_ACTION));
		broadcast_reciever.addActivity(this);	
	}
	public void update_gps()
	{
		//Toast.makeText(getApplicationContext(),"GPS is changed",Toast.LENGTH_SHORT).show();
		toggle=(ToggleButton)findViewById(R.id.toggle_location);
		
		LocationManager manager=(LocationManager)getSystemService(LOCATION_SERVICE);
		if (manager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
            toggle.setChecked(true);
           
        }else{
           toggle.setChecked(false);
        }
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_welcome, menu);
		
		return true;
	}
	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		
		
		if(item.getItemId()==R.id.settings_edit_helper)
		{
			Intent i=new Intent(getApplicationContext(),Activity_edit_helper.class);
			Bundle bundle=new Bundle();
			bundle.putString("username", var_username);
			bundle.putString("phone", var_phone);
			startActivity(i);
		}
		if(item.getItemId()==R.id.settings_security)
		{
			Intent i=new Intent(getApplicationContext(),Activity_security.class);
			Bundle bundle=new Bundle();
			bundle.putString("username", var_username);
			bundle.putString("phone", var_phone);
			i.putExtras(bundle);
			startActivity(i);
		}
		if(item.getItemId()==R.id.settings_tracklist)
		{
			Intent i=new Intent(getApplicationContext(),Activity_tracklist.class);
			Bundle bundle=new Bundle();
			bundle.putString("username", var_username);
			bundle.putString("phone", var_phone);
			i.putExtras(bundle);
			startActivity(i);
		}
		if(item.getItemId()==R.id.settings_logout)
		{
			
			pb=(ProgressBar)findViewById(R.id.progressbar_welcome);
			pb.setVisibility(View.VISIBLE);
			
			Thread t=new Thread(new Runnable() {
				
				@Override
				public void run() {
					
					
					Class_beforelogout.beforelogout();
					handler.post(new Runnable() {
						
						@Override
						public void run() {
							Db_functions funcs=new Db_functions(Activity_welcome.this.getApplicationContext());
							Db_functions.delete_table_prev_login();
							funcs.close_all();
							pb.setVisibility(View.INVISIBLE);
							Intent i=new Intent(getApplicationContext(),Activity_login.class);
							startActivity(i);
							finish();
						}
					});
				}
			});
			t.start();
			
			}
	
		
		return super.onMenuItemSelected(featureId, item);
	}

	private void databasework()
	{
		Db_functions funcs=new Db_functions(this.getApplicationContext());
		funcs.write_table_prev_login(var_username, var_phone);
		funcs.close_all();
		
	}

}
