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

/**
 *
 */
public class CryptSearcher {

	private Crypt crypt;
	private SearchDialog searchDialog;
	private String searchString = "";
	private boolean caseSensitive;
	private boolean wholeWord;
	private boolean reverse;
	private String text;

	/**
	 * @param crypt
	 */
	public CryptSearcher(Crypt crypt) {
		this.crypt = crypt;
		this.searchDialog = new SearchDialog(this);
	}

	void search()
	{
		this.searchDialog.setVisible(true);
	}

	void searchAgain() {
		if (this.searchString == "")
			this.searchDialog.setVisible(true);
		else
			this.execute();
	}

	/**
	 * @param text
	 * @param selected
	 * @param selected2
	 * @param selected3
	 */
	public void search(String searchString, boolean caseSensitive, boolean wholeWord, boolean reverse) {
		this.searchString = searchString;
		this.caseSensitive = caseSensitive;
		this.wholeWord = wholeWord;
		this.reverse = reverse;
		this.execute();
	}

	private void execute() {
		if (this.searchString != "") {
			if (this.caseSensitive) {
				this.text = this.crypt.getText();
			} else {
				this.text = this.crypt.getText().toLowerCase();
				this.searchString = this.searchString.toLowerCase();
			}
			int location = this.crypt.getCaratPosition();
			if (this.reverse) {
				for (int index = --location; index < this.text.length(); index--) {
					if (this.text.substring(index).startsWith(this.searchString)) {
						this.crypt.setCaratPosition(index);
						break;
					}
				}
			} else {
				for (int index = ++location; index < this.text.length(); index++) {
					if (this.text.substring(index).startsWith(this.searchString)) {
						this.crypt.setCaratPosition(index);
						break;
					}
				}
			}
		}
	}


}
