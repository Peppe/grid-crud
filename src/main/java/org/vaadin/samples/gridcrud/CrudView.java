package org.vaadin.samples.gridcrud;

import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import com.vaadin.annotations.Theme;
import com.vaadin.data.Item;
import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.data.util.converter.Converter;
import com.vaadin.shared.ui.grid.HeightMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.components.grid.AbstractRenderer;
import com.vaadin.ui.components.grid.Grid;
import com.vaadin.ui.components.grid.Grid.SelectionMode;
import com.vaadin.ui.components.grid.selection.SelectionChangeEvent;
import com.vaadin.ui.components.grid.selection.SelectionChangeListener;
import com.vaadin.ui.themes.ValoTheme;

import elemental.json.JsonValue;

@Theme("reindeer")
public class CrudView extends VerticalLayout {
	DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd");
	CrudPresenter presenter = new CrudPresenter(this);
	private Grid grid;
	private BeanFieldGroup<President> fieldGroup = new BeanFieldGroup<President>(
			President.class);
	private TextField firstName;
	private TextField lastName;
	private ComboBox party;
	private TextField tookOffice;
	private TextField leftOffice;

	public CrudView() {
		HorizontalLayout headerRow = createHeader();
		VerticalLayout gridLayout = createGrid();
		VerticalLayout form = createForm();
		HorizontalLayout buttonLayout = createButtonLayout();

		addComponent(headerRow);
		addComponent(gridLayout);
		addComponent(form);
		addComponent(buttonLayout);
		setExpandRatio(gridLayout, 1);
		setSizeFull();
		presenter.init();
	}

	private HorizontalLayout createHeader() {
		HorizontalLayout headerRow = new HorizontalLayout();
		Label header = new Label("POTUS Database");
		header.addStyleName(ValoTheme.LABEL_H1);
		Button addNewButton = new Button("Add new");
		headerRow.addComponent(header);
		headerRow.addComponent(addNewButton);
		headerRow.setExpandRatio(header, 1);
		headerRow.setWidth("100%");
		headerRow.setComponentAlignment(header, Alignment.MIDDLE_LEFT);
		headerRow.setComponentAlignment(addNewButton, Alignment.MIDDLE_LEFT);
		return headerRow;
	}

	private VerticalLayout createForm() {
		VerticalLayout form = new VerticalLayout();
		firstName = new TextField("First Name");
		lastName = new TextField("Last Name");
		party = new ComboBox("Party");
		party.addContainerProperty("caption", String.class, null);
		party.setItemCaptionPropertyId("caption");
		for(Party aParty : Party.values()){
			Item item = party.addItem(aParty);
			item.getItemProperty("caption").setValue(aParty.getName());
		}
		tookOffice = new TextField("Took Office");
		leftOffice = new TextField("Left Office");
		
		firstName.setNullRepresentation("");
		lastName.setNullRepresentation("");
		tookOffice.setNullRepresentation("");
		leftOffice.setNullRepresentation("");
		Converter<String, DateTime> converter = new Converter<String, DateTime>() {

			@Override
			public DateTime convertToModel(String value,
					Class<? extends DateTime> targetType, Locale locale)
					throws com.vaadin.data.util.converter.Converter.ConversionException {
				if (value != null && !value.equals("")) {
					return new DateTime(value);
				}
				return null;
			}

			@Override
			public String convertToPresentation(DateTime value,
					Class<? extends String> targetType, Locale locale)
					throws com.vaadin.data.util.converter.Converter.ConversionException {
				if (value != null) {
					DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd");
					return value.toString(formatter);
				}
				return null;
			}

			@Override
			public Class<DateTime> getModelType() {
				return DateTime.class;
			}

			@Override
			public Class<String> getPresentationType() {
				return String.class;
			}

		};
		tookOffice.setConverter(converter);
		leftOffice.setConverter(converter);
		fieldGroup.bind(firstName, "firstName");
		fieldGroup.bind(lastName, "lastName");
		fieldGroup.bind(party, "party");
		fieldGroup.bind(tookOffice, "tookOffice");
		fieldGroup.bind(leftOffice, "leftOffice");
		HorizontalLayout nameRow = new HorizontalLayout(firstName, lastName);
		nameRow.setSpacing(true);
		HorizontalLayout dateRow = new HorizontalLayout(tookOffice, leftOffice);
		dateRow.setSpacing(true);
		form.addComponent(nameRow);
		form.addComponent(dateRow);
		form.addComponent(party);
		form.setMargin(true);
		return form;
	}
	
	private VerticalLayout createGrid(){
		VerticalLayout layout = new VerticalLayout();
		layout.setMargin(true);
		layout.setSizeFull();
		grid = new Grid();

		grid.setSelectionMode(SelectionMode.SINGLE);
		grid.addSelectionChangeListener(new SelectionChangeListener() {

			@Override
			public void selectionChange(SelectionChangeEvent event) {
				Object row = grid.getSelectedRow();
				President president = (President) row;
				System.out.println(president);
				if(president == null){
					president = new President();
				}
				fieldGroup.setItemDataSource(president);
			}
		});
		layout.addComponent(grid);
		return layout;
	}

	private HorizontalLayout createButtonLayout() {
		HorizontalLayout buttonLayout = new HorizontalLayout();
		buttonLayout.setWidth("100%");
		buttonLayout.setSpacing(true);
		buttonLayout.setMargin(true);
		Button saveButton = new Button("Save");
		Button revertButton = new Button("Revert");
		Button cancelButton = new Button("Delete");
		buttonLayout.addComponent(saveButton);
		buttonLayout.addComponent(revertButton);
		buttonLayout.addComponent(cancelButton);
		buttonLayout.setExpandRatio(revertButton, 1);
		return buttonLayout;
	}

	public void setPresidents(List<President> presidents) {
		IndexedContainer container = new IndexedContainer();
		container.addContainerProperty("firstName", String.class, null);
		container.addContainerProperty("lastName", String.class, null);
		container.addContainerProperty("party", String.class, null);
		container.addContainerProperty("tookOffice", Date.class, null);
		container.addContainerProperty("leftOffice", Date.class, null);

		for (President president : presidents) {
			Item item = container.addItem(president);
			item.getItemProperty("firstName")
					.setValue(president.getFirstName());
			item.getItemProperty("lastName").setValue(president.getLastName());
			item.getItemProperty("party").setValue(president.getParty().getName());
			item.getItemProperty("tookOffice").setValue(
					president.getTookOffice().toDate());
			Date leftOffice = null;
			if (president.getLeftOffice() != null) {
				leftOffice = president.getLeftOffice().toDate();
			}
			item.getItemProperty("leftOffice").setValue(leftOffice);
		}
		grid.setContainerDataSource(container);
	}
}
