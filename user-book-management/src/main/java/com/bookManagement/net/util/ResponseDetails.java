package com.bookManagement.net.util;

public class ResponseDetails {

	public static final int OK_HttpStatusCode = 200;
	public static final String OK_RespnseMessage = "ok";

	public static final int INTERNAL_SERVER_ERROR_HttpStatusCode = 500;
	public static final String INTERNAL_SERVER_ERROR_ResponseMessage = "Server Error";

	public static final int NOT_FOUND_HttpStatusCode = 404;
	public static final String NOT_FOUND_ResponseMessage = "Not Found";

	public static final int BAD_REQUEST_HttpStatusCode = 400;
	public static final String BAD_REQUEST_ResponseMessage = "BAD REQUEST";

	public static final int FORBIDDEN_HttpStatusCode = 403;
	public static final int CONFLICT_HttpStatusCode = 409;
	public static final int UNAUTHORIZED_HttpStatusCode = 401;

	public static final int NO_CONTENT_HttpStatusCode = 204;
	public static final String NO_CONTENT_RespnseMessage = "No Content";

	// public static class Specialization {
	// public static final int OK_HttpStatusCode = 200;
	// public static final String OK_ResponseMessage = "Product updated
	// successfully";
	// public static final int NOT_FOUND_HttpStatusCode = 404;
	// public static final String NOT_FOUND_ResponseMessage = "Error while adding
	// details";
	// }

	public static class signup {
		public static final int MAX_OTP_REACHED_HttpStatusCode = 429;
		public static final int UNABLE_TO_SEND_OTP_HttpStatusCode = 430;
		public static final String UNABLE_TO_SEND_OTP_ResponseMessage = "Unable to send OTP";
		public static final String MOBILE_NUMBER_INVALID_RESPONSE_MESSAGE = "Mobile number not valid";
		public static final String OTP_SENT_SUCCESSFULLY_ResponseMessage = "OTP sent successfully";
		public static final String MAX_OTP_REACHED_ResponseMessage = "Maximum OTP Retry Attempts Reached. Try again tomorrow";
		public static final String USER_ALREADY_EXISTS_ResponseMessage = "User Already Exist";
		public static final String UNABLE_TO_COMPLETE_SIGNUP_ResponseMessage = "Unable to complete signup. Please try again later";
		public static final String MOBILE_NOT_VALID_ResponseMessage = "Mobile number not valid";
		public static final String EMAIL_NOT_VALID_ResponseMessage = "Email not valid";
	}

	public static class VerifyOTP {
		public static final String OTP_VERIFIED_ResponseMessage = "OTP Verified";
		public static final String INCORRECT_OTP_ResponseMessage = "Incorrect OTP";
		public static final String MOBILE_NUMBER_INVALID_RESPONSE_MESSAGE = "Mobile number not valid";
		public static final int UNABLE_TO_SEND_OTP_HttpStatusCode = 430;
		public static final String UNABLE_TO_SEND_OTP_ResponseMessage = "Unable to send OTP";
		public static final int MAX_OTP_REACHED_HttpStatusCode = 429;
		public static final String MAX_OTP_REACHED_ResponseMessage = "Maximum OTP Retry Attempts Reached. Try again tomorrow";
		public static final String OTP_SENT_SUCCESSFULLY_ResponseMessage = "OTP sent successfully";
	}

	public static class patientLogin {
		public static final String SIGNUP_IMCOMPLETE_RESPONSE_MESSAGE = "Please complete your Sign Up process";
		public static final String INCORRECT_OTP_ResponseMessage = "Incorrect OTP";
		public static final String INCORRECT_PIN_ResponseMessage = "Incorrect PIN";
		public static final String EMAIL_NOT_REGISTERED_ResponseMessage = "EmailId not registered";
		public static final String MOBILE_NOT_REGISTERED_ResponseMessage = "Mobile Number not Registered";
		public static final String LOGIN_SUCCESSFULL_ResponseMessage = "Login successfully";
		public static final int USER_DETAILS_NOT_FILLED_ResponseMessage = 412;
	}

