package pageparser.components;

public class Html extends WebpageElement{
	
	public Html(String tagHead, String tagBody){
		super(null, tagHead, tagBody);
	}
	
	public String getTagName(){
		return "html";
	}
}
