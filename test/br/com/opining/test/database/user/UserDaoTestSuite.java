package br.com.opining.test.database.user;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ UserGetTest.class, UserInsertTest.class, UserUpdateTest.class })
public class UserDaoTestSuite {

}
