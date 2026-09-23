// Classical Bellman-Ford: n-1 relaxation passes.

const INF: i32 = i32::MAX / 2;

fn shortest_path(n: usize, u: &[usize], v: &[usize], w: &[i32], s: usize) -> Vec<i32> {
    let mut dist = vec![INF; n];
    dist[s] = 0;
    for _ in 1..n {
        for e in 0..u.len() {
            if dist[u[e]] + w[e] < dist[v[e]] {
                dist[v[e]] = dist[u[e]] + w[e];
            }
        }
    }
    dist
}

fn main() {
    let u = [0, 0, 1, 2, 3];
    let v = [1, 2, 3, 3, 4];
    let w = [4, 1, 5, 2, 3];
    println!("dist: {:?}", shortest_path(5, &u, &v, &w, 0));
}
