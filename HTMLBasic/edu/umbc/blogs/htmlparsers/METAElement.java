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

public class METAElement implements Serializable{
	
	private String name;
	private String value;
	
	public String getName(){
		return name;
	}

	public String getValue(){
		return value;
	}
	
	public METAElement(String name, String value){
		this.name = name;
		this.value = value;
	}
	
	public static void main(String[] args) {
	}
}
