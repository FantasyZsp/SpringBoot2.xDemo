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
import xyz.mydev.jackson.mapper.OneUserMapper;
import xyz.mydev.jackson.relationship.many2one_un.OneUser;
import xyz.mydev.jackson.repository.OUserRepository;
import xyz.mydev.jackson.vo.UserWithPlansVO;
import xyz.mydev.jackson.vo.UserWithPlansVO2;

import java.io.IOException;
import java.util.List;

/**
 * @author  zhao
 * @date  2018/09/07 15:00
 * @description  测试序列化
 */
@RestController
@RequestMapping("/ouser")
public class OUserController {

  @Autowired
  private ObjectMapper objectMapper;

  @Autowired
  private OUserRepository oUserRepository;

  @Autowired
  private OneUserMapper oneUserMapper;

  Logger logger = LoggerFactory.getLogger(this.getClass());

  @PostMapping
  public OneUser createOUser(@RequestBody OneUser user) throws IOException {
    user = oUserRepository.save(user);
    return user;
  }

  @GetMapping
  public List<OneUser> getAllOUsers() throws IOException {
    List<OneUser> list = oUserRepository.findAll();
    return list;
  }

  @GetMapping("/1/{id}")
  public UserWithPlansVO getOUser(@PathVariable Long id) throws IOException {
    OneUser oneUser = oUserRepository.findById(id).orElseThrow();
    return oneUserMapper.toDTO(oneUser);
  }

  @GetMapping("/2/{id}")
  public UserWithPlansVO2 getOUser2(@PathVariable Long id) throws IOException {
    OneUser oneUser = oUserRepository.findById(id).orElseThrow();
    return oneUserMapper.toDTO2(oneUser);
  }
}
