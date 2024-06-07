package model;

public class ContactOffice {
	
	private int id;
	private int tempId;
	private int contactId;
	private String officeName;
	private String address1;
	private String address2;
	private String townCity;
	private String postcode;
	private String country;
	private String telephone1;
	private String telephone2;
	private String fax;
	private Double mobile;
	private String email;
	private String url;
	private String status;

	// Default constructor
	public ContactOffice() {
	}

	public ContactOffice(int id,int tempId, int contactId, String officeName, String address1, String address2, String townCity,
			String postcode, String country, String telephone1, String telephone2, String fax, Double mobile,
			String email, String url, String status) {
		this.id = id;
		this.tempId=tempId;
		this.contactId = contactId;
		this.officeName = officeName;
		this.address1 = address1;
		this.address2 = address2;
		this.townCity = townCity;
		this.postcode = postcode;
		this.country = country;
		this.telephone1 = telephone1;
		this.telephone2 = telephone2;
		this.fax = fax;
		this.mobile = mobile;
		this.email = email;
		this.url = url;
		this.status = status;
	}

	// Getters and setters
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public int getTempId() {
		return tempId;
	}

	public void setTempId(int tempId) {
		this.tempId = tempId;
	}

	public int getContactId() {
		return contactId;
	}

	public void setContactId(int contactId) {
		this.contactId = contactId;
	}

	public String getOfficeName() {
		return officeName;
	}

	public void setOfficeName(String officeName) {
		this.officeName = officeName;
	}

	public String getAddress1() {
		return address1;
	}

	public void setAddress1(String address1) {
		this.address1 = address1;
	}

	public String getAddress2() {
		return address2;
	}

	public void setAddress2(String address2) {
		this.address2 = address2;
	}

	public String getTownCity() {
		return townCity;
	}

	public void setTownCity(String townCity) {
		this.townCity = townCity;
	}

	public String getPostcode() {
		return postcode;
	}

	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getTelephone1() {
		return telephone1;
	}

	public void setTelephone1(String telephone1) {
		this.telephone1 = telephone1;
	}

	public String getTelephone2() {
		return telephone2;
	}

	public void setTelephone2(String telephone2) {
		this.telephone2 = telephone2;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public Double getMobile() {
		return mobile;
	}

	public void setMobile(Double mobile) {
		this.mobile = mobile;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
}