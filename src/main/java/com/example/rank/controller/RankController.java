package com.example.rank.controller;

import com.alibaba.fastjson.JSON;
import com.example.rank.entiy.ScoreDTO;
import com.example.rank.utils.RedisUtil;
import com.example.rank.utils.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisZSetCommands;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;
import java.util.Set;

/**
 * @program: rank
 * @description:
 * @author: machao42
 * @create: 2023-03-07 22:39
 **/
@RestController
@Slf4j
@RequestMapping("/rank/api")
public class RankController {

    @Autowired
    private RedisUtil redisUtil;

    public static final String PASS_WORD = "yU7JnFWoC2e13Ddr";

    @PostMapping("/updateRank")
    public Result updateRank(String usrId, Integer score){

        try {
            log.info("redisUtil:{}",JSON.toJSONString(redisUtil));
            Double scoreOld = redisUtil.zsetGetS("rank", usrId);
            if (scoreOld.intValue()>score) {
                return Result.wrapSuccess(scoreOld.intValue());
            }else {
                redisUtil.zsetAdd("rank",usrId,score.doubleValue());
                return Result.wrapSuccess(score);
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
            return Result.wrapError("更新排行榜失败,"+e.getMessage());
        } finally {
            log.info("[updateRank] usrId:{},score:{}",usrId,score);
        }
    }

    @PostMapping("/getRank")
    public Result getRank(String usrId){
        ScoreDTO dto = new ScoreDTO();
        try {
            Long rank = redisUtil.zsetGetV("rank", usrId);
            if (Objects.isNull(rank)) {
                return Result.wrapError("未查到用户排名信息");
            }
            Double score = redisUtil.zsetGetS("rank", usrId);
            dto.setScore(score);
            dto.setRank(rank.intValue());
            return Result.wrapSuccess(dto);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
            return Result.wrapError("获取具体用户排行榜失败,"+e.getMessage());
        } finally {
            log.info("[getRank] usrId:{}，ScoreDTO:{}",usrId, JSON.toJSONString(dto));
        }
    }

    @PostMapping("/delRank")
    public Result delRank(String usrId, String passWord){
        try {
            if (!Objects.equals(PASS_WORD,passWord)) {
                return Result.wrapError("密码错误");
            }
            if (StringUtils.isEmpty(usrId)) {
                redisUtil.zsetDelS("rank",0,-1);
                return Result.wrapSuccess();
            }else {
                Long rank = redisUtil.zsetGetV("rank", usrId);
                if (Objects.isNull(rank)) {
                    return Result.wrapError("未查到用户排名信息");
                }
                redisUtil.zsetDelV("rank",usrId);
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
            return Result.wrapError("删除排行榜失败,"+e.getMessage());
        } finally {
            log.info("[delRank] usrId:{}",usrId);
        }
        return Result.wrapSuccess();
    }

    @PostMapping("/getRankAll")
    public Result getRankAll(Integer ranking){
        try {
            Set<RedisZSetCommands.Tuple> itmes= redisUtil.zsetGetRange("rank", 0,ranking-1);
            return Result.wrapSuccess(itmes);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
            return Result.wrapError("获取排行榜失败,"+e.getMessage());

        } finally {
            log.info("[getRankAll] ranking:{}",ranking);
        }
    }

}
