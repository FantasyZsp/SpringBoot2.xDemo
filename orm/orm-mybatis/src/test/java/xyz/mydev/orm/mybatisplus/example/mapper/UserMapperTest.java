package xyz.mydev.orm.mybatisplus.example.mapper;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import xyz.mydev.orm.mybatisplus.example.SampleTest;
import xyz.mydev.orm.mybatisplus.example.domain.User;

import java.util.List;

/**
 * @author ZSP
 */
public class UserMapperTest extends SampleTest {

  @Autowired
  private UserMapper userMapper;

  @Test
  public void testSelect() {
    System.out.println(("----- selectAll method test ------"));
    List<User> userList = userMapper.selectList(null);
    Assert.assertEquals(5, userList.size());
    userList.forEach(System.out::println);
  }

  @Test
  public void testSelectByMapper() {
    User jone = userMapper.find(1L, "Jone");
    Assert.assertEquals("test1@baomidou.com", jone.getEmail());
    System.out.println(jone);
  }


}