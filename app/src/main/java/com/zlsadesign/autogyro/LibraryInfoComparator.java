package com.zlsadesign.autogyro;

import java.util.Comparator;

public class LibraryInfoComparator implements Comparator<LibraryInfo> {

  public int compare(LibraryInfo left, LibraryInfo right) {
    return left.name.compareTo(right.name);
  }

}
