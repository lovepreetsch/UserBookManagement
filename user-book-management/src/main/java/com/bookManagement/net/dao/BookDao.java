package com.bookManagement.net.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.bookManagement.net.beans.BookBeans;
import com.bookManagement.net.util.UtilityClass;

@Repository
public class BookDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	public int addBook(BookBeans beans) {

		StringBuilder insertQuery = new StringBuilder();
		insertQuery.append("INSERT INTO book " + " ( title, description , author, price, quantity, user_id ) "
				+ " VALUES ( ?, ?, ?, ?, ? , ? ) ");

		return jdbcTemplate.update(insertQuery.toString(), beans.getTitle(), beans.getDescription(), beans.getAuthor(),
				beans.getPrice(), beans.getQuantity(), beans.getUserId());
	}

	public List<Map<String, Object>> getbooksList(BookBeans beans) {

		StringBuilder query = new StringBuilder();

		query.append("SELECT id as bookId , title, description, author, price, quantity FROM book ");
		List<Object> params = new ArrayList<>();

		boolean conditionAdded = false;

		if (!UtilityClass.isNull(beans.getUserId())) {
			query.append(conditionAdded ? " AND " : " WHERE ").append(" user_id = ? ");
			params.add(beans.getUserId());
			conditionAdded = true;
		}

		List<Map<String, Object>> authResult = jdbcTemplate.queryForList(query.toString(), params.toArray());

		return authResult;

	}

	@SuppressWarnings("deprecation")
	public List<Map<String, Object>> getbookById(BookBeans beans) {

		StringBuilder query = new StringBuilder();

		query.append(
				"SELECT b.title, b.description, b.author, b.price, b.quantity, br.rating,  br.comment AS comment FROM book b")
				.append(" LEFT JOIN book_review br ON br.book_id = b.id");
		List<Object> params = new ArrayList<>();

		boolean conditionAdded = false;

		if (!UtilityClass.isNull(beans.getId())) {
			query.append(conditionAdded ? " AND " : " WHERE ").append(" b.id = ? ");
			params.add(beans.getId());
			conditionAdded = true;
		}

		return jdbcTemplate.query(query.toString(), params.toArray(), (rs, rowNum) -> {
			Map<String, Object> map = new HashMap<>();
			map.put("title", rs.getObject("title"));
			map.put("description", rs.getObject("description"));
			map.put("author", rs.getObject("author"));
			map.put("price", rs.getObject("price"));
			map.put("quantity", rs.getObject("quantity"));
			map.put("rating", rs.getObject("rating"));
			map.put("comment", rs.getObject("comment"));

			return map;
		});

	}

	public int updateBook(BookBeans beans) {
		StringBuilder updateQuery = new StringBuilder();
		updateQuery.append("UPDATE book SET ")
				.append(" title = ?, description = ? , author = ?, price = ?, quantity = ? ").append(" WHERE ");

		boolean conditionAdded = false;

		if (!UtilityClass.isNull(beans.getId())) {
			if (conditionAdded) {
				updateQuery.append(" AND ");
			}
			updateQuery.append(" id = " + beans.getId());
			conditionAdded = true;
		}

		if (!UtilityClass.isNull(beans.getUserId())) {
			if (conditionAdded) {
				updateQuery.append(" AND ");
			}

			updateQuery.append(" user_id = " + beans.getUserId());
			conditionAdded = true;
		}

		return jdbcTemplate.update(updateQuery.toString(), beans.getTitle(), beans.getDescription(), beans.getAuthor(),
				beans.getPrice(), beans.getQuantity());
	}

	public int updateBookQuantity(BookBeans beans) {
		StringBuilder updateQuery = new StringBuilder();
		updateQuery.append("UPDATE book SET ").append(" quantity = ? ").append(" WHERE ");

		boolean conditionAdded = false;

		if (!UtilityClass.isNull(beans.getId())) {
			if (conditionAdded) {
				updateQuery.append(" AND ");
			}
			updateQuery.append(" id = " + beans.getId());
			conditionAdded = true;
		}

		return jdbcTemplate.update(updateQuery.toString(), beans.getQuantity());
	}

	public int updateBookStatus(BookBeans beans) {
		StringBuilder updateQuery = new StringBuilder();
		updateQuery.append("UPDATE book SET ").append(" active_status = ? ").append(" WHERE ");

		boolean conditionAdded = false;

		if (!UtilityClass.isNull(beans.getId())) {
			if (conditionAdded) {
				updateQuery.append(" AND ");
			}
			updateQuery.append(" id = " + beans.getId());
			conditionAdded = true;

		}

		if (!UtilityClass.isNull(beans.getUserId())) {
			if (conditionAdded) {
				updateQuery.append(" AND ");
			}
			updateQuery.append(" user_id = " + beans.getUserId());
			conditionAdded = true;

		}

		return jdbcTemplate.update(updateQuery.toString(), beans.getActiveStatus());
	}

	public int bookReview(BookBeans beans) {
		StringBuilder insertQuery = new StringBuilder();
		insertQuery.append(" INSERT INTO book_review ").append(" ( book_id, user_id, rating, comment ) ")
				.append(" VALUES( ?, ?, ?, ? ) ");

		return jdbcTemplate.update(insertQuery.toString(), beans.getId(), beans.getUserId(), beans.getRating(),
				beans.getComment());
	}

	public List<Map<String, Object>> existingReviewForCustomerId(BookBeans beans) {

		StringBuilder query = new StringBuilder();

		query.append("SELECT rating, comment FROM book_review ");
		List<Object> params = new ArrayList<>();

		boolean conditionAdded = false;

		if (!UtilityClass.isNull(beans.getId())) {
			query.append(conditionAdded ? " AND " : " WHERE ").append(" book_id = ? ");
			params.add(beans.getId());
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
	public int getBooksCount(BookBeans beans) {
		StringBuilder query = new StringBuilder();
		query.append("SELECT COUNT(*) AS totalBooks FROM book WHERE ");

		List<Object> params = new ArrayList<>();
		boolean conditionAdded = false;

		if (!UtilityClass.isNull(beans.getUserId())) {
			query.append(conditionAdded ? " AND " : "").append(" user_id = ? ");
			params.add(beans.getUserId());
			conditionAdded = true;
		}

		if (!UtilityClass.isNull(beans.getActiveStatus())) {
			query.append(conditionAdded ? " AND " : "").append(" active_status = ? ");
			params.add(beans.getActiveStatus());
			conditionAdded = true;
		}

		if (!conditionAdded) {
			return 0;
		}

		try {
			List<Integer> result = jdbcTemplate.query(query.toString(), params.toArray(),
					(rs, rowNum) -> rs.getInt("totalBooks"));

			return result.isEmpty() ? 0 : result.get(0);
		} catch (Exception ex) {
			ex.printStackTrace();
			return 0;
		}
	}

	@SuppressWarnings("deprecation")
	public int totalBooksSold(BookBeans beans) {
		StringBuilder query = new StringBuilder();
		query.append("SELECT SUM(ord.quantity) AS totalSale " + " FROM orders ord "
				+ " LEFT JOIN book b ON b.id = ord.book_id\r\n" + " LEFT JOIN user_profile up ON up.id = b.user_id "
				+ " WHERE ");

		List<Object> params = new ArrayList<>();
		boolean conditionAdded = false;

		if (!UtilityClass.isNull(beans.getUserId())) {
			query.append(conditionAdded ? " AND " : "").append(" b.user_id = ? ");
			params.add(beans.getUserId());
			conditionAdded = true;
		}

		System.out.println("totalBooksSold query: " + query.toString());

		if (!conditionAdded) {
			return 0;
		}

		try {
			List<Integer> result = jdbcTemplate.query(query.toString(), params.toArray(),
					(rs, rowNum) -> rs.getInt("totalSale"));

			return result.isEmpty() ? 0 : result.get(0);
		} catch (Exception ex) {
			ex.printStackTrace();
			return 0;
		}
	}

}
