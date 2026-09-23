// Classical O(n^2) LIS DP.

fn solve(a: &[i32]) -> Vec<i32> {
    let n = a.len();
    let mut dp = vec![1i32; n];
    for i in 1..n {
        for j in 0..i {
            if a[j] < a[i] && dp[j] + 1 > dp[i] { dp[i] = dp[j] + 1; }
        }
    }
    dp
}

fn main() {
    let a = [3, 10, 2, 1, 20, 4];
    let dp = solve(&a);
    let lis = *dp.iter().max().unwrap_or(&0);
    println!("A = {:?}", a);
    println!("dp = {:?}, LIS length = {}", dp, lis);
}