	public static class providerLogin {
		public static final String SIGNUP_IMCOMPLETE_RESPONSE_MESSAGE = "Please create PIN before login";// "Please
																											// complete
																											// your Sign
																											// Up
																											// process";
		public static final String INCORRECT_OTP_ResponseMessage = "Incorrect OTP";
		public static final String INCORRECT_PIN_ResponseMessage = "Incorrect PIN";
		public static final String EMAIL_NOT_REGISTERED_ResponseMessage = "Email Id not registered";
		public static final String MOBILE_NOT_REGISTERED_ResponseMessage = "Mobile Number not Registered";
		public static final String LOGIN_SUCCESSFULL_ResponseMessage = "Login successfully";
		public static final int USER_DETAILS_NOT_FILLED_HttpStatusCode = 412;
		public static final String USER_DETAILS_NOT_FILLED_ResponseMessage = "Basic details not filled";
		public static final int USER_Business_DETAILS_NOT_FILLED_HttpStatusCode = 206;
		public static final String USER_Business_Details_NOT_FILLED_ResponseMessage = "Business details not filled";
		public static final int USER_PIN_NOT_CREATED_HttpStatusCode = 1006;
		public static final String USER_PIN_NOT_CREATED_ResponseMessage = "Please create PIN";
		public static final String Profile_Details_Complete = "Profile is complete. Proceed to dashboard.";
	}

	public static class resetPinPatient {
		public static final String NEW_AND_CONFIRMPIN_SHOULD_BE_SAME_ResponseMessage = "New and confirm PIN should be same";
		public static final String NEW_AND_OLDPIN_CANNOT_BE_SAME_ResponseMessage = "New and old PIN cannot be same";
		public static final String ERROR_IN_UPDATING_PIN_ResponseMessage = "Error in updating PIN";
		public static final String PIN_UPDATED_SUCCESSFULLY_ResponseMessage = "PIN updated successfully";
		public static final String INCORRECT_OLD_PIN_ResponseMessage = "Incorrect old PIN";
	}

	public static class forgotPin {
		public static final String USER_DOES_NOT_EXIST_ResponseMessage = "User does not exist";
		public static final String MOBILE_NUMBER_INVALID_RESPONSE_MESSAGE = "Mobile number not valid";
		public static final String EMAIL_NOT_VALID_ResponseMessage = "email not valid";
		public static final int UNABLE_TO_SEND_OTP_HttpStatusCode = 430;
		public static final String UNABLE_TO_SEND_OTP_ResponseMessage = "Unable to send OTP";
		public static final int MAX_OTP_REACHED_HttpStatusCode = 429;
		public static final String MAX_OTP_REACHED_ResponseMessage = "Maximum OTP Retry Attempts Reached. Try again tomorrow";
		public static final String OTP_SENT_SUCCESSFULLY_ResponseMessage = "OTP sent successfully";
	}

	public static class country {
		public static final String COUNTRY_ID_IS_ResponseMessage = "countryId is required";
		public static final String COUNTRY_NAME_IS_REQUIRED_ResponseMessage = "countryName is required";
		public static final String ABBR_IS_REQUIRED_ResponseMessage = "abbr is required";
		public static final String COUNTRY_INSERTED_SUCCESSFULLY_ResponseMessage = "Country created successfully";
		public static final String COUNTRY_UPDATED_SUCCESSFULLY_ResponseMessage = "Country Updated successfully";
		public static final String NO_COUNTRY_FOUND_ResponseMessage = "No country found";
		public static final String COUNTRY_LIST_ResponseMessage = "Country list";
		public static final String ERROR_IN_UPDATING_COUNTRY_ResponseMessage = "Error in updating country";
		public static final String ACTIVE_STATUS_IS_REQUIRED_ResponseMessage = "active_status is required";
	}

