package com.example.broadcast_recievers;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.json.Class_server_details;
import com.example.json.JSONParser;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.os.Handler;

import android.widget.Toast;

public class Class_reciever_gps extends BroadcastReceiver {

	 public  static  int GPS;
	 private Handler handler=new Handler();
	 static JSONParser parser = new JSONParser();
	private Thread t=null;
	Context con;
	@Override
	public void onReceive(Context context, Intent intent) {
		
		con=context;
		if(intent.getAction().matches("android.location.PROVIDERS_CHANGED"))
		{
			
			LocationManager manager=(LocationManager)context.getSystemService(context.LOCATION_SERVICE);
			if (manager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
	            GPS=1;
				Toast.makeText(context, "GPS turned ON", Toast.LENGTH_SHORT).show();
				if(t==null)
				{
					t=new Thread(new Runnable() {
						
						@Override
						public void run() {
							
							while(GPS==1)
							{
								handler.post(new Runnable() {
									
									@Override
									public void run() {
										
										print();
									}
								});
								send();
								try {
									Thread.sleep(5000);
								} catch (InterruptedException e) {
									
								}
							}
						}
					});
					t.start();
				}
				
	        }else{
	        	GPS=0;
	        	t=null;
	        	Toast.makeText(context, "GPS turned OFF", Toast.LENGTH_SHORT).show();
	        }
			
		}
		
		
	}
	
	private void send()
	{
		
		if(Class_server_details.server_on==1)
		{
			String var_username="gangadhar";
			String var_phone="8888888888";
			String latitude=Math.random()*100+"";
			String longitude=Math.random()*100+"";
			String url=Class_server_details.server_ip+"/android/project/update_location.php";
			
			List<NameValuePair> params = new ArrayList<NameValuePair>();
	        params.add(new BasicNameValuePair("username", var_username));
	        params.add(new BasicNameValuePair("phone",var_phone));
	        params.add(new BasicNameValuePair("latitude",latitude));
	        params.add(new BasicNameValuePair("longitude",longitude));
			
	        JSONObject json = parser.makeHttpRequest(url, "POST", params);
	        try {
	        	final int success=json.getInt("success");
	        	handler.post(new Runnable() {
					
					@Override
					public void run() {
						
						if(success==1)
			        	{
			        		Toast.makeText(con, "success=1",Toast.LENGTH_SHORT).show();
			        	
			        	}
			        	else
			        	{
			        		Toast.makeText(con, "success=0",Toast.LENGTH_SHORT).show();
			        	}
					}
				});
	        	
	        		
	        	
			} catch (JSONException e1) {
				
			}
	        catch(NullPointerException e)
	        {
	        	
	        }
	        catch(Exception e)
	        {
	        	
	        }
		}
	}
	private void print()
	{
		Toast.makeText(con, "updating server....", Toast.LENGTH_SHORT).show();
	}

}
