package com.bookManagement.net.services;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bookManagement.net.beans.CustomerBeans;
import com.bookManagement.net.beans.ResponseWrapper;
import com.bookManagement.net.dao.CustomerDao;
import com.bookManagement.net.dto.CustomerDto;
import com.bookManagement.net.util.ResponseDetails;

@Service
public class CustomerServices {

	@Autowired
	private CustomerDao dao;

	public ResponseWrapper<?> insertCustomerProfile(CustomerDto dto) {

		try {
			String mobile = dto.getMobileNumber();
			String dob = dto.getDOB();

			if (dob == null || dob.isBlank()) {
				return new ResponseWrapper<>(-1, "DOB is required", ResponseDetails.BAD_REQUEST_HttpStatusCode,
						"DOB field is missing or blank");
			}

			int age = Period.between(LocalDate.parse(dob), LocalDate.now()).getYears();
			CustomerBeans beans = new CustomerBeans();
			beans.setAuthId(dto.getAuthId());
			beans.setTitle(dto.getTitle());
			beans.setName(dto.getName());
			beans.setEmail(dto.getEmail());
			beans.setMobileNumber(mobile);
			beans.setDOB(dob);
			beans.setBloodGroup(dto.getBloodGroup());
			beans.setGender(dto.getGender());
			beans.setAge(String.valueOf(age));

			int rowsAffected = dao.insertCustomerProfile(beans);

			if (rowsAffected >= 1) {
				return new ResponseWrapper<String>(1, ResponseDetails.OK_HttpStatusCode,
						"Customer created successfully");
			} else if (rowsAffected == 0) {
				return new ResponseWrapper<String>(0, "Customer could not be created",
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

	public ResponseWrapper<?> insertCustomerAuth(CustomerDto dto) {

		try {
			String mobile = dto.getMobileNumber();

			CustomerBeans beans = new CustomerBeans();
			beans.setEmail(dto.getEmail());
			beans.setMobileNumber(mobile);
			beans.setPin(dto.getPin());
			beans.setRole(dto.getRole());

			int rowsAffected = dao.insertCustomerAuth(beans);

			if (rowsAffected >= 1) {
				return new ResponseWrapper<String>(1, ResponseDetails.OK_HttpStatusCode,
						"Customer Authenticated successfully");
			} else if (rowsAffected == 0) {
				return new ResponseWrapper<String>(0, "Customer could not be authenticated",
						ResponseDetails.INTERNAL_SERVER_ERROR_HttpStatusCode,
						ResponseDetails.INTERNAL_SERVER_ERROR_ResponseMessage);
			} else {
				return new ResponseWrapper<>(-2, ResponseDetails.INTERNAL_SERVER_ERROR_HttpStatusCode,
						"Something went wrong");
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseWrapper<>(-1, "An error occurred: " + e.getMessage(),
					ResponseDetails.INTERNAL_SERVER_ERROR_HttpStatusCode, "Database error or exception");
		}
	}

	public ResponseWrapper<?> getCustomerAuthId(CustomerDto dto) {

		try {

			CustomerBeans beans = new CustomerBeans();
			beans.setMobileNumber(dto.getMobileNumber());

			int rowsAffected = dao.getCustomerAuthId(beans);

			if (rowsAffected >= 1) {

				return new ResponseWrapper<>(1, ResponseDetails.OK_RespnseMessage, ResponseDetails.OK_HttpStatusCode,
						rowsAffected);
			} else if (rowsAffected == 0) {

				return new ResponseWrapper<>(0, "No auth Id found",
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

	public ResponseWrapper<?> getCustomerProfileId(CustomerDto dto) {

		try {
			CustomerBeans beans = new CustomerBeans();
			beans.setAuthId(dto.getAuthId());

			int rowsAffected = dao.getCustomerProfileId(beans);

			if (rowsAffected >= 1) {
				return new ResponseWrapper<>(1, ResponseDetails.OK_RespnseMessage, ResponseDetails.OK_HttpStatusCode,
						rowsAffected);
			} else if (rowsAffected == 0) {
				return new ResponseWrapper<String>(0, "No customer Id found",
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

	public ResponseWrapper<?> getExistingCustomers(CustomerDto dto) {

		try {
			CustomerBeans beans = new CustomerBeans();
			beans.setEmail(dto.getEmail());
			beans.setMobileNumber(dto.getMobileNumber());

			List<Map<String, Object>> response = dao.getExistingCustomers(beans);

			if (response.isEmpty()) {
				return new ResponseWrapper<>(0, ResponseDetails.INTERNAL_SERVER_ERROR_HttpStatusCode,
						"No Customer found");
			} else {
				return new ResponseWrapper<>(1, "Customer already exists", ResponseDetails.OK_HttpStatusCode, response);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseWrapper<>(-1, ResponseDetails.INTERNAL_SERVER_ERROR_HttpStatusCode,
					"An error occurred while logging: " + e.getMessage());
		}
	}

	public ResponseWrapper<?> loginCustomer(CustomerDto dto) {

		try {
			CustomerBeans beans = new CustomerBeans();
			beans.setEmail(dto.getEmail());
			beans.setMobileNumber(dto.getMobileNumber());

			Map<String, Object> response = dao.loginCustomer(beans);

			if (response.isEmpty()) {
				return new ResponseWrapper<>(0, ResponseDetails.INTERNAL_SERVER_ERROR_HttpStatusCode,
						"No Customer found");
			} else {
				return new ResponseWrapper<>(1, "Customer logged in successfully", ResponseDetails.OK_HttpStatusCode,
						response);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseWrapper<>(-1, ResponseDetails.INTERNAL_SERVER_ERROR_HttpStatusCode,
					"An error occurred while logging: " + e.getMessage());
		}
	}

	public ResponseWrapper<?> linkCustomerUser(CustomerDto dto) {

		try {
			CustomerBeans beans = new CustomerBeans();
			beans.setUserId(dto.getUserId());
			beans.setCustomerId(dto.getCustomerId());

			int rowsAffected = dao.linkCustomerUser(beans);

			if (rowsAffected >= 1) {
				return new ResponseWrapper<String>(1, ResponseDetails.OK_HttpStatusCode,
						"Customer linked successfully");
			} else if (rowsAffected == 0) {
				return new ResponseWrapper<String>(0, "Customer could not be linked",
						ResponseDetails.INTERNAL_SERVER_ERROR_HttpStatusCode,
						ResponseDetails.INTERNAL_SERVER_ERROR_ResponseMessage);
			} else {
				return new ResponseWrapper<>(-2, ResponseDetails.INTERNAL_SERVER_ERROR_HttpStatusCode,
						"Something went wrong");
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseWrapper<>(-1, "An error occurred: " + e.getMessage(),
					ResponseDetails.INTERNAL_SERVER_ERROR_HttpStatusCode, "Database error or exception");
		}
	}

	public ResponseWrapper<?> getCustomersList(CustomerDto dto) {

		try {
			CustomerBeans beans = new CustomerBeans();
			beans.setUserId(dto.getUserId());

			List<Map<String, Object>> response = dao.getCustomersList(beans);

			if (response.isEmpty()) {
				return new ResponseWrapper<>(0, ResponseDetails.INTERNAL_SERVER_ERROR_HttpStatusCode,
						"No Customer found");
			} else {
				return new ResponseWrapper<>(1, "Customer list", ResponseDetails.OK_HttpStatusCode, response);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseWrapper<>(-1, ResponseDetails.INTERNAL_SERVER_ERROR_HttpStatusCode,
					"An error occurred: " + e.getMessage());
		}
	}

	public ResponseWrapper<?> getCustomerById(CustomerDto dto) {

		try {
			CustomerBeans beans = new CustomerBeans();
			beans.setCustomerId(dto.getCustomerId());

			List<Map<String, Object>> response = dao.getCustomerById(beans);

			if (response.isEmpty()) {
				return new ResponseWrapper<>(0, ResponseDetails.INTERNAL_SERVER_ERROR_HttpStatusCode,
						"No Customer found");
			} else {
				return new ResponseWrapper<>(1, "Customer details", ResponseDetails.OK_HttpStatusCode, response);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseWrapper<>(-1, ResponseDetails.INTERNAL_SERVER_ERROR_HttpStatusCode,
					"An error occurred: " + e.getMessage());
		}
	}

	public ResponseWrapper<?> editCustomer(CustomerDto dto) {

		try {

			String dob = dto.getDOB();

			int age = Period.between(LocalDate.parse(dob), LocalDate.now()).getYears();

			CustomerBeans beans = new CustomerBeans();
			beans.setCustomerId(dto.getCustomerId());
			beans.setName(dto.getName());
			beans.setTitle(dto.getTitle());
			beans.setDOB(dob);
			beans.setBloodGroup(dto.getBloodGroup());
			beans.setGender(dto.getGender());
			beans.setAge(String.valueOf(age));

			int rowsAffected = dao.editCustomer(beans);

			if (rowsAffected >= 1) {
				return new ResponseWrapper<String>(1, ResponseDetails.OK_HttpStatusCode, "Customer updated");
			} else if (rowsAffected == 0) {
				return new ResponseWrapper<String>(0, "Customer could not be updated",
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

	public ResponseWrapper<?> updateCustomerStatus(CustomerDto dto) {

		try {

			CustomerBeans beans = new CustomerBeans();
			beans.setUserId(dto.getUserId());
			beans.setCustomerId(dto.getCustomerId());
			beans.setActiveStatus(dto.getActiveStatus());

			int rowsAffected = dao.updateCustomerStatus(beans);

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

	public ResponseWrapper<?> getCustomerOrderHistory(CustomerDto dto) {

		try {
			CustomerBeans beans = new CustomerBeans();
			beans.setCustomerId(dto.getCustomerId());

			List<Map<String, Object>> response = dao.getCustomerOrderHistory(beans);

			if (response.isEmpty()) {
				return new ResponseWrapper<>(0, ResponseDetails.INTERNAL_SERVER_ERROR_HttpStatusCode, "No order found");
			} else {
				return new ResponseWrapper<>(1, "Order list", ResponseDetails.OK_HttpStatusCode, response);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseWrapper<>(-1, ResponseDetails.INTERNAL_SERVER_ERROR_HttpStatusCode,
					"An error occurred: " + e.getMessage());
		}
	}

}
