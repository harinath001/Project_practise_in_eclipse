package com.example.after_login;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;


public class BroadcastReciever_gps_updates  extends BroadcastReceiver{

	public Activity_welcome activity_reciever;
	@Override
	public void onReceive(Context arg0, Intent arg1) {
			if(activity_reciever!=null)
			activity_reciever.update_gps();
		
	}
	public void addActivity(Activity_welcome activity_)
	{
		this.activity_reciever=activity_;
	}

}
