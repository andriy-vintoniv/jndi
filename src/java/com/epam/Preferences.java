package com.epam;

public class Preferences implements java.io.Serializable {

	String styleSheetURL = "Test value of styleSheetURL";

	public Preferences() {
	}

	public void setStyles(String styleSheetURL) {
		this.styleSheetURL = styleSheetURL;
	}
}