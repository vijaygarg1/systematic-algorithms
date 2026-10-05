// LLP-BitonicMerge: forbidden when a pair at distance m is inverted.
//
// `i + m < n` is an explicit bound, not part of the original two-line
// statement of this predicate: the compiler has no notion of the
// "priority" comment below (true Batcher bitonic merge restricts m to
// a specific power-of-two schedule), so without it this compiles to a
// plain nested loop over the full cross product i, m in [0, n), and
// i + m reaches n .. 2n-2 -- out of bounds on A (confirmed: crashed
// every synthesized main). With the bound, this still converges (any
// inversion A[i] > A[j], i < j, is directly compared once m = j - i)
// and still always finishes fully sorted; it just does so as a
// generic compare-exchange-network sort rather than the asymptotically
// faster, truly parallel recursive bitonic schedule the comment
// describes.

import java.util.*;

public class LLPBitonicMerge {
  int n;
  int[] A;
  int i;
  int m;

  private boolean forbidden(int i, int m) {
    if (!(((i + m) < n))) return false;
    if (!((A[i] > A[(i + m)]))) return false;
    return true;
  }

  private void advance() {
    { int tmp = A[i]; A[i] = A[(i + m)]; A[(i + m)] = tmp; }
  }

  public void LLPBitonicMerge(int[] A, int n) {
    this.A = A;
    this.n = n;
    this.n = A.length;
    {
      boolean changed = true;
      while (changed) {
        changed = false;
        for (int i = 0; i < n; i++) {
          for (int m = 0; m < n; m++) {
            if (forbidden(i, m)) {
              this.i = i; this.m = m; advance();
              changed = true;
            }
          }
        }
      }
    }
  }

  public static void main(String[] args) {
    int[] A = new int[] {3, 1, 6, 1, 6, 3, 6, 4};
    LLPBitonicMerge prog = new LLPBitonicMerge();
    prog.LLPBitonicMerge(A, A.length);
    System.out.println(Arrays.toString(A));
  }
}