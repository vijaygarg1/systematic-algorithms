// LLP-BellmanFord shortest paths.  Edge weights may be negative.

const INF: i32 = i32::MAX / 2;

fn forbidden(j: usize, d: &[i32], pre: &[Vec<usize>], w: &[Vec<i32>]) -> bool {
    for &i in &pre[j] {
        if d[i] != INF && d[i] + w[i][j] < d[j] { return true; }
    }
    false
}

fn advance(d: &mut Vec<i32>, pre: &[Vec<usize>], w: &[Vec<i32>], n: usize) {
    let mut nd = d.clone();
    for k in 0..n {
        for &i in &pre[k] {
            if d[i] != INF && d[i] + w[i][k] < nd[k] { nd[k] = d[i] + w[i][k]; }
        }
    }
    *d = nd;
}

fn llp_bellman_ford(pre: &[Vec<usize>], w: &[Vec<i32>], n: usize) -> Vec<i32> {
    let mut d = vec![INF; n];
    d[0] = 0;
    let mut changed = true;
    while changed {
        changed = false;
        for j in 0..n {
            if forbidden(j, &d, pre, w) {
                advance(&mut d, pre, w, n);
                changed = true;
                break;
            }
        }
    }
    d
}

fn main() {
    let n = 4;
    let pre: Vec<Vec<usize>> = vec![vec![], vec![0], vec![0, 1], vec![2]];
    let mut w = vec![vec![INF; n]; n];
    w[0][1] = 1; w[0][2] = 4; w[1][2] = -3; w[2][3] = 1;
    let d = llp_bellman_ford(&pre, &w, n);
    println!("d = {:?}", d);
}
