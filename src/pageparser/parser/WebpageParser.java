package pageparser.parser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import pageparser.components.Element;
import pageparser.components.ElementFactory;
import pageparser.components.WebpageBlock;
import pageparser.io.Reader;
import pageparser.io.SimpleReader;

public class WebpageParser {
	
	Reader reader;
	
	public WebpageParser(String url){
		reader = new SimpleReader(url);
	}
	
	public WebpageParser(Reader reader){
		this.reader = reader;
	}
	
	public WebpageBlock parse(){
		String content = "";
		try{
			content = reader.getPageSource();
		}catch(IOException ioe){
			System.out.println("Could not get page source");
			return null;
		}
		System.out.println("Page source fetched");
		return parseContent(content);
	}
	
	public static WebpageBlock parseContent(String content){
		return parseContent(null, content);
	}
	
	private static WebpageBlock parseContent(Element parent, String content){
		List<Element> elements = new ArrayList<Element>();
		int startPos = 0, endPos = 0, contentStartPos = 0, beginTags = 0, endTags = 0;
		String taghead = "", tagname = "", tmp = "";
		while(startPos<content.length()){
			startPos = content.indexOf('<');
			if(startPos<0)break;
			endPos = content.indexOf('>', startPos);		//FIXME can give wrong result! check quotes.
			if(endPos<0)break;
			taghead = content.substring(startPos, endPos+1);
			tagname = ElementFactory.getTagName(taghead);
			
			beginTags = 0;
			endTags = 0;
			contentStartPos = startPos;
			
			do{
				contentStartPos = content.indexOf("</"+tagname+">", contentStartPos+1);
				if(contentStartPos<0)break;
				
				tmp = content.substring(endPos, contentStartPos);
				endTags++;
				int index1, index2 = 0, index = 0;
				while((index1 = tmp.indexOf("<"+tagname+">", index)) > 0 || (index2 = tmp.indexOf("<"+tagname+" ", index)) > 0){
					index = index1*index2>0?Math.min(index1, index2):Math.max(index1, index2);
					beginTags++;
					index++;
				}
			}while(endTags<=beginTags);
			
			elements.add(ElementFactory.getElement(parent, taghead, contentStartPos<0?"":content.substring(endPos+1, contentStartPos).trim()));
			content = content.substring(contentStartPos<0?endPos+1:contentStartPos+tagname.length()+3);
			startPos = 0;
		}
		
		for(Element element : elements){
			if(!element.getTagName().equals("script")){		//do not analyze script tags
				parseContent(element, element.getContent());
			}
		}
		return new WebpageBlock(elements, true);
	}

}
