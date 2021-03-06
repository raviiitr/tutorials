package org.baeldung.ex.nontransientdataaccessexception;

import javax.sql.DataSource;

import org.baeldung.ex.nontransientexception.cause.Cause1NonTransientConfig;
import org.baeldung.persistence.service.IFooService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.InvalidDataAccessResourceUsageException;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { Cause1NonTransientConfig.class }, loader = AnnotationConfigContextLoader.class)
public class InvalidResourceUsageExceptionTest {
    @Autowired
    private IFooService fooService;

    @Autowired
    private DataSource restDataSource;

    @Test(expected = InvalidDataAccessResourceUsageException.class)
    public void whenRetrievingDataUserNoSelectRights_thenInvalidResourceUsageException() {
        fooService.findAll();
    }

    @Test(expected = BadSqlGrammarException.class)
    public void whenIncorrectSql_thenBadSqlGrammarException() {
        final JdbcTemplate jdbcTemplate = new JdbcTemplate(restDataSource);

        jdbcTemplate.queryForObject("select * fro foo where id=3", Integer.class);
    }

}
