// Interval partition: greedy by earliest start time -- assign each interval to any room whose previous interval has finished, or open a new room. Rooms used = max overlap depth.

import java.util.*;

public class IntervalPartition {
  public int[] partition(int[] s, int[] f) {
    int n = s.length;
    int[] G = new int[n];
    int[] roomFinish = new int[n];
    int numRooms = 0;
    int j = 0;
    while ((j < n)) {
      int r = (0 - 1);
      int i = 0;
      while (((i < numRooms) && (r == (0 - 1)))) {
        if ((roomFinish[i] <= s[j])) {
          r = i;
        }
        i = (i + 1);
      }
      if ((r == (0 - 1))) {
        r = numRooms;
        numRooms = (numRooms + 1);
      }
      G[j] = (r + 1);
      roomFinish[r] = f[j];
      j = (j + 1);
    }
    return G;
  }

  public static void main(String[] args) {
    int[] s = new int[] {1, 4, 7};
    int[] f = new int[] {2, 3, 5, 8};
    IntervalPartition prog = new IntervalPartition();
    int[] result = prog.partition(s, f);
    System.out.println(Arrays.toString(result));
  }
}