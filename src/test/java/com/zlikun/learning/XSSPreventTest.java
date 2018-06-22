package com.zlikun.learning;

import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;
import org.junit.Test;

/**
 * https://jsoup.org/cookbook/cleaning-html/whitelist-sanitizer
 */
@Slf4j
public class XSSPreventTest {

    @Test
    public void xss() {

        // 不安全Html代码
        String unsafe = "<p><a href='http://example.com/' onclick='stealCookies()'>Link</a></p>";
        // 清理不安全Html代码，需指定 Whitelist (白名单)
        String safe = Jsoup.clean(unsafe, Whitelist.basic());

        // <p><a href='http://example.com/' onclick='stealCookies()'>Link</a></p>
        log.info("unsafe : \n{}", unsafe);
        // <p><a href="http://example.com/" rel="nofollow">Link</a></p>
        log.info("safe : \n{}", safe);

        // 实现清除除 img 外全部标签
        String beforeHtml = "<ul><li><img src='https://image.zlikun.com/logo.png' alt='logo'/></li>" +
                "<li><a href='https://www.zlikun.com'>https://www.zlikun.com</a></li>" +
                "<li><a href='mailto:manager@zlikun.com'>mailto:manager@zlikun.com</a></li></ul>";
        String afterHtml = Jsoup.clean(beforeHtml, Whitelist.none()
                // 支持img标签
                .addTags("div")
                // 支持img标签的src/alt/title三个属性
                .addAttributes("img", "src", "alt", "title")
                // 设置属性时，默认属性对应标签也被支持
                .addAttributes("a", "href", "title")
                // 设置a标签href属性值支持的协议，仅限：http/https
                .addProtocols("a", "href", "http", "https")
        );

        // <ul><li><img src='https://image.zlikun.com/logo.png' alt='logo'/></li><li><a href='https://www.zlikun.com'>https://www.zlikun.com</a></li><li><a href='mailto:manager@zlikun.com'>mailto:manager@zlikun.com</a></li></ul>
        log.info("beforeHtml : \n{}", beforeHtml);
        // <img src="https://image.zlikun.com/logo.png" alt="logo">
        // <a href="https://www.zlikun.com">https://www.zlikun.com</a>
        // <a>mailto:manager@zlikun.com</a>
        log.info("afterHtml : \n{}", afterHtml);
    }

}
