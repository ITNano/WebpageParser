package pageparser.components;

public class UnknownElement extends WebpageElement {
	
	private String tagName;
	
	public UnknownElement(Element parent, String tagName, String tagHead, String tagBody){
		super(parent, tagHead, tagBody);
		this.tagName = tagName;
	}

	@Override
	public String getTagName() {
		return tagName;
	}

}
