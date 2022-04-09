package parsing;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class par {

	public static void main(String[] args) {
		
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document document = builder.parse("./test.xml");
			
			System.out.println("홍길동");
			return;
			
			/*
			Element root = document.getDocumentElement(); // sample
			System.out.println("[1]"+root.getNodeName());
			
			Node firstNode = root.getFirstChild();
			System.out.println("[2]"+firstNode.getNodeName());
			
			Node secondNode = firstNode.getNextSibling();
			System.out.println("[3]"+secondNode.getNodeName());
			
			NodeList childList = secondNode.getChildNodes();
			for(int i=0;i<childList.getLength();i++) {
				Node item = childList.item(i);
				System.out.println(item.getNodeName());
				System.out.println(item.getTextContent());
			}
			*/
			
		} catch (Exception e) {
			System.out.println(e);
		}
	}
}
