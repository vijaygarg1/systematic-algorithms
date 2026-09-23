// Classical augmenting-path bipartite matching.

fn try_match(u: usize, adj: &[Vec<i32>], partner: &mut [i32], seen: &mut [bool]) -> bool {
    let m = adj[0].len();
    for v in 0..m {
        if adj[u][v] == 1 && !seen[v] {
            seen[v] = true;
            if partner[v] == -1 || try_match(partner[v] as usize, adj, partner, seen) {
                partner[v] = u as i32;
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
    let mut partner = vec![-1i32; m];
    for u in 0..n {
        let mut seen = vec![false; m];
        if try_match(u, adj, &mut partner, &mut seen) { g[u] = 1; }
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
