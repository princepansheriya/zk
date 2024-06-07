package model;

public class Category {
	
	private int id;
	private String name;
	private boolean selected;

	// Default constructor
	public Category() {
	}

	// Parameterized constructor
	public Category(int id, String name) {
		this.id = id;
		this.name = name;
	}

	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}

	// Getter for id
	public int getId() {
		return id;
	}

	// Setter for id
	public void setId(int id) {
		this.id = id;
	}

	// Getter for name
	public String getName() {
		return name;
	}

	// Setter for name
	public void setName(String name) {
		this.name = name;
	}
	
}
