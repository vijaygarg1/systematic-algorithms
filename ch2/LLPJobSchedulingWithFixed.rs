// LLP-LLPJobSchedulingWithFixed: O(n + m) topological-sort variant.

fn job_scheduling_with_fixed(t: &[i32], pre: &[Vec<usize>], succ: &[Vec<usize>]) -> Vec<i32> {
    let n = t.len();
    let mut g: Vec<i32> = t.to_vec();
    let mut count: Vec<i32> = pre.iter().map(|p| p.len() as i32).collect();
    let mut queue: Vec<usize> = (0..n).filter(|&k| count[k] == 0).collect();
    let mut head = 0;
    while head < queue.len() {
        let j = queue[head];
        head += 1;
        if !pre[j].is_empty() {
            let r = pre[j].iter().map(|&i| g[i] + t[j]).max().unwrap_or(g[j]);
            if r > g[j] { g[j] = r; }
        }
        for &k in &succ[j] {
            count[k] -= 1;
            if count[k] == 0 { queue.push(k); }
        }
    }
    g
}

fn main() {
    let t    = vec![3, 2, 4, 1, 2, 3];
    let pre:  Vec<Vec<usize>> = vec![vec![], vec![0], vec![0], vec![1, 2], vec![2], vec![3, 4]];
    let succ: Vec<Vec<usize>> = vec![vec![1, 2], vec![3], vec![3, 4], vec![5], vec![5], vec![]];
    println!("G = {:?}", job_scheduling_with_fixed(&t, &pre, &succ));
}
