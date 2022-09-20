package cn.itfield.wxcc.service.impl;

import cn.itfield.wxcc.Dto.LoginDto;
import cn.itfield.wxcc.domain.Login;
import cn.itfield.wxcc.domain.Menu;
import cn.itfield.wxcc.domain.Permission;
import cn.itfield.wxcc.domain.Userinfo;
import cn.itfield.wxcc.exception.GlobalException;
import cn.itfield.wxcc.mapper.LoginLogMapper;
import cn.itfield.wxcc.mapper.LoginMapper;
import cn.itfield.wxcc.mapper.MenuMapper;
import cn.itfield.wxcc.mapper.PermissionMapper;
import cn.itfield.wxcc.result.JsonResult;
import cn.itfield.wxcc.service.ILoginService;
import cn.itfield.wxcc.utils.jwt.JwtUtils;
import cn.itfield.wxcc.utils.jwt.RsaUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;

import java.security.PrivateKey;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 登录表 服务实现类
 * </p>
 *
 * @author mr.wen
 * @since 2022-08-07
 */
@Service
public class LoginServiceImpl extends ServiceImpl<LoginMapper, Login> implements ILoginService {
    @Autowired
    private LoginMapper loginMapper;
    @Autowired
    private MenuMapper menuMapper;
    @Autowired
    private PermissionMapper permissionMapper;
    @Autowired
    private LoginLogMapper loginLogMapper;
    @Override
    public JsonResult common(LoginDto loginDto){
        Login login=loginMapper.selectbyusername(loginDto.getUsername());
        if(login==null){
            throw new GlobalException("账号不存在");
        }
        if(!login.getPassword().equals(loginDto.getPassword())){
            throw new GlobalException("账号或密码错误");
        }
        //查询所有权限
        List<Permission> ps =permissionMapper.bypermission(login.getId());
        System.out.println(ps);
        List<Menu> menus = menuMapper.bymenu(login.getId());
        System.out.println(menus);
        Userinfo userinfo = new Userinfo(login, ps, menus);
        PrivateKey privateKeyPath=null;
        String s=null;
        try {
            privateKeyPath = RsaUtils
                    .getPrivateKey(FileCopyUtils
                            .copyToByteArray(this.getClass().getClassLoader().getResourceAsStream("hrm_auth_rsa")));
            s = JwtUtils.generateTokenExpireInMinutes(userinfo,
                    privateKeyPath, 30);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Map<String, Object> map = new HashMap<>();
        map.put("login",login);
        map.put("menus",menus);
        map.put("Permission",ps);
        map.put("token",s);
       return JsonResult.me().setData(map);

    }

}
