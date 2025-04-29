package com.bookManagement.net.util;

import java.lang.reflect.Field;
import java.security.SecureRandom;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Period;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONArray;
import org.json.JSONObject;

public class UtilityClass {

	String providerRoleTitle = "SERVICE_PROVIDER";

	// public static final String acceptInvitationUrl =
	// "http://192.168.0.186:3000/mail/acceptInvitation/";
	// public static final String declineInvitationUrl =
	// "http://192.168.0.186:3000/mail/declineInvitation/";

	// Method to generate a random one-time password (OTP)
	public static String generateRandomNumber(int digits) {
		SecureRandom random = new SecureRandom();
		// int digits = 9;
		StringBuilder sb = new StringBuilder(digits);
		for (int i = 0; i < digits; i++) {
			sb.append(random.nextInt(10));
		}
		return sb.toString();
	}

	private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	private static final SecureRandom RANDOM = new SecureRandom();

	// Generate two random uppercase letters
	public static String generateRandomCharacters(int length) {
		StringBuilder sb = new StringBuilder(length);
		for (int i = 0; i < length; i++) {
			sb.append(CHARACTERS.charAt(RANDOM.nextInt(CHARACTERS.length())));
		}
		return sb.toString();
	}

	// Method to generate a random one-time password (OTP)
	public static String generateOTP() {
		SecureRandom random = new SecureRandom();
		int digits = 4;
		StringBuilder sb = new StringBuilder(digits);
		for (int i = 0; i < digits; i++) {
			sb.append(random.nextInt(10));
		}
		return sb.toString();
	}

	public static String getCurrentDateAsString(String format) {
		SimpleDateFormat dateFormat = new SimpleDateFormat(format);
		return dateFormat.format(new Date());
	}

	// Method for Email Validation
	public static boolean isValidEmail(String email) {
		String regex = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(email);
		return matcher.matches();
	}

	public static boolean isValidMobileNumber(String mobileNumber) {
		// Define the regex pattern for a 10-digit mobile number
		// String regex = "^[0-9]{10}$";
		// String regex = "^(?!0+$)(\\+\\d{1,3}[- ]?)?(?!0+$)\\d{10}$";
		String regex = "^(?:\\+?[1-9]\\d{0,2}[- ]?)?(?!0+$)[1-9]\\d{9}$";

		// Create a Pattern object
		Pattern pattern = Pattern.compile(regex);

		// Create a Matcher object
		Matcher matcher = pattern.matcher(mobileNumber);

		// Check if the mobile number matches the pattern
		return matcher.matches();
	}

	// This Method only checks if all the incoming fields are not empty or null. If
	// even one of the field is not empty or null then this will return false.
	@SuppressWarnings("unlikely-arg-type")
	public static Boolean validateFields(Object... fields) // The ... in front of String is called an ellipsis and it is
															// used in Java to denote a variable-length argument list,
															// also known as varargs (variable-length arguments).
	{
		Boolean missingFields = false;

		for (Object field : fields) {
			if (fields instanceof String[]) {
				System.out.println("Type STRING");
				String[] fieldStringArray = (String[]) field;
				if (fieldStringArray == null || fieldStringArray.length == 0 || fieldStringArray.equals("null")) {
					missingFields = true;
				}
				if (fields instanceof JSONArray[]) {
					System.out.println("Type JSONARRAY");
					JSONArray[] fieldJSONArray = (JSONArray[]) field;
					if (fieldJSONArray == null || fieldJSONArray.length == 0 || fieldJSONArray.equals("null")) {
						missingFields = true;
					}
				} else {
					return false;
				}
			}
		}

		if (missingFields == true) {
			return missingFields;
		}

		return true; // No missing fields
	}

	// This Method checks if all the incoming fields are not empty or null and also
	// returns the name of the empty or null fields
	public static String validateFields(Map<String, String> fieldValues) {
		StringBuilder missingFields = new StringBuilder();

		for (Map.Entry<String, String> entry : fieldValues.entrySet()) {
			String fieldName = entry.getKey();
			String fieldValue = entry.getValue();

			if (fieldValue == null || fieldValue.isEmpty() || fieldValue.equals("null")) {
				missingFields.append(fieldName).append(", ");
			}
		}

		if (missingFields.length() > 0) {
			missingFields.setLength(missingFields.length() - 2);
			JSONObject obj = new JSONObject();
			obj.put("status", "400");
			obj.put("message", (missingFields.length() > 1) ? (missingFields.toString() + " fields are missing.")
					: (missingFields.toString() + " field is missing."));
			return obj.toString();
		}

		return null; // No missing fields
	}

