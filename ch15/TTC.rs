// Gale's Top Trading Cycle (TTC) algorithm for the housing market: each
// stage, every unfixed agent advances its wish pointer past houses
// already owned by fixed agents, building the top-choice graph; ALL
// node-disjoint cycles in that graph are found at once (every unfixed
// node has out-degree 1), and every agent on ANY cycle this stage
// trades and is fixed -- not a single pointer-chasing walk that
// resolves one cycle at a time and rebuilds the whole graph for each one.

fn ttc(pref: &[Vec<usize>]) -> Vec<i32> {
    let n = pref.len();
    let mut house = vec![-1i32; n];
    let mut g = vec![0usize; n];
    let mut fixed = vec![false; n];
    let mut num_fixed = 0;
    while num_fixed < n {
        for i in 0..n {
            if !fixed[i] {
                while fixed[pref[i][g[i]]] { g[i] += 1; }
            }
        }
        // state: 0 = unvisited, 1 = on current walk, 2 = resolved.
        let mut state = vec![0u8; n];
        let mut in_cycle = vec![false; n];
        for i in 0..n {
            if fixed[i] || state[i] != 0 { continue; }
            let mut path = Vec::new();
            let mut cur = i;
            while state[cur] == 0 {
                state[cur] = 1;
                path.push(cur);
                cur = pref[cur][g[cur]];
            }
            if state[cur] == 1 {
                let mut on_cyc = false;
                for &node in &path {
                    if node == cur { on_cyc = true; }
                    if on_cyc { in_cycle[node] = true; }
                }
            }
            for &node in &path { state[node] = 2; }
        }
        for i in 0..n {
            if in_cycle[i] {
                house[i] = pref[i][g[i]] as i32;
                fixed[i] = true;
                num_fixed += 1;
            }
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
