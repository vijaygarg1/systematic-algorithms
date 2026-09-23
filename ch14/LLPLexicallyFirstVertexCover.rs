// LLP parallel 2-approximation for Vertex Cover: pick every endpoint
// of a lex-minimal uncovered edge.

fn lex_less(a: i32, b: i32, c: i32, d: i32) -> bool {
    let amin = a.min(b);
    let amax = a.max(b);
    let cmin = c.min(d);
    let cmax = c.max(d);
    if amin < cmin { return true; }
    if amin > cmin { return false; }
    amax < cmax
}

fn is_lex_min_incident(i: usize, j: usize, adj: &[Vec<i32>], g: &[bool]) -> bool {
    if adj[i][j] != 1 || g[i] || g[j] { return false; }
    let n = g.len();
    for x in 0..n {
        for y in (x + 1)..n {
            if adj[x][y] != 1 || g[x] || g[y] { continue; }
            if x != i && x != j && y != i && y != j { continue; }
            if lex_less(x as i32, y as i32, i as i32, j as i32) && !(x == i && y == j) {
                return false;
            }
        }
    }
    true
}

fn llp_lexically_first_vertex_cover(adj: &[Vec<i32>]) -> Vec<bool> {
    let n = adj.len();
    let mut g = vec![false; n];
    let mut changed = true;
    while changed {
        changed = false;
        for j in 0..n {
            let mut fired = false;
            for i in 0..n {
                if fired { break; }
                if is_lex_min_incident(i, j, adj, &g) { fired = true; }
            }
            if fired && !g[j] { g[j] = true; changed = true; }
        }
    }
    g
}

fn main() {
    let adj = vec![
        vec![0, 1, 1, 0, 0],
        vec![1, 0, 0, 1, 0],
        vec![1, 0, 0, 1, 0],
        vec![0, 1, 1, 0, 1],
        vec![0, 0, 0, 1, 0],
    ];
    let g = llp_lexically_first_vertex_cover(&adj);
    print!("cover:");
    for (i, b) in g.iter().enumerate() { if *b { print!(" {}", i); } }
    println!();
}
