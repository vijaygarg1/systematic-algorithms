// IntervalPartition (bxx-greedy.tex, algo:interval-partition): for each
// interval i (sorted by start time), reuse the earliest-free room via a
// min-priority-queue Q of (finishTime, roomNumber) pairs -- extract-min
// when Q is non-empty and its minimum finish time is <= s[i], else open
// a new room -- matching the book's binary-heap algorithm exactly, not
// a linear scan over existing rooms each iteration.

import java.util.*;

public class IntervalPartition {
  public int[] partition(int[] s, int[] f) {
    int n = s.length;
    int[] G = new int[n];
    int cap = (n + 1);
    int[] heapFinish = new int[cap];
    int[] heapRoom = new int[cap];
    int heapSize = 0;
    int numRooms = 0;
    int j = 0;
    while ((j < n)) {
      int r = (0 - 1);
      if (((heapSize > 0) && (heapFinish[0] <= s[j]))) {
        r = heapRoom[0];
        heapSize = heapPop(heapFinish, heapRoom, heapSize);
      } else {
        r = numRooms;
        numRooms = (numRooms + 1);
      }
      G[j] = (r + 1);
      heapSize = heapPush(heapFinish, heapRoom, heapSize, f[j], r);
      j = (j + 1);
    }
    return G;
  }

  public boolean greater(int fa, int ra, int fb, int rb) {
    if ((fa != fb)) {
      return (fa > fb);
    }
    return (ra > rb);
  }

  public int heapPush(int[] hf, int[] hr, int size, int finish, int room) {
    int i = size;
    hf[i] = finish;
    hr[i] = room;
    size = (size + 1);
    while (((i > 0) && greater(hf[((i - 1) / 2)], hr[((i - 1) / 2)], hf[i], hr[i]))) {
      int p = ((i - 1) / 2);
      { int tmp = hf[i]; hf[i] = hf[p]; hf[p] = tmp; }
      { int tmp = hr[i]; hr[i] = hr[p]; hr[p] = tmp; }
      i = p;
    }
    return size;
  }

  public int heapPop(int[] hf, int[] hr, int size) {
    size = (size - 1);
    hf[0] = hf[size];
    hr[0] = hr[size];
    heapify(hf, hr, 0, size);
    return size;
  }

  public void heapify(int[] hf, int[] hr, int i, int size) {
    int smallest = i;
    int left = ((2 * i) + 1);
    int right = ((2 * i) + 2);
    if (((left < size) && greater(hf[smallest], hr[smallest], hf[left], hr[left]))) {
      smallest = left;
    }
    if (((right < size) && greater(hf[smallest], hr[smallest], hf[right], hr[right]))) {
      smallest = right;
    }
    if ((smallest != i)) {
      { int tmp = hf[i]; hf[i] = hf[smallest]; hf[smallest] = tmp; }
      { int tmp = hr[i]; hr[i] = hr[smallest]; hr[smallest] = tmp; }
      heapify(hf, hr, smallest, size);
    }
  }

  public static void main(String[] args) {
    int[] s = new int[] {0, 0, 1, 2};
    int[] f = new int[] {0, 0, 1, 1};
    IntervalPartition prog = new IntervalPartition();
    int[] result = prog.partition(s, f);
    System.out.println(Arrays.toString(result));
  }
}