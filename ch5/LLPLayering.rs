// LLP-Layering: each j advances once all predecessors are fixed.

fn llp_layering(pre: &[Vec<usize>]) -> Vec<i32> {
    let n = pre.len();
    let mut g = vec![0i32; n];
    let mut fixed = vec![false; n];
    let mut changed = true;
    while changed {
        changed = false;
        for j in 0..n {
            if fixed[j] { continue; }
            if !pre[j].iter().all(|&i| fixed[i]) { continue; }
            let best = pre[j].iter().map(|&i| g[i] + 1).max().unwrap_or(0);
            g[j] = best; fixed[j] = true; changed = true;
        }
    }
    g
}

fn main() {
    let pre: Vec<Vec<usize>> = vec![vec![], vec![0], vec![0], vec![1, 2], vec![2], vec![3, 4]];
    println!("LLP layers: {:?}", llp_layering(&pre));
}
