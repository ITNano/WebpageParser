package pageparser.components;

import java.util.Arrays;

public class ElementFactory {

	public static String getTagName(String tagHead){
		if(tagHead.indexOf(' ', 1) < 0){
			return tagHead.substring(1, tagHead.length()-1);
		}else{
			return tagHead.substring(1, tagHead.indexOf(' ', 1));
		}
	}
	
	public static Element getElement(Element parent, String tagHead, String tagBody){
		String[] tags = {"html", "head", "body", "div"};
		String tagName = getTagName(tagHead);
		
		Element answer;
		int tagNum = Arrays.asList(tags).indexOf(tagName);
		switch(tagNum){
			case 0:	answer = new Html(tagHead, tagBody);break;
			case 1: answer = new Head(parent, tagHead, tagBody);break;
			case 2: answer = new Body(parent, tagHead, tagBody);break;
			case 3: answer = new Div(parent, tagHead, tagBody);break;
			default: answer = new UnknownElement(parent, tagName, tagHead, tagBody);break;
		}
		
		if(parent != null){
			parent.addChild(answer);
		}
		return answer;
	}
}
