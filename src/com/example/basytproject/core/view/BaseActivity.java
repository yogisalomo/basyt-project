package com.example.basytproject.core.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

import com.example.basytproject.BPContext;

public class BaseActivity extends ActionBarActivity{
	protected ActivityController aController = new ActivityController();
	
	public ActivityController getCycleController(){
		return aController;
	}
	
	@Override
	protected void onCreate(Bundle arg0){
		super.onCreate(arg0);
	}
	
	public void setContentView(int layoutResID, boolean isViewMap){
		if(isViewMap){
			setContentView(layoutResID);
			ViewMapper.mapLayout(this,getWindow().getDecorView());
		}
		else {
			setContentView(layoutResID);
		}
	}
	
	@Override
	protected void onResume(){
		super.onResume();
		aController.onResume();
		if(!BPContext.sReceiver.isActive()){
			BPContext.sReceiver.activate();
		}
	}
	
	@Override
	protected void onPause(){
		super.onPause();
		aController.onPause();
		if(BPContext.sReceiver.isActive()){
			BPContext.sReceiver.deactivate();
		}
	}
	
	@Override
	protected void onStop(){
		super.onStop();
		aController.onStop();
	}
	
	@Override
	protected void onDestroy(){
		super.onDestroy();
		aController.finish();
	}
	
	@Override
	protected void onActivityResult(int arg0, int arg1, Intent arg2){
		super.onActivityResult(arg0, arg1, arg2);
		aController.onActivityResult();
	}
}