// Mandatory: composition program forcing the intervals in subset S
// into the schedule.  Returns None on infeasibility.

fn overlap(j: usize, k: usize, s: &[i32], f: &[i32]) -> bool {
    s[j] < f[k] && s[k] < f[j]
}

fn mandatory_intervals(s: &[i32], f: &[i32], sel: &[bool]) -> Option<Vec<bool>> {
    let n = s.len();
    let mut g = vec![false; n];
    let mut changed = true;
    while changed {
        changed = false;
        for j in 0..n {
            if sel[j] && !g[j] {
                if (0..n).any(|k| g[k] && overlap(j, k, s, f)) {
                    return None;
                }
                g[j] = true;
                changed = true;
            }
        }
    }
    Some(g)
}

fn main() {
    let s = [0, 3, 5];
    let f = [2, 7, 9];
    let sel = [true, false, true];
    match mandatory_intervals(&s, &f, &sel) {
        Some(g) => println!("G: {:?}", g),
        None => println!("infeasible"),
    }
}
