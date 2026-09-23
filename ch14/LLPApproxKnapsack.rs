// LLP FPTAS for Knapsack: scaled-value DP on the 2D lattice G[i, c]
// driven by a forbidden / advance pair on (i, c) pairs.

fn llp_approx_knapsack(w: &[i32], v: &[i32], cap: usize, eps_num: i64, eps_den: i64) -> Vec<Vec<i64>> {
    let n = w.len();
    let mm = *v.iter().max().unwrap_or(&0) as i64;

    let v_prime: Vec<i64> = (0..n)
        .map(|i| (v[i] as i64) * (n as i64) * eps_den / (eps_num * mm))
        .collect();

    let mut g = vec![vec![0i64; cap + 1]; n + 1];
    let mut changed = true;
    while changed {
        changed = false;
        for i in 1..=n {
            for c in 0..=cap {
                let mut target = g[i - 1][c];
                if (w[i - 1] as usize) <= c {
                    let take = g[i - 1][c - w[i - 1] as usize] + v_prime[i - 1];
                    if take > target { target = take; }
                }
                if g[i][c] < target { g[i][c] = target; changed = true; }
            }
        }
    }
    g
}

fn main() {
    let w = [2, 3, 4];
    let v = [30, 40, 50];
    let cap = 6usize;
    let g = llp_approx_knapsack(&w, &v, cap, 1, 5);
    println!("G[n][W] (scaled) = {}", g[g.len() - 1][cap]);
}
