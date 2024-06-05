package ru.urvanov.virtualpets.server.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import ru.urvanov.virtualpets.shared.domain.ServerInfo;

@ConfigurationProperties("virtualpets-server-springboot")
@Configuration
public class VirtualpetsServerSpringBootProperties {

    private String version;
    
    private ServerInfo[] servers;

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public ServerInfo[] getServers() {
        return servers;
    }

    public void setServers(ServerInfo[] servers) {
        this.servers = servers;
    }
    
}
