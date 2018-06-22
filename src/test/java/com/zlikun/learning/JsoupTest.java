package com.zlikun.learning;

import lombok.extern.slf4j.Slf4j;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.helper.HttpConnection;
import org.jsoup.nodes.Document;
import org.junit.Test;

import java.io.IOException;
import java.net.Proxy;
import java.util.HashMap;
import java.util.Map;

/**
 * https://jsoup.org/cookbook/extracting-data/working-with-urls
 */
@Slf4j
public class JsoupTest {

    @Test
    public void jsoup() throws IOException {

        final String url = "https://jsoup.org/";
        final String headerUserAgent = HttpConnection.DEFAULT_UA;
        final String headerReferrer = "https://baidu.com/";
        final String headerHost = "jsoup.org";

        Map<String, String> headers = new HashMap<>();
        headers.put("X-Author", "zlikun");
        headers.put("Host", headerHost);

        Map<String, String> cookies = new HashMap<>();
        cookies.put("_ga", "GA1.2.1945106353.1519270331");
        cookies.put("_gid", "GA1.2.2010329734.1519270331");

        // 创建连接对象
        Connection connection = Jsoup.connect(url)
                // header -> User-Agent [ #header("User-Agent", "") ]
                .userAgent(headerUserAgent)
                // header -> Referer [ #header("Referer", "") ]
                .referrer(headerReferrer)
                // header -> host [ #put() ]
                .header("Host", headerHost)
                // header -> multi header [ #put() ]
                .headers(headers)
                // cookie -> single cookie [ #put() ]
                .cookie("__cfduid", "def9d7e994c229ca13537318d2bfa315c1509428988")
                // cookie -> multi cookie [ #put() ]
                .cookies(cookies)
//                .proxy("proxy-host", 8888)
                .proxy(Proxy.NO_PROXY)
//                .data("key1", "value1", "key2", "value2")
//                .data("key3", "value3")
//                .postDataCharset("UTF-8")
                .followRedirects(true)
                .timeout(1500)
                .method(Connection.Method.GET);

        // 执行请求并返回响应对象
        Connection.Response response = connection.execute();
        // code = 200, message = OK
        log.info("code = {}, message = {}", response.statusCode(), response.statusMessage());

        // 执行请求并返回响应文档
        Document document = connection.get();
//        Document document = connection.post();

        // 打印文档信息
        // title = jsoup Java HTML Parser, with best of DOM, CSS, and jquery
        log.info("title = {}", document.title());
        // charset = UTF-8
        log.info("charset = {}", document.charset());
        // location = https://jsoup.org/
        log.info("location = {}", document.location());
        // nodeName = #document
        log.info("nodeName = {}", document.nodeName());

    }

}
