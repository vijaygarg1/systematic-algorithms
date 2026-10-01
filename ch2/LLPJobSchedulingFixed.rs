// LLP-JobScheduling-Fixed: advance each job once all predecessors are fixed.

fn job_scheduling_fixed(t: &[i32], pre: &[Vec<usize>]) -> Vec<i32> {
    let n = t.len();
    let mut g: Vec<i32> = t.to_vec();
    let mut fixed: Vec<bool> = pre.iter().map(|p| p.is_empty()).collect();
    let mut changed = true;
    while changed {
        changed = false;
        for j in 0..n {
            if fixed[j] { continue; }
            if !pre[j].iter().all(|&i| fixed[i]) { continue; }
            g[j] = pre[j].iter().map(|&i| g[i] + t[j]).max().unwrap();
            fixed[j] = true;
            changed = true;
        }
    }
    g
}

fn main() {
    let t = vec![3, 2, 5, 1];
    let pre: Vec<Vec<usize>> = vec![vec![], vec![0], vec![0], vec![1, 2]];
    println!("G = {:?}", job_scheduling_fixed(&t, &pre));
}
