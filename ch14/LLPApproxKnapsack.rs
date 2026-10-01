// LLP FPTAS for Knapsack: profit-indexed min-weight DP D[i][p] on a
// lattice of size (nf+1) x (V'+1), independent of the capacity W --
// this is what keeps the scheme fully polynomial (a capacity-indexed
// lattice would instead grow with W and remain merely
// pseudo-polynomial). D[i][p] = minimum total weight of a subset of
// the first i feasible items with scaled profit >= p. Returns
// p* = max{p : D[nf][p] <= W}.

fn llp_approx_knapsack(w: &[i64], v: &[i64], cap: i64, eps_num: i64, eps_den: i64) -> i64 {
    let n = w.len();
    let (fw, fv): (Vec<i64>, Vec<i64>) = (0..n)
        .filter(|&i| w[i] <= cap)
        .map(|i| (w[i], v[i]))
        .unzip();
    let nf = fw.len();
    if nf == 0 { return 0; }
    let m = *fv.iter().max().unwrap();

    let v_prime: Vec<i64> = (0..nf).map(|i| fv[i] * (nf as i64) * eps_den / (eps_num * m)).collect();
    let vp: i64 = v_prime.iter().sum();

    let inf: i64 = 1 + fw.iter().sum::<i64>();
    let mut d = vec![vec![inf; (vp + 1) as usize]; nf + 1];
    for row in d.iter_mut() { row[0] = 0; }

    let mut changed = true;
    while changed {
        changed = false;
        for i in 1..=nf {
            for p in 1..=vp as usize {
                let mut target = d[i - 1][p];
                let prev = (p as i64 - v_prime[i - 1]).max(0) as usize;
                let take = fw[i - 1] + d[i - 1][prev];
                if take < target { target = take; }
                if target < d[i][p] { d[i][p] = target; changed = true; }
            }
        }
    }

    let mut p_star = 0i64;
    for p in 0..=vp {
        if d[nf][p as usize] <= cap { p_star = p; }
    }
    p_star
}

fn main() {
    let w = [2, 3, 4];
    let v = [30, 40, 50];
    let cap = 6;
    let p_star = llp_approx_knapsack(&w, &v, cap, 1, 5);
    println!("p* = {} (expect 24)", p_star);
    let scale = (1.0 * 50.0) / (5.0 * 3.0);
    println!("approx profit = {} (expect ~80)", scale * p_star as f64);
}
