package fine_dust2;

import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;


public class ApiExplorer {
	static enum dust_type {
		FINE_DUST,
		VFINE_DUST
	}
	
    public static void main(String[] args) throws IOException {
        StringBuilder urlBuilder = new StringBuilder("http://apis.data.go.kr/B552584/ArpltnInforInqireSvc/getCtprvnRltmMesureDnsty"); /*URL*/
        urlBuilder.append("?" + URLEncoder.encode("serviceKey","UTF-8") + "=kj9rVtwid%2FEpOFekRhn%2Bzyu2KBcIReuxj%2BpLdZ1NuMyi1Im5XORdmUe9lkxXAAHBNqEhOg18ATJOj4Lqs5MM%2FA%3D%3D"); /*Service Key*/
        urlBuilder.append("&" + URLEncoder.encode("returnType","UTF-8") + "=" + URLEncoder.encode("xml", "UTF-8")); /*xml 또는 json*/
        urlBuilder.append("&" + URLEncoder.encode("numOfRows","UTF-8") + "=" + URLEncoder.encode("100", "UTF-8")); /*한 페이지 결과 수*/
        urlBuilder.append("&" + URLEncoder.encode("pageNo","UTF-8") + "=" + URLEncoder.encode("1", "UTF-8")); /*페이지번호*/
        urlBuilder.append("&" + URLEncoder.encode("sidoName","UTF-8") + "=" + URLEncoder.encode("서울", "UTF-8")); /*시도 이름(전국, 서울, 부산, 대구, 인천, 광주, 대전, 울산, 경기, 강원, 충북, 충남, 전북, 전남, 경북, 경남, 제주, 세종)*/
        urlBuilder.append("&" + URLEncoder.encode("ver","UTF-8") + "=" + URLEncoder.encode("1.0", "UTF-8")); /*버전별 상세 결과 참고*/
        URL url = new URL(urlBuilder.toString());
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-type", "application/json");
        //System.out.println("Response code: " + conn.getResponseCode());
        BufferedReader rd;
        if(conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
            rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        } else {
            rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
        }
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = rd.readLine()) != null) {
            sb.append(line+"\n");
        }
        rd.close();
        conn.disconnect();
        //System.out.println(sb.toString());
        xml_parsing(sb.toString(), "중구");
    }
    
    public static void xml_parsing(String data, String target_location) {
    	try {
	    	String xml = data;
	    	DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
	    	DocumentBuilder builder = factory.newDocumentBuilder();
	    	
	    	InputStream instream = new ByteArrayInputStream(xml.getBytes());
	    	Document doc = builder.parse(instream);
	    	Element root = doc.getDocumentElement();
	    	NodeList items = root.getElementsByTagName("item");
	    	
	    	for(int i=0;i<items.getLength();i++) {
	    		Node item = items.item(i);
	    		NodeList nodeList = item.getChildNodes();
	    		
	    		String tmp_location = "none";
	    		String tmp_finedust = "none";
	    		String tmp_vfinedust = "none";
	    		
	    		for(int j=0;j<nodeList.getLength();j++) {
	    			Node cur_node = nodeList.item(j);
	    			if (cur_node.getNodeName().equals("stationName")) {
	    				tmp_location = cur_node.getTextContent();
	    			}
	    			if (cur_node.getNodeName().equals("pm10Value")) {
	    				tmp_finedust = cur_node.getTextContent();
	    			}
	    			if (cur_node.getNodeName().equals("pm25Value")) {
	    				tmp_vfinedust = cur_node.getTextContent();
	    			}
	    		}
  
    			if(tmp_location.equals(target_location)) {
    				System.out.println("지역  : "+tmp_location);
        			System.out.println("미세  : "+tmp_finedust);
        			System.out.println("초미세 : "+tmp_vfinedust);
        			
        			System.out.println("미세 등급: "+dust_level(tmp_finedust, dust_type.FINE_DUST));
        			System.out.println("초미세 등급: "+dust_level(tmp_vfinedust, dust_type.VFINE_DUST));
        			
    				break;
    			}
    			
	    	}
    	}
    	catch(Exception e) {
    		System.out.println(e.toString());
    	}
	    	
    }
    
    public static String dust_level(String input, dust_type dust_type) {
    	int[] section_array;
    	if (dust_type == dust_type.FINE_DUST) {
    		section_array = new int[] {30,80,15};
    	}
    	else if(dust_type == dust_type.VFINE_DUST){
    		section_array = new int[] {15,35,75};
    	}
    	else {
    		System.out.println("ERROR");
    		return "error";
    	}
    	
    	if (input.equals('-')) {
    		return input;
    	}
    	int value = Integer.parseInt(input);
    	
    	if (value <= section_array[0]) {
    		return "좋음";
    	}
    	else if (value <= section_array[1]) {
    		return "보통";
    	}
    	else if (value <= section_array[2]) {
    		return "나쁨";
    	}
    	else {
    		return "매우나쁨";
    	}
    }
}