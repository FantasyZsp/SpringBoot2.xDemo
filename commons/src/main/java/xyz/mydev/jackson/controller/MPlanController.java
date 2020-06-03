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
import xyz.mydev.jackson.mapper.ManyPlanMapper;
import xyz.mydev.jackson.relationship.many2one_un.ManyPlan;
import xyz.mydev.jackson.relationship.many2one_un.OneUser;
import xyz.mydev.jackson.repository.MPlanRepository;
import xyz.mydev.jackson.repository.OUserRepository;
import xyz.mydev.jackson.vo.MPlanVO;

import java.io.IOException;
import java.util.List;

/**
 * @author zhao
 * @date 2018/09/07 15:00
 * @description 测试序列化
 */
@RestController
@RequestMapping("/mplan")
public class MPlanController {

  @Autowired
  private ObjectMapper objectMapper;

  @Autowired
  private MPlanRepository mPlanRepository;

  @Autowired
  private OUserRepository oUserRepository;

  @Autowired
  private ManyPlanMapper manyPlanMapper;

  Logger logger = LoggerFactory.getLogger(this.getClass());

  @PostMapping
  public ManyPlan createMPlan(@RequestBody ManyPlan manyPlan) throws IOException {
    OneUser oneUser = manyPlan.getOneUser();
    oUserRepository.save(oneUser);
    manyPlan.setOneUser(oneUser);
    manyPlan = mPlanRepository.save(manyPlan);
    return manyPlan;
  }

  @GetMapping
  public List<ManyPlan> getAllMPlans() throws IOException {
    List<ManyPlan> list = mPlanRepository.findAll();
    return list;
  }

  @GetMapping("/withuser/{id}")
  public ManyPlan getPlanWithUser(@PathVariable Long id) throws IOException {
    ManyPlan manyPlan = mPlanRepository.findById(id).orElseThrow();

    return manyPlan;
  }

  @GetMapping("/{id}")
  public MPlanVO getPlan(@PathVariable Long id) throws IOException {
    ManyPlan manyPlan = mPlanRepository.findById(id).orElseThrow();

    return manyPlanMapper.toMPlanVO(manyPlan);
  }

  @GetMapping("/plan-code/{id}")
  public String getPlanCodeByPlanId(@PathVariable Long id) throws IOException {
    String code = mPlanRepository.findCodeById(id);
    return code;
  }

  @GetMapping("/plan-username-/{id}")
  public String getUserNameByPlanId(@PathVariable Long id) throws IOException {
    ManyPlan manyPlan = mPlanRepository.findOne(id);
    String username = manyPlan.getOneUser().getUsername();
    return username;
  }

  @GetMapping("/user/{id}")
  public Long getUserId(@PathVariable Long id) throws IOException {
    Long userId = mPlanRepository.findUserIdById(id);
    return userId;
  }

}
