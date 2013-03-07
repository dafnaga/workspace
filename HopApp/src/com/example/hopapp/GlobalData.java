package com.example.hopapp;

import hopappcore.TempUserStorage;
import hopappcore.UserStorage;
import android.app.Application;

public class GlobalData extends Application{
	
	private UserStorage<?> userStorage;
	
	public GlobalData(){
		super();
		
		userStorage =  new TempUserStorage();
	}
	
	public UserStorage<?> getUserStorage(){
		return userStorage;
	}
}
