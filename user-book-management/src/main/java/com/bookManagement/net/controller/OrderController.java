package com.bookManagement.net.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bookManagement.net.beans.ResponseWrapper;
import com.bookManagement.net.dto.OrderDto;
import com.bookManagement.net.services.OrderService;
import com.bookManagement.net.util.ResponseDetails;

@RestController
@RequestMapping("v1/order")
public class OrderController {

	@Autowired
	private OrderService orderService;

	@PostMapping("/createOrder")
	public ResponseEntity<?> createOrder(@RequestBody OrderDto dto, BindingResult bindingResult) {

		if (bindingResult.hasErrors()) {
			String errorMessage = bindingResult.getFieldErrors().stream()
					.map(error -> error.getDefaultMessage().toString()).collect(Collectors.joining(", "));

			Map<String, Object> response = new HashMap<>();
			response.put("status", HttpStatus.BAD_REQUEST.value());
			response.put("message", errorMessage);
		}

		try {

			ResponseWrapper<?> DBresponse = orderService.createOrder(dto);

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

	@PostMapping("/review")
	public ResponseEntity<?> orderReview(@RequestBody OrderDto dto, BindingResult bindingResult) {

		if (bindingResult.hasErrors()) {
			String errorMessage = bindingResult.getFieldErrors().stream()
					.map(error -> error.getDefaultMessage().toString()).collect(Collectors.joining(", "));

			Map<String, Object> response = new HashMap<>();
			response.put("status", HttpStatus.BAD_REQUEST.value());
			response.put("message", errorMessage);
		}

		try {

			ResponseWrapper<?> DBresponse = orderService.orderReview(dto);

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

	@GetMapping("/details")
	public ResponseEntity<?> getOrderById(@RequestBody OrderDto dto, BindingResult bindingResult) {

		if (bindingResult.hasErrors()) {
			String errorMessage = bindingResult.getFieldErrors().stream()
					.map(error -> error.getDefaultMessage().toString()).collect(Collectors.joining(", "));

			Map<String, Object> response = new HashMap<>();
			response.put("status", HttpStatus.BAD_REQUEST.value());
			response.put("message", errorMessage);
		}

		try {

			ResponseWrapper<?> DBresponse = orderService.getOrderById(dto);

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
