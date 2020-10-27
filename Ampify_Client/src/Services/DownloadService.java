package Services;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

public class DownloadService implements Runnable {

    private String downloadLink;
    private File outputFile;

    public DownloadService(String downloadLink, File outputFile) {
        this.downloadLink = downloadLink;
        this.outputFile = outputFile;
    }

    @Override
    public void run() {

        try {

            URL url = new URL(downloadLink);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            double fileSize = (double) httpURLConnection.getContentLengthLong();
            BufferedInputStream inputStream = new BufferedInputStream(httpURLConnection.getInputStream());
            FileOutputStream fileOutputStream = new FileOutputStream(this.outputFile);
            BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(fileOutputStream, 1024);
            byte[] buffer = new byte[1024];
            double downloaded = 0;
            int read = 0;
            double percentDownloaded = 0.0;

            while ((read = inputStream.read(buffer, 0, 1024)) >= 0) {
                bufferedOutputStream.write(buffer, 0, read);
                downloaded += read;
                percentDownloaded = (downloaded * 100) / fileSize;

                String percent = String.format("%.4f", percentDownloaded);
                System.out.println("Download progress: " + percent + "%");
            }

            bufferedOutputStream.close();
            inputStream.close();
            System.out.println("Download complete");

            // TODO: DISPLAY SUCCESS

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
