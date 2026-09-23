// 2-approximation vertex cover: greedily pick both endpoints of an
// uncovered edge.

fn approx_vertex_cover(adj: &[Vec<i32>]) -> Vec<bool> {
    let n = adj.len();
    let mut c = vec![false; n];
    let mut removed = vec![false; n];
    let mut done = false;
    while !done {
        done = true;
        for u in 0..n {
            if removed[u] { continue; }
            for v in (u + 1)..n {
                if !removed[v] && adj[u][v] == 1 {
                    c[u] = true; c[v] = true;
                    removed[u] = true; removed[v] = true;
                    done = false;
                    break;
                }
            }
        }
    }
    c
}

fn main() {
    let adj = vec![
        vec![0, 1, 1, 0, 0],
        vec![1, 0, 0, 1, 0],
        vec![1, 0, 0, 1, 0],
        vec![0, 1, 1, 0, 1],
        vec![0, 0, 0, 1, 0],
    ];
    let c = approx_vertex_cover(&adj);
    print!("cover:");
    for (i, b) in c.iter().enumerate() { if *b { print!(" {}", i); } }
    println!();
}
