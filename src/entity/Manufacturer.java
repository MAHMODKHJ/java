package entity;

public class Manufacturer {
    private int manufacturerID;
    private String fullName;
    private String phoneNumber;
    private String address;
    private String email;

    public Manufacturer(int manufacturerID, String fullName, String phoneNumber, String address, String email) {
        this.manufacturerID = manufacturerID;
        this.fullName = fullName;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.email = email;
    }

	public int getManufacturerID() {
		return manufacturerID;
	}

	public void setManufacturerID(int manufacturerID) {
		this.manufacturerID = manufacturerID;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
    
}
