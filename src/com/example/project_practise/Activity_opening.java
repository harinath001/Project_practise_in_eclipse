package com.example.project_practise;



import com.example.after_login.*;
import com.example.login.*;

import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;


public class Activity_opening extends Activity {

	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_activity_opening);
		/*new Handler().postDelayed(new Runnable() {
			
			@Override
			public void run() {
				next();
				
			}
		}, 2000);*/
		
		final TextView tv1=(TextView)findViewById(R.id.textview_activity_openig_first);
		final TextView tv2=(TextView)findViewById(R.id.textview_activity_opening_second);
		tv1.setVisibility(View.INVISIBLE);
		tv2.setVisibility(View.INVISIBLE);
		final Handler handler=new Handler();
		Thread t=new Thread(new Runnable() {
			
			@Override
			public void run() {
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					
				}
				handler.post(new Runnable() {
					
					@Override
					public void run() {
						tv1.setVisibility(View.VISIBLE);
					}
				});
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					
				}
				handler.post(new Runnable() {
					
					@Override
					public void run() {
						tv2.setVisibility(View.VISIBLE);
					}
				});
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					
				}
				handler.post(new Runnable() {
					
					@Override
					public void run() {
						next();	
					}
				});
			}
		});
		t.start();
		
				
	}
	public void next()
	{
		if(Class_alreadyLogin.login_or_not(getApplicationContext()))
		{
			Intent intent_welcome=new Intent(Activity_opening.this.getApplicationContext(),Activity_welcome.class);
			
			
			intent_welcome.putExtra("userName", Class_alreadyLogin.username);
			intent_welcome.putExtra("phone", Class_alreadyLogin.phone);
		     startActivity(intent_welcome); 
		     finish();
		}
		else
		{
			
			Intent intent_login=new Intent(Activity_opening.this.getApplicationContext(),Activity_login.class);
		     startActivity(intent_login); 
		     finish();
			
		}
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_opening, menu);
		return false;
	}
	@Override
	public void onConfigurationChanged(Configuration _newconfig)
	{
		super.onConfigurationChanged(_newconfig);
		
	}
	// Called after onCreate has finished, use to restore UI state
	  @Override
	  public void onRestoreInstanceState(Bundle savedInstanceState) {
	    super.onRestoreInstanceState(savedInstanceState);
	    // Restore UI state from the savedInstanceState.
	    // This bundle has also been passed to onCreate.
	   // Toast.makeText(getApplicationContext(), "onrestoreinstancestate()", Toast.LENGTH_SHORT).show();
	  }
	  // Called before subsequent visible lifetimes
	  // for an activity process.
	  @Override
	  public void onRestart(){
	    super.onRestart();
	    // Load changes knowing that the activity has already
	    // been visible within this process.
	    //Toast.makeText(getApplicationContext(), "onrestart()", Toast.LENGTH_SHORT).show();
	  }
	  // Called at the start of the visible lifetime.
	  @Override
	  public void onStart(){
	    super.onStart();
	   // Toast.makeText(getApplicationContext(), "onstart()", Toast.LENGTH_SHORT).show();
	    // Apply any required UI change now that the Activity is visible.
	  }
	  // Called at the start of the active lifetime.
	  @Override
	  public void onResume(){
	    super.onResume();
	   // Toast.makeText(getApplicationContext(), "onresume()", Toast.LENGTH_SHORT).show();
	    // Resume any paused UI updates, threads, or processes required
	    // by the activity but suspended when it was inactive.
	  }
	  // Called to save UI state changes at the
	  // end of the active lifecycle.
	  @Override
	  public void onSaveInstanceState(Bundle savedInstanceState) {   
	    // Save UI state changes to the savedInstanceState. 
	    // This bundle will be passed to onCreate if the process is
	    // killed and restarted.
	    super.onSaveInstanceState(savedInstanceState);
	   // Toast.makeText(getApplicationContext(), "onsaveinstancestate()", Toast.LENGTH_SHORT).show();
	  }
	@Override
	  public void onPause(){
	    // Suspend UI updates, threads, or CPU intensive processes
	    // that don’t need to be updated when the Activity isn’t
	    // the active foreground activity.
	    super.onPause();
	    //Toast.makeText(getApplicationContext(), "onpause()", Toast.LENGTH_SHORT).show();
	  }
	  // Called at the end of the visible lifetime.
	  @Override
	  public void onStop(){   
	    // Suspend remaining UI updates, threads, or processing
	    // that aren’t required when the Activity isn’t visible.
	    // Persist all edits or state changes
	    // as after this call the process is likely to be killed.
	    super.onStop();
	   // Toast.makeText(getApplicationContext(), "onstop()", Toast.LENGTH_SHORT).show();
	  }
	  // Called at the end of the full lifetime.
	  @Override
	  public void onDestroy(){
	    // Clean up any resources including ending threads,
	    // closing database connections etc.
	    super.onDestroy();
	   // Toast.makeText(getApplicationContext(), "ondestroy()", Toast.LENGTH_SHORT).show();
	  }

}
