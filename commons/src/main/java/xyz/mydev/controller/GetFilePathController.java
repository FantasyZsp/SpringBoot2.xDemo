package xyz.mydev.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URL;

@RestController
public class GetFilePathController {

  @GetMapping("/getjson")
  public String getJson() {
    String path = "";
    System.out.println(getClass().getClassLoader());


    URL url = getClass().getClassLoader().getResource("init.json");
    System.out.println("url  " + url);
    assert url != null;
    path = url.getPath();
    System.out.println("url.getPath(): " + path);
    if (path.contains(":")) {
      path = path.replace("file:/", "");
    }
    System.out.println("replace file:/  ''" + path);
    path = path.replace("\\", "/");
    System.out.println(path);

    return path;
  }
}
