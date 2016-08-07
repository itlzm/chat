package cn.lzm;

public class Demo {

	public static void main(String[] args) throws Exception{

		if(test()){
			System.out.println("true");
		}else{
			System.out.println("false");
		}
		System.out.println(2);
	}
	public static boolean test(){
		boolean flag = false;
		try{
			int[] arr = null;
			System.out.println(arr.length);
			flag = true;
			return flag;
		}catch(Exception e){
			e.printStackTrace();
		}
		return flag;
	}

}
