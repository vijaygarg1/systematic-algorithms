// 0/1 knapsack: O(nW) bottom-up table fill.  w/v are 1-indexed.

fn solve(w: &[i32], v: &[i32], cap: usize) -> Vec<Vec<i32>> {
    let n = w.len() - 1;
    let mut g = vec![vec![0i32; cap + 1]; n + 1];
    for i in 1..=n {
        for c in 1..=cap {
            if (w[i] as usize) > c {
                g[i][c] = g[i - 1][c];
            } else {
                let skip = g[i - 1][c];
                let take = g[i - 1][c - w[i] as usize] + v[i];
                g[i][c] = skip.max(take);
            }
        }
    }
    g
}

fn main() {
    let w = vec![0, 2, 3, 4, 5];
    let v = vec![0, 3, 4, 5, 6];
    let cap = 8usize;
    let g = solve(&w, &v, cap);
    let n = w.len() - 1;
    println!("optimum = G[{}][{}] = {}", n, cap, g[n][cap]);
}
