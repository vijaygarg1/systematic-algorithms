// MergeSort as an example of recursion, matching bxx-introduction.tex
// Algorithm MergeSort-Intro (algo:mergesort): split, sort each half, merge.
// Merge copies the range into a scratch array B and then walks the two
// halves back into A; its two leftover-copy loops are one line each, as
// the book prints them. B is sized to the sub-range, not to all of A:
// allocating and copying the whole array on every one of the Theta(n)
// merge calls costs Theta(n^2) by itself, which swamps the O(n log n) the
// merging achieves.

import java.util.*;

public class MergeSortIntro {
  public void MergeSortIntro(int[] A, int low, int high) {
    if ((low < high)) {
      int mid = ((low + high) / 2);
      MergeSortIntro(A, low, mid);
      MergeSortIntro(A, (mid + 1), high);
      Merge(A, low, mid, high);
    }
  }

  public void Merge(int[] A, int low, int mid, int high) {
    int[] B = new int[((high - low) + 1)];
    for (int k = low; k <= high; k++) {
      B[(k - low)] = A[k];
    }
    int i = low;
    int j = (mid + 1);
    int k = low;
    while (((i <= mid) && (j <= high))) {
      if ((B[(i - low)] <= B[(j - low)])) {
        A[k] = B[(i - low)];
        i = (i + 1);
      } else {
        A[k] = B[(j - low)];
        j = (j + 1);
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
  }

  public static void main(String[] args) {
    int[] A = new int[] {3, 1, 6, 1, 6, 3, 6, 4};
    MergeSortIntro prog = new MergeSortIntro();
    prog.MergeSortIntro(A, 0, A.length - 1);
    System.out.println(Arrays.toString(A));
  }
}