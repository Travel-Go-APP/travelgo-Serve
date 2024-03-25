package com.travelgo.backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ExpConfig {
    int maxLevel = 5;
    @Bean
    public int[] expTable(){
        int[] expTable = new int[maxLevel]; //임시 최대 레벨 5
        expTable[0] = 0; // 1레벨
        expTable[1] = 125; // 2레벨
        expTable[2] = 175; // ...
        expTable[3] = 200;
        expTable[4] = 250;
        /*for(int i = 2; i<maxLevel; i++){
            expTable[i] = expTable[i-1]*2;
        }*/ // 레벨업 로직을 만든다면 이런 식으로 사용
        return expTable;
    }
}
