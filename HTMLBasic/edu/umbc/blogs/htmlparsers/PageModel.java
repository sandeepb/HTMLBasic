/*
 * Created on May 20, 2005
 *
 */

package edu.umbc.blogs.htmlparsers;

/**
 * @author Sandeep Balijepalli
 *
 */

public class PageModel {

	private PageLINK pageLINK;
	private PageMETA pageMETA;
	private PageHyperLink pageHyperLink;
	private String URL;
	private String contents;
	
	public PageModel(String URL, String contents){
		this.URL = URL;
		this.contents = contents;
		pageLINK = new PageLINK();
		pageMETA = new PageMETA();
		pageHyperLink = new PageHyperLink();
	}
	
	public static void main(String[] args) {
	}
	
}
