package xyz.mydev.jdk.genericity.complex.repository;

public interface MessageCrudRepository<E, ID, CP> {
  int insert(E entity);
}