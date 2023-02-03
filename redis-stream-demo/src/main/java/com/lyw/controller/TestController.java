package com.lyw.controller;

import com.lyw.entity.SensorEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.stream.ObjectRecord;
import org.springframework.data.redis.connection.stream.ReadOffset;
import org.springframework.data.redis.connection.stream.StreamOffset;
import org.springframework.data.redis.connection.stream.StreamReadOptions;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.time.Duration;
import java.util.List;

@RequestMapping("/test")
@RestController
public class TestController {

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @GetMapping("/hello")
    public String hello(){
        List<ObjectRecord<String, SensorEntity>> result = stringRedisTemplate.opsForStream().read(SensorEntity.class, StreamReadOptions.empty().block(Duration.ofSeconds(30L)), StreamOffset.create("mystream", ReadOffset.latest()));
        StringBuilder sb = new StringBuilder();
        if (result != null){
            for (ObjectRecord<String, SensorEntity> record : result) {
                sb.append(record.getValue().toString());
            }
            return sb.toString();
        }else{
            return "is empty";
        }
    }



}
