package dao;

import java.util.List;

import model.Category;
import model.Contact;
import model.ContactCategory;
import model.ContactOffice;
import model.User;

public interface ContactDao {

	int checkUserExistenceForLogin(User user);

	List<Category> getAllCategories();

	int insertContact(Contact contact);

	void insertContactOffice(ContactOffice contactOffice);
	
	void insertContactCategory(ContactCategory ContactCategory);
	
	List<Contact> getAllContacts();
	
	Contact getContactDetails(String id);
	
	List<ContactCategory> getContactCategory(int id);

	List<ContactOffice> getCompanyContactOffice(int id);

	ContactOffice getContactOffice(String id);
	
	void deleteOffice(int id);
	
	void updateOffice(ContactOffice contactOffice);
	
	void updateContact(Contact contact);
	
	void deletecategory(int id);
	
	void deletecontact(int id);
	
	int checkShortnameExist(Contact contact);
	
	String getUsernameByContactId(int id);
	
	int checkUserNameExist(User user);
	
}
