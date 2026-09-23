// Classical Johnson all-pairs shortest paths: BellmanFord computes a
// non-negative price vector that reweights edges, then Dijkstra runs from
// every source on the reweighted graph. Returns None on negative cycle.

const INF: i32 = i32::MAX;

fn dijkstra(w: &[Vec<i32>], s: usize, n: usize) -> Vec<i32> {
    let mut dist = vec![INF; n];
    let mut fixed = vec![false; n];
    dist[s] = 0;
    let mut count = 0;
    while count < n {
        let mut j: i32 = -1;
        let mut best = INF;
        for k in 0..n {
            if !fixed[k] && dist[k] < best {
                j = k as i32;
                best = dist[k];
            }
        }
        if j == -1 { return dist; }
        let j = j as usize;
        fixed[j] = true;
        count += 1;
        for k in 0..n {
            if !fixed[k] && w[j][k] < INF && dist[j] + w[j][k] < dist[k] {
                dist[k] = dist[j] + w[j][k];
            }
        }
    }
    dist
}

// allPairs: returns D such that D[u][v] is the shortest-path cost from u to v;
// returns None if a negative-weight cycle exists.
fn all_pairs(w: &[Vec<i32>]) -> Option<Vec<Vec<i32>>> {
    let n = w.len();
    // Step 1: BellmanFord from auxiliary source. Init dist[i] = 0; n-1 passes.
    let mut dist = vec![0i32; n];
    for _ in 0..n.saturating_sub(1) {
        let mut changed = false;
        for i in 0..n {
            for j in 0..n {
                if w[i][j] < INF && dist[i] + w[i][j] < dist[j] {
                    dist[j] = dist[i] + w[i][j];
                    changed = true;
                }
            }
        }
        if !changed { break; }
    }
    // One more pass: any further relaxation indicates a negative cycle.
    for i in 0..n {
        for j in 0..n {
            if w[i][j] < INF && dist[i] + w[i][j] < dist[j] {
                return None;
            }
        }
    }
    // Step 2: price[v] = -dist[v].
    let price: Vec<i32> = dist.iter().map(|d| -d).collect();
    // Step 3: reweight.
    let mut w_prime = vec![vec![INF; n]; n];
    for i in 0..n {
        for j in 0..n {
            if w[i][j] < INF {
                w_prime[i][j] = w[i][j] + price[j] - price[i];
            }
        }
    }
    // Step 4: Dijkstra from every source; undo reweighting.
    let mut d = vec![vec![INF; n]; n];
    for u in 0..n {
        let dist_prime = dijkstra(&w_prime, u, n);
        for v in 0..n {
            if dist_prime[v] < INF {
                d[u][v] = dist_prime[v] + price[u] - price[v];
            }
        }
    }
    Some(d)
}

fn main() {
    // 3-vertex directed graph: A->B = 2, B->C = -1, A->C = 4.
    let w = vec![
        vec![0, 2, 4],
        vec![INF, 0, -1],
        vec![INF, INF, 0],
    ];
    match all_pairs(&w) {
        None => println!("negative-weight cycle"),
        Some(d) => {
            for row in &d {
                for x in row {
                    if *x == INF { print!("INF "); }
                    else { print!("{} ", x); }
                }
                println!();
            }
        }
    }
}
