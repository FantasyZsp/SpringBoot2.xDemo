package xyz.mydev.mysql;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * @author zhaosp
 * @see <a href="https://blog.csdn.net/qq_34246546/article/details/81736342">blog</a>
 */
@Slf4j
@SuppressWarnings("all")
public class MysqlEntityUtil {
  private static final MysqlEntityUtil INSTANCE = new MysqlEntityUtil();

  private String tableName;// 表名
  private String[] colNames; // 列名数组
  private String[] colTypes; // 列名类型数组
  private int[] colSizes; // 列名大小数组
  private boolean needJava8TimeUtil = false; // 是否需要导入包java.time.*
  private boolean needSql = false; // 是否需要导入包java.sql.*
  private boolean needLombok = true; // 是否需要导入包import lombok.Data;
  private static final SimpleDateFormat SDF = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
  private static final String SQL = "SELECT * FROM ";// 数据库操作

  // TODO 需要修改的地方
  private static final String DATABAES = "avengers";
  private static final String URL = "jdbc:mysql://localhost:3306/avengers?useSSL=false&serverTimezone=Asia/Shanghai";
  private static final String NAME = "root";
  private static final String PASS = "123456";
  private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
  private String packageOutPath = "xyz.mydev.utils.temp.result.entity.mysql";// 指定实体生成所在包的路径
  private String authorName = "zsp";// 作者名字
  private List<String> generateTables = Arrays.asList("user_reward_balance", "user_reward_record");//指定需要生成的表的表名，全部生成设置为null

  /**
   * 类的构造方法
   */
  private MysqlEntityUtil() {
  }

  /**
   * @return
   * @description 生成class的所有内容
   * @author paul
   * @date 2017年8月18日 下午5:30:07
   * @update 2017年8月18日 下午5:30:07
   * @version V1.0
   */
  private String parse() {
    StringBuffer sb = new StringBuffer();
    sb.append("package " + packageOutPath + ";\r\n");
    sb.append("\r\n");
    // 判断是否导入工具包
    if (needJava8TimeUtil) {
      sb.append("import java.time.LocalDateTime;\r\n");
    }
    if (needSql) {
      sb.append("import java.sql.*;\r\n");
    }

    if (needLombok) {
      sb.append("import lombok.Data;\r\n");
    }
    // 注释部分
    sb.append("/**\r\n");
    sb.append(" * table name:  ").append(tableName).append("\r\n");
    sb.append(" * create time: ").append(SDF.format(new Date())).append("\r\n");
    sb.append(" * @author ").append(authorName).append("\r\n");
    sb.append(" */ \r\n");

    sb.append("@Data \r\n");

    // 实体部分
    sb.append("public class ").append(getTransStr(tableName, true)).append("{\r\n\r\n");
    processAllAttrs(sb);// 属性
    sb.append("\r\n");
//    processAllMethod(sb);// get set方法
    //processToString(sb);
    sb.append("}\r\n");
    return sb.toString();
  }

  /**
   * @param sb
   * @description 生成所有成员变量
   * @author paul
   * @date 2017年8月18日 下午5:15:04
   * @update 2017年8月18日 下午5:15:04
   * @version V1.0
   */
  private void processAllAttrs(StringBuffer sb) {
    for (int i = 0; i < colNames.length; i++) {
      sb.append("\tprivate ").append(sqlType2JavaType(colTypes[i])).append(" ").append(getTransStr(colNames[i], false)).append(";\r\n");
    }
  }

//    /**
//     * 重写toString()方法
//     * @param sb
//     */
//    private void processToString(StringBuffer sb) {
//        sb.append("\t@Override\r\n\tpublic String toString() {\r\n");
//        sb.append("\t\treturn \"" +tableName + "{\" + \r\n");
//        for (int i = 0; i < colNames.length; i++) {
//            if (i != 0)
//                sb.append("\t\t\t\", ");
//            if (i == 0)
//                sb.append("\t\t\t\"");
//            sb.append(colNames[i] + "=\" + "
//                    + colNames[i]).append(" + \r\n");
//            if (i == colNames.length - 1) {
//                sb.append("\t\t\t\"}\";\r\n");
//            }
//        }
//        sb.append("\t}\r\n");
//    }

  /**
   * @param sb
   * @description 生成所有get/set方法
   * @author paul
   * @date 2017年8月18日 下午5:14:47
   * @update 2017年8月18日 下午5:14:47
   * @version V1.0
   */
  private void processAllMethod(StringBuffer sb) {
    for (int i = 0; i < colNames.length; i++) {
      sb.append("\tpublic void set").append(getTransStr(colNames[i], true)).append("(").append(sqlType2JavaType(colTypes[i])).append(" ").append(getTransStr(colNames[i], false)).append("){\r\n");
      sb.append("\t\tthis.").append(getTransStr(colNames[i], false)).append("=").append(getTransStr(colNames[i], false)).append(";\r\n");
      sb.append("\t}\r\n");
      sb.append("\tpublic ").append(sqlType2JavaType(colTypes[i])).append(" get").append(getTransStr(colNames[i], true)).append("(){\r\n");
      sb.append("\t\treturn ").append(getTransStr(colNames[i], false)).append(";\r\n");
      sb.append("\t}\r\n");
    }
  }

  /**
   * @param str 传入字符串
   * @return
   * @description 将传入字符串的首字母转成大写
   * @author paul
   * @date 2017年8月18日 下午5:12:12
   * @update 2017年8月18日 下午5:12:12
   * @version V1.0
   */
  private String initCap(String str) {
    char[] ch = str.toCharArray();
    if (ch[0] >= 'a' && ch[0] <= 'z') {
      ch[0] = (char) (ch[0] - 32);
    }
    return new String(ch);
  }

