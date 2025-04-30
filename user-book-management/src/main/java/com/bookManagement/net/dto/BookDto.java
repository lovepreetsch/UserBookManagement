package com.bookManagement.net.dto;

import com.bookManagement.net.services.DetailValidationGroup;
import com.bookManagement.net.services.FullValidationGroup;
import com.bookManagement.net.services.MinimalValidationGroup;
import com.bookManagement.net.services.ReviewValidationGroup;
import com.bookManagement.net.services.UpdateStatusValidationGroup;
import com.bookManagement.net.services.UpdateValidationGroup;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class BookDto {

	@NotBlank(message = "Book Id is required", groups = { DetailValidationGroup.class, UpdateValidationGroup.class,
			UpdateStatusValidationGroup.class, ReviewValidationGroup.class })
	private String id;

	@NotBlank(message = "User Id is required", groups = { FullValidationGroup.class, MinimalValidationGroup.class,
			UpdateValidationGroup.class, UpdateStatusValidationGroup.class })
	private String userId;

	@NotBlank(message = "Customer Id is required", groups = ReviewValidationGroup.class)
	private String customerId;
	private String orderId;

	@NotBlank(message = "Title is required", groups = { FullValidationGroup.class, UpdateValidationGroup.class })
	private String title;

	@NotBlank(message = "Description is required", groups = { FullValidationGroup.class, UpdateValidationGroup.class })
	private String description;

	@NotBlank(message = "Author is required", groups = { FullValidationGroup.class, UpdateValidationGroup.class })
	private String author;

	@NotBlank(message = "Price can not be null", groups = { FullValidationGroup.class, UpdateValidationGroup.class })
	private String price;

	@NotBlank(message = "Quantity is required", groups = { FullValidationGroup.class, UpdateValidationGroup.class })
	private String quantity;

	@NotBlank(message = "Please enter your rating", groups = ReviewValidationGroup.class)
	private String rating;
	private String comment;

	@NotNull(message = "Active status is required", groups = UpdateStatusValidationGroup.class)
	private Boolean activeStatus;

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

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getQuantity() {
		return quantity;
	}

	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}

	public Boolean getActiveStatus() {
		return activeStatus;
	}

	public void setActiveStatus(Boolean activeStatus) {
		this.activeStatus = activeStatus;
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
