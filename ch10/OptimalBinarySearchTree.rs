// Optimal BST: O(n^3) DP over interval [i, j] picking the root r.

fn solve(prob: &[f64]) -> Vec<Vec<f64>> {
    let n = prob.len();
    let mut dp = vec![vec![0.0f64; n]; n];
    let mut s = vec![vec![0.0f64; n]; n];
    for i in 0..n { dp[i][i] = prob[i]; s[i][i] = prob[i]; }
    for length in 1..n {
        for lo in 0..(n - length) {
            let hi = lo + length;
            s[lo][hi] = s[lo][hi - 1] + prob[hi];
            let mut best = f64::INFINITY;
            for r in lo..=hi {
                let left = if r > lo { dp[lo][r - 1] } else { 0.0 };
                let right = if r < hi { dp[r + 1][hi] } else { 0.0 };
                let cost = s[lo][hi] + left + right;
                if cost < best { best = cost; }
            }
            dp[lo][hi] = best;
        }
    }
    dp
}

fn main() {
    let prob = [0.25, 0.20, 0.30, 0.25];
    let dp = solve(&prob);
    println!("optimum BST cost = {}", dp[0][prob.len() - 1]);
}
