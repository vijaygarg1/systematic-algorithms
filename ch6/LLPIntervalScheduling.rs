// LLP-IntervalScheduling: G[j] = true when job j is selected.

fn llp_interval_scheduling(s: &[i32], f: &[i32]) -> Vec<bool> {
    let n = s.len();
    let mut g = vec![false; n];
    let mut changed = true;
    while changed {
        changed = false;
        for j in 0..n {
            if g[j] { continue; }
            let compat = (0..j).all(|i| !g[i] || f[i] <= s[j]);
            if compat { g[j] = true; changed = true; }
        }
    }
    g
}

fn main() {
    let s = [1, 3, 0, 5, 8, 5];
    let f = [2, 4, 6, 7, 9, 9];
    let g = llp_interval_scheduling(&s, &f);
    let selected: Vec<usize> = (0..g.len()).filter(|&i| g[i]).collect();
    println!("selected: {:?}", selected);
}
