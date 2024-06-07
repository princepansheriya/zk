package controller;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Include;

public class MenuController extends SelectorComposer<Component> {

	private static final long serialVersionUID = 1L;
	@Wire
	private Include subPageInclude;

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		Session session = Sessions.getCurrent();
		Integer id = (Integer) session.getAttribute("loggedUserId");
		if (id == null) {
			Executions.sendRedirect("index.zul");
			return;
		}
		subPageInclude = (Include) session.getAttribute("subPageInclude");
	}

	@Listen("onClick = #logo")
	public void logoClick() {
		Executions.sendRedirect("menu.zul");
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
		Sessions.getCurrent().invalidate();
		Executions.sendRedirect("index.zul");
	}

	@Listen("onClick = #addContact")
	public void showContactPage() {
		subPageInclude.setSrc("");
		subPageInclude.setDynamicProperty("subPageInclude", subPageInclude);
		subPageInclude.setSrc("contact.zul");
	}

}