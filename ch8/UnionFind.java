// Disjoint-set with path compression in find and union-by-rank.

import java.util.*;

public class UnionFind {
  public int Find(int[] parent, int x) {
    if ((parent[x] != x)) {
      parent[x] = Find(parent, parent[x]);
    }
    return parent[x];
  }

  public boolean Union(int[] parent, int[] rank, int x, int y) {
    int rx = Find(parent, x);
    int ry = Find(parent, y);
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
    int[] parent = new int[] {3, 1, 6, 1, 6, 3, 6, 4};
    int x = 0;
    UnionFind prog = new UnionFind();
    int result = prog.Find(parent, x);
    System.out.println(result);
  }
}