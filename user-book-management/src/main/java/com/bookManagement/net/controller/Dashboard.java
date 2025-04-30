package com.bookManagement.net.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bookManagement.net.beans.ResponseWrapper;
import com.bookManagement.net.dto.BookDto;
import com.bookManagement.net.services.DashboardServices;
import com.bookManagement.net.util.ResponseDetails;

@RestController
@RequestMapping("v1/dashboard")
public class Dashboard {

	@Autowired
	private DashboardServices dashboardServices;

	@GetMapping("/list")
	public ResponseEntity<?> userDashboard(@RequestBody BookDto dto) {

		try {

			ResponseWrapper<?> DBresponse = dashboardServices.getBooksCount(dto);

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
