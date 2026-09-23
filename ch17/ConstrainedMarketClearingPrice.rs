// LLP market clearing price: forbidden when item j is in a minimal
// overdemanded set; advance raises its price by 1.

fn is_overdemanded(j: usize, v: &[Vec<i32>], g: &[i32]) -> bool {
    let n = g.len();
    let m = v.len();
    let mut demand_count = 0;
    for b in 0..m {
        let best = v[b][j] - g[j];
        let mut is_best = true;
        for i in 0..n {
            if v[b][i] - g[i] > best { is_best = false; break; }
        }
        if is_best { demand_count += 1; }
    }
    demand_count > 1
}

fn constrained_market_clearing_price(v: &[Vec<i32>]) -> Vec<i32> {
    let n = v[0].len();
    let mut g = vec![0i32; n];
    let mut changed = true;
    while changed {
        changed = false;
        for j in 0..n {
            if is_overdemanded(j, v, &g) { g[j] += 1; changed = true; }
        }
    }
    g
}

fn main() {
    let v = vec![
        vec![5, 3, 1],
        vec![4, 4, 2],
        vec![1, 2, 5],
    ];
    let g = constrained_market_clearing_price(&v);
    println!("prices: {:?}", g);
}
