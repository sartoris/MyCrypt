/**
 * Copyright (c) 2014, Sartoris Software
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice, this
 *    list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR
 * ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package com.sartoris.mycrypt;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 * The main startup class.
 */
public class Program {

	public static String Name = "MyCrypt";
	public static String Version = "0.3.0";
	private static String newLine = System.lineSeparator();
	private static JFrame mainFrame;
	private static Properties properties = new Properties();
	private static ImageIcon icon =  Program.createImageIcon("/images/crypt.png", "The Crypt");

	/**
	 * The main startup method for the program.
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println(System.getProperty("user.dir"));
		if (args.length > 0) {
			if (args[0].toLowerCase().startsWith("-r")) {
				System.out.println(CryptKeeper.getRandomString());
			} else if (args[0].toLowerCase().startsWith("-p")) {
				CryptKeeper.listProviders();
			} else if (args[0].toLowerCase().startsWith("-v")) {
				System.out.println(Program.Name + " version " + Program.Version);
			} else {
				Program.startViewer(args[0]);
			}
		} else {
			String filename = properties.getFilename();
			Program.startViewer(filename);
		}
	}

	private static void startViewer(String filename) {
		CryptViewer viewer = new CryptViewer(filename);
		Program.mainFrame = viewer;
		viewer.setVisible(true);
	}

	public static void close() {
		Program.properties.save();
		System.exit(0);
	}

	public static JFrame getMainFrame() {
		return Program.mainFrame;
	}

	public static Properties getproperties() {
		return Program.properties;
	}

	public static ImageIcon getIcon() {
		return Program.icon;
	}

	protected static ImageIcon createImageIcon(String path, String description) {
		java.net.URL imgURL = Program.class.getResource(path);
		if (imgURL != null) {
			return new ImageIcon(imgURL, description);
		} else {
			System.err.println("Couldn't find file: " + path);
			return null;
		}
	}

	public static void showErrMessage(Exception ex) {
		StringBuilder message = new StringBuilder();
		message.append(ex.getClass().getName());
		message.append(Program.newLine);
		message.append("Message: " + ex.getMessage());
		message.append(Program.newLine);
		message.append("Cause: " + ex.getCause());
		message.append(Program.newLine);
		int i = 0;
		for (StackTraceElement trace : ex.getStackTrace()) {
			message.append(trace.getClassName() + "." + trace.getMethodName() + " - source=");
			message.append(trace.getFileName() + ":" + trace.getLineNumber() + Program.newLine);
			if (++i > 100) break;
		}
		JOptionPane.showMessageDialog(Program.mainFrame, message.toString(), "Error", JOptionPane.ERROR_MESSAGE);
	}

	public static void showMessage(String message) {
		JOptionPane.showMessageDialog(Program.mainFrame, message, "Error", JOptionPane.ERROR_MESSAGE);
	}

	public static void output(byte[] array) {
		for (byte element : array) {
			System.out.print(element + " ");
		}
		System.out.println();
	}

}
