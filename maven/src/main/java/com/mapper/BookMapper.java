package com.mapper;

import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface BookMapper {
//Book
    int insertBook(@Param("bookName") String bookName, @Param("authorName") String authorName,
                   @Param("publishTime") String publishTime, @Param("condition") String condition);
    // 查询所有图书信息
    List<Map<String, Object>> selectAllBooks();


//    BookXx
// 检查图书状态
    String checkBookStatus(@Param("bookName") String bookName);

    // 获取图书详细信息
    Map<String, Object> getBookInfo(@Param("bookName") String bookName);

    // 更新图书状态
    int updateBookStatus(@Param("bookName") String bookName, @Param("newStatus") String newStatus);

    // 检查副本是否存在
    int checkBookCopyExists(@Param("bookName") String bookName);

    // 插入图书副本
    int insertBookCopy(@Param("bookName") String bookName,
                       @Param("authorName") String authorName,
                       @Param("publishTime") String publishTime,
                       @Param("status") String status);

    // 更新图书副本状态
    int updateBookCopyStatus(@Param("bookName") String bookName, @Param("newStatus") String newStatus);
}

