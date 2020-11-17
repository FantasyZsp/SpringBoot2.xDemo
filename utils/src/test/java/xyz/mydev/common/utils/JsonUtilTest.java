package xyz.mydev.common.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Test;
import xyz.mydev.common.beans.vo.MarkedSerializedTypeStudentVO;
import xyz.mydev.common.beans.vo.NumberVO;
import xyz.mydev.common.beans.vo.StudentVO;

/**
 * @author ZSP
 */
public class JsonUtilTest {
  @Test
  public void test() {
    System.out.println(JsonUtil.obj2String(NumberVO.of(1)));
  }

  public static final ObjectMapper DEFAULT_OBJECT_MAPPER = JsonUtil.objectMapper;
  public static final ObjectMapper UPPER_CAMEL_CASE_OBJECT_MAPPER = JsonUtil.objectMapper.copy().setPropertyNamingStrategy(PropertyNamingStrategy.UPPER_CAMEL_CASE);
  public static final ObjectMapper LOWER_CAMEL_CASE_OBJECT_MAPPER = JsonUtil.objectMapper.copy().setPropertyNamingStrategy(PropertyNamingStrategy.LOWER_CAMEL_CASE);

  @Test
  public void serialization() throws Exception {
    StudentVO studentVO = new StudentVO();

    studentVO.setStudentAddress("地址");
    studentVO.setStudentGrade("年级");
    studentVO.setStudentName("学员姓名");
    studentVO.setTimestamp(1000L);

    System.out.println(DEFAULT_OBJECT_MAPPER.writeValueAsString(studentVO));
    System.out.println(UPPER_CAMEL_CASE_OBJECT_MAPPER.writeValueAsString(studentVO));
    System.out.println(LOWER_CAMEL_CASE_OBJECT_MAPPER.writeValueAsString(studentVO));

    // 将以类上打的注解为主
    MarkedSerializedTypeStudentVO copy = MarkedSerializedTypeStudentVO.copy(studentVO);

    System.out.println(DEFAULT_OBJECT_MAPPER.writeValueAsString(copy));
    System.out.println(UPPER_CAMEL_CASE_OBJECT_MAPPER.writeValueAsString(copy));
    System.out.println(LOWER_CAMEL_CASE_OBJECT_MAPPER.writeValueAsString(copy));


    MarkedSerializedTypeStudentVO markedSerializedTypeStudentVO = UPPER_CAMEL_CASE_OBJECT_MAPPER.readValue(DEFAULT_OBJECT_MAPPER.writeValueAsString(studentVO), MarkedSerializedTypeStudentVO.class);

    System.out.println(markedSerializedTypeStudentVO);
  }

