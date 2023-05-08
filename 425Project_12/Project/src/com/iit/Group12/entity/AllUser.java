package com.iit.Group12.entity;

public class AllUser {

	private String user_id;
	private String password;
	private String first_name;
	private String last_name;
	private String user_type;
	private String email;

    public AllUser() {

    }
	public String getUser_type() {
		return user_type;
	}

	public void setUser_type(String user_type) {
		this.user_type = user_type;
	}

	public String getUser_id() {
		return this.user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFirst_name() {
		return this.first_name;
	}

	public void setFirst_name(String first_name) {
		this.first_name = first_name;
	}

	public String getLast_name() {
		return this.last_name;
	}

	public void setLast_name(String last_name) {
		this.last_name = last_name;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	public void printdata(){
		System.out.println("user name:" + this.getUser_id());
		System.out.println("first name:"+ this.getFirst_name());
		System.out.println("last name:"+ this.getLast_name());
		System.out.println("user type:"+ this.getUser_type());
		System.out.println("user email:"+ this.getEmail());
	}
	

}