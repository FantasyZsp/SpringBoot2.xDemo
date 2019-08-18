package xyz.mydev.datastructure;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;


/**
 * @author zhao  2018/07/26 12:11
 * @description
 */
public class ListMapSet {
  public static void main(String[] args) throws JsonProcessingException {
    ObjectMapper objectMapper = new ObjectMapper();

    System.out.println("=================Object[]==================");
    Object[] objects = {new Integer(1), new Long(2), new String("3")};
    Object obj = objects;
    System.out.println(objects[0]);
    System.out.println(objects[1]);
    System.out.println(objects[2]);
    System.out.println(objectMapper.writeValueAsString(objects));
    System.out.println(obj.getClass().isArray());
    int length = Array.getLength(obj);
    Object[] newObj = new Object[length];
//        for(int i = 0;i<newObj.length;i++){
//            newObj[i] = Array.get(obj, i);
//            System.out.println(newObj[i]);
//        }

    int obj1 = (int) Array.get(obj, 0);
    long obj2 = (long) Array.get(obj, 1);
    String obj3 = (String) Array.get(obj, 2);
    System.out.println(obj1);
    System.out.println(String.valueOf(obj2));
    System.out.println(obj3);


    System.out.println("=================List==================");
    List<String> list = new ArrayList<>(10);
    list.add("XXXXXXXX");
    list.add("YYYYYYYY");
    list.add("ZZZZZZZZ");
    list.add("XXXXXXXX");
    list.add("XXXXXXXX");

    List<String> list2 = new ArrayList<>();
    for (String str : list2) {
      System.out.println(list.contains(str));
      System.out.println(list.contains(str));
    }

    String listStr = objectMapper.writeValueAsString(list);
    System.out.println(listStr);
    System.out.println(list);
    System.out.println();

    System.out.println("==================Set=================");

    Set<String> set = new HashSet<>(10);
    Set<String> set1 = new HashSet<>(list);
    set.add("XXXXXXXX");
    set.add("XXXXXXXX");
    set.add("XXXXXXXX");
    set.add("YYYYYYYY");
    set.add("ZZZZZZZZ");
    set.add("111111111");
    set.add("222222222");
    String setStr = objectMapper.writeValueAsString(set);
    System.out.println(set);
    System.out.println(setStr);
    System.out.println();
    System.out.println("set1: " + set1);
    set.contains("xxxxxxxx");


    System.out.println("=================Map==================");
    Map<String, String> map = new HashMap<>(10);
    map.put("key", "value");
    map.put("key1", "value1");
    map.put("key2", "value2");
    map.put("key3", "value3");
    map.put("key4", "value4");
    map.put("key5", "value5");
    Map<String, String> map2 = new HashMap<>(10);
    map2.put("key", "value");
    map2.put("key1", "value1");
    map2.put("key2", "value2");
    map2.put("key3", "value3");
    map2.put("key4", "value4");
    map2.put("key5", "value5");
    String mapStr = objectMapper.writeValueAsString(map);
    System.out.println(map);
    System.out.println(mapStr);
    System.out.println();
    System.out.println(map.get("xxx"));

    System.out.println("=================listMap==================");
    List<Map<String, String>> listMap = new ArrayList<>(10);
    listMap.add(map);
    listMap.add(map2);

    System.out.println(listMap);
    String listMapStr = objectMapper.writeValueAsString(listMap);
    System.out.println(listMapStr);

    System.out.println("=================Object==================");

    PersonDemo person = new PersonDemo(list, "name", "xxname", "yyname", "zzname", set, map, listMap);
    String objectStr = objectMapper.writeValueAsString(person);
    System.out.println(objectStr);


  }

  private static class PersonDemo {

    private String name = "name";
    private String xxname = "name";
    private String yyname = "name";
    private String zzname = "name";
    List<String> listS = new ArrayList<>();
    Set<String> setS = new HashSet<>();
    Map<String, String> mapSS = new HashMap<>();
    List<Map<String, String>> listMapSS = new ArrayList<>();

