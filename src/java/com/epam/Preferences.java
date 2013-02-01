package com.epam;

public class Preferences implements java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String styleSheetURL = null;

	public void setStyles(String styleSheetURL) {
		this.styleSheetURL = styleSheetURL;
	}
	// Other methods of the Preferences class

}