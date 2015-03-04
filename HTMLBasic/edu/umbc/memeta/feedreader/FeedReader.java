package edu.umbc.memeta.feedreader;

import edu.umbc.memeta.parser.*;
import java.net.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import java.util.List;

import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.feed.synd.*;
import com.sun.syndication.io.SyndFeedInput;
import com.sun.syndication.io.XmlReader;
import com.sun.syndication.feed.module.*;
import com.totsp.xml.syndication.content.ContentModule;


public class FeedReader {
	
	
	   /** Namespace URI for content:encoded elements */
    private static String CONTENT_NS =
            "http://purl.org/rss/1.0/modules/content/";

    private static String CONTENT_NS_ATOM =
        "http://purl.org/rss/1.0/modules/content/";
    
	public static void main(String args[]) throws Exception{
			//String url = "http://withhomeequityloanswill.blogspot.com/";
			//String url = "http://ebiquity.umbc.edu/blogger";
			//String url = "http://abbreviated.web24.shadowhunter.de/28.html";
			//String url = "http://error.wordpress.com/";
			String url = "http://trackbacks.org";
			//url="http://scaryduck.blogspot.com";
			//url="http://lou55.livejournal.com/";
			url="http://ebiquity.umbc.edu/blogger";
			url="http://datamining.typepad.com/data_mining/";
			//url="http://dannyayers.com/";
			// LiveJournal -- RSS seems to work fine
			url="http://vidaemonologo.blogspot.com/";
			url="http://spanglemonkey.typepad.com/spanglemonkey/";
			url="http://a2zprostatecancer.com/";
			url="http://blogs.pingpoet.com/overflow/";
			url="http://blogs.sun.com/roller/page/loveatfirstboot";
			URL page = new URL(url);
	        URLConnection connection = page.openConnection();
	    	HttpURLConnection httpConnection = (HttpURLConnection)connection;
	    	httpConnection.addRequestProperty("User-Agent", "UMBC-memeta-Bot");
	        httpConnection.setConnectTimeout(5000);
	        httpConnection.setReadTimeout(5000);
	        HTMLParser blog = new HTMLParser(url);
	        blog.getFeeds();
	        
	        
			System.out.println("*************************");
	        System.out.println("Getting useful LINK tags!");
	        HashMap hMap =  blog.getFeeds();
			String feedLocation = null;
	        if(hMap!=null){
			Set set= hMap.keySet();
			Iterator iter = set.iterator();

			while(iter.hasNext()){
				String feedType = iter.next().toString();
				String feedLoc = hMap.get(feedType).toString();
				if(feedLoc.contains("rss"))feedLocation = hMap.get(feedType).toString();
				System.out.println(feedType+"       :::      "+feedLocation);
			}
	        
			System.out.println("Parsing: "+feedLocation);
			if(!feedLocation.startsWith("http://")){
				if(!feedLocation.startsWith("/"))feedLocation = "/"+feedLocation;
				feedLocation = url.substring(0, url.lastIndexOf("/"))+feedLocation;
			}
	        }
			//feedLocation = "http://abbreviated.web24.shadowhunter.de/xml/feed.php";
			//feedLocation = "http://ebiquity.umbc.edu/blogger/feed/";
			//feedLocation = "http://eurotelcoblog.blogspot.com/atom.xml";
			feedLocation="http://blogs.sun.com/roller/rss/loveatfirstboot";
				URL feedUrl = new URL(feedLocation);
	        connection = feedUrl.openConnection();
	    	httpConnection = (HttpURLConnection)connection;
	    	httpConnection.addRequestProperty("User-Agent", "UMBC-memeta-Bot");
	        httpConnection.setConnectTimeout(2000);
	        httpConnection.setReadTimeout(2000);
	        int response = httpConnection.getResponseCode();

            SyndFeedInput input = new SyndFeedInput();

            SyndFeed feed = input.build(new XmlReader(httpConnection));
            String hp = feed.getLink();
            System.out.println("Home Page: "+hp);
            
            String author = feed.getAuthor();
            System.out.println("Author: "+author);
     
            String feedType = feed.getFeedType();
            System.out.println("FeedType: "+feed.getFeedType());
            
            //System.out.println(feed);
            
            List entries = feed.getEntries();
            for(int i=0; i<entries.size(); i++){
            	SyndEntry entry = (SyndEntry)entries.get(i);
            	System.out.println(entry.getTitle()+"--"+entry.getPublishedDate() +"--"+entry.getAuthor());
            
                SyndContent description = entry.getDescription();
                entry.getAuthor();
                System.out.println("-----------------------------------");
                if(feedLocation.contains("atom") || feedLocation.contains("a2z") || feedType.contains("2.0")){
                	//System.out.println(description.getValue());
                    HTMLParser parser = new HTMLParser(description.getValue(), 1);
                    System.out.println(parser.getURLsAsString());                	
                }
                ContentModule module =
                    ((ContentModule) entry.getModule(CONTENT_NS));
                if(module!=null)
                {

                    // Iterate through encoded HTML, creating footers
                    Iterator oldStringIter =
                            module.getEncodeds().iterator();
                    while (oldStringIter.hasNext())
                    {
                    	String html = oldStringIter.next().toString();
                        //System.out.println(oldStringIter.next());
                        System.out.println("********");
                        HTMLParser parser = new HTMLParser(html, 1);
                        System.out.println(parser.getURLsAsString());
                        System.out.println("*********");
                    }

                }                
                //System.out.println(module.getEncodeds());
                
                System.out.println("-----------------------------------");
                
                
            }
			
	}

}
