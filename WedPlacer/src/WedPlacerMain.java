import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.xml.sax.SAXException;


public class WedPlacerMain {

	/**
	 * @param args
	 * @throws IOException 
	 * @throws SAXException 
	 * @throws ParserConfigurationException 
	 * @throws TransformerException 
	 */
	public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException, TransformerException {
		// TODO Auto-generated method stub
		WedProblem problem = WedProblem.loadByXml("ProblemsXML\\problem.xml");		
		problem.print();
		
		Arrangement arrangement = Arrangement.loadByXml(problem, "ProblemsXML\\arrangement.xml");
		arrangement.print();
		arrangement.saveAsXml("ProblemsXML\\arrangement_saved.xml");
	}
}
