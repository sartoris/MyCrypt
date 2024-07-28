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

import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

/**
 *
 */
public class ValidationIcon extends JLabel {

	private static final long serialVersionUID = 1L;
	private static final String iconPackage = "/images/";
	private static final String blankIconFile = "Blank.png";
	private static final String validIconFile = "GreenCheck.png";
	private static final String invalidIconFile = "RedX.png";
	private String iconFile = invalidIconFile;

	public void setEnabled(boolean enabled) {
		if (enabled) {
			this.displayIcon(this.iconFile);
		} else {
			this.displayIcon(blankIconFile);
		}
	}
	
	public void setValid() {
		this.setIcon(validIconFile);
	}
	
	public void setInvalid() {
		this.setIcon(invalidIconFile);
	}
	
	private void setIcon(String iconFile) {
		if (iconFile == null
			|| iconFile == "") {
			this.iconFile = blankIconFile;
		} else {
			this.iconFile = iconFile;
		}
		this.displayIcon(this.iconFile);
	}

	private void displayIcon(String iconFile) {
		ImageIcon icon = null;
		URL imgURL = getClass().getResource(iconPackage + iconFile);
		if (imgURL != null) {
			icon = new ImageIcon(imgURL);
		} else {
			System.err.println("Couldn't find file: " + iconFile);
		}
		super.setIcon(icon);
	}
	
	
	
}
