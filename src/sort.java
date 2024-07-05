import java.util.ArrayList;

public class sort{

	public static void main(String[] args) {
		
		//ここから先は他のクラスから取得するデータ
		
		int N=30;
		int maxtime=2000;
		int condition=4;//再生回数　曲数多い　曲数少ない　時間ピッタリ
		
		setArray list=new setArray(N,(int)((maxtime+1)*1.05));
		
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
		list.add("take a picture",225,91181165,"id");
		list.add("星を仰ぐ",299,28128264,"id");
		list.add("w/x/y",279,153938352,"id");
		list.add("群青ランナウェイ",239,15784028,"id");
		list.add("愛を知るまでは",278,23184715,"id");
		list.add("一途",190,110470612,"id");
		list.add("ごめんね",274,14479577,"id");
		list.add("なにこれ",198,15479708,"id");
		list.add("chopstick",176,46298043,"id");
		list.add("弱音は奇",168,63743517,"id");
		
		//ここまでが他のクラスから取得するデータ
		
		switch(condition) {
			case 1:
				list.sort1(maxtime);
				list.arrayupdate(maxtime, N, condition);
				break;
			case 2:
				list.sort2(maxtime);
				list.arrayupdate(maxtime, N, condition);
				break;
			case 3:
				list.rearray(N,(int)((list.sumtime()-maxtime)*1.05));
				list.sort3((int)((list.sumtime()-maxtime)*1.05));
				list.arrayupdate(list.sumtime()-maxtime, N, condition);
				break;
			case 4:
				list.sort4(maxtime);
				list.arrayupdate(maxtime, N, condition);
				break;
		}
		list.delete();
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
	}
	
	public void add(String name,int time,int score ,String id) {
		
		ArrayList<Object> array=new ArrayList<Object>();
		array.add(name);
		array.add(time);
		array.add(score);
		array.add(id);
		
		Arraylist.add(array); 
	}
	
	public int sumtime() {
		int ret=0;
		for (ArrayList<Object> obj : Arraylist){
			 ret=ret+(int)obj.get(1);
	     }
		return ret;
	}
	
	public void rearray(int n,int m) {
		array=new int[n][m][2];
		int i;
		for(i=0;i<maxarray.length;i=i+1) {
			maxarray[i]=1;
		}
	}
	
