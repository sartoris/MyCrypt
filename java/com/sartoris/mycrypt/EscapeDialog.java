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

import java.awt.Dialog;
import java.awt.Frame;
import java.awt.GraphicsConfiguration;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JRootPane;
import javax.swing.KeyStroke;

/**
 * Extends a JDialog that closes the dialog when the user presses the ESC key
 */
public class EscapeDialog extends JDialog {

	private static final long serialVersionUID = 1L;

	public EscapeDialog() {
	}

	/**
	 * @param owner
	 */
	public EscapeDialog(Frame owner) {
		super(owner);
	}

	/**
	 * @param owner
	 */
	public EscapeDialog(Dialog owner) {
		super(owner);
	}

	/**
	 * @param owner
	 */
	public EscapeDialog(Window owner) {
		super(owner);
	}

	/**
	 * @param owner
	 * @param modal
	 */
	public EscapeDialog(Frame owner, boolean modal) {
		super(owner, modal);
	}

	/**
	 * @param owner
	 * @param title
	 */
	public EscapeDialog(Frame owner, String title) {
		super(owner, title);
	}

	/**
	 * @param owner
	 * @param modal
	 */
	public EscapeDialog(Dialog owner, boolean modal) {
		super(owner, modal);
	}

	/**
	 * @param owner
	 * @param title
	 */
	public EscapeDialog(Dialog owner, String title) {
		super(owner, title);
	}

	/**
	 * @param owner
	 * @param modalityType
	 */
	public EscapeDialog(Window owner, ModalityType modalityType) {
		super(owner, modalityType);
	}

	/**
	 * @param owner
	 * @param title
	 */
	public EscapeDialog(Window owner, String title) {
		super(owner, title);
	}

	/**
	 * @param owner
	 * @param title
	 * @param modal
	 */
	public EscapeDialog(Frame owner, String title, boolean modal) {
		super(owner, title, modal);
	}

	/**
	 * @param owner
	 * @param title
	 * @param modal
	 */
	public EscapeDialog(Dialog owner, String title, boolean modal) {
		super(owner, title, modal);
	}

	/**
	 * @param owner
	 * @param title
	 * @param modalityType
	 */
	public EscapeDialog(Window owner, String title, ModalityType modalityType) {
		super(owner, title, modalityType);
	}

	/**
	 * @param owner
	 * @param title
	 * @param modal
	 * @param gc
	 */
	public EscapeDialog(Frame owner, String title, boolean modal,
			GraphicsConfiguration gc) {
		super(owner, title, modal, gc);
	}

	/**
	 * @param owner
	 * @param title
	 * @param modal
	 * @param gc
	 */
	public EscapeDialog(Dialog owner, String title, boolean modal,
			GraphicsConfiguration gc) {
		super(owner, title, modal, gc);
	}

	/**
	 * @param owner
	 * @param title
	 * @param modalityType
	 * @param gc
	 */
	public EscapeDialog(Window owner, String title, ModalityType modalityType,
			GraphicsConfiguration gc) {
		super(owner, title, modalityType, gc);
	}

	protected void close() {
		this.setVisible(false);
	}

	protected JRootPane createRootPane() {
		ActionListener actionListener = new ActionListener() {
			public void actionPerformed(ActionEvent actionEvent) {
				close();
			}
		};
		KeyStroke stroke = KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0);
		JRootPane rootPane = new JRootPane();
		rootPane.registerKeyboardAction(actionListener, stroke, JComponent.WHEN_IN_FOCUSED_WINDOW);
		return rootPane;
	}
		
}
