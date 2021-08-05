package com.xh.srb.core;

import com.xh.srb.core.mapper.DictMapper;
import com.xh.srb.core.pojo.entity.Dict;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import org.junit.runner.RunWith;

import java.util.concurrent.TimeUnit;

@SpringBootTest
@RunWith(SpringRunner.class)
public class RedisTemplateTest {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;
    @Autowired
    private DictMapper dictMapper;
    @Test
    public void testSave() {
        Dict dict = dictMapper.selectById(1);
        redisTemplate.opsForValue().set("dict:" + 1, dict, 5, TimeUnit.MINUTES);
    }

    @Test
    public void testGet() {
        Object o = redisTemplate.opsForValue().get("dict:1");
        System.out.println(o);
    }
}
