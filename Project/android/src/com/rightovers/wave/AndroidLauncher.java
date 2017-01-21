package com.rightovers.wave;

import android.os.Bundle;
import android.util.Log;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.rightovers.wave.Main;
import com.rightovers.wave.utils.IActivityCaller;

public class AndroidLauncher extends AndroidApplication {
	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		initialize(new Main(new IActivityCaller() {
			@Override
			public void print(String tag, String msg) {
				Log.i(tag, msg);
			}
		}), config);
	}
}
