package cz.cvut.fit.timetracking.configuration;

import org.apache.http.HttpHost;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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
    public RestHighLevelClient restHighLevelClient() {
        RestHighLevelClient client = new RestHighLevelClient(RestClient.builder(parseHosts().toArray(new HttpHost[0])));
        return client;
    }

    private List<HttpHost> parseHosts() {
        List<HttpHost> httpHosts = new ArrayList<>(hosts.length);
        for (String hostString : hosts) {
            String[] ipPortSplit = hostString.split(IP_PORT_SEPARATOR);
            if (ipPortSplit.length != 2) {
                LOGGER.warn("Invalid elastic search transport client address: '" + hostString + "'. This was omitted...");
                continue;
            }
            httpHosts.add(new HttpHost(ipPortSplit[0], Integer.valueOf(ipPortSplit[1]), "http"));
        }
        return httpHosts;
    }
}
