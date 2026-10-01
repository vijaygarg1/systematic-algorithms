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

// Returns the satisfying global state, or None if none exists (some
// process would need to advance past its last retained state).
fn conjunctive_algorithm(vc: &[Vec<i32>], t: &[i32]) -> Option<Vec<i32>> {
    let n = t.len();
    let mut g = vec![1i32; n];
    let mut changed = true;
    while changed {
        changed = false;
        for j in 0..n {
            if happened_before(j, &g, vc) {
                if g[j] >= t[j] { return None; }
                g[j] += 1;
                changed = true;
            }
        }
    }
    Some(g)
}

fn main() {
    let vc = vec![
        vec![0, 0], vec![0, 0],
        vec![0, 0], vec![0, 0],
    ];
    let t = [2i32, 2];
    match conjunctive_algorithm(&vc, &t) {
        Some(g) => println!("G: {:?}", g),
        None => println!("no satisfying global state"),
    }
}
