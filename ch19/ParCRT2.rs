// Par-CRT2: descending parallel Chinese Remainder Theorem.
// Searches for the largest solution below M.

fn par_crt2(m: &[i64], b: &[i64], cap: i64) -> Vec<i64> {
    let n = m.len();
    let mut g = vec![0i64; n];
    for j in 0..n {
        let r = (cap - 1) % m[j];
        g[j] = if r >= b[j] { (cap - 1) - r + b[j] } else { (cap - 1) - r + b[j] - m[j] };
    }
    let mut changed = true;
    while changed {
        changed = false;
        let mut min_val = g[0];
        for i in 1..n { if g[i] < min_val { min_val = g[i]; } }
        for j in 0..n {
            if g[j] > min_val {
                let diff = g[j] - min_val;
                let steps = (diff + m[j] - 1) / m[j];
                g[j] -= steps * m[j];
                changed = true;
            }
        }
    }
    g
}

fn main() {
    let m = [3i64, 5, 7];
    let b = [2i64, 3, 2];
    let g = par_crt2(&m, &b, 200);
    println!("max < M: {}", g[0]);
}
