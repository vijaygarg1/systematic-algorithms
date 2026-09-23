// LLP-Prim with helper aux functions.

const INF: f64 = 1e18;

fn min_cross_cut(j: usize, fixed: &[bool], w: &[Vec<i32>], n: usize) -> f64 {
    let mut best = INF;
    for i in 0..n {
        if fixed[i] && (w[i][j] as f64) < best { best = w[i][j] as f64; }
    }
    best
}

fn arg_min_cross_cut(j: usize, fixed: &[bool], w: &[Vec<i32>], n: usize) -> i32 {
    let mut besti: i32 = -1;
    let mut best = INF;
    for i in 0..n {
        if fixed[i] && (w[i][j] as f64) < best { best = w[i][j] as f64; besti = i as i32; }
    }
    besti
}

fn global_min_cross_cut(fixed: &[bool], w: &[Vec<i32>], n: usize) -> f64 {
    let mut best = INF;
    for j in 0..n {
        if !fixed[j] {
            let m = min_cross_cut(j, fixed, w, n);
            if m < best { best = m; }
        }
    }
    best
}

fn propagate_fixed(parent: &[usize], fixed: &mut [bool], n: usize) {
    let mut changed = true;
    while changed {
        changed = false;
        for j in 0..n {
            if !fixed[j] && fixed[parent[j]] { fixed[j] = true; changed = true; }
        }
    }
}

fn llp_prim(parent: &mut [usize], fixed: &mut [bool], w: &[Vec<i32>], c: &mut [f64]) {
    let n = parent.len();
    let mut changed = true;
    while changed {
        changed = false;
        for j in 0..n {
            if fixed[j] { continue; }
            let arg = arg_min_cross_cut(j, fixed, w, n);
            if arg < 0 { continue; }
            let mcc = min_cross_cut(j, fixed, w, n);
            if mcc <= global_min_cross_cut(fixed, w, n) && c[j] < mcc {
                parent[j] = arg as usize;
                c[j] = w[arg as usize][j] as f64;
                propagate_fixed(parent, fixed, n);
                changed = true;
                break;
            }
        }
    }
}

fn main() {
    let inf_i: i32 = i32::MAX / 2;
    let w: Vec<Vec<i32>> = vec![
        vec![0, 1, 3, inf_i, inf_i],
        vec![1, 0, 2, 6, inf_i],
        vec![3, 2, 0, 4, 5],
        vec![inf_i, 6, 4, 0, 7],
        vec![inf_i, inf_i, 5, 7, 0],
    ];
    let n = w.len();
    let root = 0usize;
    let mut fixed = vec![false; n]; fixed[root] = true;
    let mut parent: Vec<usize> = vec![root; n];
    let mut c = vec![0.0f64; n];
    for j in 0..n {
        if j == root { continue; }
        let mut best = INF;
        let mut besti = root;
        for i in 0..n {
            if i != j && (w[i][j] as f64) < best { best = w[i][j] as f64; besti = i; }
        }
        parent[j] = besti;
        c[j] = w[besti][j] as f64;
    }
    llp_prim(&mut parent, &mut fixed, &w, &mut c);
    println!("parent: {:?}", parent);
    println!("C: {:?}", c);
}
