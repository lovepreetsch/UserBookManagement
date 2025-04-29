package com.bookManagement.net.services;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bookManagement.net.beans.BookBeans;
import com.bookManagement.net.beans.OrderBeans;
import com.bookManagement.net.beans.ResponseWrapper;
import com.bookManagement.net.dao.BookDao;
import com.bookManagement.net.dao.OrderDao;
import com.bookManagement.net.dto.OrderDto;
import com.bookManagement.net.util.ResponseDetails;

@Service
public class OrderService {

	@Autowired
	private OrderDao orderDao;

	@Autowired
	private BookDao bookDao;

	public ResponseWrapper<?> createOrder(OrderDto dto) {
		try {
			BookBeans bookBeans = new BookBeans();
			bookBeans.setId(dto.getBookId());

			List<Map<String, Object>> response = bookDao.getbookById(bookBeans);

			if (response == null || response.isEmpty()) {
				return new ResponseWrapper<>(0, ResponseDetails.NOT_FOUND_HttpStatusCode, "Book not found");
			}

			Map<String, Object> bookMap = response.get(0);

			Object quantityObj = bookMap.get("quantity");
			Object priceObj = bookMap.get("price");

			int availableQuantity;
			double price;

			try {
				if (quantityObj instanceof Integer) {
					availableQuantity = (Integer) quantityObj;
				} else if (quantityObj instanceof String) {
					availableQuantity = Integer.parseInt((String) quantityObj);
				} else {
					throw new IllegalArgumentException("Invalid quantity data type");
				}

				if (priceObj instanceof Float) {
					price = (Float) priceObj;
				} else if (priceObj instanceof String) {
					price = Double.parseDouble((String) priceObj);
				} else if (priceObj instanceof Integer) {
					price = ((Integer) priceObj).doubleValue();
				} else {
					throw new IllegalArgumentException("Invalid price data type");
				}

			} catch (Exception e) {
				e.printStackTrace();
				return new ResponseWrapper<>(0, ResponseDetails.BAD_REQUEST_HttpStatusCode,
						"Invalid quantity or price format");
			}

			int selectedQuantity;
			try {
				selectedQuantity = Integer.parseInt(dto.getQuantity());
			} catch (NumberFormatException e) {
				return new ResponseWrapper<>(0, ResponseDetails.BAD_REQUEST_HttpStatusCode,
						"Invalid selected quantity format");
			}

			if (availableQuantity < selectedQuantity) {
				return new ResponseWrapper<>(0, ResponseDetails.BAD_REQUEST_HttpStatusCode,
						"Not enough stock available");
			}

			String totalAmount = String.valueOf(price * selectedQuantity);

			OrderBeans order = new OrderBeans();
			order.setBookId(dto.getBookId());
			order.setCustomerId(dto.getCustomerId());
			order.setQuantity(dto.getQuantity());
			order.setTotalAmount(totalAmount);

			orderDao.createOrder(order);

			int remainingQuantity = availableQuantity - selectedQuantity;
			bookBeans.setQuantity(String.valueOf(remainingQuantity));
			bookDao.updateBookQuantity(bookBeans);

			return new ResponseWrapper<>(1, ResponseDetails.OK_HttpStatusCode, "Order placed successfully");

		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseWrapper<>(0, ResponseDetails.INTERNAL_SERVER_ERROR_HttpStatusCode,
					"Something went wrong while creating order");
		}
	}

	public ResponseWrapper<?> orderReview(OrderDto dto) {

		try {
			OrderBeans beans = new OrderBeans();
			beans.setOrderId(dto.getOrderId());
			beans.setRating(dto.getRating());
			beans.setReviewDescription(dto.getReviewDescription());

			int rowsAffected = orderDao.orderReview(beans);

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

	public ResponseWrapper<?> getOrderById(OrderDto dto) {

		try {
			OrderBeans beans = new OrderBeans();
			beans.setOrderId(dto.getOrderId());
			beans.setCustomerId(dto.getCustomerId());

			List<Map<String, Object>> response = orderDao.getOrderById(beans);

			if (response.isEmpty()) {
				return new ResponseWrapper<>(0, ResponseDetails.INTERNAL_SERVER_ERROR_HttpStatusCode,
						"No details found");
			} else {
				return new ResponseWrapper<>(1, "Order details", ResponseDetails.OK_HttpStatusCode, response);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseWrapper<>(-1, ResponseDetails.INTERNAL_SERVER_ERROR_HttpStatusCode,
					"An error occurred while logging: " + e.getMessage());
		}
	}

}
