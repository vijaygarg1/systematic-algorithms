// 2-SAT via implication graph and SCC detection (Kosaraju's algorithm).
// Literal lit > 0 maps to index lit; literal lit < 0 maps to n + (-lit).

fn dfs1(u: usize, adj: &[Vec<usize>], visited: &mut [bool], order: &mut Vec<usize>) {
    visited[u] = true;
    for &w in &adj[u] {
        if !visited[w] { dfs1(w, adj, visited, order); }
    }
    order.push(u);
}

fn dfs2(u: usize, adj: &[Vec<usize>], comp: &mut [i32], c: i32) {
    comp[u] = c;
    for &w in &adj[u] {
        if comp[w] < 0 { dfs2(w, adj, comp, c); }
    }
}

fn two_sat(clause_a: &[i32], clause_b: &[i32]) -> Vec<bool> {
    let m = clause_a.len();
    let mut n = 0i32;
    for i in 0..m {
        n = n.max(clause_a[i].abs());
        n = n.max(clause_b[i].abs());
    }
    let sz = (2 * n + 2) as usize;
    let lit_index = |lit: i32| -> usize {
        if lit > 0 { lit as usize } else { (n - lit) as usize }
    };
    let mut adj_fwd: Vec<Vec<usize>> = vec![vec![]; sz];
    let mut adj_rev: Vec<Vec<usize>> = vec![vec![]; sz];
    for i in 0..m {
        let a = clause_a[i];
        let b = clause_b[i];
        let u1 = lit_index(-a);
        let v1 = lit_index(b);
        adj_fwd[u1].push(v1);
        adj_rev[v1].push(u1);
        let u2 = lit_index(-b);
        let v2 = lit_index(a);
        adj_fwd[u2].push(v2);
        adj_rev[v2].push(u2);
    }
    let mut visited = vec![false; sz];
    let mut order: Vec<usize> = Vec::with_capacity(sz);
    for v in 0..sz {
        if !visited[v] { dfs1(v, &adj_fwd, &mut visited, &mut order); }
    }
    let mut comp = vec![-1i32; sz];
    let mut num_comp = 0i32;
    for idx in (0..order.len()).rev() {
        let u = order[idx];
        if comp[u] < 0 {
            dfs2(u, &adj_rev, &mut comp, num_comp);
            num_comp += 1;
        }
    }
    let nu = n as usize;
    let mut result = vec![false; nu + 1];
    for xi in 1..=(n as i32) {
        result[xi as usize] = comp[lit_index(xi)] > comp[lit_index(-xi)];
    }
    result
}

fn main() {
    let a = [1i32, -1, 2];
    let b = [2i32, 2, -3];
    let r = two_sat(&a, &b);
    for i in 1..r.len() {
        print!("x{}={} ", i, if r[i] { 1 } else { 0 });
    }
    println!();
}
