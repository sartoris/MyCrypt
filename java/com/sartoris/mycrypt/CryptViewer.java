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

import java.io.File;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;

/**
 * Displays the main form used for the program.
 */
class CryptViewer extends JFrame implements CryptListener {

	private static final long serialVersionUID = 1L;
	private Crypt crypt;
	private TextEditor editor;
	private String filename = "";

	CryptViewer(String filename){
		super(Program.Name);
		this.crypt = new Crypt(this);
		this.initialize();
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		if (filename != null) {
			File f = new File(filename);
			if(f.exists() && !f.isDirectory())
				this.crypt.openFile(filename);
		}
	}

	protected void processWindowEvent(WindowEvent e) {
		if(e.getID() == WindowEvent.WINDOW_CLOSING) {
			saveWindowProperties();
			Program.getproperties().save();
		}
		super.processWindowEvent(e);
	}

	private void initialize() {
		this.setIconImage(Program.getIcon().getImage());
		this.setMinimumSize(new Dimension(600,400));
		this.setLayout(new BorderLayout());
		this.setJMenuBar(new MainMenu(this.crypt));
		this.editor = new TextEditor();
		this.add(this.editor);
		this.pack();
		this.updateWindow();
	}

	public String getText(){
		return this.editor.getText();
	}

	/* (non-Javadoc)
	 * @see com.sartoris.mycrypt.CryptListener#filenameChanged(java.lang.String)
	 */
	public void updateFilename(String newValue) {
		this.filename = newValue;
		this.setTitle(String.format("%1s - %2s", Program.Name, this.filename));
		Program.getproperties().setFilename(newValue);
	}

	/* (non-Javadoc)
	 * @see com.sartoris.mycrypt.CryptListener#textChanged(java.util.List)
	 */
	public void updateText(String newValue) {
		this.editor.setText(newValue);
	}

	/* (non-Javadoc)
	 * @see com.sartoris.mycrypt.CryptListener#saving()
	 */
	public void updateCursorPosition(int newValue) {
		this.editor.setCaretPosition(newValue);
	}

	void updateWindow() {
		Properties properties = Program.getproperties();			
		this.setBounds(properties.getWindowX(),
			properties.getWindowY(),
			properties.getWindowWidth(),
			properties.getWindowHeight());
		if (properties.getWindowMaximized()) {
			this.setExtendedState(JFrame.MAXIMIZED_BOTH);
		}
	}

	void saveWindowProperties() {
		boolean isMaximized = this.getExtendedState() == JFrame.MAXIMIZED_BOTH;
		Properties properties = Program.getproperties();
		properties.setWindowMaximized(isMaximized);
		if (!isMaximized) {
			properties.setWindowX(this.getX());
			properties.setWindowY(this.getY());
			properties.setWindowWidth(this.getWidth());
			properties.setWindowHeight(this.getHeight());
		}
	}
		
}
