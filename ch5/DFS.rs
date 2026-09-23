// Recursive DFS recording discovery and finish times.

fn visit(j: usize, dep: &[Vec<usize>], visited: &mut [bool],
         parent: &mut [i32], discovered: &mut [i32], finished: &mut [i32], tick: &mut i32) {
    visited[j] = true;
    discovered[j] = *tick;
    *tick += 1;
    for &k in &dep[j] {
        if !visited[k] { parent[k] = j as i32; visit(k, dep, visited, parent, discovered, finished, tick); }
    }
    finished[j] = *tick;
    *tick += 1;
}

fn main() {
    let dep: Vec<Vec<usize>> = vec![vec![1, 2], vec![3], vec![3, 4], vec![5], vec![5], vec![]];
    let n = dep.len();
    let mut visited = vec![false; n];
    let mut parent = vec![-1i32; n];
    let mut discovered = vec![0i32; n];
    let mut finished = vec![0i32; n];
    let mut tick = 1i32;
    visit(0, &dep, &mut visited, &mut parent, &mut discovered, &mut finished, &mut tick);
    println!("discovered: {:?}", discovered);
    println!("parent:     {:?}", parent);
    println!("finished:   {:?}", finished);
}
