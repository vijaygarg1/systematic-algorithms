// Conjunctive predicate detection: forbidden when G[j] -> G[i]
// (happened-before); advance increments G[j] to the next local state.
//
// vc[j][s][i] is the i-th component of process j's vector clock at its
// s-th local state (a proper 3D structure: one vector clock per local
// state per process). An earlier version flattened this into a 2D array
// indexed by `j*n+G[j]`, which is out of bounds for any n >= 2 processes
// (the row index for the last process already exceeds vc's row count,
// regardless of G[j]) -- fixed by indexing the natural way instead.

fn happened_before(j: usize, g: &[i32], vc: &[Vec<Vec<i32>>]) -> bool {
    let n = g.len();
    for i in 0..n {
        if i != j && vc[j][g[j] as usize][i] >= g[i] { return true; }
    }
    false
}

// Returns the satisfying global state, or None if none exists (some
// process would need to advance past its last retained state).
fn conjunctive_algorithm(vc: &[Vec<Vec<i32>>], t: &[i32]) -> Option<Vec<i32>> {
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
    // 3 independent processes (no cross-process dependencies): already
    // a consistent cut at the initial state.
    let vc = vec![
        vec![vec![0, 0, 0], vec![1, 0, 0], vec![2, 0, 0]],
        vec![vec![0, 0, 0], vec![0, 1, 0], vec![0, 2, 0]],
        vec![vec![0, 0, 0], vec![0, 0, 1], vec![0, 0, 2]],
    ];
    let t = [2i32, 2, 2];
    match conjunctive_algorithm(&vc, &t) {
        Some(g) => println!("G: {:?}", g),
        None => println!("no satisfying global state"),
    }
}
