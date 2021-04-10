package xyz.mydev.orm.mybatisplus.example.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import xyz.mydev.orm.mybatisplus.example.domain.User;

/**
 * @author zhaosp
 */
public interface UserMapper extends BaseMapper<User> {

  User find(Long id, String name);

}