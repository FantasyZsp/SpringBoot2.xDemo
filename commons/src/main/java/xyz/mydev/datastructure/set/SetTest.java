package xyz.mydev.datastructure.set;

import java.util.HashSet;
import java.util.Set;

/**
 * @author zhao   2018/08/13 12:13
 * @description
 */
public class SetTest {
  public static void main(String[] args) {
    Result result2 = new Result("resultId2", "result2");
    Result result = new Result("resultId", "result1");
    Result result3 = new Result("resultId3", "result3");
    Set<Result> resultSet = new HashSet<>();
    resultSet.add(result);


    SetDemo setDemo = new SetDemo();
    setDemo.setId("1");
    setDemo.setName("name");
    setDemo.setResultSet(resultSet);
    System.out.println(setDemo);

    resultSet.add(result2);
    setDemo.setResultSet(resultSet);
    System.out.println(setDemo);

    resultSet.add(result3);
    setDemo.setResultSet(resultSet);
    setDemo.setResultSet(resultSet);
    setDemo.setResultSet(resultSet);
    setDemo.setResultSet(resultSet);
    setDemo.setResultSet(resultSet);
    setDemo.setResultSet(resultSet);
    System.out.println(setDemo);
  }
}
