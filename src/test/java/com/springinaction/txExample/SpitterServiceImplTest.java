package com.springinaction.txExample;

import junit.framework.Assert;

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
import com.springinaction.txexample.SpitterService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={
		"classpath:persistence-context.xml",
		"classpath:service-context.xml",
		"classpath:test-dataSource-context.xml",
		"classpath:test-transaction-context.xml"
})
@TransactionConfiguration(transactionManager="txMgr", defaultRollback=true)
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class, TransactionalTestExecutionListener.class})
@Transactional
public class SpitterServiceImplTest {
	
	@Autowired
	private SimpleJdbcTemplate jdbcTemplate;
	
	@Autowired
	private SpitterService service;
	
	@Test
	public void shouldCreateRowsAndIds() {
		Assert.assertEquals(0, SimpleJdbcTestUtils.countRowsInTable(jdbcTemplate, "spitter"));
		Spitter spitter = new Spitter();
		spitter.setUsername("username");
		spitter.setPassword("password");
		spitter.setFullName("fullName");
		spitter.setEmail("email");
		spitter.setUpdateByEmail(false);
		service.saveSpitter(spitter);
		Assert.assertEquals(1, SimpleJdbcTestUtils.countRowsInTable(jdbcTemplate, "spitter"));
	}
}
