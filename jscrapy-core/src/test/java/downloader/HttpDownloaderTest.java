package downloader;

import org.jscrapy.core.TaskConfig;
import org.jscrapy.core.downloader.Downloader;
import org.jscrapy.core.downloader.impl.HttpDownloader;
import org.jscrapy.core.exception.MySpiderFetalException;
import org.jscrapy.core.page.Page;
import org.jscrapy.core.parser.Html;
import org.jscrapy.core.request.Request;
import org.jscrapy.core.request.impl.HttpRequest;
import org.testng.annotations.Test;
import util.ResourcePathUtils;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

/**
 * Created by cxu on 2015/11/7.
 */
public class HttpDownloaderTest {

    @Test
    public void testDownloadCanWork() throws MySpiderFetalException {
        String path = ResourcePathUtils.getResourceFileAbsPath(HttpDownloaderTest.class, "/CacherTest.yaml");
        TaskConfig taskConfig = null;
        taskConfig = new TaskConfig(path);

        String url = "http://www.oschina.net/";
        Request request = new HttpRequest(url);
        Downloader dl = new HttpDownloader(taskConfig);
        Page pg = dl.download(request);
        assertNotNull(pg);
        Html html = new Html(pg);
        String title = html.$("title").xpath("//title/text()").get();
        assertEquals("开源中国 - 找到您想要的开源项目，分享和交流", title);
    }
}