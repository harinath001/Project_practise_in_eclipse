package com.example.groups;


import java.util.ArrayList;

import com.example.project_practise.R;
import android.os.Bundle;
import android.os.Handler;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

@SuppressLint("NewApi")
public class Activity_group_people extends Activity {

	Handler handler;
	ListView listview;
	Adapter_groups adapter;
	ProgressBar progressbar;
	String var_username;
	String var_phone;
	String var_group_id;
	String var_group_name;
	ArrayList<Class_group_object> friends_objects;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		Intent intent_recieved= getIntent();
		Bundle bundle=intent_recieved.getExtras();
		if(bundle==null || intent_recieved==null)
		{
			Toast.makeText(getApplicationContext(), "Illeagal opening of activity",Toast.LENGTH_SHORT).show();
		}
		else
		{
			setContentView(R.layout.activity_group_people);
			
			var_username=bundle.getString("username");
			var_phone=bundle.getString("phone");
			var_group_id=bundle.getString("group_id");
			var_group_name=bundle.getString("group_name");
			
			setTitle(var_group_name+"'s people");
			progressbar=(ProgressBar)findViewById(R.id.progressbar_group_people);
			listview=(ListView)findViewById(R.id.listview_group_people);
			adapter=new Adapter_groups();
			
			
			friends_objects=new ArrayList<Class_group_object>();
			friends_objects.add(new Class_group_object("friend 1","description"));
			friends_objects.add(new Class_group_object("friend 2","description"));
			friends_objects.add(new Class_group_object("friend 3","description"));
			listview.setAdapter(adapter);
			
			listview.setOnItemLongClickListener(new OnItemLongClickListener() {

				@Override
				public boolean onItemLongClick(AdapterView<?> parent, View view,
						int index, long id) {
					Class_group_object object=adapter.arraylist.get(index);
					Toast.makeText(getApplicationContext(),object.group_name+" long pressed",Toast.LENGTH_SHORT).show();
					return false;
				}
			});
			handler=new Handler();
			Thread t=new Thread(new Runnable() {
				
				@Override
				public void run() {
					load_group_people();
				}
			});
			
			t.start();
		}
		
		
	}
	public void load_group_people()
	{
		handler.post(new Runnable() {
			
			@Override
			public void run() {
				adapter.arraylist.clear();
				adapter.notifyDataSetChanged();
				progressbar.setVisibility(View.VISIBLE);
			}
		});
		Class_group_friend_loader.load_friends(var_username,var_phone,var_group_id);
		handler.post(new Runnable() {
			
			@Override
			public void run() {
				
				int total_friends=friends_objects.size();
				for(int i = 0;i<total_friends;i++)
				{
					adapter.addItem(friends_objects.get(i));
				}
				
				adapter.notifyDataSetChanged();
				progressbar.setVisibility(View.INVISIBLE);
			}
		});
	}
	public void on_listitem_long_clicked()
	{
		
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_group_people, menu);
		
		return true;
	}
	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		
		if(item.getItemId()==R.id.settings_group_people_refresh)
		{
			Thread t=new Thread(new Runnable() {
				
				@Override
				public void run() {
					load_group_people();
				}
			});
			
			t.start();
		}
		 if(item.getItemId()==R.id.settings_group_people_add)
		{
			Toast.makeText(getApplicationContext(), "New person is going to be added",Toast.LENGTH_SHORT).show();
			get_new_person();
		}
		
		return super.onMenuItemSelected(featureId, item);
		
	}
	private void get_new_person()
	{
		Intent i=new Intent(this,Activity_pic_contact.class);
		Bundle bundle=new Bundle();
		bundle.putString("username", var_username);
		bundle.putString("phone",var_phone);
		i.putExtras(bundle);
		startActivityForResult(i,0,bundle);
	}
	private void add_new_person(final String phone_num,final String name)
	{
		Thread t=new Thread(new Runnable() {
			
			@Override
			public void run() {
				
				handler.post(new Runnable() {
					
					@Override
					public void run() {
						progressbar.setVisibility(View.VISIBLE);
						
					}
				});
				if(Class_add_new_persorn.add()==true)
				{
					handler.post(new Runnable() {
						
						@Override
						public void run() {
							friends_objects.add(new Class_group_object(name,phone_num));
							
							progressbar.setVisibility(View.INVISIBLE);
							load_group_people();
						}
					});
				}
				else
				{
					handler.post(new Runnable() {
						
						@Override
						public void run() {
							Toast.makeText(getApplicationContext(), "Cannot add the perosn now",Toast.LENGTH_LONG).show();
							
						}
					});
					
				}
				
			}
		});
		t.start();	
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		
		Bundle bundle;
		if(resultCode==1)
		{
			bundle=data.getExtras();
			final String name=bundle.getString("name");
			final String phone=bundle.getString("phone");
			
			add_new_person(phone,name);	
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

}