  @Test
  public void testMapJson() {
    String json = "{\n" +
      "    \"data\": [{\n" +
      "            \"periodCardBankId\": \"6732227532634066944\",\n" +
      "            \"configOrdersId\": \"6730060064540987392\",\n" +
      "            \"configPeriodCardId\": \"6729690792627146752\",\n" +
      "            \"studentName\": \"冯泽\",\n" +
      "            \"grade\": \"八年级\",\n" +
      "            \"periodCardName\": \"历史1104班课\",\n" +
      "            \"residueValidPeriod\": 80,\n" +
      "            \"invalidPeriod\": 0,\n" +
      "            \"familyOwner\": {\n" +
      "                \"id\": \"6722698813519302656\",\n" +
      "                \"name\": \"啦啦啦\"\n" +
      "            },\n" +
      "            \"schoolName\": \"学军小学\"\n" +
      "        }, {\n" +
      "            \"periodCardBankId\": \"6732227592897826816\",\n" +
      "            \"configOrdersId\": \"6730060064540987392\",\n" +
      "            \"configPeriodCardId\": \"6729690792627146752\",\n" +
      "            \"studentName\": \"钱全\",\n" +
      "            \"grade\": \"八年级\",\n" +
      "            \"periodCardName\": \"历史1104班课\",\n" +
      "            \"residueValidPeriod\": 40,\n" +
      "            \"invalidPeriod\": 0,\n" +
      "            \"familyOwner\": {\n" +
      "                \"id\": \"6722698813519302656\",\n" +
      "                \"name\": \"啦啦啦\"\n" +
      "            },\n" +
      "            \"schoolName\": \"学军小学\"\n" +
      "        }, {\n" +
      "            \"periodCardBankId\": \"6732227598769852416\",\n" +
      "            \"configOrdersId\": \"6730060064540987392\",\n" +
      "            \"configPeriodCardId\": \"6729690792627146752\",\n" +
      "            \"studentName\": \"张宇\",\n" +
      "            \"grade\": \"八年级\",\n" +
      "            \"periodCardName\": \"历史1104班课\",\n" +
      "            \"residueValidPeriod\": 20,\n" +
      "            \"invalidPeriod\": 0,\n" +
      "            \"familyOwner\": {\n" +
      "                \"id\": \"6722698813519302656\",\n" +
      "                \"name\": \"啦啦啦\"\n" +
      "            },\n" +
      "            \"schoolName\": \"学军小学\"\n" +
      "        }, {\n" +
      "            \"periodCardBankId\": \"6732227591127830528\",\n" +
      "            \"configOrdersId\": \"6730060064540987392\",\n" +
      "            \"configPeriodCardId\": \"6729690792627146752\",\n" +
      "            \"studentName\": \"吕佑\",\n" +
      "            \"grade\": \"八年级\",\n" +
      "            \"periodCardName\": \"历史1104班课\",\n" +
      "            \"residueValidPeriod\": 40,\n" +
      "            \"invalidPeriod\": 0,\n" +
      "            \"familyOwner\": {\n" +
      "                \"id\": \"6722698813519302656\",\n" +
      "                \"name\": \"啦啦啦\"\n" +
      "            },\n" +
      "            \"schoolName\": \"学军小学\"\n" +
      "        }, {\n" +
      "            \"periodCardBankId\": \"6732227516515356672\",\n" +
      "            \"configOrdersId\": \"6730060064540987392\",\n" +
      "            \"configPeriodCardId\": \"6729690792627146752\",\n" +
      "            \"studentName\": \"吴临\",\n" +
      "            \"grade\": \"八年级\",\n" +
      "            \"periodCardName\": \"历史1104班课\",\n" +
      "            \"residueValidPeriod\": 60,\n" +
      "            \"invalidPeriod\": 0,\n" +
      "            \"familyOwner\": {\n" +
      "                \"id\": \"6722698813519302656\",\n" +
      "                \"name\": \"啦啦啦\"\n" +
      "            },\n" +
      "            \"schoolName\": \"学军小学\"\n" +
      "        }, {\n" +
      "            \"periodCardBankId\": \"6732227528532037632\",\n" +
      "            \"configOrdersId\": \"6730060064540987392\",\n" +
      "            \"configPeriodCardId\": \"6729690792627146752\",\n" +
      "            \"studentName\": \"李任\",\n" +
      "            \"grade\": \"八年级\",\n" +
      "            \"periodCardName\": \"历史1104班课\",\n" +
      "            \"residueValidPeriod\": 60,\n" +
      "            \"invalidPeriod\": 0,\n" +
      "            \"familyOwner\": {\n" +
      "                \"id\": \"6722698813519302656\",\n" +
      "                \"name\": \"啦啦啦\"\n" +
      "            },\n" +
      "            \"schoolName\": \"学军小学\"\n" +
      "        }, {\n" +
      "            \"periodCardBankId\": \"6732227524497117184\",\n" +
      "            \"configOrdersId\": \"6730060064540987392\",\n" +
      "            \"configPeriodCardId\": \"6729690792627146752\",\n" +
      "            \"studentName\": \"孙任\",\n" +
      "            \"grade\": \"八年级\",\n" +
      "            \"periodCardName\": \"历史1104班课\",\n" +
      "            \"residueValidPeriod\": 40,\n" +
      "            \"invalidPeriod\": 0,\n" +
      "            \"familyOwner\": {\n" +
      "                \"id\": \"6722698813519302656\",\n" +
      "                \"name\": \"啦啦啦\"\n" +
      "            },\n" +
      "            \"schoolName\": \"学军小学\"\n" +
      "        }, {\n" +
      "            \"periodCardBankId\": \"6732227510114848768\",\n" +
      "            \"configOrdersId\": \"6730060064540987392\",\n" +
      "            \"configPeriodCardId\": \"6729690792627146752\",\n" +
      "            \"studentName\": \"尤宏\",\n" +
      "            \"grade\": \"八年级\",\n" +
      "            \"periodCardName\": \"历史1104班课\",\n" +
      "            \"residueValidPeriod\": 60,\n" +
      "            \"invalidPeriod\": 0,\n" +
      "            \"familyOwner\": {\n" +
      "                \"id\": \"6722698813519302656\",\n" +
      "                \"name\": \"啦啦啦\"\n" +
      "            },\n" +
      "            \"schoolName\": \"学军小学\"\n" +
      "        }, {\n" +
      "            \"periodCardBankId\": \"6732227579950010368\",\n" +
      "            \"configOrdersId\": \"6730060064540987392\",\n" +
      "            \"configPeriodCardId\": \"6729690792627146752\",\n" +
      "            \"studentName\": \"李远\",\n" +
      "            \"grade\": \"八年级\",\n" +
      "            \"periodCardName\": \"历史1104班课\",\n" +
      "            \"residueValidPeriod\": 20,\n" +
      "            \"invalidPeriod\": 0,\n" +
      "            \"familyOwner\": {\n" +
      "                \"id\": \"6722698813519302656\",\n" +
      "                \"name\": \"啦啦啦\"\n" +
      "            },\n" +
      "            \"schoolName\": \"学军小学\"\n" +
      "        }, {\n" +
      "            \"periodCardBankId\": \"6732227526485217280\",\n" +
      "            \"configOrdersId\": \"6730060064540987392\",\n" +
      "            \"configPeriodCardId\": \"6729690792627146752\",\n" +
      "            \"studentName\": \"韩钟\",\n" +
      "            \"grade\": \"八年级\",\n" +
      "            \"periodCardName\": \"历史1104班课\",\n" +
      "            \"residueValidPeriod\": 40,\n" +
      "            \"invalidPeriod\": 0,\n" +
      "            \"familyOwner\": {\n" +
      "                \"id\": \"6722698813519302656\",\n" +
      "                \"name\": \"啦啦啦\"\n" +
      "            },\n" +
      "            \"schoolName\": \"学军小学\"\n" +
      "        }, {\n" +
      "            \"periodCardBankId\": \"6732227556390604800\",\n" +
      "            \"configOrdersId\": \"6730060064540987392\",\n" +
      "            \"configPeriodCardId\": \"6729690792627146752\",\n" +
      "            \"studentName\": \"秦佑\",\n" +
      "            \"grade\": \"八年级\",\n" +
      "            \"periodCardName\": \"历史1104班课\",\n" +
      "            \"residueValidPeriod\": 40,\n" +
      "            \"invalidPeriod\": 0,\n" +
      "            \"familyOwner\": {\n" +
      "                \"id\": \"6722698813519302656\",\n" +
      "                \"name\": \"啦啦啦\"\n" +
      "            },\n" +
      "            \"schoolName\": \"学军小学\"\n" +
      "        }, {\n" +
      "            \"periodCardBankId\": \"6732227552552816640\",\n" +
      "            \"configOrdersId\": \"6730060064540987392\",\n" +
      "            \"configPeriodCardId\": \"6729690792627146752\",\n" +
      "            \"studentName\": \"钱宇\",\n" +
      "            \"grade\": \"八年级\",\n" +
      "            \"periodCardName\": \"历史1104班课\",\n" +
      "            \"residueValidPeriod\": 40,\n" +
      "            \"invalidPeriod\": 0,\n" +
      "            \"familyOwner\": {\n" +
      "                \"id\": \"6722698813519302656\",\n" +
      "                \"name\": \"啦啦啦\"\n" +
      "            },\n" +
      "            \"schoolName\": \"学军小学\"\n" +
      "        }, {\n" +
      "            \"periodCardBankId\": \"6732227572307988480\",\n" +
      "            \"configOrdersId\": \"6730060064540987392\",\n" +
      "            \"configPeriodCardId\": \"6729690792627146752\",\n" +
      "            \"studentName\": \"卫言\",\n" +
      "            \"grade\": \"八年级\",\n" +
      "            \"periodCardName\": \"历史1104班课\",\n" +
      "            \"residueValidPeriod\": 20,\n" +
      "            \"invalidPeriod\": 0,\n" +
      "            \"familyOwner\": {\n" +
      "                \"id\": \"6722698813519302656\",\n" +
      "                \"name\": \"啦啦啦\"\n" +
      "            },\n" +
      "            \"schoolName\": \"学军小学\"\n" +
      "        }, {\n" +
      "            \"periodCardBankId\": \"6732227570244390912\",\n" +
      "            \"configOrdersId\": \"6730060064540987392\",\n" +
      "            \"configPeriodCardId\": \"6729690792627146752\",\n" +
      "            \"studentName\": \"孙朋\",\n" +
      "            \"grade\": \"八年级\",\n" +
      "            \"periodCardName\": \"历史1104班课\",\n" +
      "            \"residueValidPeriod\": 20,\n" +
      "            \"invalidPeriod\": 0,\n" +
      "            \"familyOwner\": {\n" +
      "                \"id\": \"6722698813519302656\",\n" +
      "                \"name\": \"啦啦啦\"\n" +
      "            },\n" +
      "            \"schoolName\": \"学军小学\"\n" +
      "        }, {\n" +
      "            \"periodCardBankId\": \"6732227566373048320\",\n" +
      "            \"configOrdersId\": \"6730060064540987392\",\n" +
      "            \"configPeriodCardId\": \"6729690792627146752\",\n" +
      "            \"studentName\": \"许佑\",\n" +
      "            \"grade\": \"八年级\",\n" +
      "            \"periodCardName\": \"历史1104班课\",\n" +
      "            \"residueValidPeriod\": 20,\n" +
      "            \"invalidPeriod\": 0,\n" +
      "            \"familyOwner\": {\n" +
      "                \"id\": \"6722698813519302656\",\n" +
      "                \"name\": \"啦啦啦\"\n" +
      "            },\n" +
      "            \"schoolName\": \"学军小学\"\n" +
      "        }, {\n" +
      "            \"periodCardBankId\": \"6732227530494971904\",\n" +
      "            \"configOrdersId\": \"6730060064540987392\",\n" +
      "            \"configPeriodCardId\": \"6729690792627146752\",\n" +
      "            \"studentName\": \"孙亦\",\n" +
      "            \"grade\": \"八年级\",\n" +
      "            \"periodCardName\": \"历史1104班课\",\n" +
      "            \"residueValidPeriod\": 60,\n" +
      "            \"invalidPeriod\": 0,\n" +
      "            \"familyOwner\": {\n" +
      "                \"id\": \"6722698813519302656\",\n" +
      "                \"name\": \"啦啦啦\"\n" +
      "            },\n" +
      "            \"schoolName\": \"学军小学\"\n" +
      "        }, {\n" +
      "            \"periodCardBankId\": \"6732227562526871552\",\n" +
      "            \"configOrdersId\": \"6730060064540987392\",\n" +
      "            \"configPeriodCardId\": \"6729690792627146752\",\n" +
      "            \"studentName\": \"许曲\",\n" +
      "            \"grade\": \"八年级\",\n" +
      "            \"periodCardName\": \"历史1104班课\",\n" +
      "            \"residueValidPeriod\": 20,\n" +
      "            \"invalidPeriod\": 0,\n" +
      "            \"familyOwner\": {\n" +
      "                \"id\": \"6722698813519302656\",\n" +
      "                \"name\": \"啦啦啦\"\n" +
      "            },\n" +
      "            \"schoolName\": \"学军小学\"\n" +
      "        }, {\n" +
      "            \"periodCardBankId\": \"6732227542750728192\",\n" +
      "            \"configOrdersId\": \"6730060064540987392\",\n" +
      "            \"configPeriodCardId\": \"6729690792627146752\",\n" +
      "            \"studentName\": \"沈名\",\n" +
      "            \"grade\": \"八年级\",\n" +
      "            \"periodCardName\": \"历史1104班课\",\n" +
      "            \"residueValidPeriod\": 60,\n" +
      "            \"invalidPeriod\": 0,\n" +
      "            \"familyOwner\": {\n" +
      "                \"id\": \"6722698813519302656\",\n" +
      "                \"name\": \"啦啦啦\"\n" +
      "            },\n" +
      "            \"schoolName\": \"学军小学\"\n" +
      "        }, {\n" +
      "            \"periodCardBankId\": \"6732227558483562496\",\n" +
      "            \"configOrdersId\": \"6730060064540987392\",\n" +
      "            \"configPeriodCardId\": \"6729690792627146752\",\n" +
      "            \"studentName\": \"许余\",\n" +
      "            \"grade\": \"八年级\",\n" +
      "            \"periodCardName\": \"历史1104班课\",\n" +
      "            \"residueValidPeriod\": 20,\n" +
      "            \"invalidPeriod\": 0,\n" +
      "            \"familyOwner\": {\n" +
      "                \"id\": \"6722698813519302656\",\n" +
      "                \"name\": \"啦啦啦\"\n" +
      "            },\n" +
      "            \"schoolName\": \"学军小学\"\n" +
      "        }, {\n" +
      "            \"periodCardBankId\": \"6732227505543057408\",\n" +
      "            \"configOrdersId\": \"6730060064540987392\",\n" +
      "            \"configPeriodCardId\": \"6729690792627146752\",\n" +
      "            \"studentName\": \"李鸿\",\n" +
      "            \"grade\": \"八年级\",\n" +
      "            \"periodCardName\": \"历史1104班课\",\n" +
      "            \"residueValidPeriod\": 40,\n" +
      "            \"invalidPeriod\": 0,\n" +
      "            \"familyOwner\": {\n" +
      "                \"id\": \"6722698813519302656\",\n" +
      "                \"name\": \"啦啦啦\"\n" +
      "            },\n" +
      "            \"schoolName\": \"学军小学\"\n" +
      "        }, {\n" +
      "            \"periodCardBankId\": \"6732227550673768448\",\n" +
      "            \"configOrdersId\": \"6730060064540987392\",\n" +
      "            \"configPeriodCardId\": \"6729690792627146752\",\n" +
      "            \"studentName\": \"吕朋\",\n" +
      "            \"grade\": \"八年级\",\n" +
      "            \"periodCardName\": \"历史1104班课\",\n" +
      "            \"residueValidPeriod\": 20,\n" +
      "            \"invalidPeriod\": 0,\n" +
      "            \"familyOwner\": {\n" +
      "                \"id\": \"6722698813519302656\",\n" +
      "                \"name\": \"啦啦啦\"\n" +
      "            },\n" +
      "            \"schoolName\": \"学军小学\"\n" +
      "        }, {\n" +
      "            \"periodCardBankId\": \"6732227546726928384\",\n" +
      "            \"configOrdersId\": \"6730060064540987392\",\n" +
      "            \"configPeriodCardId\": \"6729690792627146752\",\n" +
      "            \"studentName\": \"韩宇\",\n" +
      "            \"grade\": \"八年级\",\n" +
      "            \"periodCardName\": \"历史1104班课\",\n" +
      "            \"residueValidPeriod\": 20,\n" +
      "            \"invalidPeriod\": 0,\n" +
      "            \"familyOwner\": {\n" +
      "                \"id\": \"6722698813519302656\",\n" +
      "                \"name\": \"啦啦啦\"\n" +
      "            },\n" +
      "            \"schoolName\": \"学军小学\"\n" +
      "        }, {\n" +
      "            \"periodCardBankId\": \"6732227540821348352\",\n" +
      "            \"configOrdersId\": \"6730060064540987392\",\n" +
      "            \"configPeriodCardId\": \"6729690792627146752\",\n" +
      "            \"studentName\": \"沈宏\",\n" +
      "            \"grade\": \"八年级\",\n" +
      "            \"periodCardName\": \"历史1104班课\",\n" +
      "            \"residueValidPeriod\": 20,\n" +
      "            \"invalidPeriod\": 0,\n" +
      "            \"familyOwner\": {\n" +
      "                \"id\": \"6722698813519302656\",\n" +
      "                \"name\": \"啦啦啦\"\n" +
      "            },\n" +
      "            \"schoolName\": \"学军小学\"\n" +
      "        }, {\n" +
      "            \"periodCardBankId\": \"6732227508109971456\",\n" +
      "            \"configOrdersId\": \"6730060064540987392\",\n" +
      "            \"configPeriodCardId\": \"6729690792627146752\",\n" +
      "            \"studentName\": \"何亦\",\n" +
      "            \"grade\": \"八年级\",\n" +
      "            \"periodCardName\": \"历史1104班课\",\n" +
      "            \"residueValidPeriod\": 80,\n" +
      "            \"invalidPeriod\": 0,\n" +
      "            \"familyOwner\": {\n" +
      "                \"id\": \"6722698813519302656\",\n" +
      "                \"name\": \"啦啦啦\"\n" +
      "            },\n" +
      "            \"schoolName\": \"学军小学\"\n" +
      "        }, {\n" +
      "            \"periodCardBankId\": \"6732227534781550592\",\n" +
      "            \"configOrdersId\": \"6730060064540987392\",\n" +
      "            \"configPeriodCardId\": \"6729690792627146752\",\n" +
      "            \"studentName\": \"许言\",\n" +
      "            \"grade\": \"八年级\",\n" +
      "            \"periodCardName\": \"历史1104班课\",\n" +
      "            \"residueValidPeriod\": 20,\n" +
      "            \"invalidPeriod\": 0,\n" +
      "            \"familyOwner\": {\n" +
      "                \"id\": \"6722698813519302656\",\n" +
      "                \"name\": \"啦啦啦\"\n" +
      "            },\n" +
      "            \"schoolName\": \"学军小学\"\n" +
      "        }, {\n" +
      "            \"periodCardBankId\": \"6732227512253943808\",\n" +
      "            \"configOrdersId\": \"6730060064540987392\",\n" +
      "            \"configPeriodCardId\": \"6729690792627146752\",\n" +
      "            \"studentName\": \"冯吉\",\n" +
      "            \"grade\": \"八年级\",\n" +
      "            \"periodCardName\": \"历史1104班课\",\n" +
      "            \"residueValidPeriod\": 40,\n" +
      "            \"invalidPeriod\": 0,\n" +
      "            \"familyOwner\": {\n" +
      "                \"id\": \"6722698813519302656\",\n" +
      "                \"name\": \"啦啦啦\"\n" +
      "            },\n" +
      "            \"schoolName\": \"学军小学\"\n" +
      "        }, {\n" +
      "            \"periodCardBankId\": \"6732226890930720768\",\n" +
      "            \"configOrdersId\": \"6730060064540987392\",\n" +
      "            \"configPeriodCardId\": \"6729690792627146752\",\n" +
      "            \"studentName\": \"吕君\",\n" +
      "            \"grade\": \"八年级\",\n" +
      "            \"periodCardName\": \"历史1104班课\",\n" +
      "            \"residueValidPeriod\": 20,\n" +
      "            \"invalidPeriod\": 0,\n" +
      "            \"familyOwner\": {\n" +
      "                \"id\": \"6722698813519302656\",\n" +
      "                \"name\": \"啦啦啦\"\n" +
      "            },\n" +
      "            \"schoolName\": \"学军小学\"\n" +
      "        }, {\n" +
      "            \"periodCardBankId\": \"6732226888640630784\",\n" +
      "            \"configOrdersId\": \"6730060064540987392\",\n" +
      "            \"configPeriodCardId\": \"6729690792627146752\",\n" +
      "            \"studentName\": \"沈辰\",\n" +
      "            \"grade\": \"八年级\",\n" +
      "            \"periodCardName\": \"历史1104班课\",\n" +
      "            \"residueValidPeriod\": 20,\n" +
      "            \"invalidPeriod\": 0,\n" +
      "            \"familyOwner\": {\n" +
      "                \"id\": \"6722698813519302656\",\n" +
      "                \"name\": \"啦啦啦\"\n" +
      "            },\n" +
      "            \"schoolName\": \"学军小学\"\n" +
      "        }, {\n" +
      "            \"periodCardBankId\": \"6732226482913021952\",\n" +
      "            \"configOrdersId\": \"6730060064540987392\",\n" +
      "            \"configPeriodCardId\": \"6729690792627146752\",\n" +
      "            \"studentName\": \"张远\",\n" +
      "            \"grade\": \"八年级\",\n" +
      "            \"periodCardName\": \"历史1104班课\",\n" +
      "            \"residueValidPeriod\": 20,\n" +
      "            \"invalidPeriod\": 0,\n" +
      "            \"familyOwner\": {\n" +
      "                \"id\": \"6722698813519302656\",\n" +
      "                \"name\": \"啦啦啦\"\n" +
      "            },\n" +
      "            \"schoolName\": \"学军小学\"\n" +
      "        }\n" +
      "    ],\n" +
      "    \"total\": 29,\n" +
      "    \"personMin\": 50,\n" +
      "    \"personMax\": 100\n" +
      "}\n";

    JSONObject jsonObject = new JSONObject(json);
    JSONArray jsonArray = (JSONArray) jsonObject.get("data");
    JSONArray result = new JSONArray();
    for (Object o : jsonArray) {
      JSONObject next = (JSONObject) o;
      String periodCardBankId = (String) next.get("periodCardBankId");
      if (periodCardBankId != null) {
        result.put(periodCardBankId);
      }
    }
    System.out.println(result.toString());
  }

}