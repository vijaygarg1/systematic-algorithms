// Sequential merge sort (parallel companion in progs-divideConquer/).

import java.util.*;

public class MergeSort {
  public void sort(int lo, int hi, int[] G) {
    if ((lo < hi)) {
      int mid = ((lo + hi) / 2);
      sort(lo, mid, G);
      sort((mid + 1), hi, G);
      Merge(lo, mid, hi, G);
    }
  }

  public void Merge(int lo, int mid, int hi, int[] G) {
    int[] B = new int[G.length];
    for (int k = lo; k <= hi; k++) {
      B[k] = G[k];
    }
    int i = lo;
    int j = (mid + 1);
    int k = lo;
    while (((i <= mid) && (j <= hi))) {
      if ((B[i] <= B[j])) {
        G[k] = B[i];
        i = (i + 1);
      } else {
        G[k] = B[j];
        j = (j + 1);
      }
      k = (k + 1);
    }
    while ((i <= mid)) {
      G[k] = B[i];
      i = (i + 1);
      k = (k + 1);
    }
    while ((j <= hi)) {
      G[k] = B[j];
      j = (j + 1);
      k = (k + 1);
    }
  }

  public static void main(String[] args) {
    int[] G = new int[] {5, 2, 4, 6, 1, 3, 8, 7};
    MergeSort prog = new MergeSort();
    prog.sort(0, G.length - 1, G);
    System.out.println(Arrays.toString(G));
  }
}