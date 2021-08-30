import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.NumberFormat;

public class Merge {

  public static void main(String[] args) throws Exception {
    String urlPF =
        "https://feed-secure-qa1-gcp.gdn-app.com/product-feed/onlinesales/products/product-output.csv";
//          "https://feed.blibli.com/product-feed/onlinesales/product-output.csv";

    int bufferSize = 2048 * 2048;

    URL urls = new URL(urlPF);
    HttpURLConnection httpConnection = (HttpURLConnection) urls.openConnection();
    httpConnection.setRequestMethod("HEAD");
    long fileSize = httpConnection.getContentLengthLong();

    System.out.println(fileSize + " bytes"); // size in bytes

    File file = new File("E:/learning/mergeCSVCommon/src/file/product-qa-new.csv");
    URL url = new URL(urlPF);
    HttpURLConnection httpUrlConnection = (HttpURLConnection) url.openConnection();

    if(file.exists()) {
      System.out.println("Log: file is exists");
      System.out.println(file.length());
      httpUrlConnection.setRequestProperty("Range", "bytes="+file.length()+"-");
    }

    BufferedInputStream in = new BufferedInputStream(httpUrlConnection.getInputStream());
    FileOutputStream fos;

    if(file.exists()){
      fos = new FileOutputStream(file, true); //resume download, append to existing file
    }
    else{
      fos = new FileOutputStream(file);
    }

    BufferedOutputStream bout = new BufferedOutputStream(fos, bufferSize);

    NumberFormat numberFormat = NumberFormat.getNumberInstance();
    numberFormat.setMinimumFractionDigits(5);

   long start = System.nanoTime();

    try {
      // 16777216 => 16.7 mb/sec
      byte[] data = new byte[bufferSize];
      int x;
      while ((x = in.read(data, 0, bufferSize )) >= 0) {
        bout.write(data, 0, x);
//        System.out.println(numberFormat.format(((double) file.length())/ ((double) fileSize)));
        System.out.println(file.length());
      }
    } catch (Exception e) {
      throw e;
    } finally {
      if (bout != null) {
        bout.flush();
        bout.close();
      }
      if (fos != null) {
        fos.flush();
        fos.close();
      }
    }
    long end = System.nanoTime();
    long elapsedTime = end - start;
    double elapsedTimeInSecond = (double) elapsedTime / 1000000000;
    System.out.println(elapsedTimeInSecond + " seconds");

    //    long existingFileSize = outputFile.length();
    //    if (existingFileSize < fileLength) {
    //      httpFileConnection.setRequestProperty(
    //          "Range",
    //          "bytes=" + existingFileSize + "-" + fileLength
    //      );
    //    }

    //    FileUtils.copyURLToFile(
    //        new URL(FILE_URL),
    //        new File(FILE_NAME),
    //        CONNECT_TIMEOUT,
    //        READ_TIMEOUT);

    //    Reader in = new FileReader("E:/learning/mergeCSVCommon/src/file/product-output.csv");
    //    for (CSVRecord record : CSVFormat.DEFAULT.parse(in)) {
    //      for (String field : record) {
    //        System.out.print("\"" + field + "\", ");
    //      }
    //      System.out.println();
    //    }
  }
}
