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

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;


/**
 *
 */
public class SearchDialog extends EscapeDialog {

	private static final long serialVersionUID = 1L;
	private CryptSearcher crypt;
	private InputPanel input = new InputPanel("Search For:");
	private JCheckBox caseSensitive = new JCheckBox("Case Sensitive");
	private JCheckBox wholeWord = new JCheckBox("Whole Word");
	private JCheckBox reverseSearch = new JCheckBox("Reverse Search");
	
	
	public SearchDialog(CryptSearcher crypt) {
		this.crypt = crypt;
		this.initComponents();
	}

	private void initComponents() {
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		panel.setBorder(new EmptyBorder(10,10,10,10));
		panel.add(this.input);
		panel.add(Box.createRigidArea(new Dimension(0,5)));
		panel.add(this.caseSensitive);
		panel.add(this.wholeWord);
		panel.add(this.reverseSearch);
		panel.add(Box.createRigidArea(new Dimension(0,5)));
		panel.add(this.createSearchButton());
		this.add(panel);
		this.pack();
	}

	private JComponent createSearchButton() {
		JButton searchButton = new JButton("Search");
		searchButton.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		    	SearchDialog.this.search();
		    }
		});
		return searchButton;
	}

	private void search() {
		this.crypt.search(this.input.getText(), this.caseSensitive.isSelected(), this.wholeWord.isSelected(), this.reverseSearch.isSelected());
	}
	

}
