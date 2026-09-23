// König's-theorem construction of a vertex cover of size |M| from a
// max matching.

fn par_vertex_cover_from_matching(adj: &[Vec<i32>], match_l: &[i32]) -> Vec<bool> {
    let l = adj.len();
    let r = adj[0].len();
    let mut c = vec![false; l + r];
    let mut partner = vec![-1i32; l + r];

    for u in 0..l {
        let v = match_l[u];
        if v != -1 {
            c[u] = true;
            partner[u] = (l as i32) + v;
            partner[l + v as usize] = u as i32;
        }
    }

    for u in 0..l {
        for v in 0..r {
            if adj[u][v] == 1 && !c[u] && !c[l + v] {
                if partner[u] != -1 {
                    c[partner[u] as usize] = false;
                    c[u] = true;
                } else {
                    c[partner[l + v] as usize] = false;
                    c[l + v] = true;
                }
            }
        }
    }
    c
}

fn main() {
    let adj = vec![
        vec![1, 1, 0],
        vec![1, 0, 1],
        vec![0, 1, 1],
    ];
    let match_l = [0i32, 2, 1];
    let c = par_vertex_cover_from_matching(&adj, &match_l);
    print!("cover bits:");
    for b in &c { print!(" {}", if *b { 1 } else { 0 }); }
    println!();
}
