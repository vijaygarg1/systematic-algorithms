// Return any index of key in a sorted array A, or -1 if absent.

import java.util.*;

public class BinarySearch {
  public static int BinarySearch(int[] A, int key, int low, int high) {
    if ((low > high)) {
      return (-1);
    }
    int mid = ((low + high) / 2);
    if ((A[mid] == key)) {
      return mid;
    }
    if ((A[mid] > key)) {
      return BinarySearch(A, key, low, (mid - 1));
    }
    return BinarySearch(A, key, (mid + 1), high);
  }

  public static void main(String[] args) {
    int[] A = new int[] {5, 2, 4, 6, 1, 3, 8, 7};
    int key = 0;
    int result = BinarySearch(A, key, 0, A.length);
    System.out.println(result);
  }
}