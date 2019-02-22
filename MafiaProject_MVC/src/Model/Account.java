package Model;

public class Account {

private String account;
private String password;
private String address;
private String gender;
private String weight;
private String height;
private String hair;
private String top;
private String bottom;
private String shoes;
public String getAccount() {
	return account;
}
public void setAccount(String account) {
	this.account = account;
}
public String getPassword() {
	return password;
}
public void setPassword(String password) {
	this.password = password;
}
public String getAddress() {
	return address;
}
public void setAddress(String address) {
	this.address = address;
}
public String getGender() {
	return gender;
}
public void setGender(String gender) {
	this.gender = gender;
}
public String getWeight() {
	return weight;
}
public void setWeight(String weight) {
	this.weight = weight;
}
public String getHeight() {
	return height;
}
public void setHeight(String height) {
	this.height = height;
}
public String getHair() {
	return hair;
}
public void setHair(String hair) {
	this.hair = hair;
}
public String getTop() {
	return top;
}
public void setTop(String top) {
	this.top = top;
}
public String getBottom() {
	return bottom;
}
public void setBottom(String bottom) {
	this.bottom = bottom;
}
public String getShoes() {
	return shoes;
}
public void setShoes(String shoes) {
	this.shoes = shoes;
}

public Account(String account, String password, String address, String gender, String weight, String height,
		String hair, String top, String bottom, String shoes) {
	super();
	this.account = account;
	this.password = password;
	this.address = address;
	this.gender = gender;
	this.weight = weight;
	this.height = height;
	this.hair = hair;
	this.top = top;
	this.bottom = bottom;
	this.shoes = shoes;
	
}



}