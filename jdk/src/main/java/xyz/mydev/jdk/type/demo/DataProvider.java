package xyz.mydev.jdk.type.demo;

import java.util.List;

/**
 * @author ZSP
 */
public interface DataProvider<P, R> extends Marker {
  List<R> getData(P yourParameters);
}
