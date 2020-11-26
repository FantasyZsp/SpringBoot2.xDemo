package xyz.mydev.spring.annotation.repeatable;

import org.springframework.core.annotation.AnnotationUtils;
import xyz.mydev.common.utils.JsonUtil;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Set;

/**
 * @author ZSP
 */
public class AnnotationReader {


  public static void main(String[] args) throws Exception {
    testWithSpringUtil();
  }

  public void test() throws NoSuchMethodException {

    Method testLockSingle = SimpleService.class.getDeclaredMethod("testLockSingle");
    Method testLockMulti = SimpleService.class.getDeclaredMethod("testLockMulti");
    Method testLockGroup = SimpleService.class.getDeclaredMethod("testLockGroup");

    System.out.println("==================================================================");


    for (Annotation declaredAnnotation : testLockSingle.getDeclaredAnnotations()) {
      System.out.println(declaredAnnotation);
    }
    System.out.println(testLockSingle.isAnnotationPresent(DistributedLock.class));
    System.out.println(testLockSingle.isAnnotationPresent(DistributedLocks.class));


    System.out.println("==================================================================");

    for (Annotation declaredAnnotation : testLockMulti.getDeclaredAnnotations()) {
      System.out.println(declaredAnnotation);
    }

    System.out.println(testLockMulti.isAnnotationPresent(DistributedLock.class));
    System.out.println(testLockMulti.isAnnotationPresent(DistributedLocks.class));

    System.out.println("==================================================================");


    for (Annotation declaredAnnotation : testLockGroup.getDeclaredAnnotations()) {
      System.out.println(declaredAnnotation);
    }

    System.out.println(testLockGroup.isAnnotationPresent(DistributedLock.class));
    System.out.println(testLockGroup.isAnnotationPresent(DistributedLocks.class));
  }

  public static void testWithSpringUtil() throws Exception {
    logLine("testLockSingle");
    Set<DistributedLock> testLockSingle = AnnotationUtils.getDeclaredRepeatableAnnotations(SimpleService.class.getMethod("testLockSingle"), DistributedLock.class);
    log(testLockSingle);

    logLine("testLockMulti");
    Set<DistributedLock> testLockMulti = AnnotationUtils.getDeclaredRepeatableAnnotations(SimpleService.class.getMethod("testLockMulti"), DistributedLock.class);
    log(testLockMulti);

    logLine("testLockGroup");
    Set<DistributedLock> testLockGroup = AnnotationUtils.getDeclaredRepeatableAnnotations(SimpleService.class.getMethod("testLockGroup"), DistributedLock.class);
    log(testLockGroup);

  }

  public static void log(Set<? extends Annotation> annotationSet) {
    annotationSet.forEach(annotation -> System.out.println(JsonUtil.obj2StringPretty(AnnotationUtils.getAnnotationAttributes(annotation))));
  }

  public static void logLine(String msg) {
    System.out.println("============================================" + msg + "============================================");
  }
}
