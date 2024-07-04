import java.util.ArrayList;

public class sort{

	public static void main(String[] args) {
		
		//ここから先は他のクラスから取得するデータ
		
		int N=100;
		int maxtime=10000;
		int condition=1;//再生時間　曲数多い　曲数少ない　時間ピッタリ
		
		setArray list=new setArray(N,(int)(maxtime+1));
		
		list.add("cross a line",325,2881264,"id");
		list.add("ミセナイクセニ",200,118488,"id");
		list.add("夜は仄か",198,28009356,"id");
		list.add("太陽のロロロロロノウェ",138,19464,"id");
		list.add("あの子は悪魔",183,342070,"id");
		list.add("目に緋色",180,513055,"id");
		list.add("半透明",197,392630,"id");
		list.add("藍才",274,15110928,"id");
		list.add("さよーならまたいつか",208,32156409,"id");
		list.add("?",130,10382,"id");
		list.add("群青参加",260,25146078,"id");
		list.add("ドライフラワー",287,180777,"id");
		list.add("ゆうせいむぼう",262,24147944,"id");
		list.add("神下り",223,387698,"id");
		list.add("いっと",130,240567,"id");
		list.add("いがく",120,17893640,"id");	//曲タイトル 時間 再生回数 URL
		list.add("framingo",217,182786985,"id");
		list.add("人マニア",127,31447352,"id");
		list.add("感電",293,226984687,"id");
		list.add("冒険六",229,3070913,"id");
		list.add("cross a line",325,2881264,"id");
		list.add("ミセナイクセニ",200,118488,"id");
		list.add("夜は仄か",198,28009356,"id");
		list.add("太陽のロロロロロノウェ",138,19464,"id");
		list.add("あの子は悪魔",183,342070,"id");
		list.add("目に緋色",180,513055,"id");
		list.add("半透明",197,392630,"id");
		list.add("藍才",274,15110928,"id");
		list.add("さよーならまたいつか",208,32156409,"id");
		list.add("?",130,10382,"id");
		list.add("群青参加",260,25146078,"id");
		list.add("ドライフラワー",287,180777,"id");
		list.add("ゆうせいむぼう",262,24147944,"id");
		list.add("神下り",223,387698,"id");
		list.add("いっと",130,240567,"id");
		list.add("いがく",120,17893640,"id");	//曲タイトル 時間 再生回数 URL
		list.add("framingo",217,182786985,"id");
		list.add("人マニア",127,31447352,"id");
		list.add("感電",293,226984687,"id");
		list.add("冒険六",229,3070913,"id");
		list.add("cross a line",325,2881264,"id");
		list.add("ミセナイクセニ",200,118488,"id");
		list.add("夜は仄か",198,28009356,"id");
		list.add("太陽のロロロロロノウェ",138,19464,"id");
		list.add("あの子は悪魔",183,342070,"id");
		list.add("目に緋色",180,513055,"id");
		list.add("半透明",197,392630,"id");
		list.add("藍才",274,15110928,"id");
		list.add("さよーならまたいつか",208,32156409,"id");
		list.add("?",130,10382,"id");
		list.add("群青参加",260,25146078,"id");
		list.add("ドライフラワー",287,180777,"id");
		list.add("ゆうせいむぼう",262,24147944,"id");
		list.add("神下り",223,387698,"id");
		list.add("いっと",130,240567,"id");
		list.add("いがく",120,17893640,"id");	//曲タイトル 時間 再生回数 URL
		list.add("framingo",217,182786985,"id");
		list.add("人マニア",127,31447352,"id");
		list.add("感電",293,226984687,"id");
		list.add("冒険六",229,3070913,"id");
		list.add("cross a line",325,2881264,"id");
		list.add("ミセナイクセニ",200,118488,"id");
		list.add("夜は仄か",198,28009356,"id");
		list.add("太陽のロロロロロノウェ",138,19464,"id");
		list.add("あの子は悪魔",183,342070,"id");
		list.add("目に緋色",180,513055,"id");
		list.add("半透明",197,392630,"id");
		list.add("藍才",274,15110928,"id");
		list.add("さよーならまたいつか",208,32156409,"id");
		list.add("?",130,10382,"id");
		list.add("群青参加",260,25146078,"id");
		list.add("ドライフラワー",287,180777,"id");
		list.add("ゆうせいむぼう",262,24147944,"id");
		list.add("神下り",223,387698,"id");
		list.add("いっと",130,240567,"id");
		list.add("いがく",120,17893640,"id");	//曲タイトル 時間 再生回数 URL
		list.add("framingo",217,182786985,"id");
		list.add("人マニア",127,31447352,"id");
		list.add("感電",293,226984687,"id");
		list.add("冒険六",229,3070913,"id");
		list.add("cross a line",325,2881264,"id");
		list.add("ミセナイクセニ",200,118488,"id");
		list.add("夜は仄か",198,28009356,"id");
		list.add("太陽のロロロロロノウェ",138,19464,"id");
		list.add("あの子は悪魔",183,342070,"id");
		list.add("目に緋色",180,513055,"id");
		list.add("半透明",197,392630,"id");
		list.add("藍才",274,15110928,"id");
		list.add("さよーならまたいつか",208,32156409,"id");
		list.add("?",130,10382,"id");
		list.add("群青参加",260,25146078,"id");
		list.add("ドライフラワー",287,180777,"id");
		list.add("ゆうせいむぼう",262,24147944,"id");
		list.add("神下り",223,387698,"id");
		list.add("いっと",130,240567,"id");
		list.add("いがく",120,17893640,"id");	//曲タイトル 時間 再生回数 URL
		list.add("framingo",217,182786985,"id");
		list.add("人マニア",127,31447352,"id");
		list.add("感電",293,226984687,"id");
		list.add("冒険六",229,3070913,"id");
		
		
		//ここまでが他のクラスから取得するデータ
		list.sort1();
		list.print(maxtime,N);
	}
}


