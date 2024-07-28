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

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 *
 */
public class PasswordDialog extends EscapeDialog implements DocumentListener {

	private static final long serialVersionUID = 1L;
	public boolean ok = false;
	public String password = "";
	private PasswordPanel passwordPanel = new PasswordPanel("Enter Password:"); 
	private ValidationPanel confirmPanel = new ValidationPanel("Confirm Password:", true); 
	
	public PasswordDialog(String filename, String password, boolean confirm) {
		super(Program.getMainFrame(), filename, true);
		this.ok = false;
		this.password = password;
		this.initialize(confirm);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}

	
	public void changedUpdate(DocumentEvent arg0) {
        validatePassword();
	}


	public void insertUpdate(DocumentEvent arg0) {
        validatePassword();
	}


	public void removeUpdate(DocumentEvent arg0) {
        validatePassword();
	}

	private void initialize(boolean confirm) {
		this.passwordPanel.setText(this.password);
		this.setLayout(new BorderLayout());
		this.setModalityType(ModalityType.APPLICATION_MODAL);
		this.add(this.createPanel(confirm));
		this.pack();
		this.setLocationRelativeTo(Program.getMainFrame());
	}

	private JPanel createPanel(boolean confirm) {
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		panel.setBorder(new EmptyBorder(10,10,10,10));
		panel.add(this.passwordPanel);
		if (confirm) {
			panel.add(Box.createRigidArea(new Dimension(0,5)));
			panel.add(this.confirmPanel);
			this.passwordPanel.addDocumentListener(this);
			this.confirmPanel.addDocumentListener(this);
		}
		panel.add(Box.createRigidArea(new Dimension(0,5)));
		panel.add(this.createShowPasswordCheckBox());
		panel.add(Box.createRigidArea(new Dimension(0,5)));
		panel.add(this.createButtonPanel());
		return panel;
	}

	private JCheckBox createShowPasswordCheckBox() {
		Properties properties = Program.getproperties();
		boolean showPassword = properties.getShowPassword();
		final JCheckBox checkBox = new JCheckBox("Show password", showPassword);
		checkBox.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
				boolean showPassword = checkBox.isSelected();
		    	updatePasswordDisplayState(showPassword);
				properties.setShowPassword(showPassword);
		    }
		});
		this.updatePasswordDisplayState(showPassword);
		return checkBox;
	}

	private JPanel createButtonPanel() {
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
		panel.add(new JLabel(""));
		JButton okButton = new JButton("OK");
		this.getRootPane().setDefaultButton(okButton);
		okButton.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		        ok = validatePassword();
		        if (ok) {
					password = passwordPanel.getText().toString();
		        	close();
		        }
		    }
		});
		JButton cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		        ok = false;
		        close();
		    }
		});
		panel.add(Box.createHorizontalGlue());
		panel.add(okButton);
		panel.add(Box.createRigidArea(new Dimension(10, 0)));
		panel.add(cancelButton);
		return panel;
	}
	
	private void updatePasswordDisplayState(boolean showPassword) {
		if (showPassword)
			this.passwordPanel.unmask();
		else
			this.passwordPanel.mask();
    	this.confirmPanel.setEnabled(!showPassword);
	}

	private boolean validatePassword() {
		boolean ok = true;
		if (this.confirmPanel.isEnabled()) {
			ok = passwordPanel.getText().equals(this.confirmPanel.getText());
			if (ok)
				confirmPanel.setValid();
			else
				confirmPanel.setInvalid();
		}
        return ok;
	}
	
}
