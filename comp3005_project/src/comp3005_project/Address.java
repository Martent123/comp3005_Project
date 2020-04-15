package comp3005_project;

public class Address {
	private String street_num;
	private String stree_name;
	private String postal_code;
	private String address_type;
	private int id;
	
	public String getName() {
		return stree_name;
	}
	public void setName(String stree_name) {
		this.stree_name = stree_name;
	}
	public String getNumber() {
		return street_num;
	}
	public void setNumber(String street_num) {
		this.street_num = street_num;
	}
	public String getCode() {
		return postal_code;
	}
	public void setCode(String postal_code) {
		this.postal_code = postal_code;
	}
	public String getType() {
		return address_type;
	}
	public void setType(String address_type) {
		this.address_type = address_type;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
}