class setArray{
	
	static ArrayList<ArrayList<Object>> Arraylist = new ArrayList<ArrayList<Object>>();
	int[][][] array;
	int[] maxarray={0};
	
	setArray(int N,int time){
		this.array=new int[N][time][2];
		this.maxarray=new int[N];
		int i;
		for(i=0;i<N;i=i+1) {
			maxarray[i]=0;
		}
	}
	
	public void add(String name,int time,int score ,String id) {
		
		ArrayList<Object> array=new ArrayList<Object>();
		array.add(name);
		array.add(time);
		array.add(score);
		array.add(id);
		
		Arraylist.add(array); 
	}
	
	public void sort1() {
		int i,j;
		for(i=0;i<array.length;i=i+1){
			for(j=0;j<gettime(i);j=j+1){
				if(i==0){
					array[i][j][0]=0;
					array[i][j][1]=-1;
				}else {
					array[i][j][0]=array[i-1][j][0];
					array[i][j][1]=j;
				}
			}
			for(j=gettime(i);j<array[0].length;j=j+1) {
				if(i==0){
					array[i][j][0]=gettime(i);
					array[i][j][1]=-1;
				}else {
					if(array[i-1][j-gettime(i)][0]+gettime(i)<=array[i-1][j][0]) {
						array[i][j][0]=array[i-1][j][0];
						array[i][j][1]=j;
					}else {
						array[i][j][0]=array[i-1][j-gettime(i)][0]+gettime(i);
						array[i][j][1]=j-gettime(i);
					}
				}
			}
		}
	}
	
	public int gettime(int i) {
		return (int)Arraylist.get(i).get(1);
	}
	
	public void print(int maxtime,int N) {
		int i,j;

		/*
		for(i=0;i<array[0].length;i=i+1) {
			System.out.printf("%2d ",i);
		}
		System.out.println();
		for(i=0;i<array[0].length;i=i+1) {
			System.out.printf("---");
		}
		System.out.println();
		
		for(i=0;i<array.length;i=i+1) {
			for(j=0;j<array[0].length;j=j+1) {
				System.out.printf("%2d ",array[i][j][0]);
			}
			System.out.println();
		}
		System.out.println();
		System.out.println();
		
		for(i=0;i<array[0].length;i=i+1) {
			System.out.printf("%2d ",i);
		}
		System.out.println();
		for(i=0;i<array[0].length;i=i+1) {
			System.out.printf("---");
		}
		System.out.println();
		
		for(i=0;i<array.length;i=i+1) {
			for(j=0;j<array[0].length;j=j+1) {
				System.out.printf("%2d ",array[i][j][1]);
			}
			System.out.println();
		}
		System.out.println();
		System.out.println();
		*/
		i=N-1;
		j=maxtime;
		int k=0;
		while(k!=-1) {
			k=array[i][j][1];
			if(j!=k&&k!=-1){
				maxarray[i]=1;
			}else if(j!=k&&array[0][j][0]!=0) {
				maxarray[i]=1;
			}
			j=k;
			i=i-1;
		}
		
		for(i=0;i<maxarray.length;i=i+1) {
			System.out.print(maxarray[i]);
		}
		System.out.println();
		System.out.println();
		delete();
		for (Object obj : Arraylist){
			 System.out.println(obj);
	     }
		 System.out.println();
		 
		 int sum=0;
		 for (ArrayList<Object> obj: Arraylist){
			 sum=sum+(Integer)obj.get(1);
	     }
		 System.out.println(sum);
	}
	public void delete() {
		int i;
		for(i=maxarray.length-1;i>=0;i=i-1){
			Arraylist.get(i).remove(2);
			if(maxarray[i]==0) {
				Arraylist.remove(i);
			}
		}
	}
}