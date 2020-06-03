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
import xyz.mydev.jackson.relationship.one2one_bid.Plan;
import xyz.mydev.jackson.relationship.one2one_bid.User;
import xyz.mydev.jackson.repository.JobQueueRepository;
import xyz.mydev.jackson.repository.PlanRepository;
import xyz.mydev.jackson.repository.UserRepository;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

/**
 * @author zhao
 * @date 2018/09/07 15:00
 * @description 测试序列化
 */
@RestController
@RequestMapping("/plan")
public class PlanController {

  @Autowired
  private ObjectMapper objectMapper;

  @Autowired
  private JobQueueRepository jobQueueRepository;

  @Autowired
  private PlanRepository planRepository;

  @Autowired
  private UserRepository userRepository;

  Logger logger = LoggerFactory.getLogger(this.getClass());

  @PostMapping
  public Plan createTimer(@RequestBody Plan plan) throws IOException {
    logger.info("test...");
    User user = plan.getUser();
    userRepository.save(user);
    plan.setUser(user);
    plan = planRepository.save(plan);
    return plan;
  }

  @GetMapping
  public HashMap<String, Object> getAllPlansWithUser() throws IOException {
    List<Plan> list = planRepository.findAll();
    HashMap<String, Object> map = new HashMap<>();
    map.put("list", list);
//        objectMapper.enable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    return map;
  }

  @GetMapping("/withuser/{id}")
  public Plan getPlanWithUser(@PathVariable Long id) throws IOException {
    Plan plan = planRepository.findOneById(id);

    return plan;
  }

  @GetMapping("/plan-code/{id}")
  public String getPlanCodeByPlanId(@PathVariable Long id) throws IOException {
    Plan plan = planRepository.findOneById(id);
    String code = plan.getCode();

    return code;
  }

  @GetMapping("/plan-username-/{id}")
  public String getUserNameByPlanId(@PathVariable Long id) throws IOException {
    Plan plan = planRepository.findOneById(id);
    String username = plan.getUser().getUsername();

    return username;
  }

  @GetMapping("/user/{id}")
  public Long getUserId(@PathVariable Long id) throws IOException {
    Long userId = planRepository.findUserIdById(id);

    return userId;
  }

}
