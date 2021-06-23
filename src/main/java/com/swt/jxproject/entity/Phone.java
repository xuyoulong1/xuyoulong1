package com.swt.jxproject.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Phone {
    private String appKey;
    private String mobile;
    private String text;
}
