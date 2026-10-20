// Classical augmenting-path bipartite matching, matching
// bxx-matchingReduced.tex Algorithm Seq-AugmentingPath (algo:seq-bipartite).

fn alternating_path(j: usize, adj: &[Vec<i32>], s: &mut [i32], seen: &mut [bool]) -> bool {
    let m = adj[0].len();
    for k in 0..m {
        if adj[j][k] == 1 && !seen[k] {
            seen[k] = true;
            if s[k] == -1 || alternating_path(s[k] as usize, adj, s, seen) {
                s[k] = j as i32;
                return true;
            }
        }
    }
    false
}

fn bipartite_matching(adj: &[Vec<i32>]) -> Vec<i32> {
    let n = adj.len();
    let m = adj[0].len();
    let mut g = vec![0i32; n];
    let mut s = vec![-1i32; m];
    for j in 0..n {
        let mut seen = vec![false; m];
        if alternating_path(j, adj, &mut s, &mut seen) { g[j] = 1; }
    }
    g
}

fn main() {
    let adj = vec![
        vec![1, 1, 0],
        vec![1, 0, 0],
        vec![0, 0, 1],
    ];
    let g = bipartite_matching(&adj);
    println!("matched on left: {:?}", g);
}
