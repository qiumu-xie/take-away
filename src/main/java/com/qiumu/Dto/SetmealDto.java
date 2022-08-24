package com.qiumu.Dto;

import com.qiumu.pojo.Setmeal;
import com.qiumu.pojo.SetmealDish;
import lombok.Data;
import java.util.List;

@Data
public class SetmealDto extends Setmeal {

    private List<SetmealDish> setmealDishes;

    private String categoryName;
}
