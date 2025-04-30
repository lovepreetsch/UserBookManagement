package com.bookManagement.net.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.bookManagement.net.beans.UserBeans;
import com.bookManagement.net.util.UtilityClass;

@Repository
public class UserDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	public int userSignup(UserBeans beans) {

		StringBuilder insertQuery = new StringBuilder();
		insertQuery.append("INSERT INTO user_profile (\r\n"
				+ "  title, name, email, mobile, DOB, gender, age, blood_group, role_title\r\n"
				+ ") VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?)\r\n" + " ");

		return jdbcTemplate.update(insertQuery.toString(), beans.getTitle(), beans.getName(), beans.getEmail(),
				beans.getMobileNumber(), beans.getDOB(), beans.getGender(), beans.getAge(), beans.getBloodGroup(),
				beans.getRole());
	}

	public int userAuth(UserBeans beans) {

		StringBuilder insertQuery = new StringBuilder();
		insertQuery.append("INSERT INTO user_auth (  email, mobile, pin, role_title ) VALUES ( ?, ?, ?, ? ) ");

		return jdbcTemplate.update(insertQuery.toString(), beans.getEmail(), beans.getMobileNumber(), beans.getPin(),
				beans.getRole());
	}

	public List<Map<String, Object>> getExistingUsers(UserBeans beans) {

		StringBuilder query = new StringBuilder();

		query.append("SELECT id as authId , pin FROM user_auth ");
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

	public Map<String, Object> loginUser(UserBeans beans) {
		StringBuilder query = new StringBuilder();

		query.append("SELECT up.id, up.title, up.name, up.email, up.mobile, up.DOB, up.gender, up.age, "
				+ "up.blood_group, up.role_title, up.active_status, ua.id as authId, ua.pin " + "FROM user_profile up "
				+ "INNER JOIN user_auth ua ON ua.mobile = up.mobile ");

		List<Object> params = new ArrayList<>();
		boolean conditionAdded = false;

		if (!UtilityClass.isNull(beans.getEmail())) {
			query.append(conditionAdded ? " AND " : " WHERE ").append(" ua.email = ? ");
			params.add(beans.getEmail());
			conditionAdded = true;
		}

		if (!UtilityClass.isNull(beans.getMobileNumber())) {
			query.append(conditionAdded ? " AND " : " WHERE ").append(" ua.mobile = ? ");
			params.add(beans.getMobileNumber());
			conditionAdded = true;
		}

		query.append(conditionAdded ? " AND " : " WHERE ").append(" up.active_status = 1 AND up.deleted_status = 0 ")
				.append(" ORDER BY ua.created_on DESC LIMIT 1");

		List<Map<String, Object>> result = jdbcTemplate.queryForList(query.toString(), params.toArray());
		return result.isEmpty() ? null : result.get(0);
	}

	@SuppressWarnings("deprecation")
	public List<Map<String, Object>> getUserById(UserBeans beans) {

		StringBuilder query = new StringBuilder();

		query.append("SELECT \r\n" + " up.title AS userTitle, " + " up.name AS userName, up.email AS  userEmail,\r\n"
				+ "    up.mobile AS  userMobile,\r\n" + "    up.DOB AS  userDOB,\r\n"
				+ "   up.gender AS  userGender,\r\n" + "    up.age AS  userAge,\r\n"
				+ "    up.blood_group AS  userBloodGroup\r\n" + "FROM \r\n" + "    user_profile up \r\n");

		List<Object> params = new ArrayList<>();
		boolean conditionAdded = false;

		if (!UtilityClass.isNull(beans.getUserId())) {
			query.append(conditionAdded ? " AND " : " WHERE ").append(" up.id = ? ");
			params.add(beans.getUserId());
			conditionAdded = true;
		}

		return jdbcTemplate.query(query.toString(), params.toArray(), (rs, rowNum) -> {
			Map<String, Object> map = new HashMap<>();
			map.put("title", rs.getObject("userTitle"));
			map.put("name", rs.getObject("userName"));
			map.put("email", rs.getObject("userEmail"));
			map.put("mobile", rs.getObject("userMobile"));
			map.put("dob", rs.getObject("userDOB"));
			map.put("gender", rs.getObject("userGender"));
			map.put("age", rs.getObject("userAge"));
			map.put("bloodGroup", rs.getObject("userBloodGroup"));
			return map;
		});

	}

	public int editUser(UserBeans beans) {
		StringBuilder updateQuery = new StringBuilder();
		updateQuery.append("UPDATE user_profile SET ")
				.append(" title = ?, name = ? , DOB = ?, gender = ?, age = ?, blood_group = ? ")
				.append(" WHERE id = ? ");

		return jdbcTemplate.update(updateQuery.toString(), beans.getTitle(), beans.getName(), beans.getDOB(),
				beans.getGender(), beans.getAge(), beans.getBloodGroup(), beans.getUserId());
	}

	public int updateUserStatus(UserBeans beans) {
		StringBuilder updateQuery = new StringBuilder();
		updateQuery.append("UPDATE user_profile SET ").append(" active_status = ? ").append(" WHERE id = ? ");

		return jdbcTemplate.update(updateQuery.toString(), beans.getActiveStatus(), beans.getUserId());
	}

	public int resetPassword(UserBeans beans) {
		StringBuilder updateQuery = new StringBuilder();
		updateQuery.append("UPDATE user_auth ua SET ").append(" pin = ? ").append(" WHERE ua.id = ? ");

		return jdbcTemplate.update(updateQuery.toString(), beans.getPin(), beans.getAuthId());
	}

}
