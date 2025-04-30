package com.bookManagement.net.dao;

public class DashboardCountDto {
	private int bookCount;
	private int orderCount;
	private int totalRevenue;
	private int totalBooksSold;

	public DashboardCountDto() {
	}

	public DashboardCountDto(int bookCount, int orderCount, int totalRevenue, int totalBooksSold) {
		this.bookCount = bookCount;
		this.orderCount = orderCount;
		this.totalRevenue = totalRevenue;
		this.totalBooksSold = totalBooksSold;
	}

	public int getBookCount() {
		return bookCount;
	}

	public void setBookCount(int bookCount) {
		this.bookCount = bookCount;
	}

	public int getOrderCount() {
		return orderCount;
	}

	public void setOrderCount(int orderCount) {
		this.orderCount = orderCount;
	}

	public int getTotalRevenue() {
		return totalRevenue;
	}

	public void setTotalRevenue(int totalRevenue) {
		this.totalRevenue = totalRevenue;
	}

	public int getTotalBooksSold() {
		return totalBooksSold;
	}

	public void setTotalBooksSold(int totalBooksSold) {
		this.totalBooksSold = totalBooksSold;
	}

}