package rs.onako2.backendredirect;

import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.player.ServerPreConnectEvent;
import org.slf4j.Logger;

import java.net.InetSocketAddress;
import java.util.List;
import java.util.Map;

public class ServerPreConnectListener {
    public static Logger logger;

    @Subscribe
    public void onServerPreConnect(ServerPreConnectEvent event) {
        Object serverConfig = Config.getConfig("servers");
        if (serverConfig == null) {
            logger.error("Couldn't check if player should be redirected. Check your Backend-Redirect/config.yml file.");
        } else if (serverConfig instanceof List) {
            try {
                List<Map<String, String>> serverList = (List<Map<String, String>>) serverConfig;
                for (Map<String, String> serverMap : serverList) {
                    handleServerRedirection(event, serverMap);
                }
            } catch (ClassCastException e) {
                logger.error("Unexpected config format. Expected a list of maps. {}", e.getMessage());
            }
        } else {
            logger.error("Unexpected config format. Expected a map or list of maps.");
        }
    }

    private void handleServerRedirection(ServerPreConnectEvent event, Map<String, String> serverMap) {
        if (event.getPreviousServer() != null) {
            String server = event.getOriginalServer().getServerInfo().getName();
            String serverAddress = serverMap.get(server);
            if (serverAddress != null) {
                String[] serverAddressParts = serverAddress.split(":");
                String host = serverAddressParts[0];
                int port = Integer.parseInt(serverAddressParts[1]);
                event.getPlayer().transferToHost(InetSocketAddress.createUnresolved(host, port));
            }
        } else {
            logger.error("Couldn't get the next server for player " + event.getPlayer().getUsername());
        }
    }
}