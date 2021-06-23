package com.swt.jxproject.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.swt.jxproject.entity.emp;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface empmapper extends BaseMapper<emp> {
    @Select("SELECT * FROM emp")
    List<emp> getusername();
}
