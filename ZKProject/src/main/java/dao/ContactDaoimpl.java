package dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import mapper.ContactMapper;
import model.Category;
import model.Contact;
import model.ContactCategory;
import model.ContactOffice;
import model.User;
import util.MyBatisUtil;

@Repository("ContactDao")
public class ContactDaoimpl implements ContactDao {

	@Override
	public int checkUserExistenceForLogin(User user) {
		try (SqlSession session = MyBatisUtil.getSqlSessionFactory().openSession()) {
			ContactMapper contactMapper = session.getMapper(ContactMapper.class);
			Integer userId = contactMapper.checkUserExistenceForLogin(user);
			// If userId is null, return -1 indicating user doesn't exist
			return userId != null ? userId : -1;
		} catch (Exception e) {
			e.printStackTrace();
			return -1; // Return -1 indicating an error occurred
		}
	}

	@Override
	public List<Category> getAllCategories() {
		try (SqlSession session = MyBatisUtil.getSqlSessionFactory().openSession()) {
			ContactMapper contactMapper = session.getMapper(ContactMapper.class);
			return contactMapper.getAllCategories();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public int insertContact(Contact contact) {
		int generatedId = -1; // Default value
		try (SqlSession session = MyBatisUtil.getSqlSessionFactory().openSession()) {
			ContactMapper contactMapper = session.getMapper(ContactMapper.class);
			contactMapper.insertContact(contact);
			generatedId = contact.getId(); // Retrieve the generated ID from the Contact object after insertion
			session.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return generatedId; // Return the generated ID
	}

	@Override
	public void insertContactOffice(ContactOffice contactOffice) {
		try (SqlSession session = MyBatisUtil.getSqlSessionFactory().openSession()) {
			ContactMapper contactMapper = session.getMapper(ContactMapper.class);
			contactMapper.insertContactOffice(contactOffice);
			session.commit(); // Ensure the transaction is committed
		} catch (Exception e) {
			e.printStackTrace(); // Handle or log the exception as needed
		}
	}

	@Override
	public void insertContactCategory(ContactCategory ContactCategory) {
		try (SqlSession session = MyBatisUtil.getSqlSessionFactory().openSession()) {
			ContactMapper contactMapper = session.getMapper(ContactMapper.class);
			contactMapper.insertContactCategory(ContactCategory);
			session.commit(); // Ensure the transaction is committed
		} catch (Exception e) {
			e.printStackTrace(); // Handle or log the exception as needed
		}
	}

	@Override
	public List<Contact> getAllContacts() {
		try (SqlSession session = MyBatisUtil.getSqlSessionFactory().openSession()) {
			ContactMapper contactMapper = session.getMapper(ContactMapper.class);
			return contactMapper.getAllContacts();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public Contact getContactDetails(String id) {
		try (SqlSession session = MyBatisUtil.getSqlSessionFactory().openSession()) {
			ContactMapper contactMapper = session.getMapper(ContactMapper.class);
			return contactMapper.getContactDetails(id);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<ContactCategory> getContactCategory(int id) {
		try (SqlSession session = MyBatisUtil.getSqlSessionFactory().openSession()) {
			ContactMapper contactMapper = session.getMapper(ContactMapper.class);
			return contactMapper.getContactCategory(id);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<ContactOffice> getCompanyContactOffice(int id) {
		try (SqlSession session = MyBatisUtil.getSqlSessionFactory().openSession()) {
			ContactMapper contactMapper = session.getMapper(ContactMapper.class);
			return contactMapper.getCompanyContactOffice(id);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public ContactOffice getContactOffice(String id) {
		try (SqlSession session = MyBatisUtil.getSqlSessionFactory().openSession()) {
			ContactMapper contactMapper = session.getMapper(ContactMapper.class);
			return contactMapper.getContactOffice(id);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public void deleteOffice(int id) {
		try (SqlSession session = MyBatisUtil.getSqlSessionFactory().openSession()) {
			ContactMapper contactMapper = session.getMapper(ContactMapper.class);
			contactMapper.deleteOffice(id);
			session.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void updateOffice(ContactOffice contactOffice) {
		try (SqlSession session = MyBatisUtil.getSqlSessionFactory().openSession()) {
			ContactMapper contactMapper = session.getMapper(ContactMapper.class);
			contactMapper.updateOffice(contactOffice);
			session.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void updateContact(Contact contact) {
		try (SqlSession session = MyBatisUtil.getSqlSessionFactory().openSession()) {
			ContactMapper contactMapper = session.getMapper(ContactMapper.class);
			contactMapper.updateContact(contact);
			session.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void deletecategory(int id) {
		try (SqlSession session = MyBatisUtil.getSqlSessionFactory().openSession()) {
			ContactMapper contactMapper = session.getMapper(ContactMapper.class);
			contactMapper.deletecategory(id);
			session.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void deletecontact(int id) {
		try (SqlSession session = MyBatisUtil.getSqlSessionFactory().openSession()) {
			ContactMapper contactMapper = session.getMapper(ContactMapper.class);
			contactMapper.deletecontact(id);
			session.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public int checkShortnameExist(Contact contact) {
		try (SqlSession session = MyBatisUtil.getSqlSessionFactory().openSession()) {
			ContactMapper contactMapper = session.getMapper(ContactMapper.class);
			return contactMapper.checkShortnameExist(contact);
		}
	}

	@Override
	public String getUsernameByContactId(int id) {
		try (SqlSession session = MyBatisUtil.getSqlSessionFactory().openSession()) {
			ContactMapper contactMapper = session.getMapper(ContactMapper.class);
			return contactMapper.getUsernameByContactId(id);
		} catch (Exception e) {
			e.printStackTrace();
			return null; // or any default value you prefer
		}
	}

	@Override
	public int checkUserNameExist(User user) {
		try (SqlSession session = MyBatisUtil.getSqlSessionFactory().openSession()) {
			ContactMapper contactMapper = session.getMapper(ContactMapper.class);
			return contactMapper.checkUserNameExist(user);
		}
	}

}