package com.bookManagement.net.dao;

import java.util.ArrayList;
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

		query.append("SELECT id as bookId , title, description, author, price, quantity, review FROM book ");
		List<Object> params = new ArrayList<>();

		boolean conditionAdded = false;

		if (!UtilityClass.isNull(beans.getUserId())) {
			query.append(conditionAdded ? " AND " : " WHERE ").append(" user_id = ? ");
			params.add(beans.getUserId());
			conditionAdded = true;
		}

		query.append("ORDER BY review DESC ");

		List<Map<String, Object>> authResult = jdbcTemplate.queryForList(query.toString(), params.toArray());

		return authResult;

	}

	public List<Map<String, Object>> getbookById(BookBeans beans) {

		StringBuilder query = new StringBuilder();

		query.append("SELECT title, description, author, price, quantity, review FROM book ");
		List<Object> params = new ArrayList<>();

		boolean conditionAdded = false;

		if (!UtilityClass.isNull(beans.getId())) {
			query.append(conditionAdded ? " AND " : " WHERE ").append(" id = ? ");
			params.add(beans.getId());
			conditionAdded = true;
		}

		query.append("ORDER BY review DESC ");

		List<Map<String, Object>> authResult = jdbcTemplate.queryForList(query.toString(), params.toArray());

		return authResult;

	}

	public int updateBook(BookBeans beans) {
		StringBuilder updateQuery = new StringBuilder();
		updateQuery.append("UPDATE book SET ")
				.append(" title = ?, description = ? , author = ?, price = ?, quantity = ?, review = ? ")
				.append(" WHERE ");

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
				beans.getPrice(), beans.getQuantity(), beans.getReview());
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

}
