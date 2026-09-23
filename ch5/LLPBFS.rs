// LLP-BFS: forbidden when G[j] > min over preds of G[i] + 1.

const INF: i32 = i32::MAX;

fn llp_bfs(pre: &[Vec<usize>], g: &mut [i32]) {
    let mut changed = true;
    while changed {
        changed = false;
        for j in 0..g.len() {
            if pre[j].is_empty() { continue; }
            let best = pre[j].iter()
                .filter_map(|&i| if g[i] != INF { Some(g[i] + 1) } else { None })
                .min().unwrap_or(INF);
            if g[j] > best { g[j] = best; changed = true; }
        }
    }
}

fn main() {
    let pre: Vec<Vec<usize>> = vec![vec![], vec![0], vec![0], vec![1, 2], vec![2], vec![3, 4]];
    let mut g = vec![INF; pre.len()];
    g[0] = 0;
    llp_bfs(&pre, &mut g);
    println!("BFS distances: {:?}", g);
}
