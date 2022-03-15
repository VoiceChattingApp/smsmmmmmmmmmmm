package com.chat.app.entity;

public class Contact {
	private String username;
	private String firstName;
	private String lastName;
	private int unread;

	public Contact() {
	}

	public Contact(String username, String firstName, String lastName, int unread) {
		this.username = username;
		this.firstName = firstName;
		this.lastName = lastName;
		this.unread = unread;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public int getUnread() {
		return unread;
	}

	public void setUnread(int unread) {
		this.unread = unread;
	}

}
