package com.example.basytproject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import com.example.basytproject.BPContext;
import com.wisewells.iamzone.blelibrary.Beacon;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class BPMainActivity extends Activity {
	
	public String distance = "";
	private String M = "M";
	private double rssi = 0;
	
	private HttpClient httpclient;
	private HttpGet httpget;
	final Handler beaconHandler = new Handler();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_bpmain);
		httpclient = new DefaultHttpClient();
        httpget = new HttpGet("http://siontang.com/public/getNotif");
		initBLE();
		
	}
	
	@Override
	protected void onResume(){
		super.onResume();
		beaconHandler.post(mUpdateBeaconList);
	}
	
	@Override
	protected void onPause(){
		super.onPause();
		beaconHandler.removeCallbacks(mUpdateBeaconList);
	}

	private void initBLE() {
		// TODO Auto-generated method stub
		if(!BPContext.sReceiver.isSupprotBLE()){
			Toast.makeText(this, "Your Device doesn't Support Bluetooth LE", Toast.LENGTH_SHORT).show();
			finish();
		}
		
		if(!BPContext.sReceiver.isBluetoothOn()){
			Intent enableBTIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
			startActivityForResult(enableBTIntent,1000);
		}
	}
	
	private Runnable mUpdateBeaconList = new Runnable(){
		@Override
		public void run(){
			BPContext.sTracker.getAllNearbyBeacons();
			Beacon beacons = BPContext.sTracker.getMostNearbyBeacon();
			if(beacons == null){
				System.out.println("No Beacons Detected");
				getNotifNoBeacon();
			}
			else{
				System.out.println("ada Beacon");
				checkMessage(); // Used to check whether unread message exist in Server
			}
			beaconHandler.postDelayed(this, 1000);
		}

	};
	
	private void getNotifNoBeacon() {
		// TODO Auto-generated method stub
		Toast.makeText(this, "No Beacon Detected", Toast.LENGTH_SHORT).show();
	}
	
	private void checkMessage() {
		// TODO Auto-generated method stub
		 try {
            HttpResponse response = httpclient.execute(httpget);
            HttpEntity entity = response.getEntity();

            if (entity != null) {
                InputStream inputstream = entity.getContent();
                BufferedReader bufferedreader =
                  new BufferedReader(new InputStreamReader(inputstream));
                StringBuilder stringbuilder = new StringBuilder();

                String currentline = null;
                while ((currentline = bufferedreader.readLine()) != null) {
                    stringbuilder.append(currentline + "\n");
                }
                String result = stringbuilder.toString();
                Toast.makeText(this, result, Toast.LENGTH_SHORT).show();
                Log.v("HTTP REQUEST",result);
                inputstream.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.bpmain, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
