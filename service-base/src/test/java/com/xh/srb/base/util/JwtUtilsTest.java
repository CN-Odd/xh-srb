package com.xh.srb.base.util;


import org.junit.Test;


public class JwtUtilsTest {
    @Test
    public void test() {
        String userName = "odd";
        Long userId =1L;
        String token1 = JwtUtils.createToken(userId, userName);
        String token2 = JwtUtils.createToken(userId, userName);
        System.out.println(token1);
        System.out.println(token2);
        System.out.println(token1 == token2);
    }
}

