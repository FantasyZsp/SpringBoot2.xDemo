package xyz.mydev.spring.annotation;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.classreading.CachingMetadataReaderFactory;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;

import java.io.IOException;
import java.util.Set;


@TransactionalService
@Slf4j
public class AnnotationMetadataFetch {


  public AnnotationMetadataFetch() {
    log.info("AnnotationMetadataFetch instant");
  }

  public static void test(String[] args) throws IOException {
    // @TransactionalService 标注在当前类 TransactionalServiceAnnotationMetadataBootstrap
    String className = AnnotationMetadataFetch.class.getName();
    // 构建 MetadataReaderFactory 实例
    MetadataReaderFactory metadataReaderFactory = new CachingMetadataReaderFactory();
    // 读取 @TransactionService MetadataReader 信息
    MetadataReader metadataReader = metadataReaderFactory.getMetadataReader(className);
    // 读取 @TransactionService AnnotationMetadata 信息
    AnnotationMetadata annotationMetadata = metadataReader.getAnnotationMetadata();

    annotationMetadata.getAnnotationTypes().forEach(annotationType -> {

      Set<String> metaAnnotationTypes = annotationMetadata.getMetaAnnotationTypes(annotationType);

      metaAnnotationTypes.forEach(metaAnnotationType -> {
        System.out.printf("注解 @%s 元标注 @%s\n", annotationType, metaAnnotationType);
      });

    });
  }

  @Bean
  public String testAnnotationMetadataFetchBean() {
    log.info("testAnnotationMetadataFetchBean instant");
    return "testAnnotationMetadataFetchBean";
  }
}
