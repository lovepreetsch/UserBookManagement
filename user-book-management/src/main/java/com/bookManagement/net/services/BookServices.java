package com.bookManagement.net.services;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bookManagement.net.beans.BookBeans;
import com.bookManagement.net.beans.ResponseWrapper;
import com.bookManagement.net.dao.BookDao;
import com.bookManagement.net.dto.BookDto;
import com.bookManagement.net.util.ResponseDetails;

@Service
public class BookServices {

	@Autowired
	private BookDao dao;

	public ResponseWrapper<?> addBook(BookDto dto) {

		try {
			BookBeans beans = new BookBeans();
			beans.setTitle(dto.getTitle());
			beans.setDescription(dto.getDescription());
			beans.setAuthor(dto.getAuthor());
			beans.setPrice(dto.getPrice());
			beans.setQuantity(dto.getQuantity());
			beans.setUserId(dto.getUserId());

			int rowsAffected = dao.addBook(beans);

			if (rowsAffected >= 1) {
				return new ResponseWrapper<String>(1, ResponseDetails.OK_HttpStatusCode, "Book added");
			} else if (rowsAffected == 0) {
				return new ResponseWrapper<String>(0, "Book could not be added",
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

	public ResponseWrapper<?> getbooksList(BookDto dto) {

		try {
			BookBeans beans = new BookBeans();
			beans.setUserId(dto.getUserId());

			List<Map<String, Object>> response = dao.getbooksList(beans);

			if (response.isEmpty()) {
				return new ResponseWrapper<>(0, ResponseDetails.INTERNAL_SERVER_ERROR_HttpStatusCode, "No Books found");
			} else {
				return new ResponseWrapper<>(1, "Books list", ResponseDetails.OK_HttpStatusCode, response);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseWrapper<>(-1, ResponseDetails.INTERNAL_SERVER_ERROR_HttpStatusCode,
					"An error occurred: " + e.getMessage());
		}
	}

	public ResponseWrapper<?> getbookById(BookDto dto) {

		try {
			BookBeans beans = new BookBeans();
			beans.setId(dto.getId());

			List<Map<String, Object>> response = dao.getbookById(beans);

			if (response.isEmpty()) {
				return new ResponseWrapper<>(0, ResponseDetails.INTERNAL_SERVER_ERROR_HttpStatusCode,
						"No details found");
			} else {
				return new ResponseWrapper<>(1, "Books details", ResponseDetails.OK_HttpStatusCode, response);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseWrapper<>(-1, ResponseDetails.INTERNAL_SERVER_ERROR_HttpStatusCode,
					"An error occurred: " + e.getMessage());
		}
	}

	public ResponseWrapper<?> updateBook(BookDto dto) {

		try {
			BookBeans beans = new BookBeans();
			beans.setTitle(dto.getTitle());
			beans.setDescription(dto.getDescription());
			beans.setAuthor(dto.getAuthor());
			beans.setPrice(dto.getPrice());
			beans.setQuantity(dto.getQuantity());
			beans.setUserId(dto.getUserId());
			beans.setId(dto.getId());

			int rowsAffected = dao.updateBook(beans);

			if (rowsAffected >= 1) {
				return new ResponseWrapper<String>(1, ResponseDetails.OK_HttpStatusCode, "Book updated");
			} else if (rowsAffected == 0) {
				return new ResponseWrapper<String>(0, "Book could not be update",
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

	public ResponseWrapper<?> updateBookStatus(BookDto dto) {

		try {
			BookBeans beans = new BookBeans();
			beans.setActiveStatus(dto.getActiveStatus());
			beans.setUserId(dto.getUserId());
			beans.setId(dto.getId());

			int rowsAffected = dao.updateBookStatus(beans);

			if (rowsAffected >= 1) {
				return new ResponseWrapper<String>(1, ResponseDetails.OK_HttpStatusCode, "Status updated");
			} else if (rowsAffected == 0) {
				return new ResponseWrapper<String>(0, "Status could not be update",
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

	public ResponseWrapper<?> bookReview(BookDto dto) {

		try {
			BookBeans beans = new BookBeans();
			beans.setId(dto.getId());
			beans.setUserId(dto.getUserId());
			beans.setRating(dto.getRating());
			beans.setComment(dto.getComment());

			int rowsAffected = dao.bookReview(beans);

			if (rowsAffected >= 1) {
				return new ResponseWrapper<String>(1, ResponseDetails.OK_HttpStatusCode, "Review added successfully");
			} else if (rowsAffected == 0) {
				return new ResponseWrapper<String>(0, "Failed to add review",
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

	public ResponseWrapper<?> existingReviewForCustomerId(BookDto dto) {

		try {
			BookBeans beans = new BookBeans();
			beans.setId(dto.getId());
			beans.setCustomerId(dto.getCustomerId());

			List<Map<String, Object>> response = dao.existingReviewForCustomerId(beans);

			if (response.isEmpty()) {
				return new ResponseWrapper<>(0, ResponseDetails.INTERNAL_SERVER_ERROR_HttpStatusCode,
						"No review found");
			} else {
				return new ResponseWrapper<>(1, "You’ve already reviewed this book", ResponseDetails.OK_HttpStatusCode,
						response);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseWrapper<>(-1, ResponseDetails.INTERNAL_SERVER_ERROR_HttpStatusCode,
					"An error occurred: " + e.getMessage());
		}
	}

}
