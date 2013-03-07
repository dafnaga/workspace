package hopappcore;

import hopexceptions.StorageException;

public abstract class Storage {

	public abstract void flushStorageObject(StorageObject storageObject) throws StorageException;
}
