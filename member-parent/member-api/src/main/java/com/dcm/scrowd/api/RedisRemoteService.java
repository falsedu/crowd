package com.dcm.scrowd.api;

import com.dcm.crowd.util.ResultEntity;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.concurrent.TimeUnit;

@FeignClient("/redisProvider")
public interface RedisRemoteService {




    @RequestMapping("/set/redis/key/value/remote")
    ResultEntity<String> setRedisKeyValueRemote(
            @RequestParam("key")String key,
            @RequestParam("value") String value
    );



    @RequestMapping("/set/redis/key/value/remote/with/timeout")
    public ResultEntity<String> setRedisKeyValueRemoteWithTimeout(
            @RequestParam("key")String key,
            @RequestParam("value") String value,
            @RequestParam("time") String time,
            @RequestParam("timeUnix")TimeUnit timeUnit
            );


    @RequestMapping("/get/redis/string/value/by/key")
    ResultEntity<String> getRedisStringValueByKeyRemote(@RequestParam("key") String key);


    @RequestMapping("/remove/redis/key/remote")
    ResultEntity<String> removeRedisKeyRemote(@RequestParam("key") String key);



}
