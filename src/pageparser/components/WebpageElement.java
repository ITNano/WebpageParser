package pageparser.components;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public abstract class WebpageElement implements Element {

	private Map<String, String> attributes;
	private List<Element> children;
	private Element parent;
	private String innerHTML;
	
	public WebpageElement(Element parent, String tagHead, String tagBody){
		attributes = new LinkedHashMap<String, String>();
		children = new ArrayList<Element>();
		this.parent = parent;
		parseAttributes(tagHead);
		this.innerHTML = tagBody;
	}
	
	public void addChild(Element child){
		children.add(child);
	}
	
	@Override
	public String getAttribute(String key){
		return attributes.get(key);
	}
	
	@Override
	public String getID() {
		return getAttribute("id");
	}

	@Override
	public String[] getClassNames() {
		String classnames = getAttribute("class");
		
		if(classnames == null || classnames.length()<=0){
			return new String[]{};
		}else{
			return classnames.split(" ");
		}
	}
	
	@Override
	public String getContent(){
		return this.innerHTML;
	}
	
	@Override
	public List<Element> getChildren(){
		return children;
	}
	
	@Override
	public List<Element> getAllChildren(){
		List<Element> answer = new ArrayList<Element>();
		for(Element el : children){
			answer.addAll(el.getAllChildren());
		}
		answer.add(this);
		return answer;
	}
	
	@Override
	public Element getParent(){
		return this.parent;
	}

	@Override
	public List<Element> getParents(){
		if(getParent() == null){
			List<Element> answer = new ArrayList<Element>();
			answer.add(this);
			return answer;
		}
		
		List<Element> parents = this.getParents();
		parents.add(this);
		return parents;
	}
	
	public String toString(){
		String answer = "<"+this.getTagName();
		for(String key : attributes.keySet()){
			answer += " "+key+" = \""+attributes.get(key)+"\"";
		}
		answer += ">";	//+this.getContent()+"</"+this.getTagName()+">";
		return answer;
	}
	
	
	//TODO write doc. tagHead on form <taghead .....>
	private void parseAttributes(String tagHead){
		//No tagHead -> no attributes.
		if(tagHead == null){
			return;
		}
		
		//Remove tagName.
		tagHead = (tagHead.indexOf(" ")<0?"":tagHead.substring(tagHead.indexOf(" "), tagHead.length()-1));
		
		//Initialize help variables.
		String name = "", content = "";
		int pos = 0, tmpPos = 0;
		while(pos<tagHead.length()){
			pos = tagHead.indexOf('=');
			
			if(pos>0){
				name = tagHead.substring(0, pos).trim();
				pos++;
				
				//Take care of whitespaces after equal sign.
				while(tagHead.charAt(pos)==' ')pos++;
				
				//Take care of all cases, with or without quotes.
				if(tagHead.charAt(pos) == '\"' || tagHead.charAt(pos) == '\''){
					tmpPos = tagHead.indexOf(tagHead.charAt(pos), pos+1);
					pos++;
				}else{
					tmpPos = tagHead.indexOf(' ', pos);
				}
				
				//Calculate data content.
				if(tmpPos<0){
					content = tagHead.substring(pos);
					pos = tagHead.length();
				}else{
					content = tagHead.substring(pos, tmpPos).trim();
					pos = tmpPos;
				}
				}else if(pos<0 && tagHead.trim().length()>0){
				name = tagHead;
				content = "true";
			}else{
				break;
			}
			
			//Add attribute to map.
			attributes.put(name, content);
			
			if(pos<0){
				//Exit if special case found. (it'll be the last)
				break;
			}else{
				//Prepare for next round in loop.
				tagHead = tagHead.substring(pos+1);
				pos = 0;
			}
		}
	}
}
