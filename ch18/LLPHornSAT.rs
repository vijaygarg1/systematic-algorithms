// LLP Horn SAT: forbidden when an implication's antecedents are all
// true but the consequent x_j is false; advance sets x_j to true.

fn horn_implied(j: usize, g: &[bool], body: &[Vec<usize>], head: &[i32]) -> bool {
    if g[j] { return false; }
    let m = body.len();
    for c in 0..m {
        if head[c] != j as i32 { continue; }
        let mut all_true = true;
        for &x in &body[c] { if !g[x] { all_true = false; break; } }
        if all_true { return true; }
    }
    false
}

fn llp_horn_sat(body: &[Vec<usize>], head: &[i32], n: usize) -> Vec<bool> {
    let mut g = vec![false; n];
    let mut changed = true;
    while changed {
        changed = false;
        for j in 0..n {
            if horn_implied(j, &g, body, head) { g[j] = true; changed = true; }
        }
    }
    g
}

fn main() {
    let body: Vec<Vec<usize>> = vec![vec![], vec![0], vec![0, 1]];
    let head = vec![0i32, 1, 2];
    let g = llp_horn_sat(&body, &head, 3);
    print!("G:");
    for b in &g { print!(" {}", if *b { 1 } else { 0 }); }
    println!();
}
