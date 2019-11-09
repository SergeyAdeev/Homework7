import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

class RandusClient {

    private static final Logger LOGGER = LogManager.getLogger(RandusClient.class);

    private final JsonParser jsonParser = new JsonParser();
    private HttpURLConnection httpURLConnection;

    Person getRandomPerson() {
        setConnection();
        String response = null;
        try {
            response = getResponseBody(checkStatusConnection());
        } catch (IOException e) {
            LOGGER.error(e);
        }
        httpURLConnection.disconnect();
        return jsonParser.getPerson(response);
    }

    private void setConnection() {
        String requestUrl = "https://randus.org/api.php";
        try {
            URL url = new URL(requestUrl);
            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.connect();
        } catch (IOException e) {
            LOGGER.error("Соединение не установлено " + e);
        }
    }

    private InputStream checkStatusConnection() throws IOException {
        if (httpURLConnection.getResponseCode() != HttpURLConnection.HTTP_OK) {
            return httpURLConnection.getErrorStream();
        } else {
            return httpURLConnection.getInputStream();
        }
    }

    private String getResponseBody(InputStream stream) {
        StringBuilder stringBuilder = new StringBuilder();
        String line;
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(stream))
        ) {
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line).append("\n");
            }
            stream.close();
        } catch (IOException e) {
            LOGGER.error("Ошибка получения ответа с сервера " + e);
        }
        return stringBuilder.toString();
    }
}