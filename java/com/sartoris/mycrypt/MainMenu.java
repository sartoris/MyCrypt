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

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;

/**
 * Implements the program menu with File, Search, and Help menu items.
 */
public class MainMenu extends JMenuBar {
	
	private static final long serialVersionUID = 1L;
	private Crypt crypt;

	MainMenu(Crypt crypt){
		super();
		this.crypt = crypt;
		this.initialize();
	}

	private void initialize() {
		this.add(this.CreateFileMenu());
		this.add(this.CreateSearchMenu());
		this.add(this.CreateHelpMenu());
	}

	private JMenu CreateFileMenu () {
		JMenu menu = new JMenu("File");
		menu.setMnemonic(KeyEvent.VK_F);
		menu.add(this.CreateFileNew());
		menu.add(this.CreateFileOpen());
		menu.add(this.CreateFileSave());
		menu.add(this.CreateFileSaveAs());
		menu.add(this.CreateFileExit());
		return menu;
	}
	
	private JMenuItem CreateFileNew() {
		JMenuItem menuItem = new JMenuItem("New");
		menuItem.setMnemonic(KeyEvent.VK_N);
		menuItem.getAccessibleContext().setAccessibleDescription("Create a new file");
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, ActionEvent.CTRL_MASK));
		menuItem.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		        crypt.clear();
		    }
		});
		return menuItem;
	}

	private JMenuItem CreateFileOpen() {
		JMenuItem menuItem = new JMenuItem("Open");
		menuItem.setMnemonic(KeyEvent.VK_O);
		menuItem.getAccessibleContext().setAccessibleDescription("Open an existing file");
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, ActionEvent.CTRL_MASK));
		menuItem.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		        crypt.open();
		    }
		});
		return menuItem;
	}

	private JMenuItem CreateFileSave() {
		JMenuItem menuItem = new JMenuItem("Save");
		menuItem.setMnemonic(KeyEvent.VK_S);
		menuItem.getAccessibleContext().setAccessibleDescription("Save the current file");
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK));
		menuItem.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		        crypt.save();
		    }
		});
		return menuItem;
	}

	private JMenuItem CreateFileSaveAs() {
		JMenuItem menuItem = new JMenuItem("Save As");
		menuItem.setMnemonic(KeyEvent.VK_A);
		menuItem.getAccessibleContext().setAccessibleDescription("Save the the current file with a new name");
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, ActionEvent.CTRL_MASK));
		menuItem.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		        crypt.saveAs();
		    }
		});
		return menuItem;
	}

	private JMenuItem CreateFileExit() {
		JMenuItem menuItem = new JMenuItem("Exit");
		menuItem.setMnemonic(KeyEvent.VK_X);
		menuItem.getAccessibleContext().setAccessibleDescription("Exit the program");
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, ActionEvent.CTRL_MASK));
		menuItem.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		        Program.close();
		    }
		});
		return menuItem;
	}

	private JMenu CreateSearchMenu () {
		JMenu menu = new JMenu("Search");
		JMenuItem search = new JMenuItem("Options");
		search.setMnemonic(KeyEvent.VK_S);
		search.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F, ActionEvent.CTRL_MASK));
		search.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		    	crypt.search();
		    }
		});
		menu.add(search);
		JMenuItem searchAgain = new JMenuItem("Again");
		searchAgain.setMnemonic(KeyEvent.VK_F3);
		searchAgain.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F3, 0));
		searchAgain.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		    	crypt.searchAgain();
		    }
		});
		menu.add(searchAgain);
		return menu;
	}
	
	private JMenu CreateHelpMenu () {
		JMenu menu = new JMenu("Help");
		menu.setMnemonic(KeyEvent.VK_H);
		JMenuItem menuItem = new JMenuItem("About");
		menuItem.setMnemonic(KeyEvent.VK_A);
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F1, 0));
		menuItem.getAccessibleContext().setAccessibleDescription("Display program info");
		menuItem.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		    	String message = String.format("%1s version %2s", Program.Name, Program.Version);
		    	JOptionPane.showMessageDialog(Program.getMainFrame(), message, "About MyCrypt", JOptionPane.INFORMATION_MESSAGE, Program.getIcon());
		    }
		});
		menu.add(menuItem);
		return menu;
	}

}
