package com.qiumu.controll;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.qiumu.common.R;
import com.qiumu.exception.CostException;
import com.qiumu.pojo.AddressBook;
import com.qiumu.service.AddressBookService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/addressBook")
public class AddressBookController {

    @Autowired
    private AddressBookService addressBookService;

    @GetMapping("/list")
    public R<List<AddressBook>> list(HttpServletRequest request) {
        Object user = request.getSession().getAttribute("user");

        LambdaQueryWrapper<AddressBook> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(AddressBook::getUserId, user);

        List<AddressBook> addressBooks = addressBookService.list(queryWrapper);

        return R.success(addressBooks);
    }

    @GetMapping("/{id}")
    public R<AddressBook> getById(@PathVariable String id) {

        AddressBook addressBook = addressBookService.getById(id);

        return R.success(addressBook);
    }

    @PostMapping
    public R<String> add(HttpServletRequest request, @RequestBody AddressBook addressBook) {
        Long user = (Long) request.getSession().getAttribute("user");

        addressBook.setUserId(user);

        try {
            addressBookService.save(addressBook);
        } catch (Exception e) {
            throw new CostException("添加失败");
        }

        return R.success("ok");
    }

    @PutMapping
    public R<String> update(@RequestBody AddressBook addressBook) {

        addressBookService.updateById(addressBook);

        return R.success("ok");
    }

    @DeleteMapping
    public R<String> del(@RequestParam Long ids) {

        addressBookService.removeById(ids);

        return R.success("成功删除");
    }

    @PutMapping("/default")
    public R<String> setDefault(@RequestBody AddressBook addressBook) {

        addressBookService.updateDefault(addressBook.getId());

        return R.success("ok");
    }

    @GetMapping("/default")
    public R<AddressBook> getDefault() {
        LambdaQueryWrapper<AddressBook> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(AddressBook::getIsDefault, 1);
        AddressBook addressBook = addressBookService.getOne(queryWrapper);
        if (addressBook == null) {
            return R.error("无默认地址");
        }
        return R.success(addressBook);
    }

}
