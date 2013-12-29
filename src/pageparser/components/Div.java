package pageparser.components;

public class Div extends WebpageElement {
	
	public Div(Element parent, String tagHead, String tagBody){
		super(parent, tagHead, tagBody);
	}
	
	@Override
	public String getTagName() {
		return "div";
	}

}
