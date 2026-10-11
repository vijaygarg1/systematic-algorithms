// Ford-Fulkerson max-flow: repeatedly find an augmenting path in the
// residual graph via DFS, push its bottleneck capacity, until none
// remains. Shares its outer loop verbatim with EdmondsKarp.rs, which
// differs only in finding augmenting paths via BFS instead of DFS.

fn residual(c: &[Vec<i32>], f: &[Vec<i32>], u: usize, v: usize) -> i32 {
    c[u][v] - f[u][v]
}

fn dfs(c: &[Vec<i32>], f: &[Vec<i32>], u: usize, t: usize, parent: &mut [usize], seen: &mut [bool]) -> bool {
    if u == t { return true; }
    let n = c.len();
    for v in 0..n {
        if !seen[v] && residual(c, f, u, v) > 0 {
            seen[v] = true;
            parent[v] = u;
            if dfs(c, f, v, t, parent, seen) { return true; }
        }
    }
    false
}

fn find_augmenting_path(c: &[Vec<i32>], f: &[Vec<i32>], s: usize, t: usize, parent: &mut [usize]) -> bool {
    let n = c.len();
    let mut seen = vec![false; n];
    seen[s] = true;
    parent[s] = s;
    dfs(c, f, s, t, parent, &mut seen)
}

fn maxflow(c: &[Vec<i32>], s: usize, t: usize) -> Vec<Vec<i32>> {
    let n = c.len();
    let mut f = vec![vec![0i32; n]; n];
    let mut parent = vec![0usize; n];
    while find_augmenting_path(c, &f, s, t, &mut parent) {
        let mut bottleneck = i32::MAX;
        let mut v = t;
        while v != s {
            bottleneck = bottleneck.min(residual(c, &f, parent[v], v));
            v = parent[v];
        }
        let mut v = t;
        while v != s {
            f[parent[v]][v] += bottleneck;
            f[v][parent[v]] -= bottleneck;
            v = parent[v];
        }
    }
    f
}

fn main() {
    let n = 4;
    let mut c = vec![vec![0i32; n]; n];
    c[0][1] = 3; c[0][2] = 2; c[1][2] = 1; c[1][3] = 2; c[2][3] = 3;
    let f = maxflow(&c, 0, 3);
    let total: i32 = (0..n).map(|v| f[0][v]).sum();
    println!("max flow s=0 t=3: {}", total);
}
