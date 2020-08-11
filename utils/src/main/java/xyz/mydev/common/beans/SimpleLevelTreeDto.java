package xyz.mydev.common.beans;

import lombok.Data;
import lombok.NoArgsConstructor;
import xyz.mydev.common.utils.JsonUtil;

import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * @author ZSP
 */
@Data
@NoArgsConstructor
public class SimpleLevelTreeDto implements BasedStringLevelTree<SimpleLevelTreeDto> {

  @NotBlank
  private String id;
  @NotBlank
  private String parentId;

  private int order;

  private List<SimpleLevelTreeDto> children;

  public SimpleLevelTreeDto(String id, String parentId, int order) {
    this.id = id;
    this.parentId = parentId;
    this.order = order;
  }

  @Override
  public String level() {
    return parentId;
  }

  @Override
  public String calculateChildLevel() {
    return id;
  }


  @Override
  public int order() {
    return order;
  }

  public static void main(String[] args) {

    SimpleLevelTreeDto animal = new SimpleLevelTreeDto("animal", "root", 1);

    SimpleLevelTreeDto human = new SimpleLevelTreeDto("human", "animal", 1);

    SimpleLevelTreeDto man = new SimpleLevelTreeDto("man", "human", 1);
    SimpleLevelTreeDto smartMan = new SimpleLevelTreeDto("smartMan", "man", 1);
    SimpleLevelTreeDto stupidMan = new SimpleLevelTreeDto("stupidMan", "man", 2);
    SimpleLevelTreeDto interestingMan = new SimpleLevelTreeDto("interestingMan", "man", 3);


    SimpleLevelTreeDto woman = new SimpleLevelTreeDto("woman", "human", 2);
    SimpleLevelTreeDto smartWoman = new SimpleLevelTreeDto("smartWoman", "woman", 1);
    SimpleLevelTreeDto stupidWoman = new SimpleLevelTreeDto("stupidWoman", "woman", 2);
    SimpleLevelTreeDto interestingWoman = new SimpleLevelTreeDto("interestingWoman", "woman", 3);


    SimpleLevelTreeDto cat = new SimpleLevelTreeDto("cat", "animal", 2);
    SimpleLevelTreeDto littleCat = new SimpleLevelTreeDto("littleCat", "cat", 1);
    SimpleLevelTreeDto bigCat = new SimpleLevelTreeDto("bigCat", "cat", 2);

    List<SimpleLevelTreeDto> sourceList = List.of(animal, human, cat, man, woman, littleCat, bigCat, smartMan, stupidMan, interestingMan, smartWoman, stupidWoman, interestingWoman);

    SimpleLevelTreeDto root = IBasedLevelTree.transfer2Tree("root", sourceList).get(0);

    System.out.println(JsonUtil.obj2StringPretty(root));

    List<SimpleLevelTreeDto> tree2List = IBasedLevelTree.tree2List(root);
    System.out.println(JsonUtil.obj2StringPretty(tree2List));
//    System.out.println(JsonUtil.obj2StringPretty(IBasedLevelTree.transfer2Tree("root", tree2List)));
  }


}
