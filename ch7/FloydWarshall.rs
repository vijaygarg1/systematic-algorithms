// Floyd-Warshall APSP: triple loop on intermediate vertex.

const INF: i32 = i32::MAX / 2;

fn floyd_warshall(g: &mut [Vec<i32>]) {
    let n = g.len();
    for k in 0..n {
        for i in 0..n {
            for j in 0..n {
                if g[i][k] < INF && g[k][j] < INF && g[i][k] + g[k][j] < g[i][j] {
                    g[i][j] = g[i][k] + g[k][j];
                }
            }
        }
    }
}

fn main() {
    let n = 4;
    let mut g = vec![vec![INF; n]; n];
    for i in 0..n { g[i][i] = 0; }
    g[0][1] = 5; g[0][3] = 10; g[1][2] = 3; g[2][3] = 1;
    floyd_warshall(&mut g);
    for i in 0..n {
        for j in 0..n {
            print!("{} ", if g[i][j] >= INF { -1 } else { g[i][j] });
        }
        println!();
    }
}
