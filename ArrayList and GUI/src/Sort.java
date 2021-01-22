
public class Sort {
	public static void main(String[] args) {
		int[] nums = {-1,2,10,4,16,-1,2,10,4,16,-1,2,10,4,16};
		boolean sorted = false;
		/*
		while(!sorted) {
			int num1 = (int)(Math.random()*nums.length);
			int num2 = (int)(Math.random()*nums.length);
			
			int temp = nums[num1];
			nums[num1] = nums[num2];
			nums[num2] = temp;
			
			boolean check = true;
			for (int i = 0; i < nums.length-1; i ++) {
				if (nums[i] > nums[i +1]) {
					check = false;
				}
			}
			sorted = check;
		}
		*/
		boolean swapped = false;
		for(int j = nums.length-1; j > 0; j --) {
			swapped = false;
			for (int i = 0; i < j -1; i ++) {
				System.out.println(j);
				if (nums[i] > nums[i + 1]) {
					int temp = nums[i];
					nums[i] = nums[i +1];
					nums[i +1] = temp;
					swapped = true;
				}
			}
			if (swapped == false) {
				break;
			}
		}
		for (int i = 0; i < nums.length; i ++) {
			System.out.println(nums[i]);
		}
	}
}
