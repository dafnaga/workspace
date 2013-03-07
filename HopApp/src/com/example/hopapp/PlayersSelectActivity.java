package com.example.hopapp;

import hopappcore.User;
import hopexceptions.NoSuchUserException;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

public class PlayersSelectActivity extends Activity {
	
	private class UsersListAdapter extends ArrayAdapter<User>{

		int resource;
		
		public UsersListAdapter(Context context, int resource,
				int textViewResourceId, List<User> objects) {
			super(context, resource, textViewResourceId, objects);
			this.resource = resource;
		}
		
		@Override
		public View getView(int position, View convertView, ViewGroup parent){
			View row = convertView;
			
			//if (row == null){
				LayoutInflater inflater = ((Activity)this.getContext()).getLayoutInflater();
				row = inflater.inflate(this.resource, parent, false);				
			//}

			User user = getItem(position);
			TextView text1 = (TextView)row.findViewById(R.id.list_item_text_view);
			text1.setText(user.getFirstName() + " " + user.getLastName());			
			
			return row;
		}
	}
	
	UsersListAdapter userSearchListAdapter;	
	UsersListAdapter userSelectedListAdapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_players_select);
		
		ListView usersList = (ListView) findViewById(R.id.users_select_list);
				
		userSearchListAdapter = new UsersListAdapter(this, R.layout.user_select_list_item, android.R.id.text1, new ArrayList<User>());
		usersList.setAdapter(userSearchListAdapter);
		
		EditText filter = (EditText)findViewById(R.id.players_select_edit_text_filter);
		filter.addTextChangedListener(new TextWatcher(){
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {}			 
			public void onTextChanged(CharSequence s, int start, int before, int count) {}

			@Override
			public void afterTextChanged(Editable arg0) {
				usersListUpdate();
			}
		});
		
		usersList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView parentView, View childView, int position, long id) {				
				userChosen((User)((ListView) findViewById(R.id.users_select_list)).getItemAtPosition(position));
			}
			
		});
		
		userSelectedListAdapter = new UsersListAdapter(this, R.layout.user_select_list_item, android.R.id.text1, new ArrayList<User>());
		ListView selectedUsersList = (ListView) findViewById(R.id.users_selected_list);
		selectedUsersList.setAdapter(userSelectedListAdapter);
		
		usersListUpdate();
	}

	protected void userChosen(User user) {
		int i;

		if (userSelectedListAdapter.getPosition(user) != -1){
			return;
		}
			
		userSelectedListAdapter.add(user);		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_players_select, menu);
		return true;
	}
	
	public void buttonClickClear(View view){		
		try{
			userSearchListAdapter.clear();
		} 
		catch (Exception e) {
			AlertDialog alertDialog;
			alertDialog = new AlertDialog.Builder(this).create();
			alertDialog.setTitle("Packing List");
			alertDialog.setMessage(e.toString());
			alertDialog.show();
		}
	}
	
	public void buttonClickCreateNewPlayer(View view){
		Intent intent = new Intent(this, TempUserCreateActivity.class);
		startActivityForResult(intent, TempUserCreateActivity.CREATE_NEW_PLAYER_REQUEST_CODE);
	}
	
	private void usersListUpdate(){
		GlobalData g = (GlobalData)getApplicationContext();
		String fullname = ((EditText)findViewById(R.id.players_select_edit_text_filter)).getText().toString();
				
		ArrayList<User> userSearchResults = (ArrayList<User>) g.getUserStorage().searchUser(fullname);
		
		userSearchListAdapter.clear();		
		
		Iterator<User> iter = userSearchResults.iterator();
		User currUser;
		while(iter.hasNext()){
			currUser = iter.next();
			userSearchListAdapter.add(currUser);
		}
	}
	
	public void onClickFilter(View view){
		usersListUpdate();
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data){
		if (requestCode == TempUserCreateActivity.CREATE_NEW_PLAYER_REQUEST_CODE){
			newPlayerCreated(data.getExtras().getInt("UserId"));
		}
		usersListUpdate();
	}

	private void newPlayerCreated(int userId) {				
		User newUser;
		try {
			newUser = ((GlobalData)getApplicationContext()).getUserStorage().getUser(userId);
		} catch (NoSuchUserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return;
		}
		userChosen(newUser);		
	}
	
	public void userRemove(View view){
		int i;
		i=0;
	}
}


