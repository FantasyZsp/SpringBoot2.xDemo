package xyz.mydev.synchronizedcontainer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Demo {

  private List list = Collections.synchronizedList(new ArrayList<>());
}
