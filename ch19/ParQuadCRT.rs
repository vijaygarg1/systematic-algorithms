// Par-QuadCRT: simultaneous quadratic congruences x^2 == a[j] (mod m[j]).
// Generalises Par-CRT to a non-linear local predicate.  G[j] is
// initialised to the smallest non-negative root of x^2 == a[j] (mod
// m[j]); the forbidden / advance pair drives every G[j] up to a
// common value that satisfies all r congruences simultaneously.

fn par_quad_crt(m: &[i64], _a: &[i64], roots: &[i64]) -> Vec<i64> {
    let n = m.len();
    let mut g: Vec<i64> = roots.to_vec();
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
            g[j] = g[j] + ((g[i] - g[j] + m[j] - 1) / m[j]) * m[j];
            changed = true;
        }
    }
    g
}

fn main() {
    let m = [3i64, 5, 7];
    let a = [1i64, 1, 1];
    let roots = [1i64, 1, 1];
    let g = par_quad_crt(&m, &a, &roots);
    println!("x = {}", g[0]);
}
