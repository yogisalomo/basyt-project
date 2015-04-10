package com.example.basytproject.core.view;

import java.lang.reflect.Field;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

public class ViewMapper {
	public static View inflateLayout(Context context, Object container, int layoutId){
		View view = LayoutInflater.from(context).inflate(layoutId,null);
		mapLayout(container,view);
		return view;
	}

	public static View mapLayout(Object container, View view) {
		// TODO Auto-generated method stub
		View view0 = null;
		Field[] fields = container.getClass().getDeclaredFields();
		for(Field field:fields){
			DeclareView param = field.getAnnotation(DeclareView.class);
			if(param != null){
				int id = param.id();
				view0 = view.findViewById(id);
				try{
					field.setAccessible(true);
					field.set(container, view0);
				}
				catch(Exception e){
					e.printStackTrace();
				}
				
				//Click Listener
				String clk = param.click().trim();
				if(clk.length()>0){
					if(clk.equals("this")){
						view0.setOnClickListener((View.OnClickListener)container);
					}
					else {
						try {
							Field field0 = container.getClass().getField(clk);
							view0.setOnClickListener((View.OnClickListener) field0.get(container));
						}
						catch(Exception e) {
							e.printStackTrace();
						}
					}
				}
			}
		}
		return view;
	}
}