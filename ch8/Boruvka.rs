// Classical Boruvka MST: repeatedly attach every component to its
// cheapest outgoing edge until one component remains.

use std::collections::VecDeque;

fn neighbor_if(a: usize, b: usize, v: usize) -> Option<usize> {
    if a == v { Some(b) } else if b == v { Some(a) } else { None }
}

fn components(n: usize, u: &[usize], v: &[usize], in_tree: &[bool]) -> Vec<usize> {
    let mut cid = vec![0usize; n];
    let mut visited = vec![false; n];
    for start in 0..n {
        if visited[start] { continue; }
        visited[start] = true;
        cid[start] = start;
        let mut q = VecDeque::new();
        q.push_back(start);
        while let Some(x) = q.pop_front() {
            for e in 0..u.len() {
                if !in_tree[e] { continue; }
                if let Some(w) = neighbor_if(u[e], v[e], x) {
                    if !visited[w] {
                        visited[w] = true;
                        cid[w] = cid[start];
                        q.push_back(w);
                    }
                }
            }
        }
    }
    cid
}

fn mst(n: usize, u: &[usize], v: &[usize], w: &[f64]) -> Vec<bool> {
    let m = u.len();
    let mut in_tree = vec![false; m];
    let mut tree_edges = 0;
    while tree_edges < n - 1 {
        let cid = components(n, u, v, &in_tree);

        let mut mwe: Vec<i64> = vec![-1; n];
        let mut dist = vec![f64::INFINITY; n];
        for e in 0..m {
            let (i, j) = (u[e], v[e]);
            if cid[i] != cid[j] {
                if w[e] < dist[cid[i]] { dist[cid[i]] = w[e]; mwe[cid[i]] = e as i64; }
                if w[e] < dist[cid[j]] { dist[cid[j]] = w[e]; mwe[cid[j]] = e as i64; }
            }
        }

        for i in 0..n {
            if cid[i] == i && mwe[i] != -1 && !in_tree[mwe[i] as usize] {
                in_tree[mwe[i] as usize] = true;
                tree_edges += 1;
            }
        }
    }
    in_tree
}

fn main() {
    let n = 5;
    let u = [0usize, 1, 0, 3, 1, 2];
    let v = [2usize, 2, 3, 4, 3, 4];
    let w = [4.0, 3.0, 7.0, 2.0, 9.0, 11.0];
    let in_tree = mst(n, &u, &v, &w);
    println!("inTree: {:?}", in_tree);
}
