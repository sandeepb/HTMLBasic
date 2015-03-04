/*
 * Created on May 20, 2005
 *
 */

package edu.umbc.blogs.htmlparsers;

import java.io.Serializable;

/**
 * @author Balijepalli
 *
 */

public class LINKElement implements Serializable{
	
	private String href;
	private String rel;
	private String urn;
	private String methods;
	private String type;
	private String title;
	private String rev;
	
	public LINKElement(String href, String methods, String type, String rel, String rev, String title, String urn){
		this.href = href;
		this.rel = rel;
		this.urn = urn;
		this.methods = methods;
		this.type = type;
		this.title = title;
		this.rev = rev;
	}
	
	public String getHref(){
		return href;
	}
	
	public String getRel(){
		return rel;
	}
	
	public String getUrn(){
		return urn;
	}
	
	public String getMethods(){
		return methods;
		
	}
	
	public String getType(){
		return type;
	}
	
	public String getTitle(){
		return title;
	}
	
	public String getRev(){
		return rev;
	}
	
	public static void main(String[] args) {
	}
}
