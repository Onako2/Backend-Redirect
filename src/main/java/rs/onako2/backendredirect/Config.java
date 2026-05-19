package rs.onako2.backendredirect;

import org.slf4j.Logger;
import org.yaml.snakeyaml.Yaml;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;

public class Config {

    public static Logger logger;
    public static Path dataDirectory;
    private static String config = null;

    static Yaml yaml = new Yaml();

    public static Object getConfig(String input) {
        Path configPath = dataDirectory.resolve("config.yml");
        try {
            if (config == null) {
                config = Files.readString(configPath);
            }
            Map<String, Object> obj = yaml.load(config);
            return obj.get(input);
        } catch (IOException e) {
            logger.error("Failed to load config.yml from {}", dataDirectory, e);
            return null;
        }
    }
}
