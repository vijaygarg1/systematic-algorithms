// Counting inversions by divide and conquer, matching bxx-divideConquer.tex
// Algorithm CountingInversions (algo:counting-inversions): inversions in the
// left half, plus those in the right half, plus those spanning the two,
// which a merge counts for free -- when A[j] from the right half is taken
// first, it is inverted with every element still left in the left half.

import java.util.*;

public class CountingInversions {
  public int count(int[] A, int low, int high) {
    if ((low >= high)) {
      return 0;
    }
    int mid = ((low + high) / 2);
    int invLeft = count(A, low, mid);
    int invRight = count(A, (mid + 1), high);
    int invMerge = mergeAndCount(A, low, mid, high);
    return ((invLeft + invRight) + invMerge);
  }

  public int mergeAndCount(int[] A, int low, int mid, int high) {
    int[] B = new int[((high - low) + 1)];
    for (int k = low; k <= high; k++) {
      B[(k - low)] = A[k];
    }
    int i = low;
    int j = (mid + 1);
    int k = low;
    int inv = 0;
    while (((i <= mid) && (j <= high))) {
      if ((B[(i - low)] <= B[(j - low)])) {
        A[k] = B[(i - low)];
        i = (i + 1);
      } else {
        A[k] = B[(j - low)];
        j = (j + 1);
        inv = (inv + ((mid - i) + 1));
      }
      k = (k + 1);
    }
    while ((i <= mid)) {
      A[k] = B[(i - low)];
      i = (i + 1);
      k = (k + 1);
    }
    while ((j <= high)) {
      A[k] = B[(j - low)];
      j = (j + 1);
      k = (k + 1);
    }
    return inv;
  }

  public static void main(String[] args) {
    int[] A = new int[] {3, 1, 6, 1, 6, 3, 6, 4};
    CountingInversions prog = new CountingInversions();
    int result = prog.count(A, 0, A.length - 1);
    System.out.println(result);
  }
}