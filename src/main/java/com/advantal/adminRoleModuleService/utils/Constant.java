package com.advantal.adminRoleModuleService.utils;

public class Constant {
  
	/*
	 * -------------------------- EMAIL AND PASSWORD -------------------------------
	 */
	public static final String EMAIL_ADDRESS = "smscindia@outlook.com";
	public static final String PASSWORD = "Smsc@123";// "Smsc@123";

// -------------------------------------- TOKEN VARIABLE ---------------------------------------
	public static String HEADER_STRING = "Authorization";
	public static String TOKEN_PREFIX = "Bearer ";
	public static String AUTHORITIES_KEY = "roles";
	public static final long JWT_TOKEN_VALIDITY = 5 * 60 * 60;
	public static String SECRETKEY = "javainuse";
// ------------------------------------ END TOKEN VARIABLE ------------------------------------

//	-------------------------------------- OTHER CONSTATNT -----------------------------------

	// 1=active/unblocked, 0=deactive/delete, 2=blocked

	// for sms status 0 means success 1 means failed 2 means pending
	public static final Short Success = 0;// success
	public static final Short Failed = 1;// failed
	public static final Short Pending = 2;// pending

	public static final Short ZERO = 0;// ok,found
	public static final Short ONE = 1;// not found
	public static final Short TWO = 2;// blank field
	public static final Short THREE = 3;// not saved
	public static final Short FOUR = 4;// not saved
	public static final Boolean FALSE = false;
	public static final Boolean TRUE = true;
	public static final String ACTIVATE = "activate";
	public static final String TYPE_ACTIVATE = "kHJIqVoRke6QPOT/nSXKrw==";
	public static final String REGISTRATION_VERIFICATION_LINK = "kHJIqVoRke6QPOT/nSXKrw==";
	public static final String FORGETPASSWORD_VERIFICATION_LINK = "kHJIqVoRkelskdkejdk6QPOT/nSXKrw==";
	public static final String LOGIN_VERIFICATION_LINK = "kHJIqVoRkelskdkejdsffdgdfdfhfdk6QPOT/nSXKrw==";
//	------------------------------------------ End OTHER CONSTANT -------------------------------------------

//------------------------------------------ STATUS CODE -------------------------------------
	public static final String CREATE = "201";
	public static final String OK = "200";
	public static final String BAD_REQUEST = "400";
	public static final String NOT_AUTHORIZED = "401";
	public static final String FORBIDDEN = "403";
	public static final String WRONGEMAILPASSWORD = "402";
	public static final String NOT_FOUND = "404";
	public static final String SERVER_ERROR = "500";
	public static final String DB_CONNECTION_ERROR = "502";
	public static final String ENCRYPTION_DECRYPTION_ERROR = "503";
	public static final String NOT_EXIST = "405";
	public static final String CONFLICT = "409";

//--------------------------------------END STATUS CODE---------------------------------------

//--------------------------------------- RESPONSE KEY ---------------------------------------

	public static final String INVALID_REQUEST = "Invalid request!";//
	public static final String UNAUTHRISED = "UNAUTHRISED";//
	public static final String WRONG_INPUT_DATA = "Wrong input data!";//
	public static final String RESPONSE_CODE = "responseCode";
	public static final String OBJECT = "object";
	public static final String SUCCESS = "SUCCESS";
	public static final String ERROR = "ERROR";
	public static final String BLOCKED = "Blocked";
	public static final String NOT_VERIFIED = "Not verified";
	public static final String VERIFY_ACCOUNT = "Verify Account";
	public static final String RESET_PASSWORD = "Reset Password";
	public static final String AUTH_KEY = "authKey";
	public static final String MESSAGE = "message";
	public static final String DATA = "data";
	public static final String TOKEN = "token";
	public static final String AUTH_ID = "authId";
	public static final String TOTAL_USER = "totalUser";
	public static final String ISVALID = "isValid";

//-------------------------------------- END RESPONSE KEY ---------------------------------------	

//--------------------------------------- RESPONSE MESSAGES ----------------------------------------
	
	// ======================= Common message ==========================
		public static final String BAD_REQUEST_MESSAGE = "Bad request!!";
		public static final String ERROR_MESSAGE = "Please try again!!";
		public static final String RECORD_NOT_FOUND_MESSAGE = "Record not found!!";
		public static final String RECORD_FOUND_MESSAGE = "Record found!!";
		public static final String SERVER_MESSAGE = "Technical issue";
		public static final String NO_SERVER_CONNECTION = "The server was found but the connection to its local database was not possible.";
		public static final String PLEASE_LOGIN_FIRST_MESSAGE = "Please login again!!";
		public static final String PAGE_SIZE_MESSAGE = "Page size can't be zero, it should be more then zero!!";
		public static final String NOT_DOWNLOAD_FILE_MESSAGE = "Not able to download the file, because no record found on the database!!";
		public static final String ALREADY_DELETED_MESSAGE = "Already deleted!!";
		public static final String DELETED_MESSAGE = "Deleted successfully!!";
		public static final String ID_NOT_FOUND_MESSAGE = "Given id not found into the database!!";
		public static final String RECORD_BLOCKED_OR_DELETED_MESSAGE = "Record not found, because it may be blocked or deleted!!";

