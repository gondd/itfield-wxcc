package cn.itfield.wxcc.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <p>
 * 登录表
 * </p>
 *
 * @author mr.wen
 * @since 2022-08-07
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Login {

    private static final long serialVersionUID = 1L;

    private Long id;

    private String username;

    private String password;

    /**
     * 0是后台，1是前台
     */
    private Integer type=1;

    private Boolean enabled=true;

    private Boolean accountNonExpired=true;

    private Boolean credentialsNonExpired=true;

    private Boolean accountNonLocked=true;

    /**
     * 对应Oauth2客户端详情ID
     */
    private String clientId;

    private String clientSecret;

    private String avatar;

}
