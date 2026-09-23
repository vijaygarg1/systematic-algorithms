// Gale's Top Trading Cycle (TTC) algorithm for the housing market.

fn ttc(pref: &[Vec<usize>]) -> Vec<i32> {
    let n = pref.len();
    let mut house = vec![-1i32; n];
    let mut g = vec![0usize; n];
    let mut fixed = vec![false; n];
    let mut on_path = vec![false; n];
    let mut num_fixed = 0;
    while num_fixed < n {
        for i in 0..n {
            if !fixed[i] {
                while fixed[pref[i][g[i]]] { g[i] += 1; }
            }
        }
        for v in on_path.iter_mut() { *v = false; }
        let mut start = 0;
        while fixed[start] { start += 1; }
        let mut cur = start;
        on_path[cur] = true;
        let mut nxt = pref[cur][g[cur]];
        while !on_path[nxt] {
            cur = nxt;
            on_path[cur] = true;
            nxt = pref[cur][g[cur]];
        }
        let cycle_start = nxt;
        cur = cycle_start;
        loop {
            let wish = pref[cur][g[cur]];
            house[cur] = wish as i32;
            fixed[cur] = true;
            num_fixed += 1;
            if wish == cycle_start { break; }
            cur = wish;
        }
    }
    house
}

fn main() {
    let pref: Vec<Vec<usize>> = vec![
        vec![1, 0, 2, 3],
        vec![0, 1, 2, 3],
        vec![0, 1, 2, 3],
        vec![3, 1, 0, 2],
    ];
    let h = ttc(&pref);
    println!("house: {:?}", h);
}
