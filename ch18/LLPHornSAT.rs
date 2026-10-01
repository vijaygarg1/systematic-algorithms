// LLP Horn SAT. head[c] is the consequent variable of clause c, or -1
// if clause c is a pure negative (goal) clause (antecedents => false).
// Rule 1: a definite clause with all antecedents true and a false
// consequent forces that consequent true. Rule 2: a goal clause with
// all antecedents true proves unsatisfiability.

fn all_true(vars: &[usize], g: &[bool]) -> bool {
    vars.iter().all(|&x| g[x])
}

fn llp_horn_sat(body: &[Vec<usize>], head: &[i32], n: usize) -> Option<Vec<bool>> {
    let mut g = vec![false; n];
    let mut changed = true;
    while changed {
        changed = false;
        for c in 0..body.len() {
            if !all_true(&body[c], &g) { continue; }
            if head[c] == -1 { return None; }
            let h = head[c] as usize;
            if !g[h] { g[h] = true; changed = true; }
        }
    }
    Some(g)
}

fn main() {
    let body: Vec<Vec<usize>> = vec![vec![], vec![0], vec![0, 1]];
    let head = vec![0i32, 1, 2];
    let g = llp_horn_sat(&body, &head, 3);
    println!("G: {:?}", g);

    // Unsatisfiable: x0 forced true, then x0 => false.
    let body_unsat: Vec<Vec<usize>> = vec![vec![], vec![0]];
    let head_unsat = vec![0i32, -1];
    println!("UNSAT case: {:?}", llp_horn_sat(&body_unsat, &head_unsat, 1));
}
