// Connected components by ensure-clause label propagation.

fn slow_components(adj: &[Vec<usize>]) -> Vec<usize> {
    let n = adj.len();
    let mut g: Vec<usize> = (0..n).collect();
    let mut changed = true;
    while changed {
        changed = false;
        for j in 0..n {
            if adj[j].is_empty() { continue; }
            let m = adj[j].iter().map(|&i| g[i]).max().unwrap();
            if g[j] < m { g[j] = m; changed = true; }
        }
    }
    g
}

fn main() {
    let adj: Vec<Vec<usize>> = vec![vec![1, 2], vec![0, 2], vec![0, 1], vec![4], vec![3]];
    println!("labels: {:?}", slow_components(&adj));
}
