package hopappcore;

import hopexceptions.StorageException;

import java.util.Map;

public abstract class User extends StorageObject{
	
	public User(){
		
	}
	
	public User(Map<String, Object> initFields) {
		super(initFields);
	}

	static class UserFields{
		static final String USER_ID = "FIELD_USER_USER_ID";
		
		static final String USERNAME = "FIELD_USER_USERNAME";
		static final String FIRST_NAME = "FIELD_USER_FIRST_NAME";
		static final String LAST_NAME = "FIELD_USER_LAST_NAME";
	}	
	
	public abstract UserStorage<?> getStorage();

	public String getFirstName(){
		return (String) getField(UserFields.FIRST_NAME);
	}

	public void setFirstName(String firstName) throws StorageException {
		setField(UserFields.FIRST_NAME, firstName);
	}
	
	public String getLastName() {
		return (String) getField(UserFields.LAST_NAME);
	}

	public void setLastName(String lastName) throws StorageException {
		setField(UserFields.LAST_NAME, lastName);		
	}

	public String getUserName() {
		return (String) getField(UserFields.USERNAME);
	}

	public void setUserName(String userName) throws StorageException {
		setField(UserFields.USERNAME, userName);
	}

	public Integer getUserId() {
		// TODO Auto-generated method stub
		return (Integer) getField(UserFields.USER_ID);
	}		

	
}
