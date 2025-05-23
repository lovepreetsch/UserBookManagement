package com.bookManagement.net.dto;

import com.bookManagement.net.services.DetailValidationGroup;
import com.bookManagement.net.services.ReviewValidationGroup;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class OrderDto {

	private String id;
	private String userId;
	private String bookId;
	private String customerId;

	@NotBlank(message = "Order Id is required", groups = { ReviewValidationGroup.class, DetailValidationGroup.class })
	private String orderId;
	private String quantity;
	private String totalAmount;

	@NotBlank(message = "Please enter your rating", groups = ReviewValidationGroup.class)
	private String rating;
	private String comment;

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

	public String getBookId() {
		return bookId;
	}

	public void setBookId(String bookId) {
		this.bookId = bookId;
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getQuantity() {
		return quantity;
	}

	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}

	public String getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(String totalAmount) {
		this.totalAmount = totalAmount;
	}

	public String getRating() {
		return rating;
	}

	public void setRating(String rating) {
		this.rating = rating;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

}
