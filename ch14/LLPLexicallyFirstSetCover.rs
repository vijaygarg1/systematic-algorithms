// LLP parallel H_n-approximation for Set Cover: pick every set that
// maximises coverage among neighbours and is lex-minimal among ties.

fn is_covered(e: usize, s: &[Vec<i32>], g: &[bool]) -> bool {
    let m = g.len();
    for sidx in 0..m {
        if g[sidx] && s[sidx][e] == 1 { return true; }
    }
    false
}

fn coverage(j: usize, s: &[Vec<i32>], g: &[bool]) -> i32 {
    let u = s[j].len();
    let mut count = 0;
    for e in 0..u {
        if s[j][e] == 1 && !is_covered(e, s, g) { count += 1; }
    }
    count
}

fn share_uncovered(j: usize, k: usize, s: &[Vec<i32>], g: &[bool]) -> bool {
    let u = s[j].len();
    for e in 0..u {
        if s[j][e] == 1 && s[k][e] == 1 && !is_covered(e, s, g) { return true; }
    }
    false
}

fn is_lex_max_cov(j: usize, s: &[Vec<i32>], g: &[bool]) -> bool {
    if g[j] { return false; }
    let m = g.len();
    let cov_j = coverage(j, s, g);
    if cov_j == 0 { return false; }
    for k in 0..m {
        if k == j || g[k] { continue; }
        if !share_uncovered(j, k, s, g) { continue; }
        let cov_k = coverage(k, s, g);
        if cov_k > cov_j { return false; }
        if cov_k == cov_j && k < j { return false; }
    }
    true
}

fn llp_lexically_first_set_cover(s: &[Vec<i32>]) -> Vec<bool> {
    let m = s.len();
    let mut g = vec![false; m];
    let mut changed = true;
    while changed {
        changed = false;
        for j in 0..m {
            if is_lex_max_cov(j, s, &g) { g[j] = true; changed = true; }
        }
    }
    g
}

fn main() {
    let s = vec![
        vec![1, 1, 1, 0, 0, 0],
        vec![1, 0, 0, 1, 1, 0],
        vec![0, 1, 0, 0, 1, 1],
        vec![0, 0, 1, 0, 0, 1],
    ];
    let g = llp_lexically_first_set_cover(&s);
    print!("picked:");
    for (i, b) in g.iter().enumerate() { if *b { print!(" {}", i); } }
    println!();
}
