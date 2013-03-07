import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;


public class GuestSet extends HashSet<Guest>{
	
	private Map<String, Guest> nameToGuestMap;
	
	public GuestSet()
	{
		super();
		nameToGuestMap = new HashMap<String, Guest>();
	}
	
	@Override 
	public boolean add(Guest guest)
	{
		nameToGuestMap.put(guest.getName(), guest);
		return super.add(guest);
	}
	
	@Override
	public boolean remove(java.lang.Object arg0)
	{
		Guest guest = (Guest)arg0;
		nameToGuestMap.remove(guest.getName());
		return super.remove(guest);
	}
	
	public Guest getGuestByName(String guestName)
	{
		return nameToGuestMap.get(guestName);
	}

}
