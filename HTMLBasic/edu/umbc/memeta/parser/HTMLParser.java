package edu.umbc.memeta.parser;

import java.net.HttpURLConnection;
import java.net.URLConnection;
import java.net.URL;
import org.htmlparser.Node;
import org.htmlparser.Tag;
import org.htmlparser.Parser;
import org.htmlparser.tags.LinkTag;
import org.htmlparser.tags.MetaTag;
import org.htmlparser.tags.TitleTag;
import org.htmlparser.util.ParserException;
import org.htmlparser.util.EncodingChangeException;
import org.htmlparser.visitors.TagFindingVisitor;
import org.htmlparser.visitors.TextExtractingVisitor;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import java.util.Vector;

/**
 * @author Sandeep Balijepalli
 * 
 * This is the base class of memeta's HTML parser. The BlogParser extends
 * this interface.
 * 
 */

public class HTMLParser {
	
	Parser parser;

/**
 * Constructor takes HttpURLConnection as input -- sample usage
 * 			URL page = new URL(url);
	        URLConnection connection = page.openConnection();
	    	httpConnection = (HttpURLConnection)connection;
	    	httpConnection.addRequestProperty("User-Agent", "UMBC-memeta-Bot");
	        httpConnection.setConnectTimeout(5000);
	        httpConnection.setReadTimeout(5000);
 * It is recommended that this constructor be called after 
 * 
 	        int response = httpConnection.getResponseCode();
 */	
	
	public HTMLParser (HttpURLConnection httpConnection) throws ParserException{
			parser = new Parser(httpConnection);
	}

	public HTMLParser(String location) throws ParserException{
		parser = new Parser(location);
	}
	
	public HTMLParser(String html, int flag) throws ParserException{
		parser = new Parser();
		parser.setInputHTML(html);
		
	}
	
	
	public void set (HttpURLConnection httpConnection) throws ParserException{
			parser = new Parser(httpConnection);
	}

	public void close(){
		//
		((HttpURLConnection)parser.getConnection()).disconnect();
		
	}
/**
 * 
 * @return
 * @throws EncodingChangeException
 * @throws ParserException
 */	
	
    public String getText () throws EncodingChangeException, ParserException
    {
    	String text = null;
		parser.reset();
        TextExtractingVisitor visitor = new TextExtractingVisitor();
        parser.visitAllNodesWith (visitor);
        text = visitor.getExtractedText();
		return text;
   	}

    
/**
 * 
 * @return
 * @throws EncodingChangeException
 * @throws ParserException
 */
    
    
	public String getURLsAsString() throws EncodingChangeException, ParserException {
		String urlText = null;
		StringBuffer urlBuffer = new StringBuffer();
		parser.reset();
        Node [] links = parser.extractAllNodesThatAre (LinkTag.class);
        for (int j = 0; j < links.length; j++)
        {
            LinkTag linkTag = (LinkTag)links[j];
            //String name = linkTag.getLinkText ();
            String value = linkTag.getLink ();
            value = value.replaceAll("model","");
            value = value.replaceAll("negative","");
            
            urlBuffer.append(" "+value);
        }
		urlText = urlBuffer.toString();
	    return urlText;
	}

/**
 * 
 * @return
 * @throws EncodingChangeException
 * @throws ParserException
 */	
	
	
	public String getAnchorsAsString() throws EncodingChangeException, ParserException {
		String urlAnchor = null;
		StringBuffer urlBuffer = new StringBuffer();
		parser.reset();
        Node [] links = parser.extractAllNodesThatAre (LinkTag.class);
        for (int j = 0; j < links.length; j++)
        {
            LinkTag linkTag = (LinkTag)links[j];
            String name = linkTag.getLinkText ();
            urlBuffer.append(" "+name);
        }
		urlAnchor = urlBuffer.toString();
	    return urlAnchor;
	}
	
