package xyz.mydev.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import xyz.mydev.domain.Girl;
import xyz.mydev.repository.GirlRepository;

import java.util.List;

@RestController
public class GirlController {

  @Autowired
  GirlRepository girlRepository;

  @GetMapping(value = "/girls")
  public List<Girl> girlList() {
    return girlRepository.findAll();
  }

  @PostMapping(value = "/girls")
  public Girl girlAdd(@RequestParam(value = "age") Integer age,
                      @RequestParam(value = "cupSize") String cupSize) {
    Girl girl = new Girl();
    girl.setAge(age);
    girl.setCupSize(cupSize);
    return girlRepository.save(girl);
  }

  @GetMapping(value = "/girls/{id}")
  public Girl girlFind(@PathVariable(value = "id") Integer id) {
    return girlRepository.findById(id).orElseThrow();
  }

  @PutMapping(value = "/girls/{id}")
  public Girl girlUpdate(@PathVariable(value = "id") Integer id,
                         @RequestParam(value = "age") Integer age,
                         @RequestParam(value = "cupSize") String cupSize) {
    Girl girl = new Girl();
    girl.setId(id);
    girl.setAge(age);
    girl.setCupSize(cupSize);
    return girlRepository.save(girl);
  }

  @DeleteMapping(value = "/girls/{id}")
  public void girlDelete(@PathVariable(value = "id") Integer id) {
    girlRepository.deleteById(id);
  }
}
