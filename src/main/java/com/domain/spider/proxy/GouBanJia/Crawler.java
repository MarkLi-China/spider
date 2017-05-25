package com.domain.spider.proxy.GouBanJia;

import com.domain.common.utils.HttpRequest;
import com.domain.common.utils.JsonHelper2;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

/**
 * 爬取一个代理网站（http://www.goubanjia.com/）上的代理服务器地址
 * @author Mark Li
 * @version 1.0.0
 * @since 2017/5/25
 */
public class Crawler {

    public static void main(String[] args) {

        try {
            // Document doc = Jsoup.parse(new URL("http://www.goubanjia.com/"), 1000);
            // Document doc = Jsoup.connect("http://www.goubanjia.com/").get();
            String ret = new HttpRequest().get("http://www.goubanjia.com/", null, "UTF-8");
            Document doc = Jsoup.parse(ret);
            Element element = doc.getElementById("list");
            Elements trs = element.select("table>tbody>tr");
            List<Proxy> proxies = new ArrayList<>();
            if (trs != null) {
                for (Element tr : trs) {
                    Proxy proxy = new Proxy();
                    Elements tds = tr.getElementsByTag("td");
                    parseIpAddress(tds.get(0), proxy);
                    proxy.setAnonymity(tds.get(1).text());
                    proxy.setType(tds.get(2).text());
                    proxy.setAttribution(tds.get(3).text());
                    proxy.setCompany(tds.get(4).text());
                    proxy.setResponseSpeed(tds.get(5).text());
                    proxy.setLastTestTime(tds.get(6).text());
                    proxy.setSurvivalTime(tds.get(7).text());
                    proxies.add(proxy);
                }
            }
            System.out.println(JsonHelper2.toJson(proxies));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void parseIpAddress(Element element, Proxy proxy) {

        Elements ps = element.select("[style*=display]");
        StringBuilder ip = new StringBuilder();
        for (Element p: ps) {
            ip.append(p.text());
        }
        proxy.setIp(ip.toString());
        String port = parsePort(element.getElementsByClass("port").first());
        proxy.setPort(port);

    }

    private static String parsePort(Element element) {

        String ABCDEFGHIZ = "ABCDEFGHIZ";
        String portShow = element.text();
        if (portShow.contains("*")) {
            return portShow;
        }
        String portClass = element.attr("class");
        String[] arr = portClass.split(" ");
        String salt = arr[1];
        String[] salts = salt.split("");
        StringBuilder sb = new StringBuilder();
        for (String s: salts) {
            sb.append(ABCDEFGHIZ.indexOf(s));
        }
        int port = (Integer.parseInt(sb.toString()) >> 3);

        return String.valueOf(port);
    }
}
