   ###常见的4种尝试模型
   1. 从左往右的尝试模型 (Knapsack、ConvertLetterToString)
   2. 范围上的尝试模型 (CardsInLine)
   3. 多样本位置全对应的尝试模型 (LongestCommonSubSequence)
   4. 寻找业务限制的尝试模型
   
   ###设计暴力递归过程的原则
   1. 每一个可变参数的类型，一定不要比int、String类型更加复杂
   2. 原则1可以违反，让类型突破到一维线性结构，那必须是唯一可变参数
   3. 如果发现原则1被违反，但不违反原则2，只需要做到记忆化搜索即可
   4. 可变参数的个数，能少则少