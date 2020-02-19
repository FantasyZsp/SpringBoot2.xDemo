package xyz.mydev.jdk.stream;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import xyz.mydev.beans.domain.Person;
import xyz.mydev.util.JsonUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author ZSP
 * @date 2018/11/05 17:23
 * @description
 */
public class FlapMapOps {
  @Autowired
  ObjectMapper objectMapper;

  public static void main(String[] args) throws IOException {
    String[] arrays = {"this", "is", "a", "demo"};

//        System.out.println("====================");
//        Stream<String[]> stream2 = Stream.of(arrays).map(s -> s.split(""));
//        stream2.findFirst().ifPresent(System.out::println);
//
//        Stream<String[]> stream4 = stream2;
//        stream4.forEach(System.out::println);

    List<Person> list = new ArrayList<>();
    list.add(null);
    list.add(null);
    list.add(null);
    list.add(new Person());


    ArrayList<Person> notNull = list.stream().collect(ArrayList::new, (left, right) -> {
      if (right != null) {
        left.add(right);
      }
    }, ArrayList::addAll);

    System.out.println("notnulls");
    System.out.println(JsonUtil.obj2StringPretty(notNull));
    System.out.println("notnullsend");


    System.out.println(list);
    ObjectMapper objectMapper = new ObjectMapper();
    System.out.println(objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(list));

    String arrayss = "[ \"this\", \"is\", \"a\", \"demo\" ]";
    System.out.println(arrayss);
    System.out.println(objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(arrays));
    System.out.println(objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(arrayss));

    List list1 = objectMapper.readValue(arrayss, new TypeReference<List<String>>() {
    });
    System.out.println("================");
    System.out.println(objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(list1));


  }
}
