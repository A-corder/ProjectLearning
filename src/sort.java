import java.util.ArrayList;

public class sort {

	public static void main(String[] args) {
		
		//ここから先は他のクラスから取得するデータ
		
		int N=20;
		
		setArray list=new setArray(N);
		
		list.add("cross a line","ヒプノシスマイク",325,2881264,"id");
		list.add("ミセナイクセニ","朱鷺シッククイーン",200,118488,"id");
		list.add("夜は仄か","Eve",198,28009356,"id");
		list.add("太陽のロロロロロノウェ","まいりました人間くん",138,19464,"id");
		list.add("あの子は悪魔","こめだわら",183,342070,"id");
		list.add("目に緋色","しししし",180,513055,"id");
		list.add("半透明","しししし",197,392630,"id");
		list.add("藍才","Eve",274,15110928,"id");
		list.add("さよーならまたいつか","米津玄師",208,32156409,"id");
		list.add("?","???",130,10382,"id");
		list.add("群青参加","Eve",260,25146078,"id");
		list.add("ドライフラワー","はくぜん",287,180777,"id");
		list.add("ゆうせいむぼう","Eve",262,24147944,"id");
		list.add("神下り","しししし",223,387698,"id");
		list.add("いっと","アネイロ",130,240567,"id");
		list.add("いがく","原口",120,17893640,"id");	//曲タイトル アーティスト 時間 評価値 idアドレス
		list.add("framingo","米津犬歯",217,182786985,"id");
		list.add("人マニア","原口",127,31447352,"id");
		list.add("感電","余熱犬歯",293,226984687,"id");
		list.add("冒険六","Eve",229,3070913,"id");
		
		int maxtime=600;
		
		String condition="time";	//時間：time   その他:nontime
		
		//ここまでが他のクラスから取得するデータ
		
		int array[]=new int[N];
		array=clear(array);
		list.update(array);
		
		list.print();
		
		make(list,array,maxtime,0,condition);
		
		list.delete();
		
		list.print();
		
	}
	
	public static void make(setArray list, int[] array,int maxtime, int i,String condition){
		
		if(condition=="nontime") {
			array[i]=1;
			if(list.calculate(array,2)<(Integer)maxtime*1.05) {
				if(list.calculate(array,3)>list.calculate(list.maxarray,3)&&list.calculate(array,2)>(Integer)maxtime*0.9) {
					list.update(array);
				}
				if(i+1<list.size()) {
					make(list,array,maxtime,i+1,condition);
				}
			}
			array[i]=0;
			if(i+1<list.size()) {
				make(list,array,maxtime,i+1,condition);
			}
		}
		else if(condition=="time") {
			array[i]=1;
			if(list.calculate(array,2)<(Integer)maxtime*1.1) {
				if(Math.abs(list.calculate(array,2)-maxtime)<=Math.abs(list.calculate(list.maxarray,2)-maxtime)) {
					if(list.calculate(array,2)>(Integer)maxtime*0.9) {
						if(list.calculate(array,3)>=list.calculate(list.maxarray,3)) {
							list.update(array);
						}
					}
				}
				if(i+1<list.size()) {
					make(list,array,maxtime,i+1,condition);
				}
			}
			array[i]=0;
			if(i+1<list.size()) {
				make(list,array,maxtime,i+1,condition);
			}
		}
	}

	public static int[] clear(int[] array) {
		int i;
		for(i=0;i<array.length;i=i+1) {
			array[i]=0;
		}
		return array;
	}
}


class setArray{
	
	static ArrayList<ArrayList<Object>> Arraylist = new ArrayList<ArrayList<Object>>();
	int[] maxarray;	
	
	setArray(int N){
		this.maxarray=new int[N];	
	}
	
	public void add(String name,String artist,int time,int score ,String id) {
		
		ArrayList<Object> array=new ArrayList<Object>();
		array.add(name);
		array.add(artist);
		array.add(time);
		array.add(score);
		array.add(id);
		
		Arraylist.add(array); 
	}
	
	public int calculate(int[] array,int n){
		int ret=0,i;
		
		for(i=0;i<Arraylist.size();i=i+1) {
			if(array[i]==1) {
				ret=ret+(Integer)Arraylist.get(i).get(n);
			}
		}
		return ret;
	}
	
	public void print() {
		 for (Object obj : Arraylist){
			 System.out.println(obj);
	     }
		 System.out.println();
		 
		 int sum=0,time=0;
		 for (ArrayList<Object> obj : Arraylist){
			 time=time+(Integer)obj.get(2);
			 sum=sum+(Integer)obj.get(3);
	     }
		 System.out.println("総時間："+time);
		 System.out.println("評価値："+sum);
		 System.out.println("-------------------");
		 System.out.println();
	}
	
	public int[] maxarray() {
		return maxarray;
	}
	
	public void update(int[] newlist){
		int i;
		for(i=0;i<newlist.length;i=i+1){
			maxarray[i]=newlist[i];
		}
	}
	
	public void delete() {
		int i;
		for(i=maxarray.length-1;i>=0;i=i-1) {
			if(maxarray[i]==0) {
				Arraylist.remove(i);
			}
		}
	}
	
	public int size() {
		return Arraylist.size();
	}
}