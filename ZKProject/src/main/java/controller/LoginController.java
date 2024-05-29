package controller;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Execution;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Include;
import org.zkoss.zul.Label;
import org.zkoss.zul.Textbox;

import dao.ContactDao;
import dao.ContactDaoimpl;
import model.User;

public class LoginController extends SelectorComposer<Component> {

	private static final long serialVersionUID = 1L;

	@Wire
	private Include subPageInclude;

	@Wire
	public Grid contactGrid;

	ContactDao contactDao = new ContactDaoimpl();

	@Wire
	private Include subPageIncludes;

	@Wire("#username")
	private Textbox usernameBox;

	@Wire("#password")
	private Textbox passwordBox;
	
	@Wire
	private Label errorBox;

	Execution executions = Executions.getCurrent();

	@Listen("onClick = #nextBtn")
	public void nextStage() {
		
		User user=new User();
		user.setUsername(usernameBox.getValue());
		int exist=contactDao.checkUserNameExist(user);
		
		errorBox.setVisible(false);
		if(usernameBox.getValue().isEmpty()&&usernameBox.getValue().isBlank() ) {
			errorBox.setValue("UserName is required.");
			errorBox.setVisible(true);
			return;
		}else if(exist==0) {
			errorBox.setValue("This username does not exist.");
			errorBox.setVisible(true);
			return;
		}
		Clients.evalJavaScript("showStage2();");
		
	}

	@Listen("onClick = #backBtn")
	public void backStage() {		
		Clients.evalJavaScript("backStage();");
	}

	@Listen("onClick = #logo")
	public void logoClick() {		
		Executions.sendRedirect("menu.zul");
	}
	
	@Listen("onClick = #addContact")
	public void showContactPage() {
		subPageInclude.setSrc("");
		subPageInclude.setDynamicProperty("subPageInclude", subPageInclude);
		subPageInclude.setSrc("contact.zul");
	}


	@Listen("onClick = #dashboard")
	public void dashboardCall() {
		subPageInclude.setSrc("dashboard.zul");
	}
	
	@Listen("onClick = #newsale")
	public void newsaleCall() {
		subPageInclude.setSrc("newsale.zul");
	}
	
	@Listen("onClick = #searchsale")
	public void searchsaleCall() {
		subPageInclude.setSrc("searchsale.zul");
	}
	
	@Listen("onClick = #listsale")
	public void listsaleCall() {
		subPageInclude.setSrc("listsale.zul");
	}
	
	@Listen("onClick = #listContact")
	public void showContactListPage() {
		subPageInclude.setDynamicProperty("subPageInclude", subPageInclude);
		subPageInclude.setSrc("contactlist.zul");
	}
	
	@Listen("onClick = #logout")
	public void logout() {
		Executions.sendRedirect("index.zul");
	}

	@Listen("onClick = #loginBtn")
	public void login() {
		String username = usernameBox.getValue();
		String password = passwordBox.getValue();
		errorBox.setVisible(false);
		
		if(passwordBox.getValue().isEmpty()) {
			errorBox.setValue("Password is required.");
			errorBox.setVisible(true);
			return;
		}
		User user = new User();
		user.setUsername(username);
		user.setPassword(password);
		Session session = Sessions.getCurrent();
		session.setAttribute("loggedInUser", username);

		int loggedUserId = contactDao.checkUserExistenceForLogin(user);
		if (loggedUserId > 0) {
			session.setAttribute("loggedUserId", loggedUserId);
			Executions.sendRedirect("menu.zul");
		} else {
			errorBox.setValue("Invalid password.");
			errorBox.setVisible(true);
//			Clients.showNotification("Invalid username or password", Clients.NOTIFICATION_TYPE_ERROR, null, null, 2000);
		}

	}

}