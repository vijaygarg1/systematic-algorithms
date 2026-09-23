// LLP-Boruvka pointer-jumping kernel.

fn llp_boruvka(mut g: Vec<usize>) -> Vec<usize> {
    let mut changed = true;
    while changed {
        changed = false;
        for j in 0..g.len() {
            if g[j] != g[g[j]] { g[j] = g[g[j]]; changed = true; }
        }
    }
    g
}

fn main() {
    let g = vec![0usize, 0, 1, 2, 4, 4, 5, 6];
    let out = llp_boruvka(g);
    println!("after pointer-jumping: {:?}", out);
}
