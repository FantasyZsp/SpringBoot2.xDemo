package xyz.mydev.datastructure;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author ZSP
 */
@Data
public class OrgNodeDTO implements Serializable {

  public OrgNodeDTO() {
  }

  public OrgNodeDTO(String orgNum) {
    this.orgNum = orgNum;
  }

  /**
   * 机构编码
   */
  private String orgNum;
  /**
   * 子机构
   */
  private List<? super OrgNodeDTO> children = new ArrayList<>();


  public static void main(String[] args) {
    MyOrgNodeDTO myOrgNodeDTO = new MyOrgNodeDTO("00", "xxx");
    MyOrgNodeDTO myOrgNodeDTO1 = new MyOrgNodeDTO("0001", "222");
    MyOrgNodeDTO myOrgNodeDTO2 = new MyOrgNodeDTO("0002", "333");
    MyOrgNodeDTO myOrgNodeDTO3 = new MyOrgNodeDTO("000101", "444");

    ArrayList<MyOrgNodeDTO> objects = new ArrayList<>();
    objects.add(myOrgNodeDTO);
    objects.add(myOrgNodeDTO1);
    objects.add(myOrgNodeDTO2);
    objects.add(myOrgNodeDTO3);

    List<MyOrgNodeDTO> myOrgNodeDTOS = OrgNode2TreeUtils.node2Tree(objects);
    System.out.println(myOrgNodeDTOS);
  }
}

@Data
class OrgNode2TreeUtils {

  public static <T extends OrgNodeDTO> List<T> node2Tree(List<T> nodes) {
    List<T> retList = new ArrayList<>();
    for (T parent : nodes) {
      if ("00".equals(parent.getOrgNum())) {
        retList.add(findChildren(parent, nodes));
      }
    }


    return retList;
  }

  private static <T extends OrgNodeDTO> T findChildren(T parent, List<T> treeNodes) {
    for (T child : treeNodes) {
      if (parent.getOrgNum().equals(child.getOrgNum().substring(0, child.getOrgNum().length() - 2))) {
        if (parent.getChildren() == null) {
          parent.setChildren(new ArrayList<>());
        }

        T children = findChildren(child, treeNodes);
        parent.getChildren().add(children);
      }
    }
    return parent;
  }
}

@Data
@ToString(callSuper = true)
class MyOrgNodeDTO extends OrgNodeDTO {
  private String mark;

  public MyOrgNodeDTO(String orgNum) {
    super(orgNum);
  }


  public MyOrgNodeDTO(String orgNum, String mark) {
    super(orgNum);
    this.mark = mark;
  }
}

