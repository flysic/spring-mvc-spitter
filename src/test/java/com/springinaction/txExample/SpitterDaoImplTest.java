package com.springinaction.txExample;

import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.test.jdbc.SimpleJdbcTestUtils;
import org.springframework.transaction.annotation.Transactional;

import com.springinaction.txexample.Spitter;
import com.springinaction.txexample.SpitterDao;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={
		"classpath:persistence-context.xml",
		"classpath:test-dataSource-context.xml",
		"classpath:test-transaction-context.xml"
})
@TransactionConfiguration(transactionManager="txMgr", defaultRollback=true)
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class, TransactionalTestExecutionListener.class})
@Transactional
public class SpitterDaoImplTest {

	@Autowired
	private SimpleJdbcTemplate jdbcTemplate;
	
	@Autowired
	private SpitterDao dao;
	
	@After
	public void cleanup() {
			SimpleJdbcTestUtils.deleteFromTables(jdbcTemplate, "spitter");
	}
	
	@Test
	public void shouldCreateRowsAndSetIds() {
		Assert.assertEquals(0, SimpleJdbcTestUtils.countRowsInTable(jdbcTemplate, "spitter"));
		insertASpitter("username", "password", "fullname", "email", false);
		Assert.assertEquals(1, SimpleJdbcTestUtils.countRowsInTable(jdbcTemplate, "spitter"));
		insertASpitter("username", "password", "fullname", "email", false);
		Assert.assertEquals(2, SimpleJdbcTestUtils.countRowsInTable(jdbcTemplate, "spitter"));
	}
	
	private Spitter insertASpitter(String username,String password,String fullname,String email,boolean updateByEmail) {
		Spitter spitter = new Spitter();
		spitter.setUsername(username);
		spitter.setPassword(password);
		spitter.setFullName(fullname);
		spitter.setEmail(email);
		spitter.setUpdateByEmail(updateByEmail);
		Assert.assertNull(spitter.getId());
		dao.addSpitter(spitter);
		Assert.assertNotNull(spitter.getId());
		return spitter;
	}

}
