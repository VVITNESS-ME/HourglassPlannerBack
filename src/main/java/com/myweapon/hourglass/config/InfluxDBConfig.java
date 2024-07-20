package com.myweapon.hourglass.config;

import com.influxdb.client.InfluxDBClient;
import com.influxdb.client.InfluxDBClientFactory;
import com.influxdb.client.QueryApi;
import com.influxdb.client.WriteApiBlocking;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class InfluxDBConfig {
    @Value("${influx.token}")
    private String tokenString;

    @Value("${influx.url}")
    private String influxUrl;

    @Value("${influx.bucket}")
    private String bucket;

    @Value("${influx.org}")
    private String org;


    @Bean
    public InfluxDBClient influxDBClient(){
        char[] token = tokenString.toCharArray();
         return InfluxDBClientFactory.create(influxUrl, token, org, bucket);
    }

    @Bean
    public WriteApiBlocking writeApiBlocking(InfluxDBClient influxDBClient){
        return influxDBClient.getWriteApiBlocking();
    }

    @Bean
    public QueryApi queryApi(InfluxDBClient influxDBClient){
        return influxDBClient.getQueryApi();
    }
}