	public void sort1(int maxtime) {
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
					array[i][j][0]=getcount(i);
					array[i][j][1]=-1;
				}else {
					if(array[i-1][j-gettime(i)][0]+getcount(i)<=array[i-1][j][0]) {
						array[i][j][0]=array[i-1][j][0];
						array[i][j][1]=j;
					}else {
						array[i][j][0]=array[i-1][j-gettime(i)][0]+getcount(i);
						array[i][j][1]=j-gettime(i);
					}
				}
			}
		}
	}
	
	public void sort2(int maxtime) {
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
					array[i][j][0]=1;
					array[i][j][1]=-1;
				}else {
					if(array[i-1][j-gettime(i)][0]+1>array[i-1][j][0]) {
						array[i][j][0]=array[i-1][j-gettime(i)][0]+1;
						array[i][j][1]=j-gettime(i);
						
					}
					else if(array[i-1][j-gettime(i)][0]+1==array[i-1][j][0]){
						if(Math.abs(gettotaltime(i-1,j-gettime(i))+gettime(i)-maxtime)>Math.abs(gettotaltime(i-1,j)-maxtime)) {
							array[i][j][0]=array[i-1][j-gettime(i)][0]+1;
							array[i][j][1]=j-gettime(i);
						}else {
							array[i][j][0]=array[i-1][j][0];
							array[i][j][1]=j;
						}
					}else {
						array[i][j][0]=array[i-1][j][0];
						array[i][j][1]=j;
					}
				}
			}
		}
	}
	
	public void sort3(int maxtime) {
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
					array[i][j][0]=1;
					array[i][j][1]=-1;
				}else {
					if(array[i-1][j-gettime(i)][0]+1>array[i-1][j][0]) {
						array[i][j][0]=array[i-1][j-gettime(i)][0]+1;
						array[i][j][1]=j-gettime(i);
					}
					else if(array[i-1][j-gettime(i)][0]+1==array[i-1][j][0]){
						if(Math.abs(gettotaltime(i-1,j-gettime(i))-maxtime+gettime(i))>Math.abs(gettotaltime(i-1,j)-maxtime)) {
							array[i][j][0]=array[i-1][j-gettime(i)][0]+1;
							array[i][j][1]=j-gettime(i);
						}else {
							array[i][j][0]=array[i-1][j][0];
							array[i][j][1]=j;
						}
					}else {
						array[i][j][0]=array[i-1][j][0];
						array[i][j][1]=j;
					}
				}
			}
		}
	}
	
	public void sort4(int maxtime) {
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
					if(array[i-1][j-gettime(i)][0]+gettime(i)>array[i-1][j][0]) {
						array[i][j][0]=array[i-1][j-gettime(i)][0]+gettime(i);
						array[i][j][1]=j-gettime(i);	
					}
					else if(array[i-1][j-gettime(i)][0]+gettime(i)==array[i-1][j][0]){
						if(gettotalcount(i-1,j-gettime(i))+getcount(i)>gettotalcount(i-1,j)) {
							array[i][j][0]=array[i-1][j-gettime(i)][0]+gettime(i);
							array[i][j][1]=j-gettime(i);
						}else {
							array[i][j][0]=array[i-1][j][0];
							array[i][j][1]=j;
						}
					}
					else {
						array[i][j][0]=array[i-1][j][0];
						array[i][j][1]=j;
					}
				}
			}
		}
	}
	
	public int gettime(int i) {
		return (int)Arraylist.get(i).get(1);
	}
	
	public int getcount(int i) {
		return (int)Arraylist.get(i).get(2);
	}
	
	public int gettotalcount(int n,int i) {
		int j,ret=0;
		for(j=n;j>=0;j=j-1) {
			if(array[j][i][1]!=i&&array[j][i][1]!=-1) {
				ret=ret+getcount(j);
				i=array[j][i][1];
			}
			else if(array[j][i][1]==-1&&array[j][i][0]!=0) {
				ret=ret+getcount(j);
				i=array[j][i][1];
			}
		}
		return ret;
	}
	
	public int gettotaltime(int n,int i) {
		int j,ret=0;
		for(j=n;j>=0;j=j-1) {
			if(array[j][i][1]!=i&&array[j][i][1]!=-1) {
				ret=ret+gettime(j);
				i=array[j][i][1];
			}
			else if(array[j][i][1]==-1&&array[j][i][0]!=0) {
				ret=ret+gettime(j);
				i=array[j][i][1];
			}
		}
		return ret;
	}

	
	public void print(int maxtime,int N) {

		for (Object obj : Arraylist){
			System.out.println(obj);
	     }
		 System.out.println();
		 
		 int time=0,count=0;
		 for (ArrayList<Object> obj: Arraylist){
			 time=time+(Integer)obj.get(1);
			 count=count+(Integer)obj.get(2);
	     }
		 System.out.println("総時間："+time);
		 System.out.println("評価値："+count);
	}
	
	public void delete() {
		int i;
		for(i=maxarray.length-1;i>=0;i=i-1){
			//Arraylist.get(i).remove(2);
			if(maxarray[i]==0) {
				Arraylist.remove(i);
			}
		}
	}
	
	public void arrayupdate(int maxtime,int N,int condition) {
		int i=N-1,j=0,trace=0,n,m;
		
		switch(condition){
		
			case 1:
				j=(int)((maxtime+1)*1.05)-1;
				break;
				
			case 2:
				j=0;
				m=0;
				for(n=0;n<(int)((maxtime+1)*1.05);n=n+1) {
					if(array[i][n][0]>array[i][j][0]) {
						j=n;
					}
					if(array[i][n][0]>=array[i][m][0]) {
						m=n;
					}
				}
				for(n=j;n<=m;n=n+1) {
					if(Math.abs(gettotaltime(N-1,n)-maxtime)<Math.abs(gettotaltime(N-1,j)-maxtime)) {
						j=n;
					}
				}
				break;
				
			case 3:
				j=(int)(maxtime*0.99);
				m=(int)(maxtime*0.99);
				for(n=(int)(maxtime*0.99);n<(int)(maxtime*1.01);n=n+1) {
					if(array[i][n][0]>array[i][j][0]) {
						j=n;
					}
					if(array[i][n][0]>=array[i][m][0]) {
						m=n;
					}
				}
				for(n=j;n<=m;n=n+1) {
					if(Math.abs(gettotaltime(N-1,n)-maxtime)<Math.abs(gettotaltime(N-1,j)-maxtime)) {
						j=n;
					}
				}
				break;
				
			case 4:
				j=maxtime;
				break;
		}
		/*
		int a,b;
		for(a=27;a<30;a=a+1) {
			for(b=1935;b<2100;b=b+1) {
				System.out.printf("%5d",array[a][b][0]);
			}
			System.out.println();
		}
		System.out.println();
		System.out.println();
		
		for(a=27;a<30;a=a+1) {
			for(b=1935;b<2100;b=b+1) {
				System.out.printf("%5d",array[a][b][1]);
			}
			System.out.println();
		}
		System.out.println();
		System.out.println();
		*/
		if(condition!=3) {
			while(trace!=-1) {
				trace=array[i][j][1];
				if(j!=trace&&(trace!=-1||array[0][j][0]!=0)){
					maxarray[i]=1;
				}
				j=trace;
				i=i-1;
			}
		}else if(condition==3) {
			while(trace!=-1) {
				trace=array[i][j][1];
				if(j!=trace&&(trace!=-1||array[0][j][0]!=0)){
					maxarray[i]=0;
				}
				j=trace;
				i=i-1;
			}
		}
	}
}