// LLP-IntervalScheduling: jobs sorted by finish time, held in a doubly
// linked list (prev/next). Job 0 is always selected initially. A job j
// (not yet selected or deleted) is forbidden when either (a) its
// current list-predecessor has already finished by s[j] -- advance:
// select it -- or (b) its predecessor is selected but still overlaps j
// -- advance: delete j from the list in O(1).

fn llp_interval_scheduling(s: &[i32], f: &[i32]) -> Vec<bool> {
    let n = s.len();
    let mut g = vec![false; n];
    let mut deleted = vec![false; n];
    let mut prev: Vec<isize> = (-1..(n as isize - 1)).collect();
    let mut next: Vec<usize> = (1..=n).collect();
    g[0] = true;
    let mut changed = true;
    while changed {
        changed = false;
        for j in 1..n {
            if g[j] || deleted[j] { continue; }
            let p = prev[j] as usize;
            if f[p] <= s[j] {
                g[j] = true;
                changed = true;
            } else if g[p] && s[j] < f[p] {
                deleted[j] = true;
                let nx = next[j];
                next[p] = nx;
                if nx < n { prev[nx] = p as isize; }
                changed = true;
            }
        }
    }
    g
}

fn main() {
    let s = [1, 3, 0, 5, 8, 5];
    let f = [2, 4, 6, 7, 9, 9];
    let g = llp_interval_scheduling(&s, &f);
    let selected: Vec<usize> = (0..g.len()).filter(|&i| g[i]).collect();
    println!("selected: {:?}", selected);
}
