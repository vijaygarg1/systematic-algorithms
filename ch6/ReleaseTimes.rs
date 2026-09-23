// ReleaseTimes: composition program enforcing G[j] >= r[j].

fn release_times(r: &[i32], g: &mut [i32]) {
    let n = g.len();
    let mut changed = true;
    while changed {
        changed = false;
        for j in 0..n {
            if g[j] < r[j] { g[j] = r[j]; changed = true; }
        }
    }
}

fn main() {
    let r = [0, 3, 2, 7];
    let mut g = vec![0; r.len()];
    release_times(&r, &mut g);
    println!("G: {:?}", g);
}
