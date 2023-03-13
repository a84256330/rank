package com.example.rank.controller;

import com.alibaba.fastjson.JSON;
import com.example.rank.utils.Result;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class RankControllerTest {

    @Autowired
    private RankController rankController;
    @Test
    void updateRank() {
        Result machao = rankController.updateRank("machao", 20);
        Result ywt = rankController.updateRank("ywt", 2);
        Result smy = rankController.updateRank("smyabscDSSAFADFA", 270);
//        System.out.println(JSON.toJSONString(machao));
//        System.out.println(JSON.toJSONString(ywt));
        System.out.println(JSON.toJSONString(smy));
        Result rankAll = rankController.getRankAll(10);
        System.out.println(JSON.toJSONString(rankAll));
        System.out.println(JSON.toJSONString(rankController.getRank("machao")));
        System.out.println(rankController.getRank("smy"));
        System.out.println(rankController.getRank("ywt"));
    }

    @Test
    void delRank() {
        Result result = rankController.delRank("", "yU7JnFWoC2e13Ddr");
        System.out.println(result);
    }

    @Test
    void getRankAll() {
        Result rankAll = rankController.getRankAll(10);
        System.out.println(JSON.toJSONString(rankAll));
    }
}