	public static class state {
		public static final String STATE_INSERTED_SUCCESSFULLY_ResponseMessage = "State created successfully";
		public static final String STATE_UPDATED_SUCCESSFULLY_ResponseMessage = "State updated successfully";
		public static final String OLD_AND_NEW_VALUE_ARE_SAME_ResponseMessage = "The provided values are identical to the current data";
		public static final String NO_STATE_FOUND_ResponseMessage = "No state found";
		public static final String STATE_LIST_ResponseMessage = "State list";
	}

	public static class timezone {
		public static final String ACTIVE_STATUS_IS_REQUIRED_ResponseMessage = "active_status is required";
		public static final String NO_TIMEZONE_FOUND_ResponseMessage = "No timezone found";
		public static final String TIMEZONE_LIST_ResponseMessage = "Timezone list";
	}

	public static class listPatient {
		public static final String PROVIDER_ID_IS_REQUIRED_ResponseMessage = "providerId is required";
		public static final String PATIENT_LIST_ResponseMessage = "Patient list";
		public static final String NO_DETAILS_FOUND_ResponseMessage = "No details found";
	}

	public static class listPatientById {
		public static final String PATIENT_DETAILS_ResponseMessage = "Patient details";
		public static final String NO_DETAILS_FOUND_ResponseMessage = "No details found";
		public static final String PATIENT_ID_IS_REQUIRED_ResponseMessage = "patientId is required";
		public static final String ROLE_IS_REQUIRED_ResponseMessage = "roleTitle is required";
	}

	public static class insertMemberDetails {
		public static final String ERROR_IN_INSERTING_DETAILS_ResponseMessage = "Error in creating details";
		public static final String DETAILS_INSERTED_SUCCESSFULLY_ResponseMessage = "Member added successfully";
	}

	public static class updateMemberDetails {
		public static final String ERROR_IN_UPDATE_DETAILS_ResponseMessage = "Error in updating details";
		public static final String DETAILS_UPDATED_SUCCESSFULLY_ResponseMessage = "Member details updated successfully";
	}

	public static class bussinessCategory {
		public static final String BUSINESS_CATEGORY_LIST_ResponseMessage = "Business category list";
		public static final String BUSINESS_CATEGORY_INSERTED_SUCCESSFULLY_ResponseMessage = "Business category created";
		public static final String BUSINESS_CATEGORY_UPDATED_SUCCESSFULLY_ResponseMessage = "Business category updated";
	}

	public static class lifeStyle {
		public static final String LIFESTYLE_INSERTED_SUCCESSFULLY_ResponseMessage = "Lifestyle created successfully";
		public static final String LIFESTYLE_UPDATED_SUCCESSFULLY_ResponseMessage = "Lifestyle updated successfully";
		public static final String OLD_AND_NEW_VALUE_ARE_SAME_ResponseMessage = "The provided values are identical to the current data";
		public static final String NO_LIFESTYLE_FOUND_ResponseMessage = "No lifestyle found";
		public static final String LIFESTYLE_LIST_ResponseMessage = "Lifestyle list";
	}

	public static class specialization {
		public static final String SPECIALIZATION_INSERTED_SUCCESSFULLY_ResponseMessage = "Specialization created successfully";
		public static final String SPECIALIZATION_UPDATED_SUCCESSFULLY_ResponseMessage = "Specialization updated successfully";
		public static final String OLD_AND_NEW_VALUE_ARE_SAME_ResponseMessage = "The provided values are identical to the current data";
		public static final String NO_SPECIALIZATION_FOUND_ResponseMessage = "No Specialization found";
		public static final String SPECIALIZATION_LIST_ResponseMessage = "Specialization list";
	}

	public static class service {
		public static final String SERVICE_INSERTED_SUCCESSFULLY_ResponseMessage = "Service created successfully";
		public static final String SERVICE_UPDATED_SUCCESSFULLY_ResponseMessage = "Service updated successfully";
		public static final String OLD_AND_NEW_VALUE_ARE_SAME_ResponseMessage = "The provided values are identical to the current data";
		public static final String NO_SERVICE_FOUND_ResponseMessage = "No service found";
		public static final String SERVICE_LIST_ResponseMessage = "Service list";
	}

