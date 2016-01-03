package com.example.helpers;

import com.example.project_practise.R;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class Activity_search_helper extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search_helper);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_search_helper, menu);
		return false;
	}

}
