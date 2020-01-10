package xyz.mydev.transaction.service;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import xyz.mydev.transaction.RootTest;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author ZSP
 */
public class CasServiceTest extends RootTest {
  @Autowired
  private CasService casService;
  @Autowired
  private JdbcTemplate jdbcTemplate;

  @Test
  public void compareAndAddAge() {
    Thread threadA = new Thread(() -> casService.compareAndAddAge(1, 1), "threadA");
    Thread threadB = new Thread(() -> casService.compareAndAddAge(1, 1), "threadB");
    threadA.start();
    threadB.start();
    try {
      threadA.join();
      threadB.join();
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    System.out.println("finish..");
  }

  @Test
  public void testJdbcMapping() throws SQLException {
    Connection conn = Objects.requireNonNull(jdbcTemplate.getDataSource()).getConnection();
    String sql = "SELECT * FROM  config_discount where 1=2";
    Statement stmt = conn.createStatement();
    ResultSet rs = stmt.executeQuery(sql);
    ResultSetMetaData rsd = rs.getMetaData();
    int columnCount = rsd.getColumnCount();
    Map<String, Integer> columnType = new LinkedHashMap<>();
    for (int i = 1; i <= columnCount; i++) {
      columnType.put(rsd.getColumnName(i).toLowerCase(), rsd.getColumnType(i));
    }

    System.out.println(columnType);
  }

  @Test
  public void testDate() throws SQLException {
    Date date = new Date();
    System.out.println(date.toInstant());
  }

  @Test
  public void testTimeStamp() throws SQLException {
    Date date = new Date();
    System.out.println(date.toInstant());
  }
}