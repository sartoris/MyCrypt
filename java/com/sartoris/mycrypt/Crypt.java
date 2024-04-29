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
import java.nio.file.Files;
//import java.nio.file.Paths;
import java.nio.file.Path;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * Defines an interface used to listen for changes to the Crypt
 */
interface CryptListener {
	/**
	 * Informs the listener that the crypt filename has changed
	 */
	void updateFilename(String newValue);
	/**
	 * Asks the listener for the crypt text
	 */
	String getText();
	/**
	 * Informs the listener that the crypt text has changed
	 */
	void updateText(String newValue);
	/**
	 * Informs the listener that the position of the cursor has changed
	 */
	void updateCursorPosition(int newValue);
}

/**
 * Model for a text file used to store encrypted text.
 * Includes file open (decrypted) and save (encrypted) methods.
 * Includes text search methods and tracks the cursor.
 */
class Crypt {
	final static JFileChooser fc = new JFileChooser(); // should this be in the MainMenu code?
	private CryptListener listener;
	private String filename = "";
	private String password;
	private String text;
	private int location =  0;
    private CryptSearcher searcher = new CryptSearcher(this);

	Crypt (CryptListener listener) {
		this.listener = listener;
		this.initFileChooser();
	}

    public String getText() {
    	return this.listener.getText();
    }

    public int getCaratPosition() {
    	return this.location;	// this needs to be updated from the gui
    }

	private void initFileChooser() {
        Crypt.fc.setCurrentDirectory(new File(System.getProperty("user.home")));
        Crypt.fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
        FileNameExtensionFilter defaultFilter = new FileNameExtensionFilter("MyCrypt data files (*.crypt)", "crypt");
        Crypt.fc.addChoosableFileFilter(defaultFilter);
        Crypt.fc.addChoosableFileFilter(new FileNameExtensionFilter("Text Files", "txt"));
        Crypt.fc.setFileFilter(defaultFilter);
        Crypt.fc.setAcceptAllFileFilterUsed(true);
	}

    private void setFilename(String filename) {
    	if (this.filename != filename) {
	    	this.filename = filename;
	        this.listener.updateFilename(filename);
    	}
    }

    private void setText(String text) {
    	if (this.text != text) {
	    	this.text = text;
	        this.listener.updateText(text);
		}
    }

    public void setCaratPosition(int location) {
    	if (this.location != location) {
	    	this.location = location;
	        this.listener.updateCursorPosition(location);
		}
    }

    void clear() {
    	this.password = "";
    	this.setFilename("");
    	this.setText("");
    }

    void open() {
		int returnVal = Crypt.fc.showOpenDialog(Program.getMainFrame());
	    if (returnVal == JFileChooser.APPROVE_OPTION) {
	    	String filename = fc.getSelectedFile().getPath();
    		this.openFile(filename);
	    }
	}

	void save() {
		if (this.filename == "")
			this.saveAs();
		else
			this.saveFile(this.filename);
	}

	void saveAs() {
		int returnVal = fc.showSaveDialog(Program.getMainFrame());
	    if (returnVal == JFileChooser.APPROVE_OPTION) {
	    	String filename = fc.getSelectedFile().getPath();
    		if (this.saveFile(filename)) {
				this.setFilename(filename);
		    }
	    }
	}

	boolean openFile(String filename)
	{
		boolean success = false;
		String decrypted = null;
		if (this.getPassword())
		{
			try
			{
				byte[] encrypted = Files.readAllBytes(Path.of(filename));
				decrypted = CryptKeeper.decrypt(encrypted, this.password);
				success = true;
			}
			catch (Exception ex)
			{
				decrypted = null;
				Program.showMessage("That password is no good!");
			}
		}
		this.setText(decrypted);
		this.setFilename(filename);
		return success;
	}

	private boolean saveFile(String filename)
	{
		boolean success = false;
		if (this.setPassword()) {
			try
			{
				byte[] encrypted = CryptKeeper.encrypt(this.getText(), this.password);
				Files.write(Path.of(filename), encrypted);
				success = true;
			}
			catch (Exception ex)
			{
				Program.showErrMessage(ex);
			}
		}
		return success;
	}

	private boolean getPassword() {
		return this.displayPasswordDialog(false);
	}

	private boolean setPassword() {
		return this.displayPasswordDialog(true);
	}

	private boolean displayPasswordDialog(boolean confirm)
	{
		PasswordDialog passwordDialog = new PasswordDialog(this.password, confirm);
		passwordDialog.setVisible(true);
		boolean ok = passwordDialog.ok;
		if (ok)
		{
			this.password = passwordDialog.password;
		}
		return ok;
	}

	void search() {
		this.searcher.search();
	}

	void searchAgain() {
		this.searcher.searchAgain();
	}

}
