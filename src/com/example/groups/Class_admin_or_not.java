package com.example.groups;

public class Class_admin_or_not {

	public static boolean check()
	{
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}
		boolean result=false;
		int random=(int)(Math.random()*100);
		if(random>80)
		{
			result=true;
		}
		return result;
		
	}
}
