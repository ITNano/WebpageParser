package pageparser.io;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

public class PostRequestReader implements Reader {
	
	private String url;
	private Map<String, String> data, headers;

	public PostRequestReader(String url, Map<String, String> data, Map<String, String> requestHeaders){
		this.url = url;
		this.data = data;
		this.headers = requestHeaders;
	}
	
	@Override
	public String getPageSource() throws IOException {
		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection)obj.openConnection();
 
		//add request header
		con.setRequestMethod("POST");
		for(String key : headers.keySet()){
			con.setRequestProperty(key, headers.get(key));
		}
 
		//setup parameters
		String urlParameters = "";
		for(String key : data.keySet()){
			urlParameters += (urlParameters.length()>0?"&":"")+key+"="+data.get(key);
		}
 
		// Send post request
		con.setDoOutput(true);
		DataOutputStream wr = new DataOutputStream(con.getOutputStream());
		wr.writeBytes(urlParameters);
		wr.flush();
		wr.close();
 
		int responseCode = con.getResponseCode();
		System.out.println("\nSending 'POST' request to URL : " + url);
		System.out.println("Post parameters : " + urlParameters);
		System.out.println("Response Code : " + responseCode);
 
		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();
 
		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();
 
		return response.toString();
	}

}
