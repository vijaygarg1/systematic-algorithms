// LLP-FractionalKnapsack: items pre-sorted by v/w density.

fn prefix_weight(w: &[f64], upto: i32) -> f64 {
    if upto < 0 { return 0.0; }
    (0..=upto as usize).map(|i| w[i]).sum()
}

fn target(w: &[f64], cap: f64, j: usize) -> f64 {
    let prev = prefix_weight(w, j as i32 - 1);
    let cur = prev + w[j];
    if cur <= cap { 1.0 } else if prev >= cap { 0.0 } else { (cap - prev) / w[j] }
}

fn llp_fractional_knapsack(v: &[f64], w: &[f64], cap: f64) -> Vec<f64> {
    let _ = v;
    let n = w.len();
    let mut g = vec![0.0; n];
    let mut changed = true;
    while changed {
        changed = false;
        for j in 0..n {
            let t = target(w, cap, j);
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
