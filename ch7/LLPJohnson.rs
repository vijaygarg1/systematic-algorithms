// LLP-Johnson: compute non-negative price/potential vector p such that
// the reweighted edges w[i][j] + p[j] - p[i] are all >= 0.

const INF: i32 = i32::MAX / 2;

fn forbidden(j: usize, p: &[i32], pre: &[Vec<usize>], w: &[Vec<i32>]) -> bool {
    for &i in &pre[j] {
        if p[j] < p[i] - w[i][j] { return true; }
    }
    false
}

fn advance(p: &mut Vec<i32>, pre: &[Vec<usize>], w: &[Vec<i32>], n: usize) {
    let mut np = p.clone();
    for k in 0..n {
        for &i in &pre[k] {
            let cand = p[i] - w[i][k];
            if cand > np[k] { np[k] = cand; }
        }
    }
    *p = np;
}

fn llp_johnson(pre: &[Vec<usize>], w: &[Vec<i32>], n: usize) -> Vec<i32> {
    let mut p = vec![0i32; n];
    let mut changed = true;
    while changed {
        changed = false;
        for j in 0..n {
            if forbidden(j, &p, pre, w) {
                advance(&mut p, pre, w, n);
                changed = true;
                break;
            }
        }
    }
    p
}

fn main() {
    let n = 4;
    let pre: Vec<Vec<usize>> = vec![vec![], vec![0], vec![0, 1], vec![2]];
    let mut w = vec![vec![INF; n]; n];
    w[0][1] = 1; w[0][2] = 4; w[1][2] = -3; w[2][3] = 1;
    let p = llp_johnson(&pre, &w, n);
    println!("potentials: {:?}", p);
}
