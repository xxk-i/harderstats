package grojdg.harderstats.networking

import grojdg.harderstats.HarderStats
import grojdg.harderstats.HarderStatsConstants
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.net.HttpURLConnection
import java.net.URL

object InfoSender {
    private var HTTP_DEBUG = true

    fun send(rawURL: String, payload: String, wait: Boolean) {
        if (HarderStatsConstants.DEBUG) {
            HarderStats.LOGGER.info("Payload: $payload")
        }

        val job = CoroutineScope(Dispatchers.Default).launch {
            try {
                val url = URL(rawURL)
                val urlConnection = url.openConnection() as HttpURLConnection
                urlConnection.requestMethod = "PUT"
                urlConnection.setRequestProperty("Content-Type", "application/json")
                urlConnection.doOutput = true
                val input = payload.toByteArray()
                urlConnection.outputStream.write(input, 0, input.size)
                urlConnection.outputStream.flush()

                // Call getResponseMessage to actually send the request
                val responseMessage = urlConnection.responseMessage
                if (HTTP_DEBUG) {
                    HarderStats.LOGGER.info("Send stats response: $responseMessage")
                }
            } catch (e: Exception) {
                HarderStats.LOGGER.error("Something went wrong: $e")
            }
        }

//        val sender = Thread {
//            try {
//                val url = URL(rawURL)
//                val urlConnection = url.openConnection() as HttpURLConnection
//                urlConnection.requestMethod = "PUT"
//                urlConnection.setRequestProperty("Content-Type", "application/json")
//                urlConnection.doOutput = true
//                val input = payload.toByteArray()
//                urlConnection.outputStream.write(input, 0, input.size)
//                urlConnection.outputStream.flush()
//
//                // Call getResponseMessage to actually send the request
//                val responseMessage = urlConnection.responseMessage
//                if (HTTP_DEBUG) {
//                    HarderStats.LOGGER.info("Send stats response: $responseMessage")
//                }
//            } catch (e: Exception) {
//                HarderStats.LOGGER.error("Something went wrong: $e")
//            }
//        }
//
//        if (wait) {
//            try {
//                sender.join()
//            } catch (e: Exception) {
//                HarderStats.LOGGER.error("sender.join() failed: $e")
//            }
//        } else {
//            sender.start()
//        }
    }
}