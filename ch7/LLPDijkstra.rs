// LLP-Dijkstra: priority-queue scheduler over the distance vector.

use std::collections::BinaryHeap;
use std::cmp::Reverse;

const INF: i32 = i32::MAX / 2;

fn llp_dijkstra(adj: &[Vec<usize>], w: &[Vec<i32>], s: usize) -> Vec<i32> {
    let n = adj.len();
    let mut d = vec![INF; n];
    let mut fixed = vec![false; n];
    d[s] = 0;
    let mut heap: BinaryHeap<Reverse<(i32, usize)>> = BinaryHeap::new();
    heap.push(Reverse((0, s)));
    while let Some(Reverse((dj, j))) = heap.pop() {
        if fixed[j] { continue; }
        fixed[j] = true;
        for &k in &adj[j] {
            let nd = dj + w[j][k];
            if nd < d[k] { d[k] = nd; heap.push(Reverse((nd, k))); }
        }
    }
    d
}

fn main() {
    let n = 5;
    let mut w = vec![vec![INF; n]; n];
    w[0][1] = 4;  w[0][2] = 1;  w[1][2] = 2;  w[1][3] = 5;
    w[2][3] = 8;  w[2][4] = 10; w[3][4] = 2;
    let mut adj: Vec<Vec<usize>> = vec![vec![]; n];
    for i in 0..n {
        for j in 0..n {
            if i != j && w[i][j] != INF { adj[i].push(j); }
        }
    }
    let d = llp_dijkstra(&adj, &w, 0);
    println!("d from 0: {:?}", d);
}
