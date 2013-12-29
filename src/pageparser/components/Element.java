package pageparser.components;

import java.util.List;

public interface Element{

	public String getAttribute(String key);
	public String getTagName();
	public String getID();
	public String[] getClassNames();
	public String getContent();

	public void addChild(Element e);
	public List<Element> getChildren();
	public List<Element> getAllChildren();
	public Element getParent();
	public List<Element> getParents();
	
}
