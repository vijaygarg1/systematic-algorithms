// Disjoint-set with path compression in find and union-by-rank.

import java.util.*;

public class UnionFind {
  public int find(int[] parent, int x) {
    if ((parent[x] != x)) {
      parent[x] = find(parent, parent[x]);
    }
    return parent[x];
  }

  public boolean union(int[] parent, int[] rank, int x, int y) {
    int rx = find(parent, x);
    int ry = find(parent, y);
    if ((rx == ry)) {
      return false;
    }
    if ((rank[rx] < rank[ry])) {
      parent[rx] = ry;
    } else {
      if ((rank[rx] > rank[ry])) {
        parent[ry] = rx;
      } else {
        parent[ry] = rx;
        rank[rx] = (rank[rx] + 1);
      }
    }
    return true;
  }

  public static void main(String[] args) {
    int[] parent = new int[] {5, 2, 4, 6, 1, 3, 8, 7};
    int x = 0;
    UnionFind prog = new UnionFind();
    int result = prog.find(parent, x);
    System.out.println(result);
  }
}