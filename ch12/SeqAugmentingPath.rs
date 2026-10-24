// Classical augmenting-path bipartite matching, matching
// bxx-matchingReduced.tex Algorithm Seq-AugmentingPath (algo:seq-bipartite).

// The graph is the book's (L, R, E): adj[j] lists the R-neighbours of the
// L-vertex j, and nr is |R|, which cannot be recovered from the lists when
// some R-vertex has no incident edge. A dense |L| x |R| matrix would make
// every DFS step scan all of R however few edges exist.
fn alternating_path(j: usize, adj: &[Vec<usize>], s: &mut [i32], seen: &mut [bool]) -> bool {
    for &k in &adj[j] {
        if !seen[k] {
            seen[k] = true;
            if s[k] == -1 || alternating_path(s[k] as usize, adj, s, seen) {
                s[k] = j as i32;
                return true;
            }
        }
    }
    false
}

fn bipartite_matching(adj: &[Vec<usize>], nr: usize) -> Vec<i32> {
    let n = adj.len();
    let m = nr;
    let mut g = vec![0i32; n];
    let mut s = vec![-1i32; m];
    for j in 0..n {
        let mut seen = vec![false; m];
        if alternating_path(j, adj, &mut s, &mut seen) { g[j] = 1; }
    }
    g
}

fn main() {
    let adj: Vec<Vec<usize>> = vec![vec![0, 1], vec![0], vec![2]];
    let g = bipartite_matching(&adj, 3);
    println!("matched on left: {:?}", g);
}
