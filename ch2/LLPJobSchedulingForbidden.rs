// LLP-JobScheduling-Forbidden: minimum completion time with prerequisites.
//   forbidden(j) : G[j] < max { G[i] + t[j] | i in pre[j] }
//   advance(j)   : G[j] := max { G[i] + t[j] | i in pre[j] }

fn rhs(j: usize, g: &[i32], t: &[i32], pre: &[Vec<usize>]) -> Option<i32> {
    pre[j].iter().map(|&i| g[i] + t[j]).max()
}

fn job_scheduling(t: &[i32], pre: &[Vec<usize>]) -> Vec<i32> {
    let mut g: Vec<i32> = t.to_vec();
    let mut changed = true;
    while changed {
        changed = false;
        for j in 0..t.len() {
            if let Some(r) = rhs(j, &g, t, pre) {
                if g[j] < r { g[j] = r; changed = true; }
            }
        }
    }
    g
}

fn main() {
    let t   = vec![3, 2, 4, 1, 2, 3];
    let pre: Vec<Vec<usize>> = vec![vec![], vec![0], vec![0], vec![1, 2], vec![2], vec![3, 4]];
    println!("G = {:?}", job_scheduling(&t, &pre));
}
