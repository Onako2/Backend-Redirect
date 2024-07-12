package rs.onako2.backendredirect;

import org.slf4j.Logger;
import org.yaml.snakeyaml.Yaml;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;

public class Config {

    public static Logger logger;
    public static Path dataDirectory;

    static Yaml yaml = new Yaml();

    static InputStream inputStream = null;

    public static Object getConfig(String input) {
        Path configPath = dataDirectory.resolve("config.yml");

        try {
            inputStream = Files.newInputStream(configPath);
            Map<String, Object> obj = yaml.load(inputStream);
            return obj.get(input);
        } catch (IOException e) {
            logger.error("Failed to load config.yml from " + dataDirectory, e);
            return null;
        } finally {
            // Close the InputStream in a finally block
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    logger.error("Failed to close InputStream", e);
                }
            }
        }
    }
}