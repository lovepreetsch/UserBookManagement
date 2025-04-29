package com.bookManagement.net.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bookManagement.net.beans.ResponseWrapper;
import com.bookManagement.net.dto.CustomerDto;
import com.bookManagement.net.services.CustomerServices;
import com.bookManagement.net.util.ResponseDetails;

@RestController
@RequestMapping("v1/customer")
public class CustomerController {

	@Autowired
	private CustomerServices customerServices;

	@PostMapping("/insert")
	public ResponseEntity<Map<String, Object>> insertCustomer(@RequestBody CustomerDto dto,
			BindingResult bindingResult) {

		if (bindingResult.hasErrors()) {
			String errorMessage = bindingResult.getFieldErrors().stream().map(FieldError::getDefaultMessage)
					.collect(Collectors.joining(", "));
			return ResponseEntity.badRequest().body(
					Map.of("status", HttpStatus.BAD_REQUEST.value(), "message", "Validation failed: " + errorMessage));
		}

		if (!"CUSTOMER".equalsIgnoreCase(dto.getRole())) {
			return ResponseEntity.badRequest().body(Map.of("status", HttpStatus.BAD_REQUEST.value(), "message",
					"Invalid role. Role must be CUSTOMER."));
		}

		try {
			// Check if customer already exists
			ResponseWrapper<?> existingUser = customerServices.getExistingCustomers(dto);
			if (existingUser.getSuccess() > 0) {
				return ResponseEntity.status(HttpStatus.CONFLICT)
						.body(Map.of("status", HttpStatus.CONFLICT.value(), "message", "Customer already exists."));
			} else if (existingUser.getSuccess() < 0) {
				return ResponseEntity.status(existingUser.getStatus()).body(Map.of("status", existingUser.getStatus(),
						"message", "Error checking customer existence: " + existingUser.getMessage()));
			}

			// Insert customer authentication
			ResponseWrapper<?> insertCustomerAuth = customerServices.insertCustomerAuth(dto);
			if (insertCustomerAuth.getSuccess() < 1) {
				return ResponseEntity.status(insertCustomerAuth.getStatus())
						.body(Map.of("status", insertCustomerAuth.getStatus(), "message",
								"Failed to create customer authentication: " + insertCustomerAuth.getMessage()));
			}

			// Retrieve customer auth ID
			ResponseWrapper<?> customerAuthId = customerServices.getCustomerAuthId(dto);
			if (customerAuthId.getSuccess() < 1 || customerAuthId.getData() == null) {
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
						.body(Map.of("status", HttpStatus.INTERNAL_SERVER_ERROR.value(), "message",
								"Failed to retrieve customer authentication ID."));
			}
			String authId = String.valueOf(customerAuthId.getData());
			dto.setAuthId(authId);

			// Insert customer profile
			ResponseWrapper<?> insertCustomerProfile = customerServices.insertCustomerProfile(dto);
			if (insertCustomerProfile.getSuccess() < 0) {
				return ResponseEntity.status(insertCustomerProfile.getStatus())
						.body(Map.of("status", insertCustomerProfile.getStatus(), "message",
								"Failed to create customer profile: " + insertCustomerProfile.getMessage()));
			}

			// Retrieve customer profile ID
			ResponseWrapper<?> customerProfileId = customerServices.getCustomerProfileId(dto);
			if (customerProfileId.getSuccess() < 1 || customerProfileId.getData() == null) {
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
						.body(Map.of("status", HttpStatus.INTERNAL_SERVER_ERROR.value(), "message",
								"Failed to retrieve customer profile ID."));
			}
			String customerId = String.valueOf(customerProfileId.getData());
			dto.setCustomerId(customerId);

			// Link customer user
			if (customerId.isEmpty()) {
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
						.body(Map.of("status", HttpStatus.INTERNAL_SERVER_ERROR.value(), "message",
								"Customer ID is empty, cannot link customer."));
			}
			ResponseWrapper<?> linkCustomerUser = customerServices.linkCustomerUser(dto);
			if (linkCustomerUser.getSuccess() < 0) {
				return ResponseEntity.status(linkCustomerUser.getStatus())
						.body(Map.of("status", linkCustomerUser.getStatus(), "message",
								"Failed to link customer user: " + linkCustomerUser.getMessage()));
			}

			// Success response
			Map<String, Object> response = new HashMap<>();
			response.put("status", HttpStatus.OK.value());
			response.put("message", "Customer created successfully.");
			return ResponseEntity.ok(response);

		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(Map.of("status", HttpStatus.INTERNAL_SERVER_ERROR.value(), "message",
							"An unexpected error occurred: " + e.getMessage()));
		}
	}

