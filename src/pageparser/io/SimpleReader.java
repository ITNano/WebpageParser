package pageparser.io;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class SimpleReader implements Reader{
	
	private String url;
	
	public SimpleReader(String url){
		this.url = url;
	}
	
	public String getPageSource() throws IOException{
		URL url = new URL(this.url);
		URLConnection conn = url.openConnection();
		BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
		
		String inputLine;
		StringBuilder content = new StringBuilder();
		while((inputLine = in.readLine()) != null)content.append(inputLine);
		return content.toString();
	}

}
