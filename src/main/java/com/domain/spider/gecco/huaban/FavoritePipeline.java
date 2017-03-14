package com.domain.spider.gecco.huaban;

import com.domain.spider.utils.ImageDownloader;
import com.geccocrawler.gecco.annotation.PipelineName;
import com.geccocrawler.gecco.pipeline.Pipeline;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * com.domain.spider.gecco.huaban
 * @author Mark Li
 * @version 1.0.0
 * @since 2017/3/14
 */
@PipelineName("favoritePipeline")
public class FavoritePipeline implements Pipeline<Favorite> {

    @Override
    public void process(Favorite bean) {

        String body = bean.getBody();
        String reg = "\"key\":\"[a-zA-Z0-9]+-[a-zA-Z0-9]{6}\"";
        Pattern pattern = Pattern.compile(reg);
        Matcher matcher = pattern.matcher(body);
        while (matcher.find()) {
            String result = matcher.group();
            String key = result.substring(7, result.length() - 1);
            ImageDownloader.download("d:\\Github\\huban-img", "http://img.hb.aicdn.com/" + key, key + ".jpg");
        }
    }
}
