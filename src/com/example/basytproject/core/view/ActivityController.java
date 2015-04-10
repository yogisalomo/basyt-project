package com.example.basytproject.core.view;

import java.util.Vector;

import com.example.basytproject.core.view.ActivityControllable;
import com.example.basytproject.core.view.ActivityController.State;

public class ActivityController implements ActivityControllable{
	Vector<ActivityControllable> mList = new Vector<ActivityControllable>();
	class State {
		final static int NONE = 0;
		final static int STARTED = 0;
		final static int RESUMED = 0;
		final static int PAUSED = 0;
		final static int STOPPED = 0;
		
		public int state;
		public ActivityControllable control;
	}
	
	int state = State.NONE;
	
	public void register(ActivityControllable object, int callstate){
		if(mList.add(object)){
			if(state >= State.STARTED){
				object.onStart();
			}
			if(state >= State.RESUMED){
				object.onResume();
			}
			if(state >= State.PAUSED){
				object.onPause();
			}
			if(state >= State.STOPPED){
				object.onStop();
			}
		}
	}
	
	public void unregister(ActivityControllable object, boolean invokeDestroy){
		if(mList.remove(object)){
			if(state >= State.STARTED){
				onStart();
			}
			if(state >= State.RESUMED){
				onResume();
			}
			
			if(invokeDestroy){
				onDestroy();
			}
		}
	}
	
	public void finish(){
		for(ActivityControllable c:mList){
			c.onDestroy();
		}
		mList.removeAllElements();
	}
	
	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		state = State.STARTED;
		for(ActivityControllable c:mList){
			c.onStart();
		}
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		state = State.RESUMED;
		for(ActivityControllable c:mList){
			c.onResume();
		}
	}

	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		state = State.PAUSED;
		for(ActivityControllable c:mList){
			c.onPause();
		}
	}

	@Override
	public void onStop() {
		// TODO Auto-generated method stub
		state = State.STOPPED;
		for(ActivityControllable c:mList){
			c.onStop();
		}
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		state = State.STOPPED;
		for(ActivityControllable c:mList){
			c.onDestroy();
		}
	}

	@Override
	public void onActivityResult() {
		// TODO Auto-generated method stub
		
	}

}