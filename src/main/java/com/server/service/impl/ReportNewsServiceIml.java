package com.server.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.server.common.Constant;
import com.server.dao.ReportNewsMapper;
import com.server.entity.ReportNews;
import com.server.service.ReportNewsService;
import com.server.utils.SnowFlake;
import com.server.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

@Service
public class ReportNewsServiceIml implements ReportNewsService {

    @Autowired
    private ReportNewsMapper reportNewsMapper;

    @Autowired
    @Qualifier("redisTemplateSer")
    private RedisTemplate redisTemplate;

    private final static String REDIS_KEY_NEWS = Constant.REDIS_KEY_REPORT_NEWS;

    private final static Double DEFAULT_INCRNUM = 1D;

    @Transactional
    @Override
    public boolean saveNews(ReportNews reportNews)  {
        boolean flag = false;
        try {
            long snowId = SnowFlake.nextId();
            reportNews.setNewsId(snowId);
            reportNews.setCreateTime(Utils.getCurrentDate());
            reportNewsMapper.insert(reportNews);
            ObjectMapper objectMapper = new ObjectMapper();
            String json = objectMapper.writeValueAsString(reportNews);
            ZSetOperations<String, String> zSetOperations = redisTemplate.opsForZSet();
            flag =  zSetOperations.add(REDIS_KEY_NEWS, json, 0);
        }catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }
        return flag;
    }

    @Transactional
    @Override
    public boolean deleteNewsById(Long newsId) {
        boolean flag = false;
        try {
            ReportNews reportNews = reportNewsMapper.selectById(newsId);
            reportNewsMapper.deleteById(newsId);
            ObjectMapper objectMapper = new ObjectMapper();
            String json = objectMapper.writeValueAsString(reportNews);
            Long remove = redisTemplate.opsForZSet().remove(REDIS_KEY_NEWS, json);
            if (remove == 1L){
                flag = true;
            }
        }catch (Exception e){

        }
        return flag;
    }

    @Override
    public Set<ReportNews> hostNews(Integer startPage, Integer endPage) {
        Set<ReportNews> reportNewsSet = new HashSet<>();
        try {
            ZSetOperations<String, String> zSetOperations = redisTemplate.opsForZSet();
            Set<String> set = zSetOperations.reverseRange(REDIS_KEY_NEWS, startPage, endPage);
            for(String str : set){
                ObjectMapper objectMapper = new ObjectMapper();
                ReportNews reportNews = objectMapper.readValue(str, ReportNews.class);
                reportNewsSet.add(reportNews);
            }
        }catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }
        return reportNewsSet;
    }

    @Override
    public boolean addScore(Long newsId) {
        boolean flag = false;
        try {
            ReportNews reportNews = reportNewsMapper.selectById(newsId);
            if(reportNews == null){
                return flag;
            }
            ObjectMapper objectMapper = new ObjectMapper();
            String json = objectMapper.writeValueAsString(reportNews);
            Double aDouble = redisTemplate.opsForZSet().incrementScore(REDIS_KEY_NEWS, json, DEFAULT_INCRNUM);
            if(aDouble > 0D){
                flag = true;
            }
        }catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }
        return flag;
    }
}
