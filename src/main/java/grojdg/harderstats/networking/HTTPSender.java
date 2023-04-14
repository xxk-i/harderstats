package grojdg.harderstats.networking;

import grojdg.harderstats.HarderStats;
import grojdg.harderstats.HarderStatsConstants;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

// Sends a PUT to our API given a JSON formatted string payload on a new thread
public class HTTPSender {
    private static boolean HTTP_DEBUG = false;

    private static final String rawURL = "http://127.0.0.1:8080/world/stats";

    public static void send(String payload) {
        if (HarderStatsConstants.DEBUG) {
            HarderStats.LOGGER.info("Payload: " + payload);
        }

        new Thread(() -> {
            try {
                URL url = new URL(rawURL);
                URLConnection connection = url.openConnection();
                HttpURLConnection httpURLConnection = (HttpURLConnection) connection;
                httpURLConnection.setRequestMethod("PUT");
                httpURLConnection.setRequestProperty("Content-Type", "application/json");
                httpURLConnection.setDoOutput(true);
                OutputStream os = httpURLConnection.getOutputStream();
                byte[] input = payload.getBytes("UTF8");
                os.write(input, 0, input.length);
                os.flush();

                // calling getResponseMessage actually sends our request
                String responseMessage = httpURLConnection.getResponseMessage();
                if (HTTP_DEBUG) {
                    HarderStats.LOGGER.info(responseMessage);
                }
            } catch (Exception e) {
                HarderStats.LOGGER.error("Something went wrong: " + e);
            }
        }).start();
    }
}
