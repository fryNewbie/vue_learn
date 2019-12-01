package com.example.demo.book.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.time.LocalDateTime;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * <p>
 * 
 * </p>
 *
 * @author fry
 * @since 2019-11-26
 */
@Data

public class Book {

    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.AUTO)
    private Integer Id;
    /**
     * 图书编号
     */
    private String bookId;

    /**
     * 书名
     */
    private String bookName;

    /**
     * 出版日期
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date createTime;

    /**
     * 作者
     */
    private String author;

    /**
     * 价格
     */
    private Double price;
}
