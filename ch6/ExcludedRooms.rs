// ExcludedRooms: composition program enforcing R[j] = excluded rooms.
// G[j] is the room assigned to course j; advance picks the smallest
// non-excluded room not used by any overlapping earlier course.

use std::collections::BTreeSet;

fn excluded_rooms(r_set: &[BTreeSet<i32>],
                  pre: &[BTreeSet<usize>],
                  g: &mut [i32]) {
    let n = g.len();
    let mut changed = true;
    while changed {
        changed = false;
        for j in 0..n {
            if r_set[j].contains(&g[j]) {
                let max_r = n as i32;
                for r in 1..=max_r {
                    if r_set[j].contains(&r) { continue; }
                    let clash = pre[j].iter().any(|&k| g[k] == r);
                    if clash { continue; }
                    if g[j] != r { g[j] = r; changed = true; }
                    break;
                }
            }
        }
    }
}

fn main() {
    let r_set: Vec<BTreeSet<i32>> = vec![[1].into(), [2].into(), BTreeSet::new()];
    let pre: Vec<BTreeSet<usize>> =
        vec![BTreeSet::new(), [0usize].into(), [0usize, 1].into()];
    let mut g = vec![1, 2, 1];
    excluded_rooms(&r_set, &pre, &mut g);
    println!("G: {:?}", g);
}
