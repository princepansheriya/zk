package model;

public class ContactCategory {

	private int contactId;
	private int categoryId;

	public ContactCategory() {
	}

	// Parameterized constructor
	public ContactCategory(int contactId, int categoryId) {
		this.contactId = contactId;
		this.categoryId = categoryId;
	}

	public int getContactId() {
		return contactId;
	}

	public void setContactId(int contactId) {
		this.contactId = contactId;
	}

	public int getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}

}