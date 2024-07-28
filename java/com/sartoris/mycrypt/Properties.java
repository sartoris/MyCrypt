package com.sartoris.mycrypt;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;

public class Properties extends java.util.Properties {

	private static final long serialVersionUID = 1L;
	private static final int defaultWindowX = 0;
	private static final int defaultWindowY = 0;
	private static final int defaultWindowWidth = 600;
	private static final int defaultWindowHeight = 400;

	private static class PropertyNames
	{
		static String ShowPassword = "ShowPassword";
		static String Filename = "Filename";
		static String WindowMaximized = "WindowMaximized";
		static String WindowX = "WindowX";
		static String WindowY = "WindowY";
		static String WindowWidth = "WindowWidth";
		static String WindowHeight = "WindowHeight";
	}
	
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

	void save() {
		try {
			this.store(new FileOutputStream("mycrypt.properties"), null);
		} catch (FileNotFoundException e) {
			System.err.println("Properties file not found");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	boolean getShowPassword() {
		return this.convert2Boolean(this.getProperty(PropertyNames.ShowPassword));
	}

	void setShowPassword(boolean hidden) {
		this.setProperty(PropertyNames.ShowPassword, Boolean.toString(hidden));
	}
	
	String getFilename() {
		return this.getProperty(PropertyNames.Filename);
	}

	void setFilename(String filename) {
		this.setProperty(PropertyNames.Filename, filename);
	}
	
	boolean getWindowMaximized() {
		return this.convert2Boolean(this.getProperty(PropertyNames.WindowMaximized));
	}

	void setWindowMaximized(boolean isMaximized) {
		this.setProperty(PropertyNames.WindowMaximized, Boolean.toString(isMaximized));
	}

	int getWindowX() {
		String windowProperty = this.getProperty(PropertyNames.WindowX);
		return windowProperty == null ? defaultWindowX : Integer.parseInt(windowProperty);
	}

	void setWindowX(int windowProperty) {
		this.setProperty(PropertyNames.WindowX, Integer.toString(windowProperty));
	}

	int getWindowY() {
		String windowProperty = this.getProperty(PropertyNames.WindowY);
		return windowProperty == null ? defaultWindowY : Integer.parseInt(windowProperty);
	}

	void setWindowY(int windowProperty) {
		this.setProperty(PropertyNames.WindowY, Integer.toString(windowProperty));
	}

	int getWindowWidth() {
		String windowProperty = this.getProperty(PropertyNames.WindowWidth);
		return windowProperty == null ? defaultWindowWidth : Integer.parseInt(windowProperty);
	}

	void setWindowWidth(int windowProperty) {
		this.setProperty(PropertyNames.WindowWidth, Integer.toString(windowProperty));
	}

	int getWindowHeight() {
		String windowProperty = this.getProperty(PropertyNames.WindowHeight);
		return windowProperty == null ? defaultWindowHeight : Integer.parseInt(windowProperty);
	}

	void setWindowHeight(int windowProperty) {
		this.setProperty(PropertyNames.WindowHeight, Integer.toString(windowProperty));
	}

	private boolean convert2Boolean(String value) {
		boolean result = false;
		if (value != null) {
			String valueLower = value.toLowerCase();
			if (valueLower.equals("true")
					|| valueLower.equals("yes")
					|| valueLower.equals("1"))
				result = true;
		}
		return result;
	}

}
