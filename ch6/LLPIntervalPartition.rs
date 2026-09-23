// LLP-IntervalPartition: assign each course to least free room not used by overlapping earlier courses.

fn llp_interval_partition(pre: &[Vec<usize>]) -> Vec<i32> {
    let n = pre.len();
    let mut g = vec![1i32; n];
    let mut fixed = vec![false; n];

    let least_free_room = |g: &Vec<i32>, j: usize| -> i32 {
        let mut r = 1;
        loop {
            if !pre[j].iter().any(|&i| g[i] == r) { return r; }
            r += 1;
        }
    };

    let mut changed = true;
    while changed {
        changed = false;
        for j in 0..n {
            if fixed[j] { continue; }
            if !pre[j].iter().all(|&i| fixed[i]) { continue; }
            g[j] = least_free_room(&g, j);
            fixed[j] = true;
            changed = true;
        }
    }
    g
}

fn main() {
    let pre: Vec<Vec<usize>> = vec![vec![], vec![0], vec![0, 1]];
    println!("rooms: {:?}", llp_interval_partition(&pre));
}
