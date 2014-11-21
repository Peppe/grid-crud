package org.vaadin.samples.gridcrud;

public enum Party {
	DEMOCRATIC("Democratic"),
	REPUBLICAN("Republican"),
	DEMOCRATIC_REPUBLICAN("Democratic-Republican"),
	FEDERALIST("Federalist"),
	WHIG("Whig"),
	INDEPENDENT("Independent");
	
	String name; 
	private Party(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
}
