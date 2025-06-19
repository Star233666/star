package com.mapper;

import com.pojo.book;
import org.example.BookXx;

import java.util.List;
import java.util.Map;

public interface userbookmapper {
    List<Map<String, Object>> getAllBookCopies();
}
