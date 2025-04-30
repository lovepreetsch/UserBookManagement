package com.bookManagement.net.dto;

import com.bookManagement.net.services.DetailValidationGroup;
import com.bookManagement.net.services.FullValidationGroup;
import com.bookManagement.net.services.MinimalValidationGroup;
import com.bookManagement.net.services.UpdateStatusValidationGroup;
import com.bookManagement.net.services.UpdateValidationGroup;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CustomerDto {

	private String id;
	private String bookId;

	@NotBlank(message = "User Id is required", groups = { MinimalValidationGroup.class,
			UpdateStatusValidationGroup.class })
	private String userId;

	@NotBlank(message = "Customer Id is required", groups = { DetailValidationGroup.class, UpdateValidationGroup.class,
			UpdateStatusValidationGroup.class })
	private String customerId;
	private String authId;

	@NotBlank(message = "Title is required", groups = UpdateValidationGroup.class)
	private String title;

	@NotBlank(message = "Name is required", groups = UpdateValidationGroup.class)
	private String name;

	@Email
	@NotBlank(message = "Email is required", groups = FullValidationGroup.class)
	private String email;

	@NotBlank(message = "Mobile number is required", groups = FullValidationGroup.class)
	private String mobileNumber;

	@NotBlank(message = "Password is required", groups = FullValidationGroup.class)
	private String pin;
	private String page;
	private String count;

	@NotBlank(message = "Blood group is required", groups = UpdateValidationGroup.class)
	private String bloodGroup;
	private String invitationStatus;
	private String token;
	public String JWtoken;

	@NotBlank(message = "Gender is required", groups = UpdateValidationGroup.class)
	private String gender;

	@NotBlank(message = "Date of Birth is required", groups = UpdateValidationGroup.class)
	private String DOB;
	private String age;
	private String orderId;

	@NotNull(message = "Active status is required", groups = UpdateStatusValidationGroup.class)
	private Boolean activeStatus;
	private String rating;
	private String reviewDescription;

	private String role;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public String getBookId() {
		return bookId;
	}

	public void setBookId(String bookId) {
		this.bookId = bookId;
	}

	public void setActiveStatus(Boolean activeStatus) {
		this.activeStatus = activeStatus;
	}

	public String getAuthId() {
		return authId;
	}

	public void setAuthId(String authId) {
		this.authId = authId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public String getPin() {
		return pin;
	}

	public void setPin(String pin) {
		this.pin = pin;
	}

	public String getPage() {
		return page;
	}

	public void setPage(String page) {
		this.page = page;
	}

	public String getCount() {
		return count;
	}

	public void setCount(String count) {
		this.count = count;
	}

	public String getBloodGroup() {
		return bloodGroup;
	}

	public void setBloodGroup(String bloodGroup) {
		this.bloodGroup = bloodGroup;
	}

	public String getInvitationStatus() {
		return invitationStatus;
	}

	public void setInvitationStatus(String invitationStatus) {
		this.invitationStatus = invitationStatus;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getJWtoken() {
		return JWtoken;
	}

	public void setJWtoken(String jWtoken) {
		JWtoken = jWtoken;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getDOB() {
		return DOB;
	}

	public void setDOB(String dOB) {
		DOB = dOB;
	}

	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public boolean getActiveStatus() {
		return activeStatus;
	}

	public void setActiveStatus(boolean activeStatus) {
		this.activeStatus = activeStatus;
	}

	public String getRating() {
		return rating;
	}

	public void setRating(String rating) {
		this.rating = rating;
	}

	public String getReviewDescription() {
		return reviewDescription;
	}

	public void setReviewDescription(String reviewDescription) {
		this.reviewDescription = reviewDescription;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

}
