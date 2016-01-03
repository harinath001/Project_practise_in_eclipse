package com.example.friends;

import com.example.project_practise.R;

import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.ContactsContract;
import android.provider.ContactsContract.PhoneLookup;
import android.app.Activity;
import android.content.ContentResolver;
import android.database.Cursor;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class Activity_showfriends extends Activity {

	Adapter_friends adapter;
	ProgressBar progressbar;
	Handler handler;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_showfriends);
		
		 
		adapter=new Adapter_friends();
		ListView listview=(ListView)findViewById(R.id.listview_showfriends);
		listview.setAdapter(adapter);
		
		progressbar=(ProgressBar)findViewById(R.id.progressbar_showfriends);
		progressbar.setVisibility(View.INVISIBLE);
		handler=new Handler();
		progressbar.setVisibility(View.VISIBLE);
		listview.setOnItemClickListener(new OnItemClickListener() {
			
			
			@Override
			public void onItemClick(AdapterView<?> arg0, View view, int arg2,
					long arg3) {
				
				progressbar.setVisibility(View.VISIBLE);
				TextView tv=(TextView)view.findViewById(R.id.textview_layout_friends_phone);
				final String phone=tv.getText().toString();
				Thread t=new Thread(new Runnable() {
					
					@Override
					public void run() {
						
						if(Class_check_tracking.check(phone))
						{
							handler.post(new  Runnable() {
								public void run() {
									Toast.makeText(getApplicationContext(),"trackable",Toast.LENGTH_SHORT).show();
									progressbar.setVisibility(View.INVISIBLE);
								}
							});
						}
						else
						{
							handler.post(new  Runnable() {
								public void run() {
									Toast.makeText(getApplicationContext(),"not trackable",Toast.LENGTH_SHORT).show();
									progressbar.setVisibility(View.INVISIBLE);
								}
							});
						}
					}
				});
				t.start();
			}
		});
		
		
		Thread t=new Thread(new Runnable() {
			
			@Override
			public void run() {
				
				retrieve_contacts();
			}
		});
		t.start();
		
	}
	public void retrieve_contacts()
	{
		
		ContentResolver resolver=getContentResolver();
		Cursor cursor_phones=resolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,null, null, null);
		if(cursor_phones.moveToFirst())
		{
			while(cursor_phones.moveToNext())
			{
				final String phone_num = cursor_phones.getString(cursor_phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
				//String id=cursor_phones.getString(cursor_phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.CONTACT_ID));
				if(Class_verify_phone_number.verification(phone_num))
				{
					//final String name="NAME";
					 String name="";
					Uri uri = Uri.withAppendedPath(PhoneLookup.CONTENT_FILTER_URI, Uri.encode(phone_num));
				    Cursor cursor_names = resolver.query(uri, new String[]{PhoneLookup.DISPLAY_NAME}, null, null, null);
				    if(cursor_names.moveToFirst())
					{
						while(cursor_names.moveToNext())
						{
							name=cursor_names.getString(cursor_names.getColumnIndex(PhoneLookup.DISPLAY_NAME));
							
						}
					}
					cursor_names.close();
					final Class_friend_object obj=new Class_friend_object(name,phone_num);
					handler.post(new Runnable() {
						
						@Override
						public void run() {
							
							adapter.list.add(obj);
							adapter.notifyDataSetChanged();
							
						}
					});
					
				}
					
			}
			cursor_phones.close();
			
		}
		handler.post(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				progressbar.setVisibility(View.INVISIBLE);
			}
		});
		
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_showfriends, menu);
		return false;
	}

}
