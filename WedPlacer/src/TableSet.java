	import java.util.HashMap;
	import java.util.HashSet;
import java.util.Map;

public class TableSet extends HashSet<GuestTable>{

	private Map<Integer, GuestTable> nunberToTableMap;
	
	public TableSet()
	{
		super();
		nunberToTableMap = new HashMap<Integer, GuestTable>();
	}
	
	@Override 
	public boolean add(GuestTable guestTable)
	{
		nunberToTableMap.put(guestTable.getTableNumber(), guestTable);
		return super.add(guestTable);
	}
	
	@Override
	public boolean remove(java.lang.Object arg0)
	{
		GuestTable guestTable = (GuestTable)arg0;
		nunberToTableMap.remove(guestTable.getTableNumber());
		return super.remove(guestTable);
	}
	
	public GuestTable getTableByNumber(Integer tableNumber)
	{
		return nunberToTableMap.get(tableNumber);
	}

}


