package com.example.demo.book.controller;

import java.util.List;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.book.entity.Book;
import com.example.demo.book.service.IBookService;

import com.example.demo.model.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author fry
 * @since 2019-11-26
 */
@RestController
@RequestMapping("/book")
@CrossOrigin
public class BookController {

    @Autowired
    private IBookService bookService;

    /**
     * 分页查询
     * @param limit
     * @param sort
     * @param page
     * @param bookName
     * @return
     */
    @GetMapping("/list")
    public Result books(@RequestParam("limit") Integer limit,
                        @RequestParam("sort") String sort,
                        @RequestParam("page") Integer page,
                        String bookName
                        ) {
        QueryWrapper<Book> wrapper=new QueryWrapper<>();
        if(sort.equals("-id")){
            wrapper.orderByDesc("id");
        }
        if(bookName != null && bookName.length() != 0){
            wrapper.like("book_name",bookName);
        }
        Page<Book> bookIPage=new Page<>(page,limit);
        IPage<Book> books=bookService.page(bookIPage,wrapper);
        return Result.createSuccessResult().setData(books).setCode(20000);
    }

    /**
     * 根据id删除
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    public Result deleteBook(@PathVariable("id") Integer id){
        bookService.removeById(id);
        return Result.createSuccessResult().setCode(20000).setMessage("删除成功");
    }

    /**
     * 新增
     * @param book
     * @return
     */
    @PostMapping()
    public Result addBook(Book book){
        bookService.save(book);
        return Result.createSuccessResult().setMessage("添加成功");
    }

    /**
     * 按id修改
     * @param book
     * @return
     */
    @PutMapping()
    public Result updateBook(Book book){
        bookService.updateById(book);
        return Result.createSuccessResult().setMessage("修改成功");
    }

}
