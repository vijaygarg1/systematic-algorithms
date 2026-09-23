// Precedences: composition program enforcing G[j] >= G[i] + t[i] for
// every i in pre(j).

use std::collections::BTreeSet;

fn precedences(pre: &[BTreeSet<usize>], t: &[i32], g: &mut [i32]) {
    let n = g.len();
    let mut changed = true;
    while changed {
        changed = false;
        for j in 0..n {
            let mut target = g[j];
            for &i in &pre[j] {
                target = target.max(g[i] + t[i]);
            }
            if target > g[j] { g[j] = target; changed = true; }
        }
    }
}

fn main() {
    let pre: Vec<BTreeSet<usize>> =
        vec![BTreeSet::new(), [0usize].into(), [0usize, 1].into()];
    let t = [3, 4, 2];
    let mut g = vec![0; t.len()];
    precedences(&pre, &t, &mut g);
    println!("G: {:?}", g);
}
