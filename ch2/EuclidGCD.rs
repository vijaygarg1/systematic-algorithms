// LLP-EuclidGCD: 2-element LLP that implements Euclid by repeated
// subtraction.  Index j is forbidden when some i has G[j] > G[i];
// advance subtracts G[i] from G[j].

fn forbidden(j: usize, g: &[i32]) -> Option<usize> {
    (0..g.len()).find(|&i| i != j && g[j] > g[i])
}

fn euclid_gcd(a: Vec<i32>) -> Vec<i32> {
    let mut g = a;
    let mut changed = true;
    while changed {
        changed = false;
        for j in 0..g.len() {
            if let Some(i) = forbidden(j, &g) {
                g[j] -= g[i];
                changed = true;
            }
        }
    }
    g
}

fn main() {
    for a in &[vec![48, 18], vec![100, 75], vec![60, 36, 24]] {
        println!("euclidGCD({:?}) = {:?}", a, euclid_gcd(a.clone()));
    }
}
