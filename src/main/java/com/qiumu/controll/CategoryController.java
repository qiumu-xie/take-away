package com.qiumu.controll;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qiumu.common.R;
import com.qiumu.pojo.Category;
import com.qiumu.pojo.Employee;
import com.qiumu.pojo.PageBean;
import com.qiumu.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController()
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/page")
    public R<PageBean<Category>> list(Integer page, Integer pageSize) {

        Page<Category> pages = new Page<>(page, pageSize);
        LambdaQueryWrapper<Category> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.orderByAsc(Category::getSort);
        long total = categoryService.count();
        List<Category> records = categoryService.page(pages, queryWrapper).getRecords();

        PageBean<Category> pageBean = new PageBean<>(total, records);

        return R.success(pageBean);
    }

    @GetMapping("/list")
    public R<List<Category>> listR(Integer type){

        LambdaQueryWrapper<Category> queryWrapper =new LambdaQueryWrapper<>();
        queryWrapper.eq(type != null,Category::getType,type);
        List<Category> list = categoryService.list(queryWrapper);

        return R.success(list);
    }

    @PostMapping
    public R<String> add(@RequestBody Category category) {

        categoryService.save(category);

        return R.success("添加成功");
    }

    @DeleteMapping
    public R<String> del(@RequestParam Long ids) {

        categoryService.delete(ids);

        return R.success("成功删除");
    }
    @PutMapping
    public R<String> update(@RequestBody Category category){

        categoryService.updateById(category);

        return R.success("修改成功");
    }
}
