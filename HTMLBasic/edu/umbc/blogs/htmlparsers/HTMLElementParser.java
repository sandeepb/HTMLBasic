/*
 * Created on Apr 22, 2005
 *
 */

package edu.umbc.blogs.htmlparsers;


/**
 * @author Balijepalli
 *
 */

public class HTMLElementParser {

	public static String getAttributeValue(String tag, String attributeName){
		String attributeValue = null;
		String uAttributeName = attributeName.toUpperCase();
		String lAttributeName = attributeName.toLowerCase();
		int index =-1;
		int valueStartIndex = -1;
		int valueEndIndex = -1;
		if((index=tag.indexOf(uAttributeName+"="))>-1){
			if((valueStartIndex=tag.indexOf("\"" ,index+1))>-1){
				if((valueEndIndex=tag.indexOf("\"", valueStartIndex+1))>-1){
					attributeValue = tag.substring(valueStartIndex+1, valueEndIndex);
				}
				else return null;
			}
			else
				return null;
		}
		else if((index=tag.indexOf(lAttributeName+"="))>-1){
			if((valueStartIndex=tag.indexOf("\"" ,index+1))>-1){
				if((valueEndIndex=tag.indexOf("\"",valueStartIndex+1))>-1){
					attributeValue = tag.substring(valueStartIndex+1, valueEndIndex);
				}
				else return null;
			}
			else
				return null;			
		}
		return attributeValue; 
	}
	
	public String getAttribute(String element, String attributeName){
		String attributeValue = null;
		String uAttributeName = attributeName.toUpperCase();
		String lAttributeName = attributeName.toLowerCase();
		int index =-1;
		int valueStartIndex = -1;
		int valueEndIndex = -1;
		if((index=element.indexOf(uAttributeName+"="))>-1){
			if((valueStartIndex=element.indexOf("\"" ,index+1))>-1){
				if((valueEndIndex=element.indexOf("\"", valueStartIndex+1))>-1){
					attributeValue = element.substring(valueStartIndex+1, valueEndIndex);
				}
				else return null;
			}
			else
				return null;
		}
		else if((index=element.indexOf(lAttributeName+"="))>-1){
			if((valueStartIndex=element.indexOf("\"" ,index+1))>-1){
				if((valueEndIndex=element.indexOf("\"",valueStartIndex+1))>-1){
					attributeValue = element.substring(valueStartIndex+1, valueEndIndex);
				}
				else return null;
			}
			else
				return null;			
		}
		return attributeValue; 
	}
	
	
	// This function assumes that elements have no attributes
	
	public String getElementValue(String element, String elementName){
		String value="";
		String elementNameLower = elementName.toLowerCase();
		String elementNameUpper = elementName.toUpperCase();
		int endindex = element.lastIndexOf(elementNameLower);
		if(endindex == -1) endindex = element.lastIndexOf(elementNameUpper);
		int startindex = element.indexOf(elementNameLower);
		if(startindex == -1) element.indexOf(elementNameUpper);
		value = element.substring(startindex+elementName.length()+1, endindex-2);
		return value;
	}
	
	
	public static void main(String args[]){
		System.out.println(getAttributeValue
	("<link rel=\"alternate\" type=\"application/rss+xml\" title=\"RSS\" href=\"http://feeds.feedburner.com/adrants\" />", "REL"));
		System.out.println
		(getAttributeValue
	("<link rel=\"alternate\" type=\"application/rss+xml\" title=\"RSS\" href=\"http://feeds.feedburner.com/adrants\" />", "type"));
		
		System.out.println
		(getAttributeValue
	("<link rel=\"alternate\" type=\"application/rss+xml\" title=\"RSS\" href=\"http://feeds.feedburner.com/adrants\" />", "tiTLe"));

		System.out.println
		(getAttributeValue
	("<link rel=\"alternate\" type=\"application/rss+xml\" title=\"RSS\" href=\"http://feeds.feedburner.com/adrants\" />", "hREF"));
		
	}
}
