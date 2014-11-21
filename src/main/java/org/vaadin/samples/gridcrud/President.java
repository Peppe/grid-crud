package org.vaadin.samples.gridcrud;

import java.util.Date;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;

public class President {
	private String firstName;
	private String lastName;
	private Party party;
	private DateTime tookOffice;
	private DateTime leftOffice;
	
	public President() {
	}
	
	public President(String firstName, String lastName, Party party, DateTime tookOffice, DateTime leftOffice) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.party = party;
		this.tookOffice = tookOffice;
		this.leftOffice = leftOffice;
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

	public Party getParty() {
		return party;
	}

	public void setParty(Party party) {
		this.party = party;
	}


	public DateTime getTookOffice() {
		return tookOffice;
	}


	public void setTookOffice(DateTime tookOffice) {
		this.tookOffice = tookOffice;
	}


	public DateTime getLeftOffice() {
		return leftOffice;
	}


	public void setLeftOffice(DateTime leftOffice) {
		this.leftOffice = leftOffice;
	}
	
	@Override
	public String toString() {
		return "President " + lastName;
	}
}
