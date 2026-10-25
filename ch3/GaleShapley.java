// Classical Gale-Shapley man-optimal stable matching, written exactly as
// bxx-stableMatching.tex's Algorithm Gale--Shapley states it:
//
// var mList  : list of 1..n            // men that are free, initially all men
// partner: array[1..n] of 0..n     // current fiancee for woman i
// G      : array[1..n] of 0..n     // number of proposals made by man i
// mList := [1..n]
// while mList is nonempty:
// i := mList.removeFirst()
// G[i] := G[i] + 1
// z := mpref[i][G[i]]
// if partner[z] = 0 then partner[z] := i
// else if rank[z][i] < rank[z][partner[z]] then
// mList.add(partner[z]); partner[z] := i
// else mList.add(i)
// return G
//
// The free men are held in a list, as the book writes it, so that "pick a
// free man" is O(1). That is what the book's analysis requires: the while
// loop runs O(n^2) times because each G[i] rises at most n times, and
// bxx-stableMatching.tex states "Each iteration of the while loop takes
// O(1) time", giving O(n^2) overall. Scanning an array of free flags for
// a free man instead costs O(n) per proposal and makes the loop O(n^3).
//
// A man is on mList exactly when he is free: he is added back after a
// rejection, and a man displaced from z is added as he becomes free
// again. A man who is accepted is simply not added.
//
// The arrays are one longer than n and slot 0 is never used, so the
// indices are 1..n and the expressions are the book's with no offset.
// partner[z] = 0 means woman z is free: 0 is not a woman, since women
// are 1..n.

import java.util.*;

public class GaleShapley {
  public int[] GaleShapley(int[][] mpref, int[][] rank) {
    int n = (mpref.length - 1);
    LinkedList<Integer> mList = new LinkedList<>();
    int[] partner = new int[(n + 1)];
    int[] G = new int[(n + 1)];
    for (int i = 1; i <= n; i++) {
      mList.add(i);
    }
    while ((!mList.isEmpty())) {
      int i = mList.removeFirst();
      G[i] = (G[i] + 1);
      int z = mpref[i][G[i]];
      if ((partner[z] == 0)) {
        partner[z] = i;
      } else {
        if ((rank[z][i] < rank[z][partner[z]])) {
          mList.add(partner[z]);
          partner[z] = i;
        } else {
          mList.add(i);
        }
      }
    }
    return G;
  }

  public static void main(String[] args) {
    int[][] mpref = new int[][] {{0, 1, 2, 3, 4, 5, 6, 7}, {1, 2, 3, 4, 5, 6, 7, 0}, {2, 3, 4, 5, 6, 7, 0, 1}, {3, 4, 5, 6, 7, 0, 1, 2}, {4, 5, 6, 7, 0, 1, 2, 3}, {5, 6, 7, 0, 1, 2, 3, 4}, {6, 7, 0, 1, 2, 3, 4, 5}, {7, 0, 1, 2, 3, 4, 5, 6}};
    int[][] rank = new int[][] {{0, 7, 6, 5, 4, 3, 2, 1}, {1, 0, 7, 6, 5, 4, 3, 2}, {2, 1, 0, 7, 6, 5, 4, 3}, {3, 2, 1, 0, 7, 6, 5, 4}, {4, 3, 2, 1, 0, 7, 6, 5}, {5, 4, 3, 2, 1, 0, 7, 6}, {6, 5, 4, 3, 2, 1, 0, 7}, {7, 6, 5, 4, 3, 2, 1, 0}};
    GaleShapley prog = new GaleShapley();
    int[] result = prog.GaleShapley(mpref, rank);
    System.out.println(Arrays.toString(result));
  }
}