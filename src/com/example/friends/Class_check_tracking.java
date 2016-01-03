package com.example.friends;

public class Class_check_tracking {

	public static boolean check(String phone)
	{
		boolean result=false;
		int rand=(int)(Math.random()*100);
		if(rand>80)
		{
			result=true;
		}
		try {
			Thread.sleep(2000);
			
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}
		return result;
	}
	
}
