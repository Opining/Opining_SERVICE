package br.com.opining.rest.security;

import java.util.Random;
import java.util.StringTokenizer;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import br.com.opining.database.AccessKeyDAO;
import br.com.opining.library.model.AccessKey;
import br.com.opining.library.model.User;
import br.com.opining.library.util.EncryptUtil;

public class Authorizator {
	
	private static final Logger logger = LogManager.getLogger(Authorizator.class.getName());
	
	public AccessKey insertKey(User user){	
    	
		logger.info("Requesting a key from " + user.getLogin());
		
		AccessKeyDAO acessKeyDAO = new AccessKeyDAO();
		AccessKey accessKey;
    	
		accessKey = acessKeyDAO.getByLoginUser(user.getLogin());
    	
    	if(accessKey == null){
    		
    		logger.info(user.getLogin() + " hasn't a key. Creating one now");
    		
    		accessKey = new AccessKey();
    		
    		String key = generateKey();
	    		
	    	accessKey.setKey(key);
	    	accessKey.setUser(user);
	    	
		    acessKeyDAO.insert(accessKey);
		    
		    logger.info(user.getLogin() + " has a key now");
		    
    	} else {
    		accessKey.setKey(generateKey());
    		acessKeyDAO.update(accessKey);
    	}
    	
    	return accessKey;
	}

	public void deleteKey(User user) {
		
		logger.info("Deleting the " + user.getLogin() + "'s key");
		
		AccessKeyDAO acessKeyDAO = new AccessKeyDAO();
		AccessKey accessKey = acessKeyDAO.getByLoginUser(user.getLogin());
		
		if (accessKey != null) {
			
			logger.info(user.getLogin() + " has a key");
			acessKeyDAO.delete(accessKey);
			logger.info(user.getLogin() + "'s key is deleted");
			
		} else {
			logger.info(user.getLogin() + " didn't have a key");
		}
	}

	protected boolean isAuthorized(String key) {
		
		String decodedKey = EncryptUtil.decode(key);
		StringTokenizer tokenizer = new StringTokenizer(decodedKey, ":");
		String login = tokenizer.nextToken();
		AccessKey accessKey = new AccessKeyDAO().getByLoginUser(login);

		if (accessKey == null)
			return false;
		
		if (key.equals(accessKey.buildKey()))
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
