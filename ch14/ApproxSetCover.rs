// Greedy H_n-approximation for Set Cover: repeatedly pick the set
// covering the most uncovered elements.

fn approx_set_cover(s: &[Vec<i32>], n: usize) -> Vec<bool> {
    let m = s.len();
    let mut c = vec![false; m];
    let mut covered = vec![false; n];
    loop {
        let mut best_idx: i32 = -1;
        let mut best_cover = 0;
        for sidx in 0..m {
            if c[sidx] { continue; }
            let mut count = 0;
            for e in 0..n {
                if s[sidx][e] == 1 && !covered[e] { count += 1; }
            }
            if count > best_cover { best_cover = count; best_idx = sidx as i32; }
        }
        if best_idx == -1 { break; }
        let bi = best_idx as usize;
        c[bi] = true;
        for e in 0..n { if s[bi][e] == 1 { covered[e] = true; } }
    }
    c
}

fn main() {
    let s = vec![
        vec![1, 1, 1, 0, 0, 0],
        vec![1, 0, 0, 1, 1, 0],
        vec![0, 1, 0, 0, 1, 1],
        vec![0, 0, 1, 0, 0, 1],
    ];
    let c = approx_set_cover(&s, 6);
    print!("picked:");
    for (i, b) in c.iter().enumerate() { if *b { print!(" {}", i); } }
    println!();
}
