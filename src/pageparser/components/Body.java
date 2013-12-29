package pageparser.components;

public class Body extends WebpageElement {

	public Body(Element parent, String tagHead, String tagBody){
		super(parent, tagHead, tagBody);
	}
	
	@Override
	public String getTagName() {
		return "body";
	}

}
