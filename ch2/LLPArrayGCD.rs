// LLP-GCD: replace G[j] by G[j] mod G[i] when G[j] > G[i].

fn forbidden(j: usize, g: &[i32]) -> Option<usize> {
    (0..g.len()).find(|&i| i != j && g[j] > g[i])
}

fn llp_gcd(a: Vec<i32>) -> Vec<i32> {
    let mut g = a;
    let mut changed = true;
    while changed {
        changed = false;
        for j in 0..g.len() {
            if let Some(i) = forbidden(j, &g) {
                g[j] = if g[j] % g[i] == 0 { g[i] } else { g[j] % g[i] };
                changed = true;
            }
        }
    }
    g
}

fn main() {
    for a in &[vec![48, 18], vec![100, 75, 50], vec![60, 36, 24]] {
        println!("llpGCD({:?}) = {:?}", a, llp_gcd(a.clone()));
    }
}
