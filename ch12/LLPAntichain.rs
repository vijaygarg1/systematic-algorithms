// LLP-Antichain: maximum antichain by advancing chain indices to
// dominate-free positions.

fn llp_antichain(chains: &[Vec<usize>], len: &[usize], leq: &[Vec<bool>]) -> Vec<usize> {
    let n = len.len();
    let mut g = vec![0usize; n];
    let mut changed = true;
    while changed {
        changed = false;
        for j in 0..n {
            if g[j] >= len[j] { continue; }
            let mut dominated = false;
            for k in 0..n {
                if dominated { break; }
                if k != j && leq[chains[j][g[j]]][chains[k][g[k]]] {
                    dominated = true;
                }
            }
            if dominated { g[j] += 1; changed = true; }
        }
    }
    g
}

fn main() {
    let chains: Vec<Vec<usize>> = vec![vec![0, 1], vec![2, 3]];
    let len = vec![2usize, 2];
    let mut leq = vec![vec![false; 4]; 4];
    for i in 0..4 { leq[i][i] = true; }
    leq[0][1] = true;
    leq[2][3] = true;
    let g = llp_antichain(&chains, &len, &leq);
    println!("G: {:?}", g);
}
