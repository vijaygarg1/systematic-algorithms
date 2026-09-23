// Horn SAT forward chaining (Dowling-Gallier): unit propagation with counters.

fn horn_sat_fc(body: &[Vec<usize>], head: &[i32], adj: &[Vec<usize>]) -> Vec<bool> {
    let n = adj.len();
    let m = body.len();
    let mut a = vec![false; n];
    let mut rem: Vec<i32> = body.iter().map(|b| b.len() as i32).collect();

    let mut queue: Vec<usize> = Vec::new();
    for c in 0..m {
        if rem[c] == 0 && head[c] >= 0 && !a[head[c] as usize] {
            queue.push(head[c] as usize);
        }
    }

    let mut sat = true;
    let mut front = 0;
    while front < queue.len() && sat {
        let x = queue[front];
        front += 1;
        if a[x] { continue; }
        a[x] = true;
        for &ci in &adj[x] {
            rem[ci] -= 1;
            if rem[ci] == 0 {
                if head[ci] < 0 { sat = false; break; }
                if !a[head[ci] as usize] { queue.push(head[ci] as usize); }
            }
        }
    }
    a
}

fn main() {
    let body: Vec<Vec<usize>> = vec![vec![0], vec![0, 1], vec![]];
    let head = vec![1i32, 2, 0];
    let mut adj: Vec<Vec<usize>> = vec![vec![]; 3];
    for c in 0..3 {
        for &x in &body[c] { adj[x].push(c); }
    }
    let a = horn_sat_fc(&body, &head, &adj);
    print!("A:");
    for b in &a { print!(" {}", if *b { 1 } else { 0 }); }
    println!();
}
