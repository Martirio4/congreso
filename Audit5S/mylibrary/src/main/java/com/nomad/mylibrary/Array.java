//
//  Android PDF Writer
//  http://coderesearchlabs.com/androidpdfwriter
//
//  by Javier Santo Domingo (j-a-s-d@coderesearchlabs.com)
//

package com.nomad.mylibrary;

public class Array extends EnclosedContent {
	
	public Array() {
		super();
		setBeginKeyword("[ ", false, false);
		setEndKeyword("]", false, false);
	}

	public void addItem(String s) {
		addContent(s);
		addSpace();
	}
	
	public void addItemsFromStringArray(String[] content) {
		for (String s: content) {
			addItem(s);
		}
	}
}
