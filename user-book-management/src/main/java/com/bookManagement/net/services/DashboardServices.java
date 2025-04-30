package com.bookManagement.net.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bookManagement.net.beans.BookBeans;
import com.bookManagement.net.beans.OrderBeans;
import com.bookManagement.net.beans.ResponseWrapper;
import com.bookManagement.net.dao.BookDao;
import com.bookManagement.net.dao.DashboardCountDto;
import com.bookManagement.net.dao.OrderDao;
import com.bookManagement.net.dto.BookDto;
import com.bookManagement.net.util.ResponseDetails;

@Service
public class DashboardServices {

	@Autowired
	private BookDao bookDao;

	@Autowired
	private OrderDao orderDao;

	public ResponseWrapper<?> getBooksCount(BookDto dto) {
		try {
			BookBeans bookBeans = new BookBeans();
			bookBeans.setUserId(dto.getUserId());
			bookBeans.setActiveStatus(dto.getActiveStatus());

			OrderBeans orderBeans = new OrderBeans();
			orderBeans.setUserId(dto.getUserId());

			int bookCount = bookDao.getBooksCount(bookBeans);
			int orderCount = orderDao.getOrdersCount(orderBeans);
			int totalRevenue = orderDao.getOrdersRevenue(orderBeans);
			int totalBooksSold = bookDao.totalBooksSold(bookBeans);

			DashboardCountDto dashboardCount = new DashboardCountDto(bookCount, orderCount, totalRevenue,
					totalBooksSold);

			return new ResponseWrapper<>(1, "Data retrieved successfully", ResponseDetails.OK_HttpStatusCode,
					dashboardCount);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseWrapper<>(-1, "An error occurred: " + e.getMessage(),
					ResponseDetails.INTERNAL_SERVER_ERROR_HttpStatusCode, "Database error or exception");
		}
	}

}
