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

			Element root = document.getDocumentElement(); // sample
			System.out.println("[1]"+root.getNodeName());
			
			Node firstNode = root.getFirstChild();
			System.out.println("[2]"+firstNode.getNodeName());
			
			NodeList secondNodes = root.getChildNodes();
			for (int i=0;i<secondNodes.getLength();i++) {
				System.out.println("[3]"+secondNodes.item(i).getNodeName());
				if(secondNodes.item(i).getNodeName().equals("body")) {
					Node body_node = secondNodes.item(i);
					Node items_node = body_node.getFirstChild();
					Node second_node = items_node.getNextSibling();
					Node item_node = second_node.getFirstChild();
					Node second_item_node = item_node.getNextSibling();
					//System.out.println("[5]"+second_item_node.getNodeName());
					NodeList itemList = second_item_node.getChildNodes();
					for (int j=0;j<itemList.getLength();j++) {
						Node tmp_item = itemList.item(j);
//						System.out.println(tmp_item.getNodeName());
//						System.out.println(tmp_item.getTextContent());
						
						if(tmp_item.getNodeName().equals("stationName")) {
							System.out.println(tmp_item.getTextContent());
						}
					}
				}
			}
			
//			NodeList childList = secondNode.getChildNodes();
//			for(int i=0;i<childList.getLength();i++) {
//				Node item = childList.item(i);
//				System.out.println(item.getNodeName());
//				System.out.println(item.getTextContent());
//				
//				if(item.getNodeName().equals("name")) {
//					System.out.println(item.getTextContent());
//				}
//			}
			
		} catch (Exception e) {
			System.out.println(e);
		}
	}
}