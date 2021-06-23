package com.swt.jxproject.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.swt.jxproject.entity.emp;
import com.swt.jxproject.mapper.empmapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class se extends ServiceImpl<empmapper, emp> {
    public List<emp> test(){
        
       return  baseMapper.getusername();
    }
    public List<emp> test1(){
        //添加
        int insert = baseMapper.insert(new emp());
        //删除map里面就是删除的条件，键就是数据库的字段值就是值
        HashMap<String, Object> map = new HashMap<>();
        map.put("id","1");
        int i = baseMapper.deleteByMap(map);
        //根据id删除
        baseMapper.deleteById("1");
        //根据集合删除
        List <Integer>list = new ArrayList<>();
        list.add(1);
        list.add(2);
        baseMapper.deleteBatchIds(list);
        //根据id查询
        emp emp = baseMapper.selectById(1);
        //根据指定的条件查询
        HashMap<String, Object> map1 = new HashMap<>();
        map1.put("name","张三");
        List<com.swt.jxproject.entity.emp> emps = baseMapper.selectByMap(map1);
        //根据list查询
        List<Integer> list1 = new ArrayList<>();
        list1.add(1);
        list1.add(2);
        baseMapper.selectBatchIds(list1);
        return  null;
    }

    public List<emp> test3(){
        QueryWrapper<emp> Wrapper = new QueryWrapper<>();
        Wrapper.eq("id",1);//eq就是相当于等于
        Wrapper.or();//这个就是或者
        Wrapper.eq("name","张三");//这个语句就是id等于1或者名字是张三的
        List<emp> emps = baseMapper.selectList(Wrapper);
        Page<emp> page = new Page<>(2,2);

        return  emps;
    }
    public IPage<emp> test4(){

        Page<emp> objectPage = new Page<>(1, 5);
        IPage<emp> empIPage = baseMapper.selectPage(objectPage, null);

        return  empIPage;
    }


}
