package org.vaadin.samples.gridcrud;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.joda.time.DateMidnight;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;

import com.vaadin.data.Item;

public class CrudPresenter {

	private CrudView view;
	List<President> presidents = new ArrayList<President>();

	public CrudPresenter(CrudView view) {
		this.view = view;
		
		presidents.add(new President("Jens", "Jansson", Party.INDEPENDENT, new DateTime("2010-04-28"), new DateTime("2010-05-30")));
		presidents.add(new President("Leif", "Åstrand", Party.DEMOCRATIC, new DateTime("2010-05-31"), new DateTime("2010-07-05")));
		presidents.add(new President("Artur", "Signell", Party.WHIG, new DateTime("2010-07-06"), new DateTime("2010-09-16")));
		
	}
	
	public void init(){
		view.setPresidents(presidents);
	}

}
