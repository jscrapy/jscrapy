package cacher;

import org.jscrapy.core.cacher.Cacher;
import org.jscrapy.core.cacher.impl.H2Cacher;
import org.jscrapy.core.config.JscrapyConfig;
import org.jscrapy.core.exception.MySpiderFetalException;
import org.jscrapy.core.page.Page;
import org.jscrapy.core.request.HttpRequest;
import org.jscrapy.core.request.HttpRequestMethod;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import util.ResourcePathUtils;

import java.io.IOException;

import static org.testng.Assert.assertNotNull;

/**
 * Created by cxu on 2015/9/19.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:applicationContext.xml"})
public class CacherTest {
    @Autowired
    private H2Cacher h2Cacher;

    private static Page page;
    private static HttpRequest request;

    @BeforeClass
    public static void setup() {
        request = new HttpRequest("http://jscrapy.org/test", HttpRequestMethod.DELETE, null);
        page = new Page("this is html content, hahaha!");
        page.setRequest(request);
    }

    @Test
    public void testSave() throws IOException, MySpiderFetalException {
        Cacher[] cachers = new Cacher[]{initH2Cacher()};
        for (Cacher c : cachers) {
            doTest(c);
        }
    }

    /**
     * @param cacher
     * @throws MySpiderFetalException
     */
    private void doTest(Cacher cacher) throws MySpiderFetalException {
        cacher.cachePage(page);
        Page pg = cacher.loadPage(request);
        assertNotNull(pg);
    }

    /**
     * @return
     */
    private Cacher initH2Cacher() throws IOException {
        String path = ResourcePathUtils.getResourceFileAbsPath(CacherTest.class, "/H2CacherTest.yaml");
        JscrapyConfig jscrapyConfig = null;//(JscrapyConfig) Yaml2BeanUtil.loadAsBean(JscrapyConfig.class, new File(path));
        h2Cacher.setJscrapyConfig(jscrapyConfig);
        return h2Cacher;
    }
}