	public static boolean hasLeadingOrTrailingSpaces(String value) {
		return value != null && (!value.equals(value.trim()));
	}

	public static final String LEADING_TRAILING_SPACES_MESSAGE = "Fields can't have leading or trailing spaces";

	public static Map<String, String> findFieldsWithLeadingOrTrailingSpaces(Map<String, String> fields) {
		Map<String, String> invalidFields = new HashMap<>();

		for (Map.Entry<String, String> entry : fields.entrySet()) {
			String fieldName = entry.getKey();
			String value = entry.getValue();

			if (value != null && (value.startsWith(" ") || value.endsWith(" "))) {
				invalidFields.put(fieldName, "can't have leading or trailing spaces");
			}
		}
		return invalidFields;
	}

	public static boolean isNull(Object value) {
		if (value == null) {
			// System.out.println("2 is Null TRUE");
			return true;
		}
		if (value instanceof String || value instanceof Integer) {
			// System.out.println("2 is Null elseif TRUE");
			String strValue = String.valueOf(value);
			return (strValue.equalsIgnoreCase("null") || strValue.trim().isEmpty());
		}
		if (value instanceof JSONArray) {
			JSONArray strValue = (JSONArray) value;
			return strValue.isEmpty();
		}
		if (value instanceof List) {
			List<?> list = (List<?>) value;
			return list.isEmpty();
		}
		if (value.getClass().isArray()) {
			return java.lang.reflect.Array.getLength(value) == 0;
		}
		// Add more specific checks for other datatypes if needed
		return false; // If the object is of a different datatype, it is not considered null.
	}

	public static void setNullableString(PreparedStatement preparedStatement, int parameterIndex, String value)
			throws SQLException {
		// System.out.println("\n"+"1 VALUE: "+ value );
		if (UtilityClass.isNull(value)) {
			// System.out.println("3");
			preparedStatement.setNull(parameterIndex, java.sql.Types.VARCHAR);
			// System.out.println("4");
		} else {
			// System.out.println("5");
			preparedStatement.setString(parameterIndex, value);
		}
		// This method helps in setting NULL for String Values in the Database as
		// without this NUll Value is saved as String "NULL" instead of proper NULL.
	}

	public static void setNullableValue(PreparedStatement preparedStatement, int parameterIndex, Object value)
			throws SQLException {

		if (UtilityClass.isNull(value)) {
			// System.out.println("3");
			preparedStatement.setNull(parameterIndex, java.sql.Types.INTEGER);
			// System.out.println("4");
		} else if (value instanceof String) {
			String stringValue = (String) value;
			// System.out.println("in Utility class: "+stringValue);
			preparedStatement.setInt(parameterIndex, Integer.parseInt(stringValue));

		} else if (value instanceof Integer) {
			Integer intValue = (Integer) value;
			preparedStatement.setInt(parameterIndex, intValue);
		} else if (value instanceof Float) {
			Float intValue = (Float) value;
			preparedStatement.setFloat(parameterIndex, intValue);
		} else if (value instanceof Double) {
			Double intValue = (Double) value;
			preparedStatement.setDouble(parameterIndex, intValue);
		} else if (value instanceof Boolean) {
			Boolean boolValue = (Boolean) value;
			preparedStatement.setBoolean(parameterIndex, boolValue);
		} else if (value instanceof Object) {
			String stringValue = String.valueOf(value);
			// System.out.println("in Utility class: "+stringValue);
			preparedStatement.setInt(parameterIndex, Integer.parseInt(stringValue));
		}
		// This method helps in setting NULL for String Values in the Database as
		// without this NUll Value is saved as String "NULL" instead of proper NULL.
	}

	// public static void setNullableBoolean(PreparedStatement preparedStatement,
	// int parameterIndex, String value) throws SQLException {
	//
	// if (UtilityClass.isNull(value)) {
	//
	// preparedStatement.setNull(parameterIndex, java.sql.Types.BOOLEAN);
	//
	// } else {
	//
	// Boolean boolValue = Boolean.valueOf(value);
	//
	// preparedStatement.setBoolean(parameterIndex, boolValue);
	//
	// }

	// This method helps in setting NULL for Boolean Values in the Database as
	// without this NULL Value is saved as 0 (false) or 1 (true).

	// =======
	// This method helps in setting NULL for String Values in the Database as
	// without this NUll Value is saved as String "NULL" instead of proper NULL.
	// }

