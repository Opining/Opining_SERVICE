package br.com.opining.rest.security;

import java.util.Random;
import java.util.StringTokenizer;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import br.com.opining.database.AcessKeyDAO;
import br.com.opining.library.model.AcessKey;
import br.com.opining.library.model.User;
import br.com.opining.library.util.EncryptUtil;

public class Authorizator {
	
	private static final Logger logger = LogManager.getLogger(Authorizator.class.getName());
	
	public AcessKey insertKey(User user){	
    	
		logger.info("Requesting a key from " + user.getLogin());
		
		AcessKeyDAO acessKeyDAO = new AcessKeyDAO();
		AcessKey acessKey;
    	
		acessKey = acessKeyDAO.getByLoginUser(user.getLogin());
    	
    	if(acessKey == null){
    		
    		logger.info(user.getLogin() + " hasn't a key. Creating one now");
    		
    		acessKey = new AcessKey();
    		
    		String key = generateKey();
	    		
	    	acessKey.setKey(key);
	    	acessKey.setUser(user);
	    	
		    acessKeyDAO.insert(acessKey);
		    
		    logger.info(user.getLogin() + " has a key now");
		    
    	} else {
    		acessKey.setKey(generateKey());
    		acessKeyDAO.update(acessKey);
    	}
    	
    	return acessKey;
	}

	public void deleteKey(User user) {
		
		logger.info("Deleting the " + user.getLogin() + "'s key");
		
		AcessKeyDAO acessKeyDAO = new AcessKeyDAO();
		AcessKey acessKey = acessKeyDAO.getByLoginUser(user.getLogin());
		
		if (acessKey != null) {
			
			logger.info(user.getLogin() + " has a key");
			acessKeyDAO.delete(acessKey);
			logger.info(user.getLogin() + "'s key is deleted");
			
		} else {
			logger.info(user.getLogin() + " didn't have a key");
		}
	}

	protected boolean isAuthorized(String key) {
		
		String decodedKey = EncryptUtil.decode(key);
		StringTokenizer tokenizer = new StringTokenizer(decodedKey, ":");
		String login = tokenizer.nextToken();
		AcessKey acessKey = new AcessKeyDAO().getByLoginUser(login);

		if (acessKey == null)
			return false;
		
		if (key.equals(acessKey.buildKey()))
			return true;
		
		return false;
	}
	
	private static String generateKey(){
		Random rand = new Random();
		int intKey = rand.nextInt((999999999 - 0) + 1);
		String key = EncryptUtil.encode("" + intKey);
		
		return key;
	}
}
