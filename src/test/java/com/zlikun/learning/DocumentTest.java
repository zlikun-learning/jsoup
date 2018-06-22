package com.zlikun.learning;

import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.Test;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * https://jsoup.org/
 * https://jsoup.org/cookbook/introduction/parsing-a-document
 * https://jsoup.org/cookbook/input/parse-document-from-string
 * https://jsoup.org/cookbook/input/load-document-from-url
 * https://jsoup.org/cookbook/input/load-document-from-file
 */
@Slf4j
public class DocumentTest {

    private String url = "https://jsoup.org";

    private OkHttpClient client = new OkHttpClient.Builder()
            .connectTimeout(1500, TimeUnit.MILLISECONDS)
            .build();

    @Test
    public void connect() throws IOException {

        // 通过URL构建Document对象(内部执行HTTP下载逻辑)
        Document document = Jsoup
                .connect(url)
                .get();

        parse_document(document);
    }

    @Test
    public void parse() throws IOException {

        // 通过第三方HTTP客户端下载网页
        Request request = new Request.Builder()
                .url(url)
                .get()
                .build();

        Response response = client.newCall(request).execute();

        String html = response.body().string();
//        log.info("通过OkHttp下载网页正文：\n{}", html);

        Document document = Jsoup.parse(html, url);
        parse_document(document);
    }

    /**
     * 针对Document一些用法进行测试
     * @param document
     */
    private void parse_document(Document document) {

        // title = jsoup Java HTML Parser, with best of DOM, CSS, and jquery
        log.info("title = {}", document.title());
        // charset = UTF-8
        log.info("charset = {}", document.charset());
        // location = https://jsoup.org
        log.info("location = {}", document.location());
        // nodeName = #document
        log.info("nodeName = {}", document.nodeName());

//        log.info("html = {}", document.html());
//        log.info("data = {}", document.data());
//
//        // 获取HEAD节点
//        log.info("head = {}", document.head());
//
//        // 获取BODY节点
//        log.info("body = {}", document.body());

    }

}
