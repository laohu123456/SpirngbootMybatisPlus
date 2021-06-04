package com.server.controller;

import com.server.annotation.LogMethodRecord;
import com.server.entity.ReportNews;
import com.server.service.ReportNewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
@RequestMapping(value = "reportnews")
public class ReportNewsController {

    @Autowired
    private ReportNewsService reportNewsService;

    @LogMethodRecord(value = "redis-zset,添加实例", uri = "/reportnews/saveNews")
    @RequestMapping(value = "saveNews")
    public String saveNews(@RequestBody ReportNews reportNews){
        return reportNewsService.saveNews(reportNews) ? "插入成功" : "插入失败";
    }

    @LogMethodRecord(value = "redis-zset,根据Id删除实例", uri = "/reportnews/deleteNewsById")
    @RequestMapping(value = "deleteNewsById")
    public String deleteNewsById(@RequestParam(value = "newsId") Long newsId){
        return reportNewsService.deleteNewsById(newsId) ? "删除成功" : "删除失败";
    }

    @LogMethodRecord(value = "redis-zset,查询热点数据", uri = "/reportnews/hostNews")
    @RequestMapping(value = "hostNews")
    public Set<ReportNews> hostNews(@RequestParam(value = "startPage")Integer startPage,
                                    @RequestParam(value = "endPage")Integer endPage){
        return reportNewsService.hostNews(startPage, endPage);
    }

    @LogMethodRecord(value = "redis-zset,增加实例分数", uri = "/reportnews/addScore")
    @RequestMapping(value = "addScore")
    public String addScore(@RequestParam(value = "newsId") Long newsId){
        return reportNewsService.addScore(newsId) ? "加分成功" : "加分失败";
    }
}
