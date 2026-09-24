package aio.collection;
/**
<p>查找类</p><br>
用于查找数组中的元素。
*/
public class Search {
    /**
    <p>线性查找</p><br>
    从数组中查找目标元素的第一个出现位置。
    @param numbers 数组。
    @param target 目标元素。
    @return 目标元素的第一个出现位置。<br>
    若目标元素不存在，则返回<code>Integer.MIN_VALUE</code>。
    */
    public static int linearSearch(int numbers[],int target) {
        int n=numbers.length;
        for(int i=0;i<n;i++) {
            if(numbers[i]==target) {
                return i;
            }
        }
        return Integer.MIN_VALUE;
    }
    /**
    <p>二分查找</p><br>
    <p>使用时需确保数组已升序排序。</p><br>
    从数组中查找目标元素的位置。
    @param numbers 数组。
    @param target 目标元素。
    @return 目标元素的位置。<br>
    若目标元素不存在，则返回<code>Integer.MIN_VALUE</code>。
    */
    public static int binarySearch(int numbers[],int target) {
        int left=0,right=numbers.length-1;
        while(left<=right) {
            int middle=(right+left)/2;
            if(numbers[middle]==target) {
                return middle;
            } else if(numbers[middle]<target) {
                left=middle+1;
            } else {
                right=middle-1;
            }
        }
        return Integer.MIN_VALUE;
    }
    /**
    <p>二分查找</p><br>
    <p>使用时需确保数组已升序排序。</p><br>
    从数组中查找第一个大于等于目标元素的位置。
    @param numbers 数组。
    @param target 目标元素。
    @return 第一个大于等于目标元素的位置。<br>
    若目标元素大于数组中所有元素，则返回<code>Integer.MIN_VALUE</code>。
    */
    public static int binarySearchFirst(int numbers[],int target) {
        int left=0,right=numbers.length-1;
        int result=numbers.length;
        while(left<=right) {
            int middle=(right+left)/2;
            if(numbers[middle]>=target) {
                result=middle;
                right=middle-1;
            } else {
                left=middle+1;
            }
        }
        return result<numbers.length?result:Integer.MIN_VALUE;
    }
    /**
    <p>二分查找</p><br>
    <p>使用时需确保数组已升序排序。</p><br>
    查找数组中在[<code>min</code>,<code>max</code>]区间内的元素的个数。
    @param numbers 数组。
    @param min 最小值。
    @param max 最大值。
    @return 在[<code>min</code>,<code>max</code>]区间内的元素的个数。
    */
    public static int binarySearchBetween(int numbers[],int min,int max) {
        int left=0,right=numbers.length-1;
        int resultMin=-1;
        int resultMax=numbers.length;
        while(left<=right) {
            int middle=(right+left)/2;
            if(numbers[middle]<min) {
                resultMin=middle;
                left=middle+1;
            } else {
                right=middle-1;
            }
        }
        left=0;
        right=numbers.length-1;
        while(left<=right) {
            int middle=(right+left)/2;
            if(numbers[middle]<=max) {
                left=middle+1;
            } else {
                resultMax=middle;
                right=middle-1;
            }
        }
        return resultMax-resultMin-1>0?resultMax-resultMin-1:0;
    }
    /**
    <p>插值查找</p><br>
    <p>使用时需确保数组已升序排序。</p><br>
    从数组中查找目标元素的位置。
    @param numbers 数组。
    @param target 目标元素。
    @return 目标元素的位置。<br>
    若目标元素不存在，则返回<code>Integer.MIN_VALUE</code>。
    */
    public static int interpolationSearch(int numbers[],int target) {
        int left=0,right=numbers.length-1;
        while(left<=right) {
            int divisor=numbers[right]-numbers[left];
            if(divisor==0) {
                return numbers[left]==target?left:-1;
            } else {
                int position=left+(right-left)*(target-numbers[left])/divisor;
                if(numbers[position]==target) {
                    return position;
                } else if(numbers[position]<target) {
                    left=position+1;
                } else {
                    right=position-1;
                }
            }
        }
        return Integer.MIN_VALUE;
    }
}