    public PersonDemo() {
    }

    public PersonDemo(List<String> listS, String name, String xxname, String yyname, String zzname, Set<String> setS, Map<String, String> mapSS, List<Map<String, String>> listMapSS) {
      this.listS = listS;
      this.name = name;
      this.xxname = xxname;
      this.yyname = yyname;
      this.zzname = zzname;
      this.setS = setS;
      this.mapSS = mapSS;
      this.listMapSS = listMapSS;
    }

    @Override
    public String toString() {
      return "Pserson{" +
        "listS=" + listS +
        ", name='" + name + '\'' +
        ", xxname='" + xxname + '\'' +
        ", yyname='" + yyname + '\'' +
        ", zzname='" + zzname + '\'' +
        ", setS=" + setS +
        ", mapSS=" + mapSS +
        ", listMapSS=" + listMapSS +
        '}';
    }

    public List<String> getListS() {
      return listS;
    }

    public void setListS(List<String> listS) {
      this.listS = listS;
    }

    public String getName() {
      return name;
    }

    public void setName(String name) {
      this.name = name;
    }

    public String getXxname() {
      return xxname;
    }

    public void setXxname(String xxname) {
      this.xxname = xxname;
    }

    public String getYyname() {
      return yyname;
    }

    public void setYyname(String yyname) {
      this.yyname = yyname;
    }

    public String getZzname() {
      return zzname;
    }

    public void setZzname(String zzname) {
      this.zzname = zzname;
    }

    public Set<String> getSetS() {
      return setS;
    }

    public void setSetS(Set<String> setS) {
      this.setS = setS;
    }

    public Map<String, String> getMapSS() {
      return mapSS;
    }

    public void setMapSS(Map<String, String> mapSS) {
      this.mapSS = mapSS;
    }

    public List<Map<String, String>> getListMapSS() {
      return listMapSS;
    }

    public void setListMapSS(List<Map<String, String>> listMapSS) {
      this.listMapSS = listMapSS;
    }
  }
}

/*
=================List==================
["XXXXXXXX","YYYYYYYY","ZZZZZZZZ","XXXXXXXX","XXXXXXXX"]
[XXXXXXXX, YYYYYYYY, ZZZZZZZZ, XXXXXXXX, XXXXXXXX]

==================Set=================
[YYYYYYYY, XXXXXXXX, ZZZZZZZZ]
["YYYYYYYY","XXXXXXXX","ZZZZZZZZ"]

=================Map==================
{key1=value1, key2=value2, key5=value5, key3=value3, key4=value4, key=value}
{"key1":"value1","key2":"value2","key5":"value5","key3":"value3","key4":"value4","key":"value"}

=================listMap==================
[{key1=value1, key2=value2, key5=value5, key3=value3, key4=value4, key=value}, {key1=value1, key2=value2, key5=value5, key3=value3, key4=value4, key=value}]
[{"key1":"value1","key2":"value2","key5":"value5","key3":"value3","key4":"value4","key":"value"},{"key1":"value1","key2":"value2","key5":"value5","key3":"value3","key4":"value4","key":"value"}]

{
	"name": "name",
	"xxname": "xxname",
	"yyname": "yyname",
	"zzname": "zzname",
	"listS": ["XXXXXXXX", "YYYYYYYY", "ZZZZZZZZ", "XXXXXXXX", "XXXXXXXX"],
	"setS": ["YYYYYYYY", "XXXXXXXX", "ZZZZZZZZ"],
	"mapSS": {
		"key1": "value1",
		"key2": "value2",
		"key5": "value5",
		"key3": "value3",
		"key4": "value4",
		"key": "value"
	},
	"listMapSS": [{
		"key1": "value1",
		"key2": "value2",
		"key5": "value5",
		"key3": "value3",
		"key4": "value4",
		"key": "value"
	}, {
		"key1": "value1",
		"key2": "value2",
		"key5": "value5",
		"key3": "value3",
		"key4": "value4",
		"key": "value"
	}]
}
*/