	public String getTitle() throws EncodingChangeException, ParserException{
		parser.reset();
        String title = null;		
        String [] tagsToBeFound = {"title"};
        TagFindingVisitor visitor = new TagFindingVisitor (tagsToBeFound);
        parser.visitAllNodesWith (visitor);
        Node [] allTags = visitor.getTags(0);
        for (int j = 0; j < allTags.length; j++)
        {
            TitleTag titleTag = (TitleTag)allTags[j];
            title = titleTag.getTitle();
        }
        return title;
	}

	
	public String getGenerator() throws EncodingChangeException, ParserException{
		parser.reset();
		String generator = null;
        String [] tagsToBeFound = {"meta", "META"};
        TagFindingVisitor visitor = new TagFindingVisitor (tagsToBeFound);
        parser.visitAllNodesWith (visitor);
        Node [] allTags = visitor.getTags(0);
        for (int j = 0; j < allTags.length; j++)
        {
            MetaTag metaTag = (MetaTag)allTags[j];
            String tagName = metaTag.getMetaTagName();
            String tagValue = metaTag.getMetaContent();
            if(tagName != null)
            if(tagName.equalsIgnoreCase("generator"))
            	generator = tagValue;
        }
        return generator;
     }
	
	
	public HashMap getMetaElements() throws EncodingChangeException, ParserException{
		parser.reset();
		HashMap hMap = new HashMap();
        String [] tagsToBeFound = {"meta", "META"};
        TagFindingVisitor visitor = new TagFindingVisitor (tagsToBeFound);
        parser.visitAllNodesWith (visitor);
        Node [] allTags = visitor.getTags(0);
        for (int j = 0; j < allTags.length; j++)
        {
            MetaTag metaTag = (MetaTag)allTags[j];
            if(metaTag.getHttpEquiv() != null){
            	hMap.put(metaTag.getHttpEquiv(), metaTag.getMetaContent());
            } else {
	            String tagName = metaTag.getMetaTagName();
	            String tagValue = metaTag.getMetaContent();
	            if(tagName != null) hMap.put(tagName, tagValue);
            }
        }
        if(hMap.size() == 0) return null;
        else return hMap;
	}

	
	public HashMap getURLs() throws EncodingChangeException, ParserException{
		HashMap hMap = new HashMap();
		parser.reset();
        Node [] links = parser.extractAllNodesThatAre (LinkTag.class);
        for (int j = 0; j < links.length; j++)
        {
            LinkTag linkTag = (LinkTag)links[j];
            String value = linkTag.getLink ();
            value = value.replaceAll("model","");
            value = value.replaceAll("negative","");
            if(hMap.containsKey(value)){
            	int newVal = Integer.parseInt(hMap.get(value).toString())+1;
            	hMap.put(value, newVal+"");
            }else
            	hMap.put(value, "1");
        }
        if(hMap.size() == 0) return null;
        else return hMap;
	}

	public HashMap getAnchors() throws EncodingChangeException, ParserException{
		HashMap hMap = new HashMap();
		parser.reset();
        Node [] links = parser.extractAllNodesThatAre (LinkTag.class);
        for (int j = 0; j < links.length; j++)
        {
            LinkTag linkTag = (LinkTag)links[j];
            String name = linkTag.getLinkText ();
            if(hMap.containsKey(name)){
            	int newVal = Integer.parseInt(hMap.get(name).toString())+1;
            	hMap.put(name, newVal+"");
            }else
            	hMap.put(name, "1");
        }
        if(hMap.size() == 0) return null;
        else return hMap;
	}

	
	public Vector[] getURLsAnchorsCount() throws EncodingChangeException, ParserException{
		parser.reset();
		Vector urls = new Vector();
		Vector anchors = new Vector();
		Vector counts = new Vector();
        Node [] links = parser.extractAllNodesThatAre (LinkTag.class);
        for (int j = 0; j < links.length; j++)
        {
            LinkTag linkTag = (LinkTag)links[j];
            String name = linkTag.getLinkText ();
            String value = linkTag.getLink ();            
            // Find the key 
            int currentIndex = -1;
            boolean found = false;
/*            System.out.println("*******");
            System.out.println(value);
            System.out.println(name);
            System.out.println("*******");
*/            while((currentIndex<(urls.size()-1)) && found == false && (currentIndex = urls.indexOf(value, currentIndex+1))!= -1){
            	//System.out.println(currentIndex);
            	if(anchors.elementAt(currentIndex).equals(name)){
            		found = true;
            	}
            }
            //System.out.println(currentIndex);
            if(found == true){
            	int newVal = Integer.parseInt(counts.elementAt(currentIndex).toString())+1;
            	counts.setElementAt(newVal+"", currentIndex);            	
            }else{
            	urls.addElement(value);
            	anchors.addElement(name);
            	counts.addElement("1");
            }
        }
        Vector[] urlsAnchorsCounts = new Vector[3];
        urlsAnchorsCounts[0]=new Vector();
        urlsAnchorsCounts[1]=new Vector();
        urlsAnchorsCounts[2]=new Vector();
        urlsAnchorsCounts[0]=(Vector)urls.clone();
        urlsAnchorsCounts[1]=(Vector)anchors.clone();
        urlsAnchorsCounts[2]=(Vector)counts.clone();
/*        for(int i=0; i<urls.size(); i++){
        	System.out.println(urls.elementAt(i)+"::::"+anchors.elementAt(i)+"::::"+counts.elementAt(i));
        }*/
        return urlsAnchorsCounts;
        
	}
	
	

	public HashMap getFeeds() throws EncodingChangeException, ParserException{
		parser.reset();
		HashMap hMap = new HashMap();
        String [] tagsToBeFound = {"link", "LINK"};
        TagFindingVisitor visitor = new TagFindingVisitor (tagsToBeFound);
        parser.visitAllNodesWith (visitor);
        Node [] allTags = visitor.getTags(0);
        for (int j = 0; j < allTags.length; j++)
        {
        	Tag tag = (Tag)allTags[j];
        	if(tag.getAttribute("rel") != null){
        		if(tag.getAttribute("rel").equalsIgnoreCase("alternate")){
        			String title = null;
        			String value = null;
        			if((title=tag.getAttribute("title")) == null)
        				title = tag.getAttribute("TITLE");
        			if((value=tag.getAttribute("href")) == null)
        				value = tag.getAttribute("HREF");        			
        			if(value != null && title != null)
        				hMap.put(title, value);
        		}
        	}
        }
        if(hMap.size() == 0) return null;
        else return hMap;
	}
	
