package xyz.mydev.jdk.getclass;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Favorites {
  private Map<Class<?>, Object> favorites = new HashMap<>();

  public <T> void setFavorite(Class<T> klass, T thing) {
    favorites.put(klass, thing);
  }

  public <T> T getFavorite(Class<T> klass) {
    return klass.cast(favorites.get(klass));
  }

  public static void main(String[] args) {
    Favorites f = new Favorites();
    f.setFavorite(String.class, "Java");
    f.setFavorite(Integer.class, 0xcafebabe);
    String s = f.getFavorite(String.class);

    System.out.println(s);

    int i = f.getFavorite(Integer.class);
    System.out.println(i);


//    f.setFavorite(List<String>.class, Collections.emptyList());

  }
}