	@GetMapping("/list")
	public ResponseEntity<?> getCustomersList(@RequestBody CustomerDto dto, BindingResult bindingResult) {

		if (bindingResult.hasErrors()) {
			String errorMessage = bindingResult.getFieldErrors().stream()
					.map(error -> error.getDefaultMessage().toString()).collect(Collectors.joining(", "));

			Map<String, Object> response = new HashMap<>();
			response.put("status", HttpStatus.BAD_REQUEST.value());
			response.put("message", errorMessage);
		}

		try {

			ResponseWrapper<?> DBresponse = customerServices.getCustomersList(dto);

			Map<String, Object> responseObj = new HashMap<>();
			responseObj.put("status", DBresponse.getStatus());
			responseObj.put("message", DBresponse.getMessage());

			if (DBresponse.getSuccess() >= 1) {
				responseObj.put("data", DBresponse.getData());
				return ResponseEntity.status(DBresponse.getStatus()).body(responseObj);
			} else if (DBresponse.getSuccess() == 0) {
				return ResponseEntity.status(DBresponse.getStatus()).body(responseObj);
			} else {
				return ResponseEntity.status(DBresponse.getStatus()).body(responseObj);
			}

		} catch (Exception e) {
			e.printStackTrace();

			Map<String, Object> erroMessage = new HashMap<>();
			erroMessage.put("status", ResponseDetails.INTERNAL_SERVER_ERROR_HttpStatusCode);
			erroMessage.put("message", ResponseDetails.INTERNAL_SERVER_ERROR_ResponseMessage);

			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(erroMessage);
		}
	}

	@GetMapping("/details")
	public ResponseEntity<?> getCustomerById(@RequestBody CustomerDto dto, BindingResult bindingResult) {

		if (bindingResult.hasErrors()) {
			String errorMessage = bindingResult.getFieldErrors().stream()
					.map(error -> error.getDefaultMessage().toString()).collect(Collectors.joining(", "));

			Map<String, Object> response = new HashMap<>();
			response.put("status", HttpStatus.BAD_REQUEST.value());
			response.put("message", errorMessage);
		}

		try {

			ResponseWrapper<?> DBresponse = customerServices.getCustomerById(dto);

			Map<String, Object> responseObj = new HashMap<>();
			responseObj.put("status", DBresponse.getStatus());
			responseObj.put("message", DBresponse.getMessage());

			if (DBresponse.getSuccess() >= 1) {
				responseObj.put("data", DBresponse.getData());
				return ResponseEntity.status(DBresponse.getStatus()).body(responseObj);
			} else if (DBresponse.getSuccess() == 0) {
				return ResponseEntity.status(DBresponse.getStatus()).body(responseObj);
			} else {
				return ResponseEntity.status(DBresponse.getStatus()).body(responseObj);
			}

		} catch (Exception e) {
			e.printStackTrace();

			Map<String, Object> erroMessage = new HashMap<>();
			erroMessage.put("status", ResponseDetails.INTERNAL_SERVER_ERROR_HttpStatusCode);
			erroMessage.put("message", ResponseDetails.INTERNAL_SERVER_ERROR_ResponseMessage);

			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(erroMessage);
		}
	}

