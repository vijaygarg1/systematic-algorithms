// LLP-MinMaxLate: schedule jobs by raising start time to prefix sum of earlier processing times.

fn llp_min_max_late(t: &[i32]) -> Vec<i32> {
    let n = t.len();
    let mut g = vec![0i32; n];
    let mut changed = true;
    while changed {
        changed = false;
        let mut prefix = 0i32;
        for j in 0..n {
            if g[j] < prefix { g[j] = prefix; changed = true; }
            prefix += t[j];
        }
    }
    g
}

fn main() {
    let t = [1, 4, 3];
    println!("start times: {:?}", llp_min_max_late(&t));
}
