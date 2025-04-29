package com.bookManagement.net.services;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bookManagement.net.beans.ResponseWrapper;
import com.bookManagement.net.beans.UserBeans;
import com.bookManagement.net.dao.UserDao;
import com.bookManagement.net.dto.UserDto;
import com.bookManagement.net.util.ResponseDetails;

@Service
public class UserServices {

	@Autowired
	private UserDao dao;

	public ResponseWrapper<?> userSignup(UserDto dto) {

		try {
			String mobile = dto.getMobileNumber();
			String dob = dto.getDOB();

			if (dob == null || dob.isBlank()) {
				return new ResponseWrapper<>(-1, "DOB is required", ResponseDetails.BAD_REQUEST_HttpStatusCode,
						"DOB field is missing or blank");
			}

			int age = Period.between(LocalDate.parse(dob), LocalDate.now()).getYears();
			UserBeans beans = new UserBeans();
			beans.setTitle(dto.getTitle());
			beans.setName(dto.getName());
			beans.setEmail(dto.getEmail());
			beans.setMobileNumber(mobile);
			beans.setDOB(dob);
			beans.setBloodGroup(dto.getBloodGroup());
			beans.setGender(dto.getGender());
			beans.setAge(String.valueOf(age));
			beans.setRole(dto.getRole());

			int rowsAffected = dao.userSignup(beans);

			if (rowsAffected >= 1) {
				return new ResponseWrapper<String>(1, ResponseDetails.OK_HttpStatusCode, "User created successfully");
			} else if (rowsAffected == 0) {
				return new ResponseWrapper<String>(0, "User could not be created",
						ResponseDetails.INTERNAL_SERVER_ERROR_HttpStatusCode,
						ResponseDetails.INTERNAL_SERVER_ERROR_ResponseMessage);
			} else {
				return new ResponseWrapper<String>(-2, ResponseDetails.INTERNAL_SERVER_ERROR_HttpStatusCode,
						"Something went wrong");
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseWrapper<>(-1, "An error occurred: " + e.getMessage(),
					ResponseDetails.INTERNAL_SERVER_ERROR_HttpStatusCode, "Database error or exception");
		}
	}

	public ResponseWrapper<?> userAuth(UserDto dto) {

		try {
			String mobile = dto.getMobileNumber();

			UserBeans beans = new UserBeans();
			beans.setEmail(dto.getEmail());
			beans.setMobileNumber(mobile);
			beans.setPin(dto.getPin());
			beans.setRole(dto.getRole());

			int rowsAffected = dao.userAuth(beans);

			if (rowsAffected >= 1) {
				return new ResponseWrapper<String>(1, ResponseDetails.OK_HttpStatusCode,
						"User Authenticated successfully");
			} else if (rowsAffected == 0) {
				return new ResponseWrapper<String>(0, "User could not be authenticated",
						ResponseDetails.INTERNAL_SERVER_ERROR_HttpStatusCode,
						ResponseDetails.INTERNAL_SERVER_ERROR_ResponseMessage);
			} else {
				return new ResponseWrapper<String>(-2, ResponseDetails.INTERNAL_SERVER_ERROR_HttpStatusCode,
						"Something went wrong");
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseWrapper<>(-1, "An error occurred: " + e.getMessage(),
					ResponseDetails.INTERNAL_SERVER_ERROR_HttpStatusCode, "Database error or exception");
		}
	}

	public ResponseWrapper<?> getExistingUsers(UserDto dto) {

		try {
			UserBeans beans = new UserBeans();
			beans.setEmail(dto.getEmail());
			beans.setMobileNumber(dto.getMobileNumber());

			List<Map<String, Object>> response = dao.getExistingUsers(beans);

			if (response.isEmpty()) {
				return new ResponseWrapper<>(0, ResponseDetails.INTERNAL_SERVER_ERROR_HttpStatusCode, "No user found");
			} else {
				return new ResponseWrapper<>(1, "User already exists", ResponseDetails.OK_HttpStatusCode, response);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseWrapper<>(-1, ResponseDetails.INTERNAL_SERVER_ERROR_HttpStatusCode,
					"An error occurred while logging: " + e.getMessage());
		}
	}

	public ResponseWrapper<?> loginUser(UserDto dto) {

		try {
			UserBeans beans = new UserBeans();
			beans.setEmail(dto.getEmail());
			beans.setMobileNumber(dto.getMobileNumber());

			Map<String, Object> response = dao.loginUser(beans);

			if (response.isEmpty()) {
				return new ResponseWrapper<>(0, ResponseDetails.INTERNAL_SERVER_ERROR_HttpStatusCode, "No user found");
			} else {
				return new ResponseWrapper<>(1, "User logged in successfully", ResponseDetails.OK_HttpStatusCode,
						response);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseWrapper<>(-1, ResponseDetails.INTERNAL_SERVER_ERROR_HttpStatusCode,
					"An error occurred while logging: " + e.getMessage());
		}
	}

	public ResponseWrapper<?> getUserById(UserDto dto) {

		try {
			UserBeans beans = new UserBeans();
			beans.setCustomerId(dto.getCustomerId());

			List<Map<String, Object>> response = dao.getUserById(beans);

			if (response.isEmpty()) {
				return new ResponseWrapper<>(0, ResponseDetails.INTERNAL_SERVER_ERROR_HttpStatusCode, "No User found");
			} else {
				return new ResponseWrapper<>(1, "User details", ResponseDetails.OK_HttpStatusCode, response);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseWrapper<>(-1, ResponseDetails.INTERNAL_SERVER_ERROR_HttpStatusCode,
					"An error occurred: " + e.getMessage());
		}
	}

	public ResponseWrapper<?> editUser(UserDto dto) {

		try {

			String dob = dto.getDOB();

			int age = Period.between(LocalDate.parse(dob), LocalDate.now()).getYears();

			UserBeans beans = new UserBeans();
			beans.setUserId(dto.getUserId());
			beans.setName(dto.getName());
			beans.setTitle(dto.getTitle());
			beans.setDOB(dob);
			beans.setBloodGroup(dto.getBloodGroup());
			beans.setGender(dto.getGender());
			beans.setAge(String.valueOf(age));

			int rowsAffected = dao.editUser(beans);

			if (rowsAffected >= 1) {
				return new ResponseWrapper<String>(1, ResponseDetails.OK_HttpStatusCode, "User updated");
			} else if (rowsAffected == 0) {
				return new ResponseWrapper<String>(0, "User could not be updated",
						ResponseDetails.INTERNAL_SERVER_ERROR_HttpStatusCode,
						ResponseDetails.INTERNAL_SERVER_ERROR_ResponseMessage);
			} else {
				return new ResponseWrapper<String>(-2, ResponseDetails.INTERNAL_SERVER_ERROR_HttpStatusCode,
						"Something went wrong");
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseWrapper<>(-1, "An error occurred: " + e.getMessage(),
					ResponseDetails.INTERNAL_SERVER_ERROR_HttpStatusCode, "Database error or exception");
		}
	}

	public ResponseWrapper<?> updateUserStatus(UserDto dto) {

		try {

			UserBeans beans = new UserBeans();
			beans.setUserId(dto.getUserId());
			beans.setActiveStatus(dto.getActiveStatus());

			int rowsAffected = dao.updateUserStatus(beans);

			if (rowsAffected >= 1) {
				return new ResponseWrapper<String>(1, ResponseDetails.OK_HttpStatusCode, "Status updated successfully");
			} else if (rowsAffected == 0) {
				return new ResponseWrapper<String>(0, "Status could not be updated",
						ResponseDetails.INTERNAL_SERVER_ERROR_HttpStatusCode,
						ResponseDetails.INTERNAL_SERVER_ERROR_ResponseMessage);
			} else {
				return new ResponseWrapper<String>(-2, ResponseDetails.INTERNAL_SERVER_ERROR_HttpStatusCode,
						"Something went wrong");
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseWrapper<>(-1, "An error occurred: " + e.getMessage(),
					ResponseDetails.INTERNAL_SERVER_ERROR_HttpStatusCode, "Database error or exception");
		}
	}

}
