package com.domain.spider.utils;

import com.google.common.base.Strings;
import com.google.common.io.ByteStreams;
import com.google.common.io.Closer;
import com.google.common.io.Files;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

/**
 * com.domain.spider.utils
 * @author Mark Li
 * @version 1.0.0
 * @since 2017/3/14
 */
public class ImageDownloader {

    private static Log log = LogFactory.getLog(ImageDownloader.class);

    public static String download(String parentPath, String imgUrl, String fileName) {

        log.info("Downloading " + imgUrl);

        if (Strings.isNullOrEmpty(imgUrl) || Strings.isNullOrEmpty(parentPath)) {
            return null;
        }
        if (imgUrl.length() > 500) {
            return null;
        }
        Closer closer = Closer.create();
        try {
            File imageDir = new File(parentPath);
            if (!imageDir.exists()) {
                if (!imageDir.mkdirs()) {
                    log.error("Failed in Making " + parentPath);
                    return null;
                }
            }
            if (StringUtils.isEmpty(fileName)) {
                fileName = StringUtils.substringBefore(imgUrl, "?");
                fileName = StringUtils.substringAfterLast(fileName, "/");
            }
            File imageFile = new File(imageDir, fileName);
            if (!imageFile.exists()) {
                InputStream in = closer.register(new URL(imgUrl).openStream());
                Files.write(ByteStreams.toByteArray(in), imageFile);
            }
            return imageFile.getAbsolutePath();
        } catch (Exception ex) {
            ex.printStackTrace();
            log.error("image download error :" + imgUrl);
            return null;
        } finally {
            try {
                closer.close();
            } catch (IOException e) {
                log.error("Failed in closing Closer!");
            }
        }
    }
}
