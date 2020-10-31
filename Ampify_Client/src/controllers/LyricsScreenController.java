package controllers;

import com.jfoenix.controls.JFXTextArea;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

public class LyricsScreenController {
    public JFXTextArea lyricsTextArea;

    public void displayLyrics(String lyricsUrl) {

        BufferedReader reader = null;
        String stringBuffer = null;
        try {
            URL url = new URL(lyricsUrl);

            try {
                reader = new BufferedReader(new InputStreamReader(url.openStream(), "UTF-8"));
                String lineNumber = null;

                while ((lineNumber = reader.readLine()) != null) {
                    String time = reader.readLine();

                    String text = "";
                    while (!(text = reader.readLine()).equals(""))
                        lyricsTextArea.appendText(text);
                }

            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (reader != null) {
                        reader.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }
}
