package pageparser.components;

public class Head extends WebpageElement {
	
	public Head(Element parent, String tagHead, String tagBody){
		super(parent, tagHead, tagBody);
	}

	@Override
	public String getTagName() {
		return "head";
	}

}
