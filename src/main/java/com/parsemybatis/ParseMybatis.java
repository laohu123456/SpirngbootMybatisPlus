package com.parsemybatis;

import org.apache.ibatis.parsing.GenericTokenParser;
import org.apache.ibatis.parsing.TokenHandler;

public class ParseMybatis {


    public static void main(String[] args) {
        String orgion = "select * from where id = #{id}";
        MybatisHander mybatisHander = new MybatisHander();
        GenericTokenParser genericTokenParser = new GenericTokenParser("#{", "}", mybatisHander);
        String parse = genericTokenParser.parse(orgion);
        System.out.println(parse);
    }

    private static class MybatisHander implements TokenHandler {

        @Override
        public String handleToken(String content) {
            return "?";
        }

    }
}
