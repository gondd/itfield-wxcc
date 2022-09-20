package cn.itfield.wxcc.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Userinfo {
    private Login login;
    private List permissions;
    private List  menus;
}
