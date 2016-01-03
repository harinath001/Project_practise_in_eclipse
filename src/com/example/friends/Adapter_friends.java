package com.example.friends;

import java.util.ArrayList;
import com.example.project_practise.R;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


public class Adapter_friends extends BaseAdapter{

	public ArrayList<Class_friend_object> list;
	public Adapter_friends()
	{
		list=new ArrayList<Class_friend_object>();
		/*Class_friend_object obj1=new Class_friend_object("friend1");
		list.add(obj1);
		Class_friend_object obj2=new Class_friend_object("friend2");
		list.add(obj2);
		Class_friend_object obj3=new Class_friend_object("friend3");
		list.add(obj3);
		Class_friend_object obj4=new Class_friend_object("friend4");
		list.add(obj4);
		Class_friend_object obj5=new Class_friend_object("friend5");
		list.add(obj5);
		Class_friend_object obj7=new Class_friend_object("friend6");
		list.add(obj7);
		Class_friend_object obj8=new Class_friend_object("friend7");
		list.add(obj8);
		Class_friend_object obj9=new Class_friend_object("friend8");
		list.add(obj9);
		Class_friend_object obj10=new Class_friend_object("friend9");
		list.add(obj10);*/
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		
		return list.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		
		return list.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	@Override
	public View getView(int index,  View view, final ViewGroup parent) {
		// TODO Auto-generated method stub
		if(view==null)
		{
			LayoutInflater inflater= LayoutInflater.from(parent.getContext());
			view=inflater.inflate(R.layout.layout_friends,parent,false);
			
		}
		final String phone_num=list.get(index).phone;
		TextView tv=(TextView)(view.findViewById(R.id.textview_layout_friends));
		tv.setText(list.get(index).name);
		TextView tv2=(TextView)(view.findViewById(R.id.textview_layout_friends_phone));
		tv2.setText(phone_num);
		
		
		
		
		return view;
	}

}
