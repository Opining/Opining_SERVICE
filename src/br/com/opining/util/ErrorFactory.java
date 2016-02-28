package br.com.opining.util;

import java.util.HashMap;
import java.util.Map;

import br.com.opining.library.model.error.OpiningError;

public class ErrorFactory {

	private ErrorFactory() {
	}

	private static int index = 1;

	/*
	 * Error status relatives to users.
	 */
	public static final int DUPLICATE_LOGIN = index++;
	public static final int USER_NOT_FOUND = index++;
	public static final int USER_IS_INVALID = index++;
	public static final int INCORRECT_PASSWORD = index++;
	public static final int LOGIN_IS_TOO_LONG = index++;
	public static final int PASSWORD_IS_TOO_LONG = index++;
	public static final int NAME_IS_TOO_LONG = index++;
	public static final int NAME_IS_TOO_SHORT = index++;
	public static final int PASSWORD_IS_TOO_SHORT = index++;
	public static final int LOGIN_IS_TOO_SHORT = index++;
	public static final int ROOM_NOT_FOUND = index++;
	public static final int SUBJECT_IS_TOO_SHORT = index++;
	public static final int SUBJECT_IS_TOO_LONG = index++;
	public static final int ARGUMENT_TIME_TOO_SHORT = index++;
	public static final int ARGUMENT_TIME_TOO_LONG = index++;
	
	private static final Map<Integer, String> mapErrors = generateErrorMapping();


	private final static Map<Integer, String> generateErrorMapping() {
		HashMap<Integer, String> hashMap = new HashMap<Integer, String>();

		hashMap.put(DUPLICATE_LOGIN, "Este nome de login já está em uso");
		hashMap.put(USER_NOT_FOUND, "Usuário inexistente");
		hashMap.put(USER_IS_INVALID, "Usuário não existe mais");
		hashMap.put(INCORRECT_PASSWORD, "Senha incorreta");
		hashMap.put(LOGIN_IS_TOO_LONG, "Este nome de login possui mais caracteres que o permitido");
		hashMap.put(PASSWORD_IS_TOO_LONG, "Esta senha possui mais caracteres que o permitido");
		hashMap.put(NAME_IS_TOO_LONG, "Este nome possui mais caracteres que o permitido");
		hashMap.put(NAME_IS_TOO_SHORT, "Este nome possui menos caracteres que o permitido");
		hashMap.put(PASSWORD_IS_TOO_SHORT, "Esta senha possui menos caracteres que o permitido");
		hashMap.put(LOGIN_IS_TOO_SHORT, "Este nome de login possui menos caracteres que o permitido");
		hashMap.put(ROOM_NOT_FOUND, "Esta sala não existe");
		hashMap.put(SUBJECT_IS_TOO_SHORT, "Tema da sala possui menos caracteres que o permitido");
		hashMap.put(SUBJECT_IS_TOO_LONG, "Tema da sala possui menos caracteres que o permitido");
		hashMap.put(ARGUMENT_TIME_TOO_SHORT, "O tempo de argumentação deve ser maior");
		hashMap.put(ARGUMENT_TIME_TOO_LONG, "O tempo de argumentação deve ser menor");
		
		return hashMap;
	}

	public static final OpiningError getErrorFromIndex(int index) {
		OpiningError error = new OpiningError();
		error.setCode(index);
		error.setMessage(mapErrors.get(index));
		return error;
	}
}
