// FPTAS for Knapsack: scale values down by scale = eps*M/n, then run the
// standard 0/1 DP on the scaled instance.

fn fptas_knapsack(w: &[i32], v: &[i32], cap: usize, eps_num: i64, eps_den: i64) -> Vec<bool> {
    let n = w.len();
    let mm = *v.iter().max().unwrap_or(&0) as i64;

    let v_prime: Vec<i64> = (0..n)
        .map(|i| (v[i] as i64) * (n as i64) * eps_den / (eps_num * mm))
        .collect();

    let mut dp = vec![vec![0i64; cap + 1]; n + 1];
    for i in 1..=n {
        for c in 0..=cap {
            dp[i][c] = dp[i - 1][c];
            if (w[i - 1] as usize) <= c {
                let take = dp[i - 1][c - w[i - 1] as usize] + v_prime[i - 1];
                if take > dp[i][c] { dp[i][c] = take; }
            }
        }
    }
    let mut s = vec![false; n];
    let mut rem = cap;
    for i in (1..=n).rev() {
        if dp[i][rem] != dp[i - 1][rem] {
            s[i - 1] = true;
            rem -= w[i - 1] as usize;
        }
    }
    s
}

fn main() {
    let w = [2, 3, 4];
    let v = [30, 40, 50];
    let cap = 6usize;
    let s = fptas_knapsack(&w, &v, cap, 1, 5);
    print!("picked:");
    for (i, b) in s.iter().enumerate() { if *b { print!(" {}", i); } }
    println!();
}
