// Horn SAT forward chaining (Dowling-Gallier): unit propagation with counters.

import java.util.*;

public class HornSATFC {
  public boolean[] HornSATFC(int[][] body, int[] head, int[][] adj) {
    int n = adj.length;
    int m = body.length;
    boolean[] A = new boolean[n];
    int[] rem = new int[m];
    int c = 0;
    while ((c < m)) {
      rem[c] = body[c].length;
      c = (c + 1);
    }
    int[] queue = new int[n];
    int front = 0;
    int back = 0;
    c = 0;
    while ((c < m)) {
      if (((rem[c] == 0) && (head[c] >= 0))) {
        if ((!A[head[c]])) {
          queue[back] = head[c];
          back = (back + 1);
        }
      }
      c = (c + 1);
    }
    boolean sat = true;
    while (((front < back) && sat)) {
      int x = queue[front];
      front = (front + 1);
      if ((!A[x])) {
        A[x] = true;
        int k = 0;
        while ((k < adj[x].length)) {
          int ci = adj[x][k];
          rem[ci] = (rem[ci] - 1);
          if ((rem[ci] == 0)) {
            if ((head[ci] < 0)) {
              sat = false;
            } else {
              if ((!A[head[ci]])) {
                queue[back] = head[ci];
                back = (back + 1);
              }
            }
          }
          k = (k + 1);
        }
      }
    }
    return A;
  }

  public static void main(String[] args) {
    int[][] body = new int[][] {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};
    int[] head = new int[] {5, 2, 4, 6, 1, 3, 8, 7};
    int[][] adj = new int[][] {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};
    HornSATFC prog = new HornSATFC();
    boolean[] result = prog.HornSATFC(body, head, adj);
    System.out.println(Arrays.toString(result));
  }
}