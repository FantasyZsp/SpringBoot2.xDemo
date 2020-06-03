package demo;

public class FactoryTest {

  public static void main(String[] args) {
//    Factory factory = getFactory(1);
//    Production produce = factory.produce();
//    System.out.println(produce);
//
//
//    Factory factory2 = getFactory(2);
//    Production produce2 = factory2.produce();
//    System.out.println(produce2);

//    Factory factory3 = getFactory(3);
//    Production produce3 = factory3.produce();
//    System.out.println(produce3);


    GeliFactory meidiFactory = (GeliFactory) getFactory(1);
    Production produce = meidiFactory.produce();
    System.out.println(produce);

  }

  private static void print(Object o) {
    System.out.println(o);
  }

  private static Factory getFactory(int type) {

    if (1 == type) {
      return new GeliFactory("geli");
    } else if (2 == type) {
      return new MeidiFactory("meidi");
    }
    return new AoksFactory("aoks");
  }


}
