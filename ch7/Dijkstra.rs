// Classical Dijkstra: binary min-heap H of (cost, vertex) pairs, with
// lazy deletion -- a vertex may be pushed more than once as its
// distance improves; stale entries are skipped on removal via the
// fixed[] check.
//
// The graph is a weighted adjacency list: adj[j] holds the out-neighbours
// of j and the parallel aw[j] their edge weights, so the weight of
// (j, adj[j][t]) is aw[j][t].  No n x n matrix is involved; the whole
// structure is O(n + m).
//
// The relaxation loop walks adj[j], which is the book's "forall k: not
// fixed[k] and (j,k) in E". That is what makes the algorithm O(m log n):
// each edge is relaxed once and contributes at most one heap push.

use std::cmp::Reverse;
use std::collections::BinaryHeap;

const INF: i32 = i32::MAX / 2;

fn shortest_path(adj: &[Vec<usize>], aw: &[Vec<i32>], s: usize) -> Vec<i32> {
    let n = adj.len();
    let mut dist = vec![INF; n];
    let mut fixed = vec![false; n];
    dist[s] = 0;
    let mut heap = BinaryHeap::new();
    heap.push(Reverse((0, s)));
    while let Some(Reverse((_c, j))) = heap.pop() {
        if fixed[j] { continue; }
        fixed[j] = true;
        for (t, &k) in adj[j].iter().enumerate() {
            if fixed[k] { continue; }
            if dist[j] + aw[j][t] < dist[k] {
                dist[k] = dist[j] + aw[j][t];
                heap.push(Reverse((dist[k], k)));
            }
        }
    }
    dist
}

fn main() {
    let adj: Vec<Vec<usize>> = vec![vec![1, 2], vec![2, 3], vec![3, 4], vec![4], vec![]];
    let aw:  Vec<Vec<i32>>   = vec![vec![4, 1], vec![2, 5], vec![8, 10], vec![2], vec![]];
    println!("dist: {:?}", shortest_path(&adj, &aw, 0));
}
