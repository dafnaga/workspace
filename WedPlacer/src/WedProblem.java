import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder; 
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;


public class WedProblem {
	private TableSet tables;
	private GuestSet guests;
	private List<GuestSet> friendSets;
	private List<GuestSet> familySets;
	
	
	public WedProblem(TableSet tables, GuestSet guests, List<GuestSet> friendSets, List<GuestSet> familySets)
	{
		this.tables = tables;
		this.guests = guests;
		this.friendSets = friendSets;
		this.familySets = familySets;
	}
	
	public void print(){
		// Print guests
		System.out.println("Guests:");
		System.out.println("------");
		
		Iterator<Guest> iter = guests.iterator();
		Guest guest;
		while (iter.hasNext()){
			guest = iter.next();
			System.out.println(guest.getName() + " - MusicLover = " + guest.getMusicLover());
			System.out.print("--- Enemies:");
			
			Guest enemy;
			Iterator<Guest> enemiesIter = guest.getEnemies().iterator();
			while(enemiesIter.hasNext()){
				enemy = enemiesIter.next();
				System.out.print(" " + enemy.getName());
			}
			System.out.println("");
		}
		
		System.out.println("Tables:");
		System.out.println("------");
		
		Iterator<GuestTable> tableIter = tables.iterator();
		GuestTable guestTable;
		while (tableIter.hasNext())
		{
			guestTable = tableIter.next();
			System.out.println("Table number: " + guestTable.getTableNumber() + " CloseToMusic: " + guestTable.getCloseToMusic());
		}
		
		System.out.println("Familes:");
		System.out.println("-------");
		
		for (int i = 0; i < familySets.size(); i++){
			iter = familySets.get(i).iterator();
			while (iter.hasNext()){
				guest = iter.next();
				System.out.print(" " + guest.getName());
			}
			System.out.println("");
		}

		System.out.println("Friends:");
		System.out.println("-------");
		
		for (int i = 0; i < friendSets.size(); i++){
			iter = friendSets.get(i).iterator();
			while (iter.hasNext()){
				guest = iter.next();
				System.out.print(" " + guest.getName());
			}
			System.out.println("");
		}
		System.out.println("");
	}
	
