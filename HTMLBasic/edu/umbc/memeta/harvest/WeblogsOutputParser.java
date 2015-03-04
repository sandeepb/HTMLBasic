/*
 * Created on May 12, 2005
 *
 */

package edu.umbc.memeta.harvest;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

import java.util.Vector;

import edu.umbc.memeta.helper.ChangesXMLExtractor;
import edu.umbc.memeta.helper.DateFormatter;
import edu.umbc.memeta.helper.UpdateChanges;
import edu.umbc.memeta.helper.WeblogHolder;

/**
 * @author Sandeep Balijepalli
 *
 */

public class WeblogsOutputParser{

	public static void main(String[] args) throws Exception{
		String urls[] = {
				"04-12-13"/*,
				"04-12-14",
				"04-12-15"*/
				/*"12-10-12",
				"12-10-13",
				"12-10-14",
				"12-10-15",
				"12-10-16",
				"12-10-17",
				"12-10-18",
				
				"12-07-13",
				"12-07-12",
				"12-07-11",
				"12-07-10",
				"12-07-09",
				"12-07-08",
				"12-07-07",
				"12-07-06",
				"12-07-05",
				"12-07-04",
				"12-07-03",
				"12-07-02",
				"12-07-01",
				"12-07-00",

				"12-06-21",
				"12-06-22",
				"12-06-23"
*/		};
		
/*			String urls[] ={"11-20-22", "11-24-08",
					"11-24-09",
					"11-24-10",
					"11-24-11",
					"11-24-12",
					"11-24-13",
					"11-24-14",
					"11-24-15",
					"11-30-23"}; */
			for(int k=0; k<urls.length; k++){
					
					//String url = "http://130.85.95.83:8080/memeta/changes/2006/"+urls[k]+".xml";
					String url="http://rpc.weblogs.com/shortChanges.xml";
					
		
		//String url = "http://130.85.95.83:8080/memeta/changes/2005/"+"12-06-07"+".xml";
					try {
						URL page = new URL(url);
					    URLConnection connection = page.openConnection();
						HttpURLConnection httpConnection = (HttpURLConnection)connection;
						// "Mozilla/5.0 (Windows; U; Win98; en-US; rv:1.7.2) Gecko/20040803"
						httpConnection.addRequestProperty("User-Agent", "UMBC Ebiquity Research;");
					    httpConnection.setConnectTimeout(10000);
					    httpConnection.connect();
					    int response = httpConnection.getResponseCode();
					    HttpURLConnection.setFollowRedirects(false);
					  	
					    InputStream r = httpConnection.getInputStream();
			            BufferedReader in = new BufferedReader
						(new InputStreamReader(httpConnection.getInputStream()));
						String inputLine;
						StringBuffer contents = new StringBuffer();
						while ((inputLine= in.readLine()) != null){
							contents.append(inputLine+"\n");
							//System.out.println(inputLine);
						}
						ChangesXMLExtractor extractor = new ChangesXMLExtractor(contents.toString());
						String time = extractor.getXMLUpdateTime();
						System.out.println(time);
						Vector weblogs = extractor.getWeblogs();
						UpdateChanges uC = new UpdateChanges();
						int status=0;
						int count = 0;
						for(int i=0; i <weblogs.size(); i++){
							WeblogHolder w = (WeblogHolder)weblogs.elementAt(i);
						
							//System.out.println(i+":"+w.name+":"+w.url+":");
							//System.out.println(DateFormatter.getFormattedDateTime(time, "-"+w.when));
							String formattedDate;
							//System.out.println(w.url);
							if(w.when.indexOf("-") == 1) formattedDate = DateFormatter.getFormattedDateTime(time, w.when);
							else formattedDate = DateFormatter.getFormattedDateTime(time, "-"+w.when);
							//if(i==1) System.out.println(w.name+" :"+w.url+":"+formattedDate);
							if(w.url.contains("myspace.com")){
								System.out.println(w.name+" :"+w.url+":"+formattedDate);
								//status = uC.insertWeblog(w.name, w.url, formattedDate );
							}
							if(status == 1)count++;
							//System.out.println(w.name+" :"+w.url+":"+formattedDate);
						}
						System.out.println("Inserted new blogs: "+count);
					    //Thread.sleep(1800000);
					}
						
					catch(Exception e){
						System.out.println(url);
					}
					
					
					
			}
	}
			//}
	}

