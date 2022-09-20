package cn.itfield.wxcc.doc.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AggrTermsVo {
    private String key;
    private Long count;
}
