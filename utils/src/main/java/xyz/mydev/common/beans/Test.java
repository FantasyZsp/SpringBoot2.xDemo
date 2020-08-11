package xyz.mydev.common.beans;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.system.SystemProperties;
import xyz.mydev.common.utils.JsonUtil;

/**
 * @author ZSP
 */
public class Test {
  public static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

  public static void main(String[] args) {
    testImplements();
  }

  public static void testImplements() {
    Data dataDto = getInstant();

    dataDto.setInfo("info");
    dataDto.setRoute("route");

    if (dataDto instanceof DataDtoImpl) {
      ((DataDtoImpl) dataDto).setAge("age");
      ((DataDtoImpl) dataDto).setId("id");
      ((DataDtoImpl) dataDto).setName("name");
    }

    System.out.println(JsonUtil.obj2StringPretty(dataDto));


    String msg = "{\n" +
      "  \"id\" : \"id\",\n" +
      "  \"name\" : \"name\",\n" +
      "  \"age\" : \"age\",\n" +
      "  \"info\" : \"route\",\n" +
      "  \"route\" : \"route\"\n" +
      "}";

    Data data = JsonUtil.string2Obj(msg, DataDtoImpl.class);
    System.out.println(data);
  }

  private static Data getInstant() {
    return new DataDtoImpl();
  }


  public static void testInterfaceProperty(String[] args) {
    AbstractDto abstractDto = new AbstractDto();
    Data data = new Data() {
      @Override
      public String getInfo() {
        return "info";
      }

      @Override
      public String getRoute() {
        return "route";
      }

      @Override
      public void setInfo(String info) {

      }

      @Override
      public void setRoute(String info) {

      }
    };

    abstractDto.setAge("1");
    abstractDto.setId("id");
    abstractDto.setName("name");
    abstractDto.setData(data);

    System.out.println(JsonUtil.obj2StringPretty(abstractDto));


    String msg = "{\n" +
      "  \"id\" : \"id\",\n" +
      "  \"name\" : \"name\",\n" +
      "  \"age\" : \"1\",\n" +
      "  \"data\" : {\n" +
      "    \"info\" : \"info\",\n" +
      "    \"route\" : \"route\"\n" +
      "  }\n" +
      "}";

    try {
      AbstractDto abstractDto1 = JsonUtil.string2Obj(msg, AbstractDto.class);
      System.out.println(abstractDto1);
    } catch (Exception e) {
      System.out.println("无法序列化接口属性");
    }


    System.out.println(SystemProperties.get("user.dir"));
    System.out.println(SystemProperties.get("user.home"));

  }
}
