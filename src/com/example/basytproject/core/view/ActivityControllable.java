package com.example.basytproject.core.view;

public interface ActivityControllable {
	public void onStart();
	public void onResume();
	public void onPause();
	public void onStop();
	public void onDestroy();
	public void onActivityResult();
}
