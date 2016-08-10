package dal;

import org.jscrapy.core.dal.UrlQueueMapper;
import org.jscrapy.core.dal.h2.H2UrlQueueMapper;
import org.jscrapy.core.dal.h2.H2UrlQueueDo;
import org.jscrapy.core.dal.pg.PgUrlQueueMapper;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static org.testng.AssertJUnit.assertEquals;

/**
 * Created by cxu on 2016/8/5.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = H2QueueTest.class)
@SpringBootApplication(scanBasePackages = {"org.jscrapy.core.bootcfg"})
@PropertySource("db.properties")
public class H2QueueTest extends QueueTest{

    @Autowired
    H2UrlQueueMapper h2UrlQueueMapper;

    @Override
    protected UrlQueueMapper getQueueMapper() {
        return h2UrlQueueMapper;
    }
}