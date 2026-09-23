// Classical Prim MST: O(n^2) linear-scan version using a weight matrix.

const INF: i32 = i32::MAX / 2;

fn mst(w: &[Vec<i32>]) -> Vec<i32> {
    let n = w.len();
    let mut d = vec![INF; n];
    let mut parent = vec![-1i32; n];
    let mut fixed = vec![false; n];
    d[0] = 0;
    for _ in 0..n {
        let mut v: i32 = -1;
        let mut best = INF;
        for k in 0..n {
            if !fixed[k] && d[k] < best { v = k as i32; best = d[k]; }
        }
        if v == -1 { break; }
        let v = v as usize;
        fixed[v] = true;
        for k in 0..n {
            if !fixed[k] && w[v][k] != INF && w[v][k] < d[k] {
                d[k] = w[v][k];
                parent[k] = v as i32;
            }
        }
    }
    parent
}

fn main() {
    let w: Vec<Vec<i32>> = vec![
        vec![0, 1, 3, INF, INF],
        vec![1, 0, 2, 6, INF],
        vec![3, 2, 0, 4, 5],
        vec![INF, 6, 4, 0, 7],
        vec![INF, INF, 5, 7, 0],
    ];
    let p = mst(&w);
    println!("parent: {:?}", p);
}
