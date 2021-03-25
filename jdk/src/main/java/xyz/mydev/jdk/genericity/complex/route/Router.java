package xyz.mydev.jdk.genericity.complex.route;

public interface Router<K, V> {

  V get(K key);

}