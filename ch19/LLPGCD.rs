// LLP-GCD: greatest common divisor of two numbers via Euclidean
// reduction, matching bxx-numberTheory.tex Algorithm LLP-GCD. G is the
// two-element vector (a, b); forbidden picks an entry strictly larger
// than another, and advance replaces it by its remainder mod the smaller
// (or by the smaller itself when it divides exactly, so entries stay at
// least 1). At termination G[0] = G[1] = gcd(a, b).
//
// Each advance replaces G[j] by G[j] mod G[i] < G[i] <= G[j], which at
// least halves it, so this performs O(log(min(a,b))) advances.
//
// This file previously held the n-element array reduction (LLP-ArrayGCD,
// bxx-llp-alg.tex:966) under the LLP-GCD name -- a different algorithm
// from a different chapter.

fn llp_gcd(a: i32, b: i32) -> i32 {
    let mut g = [a, b];
    let mut changed = true;
    while changed {
        changed = false;
        for j in 0..2 {
            // `pick i`: any witness will do, so take the first.
            let mut picked: i32 = -1;
            for i in 0..2 {
                if g[j] > g[i] { picked = i as i32; break; }
            }
            if picked == -1 { continue; }
            let i = picked as usize;
            g[j] = if g[j] % g[i] == 0 { g[i] } else { g[j] % g[i] };
            changed = true;
        }
    }
    g[0]
}

fn main() {
    println!("gcd(1071, 462) = {}", llp_gcd(1071, 462));
}
