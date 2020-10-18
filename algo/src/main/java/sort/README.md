|               | 时间复杂度      | 空间复杂度     | 稳定性 |
|---------------|------------|-----------|-----|
| SelectionSort | O\(n^2\)   | O\(1\)    | no  |
| BubbleSort    | O\(n^2\)   | O\(1\)    | yes |
| InsertionSort | O\(n^2\)   | O\(1\)    | yes |
| MergeSort     | O\(nlogn\) | O\(n\)    | yes |
| QuickSort     | O\(nlogn\) | O\(logn\) | no  |
| HeapSort      | O\(nlogn\) | O\(1\)    | no  |
|               |            |           |     |
| CountSort     | O\(n\)     | O\(k\)    | yes |
| RedixSort     | O\(n\)     | O\(n\)    | yes |


###常见的坑
1. MergeSort的空间复杂度可以优化成O(1)，通过内部缓存法，但是会变得不再稳定
2. QuickSort可以变成稳定性，通过01 stable sort，但是对数据样本有要求（规定范围内）

###Java里实现的Sort
Java里面的sort是基于多种排序组合起来的
1. 会先根据要排序的对象判断，如果是引用传递的就用MergeSort（要保证稳定性），如果是以值传递的就用QuickSort
2. 在QuickSort里面，会根据数组的大小判断，如果小于60个，直接用InsertionSort返回，因为InsertionSort的常量操作比QuickSort要快，而在数组长度较小时会更高效。