package com.sartoris.mycrypt;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Properties extends java.util.Properties {

	private static final long serialVersionUID = 1L;
	
	Properties () {
		super();
		try {
			this.load(new FileReader("mycrypt.properties"));
		} catch (FileNotFoundException e) {
			System.err.println("Properties file not found");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	boolean getHidePassword() {
		return this.convert2Boolean(this.getProperty("hidePassword"));
	}
	
	String getFilename() {
		return this.getProperty("filename");
	}

	private boolean convert2Boolean(String key) {
		boolean value = false;
		if (key != null) {
			String keyLower = key.toLowerCase();
			if (keyLower.equals("true")
					|| keyLower.equals("yes")
					|| keyLower.equals("1"))
				value = true;
		}
		return value;
	}

}
