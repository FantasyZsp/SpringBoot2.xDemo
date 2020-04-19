package xyz.mydev.jvm.classload.myloader;

import lombok.extern.slf4j.Slf4j;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

/**
 * @author ZSP
 */
@Slf4j
public class MyLocalClassLoader extends ClassLoader {

  private final String classLoaderName;
  private final String fileExtension = ".class";


  /**
   * ClassLoader无参构造函数会将 systemClassLoader作为父类加载器
   * {@link ClassLoader#ClassLoader()}
   */
  public MyLocalClassLoader(String classLoaderName) {
    super();
    this.classLoaderName = classLoaderName;
  }

  /**
   * 手动指定父类加载器
   */
  public MyLocalClassLoader(ClassLoader parent, String classLoaderName) {
    super(parent);
    this.classLoaderName = classLoaderName;
  }

  @Override
  protected Class<?> findClass(String name) throws ClassNotFoundException {

    log.info("MyLocalClassLoader worked by loading class : {}", name);
    byte[] bytes = this.loadClassData(name);
    return super.defineClass(name, bytes, 0, bytes.length);
  }

  private byte[] loadClassData(String name) {

    name = name.replace(".", "/");

    byte[] results = null;

    try (InputStream in = new FileInputStream(new File(name + this.fileExtension));
         ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream()
    ) {

      int ch;
      while (-1 != (ch = in.read())) {
        byteArrayOutputStream.write(ch);
      }

      results = byteArrayOutputStream.toByteArray();
    } catch (Exception e) {
      e.printStackTrace();
    }
    return results;
  }


  public static void main(String[] args) throws ClassNotFoundException {
    MyLocalClassLoader localClassLoader = new MyLocalClassLoader("myClassLoader");
    Class<?> aClass = localClassLoader.loadClass("xyz.mydev.jvm.classload.loader.LoaderTest");
    System.out.println(aClass);
    System.out.println(aClass.getClassLoader().getName());
  }

}
