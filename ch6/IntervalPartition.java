// Interval partitioning, as bxx-greedy.tex's Algorithm IntervalPartition
// states it:
//
// var Q: min-priority queue of (finishTime, roomNumber), initially empty
// numRooms: int, initially 0
// for i := 1 to n:
// if Q is not empty and Q.min().finishTime <= s[i]:
// room[i] := Q.extract-min().roomNumber    // reuse earliest-free room
// else:
// numRooms := numRooms + 1                 // allocate a new room
// room[i] := numRooms
// Q.insert(f[i], room[i])                    // busy until f[i]
// return room, numRooms
//
// Intervals arrive sorted by start time. The heap is keyed by finish
// time, so its minimum is the room that frees up earliest.
//
// The loop is `for`, not `forall`: Q and numRooms both carry from one
// iteration to the next.
//
// The book returns the pair (room, numRooms). LL returns one value, so
// room is returned; numRooms is max(room) and so is recoverable from it.
// Rooms are numbered from 1, as the book numbers them.

import java.util.*;

public class IntervalPartition {
  public int[] IntervalPartition(int[] s, int[] f) {
    int n = s.length;
    int[] room = new int[n];
    java.util.PriorityQueue<int[]> Q = new java.util.PriorityQueue<int[]>((a, b) -> a[0] != b[0] ? Integer.compare(a[0], b[0]) : Integer.compare(a[1], b[1]));
    int numRooms = 0;
    for (int i = 0; i < n; i++) {
      {
        if (((!Q.isEmpty()) && (Q.peek()[0] <= s[i]))) {
          room[i] = Q.peek()[1];
          Q.poll();
        } else {
          numRooms = (numRooms + 1);
          room[i] = numRooms;
        }
        Q.add(new int[]{f[i], room[i]});
      }
    }
    return room;
  }

  public static void main(String[] args) {
    int[] s = new int[] {0, 0, 1, 2};
    int[] f = new int[] {0, 0, 1, 1};
    IntervalPartition prog = new IntervalPartition();
    int[] result = prog.IntervalPartition(s, f);
    System.out.println(Arrays.toString(result));
  }
}