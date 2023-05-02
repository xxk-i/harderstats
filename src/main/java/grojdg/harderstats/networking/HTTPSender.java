package grojdg.harderstats.networking;

import grojdg.harderstats.HarderStats;
import grojdg.harderstats.HarderStatsConstants;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.server.network.ServerPlayerEntity;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

// Sends a PUT to our API given a JSON formatted string payload on a new thread
public class HTTPSender {
    private static boolean HTTP_DEBUG = true;


    public static void send(String rawUrl, String payload, boolean wait) {
        if (HarderStatsConstants.DEBUG) {
            HarderStats.LOGGER.info("Payload: " + payload);
        }

        Thread sender = new Thread(() -> {
            try {
                URL url = new URL(rawUrl);
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
                    HarderStats.LOGGER.info("Send stats response: " + responseMessage);
                }
            } catch (Exception e) {
                HarderStats.LOGGER.error("Something went wrong: " + e);
            }
        });

        if (wait) {
            try {
                sender.join();
            } catch (Exception e) {
                HarderStats.LOGGER.error("sender.join() failed: " + e);
            }
        }
        else {
            sender.start();
        }
    }
}
