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
import java.awt.Color;
import javax.swing.BoxLayout;
import javax.swing.JEditorPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

/**
 *
 */
public class TextEditor extends JPanel {

	private static final long serialVersionUID = 1L;
	private JEditorPane editor;
	
	/**
	 * 
	 */
	public TextEditor() {
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.setBackground(Color.blue);
		this.editor = new JEditorPane() {
		    private static final long serialVersionUID = 1L;
			public boolean getScrollableTracksViewportWidth() {
		        return true;
		    }
		};
		JScrollPane scrollPane = new JScrollPane(this.editor);
        this.add(scrollPane, BorderLayout.CENTER);
    }

	public void setText(String text) {
		this.editor.setText(text);
		this.resetCaretPosition();
	}

	public String getText() {
		return this.editor.getText();
	}

	public void resetText() {
		this.editor.setText("");
	}

	public void setCaretPosition(int location) {
		this.editor.setCaretPosition(location);
	}

	public int getCaretPosition() {
		return this.editor.getCaretPosition();
	}

	public void resetCaretPosition() {
		this.editor.setCaretPosition(0);
	}

}
