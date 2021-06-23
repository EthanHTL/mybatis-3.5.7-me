package org.myatis.example.mapper;

import org.myatis.example.entity.Blog;

/**
 * @description:
 * @author: 黄天亮
 * @create: 2021-06-23 10:41
 **/
public interface BlogMapper {
    Blog findOne(int id);
}
