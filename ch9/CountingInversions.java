// Counting inversions by divide and conquer, matching bxx-divideConquer.tex
// Algorithm CountingInversions (algo:counting-inversions): inversions in the
// left half, plus those in the right half, plus those spanning the two,
// which a merge counts for free -- when A[j] from the right half is taken
// first, it is inverted with every element still left in the left half.
//
// Laid out step for step as the book prints it: the merge reads A and
// writes the scratch array `temp`, and a final copy-back puts temp back
// into A[left..right]. An earlier version ran the merge the other way
// round -- snapshot A into B, then merge B back into A -- which computes
// the same thing but is the mirror image of the book's MergeAndCount and
// has no copy-back step at all.
//
// `temp` is sized to the sub-range, not to A. A new int[A.length] on every
// one of the Theta(n) merge calls costs Theta(n^2) on its own and swamps
// the O(n log n) the merging achieves.
//
// Two unavoidable departures from the printed page:
// - the book writes `temp[k++] := A[i++]`; LL has no ++, so each of
// those becomes three statements.
// - the copy-back loop variable is `t`, not the book's `k`. The book
// reuses the merge cursor, which is dead by then, but `for k in [...]`
// in LL re-declares it and the generated Java will not compile:
// "variable k is already defined in method MergeAndCount".

import java.util.*;

public class CountingInversions {
  public int CountInversions(int[] A, int left, int right) {
    if ((left >= right)) {
      return 0;
    }
    int mid = ((left + right) / 2);
    int invLeft = CountInversions(A, left, mid);
    int invRight = CountInversions(A, (mid + 1), right);
    int invMerge = MergeAndCount(A, left, mid, right);
    return ((invLeft + invRight) + invMerge);
  }

  public int MergeAndCount(int[] A, int left, int mid, int right) {
    int i = left;
    int j = (mid + 1);
    int k = 0;
    int count = 0;
    int[] temp = new int[((right - left) + 1)];
    while (((i <= mid) && (j <= right))) {
      if ((A[i] <= A[j])) {
        temp[k] = A[i];
        k = (k + 1);
        i = (i + 1);
      } else {
        temp[k] = A[j];
        k = (k + 1);
        j = (j + 1);
        count = (count + ((mid - i) + 1));
      }
    }
    while ((i <= mid)) {
      temp[k] = A[i];
      k = (k + 1);
      i = (i + 1);
    }
    while ((j <= right)) {
      temp[k] = A[j];
      k = (k + 1);
      j = (j + 1);
    }
    for (int t = 0; t <= (right - left); t++) {
      A[(left + t)] = temp[t];
    }
    return count;
  }

  public static void main(String[] args) {
    int[] A = new int[] {3, 1, 6, 1, 6, 3, 6, 4};
    CountingInversions prog = new CountingInversions();
    int result = prog.CountInversions(A, 0, A.length - 1);
    System.out.println(result);
  }
}