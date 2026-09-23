// Layering of a DAG via Kahn's algorithm.

use std::collections::VecDeque;

fn layering(pre: &[Vec<usize>], succ: &[Vec<usize>]) -> Vec<i32> {
    let n = pre.len();
    let mut g = vec![0i32; n];
    let mut indeg: Vec<i32> = pre.iter().map(|p| p.len() as i32).collect();
    let mut q: VecDeque<usize> = (0..n).filter(|&j| indeg[j] == 0).collect();
    while let Some(j) = q.pop_front() {
        for &k in &succ[j] {
            indeg[k] -= 1;
            if indeg[k] == 0 {
                let best = pre[k].iter().map(|&i| g[i] + 1).max().unwrap_or(0);
                g[k] = best;
                q.push_back(k);
            }
        }
    }
    g
}

fn main() {
    let pre:  Vec<Vec<usize>> = vec![vec![], vec![0], vec![0], vec![1, 2], vec![2], vec![3, 4]];
    let succ: Vec<Vec<usize>> = vec![vec![1, 2], vec![3], vec![3, 4], vec![5], vec![5], vec![]];
    println!("layers: {:?}", layering(&pre, &succ));
}
