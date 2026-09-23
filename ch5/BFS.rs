// BFS distance from source s.

use std::collections::VecDeque;

fn bfs(dep: &[Vec<usize>], s: usize) -> Vec<i32> {
    let n = dep.len();
    let mut g = vec![i32::MAX; n];
    g[s] = 0;
    let mut q: VecDeque<usize> = VecDeque::from([s]);
    while let Some(j) = q.pop_front() {
        for &k in &dep[j] {
            if g[k] > g[j] + 1 { g[k] = g[j] + 1; q.push_back(k); }
        }
    }
    g
}

fn main() {
    let dep: Vec<Vec<usize>> = vec![vec![1, 2], vec![3], vec![3, 4], vec![5], vec![5], vec![]];
    println!("BFS from 0: {:?}", bfs(&dep, 0));
}
