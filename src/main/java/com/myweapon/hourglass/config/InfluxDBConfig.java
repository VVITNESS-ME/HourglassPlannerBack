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
    //${gpt.auth.token}
//    @Value("${gpt.auth.token}")
//    private String secretKey ;
    @Value("${influx.token}")
    private String tokenString;
    public static String org = "hourglass";
    public static String bucket = "tt";

    @Bean
    public InfluxDBClient influxDBClient(){
        char[] token = tokenString.toCharArray();
         return InfluxDBClientFactory.create("http://localhost:8086", token, org, bucket);
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