	// =============================== Module =============================
	public static final String MODULE_ADDED_SUCCESS_MESSAGE = "Module registered successfully!!";
	public static final String MODULE_NAME_CAN_NOT_EMPTY_MESSAGE = "Module name can't be null or blank!!";
	public static final String MODULE_LIST_FOUND_MESSAGE = "Modules list found!!";
	public static final String MODULE_LIST_EMPTY_MESSAGE = "Module list empty!!";
	public static final String MODULE_ID_NOT_FOUND_MESSAGE = "Module_id can not blank or null, it should be valid!!";
	public static final String MODULE_NOT_FOUND_MESSAGE = "Given module_id not found into the database!!";
	public static final String MODULE_NAME_AND_ID_NOT_FOUND_MESSAGE = "Given moduleId or moduleName not found into the database!!";
	public static final String MODULE_ID_CAN_NOT_BLANK_MESSAGE = "Module_id can not blank or null, it should be valid!!";

	// ========================== Role message ============================
	public static final String ROLE_ID_NOT_FOUND_MESSAGE = "Given role_id not found into the database!!";
	public static final String ROLENAME_EXISTS_MESSAGE = "Please provide another roleName, as this roleName already exist!!";
	public static final String ROLE_ADDED_SUCCESS_MESSAGE = "Role registration successfully!!";
	public static final String ROLE_ADDED_FAILED_MESSAGE = "Role registration failed, because no module selected!!";
	public static final String ROLE_UPDATED_SUCCESS_MESSAGE = "Role updated successfully!!";
	public static final String ROLE_CAN_NOT_DELETE_MESSAGE = "This role assigned someone, you can't delete!!";
	public static final String ROLE_FOUND_MESSAGE = "Role list found!!";
	public static final String ROLE_LIST_EMPTY_MESSAGE = "Role list empty!!";
	public static final String ROLE_NOT_ACTIVE_MESSAGE = "Role not active!!";
	public static final String MODULE_NOT_ACTIVE_MESSAGE = "Module not active!!";
	public static final String ROLE_NOT_ASSIGNED_MESSAGE = "You have not assigned role!!";
	public static final String NOT_ASSIGNED_ROLE_OR_MODULE_MESSAGE = "You have not assigned role and modules!!";
	public static final String ALREADY_BLOCKED_MESSAGE = "Already blocked!!";
	public static final String BLOCKED_SUCCESS_MESSAGE = "Blocked successfully!!";
	public static final String STATUS_INVALID_MESSAGE = "Status value invalid!!";
	public static final String UNBLOCKED_SUCCESS_MESSAGE = "Unblocked successfully!!";
	public static final String ALREADY_UNBLOCKED_MESSAGE = "Already unblocked!!";

	// ========================== Admin message ============================
	public static final String ADMIN_LOGIN_SUCCESSFULLY = "Admin login successfully!!";
	public static final String INVALID_CREDENTIAL = "Invalid credential!!";
	public static final String ROLE_NOT_FOUND_MESSAGE = "Given role not found into the database!!";
	public static final String EMAIL_EXISTS_MESSAGE = "Please provide another Email, as this Email already exist!!";
	public static final String MOBILE_EXISTS_MESSAGE = "Please provide another Mobile, as this Mobile already exist!!";
	public static final String ADMIN_ADDED_SUCCESS_MESSAGE = "Admin registered successfully!!";
	public static final String INVALID_EMAIL_AND_PASSWORD = "Invalid email and password!!";
	public static final String ADMIN_LOGIN_EMAIL = "Email not valid!!";
	public static final String ADMIN_LOGIN_PASSWORD = "password not valid!!";
	public static final String EMAIL_AND_PASSWORD_NOT_FOUND = "Email and password not found!!";
	public static final String ADMIN_UPDATED_SUCCESS_MESSAGE = "Admin updated successfully!!";
	public static final String ID_CAN_NOT_NULL_MESSAGE = "Id can not null, it shoulsd be valid!!";
	

	public static final Object PASSWORD_UPDATE = "password change sucessfully";
	public static final Object PASSWORD_NOT_MATCH = " old password not match";
	
	public static final Object FIELD_NOT_EMPTY = "email not valid";

//----------------------------------------- END RESPONSE MESSAGES ----------------------------------------	

}
