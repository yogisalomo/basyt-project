package com.example.basytproject;

import java.util.HashMap;

import com.wisewells.iamzone.blelibrary.BeaconReceiver;
import com.wisewells.iamzone.blelibrary.BeaconTracker;

import android.app.Application;

public class BPApplication extends Application {
	
	public static BPApplication mInstance = null;
	HashMap<String, Object> mStorage = new HashMap<String,Object>();
	
	
	public BeaconTracker mTracker;
	public BeaconReceiver mReceiver;
	
	@Override
	public void onCreate(){
		mInstance = this;
		mTracker = BeaconTracker.getInstance();
		mReceiver = new BeaconReceiver(this, mTracker);
		
		super.onCreate();
	}
	
	public void saveObject(String key, Object value){
		mStorage.put(key, value);
	}
	
	public Object loadObject(String key, boolean remove){
		Object value = null;
		if(remove){
			value = mStorage.remove(key);
		}
		else{
			value = mStorage.get(key);
		}
		
		return value;
	}
}
