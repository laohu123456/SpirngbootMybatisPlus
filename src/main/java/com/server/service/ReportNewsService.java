package com.server.service;

import com.server.entity.ReportNews;

import java.util.Set;

public interface ReportNewsService {

    public boolean saveNews(ReportNews reportNews);

    public boolean deleteNewsById(Long newsId);

    public Set<ReportNews> hostNews(Integer startPage, Integer endPage);

    public boolean addScore(Long newsId);


}
