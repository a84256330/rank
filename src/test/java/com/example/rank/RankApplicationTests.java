package com.example.rank;

import com.example.rank.utils.RedisUtil;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
class RankApplicationTests {
    @Resource
    RedisUtil redisUtil;

    @Test
    public void test(){
        System.out.println(redisUtil.get("name"));
    }


}
