package com.example.groups;

import java.util.ArrayList;

public class Class_locations_provider {
	public static ArrayList<Class_locations> provide_locations(ArrayList<Class_locations> locations)
	{
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			
		}
		return locations;
	}
}
