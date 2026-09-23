// LCM1: ascending LLP to find least common multiple.
// Forbidden when G[j] < G[i]; advance jumps to next multiple of A[j] >= G[i].

fn lcm1(a: &[i32]) -> Vec<i32> {
    let n = a.len();
    let mut g: Vec<i32> = a.to_vec();
    let mut changed = true;
    while changed {
        changed = false;
        for j in 0..n {
            let mut picked: i32 = -1;
            for i in 0..n {
                if g[j] < g[i] { picked = i as i32; break; }
            }
            if picked == -1 { continue; }
            let i = picked as usize;
            g[j] = g[j] + ((g[i] - g[j] + a[j] - 1) / a[j]) * a[j];
            changed = true;
        }
    }
    g
}

fn main() {
    let g = lcm1(&[4, 6, 8]);
    println!("lcm={}", g[0]);
}
