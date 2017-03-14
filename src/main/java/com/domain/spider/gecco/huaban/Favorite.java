package com.domain.spider.gecco.huaban;

import com.geccocrawler.gecco.GeccoEngine;
import com.geccocrawler.gecco.annotation.Gecco;
import com.geccocrawler.gecco.annotation.HtmlField;
import com.geccocrawler.gecco.annotation.Request;
import com.geccocrawler.gecco.annotation.RequestParameter;
import com.geccocrawler.gecco.request.HttpRequest;
import com.geccocrawler.gecco.spider.HtmlBean;

/**
 * com.domain.spider.gecco.huaban
 * @author Mark Li
 * @version 1.0.0
 * @since 2017/3/13
 */
@Gecco(matchUrl = "http://huaban.com/favorite/{subject}/?{link}", pipelines = {"favoritePipeline", "consolePipeline"})
public class Favorite implements HtmlBean {

    @Request
    private HttpRequest request;

    @RequestParameter
    private String subject;

    @HtmlField(cssPath = "body")
    private String body;

    public HttpRequest getRequest() {

        return request;
    }

    public void setRequest(HttpRequest request) {

        this.request = request;
    }

    public String getSubject() {

        return subject;
    }

    public void setSubject(String subject) {

        this.subject = subject;
    }

    public String getBody() {

        return body;
    }

    public void setBody(String body) {

        this.body = body;
    }

    public static void main(String[] args) {

        GeccoEngine.create()
                .classpath("com.domain.spider.gecco.huaban")
//                .start("http://huaban.com/favorite/quotes/")
                .start("http://huaban.com/favorite/beauty/?j09aggap&max=1054396845&limit=1000&wfl=1")
                .thread(1)
                .interval(2000)
                .run();
    }
}
