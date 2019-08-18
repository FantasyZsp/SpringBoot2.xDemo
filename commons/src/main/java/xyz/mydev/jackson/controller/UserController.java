package xyz.mydev.jackson.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.mydev.jackson.relationship.one2one_bid.User;
import xyz.mydev.jackson.repository.UserRepository;

import java.io.IOException;
import java.util.List;

/**
 * @author zhao
 * @date 2018/09/07 15:00
 * @description 测试序列化
 */
@RestController
@RequestMapping("/user")
public class UserController {

  @Autowired
  private ObjectMapper objectMapper;

  @Autowired
  private UserRepository userRepository;

  Logger logger = LoggerFactory.getLogger(this.getClass());

  @PostMapping
  public User createTimer(@RequestBody User user) throws IOException {
    System.out.println(user);
    logger.info("{}", objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(user));
    user = userRepository.save(user);
    return user;
  }

  @GetMapping
  public List<User> getAllTimes() {
    List<User> list = userRepository.findAll();
    return list;
  }

  @GetMapping("/{id}")
  public User getTime(@PathVariable Long id) {
    User list = userRepository.findById(id).orElseThrow();
    return list;
  }
}
