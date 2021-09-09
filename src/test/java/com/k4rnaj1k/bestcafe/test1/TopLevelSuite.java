package com.k4rnaj1k.bestcafe.test1;

import com.k4rnaj1k.bestcafe.test2.UserServiceTests;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@Suite.SuiteClasses(SuiteOne.class)
@RunWith(Suite.class)
public class TopLevelSuite {
}

@Suite.SuiteClasses({MenuServiceTests.class, UserServiceTests.class})
@RunWith(Suite.class)
class SuiteOne{}
