/*
 * Created on May 25, 2005
 *
 */

package edu.umbc.util.contentextractor;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Sandeep Balijepalli
 *
 */

public class GenericContentExtractor {

	
	protected String page;
    protected String REGEX;
    protected Pattern pattern;
    protected Matcher matcher;

	
	public GenericContentExtractor(String page){
		this.page = page;
	}
	
	
	public static void main(String[] args) {
	}
	
	
}
