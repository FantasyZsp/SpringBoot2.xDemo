//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package xyz.mydev.jvm.bytecode.dynamicproxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.lang.reflect.UndeclaredThrowableException;

@SuppressWarnings("all")
public final class $Proxy0 extends Proxy implements Subject, Marker {
  private static Method m1;
  private static Method m5;
  private static Method m3;
  private static Method m2;
  private static Method m4;
  private static Method m0;

  public $Proxy0(InvocationHandler var1) {
    super(var1);
  }

  @Override
  public final boolean equals(Object var1) {
    try {
      return (Boolean) super.h.invoke(this, m1, new Object[]{var1});
    } catch (RuntimeException | Error var3) {
      throw var3;
    } catch (Throwable var4) {
      throw new UndeclaredThrowableException(var4);
    }
  }

  @Override
  public final void mark() {
    try {
      super.h.invoke(this, m5, (Object[]) null);
    } catch (RuntimeException | Error var2) {
      throw var2;
    } catch (Throwable var3) {
      throw new UndeclaredThrowableException(var3);
    }
  }

  @Override
  public final void valid() {
    try {
      super.h.invoke(this, m3, (Object[]) null);
    } catch (RuntimeException | Error var2) {
      throw var2;
    } catch (Throwable var3) {
      throw new UndeclaredThrowableException(var3);
    }
  }

  @Override
  public final String toString() {
    try {
      return (String) super.h.invoke(this, m2, (Object[]) null);
    } catch (RuntimeException | Error var2) {
      throw var2;
    } catch (Throwable var3) {
      throw new UndeclaredThrowableException(var3);
    }
  }

  @Override
  public final void request() {
    try {
      super.h.invoke(this, m4, (Object[]) null);
    } catch (RuntimeException | Error var2) {
      throw var2;
    } catch (Throwable var3) {
      throw new UndeclaredThrowableException(var3);
    }
  }

  @Override
  public final int hashCode() {
    try {
      return (Integer) super.h.invoke(this, m0, (Object[]) null);
    } catch (RuntimeException | Error var2) {
      throw var2;
    } catch (Throwable var3) {
      throw new UndeclaredThrowableException(var3);
    }
  }

  static {
    try {
      m1 = Class.forName("java.lang.Object").getMethod("equals", Class.forName("java.lang.Object"));
      m5 = Class.forName("xyz.mydev.jvm.bytecode.dynamicproxy.Marker").getMethod("mark");
      m3 = Class.forName("xyz.mydev.jvm.bytecode.dynamicproxy.Subject").getMethod("valid");
      m2 = Class.forName("java.lang.Object").getMethod("toString");
      m4 = Class.forName("xyz.mydev.jvm.bytecode.dynamicproxy.Subject").getMethod("request");
      m0 = Class.forName("java.lang.Object").getMethod("hashCode");
    } catch (NoSuchMethodException var2) {
      throw new NoSuchMethodError(var2.getMessage());
    } catch (ClassNotFoundException var3) {
      throw new NoClassDefFoundError(var3.getMessage());
    }
  }
}
