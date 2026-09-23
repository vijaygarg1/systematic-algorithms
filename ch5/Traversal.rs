// Generic queue-based reachability from vertex 0.

use std::collections::VecDeque;

fn traversal(dep: &[Vec<usize>]) -> Vec<i32> {
    let n = dep.len();
    let mut g = vec![0i32; n];
    g[0] = 1;
    let mut q: VecDeque<usize> = VecDeque::from([0]);
    while let Some(j) = q.pop_front() {
        for &k in &dep[j] {
            if g[k] == 0 { g[k] = 1; q.push_back(k); }
        }
    }
    g
}

fn main() {
    let dep: Vec<Vec<usize>> = vec![
        vec![1, 2], vec![3], vec![3, 4], vec![5], vec![5], vec![], vec![7], vec![]
    ];
    println!("reachable: {:?}", traversal(&dep));
}
