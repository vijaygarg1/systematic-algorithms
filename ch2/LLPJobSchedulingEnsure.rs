// LLP-JobScheduling-Ensure: identical to the Forbidden form, written
// using the `ensure` shorthand.

fn job_scheduling_ensure(t: &[i32], pre: &[Vec<usize>]) -> Vec<i32> {
    let mut g: Vec<i32> = t.to_vec();
    let mut changed = true;
    while changed {
        changed = false;
        for j in 0..t.len() {
            if let Some(r) = pre[j].iter().map(|&i| g[i] + t[j]).max() {
                if g[j] < r { g[j] = r; changed = true; }
            }
        }
    }
    g
}

fn main() {
    let t   = vec![3, 2, 4, 1, 2, 3];
    let pre: Vec<Vec<usize>> = vec![vec![], vec![0], vec![0], vec![1, 2], vec![2], vec![3, 4]];
    println!("G = {:?}", job_scheduling_ensure(&t, &pre));
}
