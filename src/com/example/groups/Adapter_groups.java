package com.example.groups;

import java.util.ArrayList;

import com.example.project_practise.R;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class Adapter_groups extends BaseAdapter {
	public ArrayList<Class_group_object> arraylist;
	public Adapter_groups()
	{
		arraylist=new ArrayList<Class_group_object>();
	}
	@Override
	public int getCount() {
		
		return arraylist.size();
	}

	@Override
	public Object getItem(int arg0) {
		
		return arraylist.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	@Override
	public View getView(int index, View view, ViewGroup parent) {
		if(view==null)
		{
			LayoutInflater inflater=LayoutInflater.from(parent.getContext());
			view=inflater.inflate(R.layout.layout_groups,parent,false);
		}
		TextView tv_large=(TextView)(view.findViewById(R.id.textview_layout_group_large));
		TextView tv_medium=(TextView)(view.findViewById(R.id.textview_layout_group_medium));
		Class_group_object object=arraylist.get(index);
		tv_large.setText(object.group_name);
		tv_medium.setText(object.total_numbers);
		return view;
	}
	public void addItem(Class_group_object object)
	{
		arraylist.add(object);
	}

}