	public String getRSS() throws EncodingChangeException, ParserException{
		parser.reset();
		String rss = null;
        String [] tagsToBeFound = {"link", "LINK"};
        TagFindingVisitor visitor = new TagFindingVisitor (tagsToBeFound);
        parser.visitAllNodesWith (visitor);
        Node [] allTags = visitor.getTags(0);
        for (int j = 0; j < allTags.length; j++)
        {
        	Tag tag = (Tag)allTags[j];
        	if(tag.getAttribute("rel") != null){
        		if(tag.getAttribute("rel").equalsIgnoreCase("alternate")){
        			String title = null;
        			String value = null;
        			if((title=tag.getAttribute("title")) == null)
        				title = tag.getAttribute("TITLE");
        			if((value=tag.getAttribute("href")) == null)
        				value = tag.getAttribute("HREF");        			
        			if(value != null && title != null && (title.contains("rss") || title.contains("RSS")))
        				rss = value;
        		}
        	}
        }
        return rss;
	}

	public String getAtom() throws EncodingChangeException, ParserException{
		parser.reset();
		String rss = null;
        String [] tagsToBeFound = {"link", "LINK"};
        TagFindingVisitor visitor = new TagFindingVisitor (tagsToBeFound);
        parser.visitAllNodesWith (visitor);
        Node [] allTags = visitor.getTags(0);
        for (int j = 0; j < allTags.length; j++)
        {
        	Tag tag = (Tag)allTags[j];
        	if(tag.getAttribute("rel") != null){
        		if(tag.getAttribute("rel").equalsIgnoreCase("alternate")){
        			String title = null;
        			String value = null;
        			if((title=tag.getAttribute("title")) == null)
        				title = tag.getAttribute("TITLE");
        			if((value=tag.getAttribute("href")) == null)
        				value = tag.getAttribute("HREF");        			
        			if(value != null && title != null && (title.contains("atom") || title.contains("Atom")))
        				rss = value;
        		}
        	}
        }
        return rss;
	}
	
	public static void main(String args[]) throws Exception {
			String url = "http://0-degrees.blogspot.com";
			URL page = new URL("http://0-degrees.blogspot.com");
	        URLConnection connection = page.openConnection();
	        HttpURLConnection httpConnection = (HttpURLConnection)connection;
	    	httpConnection.addRequestProperty("User-Agent", "UMBC-memeta-Bot");
	        httpConnection.setConnectTimeout(5000);
	        httpConnection.setReadTimeout(5000);
	        int response = httpConnection.getResponseCode();
	        System.out.println(httpConnection.getContentType());
	        System.out.println(httpConnection.getContentEncoding());
	        
	        
	        HTMLParser parser = new HTMLParser(httpConnection);
/*	        System.out.println("Getting all META tags!");
	        HashMap hMap = htmlParser.getMetaElements();
			Set set= hMap.keySet();
			Iterator iter = set.iterator();
			while(iter.hasNext()){
				String tagName = iter.next().toString();
				String tagValue = hMap.get(tagName).toString();
				System.out.println(tagName+"       :::      "+tagValue);
			}
			
			System.out.println("*************************");
	        System.out.println("Getting useful LINK tags!");
	        hMap =  htmlParser.getFeeds();
			set= hMap.keySet();
			iter = set.iterator();
			while(iter.hasNext()){
				String tagName = iter.next().toString();
				String tagValue = hMap.get(tagName).toString();
				System.out.println(tagName+"       :::      "+tagValue);
			}
*/		
			System.out.println("*************************");
	        System.out.println("Getting useful META tags!");
	        
	        
	        HashMap hMap = parser.getMetaElements();
			Set set= hMap.keySet();
			Iterator iter = set.iterator();
			while(iter.hasNext()){
				String tagName = iter.next().toString();
				String tagValue = hMap.get(tagName).toString();
				System.out.println(tagName+"--"+tagValue);
				//System.out.println(tagName+"       :::      "+tagValue);
			}
			
			System.out.println("*************************");
	        System.out.println("Getting useful LINK tags!");
	        hMap =  parser.getFeeds();
			set= hMap.keySet();
			iter = set.iterator();
			while(iter.hasNext()){
				String tagName = iter.next().toString();
				String tagValue = hMap.get(tagName).toString();
				System.out.println(tagName+"--"+tagValue);
			}

			
			System.out.println("*************************");
	        System.out.println("Getting useful outlinks ");
			
			Vector outlinks[] = parser.getURLsAnchorsCount();
			Vector urls = outlinks[0];
			Vector anchors = outlinks[1];
			
			for(int i=0; i<urls.size(); i++){
				String outlink = urls.elementAt(i).toString();
				String anchor = anchors.elementAt(i).toString();
				if(outlink.startsWith("http://") && anchor.length()< 256 && outlink.length()< 75 && !outlink.contains(url)){
					System.out.println(anchor+"--"+outlink);
					
				}
			}
	        
	}
}
