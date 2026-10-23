// LLP-StableMarriage: Gale-Shapley via a cached partner[] vector and an
// explicit FIFO worklist of currently-forbidden men -- O(1) amortized
// per check, O(n^2) worst case. See book/lang/progs-stableMatching's
// LLPStableMarriage.llp for why this is procedural rather than the
// language's forbidden/advance sugar (the compiler's realizations of
// that sugar either rescan all n men every round -- measured O(n^3)
// here -- or need a PriorityQueue that cannot cheaply re-examine a
// displaced man whose own proposal count did not change).
//
// G[i] is a proposal count (1-based once nonzero), so mpref lookups are
// mpref[i][G[i] - 1], not mpref[i][G[i]].
//
// The queue is a circular buffer of capacity n + 1: in_queue[] stops a
// man being enqueued twice at once, so at most n distinct men are ever
// in-flight, regardless of the total number of times each is pushed.

fn llp_stable_marriage(mpref: &[Vec<usize>], rank: &[Vec<usize>]) -> Vec<usize> {
    let n = mpref.len();
    let mut g: Vec<usize> = vec![0; n];
    let mut partner: Vec<i64> = vec![-1; n];

    let cap = n + 1;
    let mut queue: Vec<usize> = vec![0; cap];
    let mut in_queue: Vec<bool> = vec![false; n];
    let mut q_head = 0usize;
    let mut q_tail = 0usize;
    let mut q_count = 0usize;
    for i in 0..n {
        queue[q_tail] = i;
        q_tail = (q_tail + 1) % cap;
        q_count += 1;
        in_queue[i] = true;
    }

    while q_count > 0 {
        let i = queue[q_head];
        q_head = (q_head + 1) % cap;
        q_count -= 1;
        in_queue[i] = false;

        let is_forbidden = if g[i] == 0 {
            true
        } else {
            let z0 = mpref[i][g[i] - 1];
            partner[z0] != -1 && rank[z0][partner[z0] as usize] < rank[z0][i]
        };

        if is_forbidden {
            g[i] += 1;
            let z = mpref[i][g[i] - 1];
            if partner[z] == -1 || rank[z][i] < rank[z][partner[z] as usize] {
                let y = partner[z];
                partner[z] = i as i64;
                if y != -1 && !in_queue[y as usize] {
                    queue[q_tail] = y as usize;
                    q_tail = (q_tail + 1) % cap;
                    q_count += 1;
                    in_queue[y as usize] = true;
                }
            }
            if !in_queue[i] {
                queue[q_tail] = i;
                q_tail = (q_tail + 1) % cap;
                q_count += 1;
                in_queue[i] = true;
            }
        }
    }
    g
}

fn main() {
    let mpref: Vec<Vec<usize>> = vec![
        vec![0, 1, 2],
        vec![1, 0, 2],
        vec![0, 1, 2],
    ];
    let rank: Vec<Vec<usize>> = vec![
        vec![2, 1, 3],
        vec![1, 2, 3],
        vec![1, 2, 3],
    ];
    let g = llp_stable_marriage(&mpref, &rank);
    for i in 0..g.len() {
        println!("man {}: {} proposals, matched with woman {}", i, g[i], mpref[i][g[i] - 1]);
    }
}
