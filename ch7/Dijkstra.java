// Classical Dijkstra (bxx-shortestPath.tex, fig:dijk): binary min-heap
// H of (vertex, cost) pairs keyed by cost, with lazy deletion -- a
// vertex may be pushed more than once as its distance improves; stale
// entries are simply skipped on removal via the fixed[] check -- all
// exactly matching the book's H.add()/H.removeMin() algorithm, not a
// linear O(n) scan for the minimum each round.

import java.util.*;

public class Dijkstra {
  public int[] shortestPath(int[][] w, int s) {
    int n = w.length;
    int INF = 2147483647;
    int[] dist = new int[n];
    boolean[] fixed = new boolean[n];
    for (int i = 0; i < n; i++) {
      dist[i] = INF;
    }
    dist[s] = 0;
    int cap = ((n * n) + 1);
    int[] heapVertex = new int[cap];
    int[] heapCost = new int[cap];
    int heapSize = 0;
    heapSize = heapPush(heapVertex, heapCost, heapSize, s, dist[s]);
    while ((heapSize > 0)) {
      int j = heapVertex[0];
      heapSize = heapPop(heapVertex, heapCost, heapSize);
      if ((!fixed[j])) {
        fixed[j] = true;
        int k = 0;
        while ((k < n)) {
          if ((((!fixed[k]) && (w[j][k] < INF)) && ((dist[j] + w[j][k]) < dist[k]))) {
            dist[k] = (dist[j] + w[j][k]);
            heapSize = heapPush(heapVertex, heapCost, heapSize, k, dist[k]);
          }
          k = (k + 1);
        }
      }
    }
    return dist;
  }

  public int heapPush(int[] hv, int[] hc, int size, int vertex, int cost) {
    int i = size;
    hv[i] = vertex;
    hc[i] = cost;
    size = (size + 1);
    while (((i > 0) && (hc[((i - 1) / 2)] > hc[i]))) {
      int p = ((i - 1) / 2);
      { int tmp = hv[i]; hv[i] = hv[p]; hv[p] = tmp; }
      { int tmp = hc[i]; hc[i] = hc[p]; hc[p] = tmp; }
      i = p;
    }
    return size;
  }

  public int heapPop(int[] hv, int[] hc, int size) {
    size = (size - 1);
    hv[0] = hv[size];
    hc[0] = hc[size];
    heapify(hv, hc, 0, size);
    return size;
  }

  public void heapify(int[] hv, int[] hc, int i, int size) {
    int smallest = i;
    int left = ((2 * i) + 1);
    int right = ((2 * i) + 2);
    if (((left < size) && (hc[left] < hc[smallest]))) {
      smallest = left;
    }
    if (((right < size) && (hc[right] < hc[smallest]))) {
      smallest = right;
    }
    if ((smallest != i)) {
      { int tmp = hv[i]; hv[i] = hv[smallest]; hv[smallest] = tmp; }
      { int tmp = hc[i]; hc[i] = hc[smallest]; hc[smallest] = tmp; }
      heapify(hv, hc, smallest, size);
    }
  }

  public static void main(String[] args) {
    int[][] w = new int[][] {{0, 1, 2, 3, 4, 5, 6, 7}, {1, 2, 3, 4, 5, 6, 7, 0}, {2, 3, 4, 5, 6, 7, 0, 1}, {3, 4, 5, 6, 7, 0, 1, 2}, {4, 5, 6, 7, 0, 1, 2, 3}, {5, 6, 7, 0, 1, 2, 3, 4}, {6, 7, 0, 1, 2, 3, 4, 5}, {7, 0, 1, 2, 3, 4, 5, 6}};
    Dijkstra prog = new Dijkstra();
    int[] result = prog.shortestPath(w, 0);
    System.out.println(Arrays.toString(result));
  }
}