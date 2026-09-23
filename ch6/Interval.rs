// Interval scheduling: maximum compatible subset, greedy by earliest finish.

fn schedule(s: &[i32], f: &[i32]) -> Vec<i32> {
    let n = s.len();
    let mut g = vec![0i32; n];
    if n == 0 { return g; }
    g[0] = 1;
    let mut last = 0;
    for i in 1..n {
        if s[i] >= f[last] { g[i] = 1; last = i; }
    }
    g
}

fn main() {
    let s = [1, 3, 0, 5, 8, 5];
    let f = [4, 5, 6, 7, 9, 9];
    println!("selected: {:?}", schedule(&s, &f));
}
