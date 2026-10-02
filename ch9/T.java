import java.util.*;
public class T {
  public static void main(String[] a) {
    int[] A = {0, 5, 2, 8, 1, 9, 3, 7, 4, 6};
    MergeSort ms = new MergeSort(1, A.length-1, A);
    System.out.println("ms = " + Arrays.toString(ms.G));
    QuickSort qs = new QuickSort(1, A.length-1, A);
    System.out.println("qs = " + Arrays.toString(qs.G));
    double[] Px = {0, 0, 1, 3, 5, 8, 9};
    double[] Py = {0, 0, 1, 4, 5, 2, 3};
    ClosestPair cp = new ClosestPair(1, 6, Px, Py);
    System.out.println("cp = " + cp.G[0]);
  }
}