  /**
   * @return
   * @description 将mysql中表名和字段名转换成驼峰形式
   * @author paul
   * @date 2017年8月18日 下午4:55:07
   * @update 2017年8月18日 下午4:55:07
   * @version V1.0
   */
  private String getTransStr(String before, boolean firstChar2Upper) {
    //不带"_"的字符串,则直接首字母大写后返回
    if (!before.contains("_")) {
      return firstChar2Upper ? initCap(before) : before;
    }
    String[] strs = before.split("_");
    StringBuffer after = null;
    if (firstChar2Upper) {
      after = new StringBuffer(initCap(strs[0]));
    } else {
      after = new StringBuffer(strs[0]);
    }
    if (strs.length > 1) {
      for (int i = 1; i < strs.length; i++) {
        after.append(initCap(strs[i]));
      }
    }
    return after.toString();
  }

  /**
   * @return
   * @description 查找sql字段类型所对应的Java类型
   * @author paul
   * @date 2017年8月18日 下午4:55:41
   * @update 2017年8月18日 下午4:55:41
   * @version V1.0
   */
  private String sqlType2JavaType(String sqlType) {
    if ("bit".equalsIgnoreCase(sqlType)) {
      return "Boolean";
    } else if ("tinyint".equalsIgnoreCase(sqlType)) {
      return "Integer";
    } else if ("smallint".equalsIgnoreCase(sqlType)) {
      return "Integer";
    } else if ("int".equalsIgnoreCase(sqlType)) {
      return "Integer";
    } else if ("bigint".equalsIgnoreCase(sqlType)) {
      return "Long";
    } else if ("float".equalsIgnoreCase(sqlType)) {
      return "Float";
    } else if ("decimal".equalsIgnoreCase(sqlType) || "numeric".equalsIgnoreCase(sqlType)
      || "real".equalsIgnoreCase(sqlType) || "money".equalsIgnoreCase(sqlType)
      || "smallmoney".equalsIgnoreCase(sqlType)) {
      return "double";
    } else if ("varchar".equalsIgnoreCase(sqlType) || "char".equalsIgnoreCase(sqlType)
      || "nvarchar".equalsIgnoreCase(sqlType) || "nchar".equalsIgnoreCase(sqlType)
      || "text".equalsIgnoreCase(sqlType)) {
      return "String";
    } else if ("datetime".equalsIgnoreCase(sqlType)) {
      return "LocalDateTime";
    } else if ("image".equalsIgnoreCase(sqlType)) {
      return "Blod";
    } else if ("timestamp".equalsIgnoreCase(sqlType)) {
      return "LocalDateTime";
    }
    return null;
  }

  /**
   * @throws Exception
   * @description 生成方法
   * @author paul
   * @date 2017年8月18日 下午2:04:20
   * @update 2017年8月18日 下午2:04:20
   * @version V1.0
   */
  private void generate() throws Exception {
    //与数据库的连接
    Connection con;
    PreparedStatement pStemt = null;
    Class.forName(DRIVER);
    con = DriverManager.getConnection(URL, NAME, PASS);
    System.out.println("connect database success...");
    //获取数据库的元数据
    DatabaseMetaData db = con.getMetaData();
    //是否有指定生成表，有指定则直接用指定表，没有则全表生成
    List<String> tableNames = new ArrayList<>();
    if (CollectionUtils.isEmpty(generateTables)) {
      //从元数据中获取到所有的表名
      ResultSet rs = db.getTables(DATABAES, null, null, new String[]{"TABLE"});
      while (rs.next()) {
        tableNames.add(rs.getString(3));
      }
    } else {
      for (String tableName : generateTables) {
        tableNames.add(tableName);
      }
    }

    log.info("tables : {}", tableNames);

    String tableSql;
    PrintWriter pw = null;
    for (int j = 0; j < tableNames.size(); j++) {
      tableName = tableNames.get(j);
      tableSql = SQL + tableName;
      pStemt = con.prepareStatement(tableSql);
      ResultSetMetaData rsmd = pStemt.getMetaData();
      int size = rsmd.getColumnCount();
      colNames = new String[size];
      colTypes = new String[size];
      colSizes = new int[size];
      //获取所需的信息
      for (int i = 0; i < size; i++) {
        colNames[i] = rsmd.getColumnName(i + 1);
        colTypes[i] = rsmd.getColumnTypeName(i + 1);
        if ("datetime".equalsIgnoreCase(colTypes[i])) {
          needJava8TimeUtil = true;
        }
        if ("image".equalsIgnoreCase(colTypes[i]) || "text".equalsIgnoreCase(colTypes[i])) {
          needSql = true;
        }
        colSizes[i] = rsmd.getColumnDisplaySize(i + 1);
      }
      //解析生成class的所有内容
      String content = parse();
      //输出生成文件
      File directory = new File("");
      String dirName = directory.getAbsolutePath() + "/utils/src/main/java/" + packageOutPath.replace(".", "/");
      File dir = new File(dirName);
      if (!dir.exists() && dir.mkdirs()) {
        System.out.println("generate dir 【" + dirName + "】");
      }
      String javaPath = dirName + "/" + getTransStr(tableName, true) + ".java";
      FileWriter fw = new FileWriter(javaPath);
      pw = new PrintWriter(fw);
      pw.println(content);
      pw.flush();
      System.out.println("create class 【" + tableName + "】");
    }
    if (pw != null) {
      pw.close();
    }
  }

  /**
   * @param args
   * @description 执行方法
   * @author paul
   * @date 2017年8月18日 下午2:03:35
   * @update 2017年8月18日 下午2:03:35
   * @version V1.0
   */
  public static void main(String[] args) {
    try {
      INSTANCE.generate();
      System.out.println("generate classes success!");
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}