package com.example.contacts;

import java.util.ArrayList;

	public class Class_give_contacts {
	private static ArrayList<Class_contacts_object> arraylist;
	
	public static ArrayList<Class_contacts_object> giveList()
	{
		return arraylist;
	}
	public static void initialise()
	{
		if(arraylist==null)
		{
			arraylist=new  ArrayList<Class_contacts_object>();
			refresh();
		}
	}
	public static void refresh()
	{
		
	}
}