	public static class vaccinationCategory {
		public static final String VACCINATION_CATEGORY_INSERTED_SUCCESSFULLY_ResponseMessage = "Vaccination category created successfully";
		public static final String VACCINATION_CATEGORY_UPDATED_SUCCESSFULLY_ResponseMessage = "Vaccination category updated successfully";
		public static final String OLD_AND_NEW_VALUE_ARE_SAME_ResponseMessage = "The provided values are identical to the current data";
		public static final String NO_VACCINATION_CATEGORY_FOUND_ResponseMessage = "No vaccination category found";
		public static final String VACCINATION_CATEGORY_LIST_ResponseMessage = "Vaccination category list";
	}

	public static class provider {
		public static final String PROVIDER_INSERTED_SUCCESSFULLY_ResponseMessage = "Provider created successfully";
		public static final String PROVIDER_UPDATED_SUCCESSFULLY_ResponseMessage = "Provider updated successfully";
		public static final String OLD_AND_NEW_VALUE_ARE_SAME_ResponseMessage = "The provided values are identical to the current data";
		public static final String NO_PROVIDER_FOUND_ResponseMessage = "No provider found";
		public static final String PROVIDER_LIST_ResponseMessage = "Provider list";
	}

	public static class superAdminLogin {
		public static final String INCORRECT_USERNAME_OR_PASSWORD_ResponseMessage = "Incorrect Email or PIN";
		public static final String LOGIN_SUCCESSFULl_ResponseMessage = "Login successfull";
	}

	public static class deleteAccount {
		public static final String ACCOUNT_DELETED_ResponseMessage = "Your account has been deleted successfully";
		public static final String COULD_NOT_DELETE_ResponseMessage = "Could not delete you account";
	}

	public static class superAdminLogout {
		public static final String LOGGED_OUT_SUCCESSFULl_ResponseMessage = "Logged out successfully";
	}

	public static class insertStaffDetails {
		public static final String INVITATION_ALREADY_SENT = "Invitation email already sent but confirmation pending";
	}

	public static class insertFrontdeskDetails {
		public static final String INVITATION_ALREADY_SENT = "Invitation email already sent but confirmation pending";
	}

	public static class UpdateStaffDetails {
		public static final String MOBILE_OR_EMAIL_ALREADY_EXISTS = "Mobile or Email already exists";
	}

	public static class resendInvitation {
		public static final String ALREADY_ACCEPTED = "Invitation already accepted";
	}

	public static class insertClinic {
		public static final String NUMBER_OF_CLINIC_EXCEEDED_THE_LIMIT = "Number of clinics exceeds your available limit";
	}

	public static class insertService {
		public static final String NUMBER_OF_SERVICE_EXCEED_THE_LIMIT = "Number of servise exceeds your available limit";
	}

	public static class unArchiveService {
		public static final String NUMBER_OF_SERVICE_EXCEED_THE_LIMIT = "Number of servise exceeds your available limit";
	}

	public static class availability {
		public static final String AVAILABILITY_CREATED_ResponseMessage = "Availability created successfully";
		public static final String AVAILABILITY_UPDATED_ResponseMessage = "Availability updated successfully";
		public static final String AVAILABILITY_DELETED_ResponseMessage = "Availability deleted successfully";
		public static final String AVAILABILITY_ALREADY_EXISTS_ResponseMessage = "Availability already exists for time period";
	}

	public static String insertFieldMessage(String fieldName) {
		return fieldName + " created successfully";
	}

	public static String updateFieldMessage(String fieldName) {
		return fieldName + " updated successfully";
	}

	public static String notFoundMessage(String fieldName) {
		return "No " + fieldName + " found";
	}

	public static String listMessage(String fieldName) {
		return fieldName + " list";
	}

	public static String alreadyExistsMessage() {
		return "Already exists";
	}

	public static String updatingSameDataMessage() {
		return "The provided values are identical to the current data";
	}

	public static String generateRequiredFieldMessage(String fieldName) {
		return fieldName + " is required";
	}

	public static String deleteFieldMessage(String fieldName) {
		return fieldName + " deleted successfully";
	}
}
