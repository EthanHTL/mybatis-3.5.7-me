package org.myatis.example.entity;

import lombok.Data;

import java.util.Date;

@Data
public class Blog {
    private Integer id;

    private String title;

    private String author;

    private Date createTime;
}
