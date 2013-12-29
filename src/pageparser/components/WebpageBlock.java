package pageparser.components;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class WebpageBlock{
	
	List<Element> elements;
	
	public WebpageBlock(Element el){
		this(el, true);
	}
	
	public WebpageBlock(Element el, boolean all){
		this(all?el.getAllChildren():el.getChildren());
		if(!all)elements.add(el);
	}
	
	public WebpageBlock(List<Element> elements, boolean all){
		this.elements = new ArrayList<Element>();
		for(Element el : elements){
			this.elements.addAll(all?el.getAllChildren():el.getChildren());
			if(!all)this.elements.add(el);
		}
	}
	
	public WebpageBlock(List<Element> elements){
		this.elements = elements;
	}
	
	public Element getElementById(String id){
		for(Element el : elements){
			if(id.equals(el.getID())){
				return el;
			}
		}
		return null;
	}
	
	public List<Element> getElementsByClassname(String classname){
		List<Element> answer = new ArrayList<Element>();
		for(Element el : elements){
			if(Arrays.asList(el.getClassNames()).indexOf(classname) >= 0){
				answer.add(el);
			}
		}
		return answer;
	}
	
	public List<Element> getElementsByTagname(String tagname){
		List<Element> answer = new ArrayList<Element>();
		for(Element el : elements){
			if(tagname.equals(el.getTagName())){
				answer.add(el);
			}
		}
		return answer;
	}
	
	public List<Element> getAllElements(){
		return this.elements;
	}
	
	public void printTree(){
		printTree(null, 0);
	}
	
	private void printTree(Element element, int index){
		if(element == null){
			element = this.elements.get(0);
			while(element.getParent() != null)element = element.getParent();	//Get top element
		}

		for(int i = 0; i<index*6; i++)System.out.print("-");
		System.out.println(element.getTagName());
		for(Element el : element.getChildren()){
			printTree(el, index+1);
		}
	}
}
