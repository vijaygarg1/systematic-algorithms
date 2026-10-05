// Classical sequential weighted-interval-scheduling DP given p[].

fn schedule(s: &[i32], _f: &[i32], w: &[i32], p: &[usize]) -> Vec<i32> {
    let n = s.len();
    let mut opt = vec![0i32; n];
    let mut g = vec![0i32; n];
    // Phase 1: compute optimal values.
    for cur in 1..n {
        if w[cur] + opt[p[cur]] >= opt[cur - 1] {
            opt[cur] = w[cur] + opt[p[cur]];
        } else {
            opt[cur] = opt[cur - 1];
        }
    }
    // Phase 2: backtrack to find selected intervals.
    let mut cur = n - 1;
    while cur > 0 {
        if w[cur] + opt[p[cur]] >= opt[cur - 1] {
            g[cur] = 1;
            cur = p[cur];
        } else {
            cur -= 1;
        }
    }
    g
}

fn main() {
    let s = [0, 1, 2, 4, 6, 5];
    let f = [0, 3, 5, 6, 8, 9];
    let w = [0, 4, 6, 5, 3, 7];
    let p = [0usize, 0, 0, 1, 3, 2];
    let g = schedule(&s, &f, &w, &p);
    println!("selected: {:?}", g);
}
