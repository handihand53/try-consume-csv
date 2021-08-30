import org.apache.commons.io.FileUtils;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;
import java.util.concurrent.TimeUnit;

public class Merge2 {

  public static void main(String[] args) throws Exception {
    String urlDownload =
                "https://feed-secure-qa1-gcp.gdn-app.com/product-feed/onlinesales/products/product-output.csv";
//        "https://feed.blibli.com/product-feed/onlinesales/product-output.csv";
    String pathFile = "E:/learning/mergeCSVCommon/src/file/product-output-apache-common.csv";

    URL url = new URL(urlDownload);
    long start = System.nanoTime();
    FileUtils.copyURLToFile(url, new File(pathFile));
    long end = System.nanoTime();
    long elapsedTime = end - start;
    double elapsedTimeInSecond = (double) elapsedTime / 1000000000;
    System.out.println(elapsedTimeInSecond + " seconds");
  }
}
