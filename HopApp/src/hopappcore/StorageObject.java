package hopappcore;

import hopexceptions.StorageException;

import java.util.Map;

public abstract class StorageObject {
	protected Map<String,Object> fields;
	
	protected abstract Storage getStorage();
	
	public StorageObject(){
		
	}
	
	public StorageObject( Map<String, Object> initFields){
		this.fields = initFields;
	}
	
	public Object getField(String fieldName){
		if (fields.containsKey(fieldName)){
			return fields.get(fieldName);	
		}
		return null;
	}
	
	public void setField(String name, Object value) throws StorageException{
		Object oldValue = getField(name);
		try{
			fields.put(name, value);
			getStorage().flushStorageObject(this);			
		} catch (StorageException e) {
			if (oldValue != null){
				fields.put(name, oldValue);
			}
			throw e;
		}
	}
	
	
}
