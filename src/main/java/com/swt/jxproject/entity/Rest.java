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
public class Rest {
    private String code;
    private String count;
    private String message;
    private String mobile;
    private String sid;
}
