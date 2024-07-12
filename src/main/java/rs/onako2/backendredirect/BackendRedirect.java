package rs.onako2.backendredirect;

import com.google.inject.Inject;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.plugin.annotation.DataDirectory;
import com.velocitypowered.api.proxy.ProxyServer;
import org.slf4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;

@Plugin(
        id = "backendredirect",
        name = "backendredirect",
        version = BuildConstants.VERSION,
        description = "Redirect players to another server when they try joining a specific backend server",
        authors = {"Onako2"}
)
public class BackendRedirect {

    private final Logger logger;
    private final Path dataDirectory;
    private final ProxyServer server;
    private final Metrics.Factory metricsFactory;

    @Inject
    public BackendRedirect(ProxyServer server, Logger logger, @DataDirectory Path dataDirectory, Metrics.Factory metricsFactory) {
        this.server = server;
        this.logger = logger;
        this.dataDirectory = dataDirectory;
        Config.dataDirectory = dataDirectory;
        Config.logger = logger;
        ServerPreConnectListener.logger = logger;
        this.metricsFactory = metricsFactory;
    }

    @Subscribe
    public void onProxyInitialization(ProxyInitializeEvent event) {
        logger.info("Loading Backend-Redirect");
        Path configPath = dataDirectory.resolve("config.yml");


        if (!Files.exists(dataDirectory)) {
            try {
                Files.createDirectories(dataDirectory);
            } catch (IOException e) {
                logger.error("Failed to create directory " + dataDirectory, e);
                return;
            }
        }

        //put config.yml from resources to dataDirectory
        if (!Files.exists(configPath)) {
            try (InputStream in = getClass().getClassLoader().getResourceAsStream("config.yml")) {
                if (in == null) {
                    logger.error("Could not find config.yml in resources");
                    return;
                }
                Files.copy(in, configPath);
                logger.info("Copied config.yml to " + dataDirectory);
            } catch (IOException e) {
                logger.error("Failed to copy config.yml to " + dataDirectory, e);
            }
        }
        server.getEventManager().register(this, new ServerPreConnectListener());

        int pluginId = 22620; // <-- Replace with the id of your plugin!
        Metrics metrics = metricsFactory.make(this, pluginId);

        logger.info("Loaded Backend-Redirect");
    }
}
