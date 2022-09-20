package cn.itfield.wxcc.utils;

import cn.itfield.wxcc.domain.Login;
import cn.itfield.wxcc.domain.Userinfo;
import cn.itfield.wxcc.utils.jwt.JwtUtils;
import cn.itfield.wxcc.utils.jwt.Payload;
import cn.itfield.wxcc.utils.jwt.RsaUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.FileCopyUtils;

import java.security.PublicKey;
@Component
public class UserContext {
    @Value("${jwt.rsa.pub}")// SPEL表达式读取配置文件中的值
    private String jwtRsaPublic;
    public Login getuser(String token) {

        try {
            PublicKey publicKey = RsaUtils
                    .getPublicKey(FileCopyUtils
                            .copyToByteArray(this.getClass().getClassLoader().getResourceAsStream(jwtRsaPublic)));
            //判断是否有用户信息
            if (token != null) {
                //获取用户信息
                Payload<Userinfo> userinfo = JwtUtils.getInfoFromToken(token, publicKey, Userinfo.class);
                Login login = userinfo.getUserInfo().getLogin();
                return login;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
