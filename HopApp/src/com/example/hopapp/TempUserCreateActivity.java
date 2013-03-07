package com.example.hopapp;

import hopexceptions.StorageException;
import hopappcore.User;
import android.os.Bundle;
import android.R.anim;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;

public class TempUserCreateActivity extends Activity {

	public static final int CREATE_NEW_PLAYER_REQUEST_CODE = 0;
	Intent inputIntent;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_temp_user_create);
		
		inputIntent = getIntent();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_temp_user_create, menu);
		return true;
	}
	
	public void buttonClickCreate(View view){
		GlobalData g = ((GlobalData)getApplicationContext());
		User newUser;
		// TODO validate name is legal		
		
		String firstName = ((EditText)findViewById(R.id.user_create_edit_text_first_name)).getText().toString();
		String lastName = ((EditText)findViewById(R.id.user_create_edit_text_last_name)).getText().toString();
		String username = firstName+lastName;
				
		try{
			newUser = g.getUserStorage().createUser(username);
			newUser.setFirstName(firstName);
			newUser.setLastName(lastName);			
		}
		catch (StorageException e) {
			return;
		}
		
		Intent intent = new Intent();
		intent.putExtra("UserId", newUser.getUserId());
		setResult(RESULT_OK, intent);
		finish();
	}
}