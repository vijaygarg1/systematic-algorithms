// Classical Dijkstra: extract-min frontier vertex, relax outgoing edges.

const INF: i32 = i32::MAX / 2;

fn shortest_path(w: &[Vec<i32>], s: usize) -> Vec<i32> {
    let n = w.len();
    let mut dist = vec![INF; n];
    let mut fixed = vec![false; n];
    dist[s] = 0;
    for _ in 0..n {
        let mut j: Option<usize> = None;
        let mut best = INF;
        for k in 0..n {
            if !fixed[k] && dist[k] < best { j = Some(k); best = dist[k]; }
        }
        let j = match j { Some(x) => x, None => break };
        fixed[j] = true;
        for k in 0..n {
            if fixed[k] || w[j][k] >= INF { continue; }
            if dist[j] + w[j][k] < dist[k] { dist[k] = dist[j] + w[j][k]; }
        }
    }
    dist
}

fn main() {
    let n = 5;
    let mut w = vec![vec![INF; n]; n];
    w[0][1] = 4;  w[0][2] = 1;  w[1][2] = 2;  w[1][3] = 5;
    w[2][3] = 8;  w[2][4] = 10; w[3][4] = 2;
    println!("dist: {:?}", shortest_path(&w, 0));
}
