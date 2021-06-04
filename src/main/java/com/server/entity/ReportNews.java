package com.server.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

@TableName("report_news")
@Data
public class ReportNews implements Serializable {

    private static final long serialVersionUID = -5460004244545858740L;

    @TableId(type = IdType.ASSIGN_ID)
    @TableField(value = "news_id")
    private Long newsId;

    @TableField(value = "news_name")
    private String name;

    @TableField(value = "news_url")
    private String url;

    @TableField(value = "news_author")
    private String author;

    @TableField(value = "news_content")
    private String content;

    @TableField(value = "news_category")
    private Integer category;

    @TableField(value = "news_desc")
    private String ms;

    @TableField(value = "news_createTime")
    private String createTime;



}
