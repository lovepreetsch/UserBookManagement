package com.bookManagement.net.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.bookManagement.net.beans.CustomerBeans;
import com.bookManagement.net.util.UtilityClass;

@Repository
public class CustomerDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	public int insertCustomerProfile(CustomerBeans beans) {

		StringBuilder insertQuery = new StringBuilder();
		insertQuery.append("INSERT INTO customer_profile (\r\n"
				+ " auth_id, title, name, email, mobile, DOB, gender, age, blood_group, type\r\n"
				+ ") VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?, 'CUSTOMER')\r\n" + " ");

		return jdbcTemplate.update(insertQuery.toString(), beans.getAuthId(), beans.getTitle(), beans.getName(),
				beans.getEmail(), beans.getMobileNumber(), beans.getDOB(), beans.getGender(), beans.getAge(),
				beans.getBloodGroup());
	}

	public int insertCustomerAuth(CustomerBeans beans) {

		StringBuilder insertQuery = new StringBuilder();
		insertQuery.append("INSERT INTO customer_auth (  email, mobile, pin, role_title ) VALUES ( ?, ?, ?, ? ) ");

		return jdbcTemplate.update(insertQuery.toString(), beans.getEmail(), beans.getMobileNumber(), beans.getPin(),
				beans.getRole());
	}

	public List<Map<String, Object>> listCustomers(CustomerBeans beans) {

		StringBuilder query = new StringBuilder();

		query.append("SELECT id as customerId , name, email, mobile, DOB, blood_group FROM customer_profile ");
		List<Object> params = new ArrayList<>();

		boolean conditionAdded = false;

		if (!UtilityClass.isNull(beans.getEmail())) {
			query.append(conditionAdded ? " AND " : " WHERE ").append(" email = ? ");
			params.add(beans.getEmail());
			conditionAdded = true;
		}

		query.append("ORDER BY created_on DESC LIMIT 1");

		List<Map<String, Object>> authResult = jdbcTemplate.queryForList(query.toString(), params.toArray());

		return authResult;

	}

	public List<Map<String, Object>> getExistingCustomers(CustomerBeans beans) {

		StringBuilder query = new StringBuilder();

		query.append("SELECT id as authId , pin FROM customer_auth ");
		List<Object> params = new ArrayList<>();

		boolean conditionAdded = false;

		if (!UtilityClass.isNull(beans.getMobileNumber())) {
			query.append(conditionAdded ? " AND " : " WHERE ").append(" mobile = ? ");
			params.add(beans.getMobileNumber());
			conditionAdded = true;
		}

		if (!UtilityClass.isNull(beans.getEmail())) {
			query.append(conditionAdded ? " AND " : " WHERE ").append(" email = ? ");
			params.add(beans.getEmail());
			conditionAdded = true;
		}

		query.append("ORDER BY created_on DESC LIMIT 1");

		List<Map<String, Object>> authResult = jdbcTemplate.queryForList(query.toString(), params.toArray());

		return authResult;

	}

	public Map<String, Object> loginCustomer(CustomerBeans beans) {
		StringBuilder query = new StringBuilder();

		query.append("SELECT cp.id, cp.title, cp.name, cp.email, cp.mobile, cp.DOB, cp.gender, cp.age, "
				+ "cp.blood_group, cp.role_title, cp.active_status, ca.id as authId, ca.pin "
				+ "FROM customer_profile cp " + "INNER JOIN customer_auth ca ON ca.mobile = cp.mobile ");

		List<Object> params = new ArrayList<>();
		boolean conditionAdded = false;

		if (!UtilityClass.isNull(beans.getEmail())) {
			query.append(conditionAdded ? " AND " : " WHERE ").append(" ca.email = ? ");
			params.add(beans.getEmail());
			conditionAdded = true;
		}

		if (!UtilityClass.isNull(beans.getMobileNumber())) {
			query.append(conditionAdded ? " AND " : " WHERE ").append(" ca.mobile = ? ");
			params.add(beans.getMobileNumber());
			conditionAdded = true;
		}

		if (!UtilityClass.isNull(beans.getPin())) {
			query.append(conditionAdded ? " AND " : " WHERE ").append(" ca.pin = ? ");
			params.add(beans.getPin());
			conditionAdded = true;
		}

		query.append(conditionAdded ? " AND " : " WHERE ").append(" cp.active_status = 1 AND cp.deleted_status = 0 ")
				.append(" ORDER BY ca.created_on DESC LIMIT 1");

		List<Map<String, Object>> result = jdbcTemplate.queryForList(query.toString(), params.toArray());
		return result.isEmpty() ? null : result.get(0);
	}

	@SuppressWarnings("deprecation")
	public int getCustomerAuthId(CustomerBeans beans) {
		StringBuilder query = new StringBuilder();
		query.append("SELECT id as authId FROM customer_auth WHERE ");

		List<Object> params = new ArrayList<>();
		boolean conditionAdded = false;

		if (!UtilityClass.isNull(beans.getEmail())) {
			query.append(conditionAdded ? " OR " : "").append(" email = ? ");
			params.add(beans.getEmail());
			conditionAdded = true;
		}

		if (!UtilityClass.isNull(beans.getMobileNumber())) {
			query.append(conditionAdded ? " OR " : "").append(" mobile = ? ");
			params.add(beans.getMobileNumber());
			conditionAdded = true;
		}

		if (!conditionAdded) {
			return 0;
		}

		try {
			List<Integer> result = jdbcTemplate.query(query.toString(), params.toArray(),
					(rs, rowNum) -> rs.getInt("authId"));

			return result.isEmpty() ? 0 : result.get(0);
		} catch (Exception ex) {
			ex.printStackTrace();
			return 0;
		}
	}

	@SuppressWarnings("deprecation")
	public int getCustomerProfileId(CustomerBeans beans) {
		StringBuilder query = new StringBuilder();
		query.append("SELECT id as customerId FROM customer_profile WHERE ");

		List<Object> params = new ArrayList<>();
		boolean conditionAdded = false;

		if (!UtilityClass.isNull(beans.getAuthId())) {
			query.append(conditionAdded ? " OR " : "").append(" auth_id = ? ");
			params.add(beans.getAuthId());
			conditionAdded = true;
		}

		query.append(" AND deleted_status = false ");
		query.append(" ORDER BY created_on DESC ");
		query.append(" LIMIT 1 ");
		if (!conditionAdded) {
			return 0;
		}

		try {
			List<Integer> result = jdbcTemplate.query(query.toString(), params.toArray(),
					(rs, rowNum) -> rs.getInt("customerId"));

			return result.isEmpty() ? 0 : result.get(0);
		} catch (Exception ex) {
			ex.printStackTrace();
			return 0;
		}
	}

	public int linkCustomerUser(CustomerBeans beans) {

		StringBuilder insertQuery = new StringBuilder();
		insertQuery.append("INSERT INTO customer_linked_user (\r\n" + " user_id, customer_id, type\r\n"
				+ ") VALUES ( ?, ?, 'CUSTOMER')\r\n" + " ");

		return jdbcTemplate.update(insertQuery.toString(), beans.getUserId(), beans.getCustomerId());
	}

	@SuppressWarnings("deprecation")
	public List<Map<String, Object>> getCustomersList(CustomerBeans beans) {

		StringBuilder query = new StringBuilder();

		query.append("SELECT \r\n" + "    u.id AS userId,\r\n" + "    u.name AS userName,\r\n"
				+ "    u.email AS userEmail,\r\n" + "    cp.name AS customerName,\r\n"
				+ "    cp.email AS customerEmail,\r\n" + "    cp.mobile AS customerMobile,\r\n"
				+ "    cp.DOB AS customerDOB,\r\n" + "    cp.gender AS customerGender,\r\n"
				+ "    cp.age AS customerAge,\r\n" + "    cp.blood_group AS customerBloodGroup\r\n" + "FROM \r\n"
				+ "    user_profile u\r\n" + "    INNER JOIN customer_linked_user clu ON u.id = clu.user_id\r\n"
				+ "    INNER JOIN customer_profile cp ON clu.customer_id = cp.id\r\n");

		List<Object> params = new ArrayList<>();
		boolean conditionAdded = false;

		if (!UtilityClass.isNull(beans.getUserId())) {
			query.append(conditionAdded ? " AND " : " WHERE ").append(" u.id = ? ");
			params.add(beans.getUserId());
			conditionAdded = true;
		}

		query.append(conditionAdded ? " AND " : " WHERE ").append(" cp.active_status = 1 AND cp.deleted_status = 0 ")
				.append(" ORDER BY cp.created_on DESC ");

		return jdbcTemplate.query(query.toString(), params.toArray(), (rs, rowNum) -> {
			Map<String, Object> map = new HashMap<>();
			map.put("userId", rs.getObject("userId"));
			map.put("userName", rs.getObject("userName"));
			map.put("userEmail", rs.getObject("userEmail"));
			map.put("customerName", rs.getObject("customerName"));
			map.put("customerEmail", rs.getObject("customerEmail"));
			map.put("customerMobile", rs.getObject("customerMobile"));
			map.put("customerDOB", rs.getObject("customerDOB"));
			map.put("customerGender", rs.getObject("customerGender"));
			map.put("customerAge", rs.getObject("customerAge"));
			map.put("customerBloodGroup", rs.getObject("customerBloodGroup"));
			return map;
		});

	}

	@SuppressWarnings("deprecation")
	public List<Map<String, Object>> getCustomerById(CustomerBeans beans) {

		StringBuilder query = new StringBuilder();

		query.append("SELECT \r\n" + " cp.name AS customerName, cp.email AS customerEmail,\r\n"
				+ "    cp.mobile AS customerMobile,\r\n" + "    cp.DOB AS customerDOB,\r\n"
				+ "    cp.gender AS customerGender,\r\n" + "    cp.age AS customerAge,\r\n"
				+ "    cp.blood_group AS customerBloodGroup\r\n" + "FROM \r\n" + "    customer_profile cp \r\n");

		List<Object> params = new ArrayList<>();
		boolean conditionAdded = false;

		if (!UtilityClass.isNull(beans.getCustomerId())) {
			query.append(conditionAdded ? " AND " : " WHERE ").append(" cp.id = ? ");
			params.add(beans.getCustomerId());
			conditionAdded = true;
		}

		return jdbcTemplate.query(query.toString(), params.toArray(), (rs, rowNum) -> {
			Map<String, Object> map = new HashMap<>();
			map.put("name", rs.getObject("customerName"));
			map.put("email", rs.getObject("customerEmail"));
			map.put("mobile", rs.getObject("customerMobile"));
			map.put("dob", rs.getObject("customerDOB"));
			map.put("gender", rs.getObject("customerGender"));
			map.put("age", rs.getObject("customerAge"));
			map.put("bloodGroup", rs.getObject("customerBloodGroup"));
			return map;
		});

	}

	public int editCustomer(CustomerBeans beans) {
		StringBuilder updateQuery = new StringBuilder();
		updateQuery.append("UPDATE customer_profile SET ")
				.append(" title = ?, name = ? , DOB = ?, gender = ?, age = ?, blood_group = ? ")
				.append(" WHERE id = ? ");

		return jdbcTemplate.update(updateQuery.toString(), beans.getTitle(), beans.getName(), beans.getDOB(),
				beans.getGender(), beans.getAge(), beans.getBloodGroup(), beans.getCustomerId());
	}

	public int updateCustomerStatus(CustomerBeans beans) {
		StringBuilder updateQuery = new StringBuilder();
		updateQuery.append("UPDATE customer_profile cp, user_profile up SET ").append(" cp.active_status = ? ")
				.append(" WHERE cp.id = ? ").append(" AND up.id = ? ");

		return jdbcTemplate.update(updateQuery.toString(), beans.getActiveStatus(), beans.getCustomerId(),
				beans.getUserId());
	}

	@SuppressWarnings("deprecation")
	public List<Map<String, Object>> getCustomerOrderHistory(CustomerBeans beans) {

		StringBuilder query = new StringBuilder();

		query.append(
				"SELECT ord.id AS orderId, ord.book_id AS bookId, ord.quantity AS orderQuantity, ord.total_amount AS totalAmount , "
						+ " bk.title AS bookTitle , bk.description AS bookDescription, bk.author AS bookAuthor, bk.price AS bookPrice, "
						+ " orv.rating AS orderRating, orv.comment AS comment FROM customer_profile cp ")
				.append(" LEFT JOIN orders ord ON ord.customer_id = cp.id ")
				.append(" LEFT JOIN book bk ON bk.id = ord.book_id ")
				.append(" LEFT JOIN order_review orv ON orv.order_id = ord.id ");
		List<Object> params = new ArrayList<>();

		boolean conditionAdded = false;

		if (!UtilityClass.isNull(beans.getCustomerId())) {
			query.append(conditionAdded ? " AND " : " WHERE ").append(" cp.id = ? ");
			params.add(beans.getCustomerId());
			conditionAdded = true;
		}

		query.append(" ORDER BY ord.order_date DESC ");

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

	public int resetPassword(CustomerBeans beans) {
		StringBuilder updateQuery = new StringBuilder();
		updateQuery.append("UPDATE customer_auth ca SET ").append(" ca.pin = ? ").append(" WHERE ca.id = ? ");

		return jdbcTemplate.update(updateQuery.toString(), beans.getPin(), beans.getAuthId());
	}

}
