// IntervalPartition (bxx-greedy.tex, algo:interval-partition): for each
// interval i (sorted by start time), reuse the earliest-free room via a
// min-priority-queue Q of (finishTime, roomNumber) pairs -- extract-min
// when Q is non-empty and its minimum finish time is <= s[i], else open
// a new room -- matching the book's binary-heap algorithm exactly, not
// a linear scan over existing rooms each iteration. Uses the heap<int>
// builtin (-> java.util.PriorityQueue<int[]>, lexicographically ordered
// by (finish, room) so ties reuse the lowest-numbered free room) instead
// of hand-rolled heapPush/heapPop/heapify/greater.

import java.util.*;

public class IntervalPartition {
  public int[] partition(int[] s, int[] f) {
    int n = s.length;
    int[] G = new int[n];
    java.util.PriorityQueue<int[]> Q = new java.util.PriorityQueue<int[]>((a, b) -> a[0] != b[0] ? Integer.compare(a[0], b[0]) : Integer.compare(a[1], b[1]));
    int numRooms = 0;
    int j = 0;
    while ((j < n)) {
      int r = (0 - 1);
      if (((!Q.isEmpty()) && (Q.peek()[0] <= s[j]))) {
        r = Q.peek()[1];
        Q.poll();
      } else {
        r = numRooms;
        numRooms = (numRooms + 1);
      }
      G[j] = (r + 1);
      Q.add(new int[]{f[j], r});
      j = (j + 1);
    }
    return G;
  }

  public static void main(String[] args) {
    int[] s = new int[] {0, 0, 1, 2};
    int[] f = new int[] {0, 0, 1, 1};
    IntervalPartition prog = new IntervalPartition();
    int[] result = prog.partition(s, f);
    System.out.println(Arrays.toString(result));
  }
}