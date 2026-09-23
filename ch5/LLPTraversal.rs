// LLP-Traversal1: reachability on a directed graph.

fn llp_traversal(pre: &[Vec<usize>], g: &mut [bool]) {
    let mut changed = true;
    while changed {
        changed = false;
        for j in 0..g.len() {
            if g[j] { continue; }
            for &i in &pre[j] {
                if g[i] { g[j] = true; changed = true; break; }
            }
        }
    }
}

fn main() {
    let pre: Vec<Vec<usize>> = vec![vec![], vec![0], vec![0], vec![1, 2], vec![2], vec![3, 4]];
    let mut g = vec![false; pre.len()];
    g[0] = true;
    llp_traversal(&pre, &mut g);
    println!("reachable: {:?}", g);
}
