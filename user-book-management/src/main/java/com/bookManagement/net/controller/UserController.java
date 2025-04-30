package com.bookManagement.net.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bookManagement.net.beans.ResponseWrapper;
import com.bookManagement.net.dto.UserDto;
import com.bookManagement.net.services.DetailValidationGroup;
import com.bookManagement.net.services.FullValidationGroup;
import com.bookManagement.net.services.UpdateStatusValidationGroup;
import com.bookManagement.net.services.UpdateValidationGroup;
import com.bookManagement.net.services.UserServices;
import com.bookManagement.net.util.ResponseDetails;

@RestController
@RequestMapping("v1/user")
public class UserController {

	@Autowired
	private UserServices userServices;

	@PostMapping("/signup")
	public ResponseEntity<?> userSignup(@Validated(FullValidationGroup.class) @RequestBody UserDto dto) {

		if (!"PROVIDER".equalsIgnoreCase(dto.getRole()) && "CUSTOMER".equalsIgnoreCase(dto.getRole())) {
			return ResponseEntity.badRequest().body(Map.of("status", HttpStatus.BAD_REQUEST.value(), "message",
					"Invalid role. Role must be PROVIDER."));
		}

		try {
			ResponseWrapper<?> existingUser = userServices.getExistingUsers(dto);
			if (existingUser.getSuccess() != 0) {
				return ResponseEntity.status(existingUser.getStatus())
						.body(Map.of("status", existingUser.getStatus(), "message", existingUser.getMessage()));
			}

			ResponseWrapper<?> signupResponse = userServices.userSignup(dto);
			if (signupResponse.getSuccess() <= 0) {
				return ResponseEntity.status(signupResponse.getStatus())
						.body(Map.of("status", signupResponse.getStatus(), "message", signupResponse.getMessage()));
			}

			userServices.userAuth(dto);

			return ResponseEntity.status(signupResponse.getStatus())
					.body(Map.of("status", signupResponse.getStatus(), "message", signupResponse.getMessage()));

		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(Map.of("status", ResponseDetails.INTERNAL_SERVER_ERROR_HttpStatusCode, "message",
							ResponseDetails.INTERNAL_SERVER_ERROR_ResponseMessage));
		}
	}

	@GetMapping("/login")
	public ResponseEntity<?> userLogin(@RequestBody UserDto dto) {

		try {
			ResponseWrapper<?> DBresponse = userServices.loginUser(dto);

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
	public ResponseEntity<?> getUserById(@Validated(DetailValidationGroup.class) @RequestBody UserDto dto) {

		try {

			ResponseWrapper<?> DBresponse = userServices.getUserById(dto);

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
	public ResponseEntity<?> editUser(@Validated(UpdateValidationGroup.class) @RequestBody UserDto dto) {

		try {

			ResponseWrapper<?> DBresponse = userServices.editUser(dto);

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
	public ResponseEntity<?> updateUserStatus(@Validated(UpdateStatusValidationGroup.class) @RequestBody UserDto dto) {

		try {

			ResponseWrapper<?> DBresponse = userServices.updateUserStatus(dto);

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

	@PutMapping("/password/reset")
	public ResponseEntity<?> resetPassword(@RequestBody UserDto dto) {

		try {

			ResponseWrapper<?> DBresponse = userServices.resetPassword(dto);

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

}
