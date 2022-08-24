package com.qiumu.controll;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.qiumu.common.R;
import com.qiumu.pojo.User;
import com.qiumu.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public R<User> login(HttpServletRequest request,@RequestBody User user){
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getPhone,user.getPhone());
        User one = userService.getOne(queryWrapper);

        if (one == null) {
            userService.save(user);
            request.getSession().setAttribute("user",user.getId());
            return R.success(user);
        }
        request.getSession().setAttribute("user",one.getId());
        return R.success(one);
    }
    @PostMapping("/loginout")
    public R<String> logout(HttpServletRequest request){

        request.getSession().removeAttribute("user");

        return R.success("成功");
    }
}
