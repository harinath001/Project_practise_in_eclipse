package com.example.login;

import com.example.project_practise.R;
import com.example.project_practise.Response;

import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

public class Activity_register extends Activity {

	ProgressBar progressbar;
	Handler handler;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);
		handler=new Handler();
		progressbar=(ProgressBar)findViewById(R.id.progressbar_register);
		progressbar.setVisibility(View.INVISIBLE);
		Button var_button_login=(Button)findViewById(R.id.button_login2);
		var_button_login.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Intent intent_login=new Intent(Activity_register.this.getApplicationContext(),Activity_login.class);
				startActivity(intent_login);
				finish();
			}
		});
		Button var_button_register=(Button)findViewById(R.id.button_register2);
		var_button_register.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				
				EditText var_edittext_username=(EditText)findViewById(R.id.edittext_username);
				EditText var_edittext_email=(EditText)findViewById(R.id.edittext_email);
				EditText var_edittext_phone=(EditText)findViewById(R.id.edittext_phone);
				EditText var_edittext_password=(EditText)findViewById(R.id.edittext_password);
				EditText var_edittext_confirm_password=(EditText)findViewById(R.id.edittext_confirm_password);
				final String string_username=var_edittext_username.getText().toString().trim();
				final String string_phone=var_edittext_phone.getText().toString().trim();
				final String string_password=var_edittext_password.getText().toString().trim();
				final String string_confirm_password=var_edittext_confirm_password.getText().toString().trim();
				final String string_email=var_edittext_email.getText().toString().trim();
				if(check(string_username,string_email,string_phone,string_password,string_confirm_password))
				{
					
					progressbar.setVisibility(View.VISIBLE);
					Thread t=new Thread(new Runnable() {
						
						@Override
						public void run() {
							
							final Response res=Class_save_data.save_data(string_username, string_email, string_phone, string_password, string_confirm_password);
							
							
							handler.post(new Runnable() {
								
								@Override
								public void run() {
									if(res.bool)
									{
										Toast.makeText(Activity_register.this.getApplicationContext(),"Registration successful now please login", Toast.LENGTH_LONG).show();
										Intent intent_login=new Intent(Activity_register.this.getApplicationContext(),Activity_login.class);
										startActivity(intent_login);
										finish();
									}
									else
									{
										Toast.makeText(Activity_register.this.getApplicationContext(),res.message, Toast.LENGTH_LONG).show();
										progressbar.setVisibility(View.INVISIBLE);
									}
									
								}
							});
									
							
						}
					});
					t.start();
						
				}
				
			}
		});
	}
	private boolean check(String username,String email,String phone,String password,String confirm_password)
	{
		boolean result=false;
		if(username.length()==0)
		{
			Toast.makeText(Activity_register.this.getApplicationContext(),"Invalid username", Toast.LENGTH_LONG).show();
		}
		else if(!email.contains("@")||!email.contains(".")||email.length()<5)
		{
			Toast.makeText(Activity_register.this.getApplicationContext(),"Invalid email", Toast.LENGTH_LONG).show();
		}
		else if(phone.length()!=10)
		{
			Toast.makeText(Activity_register.this.getApplicationContext(),"Invalid phone number", Toast.LENGTH_LONG).show();
		}
		else if(password.length()<6)
		{
			Toast.makeText(Activity_register.this.getApplicationContext(),"Short password", Toast.LENGTH_LONG).show();
		}
		else if(!confirm_password.equals(password))
		{
			Toast.makeText(Activity_register.this.getApplicationContext(),"Passwords does not match", Toast.LENGTH_LONG).show();
		}
		else
		{
			result=true;
		}
		return result;
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_register, menu);
		return true;
	}

}
