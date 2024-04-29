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

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentListener;

/**
 * Implements a panel that contains a text field with an accompanying label to display a prompt.
 */
public class InputPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private JLabel prompt = new JLabel();
	private JTextField textField = new JTextField(20);
	
	/**
	 * Constructor for an InputPanel.
	 * @param prompt The prompt to display in the input panel.
	 */
	public InputPanel(String prompt) {
		this(prompt, "");
	}
	
	/**
	 * Constructor for an InputPanel.
	 * @param prompt The prompt to display in the input panel.
	 * @param text The editable text to display in the input panel.
	 */
	public InputPanel(String prompt, String text) {
		this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		this.setBorder(new EmptyBorder(5,10,5,10));
		this.prompt.setText(prompt);
		this.add(this.prompt);
		this.add(Box.createRigidArea(new Dimension(5,0)));
		this.add(this.textField);
	}
	
	/**
	 * Gets the editable text from the input panel.
	 * @return The editable text of the input panel.
	 */
	public String getText() {
		return new String(this.textField.getText());
	}
	
	/**
	 * Sets the editable text of the input panel.0
	 * @param text The editable text to display in the input panel.
	 */
	public void setText(String text) {
		this.textField.setText(text);
	}
	
	public void addDocumentListener (DocumentListener listener) {
		this.textField.getDocument().addDocumentListener(listener);
	}
	
	/**
	 * Enables or disables the input panel.
	 */
	public void setEnabled(boolean enabled) {
		super.setEnabled(enabled);
		this.prompt.setEnabled(enabled);
		this.textField.setEnabled(enabled);
	}
	
}
