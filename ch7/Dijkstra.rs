// Classical Dijkstra: binary min-heap H of (cost, vertex) pairs, with
// lazy deletion -- a vertex may be pushed more than once as its
// distance improves; stale entries are skipped on removal via the
// fixed[] check.

use std::cmp::Reverse;
use std::collections::BinaryHeap;

const INF: i32 = i32::MAX / 2;

fn shortest_path(w: &[Vec<i32>], s: usize) -> Vec<i32> {
    let n = w.len();
    let mut dist = vec![INF; n];
    let mut fixed = vec![false; n];
    dist[s] = 0;
    let mut heap = BinaryHeap::new();
    heap.push(Reverse((0, s)));
    while let Some(Reverse((_c, j))) = heap.pop() {
        if fixed[j] { continue; }
        fixed[j] = true;
        for k in 0..n {
            if fixed[k] || w[j][k] >= INF { continue; }
            if dist[j] + w[j][k] < dist[k] {
                dist[k] = dist[j] + w[j][k];
                heap.push(Reverse((dist[k], k)));
            }
        }
    }
    dist
}

fn main() {
    let n = 5;
    let mut w = vec![vec![INF; n]; n];
    w[0][1] = 4;  w[0][2] = 1;  w[1][2] = 2;  w[1][3] = 5;
    w[2][3] = 8;  w[2][4] = 10; w[3][4] = 2;
    println!("dist: {:?}", shortest_path(&w, 0));
}
