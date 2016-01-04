package br.com.opining.util;

import java.util.HashMap;
import java.util.Map;

import br.com.opining.library.model.error.OpiningError;

public class ErrorFactory {

	private ErrorFactory() {
	}

	private static int index = 0;

	/*
	 * Error status relatives to users.
	 */
	public static final int DUPLICATE_LOGIN = index++;
	public static final int USER_NOT_FOUND = index++;
	public static final int INCORRECT_PASSWORD = index++;
	
	private static final Map<Integer, String> mapErrors = generateErrorMapping();


	private final static Map<Integer, String> generateErrorMapping() {
		HashMap<Integer, String> hashMap = new HashMap<Integer, String>();

		hashMap.put(DUPLICATE_LOGIN, "Duplicate entry for username");
		hashMap.put(USER_NOT_FOUND, "User not registered");
		hashMap.put(INCORRECT_PASSWORD, "Incorrect password");
		
		return hashMap;
	}

	public static final OpiningError getErrorFromIndex(int index) {
		OpiningError error = new OpiningError();
		error.setCode(index);
		error.setMessage(mapErrors.get(index));
		return error;
	}
}
