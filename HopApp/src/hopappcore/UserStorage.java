package hopappcore;

import hopexceptions.NoSuchUserException;
import hopexceptions.UsernameAlreadyExistsException;

import java.util.List;

public abstract class UserStorage<T extends User> extends Storage {		
		public abstract T getUser(Integer userId) throws NoSuchUserException;
		public abstract T getUser(String  username) throws NoSuchUserException;
		public abstract List<T> searchUser(String fullName);
		public abstract List<T> searchUser(String firstName, String lastName);
		
		public abstract T createUser(String username) throws UsernameAlreadyExistsException;
		public abstract void removeUser(String username) throws NoSuchUserException;
		public abstract void updateUser(User user);	
}
