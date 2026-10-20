// GCD1: descending GCD via Euclidean reduction.
// Forbidden when G[j] > G[i]; advance reduces G[j] by mod.

fn gcd1(a: Vec<i32>) -> Vec<i32> {
    let n = a.len();
    let mut g = a;
    let mut changed = true;
    while changed {
        changed = false;
        for j in 0..n {
            let mut picked: i32 = -1;
            for i in 0..n {
                if g[j] > g[i] { picked = i as i32; break; }
            }
            if picked == -1 { continue; }
            let i = picked as usize;
            g[j] = if g[j] % g[i] == 0 { g[i] } else { g[j] % g[i] };
            changed = true;
        }
    }
    g
}

fn main() {
    let g = gcd1(vec![48, 36, 60]);
    println!("gcd={}", g[0]);
}
