package controller;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Execution;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.ForwardEvent;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Button;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Include;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Paging;
import org.zkoss.zul.Popup;
import org.zkoss.zul.Row;
import org.zkoss.zul.Rows;
import org.zkoss.zul.Textbox;

import dao.ContactDao;
import dao.ContactDaoimpl;
import model.Category;
import model.Contact;
import model.ContactCategory;
import model.ContactOffice;

public class ContactController extends SelectorComposer<Component> {

	private static final long serialVersionUID = 1L;
	@Wire
	private Combobox pageSizeCombobox;
	@Wire
	private Combobox pageSizeOffice;
	@Wire
	private Label idLabel;
	@Wire
	private Button finalSubmit;
	@Wire
	private Intbox contactIntId;
	@Wire
	private Label dateAddedLabel;
	@Wire
	private Label addedByLabel;
	@Wire
	public Popup mypopup;
	@Wire
	public Popup mpopup;
	@Wire
	public Popup deleteOfficePooup;
	@Wire
	private Combobox statusCombobox;
	@Wire
	private Textbox companyNameTextbox;
	@Wire
	private Textbox shortNameTextbox;
	private ContactDao contactDao = new ContactDaoimpl();
	@Wire
	private Grid categoriesGrid;
	@Wire
	private Label categoriesError;
	@Wire
	private Label shortNameError;
	@Wire
	private Label companyNameError;
	@Wire
	private Label statusError;
	@Wire
	private Grid officeGrid;
	Contact contact;
	@Wire
	private Include officePageInclude;
	@Wire
	Paging paging;
	@Wire
	Paging paging1;
	@Wire
	Paging paging2;
	@Wire
	private Grid contactGrid;
	Include subPageInclude;
	Execution executions = Executions.getCurrent();
	public static int contactId;
	public static ContactOffice contactOffices;
	OfficeController officeController;
	int currentPage = 0;
	int pageSize = 10;
	int currentPage1 = 0;
	int pageSize1 = 5;
	int currentPage2 = 0;
	int pageSize2 = 5;

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		Session session = Sessions.getCurrent();
		session.setAttribute("mypopup", mypopup);
		session.setAttribute("myInteger", -1);
		session.setAttribute("int", 0);
		officeController = (OfficeController) Executions.getCurrent().getAttributes().get("officeController");
		subPageInclude = (Include) executions.getArg().get("subPageInclude");
		if (comp.getId().equals("contactWindow")) {
			paging1.setVisible(false);
			paging2.setVisible(true);
			Session sessions = Sessions.getCurrent();
			contact = (Contact) (sessions).getAttribute("customer");
			loadCategoriesData();
			statusCombobox.setSelectedIndex(0);
			pageSizeOffice.setSelectedItem(pageSizeOffice.getItems().get(0));
			if (contact != null && contact.getId() > 0) {
				paging2.setVisible(false);
				paging1.setVisible(true);
				addedByLabel.setValue(contactDao.getUsernameByContactId(contact.getId()));
				Timestamp timestamp = contact.getCreatedOn();
				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
				String dateString = dateFormat.format(timestamp);
				dateAddedLabel.setValue(dateString);
				contactIntId.setValue(contact.getId());
				shortNameTextbox.setValue(contact.getShortName());
				idLabel.setValue("" + contact.getId());
				companyNameTextbox.setValue(contact.getCompanyName());
				contactId = contact.getId();
				loadContactOffice(contact.getId(), null);
				if (contact != null && contact.getId() > 0) {
					session.setAttribute("myInteger", contact.getId());
				}
				loadCategoriesDataa();
				statusCombobox.setValue(contact.getStatus());
				contact.setId(0);
			} else {
				contactId = 0;
			}
		} else if (comp.getId().equals("loginWindow")) {
			pageSizeCombobox.setSelectedItem(pageSizeCombobox.getItems().get(0));
			loadContactsData();
		}
	}

	private void loadContactsData() {
		List<Contact> contacts = contactDao.getAllContacts();
		int startIndex = currentPage * pageSize;
		int endIndex = Math.min(startIndex + pageSize, contacts.size());
		List<Contact> contacts1 = contacts.subList(startIndex, endIndex);
		if (contactGrid != null) {
			contactGrid.setModel(new ListModelList<>(contacts1));
			updatePagingInfo();
		} 
	}

	public void loadContactOffice(int id, List<ContactOffice> contactOffices2) {
		if (id == 0) {
			List<ContactOffice> contactOffices = contactOffices2;
			 Collections.sort(contactOffices, Comparator.comparingInt(ContactOffice::getId).reversed());
			int startIndex = currentPage2 * pageSize2;
			int endIndex = Math.min(startIndex + pageSize2, contactOffices.size());
			List<ContactOffice> contactOffice = contactOffices.subList(startIndex, endIndex);
			officeGrid.setModel(new ListModelList<>(contactOffice));
			updatePagingInfo2(id, contactOffices);
		} else {
			List<ContactOffice> contactOffice = contactDao.getCompanyContactOffice(id);
			int startIndex = currentPage1 * pageSize1;
			int endIndex = Math.min(startIndex + pageSize1, contactOffice.size());
			List<ContactOffice> contactOffice1 = contactOffice.subList(startIndex, endIndex);
			officeGrid.setModel(new ListModelList<>(contactOffice1));
			updatePagingInfo1(id, contactOffice);
		}
	}

	private void updatePagingInfo1(int id, List<ContactOffice> contactOffices2) {
		int totalSize = contactDao.getCompanyContactOffice(id).size();
		paging1.setTotalSize(totalSize);
		paging1.setPageSize(pageSize1);
		paging1.setActivePage(currentPage1);
	}

	private void updatePagingInfo2(int id, List<ContactOffice> contactOffices2) {
		int totalSize = contactOffices2.size();
		paging2.setTotalSize(totalSize);
		paging2.setPageSize(pageSize2);
		paging2.setActivePage(currentPage2);
	}

	private void updatePagingInfo() {
		int totalSize = contactDao.getAllContacts().size();
		paging.setTotalSize(totalSize);
		paging.setPageSize(pageSize);
		paging.setActivePage(currentPage);
	}

	@Listen("onChange = #pageSizeCombobox")
	public void changePageSize() {
		Comboitem selectedItem = pageSizeCombobox.getSelectedItem();
		if (selectedItem != null) {
			pageSize = Integer.parseInt(selectedItem.getValue());
			currentPage = 0;
			loadContactsData();
		}
	}

	@Listen("onChange = #pageSizeOffice")
	public void changePageSizeOffice() {
		Comboitem selectedItem = pageSizeOffice.getSelectedItem();
		if (selectedItem != null) {
			if (contactId == 0) {
				pageSize2 = Integer.parseInt(selectedItem.getValue());
				currentPage2 = 0;
				loadContactOffice(contactId, officeController.temporaryOfficeList);
			} else {
				pageSize1 = Integer.parseInt(selectedItem.getValue());
				currentPage1 = 0;
				loadContactOffice(contactId, contactDao.getCompanyContactOffice(contactId));
			}
		}
	}

	@Listen("onChange = #shortNameTextbox")
	public void validateShortName() {
	    String shortName = shortNameTextbox.getValue();
	    // Convert all characters to uppercase
	    String cleanedInput = shortName.toUpperCase();
	    // Remove any non-uppercase letters
	    cleanedInput = cleanedInput.replaceAll("[^A-Z]", "");
	    shortNameTextbox.setValue(cleanedInput);
	}

	@Listen("onPaging = #paging")
	public void onPaging() {
		currentPage = paging.getActivePage();
		loadContactsData();
	}

	@Listen("onPaging = #paging1")
	public void onPagingOffice() {
		currentPage1 = paging1.getActivePage();
		loadContactOffice(contactId, null);
	}

	@Listen("onPaging = #paging2")
	public void onPagingOffice2() {
		currentPage2 = paging2.getActivePage();
		List<ContactOffice> contactOffices = officeController.temporaryOfficeList;
		loadContactOffice(0, contactOffices);
	}

	@Listen("onClick = #officeForm")
	public void officeForm() {
		officePageInclude.setSrc("office.zul");
	}

	@Listen("onChange = #shortNameTextbox")
	public void validShortName() {
		Contact contact = new Contact();
		contact.setId(contactIntId.getValue());
		contact.setShortName(shortNameTextbox.getValue());
		int result = contactDao.checkShortnameExist(contact);

		if (!shortNameTextbox.getValue().isBlank() && !shortNameTextbox.getValue().isEmpty() && result > 0) {
			shortNameError.setValue("Short name already exists.");
			shortNameError.setVisible(true);

		} else {
			shortNameError.setVisible(false);
		}
	}

	@Listen("onChange = #shortNameTextbox, #companyNameTextbox,#statusCombobox, #categoriesGrid")
	public void validfilds() {
		validContactSection();
	}

	@Listen("onChange = #categoriesGrid")
	public void validCheckBox() {
		validContactSection();
	}

	@Listen("onChange = #categoriesGrid")
	public void validCheckBox1() {
		validContactSection();
	}

	@Listen("onCheck = #categoriesGrid checkbox")
	public void onCategoryChecked(Event event) {
		validContactSection();
	}

	public boolean validCompanyName() {
		String companyName = companyNameTextbox.getValue();
		if (companyName.isBlank() || companyName.isEmpty()) {
			return false;
		}
		return true;
	}

	public boolean validStatus() {
		String status = statusCombobox.getValue();
		if (status.isBlank() || status.isEmpty()) {
			return false;
		}
		return true;
	}

	public boolean validOffice() {
		Rows rowsComponent = officeGrid.getRows();
		if (rowsComponent == null) {
			return false;
		}
		List<Component> rows = officeGrid.getRows().getChildren();
		for (Component row : rows) {
			if (row instanceof Row) {
				return true;
			}
		}
		return false;
	}

	public boolean validShortName1() {
		String shortName = shortNameTextbox.getValue();
		if (shortName.isBlank() || shortName.isEmpty()) {
			return false;
		}
		return true;
	}

	public boolean validContactSection() {
		Contact contact = new Contact();
		contact.setId(contactIntId.getValue());
		contact.setShortName(shortNameTextbox.getValue());
		int result = contactDao.checkShortnameExist(contact);
		boolean category = isAnyCategorySelected();
		boolean shortname = validShortName1();
		boolean status = validStatus();
		boolean companyName = validCompanyName();
		boolean office = validOffice();
		if (shortname && status && companyName && category && result != 1 && office) {
			finalSubmit.setVisible(true);
			return true;
		}
		finalSubmit.setVisible(false);
		return false;
	}

	private boolean isAnyCategorySelected() {
		for (Component row : categoriesGrid.getRows().getChildren()) {
			if (row instanceof Row) {
				for (Component child : row.getChildren()) {
					if (child instanceof Checkbox) {
						Checkbox checkbox = (Checkbox) child;
						if (checkbox.isChecked()) {
							return true;
						}
					}
				}
			}
		}
		return false;
	}

	private void loadCategoriesDataa() {
		List<Category> categories = contactDao.getAllCategories();
		List<ContactCategory> contactCategories = contactDao.getContactCategory(contact.getId());

		for (Category category : categories) {
			for (ContactCategory contactCategory : contactCategories) {
				if (category.getId() == contactCategory.getCategoryId()) {
					category.setSelected(true);
					break;
				}
			}
		}
		categoriesGrid.setModel(new ListModelList<>(categories));
	}

	@Listen("onEditButtonClick=#loginWindow")
	public void onEditButtonClick(Event event) {
		ForwardEvent forwardEvent = (ForwardEvent) event;
		Component target = forwardEvent.getOrigin().getTarget();
		Row contactRow = (Row) target.getParent().getParent();
		Contact contact = (Contact) contactRow.getValue();
		if (contact != null) {
			Session sess = Sessions.getCurrent();
			sess.setAttribute("customer", contact);
			subPageInclude.setSrc("contact.zul");
		} else {
			System.out.println("Contact details not found.");
		}
	}

	private Contact selectedContact;

	@Listen("onDeleteButtonClick=#loginWindow")
	public void onDeleteButtonClick(Event event) {
		ForwardEvent forwardEvent = (ForwardEvent) event;
		Component target = forwardEvent.getOrigin().getTarget();
		Row contactRow = (Row) target.getParent().getParent();
		Contact contact = (Contact) contactRow.getValue();
		selectedContact = contact;
		mpopup.open(800, 150);
	}

	@Listen("onClick = #confirmDelete")
	public void confirmDelete(Event event) {
		mpopup.close();
		contactDao.deletecontact(selectedContact.getId());
		loadContactsData();
	}

	@Listen("onClick = #cancelDelete")
	public void cancelDelete(Event event) {
		mpopup.close();
	}

	@Listen("onClick = #addOffice")
	public void addOffice(Event event) {
		officePageInclude.setSrc("");
		officePageInclude.setDynamicProperty("contactController", this);
		officePageInclude.setSrc("office.zul");
		OfficeController officeController = (OfficeController) Executions.getCurrent().getAttribute("officeController");
		officeController.clearFields();
		mypopup.open(700, 150);
	}

	@Listen("onDelete = #contactWindow")
	public void onDelete(Event event) {
		ForwardEvent forwardEvent = (ForwardEvent) event;
		Component target = forwardEvent.getOrigin().getTarget();
		Row contactRow = (Row) target.getParent().getParent();
		ContactOffice office1 = (ContactOffice) contactRow.getValue();
		Session session = Sessions.getCurrent();
		session.setAttribute("ContactOffice", office1);
		deleteOfficePooup.open(800, 150);
	}

	@Listen("onClick = #deleteoffice")
	public void deleteoffice(Event event) {
		Session session = Sessions.getCurrent();
		ContactOffice office1 = (ContactOffice) session.getAttribute("ContactOffice");
		if (office1.getTempId() > 0) {
			List<ContactOffice> contactOffices = officeController.temporaryOfficeList;
			Iterator<ContactOffice> iterator = contactOffices.iterator();
			while (iterator.hasNext()) {
				ContactOffice tempOffice = iterator.next();
				if (tempOffice.getTempId() == office1.getTempId()) {
					iterator.remove();
				}
			}
			loadContactOffice(0, contactOffices);
		} else {
			contactDao.deleteOffice(office1.getId());
			loadContactOffice(contactId, null);
		}
		deleteOfficePooup.close();
		validContactSection();
	}

	@Listen("onClick = #canceldeleteOffice")
	public void canceldeleteOffice(Event event) {
		deleteOfficePooup.close();
	}

	@Listen("onUpdate = #contactWindow")
	public void onUpdate(ForwardEvent event) {
		ContactOffice office = (ContactOffice) event.getData();
		if (office != null) {
			Sessions.getCurrent().setAttribute("contactOffices", office);
		}
		officePageInclude.setDynamicProperty("contactController", this);
		officePageInclude.setSrc("");
		officePageInclude.setSrc("office.zul");
		mypopup.open(700, 150);
	}

	private void loadCategoriesData() {
		List<Category> categories = contactDao.getAllCategories();
		categoriesGrid.setModel(new ListModelList<>(categories));
	}

	@Listen("onClick = #finalSubmit")
	public void saveContact() {
		if (!validContactSection()) {
			return;
		}
		Session sessions = Sessions.getCurrent();
		sessions.removeAttribute("customer");
		int loggedUserId = (Integer) sessions.getAttribute("loggedUserId");
		String status = statusCombobox.getSelectedItem() != null ? statusCombobox.getSelectedItem().getLabel() : "";
		String companyName = companyNameTextbox.getValue();
		String shortName = shortNameTextbox.getValue();
		Contact contact = new Contact();
		contact.setStatus(status);
		contact.setCompanyName(companyName);
		contact.setShortName(shortName);
		contact.setCreatedBy(loggedUserId);
		long currentTimeMillis = System.currentTimeMillis();
		Timestamp currentTimestamp = new Timestamp(currentTimeMillis);
		contact.setCreatedOn(currentTimestamp);
		if (contactId == 0) {
			int generatedId = contactDao.insertContact(contact);
			if (generatedId != 0) {
				for (Component component : categoriesGrid.getRows().getChildren()) {
					if (component instanceof Row) {
						Row row = (Row) component;
						List<Component> children = row.getChildren();
						if (children.size() >= 2) { // Ensure the row has at least two children
							Component firstChild = children.get(0); // Checkbox is now the first child
							if (firstChild instanceof Checkbox) {
								Checkbox checkbox = (Checkbox) firstChild;
								if (checkbox.isChecked()) {
									Category category = (Category) checkbox.getValue();
									ContactCategory contactCategory = new ContactCategory();
									contactCategory.setContactId(generatedId);
									contactCategory.setCategoryId(category.getId());
									contactDao.insertContactCategory(contactCategory);
								}
							}
						}
					}
				}

			}
			if (generatedId != 0) {
				for (ContactOffice contactOffice : OfficeController.temporaryOfficeList) {
					contactOffice.setContactId(generatedId);
					contactDao.insertContactOffice(contactOffice);
				}
				OfficeController.temporaryOfficeList.clear();
			}
		} else {
			contact.setUpdatedBy(loggedUserId);
			contact.setUpdatedOn(currentTimestamp);
			contact.setId(contactId);
			contactDao.updateContact(contact);
			contactDao.deletecategory(contact.getId());
			for (Component component : categoriesGrid.getRows().getChildren()) {
				if (component instanceof Row) {
					Row row = (Row) component;
					List<Component> children = row.getChildren();
					if (children.size() >= 2) { 
						Component firstChild = children.get(0); 
						if (firstChild instanceof Checkbox) {
							Checkbox checkbox = (Checkbox) firstChild;
							if (checkbox.isChecked()) {
								Category category = (Category) checkbox.getValue();
								ContactCategory contactCategory = new ContactCategory();
								contactCategory.setContactId(contact.getId());
								contactCategory.setCategoryId(category.getId());
								contactDao.insertContactCategory(contactCategory);
							}
						}
					}
				}
			}
		}
		subPageInclude.setSrc("contactlist.zul");
		clearFields();
	}

	public void clearFields() {
		if (officeGrid != null) {
			officeGrid.getChildren().clear();
		}
		statusCombobox.setSelectedIndex(0);
		idLabel.setValue(null);
		companyNameTextbox.setValue("");
		shortNameTextbox.setValue("");
		List<Component> rows = categoriesGrid.getRows().getChildren();
		for (Component row : rows) {
			if (row instanceof Row) {
				Row r = (Row) row;
				for (Component child : r.getChildren()) {
					if (child instanceof Checkbox) {
						((Checkbox) child).setChecked(false);
					}
				}
			}
		}
	}

}