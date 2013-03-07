package hopappcore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import hopappcore.TempUser;
import hopexceptions.NoSuchUserException;
import hopexceptions.UsernameAlreadyExistsException;

public class TempUserStorage extends UserStorage<TempUser>{	
	
	private List<TempUser> users;
	private Map<Integer, TempUser> userByIdMap;
	private Map<String, TempUser> userByUsernameMap;
	
	private Integer highestId = 0;
	
	public TempUserStorage(){
		super();
		TempUser.userStorage = this;
		
		users = new ArrayList<TempUser>();
		userByIdMap = new HashMap<Integer, TempUser>();
		userByUsernameMap = new HashMap<String, TempUser>();
	}
	
	@Override
	public TempUser getUser(Integer userId) throws NoSuchUserException {
		if (userByIdMap.containsKey(userId)){
			return userByIdMap.get(userId);
		}
		throw new NoSuchUserException();
	}

	@Override
	public TempUser getUser(String username) throws NoSuchUserException {
		if (userByIdMap.containsKey(username)){
			return userByIdMap.get(username);
		}
		throw new NoSuchUserException();
	}

	@Override
	public List<TempUser> searchUser(String fullName) {
		List<TempUser> results = new ArrayList<TempUser>();
		
		String firstName = "";
		String lastName = "";
		String[] temp = fullName.split(" ");
		
		if (temp.length > 0){
			firstName = temp[0];
			if (temp.length > 1){
				lastName = temp[1];
			}
		}
		
		Iterator<TempUser> it = users.iterator();		
		while(it.hasNext()){
			TempUser curUser = it.next();
			if (curUser.getFirstName().contains(firstName) && curUser.getLastName().contains(lastName)
					|| curUser.getLastName().contains(firstName) && curUser.getFirstName().contains(lastName)){
				results.add(curUser);
			}
		}
		
		return results;
	}

	@Override
	public List<TempUser> searchUser(String firstName, String lastName) {
		return searchUser(firstName + " " + lastName);
	}

	@Override
	public TempUser createUser(String username) throws UsernameAlreadyExistsException {
		Map<String, Object> newUserFields = new HashMap<String, Object>();
		Integer newId = getNewId();
		
		if (userByUsernameMap.containsKey(username)){
			throw new UsernameAlreadyExistsException();
		}
		
		newUserFields.put(User.UserFields.USER_ID, newId);
		newUserFields.put(User.UserFields.USERNAME, username);
		
		TempUser newUser = new TempUser(newUserFields);
		userByUsernameMap.put(username, newUser);
		userByIdMap.put(newId, newUser);
		
		users.add(newUser);
		
		return newUser;
	}

	private Integer getNewId() {
		return highestId++;
	}

	@Override
	public void removeUser(String username) throws NoSuchUserException {
		TempUser user;
	
		if (!userByUsernameMap.containsKey(username)){
			throw new NoSuchUserException();
		}
		user = userByUsernameMap.get(username);
		
		users.remove(user);
		userByUsernameMap.remove(username);
		userByIdMap.remove(user.getUserId());
	}

	@Override
	public void updateUser(User user) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void flushStorageObject(StorageObject storageObject) {
		// TODO Auto-generated method stub
		
	}

}
