/*
 * Created on May 25, 2005
 *
 */

package edu.umbc.memeta.helper;

import edu.umbc.util.contentextractor.GenericContentExtractor;
import java.util.Vector;
import java.util.regex.Pattern;
import edu.umbc.blogs.htmlparsers.HTMLElementParser;

import java.util.StringTokenizer;


/*
 * @author Sandeep Balijepalli
 *
 */

public class ChangesXMLExtractor extends GenericContentExtractor {

	/**
	 * @param page
	 */
	
	public ChangesXMLExtractor(String page) {
		super(page);
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {
		
	}
	
	public Vector getAllWeblogs(){
		Vector weblogs = new Vector();
		REGEX = "<weblog[ue]* name=\"[\"a-zA-Z0-9\\s-:#?()=~_,/.a-zA-Z,'-;/()@|.!+?:$\\s]*\"[\\s]*/>";
        pattern = Pattern.compile(REGEX);
        matcher = pattern.matcher(page);
        int count = 0;
        while(matcher.find()){
        	String blog = matcher.group();
        	WeblogHolder w = new WeblogHolder(HTMLElementParser.getAttributeValue(blog, "name"),
        			HTMLElementParser.getAttributeValue(blog, "url"), HTMLElementParser.getAttributeValue(blog, "when"));
        	weblogs.addElement(w);
        }
		return weblogs;
	}
	
	
	public Vector getWeblogs(){
		Vector weblogs = new Vector();
		
		StringTokenizer st = new StringTokenizer(page, "\n");
		int count = 1;
		while(st.hasMoreTokens()){
			String line = st.nextToken();
			//System.out.println(line);
			if(line.contains("weblog ") && line.contains("/>")){
				weblogs.addElement(new WeblogHolder(getName(line),
						getURL(line), 
						getTime(line)));
			}
		}
		
		
		return weblogs;
	}
	
	
	public String getName(String weblogEntry){
		String name = "";
		name = weblogEntry.substring(weblogEntry.indexOf("ame=\"")+5, weblogEntry.indexOf("\" url", weblogEntry.indexOf("ame=\"")+5));
		name = name.replaceAll("\n","");
		return name;
	}

	public String getURL(String weblogEntry){
		String url="";
		url = weblogEntry.substring(weblogEntry.indexOf("url=\"")+5, weblogEntry.indexOf("\" when", weblogEntry.indexOf("url=\"")+5));
		url = url.replaceAll("\n","");
		if(url.endsWith("/"))url=url.substring(0,url.length()-1);
		//System.out.println(url);
		return url;
	}

	public String getTime(String weblogEntry){
		String time="";
		time = weblogEntry.substring(weblogEntry.indexOf("hen=\"")+5, weblogEntry.indexOf("\"", weblogEntry.indexOf("hen=\"")+5));
		time = time.replaceAll("\n","");
		return time;
	}
	
	public String getXMLUpdateTime(){
		String time=null;
		REGEX = "updated=\"[/a-zA-Z0-9\\s-:#a-zA-Z,\\s]*\"";
        pattern = Pattern.compile(REGEX);
        matcher = pattern.matcher(page);
        int count = 0;
        if(matcher.find()){
        	time = matcher.group();
        	time = time.substring(time.indexOf("ed=")+4, time.lastIndexOf("\""));
        }
		return time;
	}
}
