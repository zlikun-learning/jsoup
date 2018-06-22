package com.zlikun.learning;

import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.Test;

import java.io.IOException;

/**
 * https://jsoup.org/cookbook/extracting-data/dom-navigation
 */
@Slf4j
public class ElementTest {

    @Test
    public void element() throws IOException {

        Document document = Jsoup.connect("https://www.oschina.net/").get();

        // 获取全部超链接，过滤掉非以"https://www.oschina.net/news/"开头的链接，并将其打印出来
        log.info("/============== 获取所有超链接 =================");
        document.getElementsByTag("a")
                .parallelStream()
                .map(element -> element.attr("abs:href"))
                .distinct()
                .filter(link -> link != null && link.toLowerCase().startsWith("https://www.oschina.net/news/"))
                .forEach(log::info);
        log.info("============== 获取所有超链接 =================/");

        // 获取全部图片
        log.info("/============== 获取所有PNG图片 =================");
        document.getElementsByTag("img")
                .parallelStream()
                .map(element -> element.attr("abs:src"))
                .filter(src -> src != null)
                .map(src -> src.replaceAll("t=\\d+", ""))
                .distinct()
                .filter(link -> link.toLowerCase().endsWith(".png"))
                .forEach(log::info);
        log.info("============== 获取所有PNG图片 =================/");

        // 获取一个DIV，这里获取资讯列表，并输出列表标题
        log.info("/============== 获取资讯列表 =================");
        document.getElementsByClass("col-of-news")
                .stream()
                .flatMap(element -> element.getElementsByClass("news-link").stream())
                .map(element -> element.text())
                .forEach(log::info);
        log.info("============== 获取资讯列表 =================/");

    }

}
