package com.qiumu.controll;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.qiumu.common.BaseContext;
import com.qiumu.common.R;
import com.qiumu.pojo.ShoppingCart;
import com.qiumu.service.ShoppingCartService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/shoppingCart")
public class ShoppingCartController {

    @Autowired
    private ShoppingCartService cartService;

    @GetMapping("/list")
    public R<List<ShoppingCart>> list(HttpServletRequest request) {

        LambdaQueryWrapper<ShoppingCart> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ShoppingCart::getUserId, request.getSession().getAttribute("user"))
                .orderByAsc(ShoppingCart::getCreateTime);

        List<ShoppingCart> list = cartService.list(queryWrapper);

        return R.success(list);
    }

    @PostMapping("/add")
    public R<ShoppingCart> add(HttpServletRequest request, @RequestBody ShoppingCart shoppingCart) {

        Long user = (Long) request.getSession().getAttribute("user");

        LambdaQueryWrapper<ShoppingCart> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ShoppingCart::getUserId, user)
                .eq(shoppingCart.getDishId() != null, ShoppingCart::getDishId, shoppingCart.getDishId())
                .eq(shoppingCart.getSetmealId() != null, ShoppingCart::getSetmealId, shoppingCart.getSetmealId());
        ShoppingCart cart = cartService.getOne(queryWrapper);

        if (cart == null) {
            shoppingCart.setUserId(user);
            shoppingCart.setCreateTime(LocalDateTime.now());
            cartService.save(shoppingCart);
            return R.success(shoppingCart);
        }else {
            Integer number = cart.getNumber();
            cart.setNumber(number + 1);
            cartService.updateById(cart);
            return R.success(cart);
        }

    }

    @PostMapping("/sub")
    public R<ShoppingCart> sub(HttpServletRequest request, @RequestBody ShoppingCart shoppingCart){

        Long user = (Long) request.getSession().getAttribute("user");

        LambdaQueryWrapper<ShoppingCart> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ShoppingCart::getUserId, user)
                .eq(shoppingCart.getDishId() != null, ShoppingCart::getDishId, shoppingCart.getDishId())
                .eq(shoppingCart.getSetmealId() != null, ShoppingCart::getSetmealId, shoppingCart.getSetmealId());
        ShoppingCart cart = cartService.getOne(queryWrapper);
        Integer number = cart.getNumber();

        if (number == 1) {
            cart.setNumber(0);
            cartService.removeById(cart);
        }else {
            cart.setNumber(number - 1);
            cartService.updateById(cart);
        }
        return R.success(cart);

    }
    @DeleteMapping("/clean")
    public R<String> clean(){

        LambdaQueryWrapper<ShoppingCart> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ShoppingCart::getUserId, BaseContext.getEmpId());
        cartService.remove(queryWrapper);

        return R.success("ok");
    }
}
