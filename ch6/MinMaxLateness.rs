// Minimise max lateness on a single processor: earliest-deadline first.

fn schedule(t: &[i32], d: &[i32]) -> (Vec<i32>, i32) {
    let mut g = vec![0i32; t.len()];
    let mut last = 0i32;
    let mut max_late = 0i32;
    for i in 0..t.len() {
        g[i] = last;
        last += t[i];
        let late = last - d[i];
        if late > max_late { max_late = late; }
    }
    (g, max_late)
}

fn main() {
    let t = [3, 2, 1, 4, 3, 2];
    let d = [6, 8, 9, 9, 14, 15];
    let (g, mx) = schedule(&t, &d);
    println!("start times: {:?}\nmax lateness: {}", g, mx);
}
