package com.example.groups;

import java.util.ArrayList;

import com.example.project_practise.R;
import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class Activity_show_groups extends Activity {

	String var_username;
	String var_phone;
	ProgressBar var_progressbar;
	Handler handler;
	Adapter_groups adapter;
	ListView listview;
	ArrayList<Class_group_object> group_objects;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_show_groups);
		
		if(getIntent().getExtras()==null)
		{
			Toast.makeText(getApplicationContext(),"illeagal opening of activity",Toast.LENGTH_SHORT).show();
			finish();
		}
		else
		{
			
			Bundle bundle=getIntent().getExtras();
			var_username=bundle.getString("username");
			var_phone=bundle.getString("phone");
			
			var_progressbar=(ProgressBar)findViewById(R.id.progressbar_show_groups);
			
			adapter=new Adapter_groups();
			
			
			group_objects = new ArrayList<Class_group_object>();
			group_objects.add(new Class_group_object("Group 1", "you are in this group"));
			group_objects.add(new Class_group_object("Group 2", "you are in this group"));
			group_objects.add(new Class_group_object("Group 3", "you are in this group"));
			group_objects.add(new Class_group_object("Group 4", "you are in this group"));
			
			
			
			ListView listview=(ListView)findViewById(R.id.listview_groups);
			listview.setAdapter(adapter);
			
			listview.setOnItemClickListener(new OnItemClickListener() 
			  {
				@Override
				public void onItemClick(AdapterView<?> arg0, View view, int arg2,long arg3)
			 	{
					on_item_click(arg0,view,arg2,arg3); 
				}
				
			});
			
			handler=new Handler();
			
		}
	}
	
	private void load_group_names()
	{
		handler.post(new Runnable() {
			
			@Override
			public void run() {
				adapter.arraylist.clear();
				adapter.notifyDataSetChanged();
				var_progressbar.setVisibility(View.VISIBLE);
			}
		});
		
		Class_group_loader.get_group_names();
		handler.post(new Runnable() {
			
			@Override
			public void run() {
				int total_items=group_objects.size();
				for(int i=0;i<total_items;i++)
				{
					adapter.addItem(group_objects.get(i));
				}
				
				adapter.notifyDataSetChanged();
				var_progressbar.setVisibility(View.INVISIBLE);
			}
		});
		
	}
	private void on_item_click(AdapterView<?> parent, View view, int pos,long id)
	{
		Class_group_object object=adapter.arraylist.get(pos);
		Intent intent=new Intent(getApplicationContext(),Activity_show_people.class);
		Bundle bundle=new Bundle();
		bundle.putString("username", var_username);
		bundle.putString("phone", var_phone);
		bundle.putString("group_name",object.group_name);
		bundle.putString("group_id","group_id");
		
		
		intent.putExtras(bundle);
		
		startActivity(intent);
		Toast.makeText(getApplicationContext(), object.group_name, Toast.LENGTH_SHORT).show();
		
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_show_groups, menu);
		
		return true;
	}
	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		
		if(item.getItemId()==R.id.settings_new_group)
		{
			Toast.makeText(getApplicationContext(), "New group will be created",Toast.LENGTH_SHORT).show();
			create_new_group();
		}
		else if(item.getItemId()==R.id.settings_groups_refresh)
		{
			
			Thread t=new Thread(new Runnable() {
				
				@Override
				public void run() {
					load_group_names();
				}
			});
			
			t.start();
			
		}
		return super.onMenuItemSelected(featureId, item);
	}
	@Override
	protected void onStart() {
		
		super.onStart();
		Thread t=new Thread(new Runnable() {
			
			@Override
			public void run() {
				
				load_group_names();
				
			}
		});
		
		t.start();
	}
	private void create_new_group()
	{
		AlertDialog.Builder dialogbuilder = new AlertDialog.Builder(this);
		LayoutInflater inflater=LayoutInflater.from(getApplicationContext());
		final View dialog_view =inflater.inflate(R.layout.dialog_new_group,null);
		dialogbuilder.setView(dialog_view);
		dialogbuilder.setCancelable(false);
		dialogbuilder.setNegativeButton("Cancel",new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				arg0.cancel();
			}
		});
		dialogbuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				
				TextView textview =(TextView)(dialog_view.findViewById(R.id.edittext_dialog_new_group_input));
				String group_name=textview.getText().toString().trim();
				add_new_group(group_name);
				
			}
		});
		
		AlertDialog alert=dialogbuilder.create();
		alert.setTitle("Enter group name");
		alert.setIcon(R.drawable.ic_launcher);
		alert.show();
		
	}
	private void add_new_group(final String group_name)
	{
		
		Thread t=new Thread(new Runnable() {
			
			boolean result;
			@Override
			public void run() {
				
				handler.post(new Runnable() {
					
					@Override
					public void run() {
						var_progressbar.setVisibility(View.VISIBLE);
					}
				});
				result=Class_add_new_group.add_new_group(var_username, var_phone, group_name);
				handler.post(new Runnable() {
					
					@Override
					public void run() {
						
						if(result==true)
						{
							group_objects.add(new Class_group_object(group_name,"You created this group"));
							load_group_names();
						}
						else
						{
							Toast.makeText(Activity_show_groups.this.getApplicationContext(),"Creation of new group failed", Toast.LENGTH_SHORT).show();
						}
						var_progressbar.setVisibility(View.INVISIBLE);
					}
				});
				
			}
			
		});
		t.start();
	}
}
