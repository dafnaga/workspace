package hopappcore;

import java.util.Map;

public class TempUser extends User{
	
	static TempUserStorage userStorage;
	
	public TempUser(Map<String, Object> initFields) {
		super(initFields);
	}

	@Override
	public TempUserStorage getStorage(){
		return userStorage;
	}
}
