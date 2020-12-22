package ca.sheridancollege.beans;

import java.io.Serializable;

public class User implements Serializable {

	private Long userId;
	private String firstName;
	private String lastName;
	private String userName;
	private String encyptedPassword;
	
	public User() {
		
	}


	public User(Long userId, String firstName, String lastName, String userName,
			String encyptedPassword) {
		super();
		this.userId = userId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.userName = userName;
		this.encyptedPassword = encyptedPassword;
	}




	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	

	public String getUserName() {
		return userName;
	}



	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getEncryptedPassword() {
		return encyptedPassword;
	}



	public void setEncyptedPassword(String encyptedPassword) {
		this.encyptedPassword = encyptedPassword;
	}



	@Override
	public String toString() {
		return "User [userId=" + userId + ", firstName=" + firstName + ", lastName=" + lastName + ", userName="
				+ userName +", encyptedPassword=" + encyptedPassword + "]";
	}





	
}