	public static void setNullableBoolean(PreparedStatement preparedStatement, int parameterIndex, String value)
			throws SQLException {
		if (UtilityClass.isNull(value)) {
			preparedStatement.setNull(parameterIndex, java.sql.Types.BOOLEAN);
		} else {
			Boolean boolValue = Boolean.valueOf(value);
			preparedStatement.setBoolean(parameterIndex, boolValue);
		}
		// This method helps in setting NULL for Boolean Values in the Database as
		// without this NULL Value is saved as 0 (false) or 1 (true).
	}

	public static String removeSqlInjection(String value) {
		value = value.replaceAll("'", "\\\\'");
		value = value.replaceAll("--", "\\\\--");
		return value;
	}

	public static String formatNullableString(String value) {
		return UtilityClass.isNull(value) ? "NULL" : "'" + value + "'";
	}

	public static String formatNullableValue(Object value) {
		if (UtilityClass.isNull(value)) {
			return "NULL";
		} else {
			return removeSqlInjection(value.toString());
		}
	}

	public static void printNonNullFields(Object obj) {
		Class<?> clazz = obj.getClass();
		Field[] fields = clazz.getDeclaredFields();

		System.out.print(clazz.getSimpleName() + " [");
		boolean first = true;

		for (Field field : fields) {
			field.setAccessible(true);

			try {
				Object value = field.get(obj);
				if (value != null) {
					if (!first) {
						System.out.print(", ");
					}
					System.out.print(field.getName() + "=" + value);
					first = false;
				}
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}

		System.out.println("]");
	}

	public static void closeResources(ResultSet resultSet, PreparedStatement statement) {
		if (resultSet != null) {
			try {
				resultSet.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		if (statement != null) {
			try {
				statement.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public static void closeResources(ResultSet resultSet, PreparedStatement statement, Connection connection) {
		if (resultSet != null) {
			try {
				resultSet.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		if (statement != null) {
			try {
				statement.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		if (connection != null) {
			try {
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public static void closeResources(PreparedStatement statement) {
		if (statement != null) {
			try {
				statement.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public static void closeResources(PreparedStatement statement, Connection connection) {
		if (statement != null) {
			try {
				statement.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		if (connection != null) {
			try {
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public static void closeResources(ResultSet resultSet, Statement statement) {
		if (resultSet != null) {
			try {
				resultSet.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		if (statement != null) {
			try {
				statement.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public static void closeResources(Statement statement) {
		if (statement != null) {
			try {
				statement.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public static boolean containsNumbers(String text) {

		Pattern p = Pattern.compile("[0-9]");

		Matcher m = p.matcher(text);

		return m.find();

	}

	public static String getQueryFromPreparedStatement(PreparedStatement preparedStatement) {
		String query = preparedStatement.toString();
		if (query.startsWith("com.mysql.jdbc.PreparedStatement")) {
			query = query.substring(query.indexOf(":") + 2);
		} else if (query.startsWith("com.mysql.cj.jdbc.ClientPreparedStatement")) {
			query = query.substring(query.indexOf(":") + 2);
		}
		return query;
	}

	/**
	 * Calculates the age based on the given date of birth string. Automatically
	 * detects the format from the input and uses UTC for ISO 8601 formats.
	 *
	 * @param dob the date of birth as a string
	 * @return the calculated age, or -1 if the date cannot be parsed
	 */
	public static int calculateAge(String dob) {
		// List of possible date formats
		List<DateTimeFormatter> formatters = new ArrayList<>();
		formatters.add(DateTimeFormatter.ofPattern("yyyy-MM-dd")); // e.g., 1998-03-26
		formatters.add(DateTimeFormatter.ofPattern("dd-MM-yyyy")); // e.g., 26-03-1998
		formatters.add(DateTimeFormatter.ISO_ZONED_DATE_TIME); // e.g., 2024-11-21T11:00:00.000Z

		for (DateTimeFormatter formatter : formatters) {
			try {
				LocalDate birthDate;

				// Special handling for ISO Zoned Date Time
				if (formatter == DateTimeFormatter.ISO_ZONED_DATE_TIME) {
					ZonedDateTime zonedDateTime = ZonedDateTime.parse(dob, formatter);
					birthDate = zonedDateTime.withZoneSameInstant(java.time.ZoneOffset.UTC).toLocalDate();
				} else {
					birthDate = LocalDate.parse(dob, formatter);
				}

				// Get the current date in UTC
				LocalDate currentDate = LocalDate.now(java.time.Clock.systemUTC());

				// Calculate and return the age
				return Period.between(birthDate, currentDate).getYears();
			} catch (DateTimeParseException ignored) {
				// Try the next format
			}
		}

		// If all formats fail
		System.out.println("Invalid date format: " + dob);
		return -1;
	}

	public static int convertToInt(Object field, String fieldName) {
		if (field == null) {
			throw new IllegalArgumentException(fieldName + " cannot be null");
		}
		if (field instanceof Integer) {
			return (Integer) field;
		} else if (field instanceof Double) {
			return (int) Math.round((Double) field);
		} else if (field instanceof String) {
			try {
				return Integer.parseInt((String) field);
			} catch (NumberFormatException e) {
				throw new IllegalArgumentException("Invalid answer for field " + fieldName);
			}
		} else {
			throw new IllegalArgumentException("Invalid answer for field " + fieldName);
		}
	}

	public static int[] convertToIntArray(ArrayList<Object> list, String fieldName) {
		// Check if all elements are Integer
		int[] intArray = new int[list.size()];

		for (int i = 0; i < list.size(); i++) {
			Object obj = list.get(i);

			if (obj instanceof Integer) {
				intArray[i] = (Integer) obj; // Direct cast to int
			} else if (obj instanceof Float) {
				intArray[i] = Math.round((Float) obj); // Convert float to int using rounding
			} else if (obj instanceof Double) {
				intArray[i] = (int) Math.round((Double) obj); // Convert double to int using rounding
			} else if (obj instanceof Number) {
				// For other numeric types, you can use their int value
				intArray[i] = ((Number) obj).intValue();
			} else {
				throw new ClassCastException("Invalid answer for field " + fieldName);
			}
		}
		return intArray;
	}

	public static ArrayList<Integer> convertToIntList(ArrayList<Object> list, String fieldName) {
		// Check if all elements are Integer
		ArrayList<Integer> intArray = new ArrayList<>(); // [list.size()];

		for (int i = 0; i < list.size(); i++) {
			Object obj = list.get(i);

			if (obj instanceof Integer) {
				intArray.add((Integer) obj); // Direct cast to int
			} else if (obj instanceof Float) {
				intArray.add(Math.round((Float) obj)); // Convert float to int using rounding
			} else if (obj instanceof Double) {
				intArray.add((int) Math.round((Double) obj)); // Convert double to int using rounding
			} else if (obj instanceof Number) {
				// For other numeric types, you can use their int value
				intArray.add(((Number) obj).intValue());
			} else {
				throw new ClassCastException("Invalid answer for field " + fieldName);
			}
		}
		return intArray;
	}

	// public static boolean isValidDateFormat(String date)
	// {
	// SimpleDateFormat sdf = new SimpleDateFormat("dd-mm-yyyy");
	// sdf.setLenient(false);
	// try
	// {
	// sdf.parse(date);
	// return true;
	// }
	// catch (ParseException e)
	// {
	// return false;
	// }
	// }

	public static boolean isValidDateFormat(String date) {
		// Define the date formats
		SimpleDateFormat ddMMyyyy = new SimpleDateFormat("dd-MM-yyyy");
		SimpleDateFormat yyyyMMdd = new SimpleDateFormat("yyyy-MM-dd");

		ddMMyyyy.setLenient(false);
		yyyyMMdd.setLenient(false);

		try {
			// Try parsing with "dd-MM-yyyy"
			ddMMyyyy.parse(date);
			return true; // If it parses successfully, return the same date
		} catch (ParseException e1) {
			try {
				// Try parsing with "yyyy-MM-dd"
				yyyyMMdd.parse(date);
				// Convert "yyyy-MM-dd" to "dd-MM-yyyy"
				// return ddMMyyyy.format(yyyyMMdd.parse(date));
				return true;
			} catch (ParseException e2) {
				// Neither format matches, return null or an indication of invalid format
				return false;
			}
		}
	}

	public static String formatDateTime(LocalDate date, LocalTime time) {
		// Format the LocalDate and LocalTime into the required
		// "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'" format
		return date.atTime(time).atOffset(ZoneOffset.UTC)
				.format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"));
	}
	// public static String generateToken(String mobileoremail) {
	// Date now = new Date();
	//// Date expiryDate = new Date(now.getTime() + 3600000); // Token valid for 1
	// hour
	//
	// return "123token";
	//// return Jwts.builder()
	//// .setSubject(mobileoremail)
	//// .setIssuedAt(now)
	////// .setExpiration(expiryDate)
	//// .signWith(SignatureAlgorithm.HS256, tokenKey)
	//// .compact();
	// }
}