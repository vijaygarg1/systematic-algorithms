// LLP-FractionalKnapsack: items pre-sorted by v/w density.

fn prefix_scan(w: &[f64]) -> Vec<f64> {
    let mut s = vec![0.0; w.len()];
    let mut run = 0.0;
    for i in 0..w.len() {
        run += w[i];
        s[i] = run;
    }
    s
}

fn target(w: &[f64], s: &[f64], cap: f64, j: usize) -> f64 {
    let prev = if j > 0 { s[j - 1] } else { 0.0 };
    if s[j] <= cap { 1.0 } else if prev >= cap { 0.0 } else { (cap - prev) / w[j] }
}

fn llp_fractional_knapsack(v: &[f64], w: &[f64], cap: f64) -> Vec<f64> {
    let _ = v;
    let n = w.len();
    let mut g = vec![0.0; n];
    let s = prefix_scan(w);
    let mut changed = true;
    while changed {
        changed = false;
        for j in 0..n {
            let t = target(w, &s, cap, j);
            if g[j] < t { g[j] = t; changed = true; }
        }
    }
    g
}

fn main() {
    let v = [1.0, 2.0, 3.0, 4.0];
    let w = [1.0, 2.0, 3.0, 4.0];
    println!("G: {:?}", llp_fractional_knapsack(&v, &w, 5.0));
}
