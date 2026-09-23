// Conjunctive predicate detection: forbidden when G[j] -> G[i]
// (happened-before); advance increments G[j] to the next local state.

fn happened_before(j: usize, g: &[i32], vc: &[Vec<i32>]) -> bool {
    let n = g.len();
    let row = j * n + g[j] as usize;
    for i in 0..n {
        if i != j && vc[row][i] >= g[i] { return true; }
    }
    false
}

fn conjunctive_algorithm(vc: &[Vec<i32>], t: &[i32]) -> Vec<i32> {
    let n = t.len();
    let mut g = vec![1i32; n];
    let mut changed = true;
    while changed {
        changed = false;
        for j in 0..n {
            if g[j] >= t[j] { continue; }
            if happened_before(j, &g, vc) { g[j] += 1; changed = true; }
        }
    }
    g
}

fn main() {
    let vc = vec![
        vec![0, 0], vec![0, 0],
        vec![0, 0], vec![0, 0],
    ];
    let t = [2i32, 2];
    let g = conjunctive_algorithm(&vc, &t);
    println!("G: {:?}", g);
}
