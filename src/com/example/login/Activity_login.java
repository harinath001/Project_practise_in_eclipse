
package com.example.login;


import com.example.after_login.Activity_welcome;
import com.example.project_practise.R;
import com.example.project_practise.Response;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
public class Activity_login extends Activity {
	
	
	Handler handler;
	@Override
	protected void onCreate(Bundle b)
	{
		super.onCreate(b);
		setContentView(R.layout.activity_login);
		
		handler=new Handler();
		
		TextView var_textview_username=(TextView)findViewById(R.id.edittext_username);
		TextView var_textview_password=(TextView)findViewById(R.id.textview_password);
		TextView var_textview_phone=(TextView)findViewById(R.id.textview_phone);
		
		var_textview_username.setText("harinath");
		var_textview_password.setText("harinath");
		var_textview_phone.setText("9999999999");
		
		
		
		
		
		ProgressBar var_progressbar=(ProgressBar)findViewById(R.id.progressbar);
		var_progressbar.setVisibility(View.INVISIBLE);
		Button var_button_register=(Button)findViewById(R.id.button_register);
		var_button_register.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Intent intent_register=new Intent(Activity_login.this.getApplicationContext(),Activity_register.class);
				startActivity(intent_register);
				finish();
			}
		});
		Button var_button_login=(Button)findViewById(R.id.button_login);
		var_button_login.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				
				TextView var_textview_username=(TextView)findViewById(R.id.edittext_username);
				TextView var_textview_password=(TextView)findViewById(R.id.textview_password);
				TextView var_textview_phone=(TextView)findViewById(R.id.textview_phone);
				final String string_username=var_textview_username.getText().toString().trim();
				final String string_password=var_textview_password.getText().toString().trim();
				final String string_phone=var_textview_phone.getText().toString().trim();
			
				if(string_username.length()==0)
				{
					Toast.makeText(Activity_login.this.getApplicationContext(),"Invalid username", Toast.LENGTH_LONG).show();
					
				}
				else if(string_phone.length()!=10)
				{
					Toast.makeText(Activity_login.this.getApplicationContext(),"Invalid phone number", Toast.LENGTH_LONG).show();
					
				}
				else if(string_password.length()<6)
				{
					Toast.makeText(Activity_login.this.getApplicationContext(),"Invalid password", Toast.LENGTH_LONG).show();
					
				}
				else
				{
					final ProgressBar var_progressbar=(ProgressBar)findViewById(R.id.progressbar);
					var_progressbar.setVisibility(View.VISIBLE);
					
					
					Thread t=new Thread(new Runnable() {
						
						@Override
						public void run() {
							
							final Response res;
							res=Class_login_verifier.verify(string_username,string_phone,string_password);
							handler.post(new Runnable() {
								
								@Override
								public void run() {
									if(res.bool==true)
									{
										
										Toast.makeText(getApplicationContext(),"Login Successfull", Toast.LENGTH_LONG).show();
										take_after_login(string_username,string_phone);
									}
									else
									{
										Toast.makeText(getApplicationContext(),"Wrong Credentials\n"+res.message, Toast.LENGTH_LONG).show();
										
										var_progressbar.setVisibility(View.INVISIBLE);
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

	private void take_after_login(String string_username,String string_phone)
	{
		Intent intent_after_login=new Intent(Activity_login.this.getApplicationContext(),Activity_welcome.class);
		intent_after_login.putExtra("userName",string_username);
		intent_after_login.putExtra("phone", string_phone);
		startActivity(intent_after_login);
		finish();
	}
}