	public static WedProblem loadByXml(String xmlPath) throws ParserConfigurationException, SAXException, IOException
	{
		WedProblem problem;	
		
		GuestSet guests = new GuestSet();
		TableSet tableList = new TableSet();					
		List<GuestSet> friendsSets = new ArrayList<GuestSet>();
		List<GuestSet> familiesSets = new ArrayList<GuestSet>();		
		Map<String, Guest> nameToGuestMap = new HashMap<String, Guest>();
				
		File problemXmlFile = new File(xmlPath);
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();		 
		Document doc;		
		
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();			
		doc = dBuilder.parse(problemXmlFile);
		doc.getDocumentElement().normalize();
		
		Node pNode = doc.getElementsByTagName("WeddingProblem").item(0);
		
		// Parse guests
		Element guestsNode = (Element) ((Element)pNode).getElementsByTagName("Guests").item(0);
		NodeList guestNodes = guestsNode.getElementsByTagName("Guest");
		for (int i = 0; i < guestNodes.getLength(); i++){
			String name;
			Boolean musicLover;
			String musicLoverStr;
			GuestSet enemies = new GuestSet();
			Guest guest;
			
			Element guestNode = (Element)guestNodes.item(i);					
			
			// Name
			name = guestNode.getElementsByTagName("Name").item(0).getTextContent();
			
			// Music lover
			musicLoverStr = guestNode.getElementsByTagName("MusicLover").item(0).getTextContent();
			if (musicLoverStr.equals("True")){
				musicLover = true;
			} else {
				musicLover = false;
			}
			
			// Enemies			
			Node enemiesNode = guestNode.getElementsByTagName("Enemies").item(0);
			NodeList enemiesNodes = ((Element)enemiesNode).getElementsByTagName("Name");
			for (int e = 0; e < enemiesNodes.getLength(); e++){
				Guest enemie;
				Element enemieNode = (Element)enemiesNodes.item(e);
				String enemieName = enemieNode.getTextContent();
				
				// Find a guest with that name
				if (nameToGuestMap.containsKey(enemieName)){
					enemie = nameToGuestMap.get(enemieName);
				} else {
					enemie = new Guest(enemieName);
					guests.add(enemie);						
					nameToGuestMap.put(enemieName, enemie);
				}
				
				enemies.add(enemie);
			}			
			
			// Find a guest with that name, or create a new object
			if (nameToGuestMap.containsKey(name)){
				guest = nameToGuestMap.get(name);
			} else {
				guest = new Guest(name);
				guests.add(guest);
				nameToGuestMap.put(name, guest);					
			}
			
			guest.setName(name);
			guest.setMusicLover(musicLover);
			guest.setEnemies(enemies);
		}
					
		// Parse friends sets
		Element friendsSetsNode = (Element) ((Element)pNode).getElementsByTagName("FriendsSets").item(0);		
		NodeList friendsSetsNodes = friendsSetsNode.getElementsByTagName("FriendsSet");	
		for (int i = 0; i < friendsSetsNodes.getLength(); i++){
			Node friendsSetNode = friendsSetsNodes.item(i);
			GuestSet friendsSet = new GuestSet();
			
			NodeList friendsNodes = ((Element)friendsSetNode).getElementsByTagName("Name");
			for (int f = 0; f < friendsNodes.getLength(); f++){
				Node friendNode = friendsNodes.item(f);
				String friendName = friendNode.getTextContent();
				Guest friend = nameToGuestMap.get(friendName);
				
				friend.setFriends(friendsSet);
				friendsSet.add(friend);
			}
			
			friendsSets.add(friendsSet);
		}
		
		// Parse families sets		
		Element familiesSetsNode = (Element) ((Element)pNode).getElementsByTagName("FamiliesSets").item(0);		
		NodeList familiesSetsNodes = familiesSetsNode.getElementsByTagName("FamilySet");			
		for (int i = 0; i < familiesSetsNodes.getLength(); i++){
			Node familiesSetNode = familiesSetsNodes.item(i);
			GuestSet familySet = new GuestSet();
			
			NodeList familiesNodes = ((Element)familiesSetNode).getElementsByTagName("Name");
			for (int f = 0; f < familiesNodes.getLength(); f++){
				Node familyNode = familiesNodes.item(f);
				String familyName = familyNode.getTextContent();
				Guest family = nameToGuestMap.get(familyName);
				
				family.setFamily(familySet);
				familySet.add(family);
			}
			
			familiesSets.add(familySet);
		}
		
		// Parse tables list
		Element tablesNode = (Element) ((Element)pNode).getElementsByTagName("Tables").item(0);		
		NodeList tableNodes = tablesNode.getElementsByTagName("Table");			
		for (int i = 0; i < tableNodes.getLength(); i++){
			Node tableNode = tableNodes.item(i);
			
			Boolean closeToMusic;
			Integer tableNumber = Integer.parseInt(((Element)tableNode).getElementsByTagName("Number").item(0).getTextContent());
			String closeToMusicStr = ((Element)tableNode).getElementsByTagName("CloseToMusic").item(0).getTextContent();
			if (closeToMusicStr.equals("True")){
				closeToMusic = true;
			} else {
				closeToMusic = false;
			}
			
			GuestTable table = new GuestTable(tableNumber, closeToMusic);
			tableList.add(table);
		}						
		
		problem = new WedProblem(tableList, guests, friendsSets, familiesSets);		
		return problem;
	}

	public Guest getGuestByName(String guestName) {
		return guests.getGuestByName(guestName);
	}

	public GuestTable getGuestTableByNumber(Integer tableNumber) {
		return tables.getTableByNumber(tableNumber);
	}		
}

