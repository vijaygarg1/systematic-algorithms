// Classical sequential weighted-interval-scheduling DP. Intervals are
// presorted once by finish time, with index 0 a 0-weight sentinel, so
// p[j] -- the last interval that finishes no later than j starts -- is
// derived here rather than supplied.

fn schedule(s: &[i64], f: &[i64], w: &[i64]) -> Vec<i64> {
    let n = s.len();
    let mut opt = vec![0i64; n];
    let mut g = vec![0i64; n];
    let mut p = vec![0usize; n];
    for j in 1..n {
        for i in 1..j {
            if f[i] <= s[j] { p[j] = i; }      // f is sorted, so the last fit wins
        }
    }
    // Phase 1: compute optimal values.
    for cur in 1..n {
        opt[cur] = if w[cur] + opt[p[cur]] >= opt[cur - 1] {
            w[cur] + opt[p[cur]]
        } else {
            opt[cur - 1]
        };
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
    // Book's 5-interval example.  Index 0 is a 0-weight sentinel.
    let s = [0, 1, 2, 4, 6, 5];
    let f = [0, 3, 5, 6, 8, 9];
    let w = [0, 4, 6, 5, 3, 7];
    println!("selected: {:?}", schedule(&s, &f, &w));
}