	@PutMapping("/update")
	public ResponseEntity<?> editCustomer(@RequestBody CustomerDto dto, BindingResult bindingResult) {

		if (bindingResult.hasErrors()) {
			String errorMessage = bindingResult.getFieldErrors().stream().map(error -> error.getDefaultMessage())
					.collect(Collectors.joining(", "));

			Map<String, Object> response = new HashMap<>();
			response.put("status", HttpStatus.BAD_REQUEST.value());
			response.put("message", errorMessage);
		}

		try {

			ResponseWrapper<?> DBresponse = customerServices.editCustomer(dto);

			Map<String, Object> responseObj = new HashMap<>();
			responseObj.put("status", DBresponse.getStatus());
			responseObj.put("message", DBresponse.getMessage());

			if (DBresponse.getSuccess() >= 1) {
				return ResponseEntity.status(DBresponse.getStatus()).body(responseObj);
			} else if (DBresponse.getSuccess() == 0) {
				return ResponseEntity.status(DBresponse.getStatus()).body(responseObj);
			} else {
				return ResponseEntity.status(DBresponse.getStatus()).body(responseObj);
			}
		} catch (Exception e) {
			e.printStackTrace();

			Map<String, Object> erroMessage = new HashMap<>();
			erroMessage.put("status", ResponseDetails.INTERNAL_SERVER_ERROR_HttpStatusCode);
			erroMessage.put("message", ResponseDetails.INTERNAL_SERVER_ERROR_ResponseMessage);

			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(erroMessage);
		}

	}

	@PutMapping("/updateStatus")
	public ResponseEntity<?> updateCustomerStatus(@RequestBody CustomerDto dto, BindingResult bindingResult) {

		if (bindingResult.hasErrors()) {
			String errorMessage = bindingResult.getFieldErrors().stream().map(error -> error.getDefaultMessage())
					.collect(Collectors.joining(", "));

			Map<String, Object> response = new HashMap<>();
			response.put("status", HttpStatus.BAD_REQUEST.value());
			response.put("message", errorMessage);
		}

		try {

			ResponseWrapper<?> DBresponse = customerServices.updateCustomerStatus(dto);

			Map<String, Object> responseObj = new HashMap<>();
			responseObj.put("status", DBresponse.getStatus());
			responseObj.put("message", DBresponse.getMessage());

			if (DBresponse.getSuccess() >= 1) {
				return ResponseEntity.status(DBresponse.getStatus()).body(responseObj);
			} else if (DBresponse.getSuccess() == 0) {
				return ResponseEntity.status(DBresponse.getStatus()).body(responseObj);
			} else {
				return ResponseEntity.status(DBresponse.getStatus()).body(responseObj);
			}
		} catch (Exception e) {
			e.printStackTrace();

			Map<String, Object> erroMessage = new HashMap<>();
			erroMessage.put("status", ResponseDetails.INTERNAL_SERVER_ERROR_HttpStatusCode);
			erroMessage.put("message", ResponseDetails.INTERNAL_SERVER_ERROR_ResponseMessage);

			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(erroMessage);
		}

	}

	@GetMapping("/order/history")
	public ResponseEntity<?> getCustomerOrderHistory(@RequestBody CustomerDto dto, BindingResult bindingResult) {

		if (bindingResult.hasErrors()) {
			String errorMessage = bindingResult.getFieldErrors().stream().map(error -> error.getDefaultMessage())
					.collect(Collectors.joining(", "));

			Map<String, Object> response = new HashMap<>();
			response.put("status", HttpStatus.BAD_REQUEST.value());
			response.put("message", errorMessage);
		}

		try {
			ResponseWrapper<?> DBresponse = customerServices.getCustomerOrderHistory(dto);

			Map<String, Object> responseObj = new HashMap<>();
			responseObj.put("status", DBresponse.getStatus());
			responseObj.put("message", DBresponse.getMessage());

			if (DBresponse.getSuccess() >= 1) {
				responseObj.put("data", DBresponse.getData());
				return ResponseEntity.status(DBresponse.getStatus()).body(responseObj);
			} else if (DBresponse.getSuccess() == 0) {
				return ResponseEntity.status(DBresponse.getStatus()).body(responseObj);
			} else {
				return ResponseEntity.status(DBresponse.getStatus()).body(responseObj);
			}

		} catch (Exception e) {
			e.printStackTrace();

			Map<String, Object> erroMessage = new HashMap<>();
			erroMessage.put("status", ResponseDetails.INTERNAL_SERVER_ERROR_HttpStatusCode);
			erroMessage.put("message", ResponseDetails.INTERNAL_SERVER_ERROR_ResponseMessage);

			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(erroMessage);
		}
	}

}
