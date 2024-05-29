package controller;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Execution;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Button;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Popup;
import org.zkoss.zul.Textbox;

import dao.ContactDao;
import dao.ContactDaoimpl;
import model.ContactOffice;

public class OfficeController extends SelectorComposer<Component> {

	private static final long serialVersionUID = 1L;

	ContactDao contactDao = new ContactDaoimpl();
	public static List<ContactOffice> temporaryOfficeList = new ArrayList<>(); // ArrayList to hold temporary
	// ContactOffice objects
	@Wire
	private Button submit;
	@Wire
	private Textbox officeNameTextbox;
	@Wire
	private Intbox officeIntId;
	@Wire
	private Intbox tempIntId;
	@Wire
	private Textbox address1Textbox;
	@Wire
	private Textbox address2Textbox;
	@Wire
	private Combobox statusCombobox;
	@Wire
	private Textbox townCityTextbox;
	@Wire
	private Textbox postcodeTextbox;
	@Wire
	private Textbox countryTextbox;
	@Wire
	private Textbox telephone1Textbox;
	@Wire
	private Textbox telephone2Textbox;
	@Wire
	private Textbox faxTextbox;
	@Wire
	private Textbox mobileTextbox;
	@Wire
	private Textbox emailTextbox;
	@Wire
	private Label officeLabel;
	@Wire
	private Label emailError;
	@Wire
	private Label mobileError;
	@Wire
	private Label officeNameError;
	@Wire
	private Textbox urlTextbox;
	private Popup mypopup;
	ContactController contactController;

	ContactController contactControllers;
	@Wire
	private Grid officeGrid;
	Execution executions = Executions.getCurrent();

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		officeLabel.setValue("New office:");
		Session session = Sessions.getCurrent();
		mypopup = (Popup) session.getAttribute("mypopup");

