// Classical Prim MST (bxx-mst.tex, algo:prim): binary min-heap of
// (cost, vertex) with lazy deletion, relaxing over adj[v] -- the book's
// "forall v': not fixed[v'] and (v,v') in E".  The graph is a weighted
// adjacency list: adj[v] holds the neighbours of v and the parallel aw[v]
// their edge weights, so the weight of (v, adj[v][t]) is aw[v][t].  No
// n x n matrix is involved; the whole structure is O(n + m).  With the heap supplying the minimum this is the
// O(m log n) variant of Table 8.1, not the O(n^2) array-scan variant of
// the surrounding text (bxx-mst.tex:1009), which finds the minimum by
// scanning d[] rather than with a heap.  This file used to be the
// array-scan version while the .llp was already the heap one.

use std::cmp::Reverse;
use std::collections::BinaryHeap;

const INF: i32 = i32::MAX / 2;

fn mst(adj: &[Vec<usize>], aw: &[Vec<i32>]) -> Vec<i32> {
    let n = adj.len();
    let mut d = vec![INF; n];
    let mut parent = vec![-1i32; n];
    let mut fixed = vec![false; n];
    d[0] = 0;
    let mut heap = BinaryHeap::new();
    heap.push(Reverse((0, 0usize)));
    let mut count = 0;
    while count < n {
        let v = match heap.pop() {
            Some(Reverse((_c, v))) => v,
            None => break,                 // disconnected: no spanning tree
        };
        if fixed[v] { continue; }
        fixed[v] = true;
        count += 1;
        for (t, &k) in adj[v].iter().enumerate() {
            if !fixed[k] && aw[v][t] < d[k] {
                d[k] = aw[v][t];
                parent[k] = v as i32;
                heap.push(Reverse((d[k], k)));
            }
        }
    }
    parent
}

fn main() {
    let adj: Vec<Vec<usize>> = vec![vec![1,2], vec![0,2,3], vec![0,1,3,4], vec![1,2,4], vec![2,3]];
    let aw:  Vec<Vec<i32>>   = vec![vec![1,3], vec![1,2,6], vec![3,2,4,5], vec![6,4,7], vec![5,7]];
    let p = mst(&adj, &aw);
    println!("parent: {:?}", p);
}
