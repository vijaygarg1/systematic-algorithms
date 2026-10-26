// Mandatory-Intervals: interval scheduling when a subset S of the intervals
// must appear in the answer.  Matches bxx-greedy.tex Algorithm
// Mandatory-Intervals.  Returns None for "infeasible", which happens exactly
// when two mandatory intervals overlap.
//
// As in the chapter, intervals arrive already indexed in finish-time order.
//
// Until 2026-10-26 this was a one-rule composition program, conjoined with
// LLP-IntervalScheduling.  That conjunction rejects feasible instances: with
// interval 0 = [0,2) optional and interval 1 = [1,3) mandatory,
// LLP-IntervalScheduling opens by selecting interval 0 -- correct only for
// the UNCONSTRAINED problem -- and the selection can never be retracted, so
// the mandatory rule sees the overlap and reports infeasible even though {1}
// is feasible and optimal.  The constraint has to restrict the INSTANCE, in
// the three phases below.

fn overlap(j: usize, k: usize, s: &[i32], f: &[i32]) -> bool {
    s[j] < f[k] && s[k] < f[j]
}

fn mandatory_intervals(s: &[i32], f: &[i32], mandatory: &[bool]) -> Option<Vec<bool>> {
    let n = s.len();
    let mut sel = vec![false; n];

    // Phase 1: feasibility.  Two mandatory intervals may not overlap.
    // Reserve the mandatory intervals that pass.
    for j in 0..n {
        if mandatory[j] {
            if (0..n).any(|k| mandatory[k] && k != j && overlap(j, k, s, f)) {
                return None;
            }
            sel[j] = true;
        }
    }

    // Phase 2: an optional interval that conflicts with a mandatory one can
    // appear in no compatible set containing S, so drop it.  Every surviving
    // candidate is disjoint from all of S.
    let mut cand = vec![false; n];
    for j in 0..n {
        if !mandatory[j] {
            cand[j] = !(0..n).any(|k| mandatory[k] && overlap(j, k, s, f));
        }
    }

    // Phase 3: ordinary earliest-finish-time greedy over the survivors.
    // They never conflict with S, so only conflicts among themselves matter.
    let mut chosen = false;
    let mut last_finish = 0;
    for j in 0..n {
        if cand[j] && (!chosen || s[j] >= last_finish) {
            sel[j] = true;
            last_finish = f[j];
            chosen = true;
        }
    }
    Some(sel)
}

fn main() {
    // interval 0 = [0,2) optional, interval 1 = [1,3) mandatory:
    // the conjunction used to call this infeasible; {1} is the optimum.
    let s = [0, 1];
    let f = [2, 3];
    let mandatory = [false, true];
    match mandatory_intervals(&s, &f, &mandatory) {
        Some(sel) => println!("selected: {:?}", sel),
        None => println!("infeasible"),
    }
}
