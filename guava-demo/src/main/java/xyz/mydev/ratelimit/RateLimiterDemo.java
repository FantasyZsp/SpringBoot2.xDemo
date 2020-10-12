package xyz.mydev.ratelimit;


import com.google.common.util.concurrent.RateLimiter;

/**
 * @author ZSP
 */
public class RateLimiterDemo {
  public static void main(String[] args) {
    RateLimiter rateLimiter = RateLimiter.create(2);

    System.out.println(rateLimiter.acquire());
    System.out.println(rateLimiter.acquire());
    System.out.println(rateLimiter.acquire());
    System.out.println(rateLimiter.acquire());
    System.out.println(rateLimiter.acquire());
    System.out.println(rateLimiter.acquire());
    System.out.println(rateLimiter.acquire());
    System.out.println(rateLimiter.acquire());
    System.out.println(rateLimiter.acquire());




    System.out.println(rateLimiter.getRate());
  }
}
