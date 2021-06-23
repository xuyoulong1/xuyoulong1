package com.swt.jxproject.controller;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.swt.jxproject.annotation.NoCheck;
import com.swt.jxproject.dto.ResultBody;
import com.swt.jxproject.entity.Yjs_ident_card;
import com.swt.jxproject.service.Yjs_ident_cardService;
import com.swt.jxproject.utils.NowDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author xyl
 * @since 2021-06-18
 */
@RestController
@RequestMapping("/yjs_ident_card")
public class Yjs_ident_cardController {

    @Autowired
    private Yjs_ident_cardService yjs_ident_cardService;


}

