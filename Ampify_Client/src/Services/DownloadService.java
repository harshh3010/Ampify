/*
Class to handle the download tasks
*/

package Services;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

public class DownloadService implements Runnable {

    private String downloadLink;    // Download url of music file
    private File outputFile;    // File to store downloaded music

    // Parameterized Constructor
    // @params downloadLink, outputFile
    public DownloadService(String downloadLink, File outputFile) {
        this.downloadLink = downloadLink;
        this.outputFile = outputFile;
    }

    @Override
    public void run() {

        try {

            // Creating a URL object from download link
            URL url = new URL(downloadLink);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

            // Getting the size of file to be downloaded
            double fileSize = (double) httpURLConnection.getContentLengthLong();

            // Creating an input stream to read from server
            BufferedInputStream inputStream = new BufferedInputStream(httpURLConnection.getInputStream());

            // Creating output stream to write to output file
            FileOutputStream fileOutputStream = new FileOutputStream(this.outputFile);
            BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(fileOutputStream, 1024);

            // Buffer for transferring files from source to destination
            byte[] buffer = new byte[1024];

            // Variables for displaying download progress
            double downloaded = 0;
            int read = 0;
            double percentDownloaded = 0.0;

            // Displaying the download progress
            while ((read = inputStream.read(buffer, 0, 1024)) >= 0) {
                bufferedOutputStream.write(buffer, 0, read);
                downloaded += read;
                percentDownloaded = (downloaded * 100) / fileSize;

                String percent = String.format("%.4f", percentDownloaded);
                System.out.println("Download progress: " + percent + "%");
            }

            // Closing the input and output streams
            bufferedOutputStream.close();
            inputStream.close();
            System.out.println("Download complete");

            // Displaying a success dialog
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Download Complete!", ButtonType.OK);
            alert.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
