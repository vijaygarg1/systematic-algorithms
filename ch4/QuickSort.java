// Sequential quicksort with Lomuto partition (parallel form in progs-divideConquer/).

import java.util.*;

public class QuickSort {
  public void sort(int lo, int hi, int[] G) {
    if ((lo < hi)) {
      int p = Partition(lo, hi, G);
      sort(lo, (p - 1), G);
      sort((p + 1), hi, G);
    }
  }

  public int Partition(int lo, int hi, int[] G) {
    int pivot = G[hi];
    int i = (lo - 1);
    int j = lo;
    while ((j <= (hi - 1))) {
      if ((G[j] <= pivot)) {
        i = (i + 1);
        int tmp = G[i];
        G[i] = G[j];
        G[j] = tmp;
      }
      j = (j + 1);
    }
    int tmp = G[(i + 1)];
    G[(i + 1)] = G[hi];
    G[hi] = tmp;
    return (i + 1);
  }

  public static void main(String[] args) {
    int[] G = new int[] {5, 2, 4, 6, 1, 3, 8, 7};
    QuickSort prog = new QuickSort();
    prog.sort(0, G.length - 1, G);
    System.out.println(Arrays.toString(G));
  }
}