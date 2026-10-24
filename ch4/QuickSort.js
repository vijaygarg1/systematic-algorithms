/*
 * Sequential quicksort with Lomuto partition (parallel form in progs-divideConquer/).
 *
 * Auto-generated from QuickSort.llp by lang/llcjssrc.py.
 * Do not edit by hand; edit the .llp source and rerun the build.
 */


class QuickSort {
  sort(lo, hi, G) {
    let n = G.length;
    if ((lo < hi)) {
        let p = this.Partition(lo, hi, G);
        this.sort(lo, (p - 1), G);
        this.sort((p + 1), hi, G);
    }
  }

  Partition(lo, hi, G) {
    let pivot = G[hi];
    let i = (lo - 1);
    let j = lo;
    while ((j <= (hi - 1))) {
        if ((G[j] <= pivot)) {
            i = (i + 1);
            let tmp = G[i];
            G[i] = G[j];
            G[j] = tmp;
        }
        j = (j + 1);
    }
    let tmp = G[(i + 1)];
    G[(i + 1)] = G[hi];
    G[hi] = tmp;
    return (i + 1);
  }
}

if (typeof module !== 'undefined') module.exports = QuickSort;

if (typeof require !== 'undefined' && require.main === module) {
    const G = [3, 1, 6, 1, 6, 3, 6, 4];
    const prog = new QuickSort();
    prog.sort(0, G.length - 1, G);
    console.log(JSON.stringify(G));
}
