package com.bookManagement.net.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.bookManagement.net.beans.OrderBeans;
import com.bookManagement.net.util.UtilityClass;

@Repository
public class OrderDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	public int createOrder(OrderBeans beans) {

		StringBuilder createQuery = new StringBuilder();
		createQuery.append(" INSERT INTO orders ").append(" ( book_id, customer_id, quantity, total_amount ) ")
				.append(" VALUES( ?, ?, ?, ? ) ");

		return jdbcTemplate.update(createQuery.toString(), beans.getBookId(), beans.getCustomerId(),
				beans.getQuantity(), beans.getTotalAmount());
	}

	public int orderReview(OrderBeans beans) {
		StringBuilder insertQuery = new StringBuilder();
		insertQuery.append(" INSERT INTO order_review ").append(" ( order_id, customer_id, rating, comment ) ")
				.append(" VALUES( ?, ?, ?, ? ) ");

		return jdbcTemplate.update(insertQuery.toString(), beans.getOrderId(), beans.getCustomerId(), beans.getRating(),
				beans.getComment());
	}

	public List<Map<String, Object>> existingReviewForCustomerId(OrderBeans beans) {

		StringBuilder query = new StringBuilder();

		query.append("SELECT rating, comment FROM order_review ");
		List<Object> params = new ArrayList<>();

		boolean conditionAdded = false;

		if (!UtilityClass.isNull(beans.getOrderId())) {
			query.append(conditionAdded ? " AND " : " WHERE ").append(" order_id = ? ");
			params.add(beans.getOrderId());
			conditionAdded = true;
		}

		if (!UtilityClass.isNull(beans.getCustomerId())) {
			query.append(conditionAdded ? " AND " : " WHERE ").append(" customer_id = ? ");
			params.add(beans.getCustomerId());
			conditionAdded = true;
		}

		List<Map<String, Object>> authResult = jdbcTemplate.queryForList(query.toString(), params.toArray());

		return authResult;

	}

	@SuppressWarnings("deprecation")
	public List<Map<String, Object>> getOrderById(OrderBeans beans) {

		StringBuilder query = new StringBuilder();
		query.append(
				"SELECT ord.id AS orderId, ord.book_id AS bookId, ord.quantity AS orderQuantity, ord.total_amount AS totalAmount , "
						+ " bk.title AS bookTitle , bk.description AS bookDescription, bk.author AS bookAuthor, bk.price AS bookPrice, "
						+ " orv.rating AS orderRating, orv.comment AS comment FROM orders ord ")
				.append(" LEFT JOIN customer_profile cp ON ord.customer_id = cp.id ")
				.append(" LEFT JOIN book bk ON bk.id = ord.book_id ")
				.append(" LEFT JOIN order_review orv ON orv.order_id = ord.id ").append(" WHERE ");

		List<Object> params = new ArrayList<>();

		boolean conditionAdded = false;

		if (!UtilityClass.isNull(beans.getOrderId())) {
			if (conditionAdded) {
				query.append(" AND ");
			}

			query.append(" ord.id = ? ");
			params.add(beans.getOrderId());
			conditionAdded = true;
		}

		if (!UtilityClass.isNull(beans.getCustomerId())) {
			if (conditionAdded) {
				query.append(" AND ");
			}

			query.append(" ord.customer_id = ? ");
			params.add(beans.getCustomerId());
			conditionAdded = true;
		}

		return jdbcTemplate.query(query.toString(), params.toArray(), (rs, rowNum) -> {
			Map<String, Object> map = new HashMap<>();
			map.put("orderId", rs.getObject("orderId"));
			map.put("bookId", rs.getObject("bookId"));
			map.put("quantity", rs.getObject("orderQuantity"));
			map.put("totalAmount", rs.getObject("totalAmount"));
			map.put("bookTitle", rs.getObject("bookTitle"));
			map.put("bookDescription", rs.getObject("bookDescription"));
			map.put("bookAuthor", rs.getObject("bookAuthor"));
			map.put("bookPrice", rs.getObject("bookPrice"));
			map.put("rating", rs.getObject("orderRating"));
			map.put("comment", rs.getObject("comment"));
			return map;
		});

	}

	@SuppressWarnings("deprecation")
	public int getOrdersCount(OrderBeans beans) {
		StringBuilder query = new StringBuilder();
		query.append("SELECT COUNT(*) AS total FROM orders ord \r\n" + "LEFT JOIN book b ON b.id = ord.book_id\r\n"
				+ "LEFT JOIN user_profile up ON up.id = b.user_id\r\n" + "WHERE ");

		List<Object> params = new ArrayList<>();
		boolean conditionAdded = false;

		if (!UtilityClass.isNull(beans.getUserId())) {
			query.append(conditionAdded ? " AND " : "").append(" user_id = ? ");
			params.add(beans.getUserId());
			conditionAdded = true;
		}

		if (!conditionAdded) {
			return 0;
		}

		try {
			List<Integer> result = jdbcTemplate.query(query.toString(), params.toArray(),
					(rs, rowNum) -> rs.getInt("total"));

			return result.isEmpty() ? 0 : result.get(0);
		} catch (Exception ex) {
			ex.printStackTrace();
			return 0;
		}
	}

	@SuppressWarnings("deprecation")
	public int getOrdersRevenue(OrderBeans beans) {
		StringBuilder query = new StringBuilder();
		query.append("SELECT SUM(ord.total_amount) AS totalRevenue FROM orders ord \r\n"
				+ "LEFT JOIN book b ON b.id = ord.book_id\r\n" + "LEFT JOIN user_profile up ON up.id = b.user_id\r\n"
				+ "WHERE ");

		List<Object> params = new ArrayList<>();
		boolean conditionAdded = false;

		if (!UtilityClass.isNull(beans.getUserId())) {
			query.append(conditionAdded ? " AND " : "").append(" user_id = ? ");
			params.add(beans.getUserId());
			conditionAdded = true;
		}

		if (!conditionAdded) {
			return 0;
		}

		try {
			List<Integer> result = jdbcTemplate.query(query.toString(), params.toArray(),
					(rs, rowNum) -> rs.getInt("totalRevenue"));

			return result.isEmpty() ? 0 : result.get(0);
		} catch (Exception ex) {
			ex.printStackTrace();
			return 0;
		}
	}
}
