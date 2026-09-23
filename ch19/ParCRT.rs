// Par-CRT: ascending parallel Chinese Remainder Theorem.
// Forbidden when G[j] < G[i] for some i;
// advance jumps to next multiple-of-m[j] congruence >= G[i].

fn par_crt(m: &[i64], b: &[i64]) -> Vec<i64> {
    let n = m.len();
    let mut g: Vec<i64> = b.to_vec();
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
    let b = [2i64, 3, 2];
    let g = par_crt(&m, &b);
    println!("x = {}", g[0]);
}
