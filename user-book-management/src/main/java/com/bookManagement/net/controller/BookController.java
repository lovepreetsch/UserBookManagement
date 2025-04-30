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
import com.bookManagement.net.dto.BookDto;
import com.bookManagement.net.services.BookServices;
import com.bookManagement.net.services.DetailValidationGroup;
import com.bookManagement.net.services.FullValidationGroup;
import com.bookManagement.net.services.MinimalValidationGroup;
import com.bookManagement.net.services.ReviewValidationGroup;
import com.bookManagement.net.services.UpdateStatusValidationGroup;
import com.bookManagement.net.services.UpdateValidationGroup;
import com.bookManagement.net.util.ResponseDetails;

@RestController
@RequestMapping("v1/book")
public class BookController {

	@Autowired
	private BookServices bookServices;

	@PostMapping("/add")
	public ResponseEntity<?> addBook(@Validated(FullValidationGroup.class) @RequestBody BookDto dto) {

		try {
			ResponseWrapper<?> DBresponse = bookServices.addBook(dto);

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

	@GetMapping("/list")
	public ResponseEntity<?> getbooksList(@Validated(MinimalValidationGroup.class) @RequestBody BookDto dto) {

		try {
			ResponseWrapper<?> DBresponse = bookServices.getbooksList(dto);

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
	public ResponseEntity<?> getbookById(@Validated(DetailValidationGroup.class) @RequestBody BookDto dto) {

		try {
			ResponseWrapper<?> DBresponse = bookServices.getbookById(dto);

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
	public ResponseEntity<?> updateBook(@Validated(UpdateValidationGroup.class) @RequestBody BookDto dto) {

		try {

			ResponseWrapper<?> DBresponse = bookServices.updateBook(dto);

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
	public ResponseEntity<?> updateBookStatus(@Validated(UpdateStatusValidationGroup.class) @RequestBody BookDto dto) {

		try {

			ResponseWrapper<?> DBresponse = bookServices.updateBookStatus(dto);

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
	public ResponseEntity<?> bookReview(@Validated(ReviewValidationGroup.class) @RequestBody BookDto dto) {

		try {

			ResponseWrapper<?> alreadyReviewed = bookServices.existingReviewForCustomerId(dto);
			if (alreadyReviewed.getSuccess() != 0) {
				return ResponseEntity.status(alreadyReviewed.getStatus())
						.body(Map.of("status", alreadyReviewed.getStatus(), "message", alreadyReviewed.getMessage()));
			}

			ResponseWrapper<?> DBresponse = bookServices.bookReview(dto);

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
