package cz.cvut.fit.timetracking.configuration;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

@Configuration
public class ElasticsearchConfiguration {
    private static final String IP_PORT_SEPARATOR = ":";
    private static final Logger LOGGER = LogManager.getLogger();

    @Value("${time-tracking.search.elasticsearch.clusterName}")
    private String clusterName;

    @Value("${time-tracking.search.elasticsearch.hosts}")
    private String[] hosts;

    @Bean
    public Client client() {
        Settings esSettings = Settings.builder()
                .put("cluster.name", clusterName)
                .build();

        TransportClient transportClient = new PreBuiltTransportClient(esSettings);
        List<TransportAddress> settingsAddresses = parseSettingsAddresses();
        for (TransportAddress settingsAddress : settingsAddresses) {
            transportClient.addTransportAddress(settingsAddress);
        }
        return transportClient;
    }

    @Bean
    public ElasticsearchTemplate elasticsearchTemplate() {
        return new ElasticsearchTemplate(client());
    }

    private List<TransportAddress> parseSettingsAddresses() {
        List<TransportAddress> hostSettings = new ArrayList<>(hosts.length);
        for (String hostString : hosts) {
            String[] ipPortSplit = hostString.split(IP_PORT_SEPARATOR);
            if (ipPortSplit.length != 2) {
                LOGGER.warn("Invalid elastic search transport client address: '" + hostString + "'. This was omitted...");
                continue;
            }

            InetAddress inetAddress;
            try {
                inetAddress = InetAddress.getByName(ipPortSplit[0]);
                hostSettings.add(new TransportAddress(inetAddress, Integer.parseInt(ipPortSplit[1])));
            } catch (UnknownHostException e) {
                throw new RuntimeException(e);
            }
        }
        return hostSettings;
    }

}
