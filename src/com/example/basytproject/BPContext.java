package com.example.basytproject;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.wisewells.iamzone.blelibrary.BeaconReceiver;
import com.wisewells.iamzone.blelibrary.BeaconTracker;

public class BPContext {
	public static BeaconTracker sTracker = BPApplication.mInstance.mTracker;
	public static BeaconReceiver sReceiver = BPApplication.mInstance.mReceiver;
	
	public static Context getContext(){
		return BPApplication.mInstance;
	}
	
	/*public static SQLiteDatabase getDB(){
		return BPApplication.mInstance.mDB.mDB;
	}*/
	
	//Data Transfer
	public static void putValue(Class<?> cls, Object value){
		String key = cls.getName();
		BPApplication.mInstance.saveObject(key, value);
	}
	
	public static <T> T getValue(Class<?> cls, boolean remove){
		String key = cls.getName();
		T t = (T) BPApplication.mInstance.loadObject(key, remove);
		return t;
	}
	
	//Store Key-Value
	public static void putValue(String key, Object value){
		BPApplication.mInstance.saveObject(key, value);
	}
	
	public static<T> T getValue(String key, boolean remove){
		T t = (T) BPApplication.mInstance.loadObject(key, remove);
		return t;
	}
}