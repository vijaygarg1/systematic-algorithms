// Fractional knapsack: highest value-density first.

fn solve(v: &[f64], w: &[f64], cap: f64) -> Vec<f64> {
    let n = v.len();
    let mut x = vec![0.0; n];
    let mut rem = cap;
    for i in 0..n {
        if w[i] <= rem { x[i] = 1.0; rem -= w[i]; }
        else { x[i] = rem / w[i]; break; }
    }
    x
}

fn main() {
    let v = [60.0, 100.0, 120.0, 50.0];
    let w = [10.0, 20.0, 30.0, 40.0];
    let x = solve(&v, &w, 50.0);
    let total: f64 = x.iter().zip(v.iter()).map(|(a, b)| a * b).sum();
    println!("fractions: {:?}\ntotal value: {}", x, total);
}
