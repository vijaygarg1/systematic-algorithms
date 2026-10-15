// Sequential merge sort, in the book's own shape: each call RETURNS a
// newly sorted array rather than sorting a range of G in place, and the
// merge step is the sibling MergeTwo program -- `MergeTwo(B, C)`,
// compiling to `new MergeTwo().merge(B, C)` -- exactly as Algorithm
// MergeSort in bxx-sort.tex writes `Return MergeTwo(B, C)`. That makes
// MergeSort.java a multi-file program: it needs MergeTwo.java alongside
// it to compile (see JAVA_MULTI_FILE in validate-site.py and the
// `javaRequires` demo-spec field in llp.js).
//
// An earlier version sorted G[lo..hi] in place and carried its own
// 26-line two-pointer merge, duplicating MergeTwo.llp.
//
// The parallel companion is MergeSortPar in progs-divideConquer/, which
// is the same recurrence with the two recursive calls run in parallel --
// a split only this out-of-place shape makes natural, since in-place
// halves would contend for one array.

import java.util.*;

public class MergeSort {
  public int[] sort(int[] A, int lo, int hi) {
    if ((lo < hi)) {
      int mid = ((lo + hi) / 2);
      int[] B = sort(A, lo, mid);
      int[] C = sort(A, (mid + 1), hi);
      return new MergeTwo().merge(B, C);
    }
    int[] single = new int[1];
    single[0] = A[lo];
    return single;
  }

  public static void main(String[] args) {
    int[] A = new int[] {3, 1, 6, 1, 6, 3, 6, 4};
    MergeSort prog = new MergeSort();
    int[] result = prog.sort(A, 0, A.length - 1);
    System.out.println(Arrays.toString(result));
  }
}