package xyz.mydev.forkjoin;

import org.springframework.util.StopWatch;

import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

/**
 * link https://time.geekbang.org/column/article/92524
 *
 * @author ZSP
 */
public class SimpleDemo {

  public static void main(String[] args) {
    mergeSort();
  }

  private static void mergeSort() {
    Random random = new Random();
    StopWatch stopWatch = new StopWatch();

    int n = 5000_0000;

    long[] arrays = new long[n];
    for (int i = 0; i < n; i++) {
      arrays[i] = random.nextLong();
    }
    stopWatch.start("Arrays.copyOf");
    long[] array1 = Arrays.copyOf(arrays, arrays.length);
    stopWatch.stop();

    stopWatch.start("Arrays.copyOf22");
    long[] array2 = Arrays.copyOf(arrays, arrays.length);
    stopWatch.stop();

    ForkJoinPool forkJoinPool = new ForkJoinPool(Runtime.getRuntime().availableProcessors());
    MergeSort mergeSort = new MergeSort(array1);
    stopWatch.start("forkJoinPool");
    forkJoinPool.invoke(mergeSort);
    stopWatch.stop();

    //传统递归
    stopWatch.start("传统递归");
    mergeSort(array2);
    stopWatch.stop();
    System.out.println(stopWatch.prettyPrint());
  }

  /**
   * fork/join
   */
  static class MergeSort extends RecursiveTask<long[]> {
    long[] array;

    MergeSort(long[] array) {
      this.array = array;
    }

    @Override
    protected long[] compute() {
      if (array.length < 2) {
        return array;
      }
      int mid = array.length / 2;
      MergeSort left = new MergeSort(Arrays.copyOfRange(array, 0, mid));
      left.fork();
      MergeSort right = new MergeSort(Arrays.copyOfRange(array, mid, array.length));
      return merge(right.compute(), left.join());
    }
  }

  /**
   * 传统递归
   */
  private static long[] mergeSort(long[] array) {
    if (array.length < 2) {
      return array;
    }
    int mid = array.length / 2;
    long[] left = Arrays.copyOfRange(array, 0, mid);
    long[] right = Arrays.copyOfRange(array, mid, array.length);
    return merge(mergeSort(left), mergeSort(right));
  }

  private static long[] merge(long[] left, long[] right) {
    long[] result = new long[left.length + right.length];
    for (int i = 0, m = 0, j = 0; m < result.length; m++) {
      if (i >= left.length) {
        result[m] = right[j++];
      } else if (j >= right.length) {
        result[m] = left[i++];
      } else if (left[i] > right[j]) {
        result[m] = right[j++];
      } else {
        result[m] = left[i++];
      }
    }
    return result;
  }
}
