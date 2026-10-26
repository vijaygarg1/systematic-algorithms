/*
 * Min-heap on an int[]: build, heapify, insert, extract-min.
 *
 * Auto-generated from Heap.llp by lang/llcjssrc.py.
 * Do not edit by hand; edit the .llp source and rerun the build.
 */


class Heap {
  build(A, n) {
    this.A = A;
    this.n = n;
    this.i = (Math.trunc(this.n / 2) - 1);
    while ((this.i >= 0)) {
        this.heapify(this.A, this.i, this.n);
        this.i = (this.i - 1);
    }
  }

  heapify(A, i, n) {
    let smallest = i;
    let left = ((2 * i) + 1);
    let right = ((2 * i) + 2);
    if (((left < n) && (A[left] < A[smallest]))) {
        smallest = left;
    }
    if (((right < n) && (A[right] < A[smallest]))) {
        smallest = right;
    }
    if ((smallest !== i)) {
        const _sw1 = A[i]; A[i] = A[smallest]; A[smallest] = _sw1;
        this.heapify(A, smallest, n);
    }
  }

  insert(A, key, n) {
    n = (n + 1);
    let i = (n - 1);
    while (((i > 0) && (A[Math.trunc((i - 1) / 2)] > key))) {
        A[i] = A[Math.trunc((i - 1) / 2)];
        i = Math.trunc((i - 1) / 2);
    }
    A[i] = key;
    return n;
  }

  extractMin(A, n) {
    if ((n < 1)) {
        return (-2147483648);
    }
    let min_ = A[0];
    A[0] = A[(n - 1)];
    n = (n - 1);
    this.heapify(A, 0, n);
    return min_;
  }
}

if (typeof module !== 'undefined') module.exports = Heap;

if (typeof require !== 'undefined' && require.main === module) {
    const A = [3, 1, 6, 1, 6, 3, 6, 4];
    const prog = new Heap();
    prog.build(A, A.length);
    console.log(JSON.stringify(A));
}
