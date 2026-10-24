// Classical Johnson all-pairs shortest paths: BellmanFord computes a
// non-negative price vector that reweights edges, then Dijkstra runs from
// every source on the reweighted graph. Returns None on negative cycle.
//
// The graph is a weighted adjacency list: adj[i] holds the out-neighbours
// of i and the parallel aw[i] their edge weights, so the weight of
// (i, adj[i][t]) is aw[i][t]. The reweighted graph is stored the same way,
// in aw_prime, which shares adj since reweighting changes no edge -- so the
// only Theta(n^2) object left is the all-pairs output itself, which is
// Theta(n^2) by definition.
//
// Both relaxation loops walk adj[i] rather than all n candidate heads, and
// the inner Dijkstra uses a binary heap rather than an array scan. That is
// what gives the book's O(nm log n): step 1 is n-1 passes over m edges, and
// step 4 is n Dijkstra runs of O(m log n) each. Scanning all pairs instead
// makes step 1 Theta(n^3) on its own, which already exceeds the bound on
// any sparse graph.

use std::cmp::Reverse;
use std::collections::BinaryHeap;

const INF: i32 = i32::MAX;

fn dijkstra(adj: &[Vec<usize>], aw: &[Vec<i32>], s: usize) -> Vec<i32> {
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

// all_pairs: returns D such that D[u][v] is the shortest-path cost from u to v;
// returns None if a negative-weight cycle exists.
fn all_pairs(adj: &[Vec<usize>], aw: &[Vec<i32>]) -> Option<Vec<Vec<i32>>> {
    let n = adj.len();
    // Step 1: BellmanFord from auxiliary source. Init dist[i] = 0; n-1 passes.
    let mut dist = vec![0i32; n];
    for _ in 0..n.saturating_sub(1) {
        let mut changed = false;
        for i in 0..n {
            for (t, &j) in adj[i].iter().enumerate() {
                if dist[i] + aw[i][t] < dist[j] {
                    dist[j] = dist[i] + aw[i][t];
                    changed = true;
                }
            }
        }
        if !changed { break; }
    }
    // One more pass: any further relaxation indicates a negative cycle.
    for i in 0..n {
        for (t, &j) in adj[i].iter().enumerate() {
            if dist[i] + aw[i][t] < dist[j] { return None; }
        }
    }
    // Step 2: price[v] = -dist[v].
    let price: Vec<i32> = dist.iter().map(|d| -d).collect();
    // Step 3: reweight, keeping the same adjacency structure.
    let mut aw_prime: Vec<Vec<i32>> = Vec::with_capacity(n);
    for i in 0..n {
        let mut row = Vec::with_capacity(adj[i].len());
        for (t, &j) in adj[i].iter().enumerate() {
            row.push(aw[i][t] + price[j] - price[i]);
        }
        aw_prime.push(row);
    }
    // Step 4: Dijkstra from every source; undo reweighting.
    let mut d = vec![vec![INF; n]; n];
    for u in 0..n {
        let dist_prime = dijkstra(adj, &aw_prime, u);
        for v in 0..n {
            if dist_prime[v] < INF {
                d[u][v] = dist_prime[v] + price[u] - price[v];
            }
        }
    }
    Some(d)
}

fn main() {
    // 3-vertex directed graph: A->B = 2, B->C = -1, A->C = 4.
    let adj: Vec<Vec<usize>> = vec![vec![1, 2], vec![2], vec![]];
    let aw:  Vec<Vec<i32>>   = vec![vec![2, 4], vec![-1], vec![]];
    match all_pairs(&adj, &aw) {
        None => println!("negative-weight cycle"),
        Some(d) => {
            for row in &d {
                for x in row {
                    if *x == INF { print!("INF "); }
                    else { print!("{} ", x); }
                }
                println!();
            }
        }
    }
}