		Execution execution = Executions.getCurrent();
		execution.setAttribute("officeController", this);
		statusCombobox.setSelectedIndex(0);
		contactController = (ContactController) executions.getArg().get("contactController");
		ContactOffice contactOffice = (ContactOffice) session.getAttribute("contactOffices");
		if (contactOffice != null) {
			officeLabel.setValue("Edit "+ contactOffice.getOfficeName()+" office:");
			officeIntId.setValue(contactOffice.getId());
			tempIntId.setValue(contactOffice.getTempId());
			officeNameTextbox.setValue(contactOffice.getOfficeName());
			address1Textbox.setValue(contactOffice.getAddress1());
			address2Textbox.setValue(contactOffice.getAddress2());
			townCityTextbox.setValue(contactOffice.getTownCity());
			postcodeTextbox.setValue(contactOffice.getPostcode());
			countryTextbox.setValue(contactOffice.getCountry());
			telephone1Textbox.setValue(contactOffice.getTelephone1());
			telephone2Textbox.setValue(contactOffice.getTelephone2());
			faxTextbox.setValue(contactOffice.getFax());
			mobileTextbox.setValue(contactOffice.getMobile());
			emailTextbox.setValue(contactOffice.getEmail());
			urlTextbox.setValue(contactOffice.getUrl());
			statusCombobox.setValue(contactOffice.getStatus());
		} else {
			System.out.println("No contact office data found.");
		}
	}

	@Listen("onBlur = #address1Textbox,#address2Textbox,#statusCombobox,#townCityTextbox,#postcodeTextbox,#countryTextbox,#telephone1Textbox,#telephone2Textbox,#faxTextbox")
	public void validOffice() {
		isValid();
	}

	@Listen("onClick = #canselOffice")
	public void canselOffice() {
		mypopup.close();
		clearFields();
	}

	@Listen("onBlur = #emailTextbox")
	public void emailError() {
		String email = emailTextbox.getValue();
		emailError.setVisible(false);
		if (isValidEmail(email)) {
			System.out.println("email valid");
		} else if (email.isEmpty()) {
			emailError.setVisible(true);
			emailError.setValue("Please enter email");
		} else {

			emailError.setVisible(true);
			emailError.setValue("Email is not valid");
			System.out.println("email not valid");
		}
		isValid();
	}

	@Listen("onBlur = #officeNameTextbox")
	public void officeNameError() {
		String officeName = officeNameTextbox.getValue();

		officeNameError.setVisible(false);
		if (officeName.isBlank() || officeName.isEmpty()) {
			officeNameError.setVisible(true);
			officeNameError.setValue("Please enter officeName");
		}
		isValid();
	}

	@Listen("onBlur = #mobileTextbox")
	public void mobileError() {
		String mobile = mobileTextbox.getValue();
		mobileError.setVisible(false);
		if (isValidMobile(mobile)) {
			System.out.println("Mobile number valid");
		} else if (mobile.isEmpty()) {
			mobileError.setVisible(true);
			mobileError.setValue("Please enter Mobile Number");
		} else {
			mobileError.setVisible(true);
			mobileError.setValue("Mobile number is not valid");
			System.out.println("Mobile number not valid");
		}
		isValid();
	}

	private boolean isValid() {
		boolean mobile = isValidMobile(mobileTextbox.getValue());
		boolean email = isValidEmail(emailTextbox.getValue());
		boolean officeName = isValidOfficeName(officeNameTextbox.getValue());

		if (mobile && email && officeName) {
			submit.setVisible(true);
			return true;
		}
		submit.setVisible(false);
		return false;
	}

	private boolean isValidOfficeName(String officeName) {

		if (officeName.isEmpty() || officeName.isBlank()) {
			return false;
		}
		return true;
	}

	private boolean isValidMobile(String mobile) {
		String mobilePattern = "\\d{10}";
		if (mobile.isEmpty() || mobile.isBlank()) {
			return false;
		} else if (!mobile.matches(mobilePattern)) {
			return false;
		}
		return mobile.matches(mobilePattern);
	}

	private boolean isValidEmail(String email) {
		String emailPattern = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
		Pattern pattern = Pattern.compile(emailPattern);
		Matcher matcher = pattern.matcher(email);
		if (email.isBlank() || email.isEmpty()) {
			return false;
		} else if (!matcher.matches()) {
			return false;
		}
		return matcher.matches();
	}

	@Listen("onClick = #submit")
	public void submit() {

		if (!isValid()) {
			return;
		}

		int officeid = officeIntId.getValue();
		int tempID = tempIntId.getValue();
		String officeName = officeNameTextbox.getValue();
		String address1 = address1Textbox.getValue();
		String address2 = address2Textbox.getValue();
		String townCity = townCityTextbox.getValue();
		String postcode = postcodeTextbox.getValue();
		String country = countryTextbox.getValue();
		String telephone1 = telephone1Textbox.getValue();
		String telephone2 = telephone2Textbox.getValue();
		String fax = faxTextbox.getValue();
		String mobile = mobileTextbox.getValue();
		String email = emailTextbox.getValue();
		String url = urlTextbox.getValue();
		String status = statusCombobox.getSelectedItem() != null ? statusCombobox.getSelectedItem().getLabel() : "";

		ContactOffice contactOffice = new ContactOffice();
		contactOffice.setOfficeName(officeName);
		contactOffice.setAddress1(address1);
		contactOffice.setAddress2(address2);
		contactOffice.setTownCity(townCity);
		contactOffice.setPostcode(postcode);
		contactOffice.setCountry(country);
		contactOffice.setTelephone1(telephone1);
		contactOffice.setTelephone2(telephone2);
		contactOffice.setFax(fax);
		contactOffice.setMobile(mobile);
		contactOffice.setEmail(email);
		contactOffice.setUrl(url);
		contactOffice.setStatus(status);
		contactOffice.setTempId(tempID);

		Session session = Sessions.getCurrent();
		Integer myInteger = (Integer) session.getAttribute("myInteger");
		int value = 0;
		if (myInteger != null) {
			value = myInteger.intValue();
		}

		if (officeid > 0) {
			if (tempID > 0) {
				List<ContactOffice> contactOffices = temporaryOfficeList;
				for (ContactOffice tempOffice : temporaryOfficeList) {
					if (tempOffice.getTempId() == tempID) {
						// Update the ContactOffice object
						tempOffice.setOfficeName(contactOffice.getOfficeName());
						tempOffice.setAddress1(contactOffice.getAddress1());
						tempOffice.setAddress2(contactOffice.getAddress2());
						tempOffice.setTownCity(contactOffice.getTownCity());
						tempOffice.setPostcode(contactOffice.getPostcode());
						tempOffice.setCountry(contactOffice.getCountry());
						tempOffice.setTelephone1(contactOffice.getTelephone1());
						tempOffice.setTelephone2(contactOffice.getTelephone2());
						tempOffice.setFax(contactOffice.getFax());
						tempOffice.setMobile(contactOffice.getMobile());
						tempOffice.setEmail(contactOffice.getEmail());
						tempOffice.setUrl(contactOffice.getUrl());
						tempOffice.setStatus(contactOffice.getStatus());
						mypopup.close();
						contactController.loadContactOffice(0, contactOffices);
						System.out.println("Update temp office");
					}
				}
			} else {
				contactOffice.setId(officeid);
				contactDao.updateOffice(contactOffice);
				contactController.loadContactOffice(contactController.contactId, null);
				mypopup.close();
			}

		} else if (value > 0 && officeid == 0) {
			mypopup.close();
			contactOffice.setContactId(contactController.contactId);
			contactDao.insertContactOffice(contactOffice);
			contactController.loadContactOffice(contactController.contactId, null);
		} else {
			Integer id = (Integer) session.getAttribute("int");
			int tId = ++id;
			contactOffice.setTempId(tId);
			contactOffice.setId(tId);
			temporaryOfficeList.add(contactOffice);
			mypopup.close();
			List<ContactOffice> contactOffices = temporaryOfficeList;
			contactController.loadContactOffice(0, contactOffices);
			session.setAttribute("int", tId);
		}
		contactController.validContactSection();
		clearFields();
	}

	// Method to clear input fields after submission
	public void clearFields() {
		officeIntId.setValue(0);
		officeNameTextbox.setValue("");
		address1Textbox.setValue("");
		address2Textbox.setValue("");
		townCityTextbox.setValue("");
		postcodeTextbox.setValue("");
		countryTextbox.setValue("");
		telephone1Textbox.setValue("");
		telephone2Textbox.setValue("");
		faxTextbox.setValue("");
		mobileTextbox.setValue("");
		emailTextbox.setValue("");
		urlTextbox.setValue("");
		statusCombobox.setSelectedIndex(0);
		officeLabel.setValue("New office:");
	}

}