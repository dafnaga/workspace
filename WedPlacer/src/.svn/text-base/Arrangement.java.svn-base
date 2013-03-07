import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;


public class Arrangement 
{
	private WedProblem problem;
	private Map<Guest, GuestTable> guestPlacement;
	
	public Arrangement(WedProblem problem)
	{
		guestPlacement = new HashMap<Guest, GuestTable>();
		this.problem = problem;
	}
	
	public GuestTable getGuestPlacement(Guest guest)
	{
		return guestPlacement.get(guest);
	}
	
	public void setGuestPlacement(Guest guest, GuestTable guestTable)
	{
		guestPlacement.put(guest, guestTable);
	}
	
	public void setGuestPlacement(String guestName, Integer tableNumber)
	{
		Guest guest = problem.getGuestByName(guestName);
		GuestTable guestTable = problem.getGuestTableByNumber(tableNumber);
		guestPlacement.put(guest, guestTable);
	}
	
	public static Arrangement loadByXml(WedProblem problem, String xmlPath) throws ParserConfigurationException, SAXException, IOException
	{
		File arrangementXmlFile = new File(xmlPath);
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();		 
		Document doc;		
		
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();			
		doc = dBuilder.parse(arrangementXmlFile);
		doc.getDocumentElement().normalize();
		
		Node pNode = doc.getElementsByTagName("Arrangement").item(0);
		
		//parse arrangement
		NodeList placementNodes = ((Element) pNode).getElementsByTagName("Placement");
		Arrangement arrangement = new Arrangement(problem);
		String guestName;
		Integer guestTable;
		for (int i = 0; i < placementNodes.getLength(); i++){
			Element placementElement = (Element)placementNodes.item(i);
			guestName = placementElement.getAttribute("guestName");
			guestTable = Integer.parseInt(placementElement.getAttribute("tableNumber"));
			arrangement.setGuestPlacement(guestName, guestTable);
		}
		return arrangement;
	}

	public void saveAsXml(String xmlPath) throws ParserConfigurationException, TransformerException
	{		 
		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
 
		// root elements
		Document doc = docBuilder.newDocument();
		
		Element arrangementRootElement = doc.createElement("Arrangement");			
		doc.appendChild(arrangementRootElement);
		
		Iterator<Guest> iter = guestPlacement.keySet().iterator();
		while(iter.hasNext()){
			Guest guest = (Guest)iter.next();
			
			Element placementElement = doc.createElement("Placement");
			arrangementRootElement.appendChild(placementElement);
			
			placementElement.setAttribute("guestName", guest.getName());
			placementElement.setAttribute("tableNumber", ((guestPlacement.get(guest)).getTableNumber()).toString());					
		}
		
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
		DOMSource source = new DOMSource(doc);
		StreamResult result = new StreamResult(xmlPath);
		transformer.setOutputProperty(OutputKeys.INDENT, "yes");		
		transformer.transform(source, result);
	}
	
	public WedProblem getProblem() {
		return problem;
	}

	public void setProblem(WedProblem problem) {
		this.problem = problem;
	}
	
	public void print(){
		System.out.println("Arrangement:");
		System.out.println("------------");
		
		Iterator<Guest> iter = guestPlacement.keySet().iterator();
		Guest guest;
		while (iter.hasNext())
		{
			guest = iter.next();
			Integer tableNumber = guestPlacement.get(guest).getTableNumber();
			System.out.println(guest.getName() + " : " + tableNumber);
		}
		System.out.println("");		
	}
}
