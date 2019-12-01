package com.example.demo.book.service.impl;

import com.example.demo.book.entity.Book;
import com.example.demo.book.mapper.BookMapper;
import com.example.demo.book.service.IBookService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author fry
 * @since 2019-11-26
 */
@Service
public class BookServiceImpl extends ServiceImpl<BookMapper, Book> implements IBookService {

}
