package com.joolun.mall.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.joolun.mall.entity.Book;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.security.core.parameters.P;

import java.util.List;


/**
 * 书籍Mapper接口
 *
 * @author Owen
 * @date 2021-12-08
 */
public interface BookMapper extends BaseMapper<Book> {

    @Select("select i.id,i.title from book i where i.id in(select book_id j from course_book j where j.course_id = #{id})")
    List<Book> getListByCourse(@Param("id") Long id);

    @Delete("delete from course_book where course_id = #{id}")
    boolean deleteByCourse(@Param("id") Long id);

    @Select("insert into course_book (course_id,book_id) value (#{courseId},#{bookId})")
    void addRelatedCourse(@Param("courseId") Long id, @Param("bookId") Long bookId);
}
