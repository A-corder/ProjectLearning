import java.util.ArrayList;

public class Playlist{

	public static void main(String[] args) {
		
		//ここから先は他のクラスから取得するデータ
		
		int N=100;			//取得する曲数　100固定の予定
		int maxtime=6000;	//再生リストの時間　0.95倍から1.05倍の間で出来上がる
		int condition=4;	//1:再生回数　2:曲数多い　3:曲数少ない　4:時間ピッタリ
		
		setArray list=new setArray(N,(int)((maxtime)*1.026+20));	//コンストラクタ呼びだし
		
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
		list.add("いがく",120,17893640,"id");
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
		list.add("怪物",208,358769857,"id");
		list.add("残響参加",181,221736869,"id");
		list.add("踊",209,205544095,"id");
		list.add("ギラギラ",277,171910112,"id");
		list.add("cry baby",240,151989153,"id");
		list.add("ただ声一つ",162,130656360,"id");
		list.add("きらり",240,125634398,"id");
		list.add("シンデレラボーイ",244,108627943,"id");
		list.add("初心LOVE",228,106414876,"id");
		list.add("群青",361,106313595,"id");
		list.add("大統領になったらね",194,98424082,"id");
		list.add("春泥棒",299,96141780,"id");
		list.add("阿修羅ちゃん",198,93874736,"id");
		list.add("踊り子",245,91717976,"id");
		list.add("one last kiss",258,89246514,"id");
		list.add("三原色",226,85074289,"id");
		list.add("好きだから",298,81267162,"id");
		list.add("ヴァンパイア",180,79964405,"id");
		list.add("アンコール",281,77052347,"id");
		list.add("忘れ",272,76335222,"id");
		list.add("神っぽいな",204,73769161,"id");
		list.add("paleblue",321,71574060,"id");
		list.add("ペテルギウス",270,67723379,"id");
		list.add("おもかげ",244,65362727,"id");
		list.add("poppin shakin",197,64641355,"id");
		list.add("U",205,59060824,"id");
		list.add("フォニ",188,57668278,"id");
		list.add("恋古月夜に",203,56958636,"id");
		list.add("暗く宇六",258,56550045,"id");
		list.add("選考",309,56085212,"id");
		list.add("hellohello",195,53803609,"id");
		list.add("シンデレラガール",208,53717066,"id");
		list.add("なんでもないよ",270,49248185,"id");
		list.add("夜の人笑い",240,49005598,"id");
		list.add("chopstick",176,46308414,"id");
		list.add("エゴロック",171,45488504,"id");
		list.add("ラブレター",209,45292799,"id");
		list.add("ロウワー",230,44960061,"id");
		list.add("boy",244,44871229,"id");
		list.add("のびしろ",219,44265565,"id");
		list.add("より良い",217,44044011,"id");
		list.add("死神",119,42582648,"id");
		list.add("breakfast",191,42258541,"id");
		list.add("plastic love",309,41662071,"id");
		list.add("ki wasurai",230,41501206,"id");
		list.add("なにやってもうまくいかない",147,41017231,"id");
		list.add("解凍",203,40347048,"id");
		list.add("君に夢中",261,40074519,"id");
		list.add("不思議",353,39505322,"id");
		list.add("ちんてんか",230,38712653,"id");
		list.add("shining one",258,38076488,"id");
		list.add("花占い",236,37595376,"id");
		list.add("paradise",328,37572211,"id");
		list.add("キュートな彼女",131,37360453,"id");
		list.add("one ok",247,36917639,"id");
		list.add("涙の海を超えて行け",241,36894853,"id");
		list.add("curtain",266,36883473,"id");
		list.add("優しい水星",219,35990124,"id");
		list.add("小悪魔だってかまわない",209,35479700,"id");
		list.add("開会来たん",224,34814843,"id");
		list.add("メンタルチェンソー",177,34384277,"id");
		list.add("明星",273,34275700,"id");
		list.add("家庭の事情",236,32583146,"id");
		list.add("daice",323,32336069,"id");
		list.add("evolution",226,32069957,"id");
		list.add("fakeland",216,31950353,"id");
		list.add("yumyumyum",172,31917162,"id");
		list.add("universe",334,31175325,"id");
		list.add("easygame",185,30987587,"id");
		list.add("サクラが降る夜は",292,30627626,"id");		//曲タイトル 時間 再生回数 URL
		
		//ここまでが他のクラスから取得するデータ
		list.resetting(maxtime);
		N=list.recount();
		list.rearray(N,(int)((maxtime+1)*1.05));
		switch(condition) {
			case 1:
				list.sort1(maxtime);						//再生回数ソート
				list.arrayupdate(maxtime, N, condition);
				break;
			case 2:
				list.sort2(maxtime);						//曲数多いソート
				list.arrayupdate(maxtime, N, condition);
				break;
			case 3:
				list.sort3(maxtime);						//曲数少ないソート
				list.arrayupdate(maxtime, N, condition);
				break;
			case 4:
				list.sort4(maxtime);						//時間ピッタリソート
				list.arrayupdate(maxtime, N, condition);
				break;
		}
		list.delete();			//不必要な曲を削除
		list.print();	//出力
	}
}


class setArray{
	
	static ArrayList<ArrayList<Object>> Arraylist = new ArrayList<ArrayList<Object>>();	//100曲を保存するArraList<ArrayList<Object>>
	long[][][] array;			//動的計画法に必要な表(3次元)
	int[] maxarray={0};			//解を保存する配列
	
	setArray(int N,int time){
		this.array=new long[N][time][3];
		this.maxarray=new int[N];
	}
	
	public void add(String name,int time,int score ,String id) {	//ArraList<ArrayList<Object>>を作っているところ
		
		ArrayList<Object> array=new ArrayList<Object>();
		array.add(name);
		array.add(time);
		array.add(score/1000);
		array.add(id);
		
		Arraylist.add(array); 
	}
	
	public void resetting(int maxtime) {
		int i;
		for(i=maxarray.length-1;i>=0;i=i-1){
			if((int)Arraylist.get(i).get(1)>maxtime) {
				Arraylist.remove(i);
			}
		}
	}
	
	public int recount() {
		int ret=0;
		for (Object obj : Arraylist){
			ret=ret+1;
		}
		return ret;
	}
	
	public void rearray(int N,int time) {
		array=new long[N][time][3];
		maxarray=new int[N];
	}
	
	public void sort1(int maxtime) {	//再生回数ソート　中身の説明は略
		int i,j;
		for(i=0;i<array.length;i=i+1){
			for(j=0;j<gettime(i);j=j+1){
				if(i==0){
					array[i][j][0]=0;
					array[i][j][1]=-1;
					array[i][j][2]=-1;
				}else {
					array[i][j][0]=array[i-1][j][0];
					array[i][j][1]=j;
					array[i][j][2]=j;
				}
			}
			for(j=gettime(i);j<array[0].length;j=j+1) {
				if(i==0){
					array[i][j][0]=getcount(i);
					array[i][j][1]=-1;
					array[i][j][2]=-1;
				}else {
					if(array[i-1][j-gettime(i)][0]+getcount(i)>array[i-1][j][0]) {
						array[i][j][0]=array[i-1][j-gettime(i)][0]+getcount(i);
						array[i][j][1]=j-gettime(i);
						array[i][j][2]=j-gettime(i);
					}else if(array[i-1][j-gettime(i)][0]+getcount(i)==array[i-1][j][0]){
						array[i][j][0]=array[i-1][j][0];
						array[i][j][1]=j-gettime(i);
						array[i][j][2]=j;
					}
					else {
						array[i][j][0]=array[i-1][j][0];
						array[i][j][1]=j;
						array[i][j][2]=j;
					}
				}
			}
		}
	}
	
	public void sort2(int maxtime) {	//曲数多いソート　中身の説明は略
		int i,j;
		for(i=0;i<array.length;i=i+1){
			for(j=0;j<gettime(i);j=j+1){
				if(i==0){
					array[i][j][0]=0;
					array[i][j][1]=-1;
					array[i][j][2]=-1;
				}else {
					array[i][j][0]=array[i-1][j][0];
					array[i][j][1]=j;
					array[i][j][2]=j;
				}
			}
			for(j=gettime(i);j<array[0].length;j=j+1) {
				if(i==0){
					array[i][j][0]=1;
					array[i][j][1]=-1;
					array[i][j][2]=-1;
				}else {
					if(array[i-1][j-gettime(i)][0]+1>array[i-1][j][0]) {
						array[i][j][0]=array[i-1][j-gettime(i)][0]+1;
						array[i][j][1]=j-gettime(i);
						array[i][j][2]=j-gettime(i);
					}
					else if(array[i-1][j-gettime(i)][0]+1==array[i-1][j][0]){
						array[i][j][0]=array[i-1][j][0];
						array[i][j][1]=j-gettime(i);
						array[i][j][2]=j;
					}
					else {
						array[i][j][0]=array[i-1][j][0];
						array[i][j][1]=j;
						array[i][j][2]=j;
					}
				}
			}
		}
	}
	
	public void sort3(int maxtime) {	//曲数少ないソート　中身ry
		int i,j;
		for(i=0;i<array.length;i=i+1){
			for(j=0;j<gettime(i);j=j+1){
				if(i==0){
					array[i][j][0]=100;
					array[i][j][1]=-1;
					array[i][j][2]=-1;
				}else {
					array[i][j][0]=array[i-1][j][0];
					array[i][j][1]=j;
					array[i][j][2]=j;
				}
			}
			if(j==gettime(i)) {
				if(i==0){
					array[i][j][0]=1;
					array[i][j][1]=-1;
					array[i][j][2]=-1;
				}else {
					if(array[i-1][j-gettime(i)][0]+1<array[i-1][j][0]) {
						array[i][j][0]=array[i-1][j-gettime(i)][0]+1;
						array[i][j][1]=j-gettime(i);
						array[i][j][2]=j-gettime(i);
					}
					else if(array[i-1][j-gettime(i)][0]+1==array[i-1][j][0]){
						array[i][j][0]=array[i-1][j][0];
						array[i][j][1]=j-gettime(i);
						array[i][j][2]=j;
					}
					else {
						array[i][j][0]=array[i-1][j][0];
						array[i][j][1]=j;
						array[i][j][2]=j;
					}
				}
			}
			for(j=gettime(i)+1;j<array[0].length;j=j+1) {
				if(i==0){
					array[i][j][0]=100;
					array[i][j][1]=-1;
					array[i][j][2]=-1;
				}else {
					if(array[i-1][j-gettime(i)][0]+1<array[i-1][j][0]) {
						array[i][j][0]=array[i-1][j-gettime(i)][0]+1;
						array[i][j][1]=j-gettime(i);
						array[i][j][2]=j-gettime(i);
					}
					else if(array[i-1][j-gettime(i)][0]+1==array[i-1][j][0]){
						array[i][j][0]=array[i-1][j][0];
						array[i][j][1]=j-gettime(i);
						array[i][j][2]=j;
					}
					else {
						array[i][j][0]=array[i-1][j][0];
						array[i][j][1]=j;
						array[i][j][2]=j;
					}
				}
			}
		}
	}
	
	public void sort4(int maxtime) {	//再生時間ソート　(ry
		int i,j;
		for(i=0;i<array.length;i=i+1){
			for(j=0;j<gettime(i);j=j+1){
				if(i==0){
					array[i][j][0]=0;
					array[i][j][1]=-1;
					array[i][j][2]=-1;
				}else {
					array[i][j][0]=array[i-1][j][0];
					array[i][j][1]=j;
					array[i][j][2]=j;
				}
			}
			for(j=gettime(i);j<array[0].length;j=j+1) {
				if(i==0){
					array[i][j][0]=gettime(i);
					array[i][j][1]=-1;
					array[i][j][2]=-1;
				}else {
					if(array[i-1][j-gettime(i)][0]+gettime(i)>array[i-1][j][0]) {
						array[i][j][0]=array[i-1][j-gettime(i)][0]+gettime(i);
						array[i][j][1]=j-gettime(i);
						array[i][j][2]=j-gettime(i);
					}
					else if(array[i-1][j-gettime(i)][0]+gettime(i)==array[i-1][j][0]){
						array[i][j][0]=array[i-1][j][0];
						array[i][j][1]=j-gettime(i);
						array[i][j][2]=j;
					}
					else {
						array[i][j][0]=array[i-1][j][0];
						array[i][j][1]=j;
					}
				}
			}
		}
	}
	
	public int sumtime() {		//100曲の総再生時間を計算
		int ret=0;
		for (ArrayList<Object> obj : Arraylist){
			 ret=ret+(int)obj.get(1);
	     }
		return ret;
	}
	
	public int gettime(int i) {
		return (int)Arraylist.get(i).get(1);	//i曲目の時間を返す
	}
	
	public int getcount(int i) {
		return (int)Arraylist.get(i).get(2);	//i曲目の再生回数を返す
	}
	
	public int searchtime(int i,int j,int sum,int maxtime) {	
		
		if(i==0){
			if(array[i][j][0]==0){
				return sum;
			}else {
				return sum+gettime(i);
			}
		}else {
			if(array[i][j][1]==array[i][j][2]) {
				if(array[i][j][1]==j) {
					return searchtime(i-1,(int)array[i][j][1],sum,maxtime);
				}
				else {
					return searchtime(i-1,(int)array[i][j][1],sum+gettime(i),maxtime);
				}
			}
			else{
				if(Math.abs(searchtime(i-1,(int)array[i][j][1],sum+gettime(i),maxtime)-maxtime)>Math.abs(searchtime(i-1,(int)array[i][j][2],sum,maxtime)-maxtime)) {
					return searchtime(i-1,(int)array[i][j][2],sum,maxtime);
				}
				else{
					return searchtime(i-1,(int)array[i][j][1],sum+gettime(i),maxtime);
				}
				
			}
		}
	}
	
	public int filltime(int i,int j,int sum,int maxtime) {	
		
		if(i==0){
			if(array[i][j][0]==0) {
				return sum;
			}else {
				return sum+gettime(i);
			}
		}else {
			if(array[i][j][1]==array[i][j][2]) {
				if(array[i][j][1]==j) {
					return filltime(i-1,(int)array[i][j][1],sum,maxtime);
				}
				else {
					return filltime(i-1,(int)array[i][j][1],sum+gettime(i),maxtime);
				}
			}
			else{
				if(Math.abs(filltime(i-1,(int)array[i][j][1],sum+gettime(i),maxtime)-maxtime)>Math.abs(filltime(i-1,(int)array[i][j][2],sum,maxtime)-maxtime)) {
					array[i][j][1]=array[i][j][2];
					return filltime(i-1,(int)array[i][j][2],sum,maxtime);
				}
				else{
					array[i][j][2]=array[i][j][1];
					return filltime(i-1,(int)array[i][j][1],sum+gettime(i),maxtime);
				}
				
			}
		}
	}
	
	public long searchcount(int i,int j,int sum,int maxtime) {	
		
		if(i==0){
			if(array[i][j][0]==0) {
				return sum;
			}else {
				return sum+getcount(i);
			}
		}else {
			if(array[i][j][1]==array[i][j][2]) {
				if(array[i][j][1]==j) {
					return searchcount(i-1,(int)array[i][j][1],sum,maxtime);
				}
				else {
					return searchcount(i-1,(int)array[i][j][1],sum+getcount(i),maxtime);
				}
			}
			else{
				if(Math.abs(searchcount(i-1,(int)array[i][j][1],sum+getcount(i),maxtime)-maxtime)>Math.abs(searchcount(i-1,(int)array[i][j][2],sum,maxtime)-maxtime)) {
					return searchcount(i-1,(int)array[i][j][2],sum,maxtime);
				}
				else{
					return searchcount(i-1,(int)array[i][j][1],sum+getcount(i),maxtime);
				}
				
			}
		}
	}
	
	public long fillcount(int i,int j,int sum,int maxtime) {	
		
		if(i==0){
			System.out.println("root");
			if(array[i][j][0]==0||array[i][j][0]==100) {
				return sum;
			}else {
				return sum+getcount(i);
			}
		}else {
			if(array[i][j][1]==array[i][j][2]) {
				if(array[i][j][1]==j) {
					return fillcount(i-1,(int)array[i][j][1],sum,maxtime);
				}
				else {
					return fillcount(i-1,(int)array[i][j][1],sum+getcount(i),maxtime);
				}
			}
			else{
				System.out.println("branch "+fillcount(i-1,(int)array[i][j][1],sum+getcount(i),maxtime)+" "+fillcount(i-1,(int)array[i][j][2],sum,maxtime));
				if(fillcount(i-1,(int)array[i][j][1],sum+getcount(i),maxtime)<fillcount(i-1,(int)array[i][j][2],sum,maxtime)) {
					System.out.println("right");
					System.out.println();
					array[i][j][1]=array[i][j][2];
					return fillcount(i-1,(int)array[i][j][2],sum,maxtime);
				}
				else{
					System.out.println("left");
					System.out.println();
					array[i][j][2]=array[i][j][1];
					return fillcount(i-1,(int)array[i][j][1],sum+getcount(i),maxtime);
				}
				
			}
		}
	}
	
	public void print() {	//結果の出力

		for (Object obj : Arraylist){
			System.out.println(obj);
		}
		System.out.println();
		/*
		int time=0,count=0;
		for (ArrayList<Object> obj: Arraylist){
			time=time+(Integer)obj.get(1);
			count=count+(Integer)obj.get(2);
	    }
		System.out.println("総時間："+time);
		System.out.println("評価値："+count);
		*/
	}
	
	public void delete() {					//いらない曲の消去
		int i;
		for(i=maxarray.length-1;i>=0;i=i-1){
			Arraylist.get(i).remove(2);
			if(maxarray[i]==0) {
				Arraylist.remove(i);
			}
		}
	}
	
	public void arrayupdate(int maxtime,int N,int condition) {	//表を埋め終わった後に最適な候補を探す
		int i=N-1,j=0,trace=0,n=0,left,right;
		
		switch(condition){
			case 1:					//表のN-1行j番目
				j=(int)((maxtime+1)*1.05)-1;
				
				while(trace!=-1) {
					trace=(int)array[i][j][1];
					if(j!=trace&&(trace!=-1||array[0][j][0]!=0)){
						maxarray[i]=1;
					}
					j=trace;
					i=i-1;
				}
				break;
				
			case 2:					//表のN-1行目の中から曲数の一番多いところ、かつその中でも総再生回数が一番多いものを探す
				left=(int)(array[0].length*0.95);
				right=(int)(array[0].length*0.95);
				for(n=(int)(maxtime*0.95);n<array[0].length;n=n+1) {
					if(array[i][n][0]>array[i][left][0]) {
						left=n;
					}
					if(array[i][n][0]>=array[i][right][0]) {
						right=n;
					}
				}
				j=left;
				for(n=left;n<=right;n=n+1) {
					if(array[i][n][0]==array[i][left][0]) {
						if(Math.abs(j-maxtime)>Math.abs(n-maxtime)) {
							j=n;
						}
					}
				}
				fillcount(i,j,0,maxtime);
				while(trace!=-1) {
					trace=(int)array[i][j][1];
					if(j!=trace&&(trace!=-1||array[0][j][0]!=0)){
						maxarray[i]=1;
					}
					j=trace;
					i=i-1;
				}
				break;
				
			case 3:				//表のN-1行目の中から曲数の一番多いところ、かつその中でも総再生回数が一番少ないものを探す
				left=(int)(maxtime*0.99);
				right=(int)(maxtime*0.99);
				for(n=(int)(maxtime*0.99);n<array[0].length;n=n+1) {
					if(array[i][n][0]<array[i][left][0]) {
						left=n;
					}
					if(array[i][n][0]<=array[i][right][0]) {
						right=n;
					}
				}
				j=left;
				for(n=left;n<=right;n=n+1) {
					if(array[i][n][0]==array[i][left][0]) {
						if(Math.abs(j-maxtime)>Math.abs(n-maxtime)) {
							j=n;
						}
					}
				}
				fillcount(i,j,0,maxtime);
				while(trace!=-1) {
					trace=(int)array[i][j][1];
					if(j!=trace&&(trace!=-1||array[0][j][0]!=0)){
						maxarray[i]=1;
					}
					j=trace;
					i=i-1;
				}
				break;
				
			case 4:
				left=(int)(maxtime*0.99);
				right=(int)(maxtime*0.99);
				for(n=(int)(maxtime*0.99);n<array[0].length;n=n+1) {
					if(Math.abs(array[i][n][0]-maxtime)<Math.abs(array[i][left][0]-maxtime)) {
						left=n;
					}
					if(Math.abs(array[i][n][0]-maxtime)<=Math.abs(array[i][left][0]-maxtime)) {
						right=n;
					}
				}
				j=(int)((left+right)/2);
				fillcount(i,j,0,maxtime);
				while(trace!=-1) {
					trace=(int)array[i][j][1];
					if(j!=trace&&(trace!=-1||array[0][j][0]!=0)){
						maxarray[i]=1;
					}
					j=trace;
					i=i-1;
				}
				break;
		}
		/*
		int a,b;
		for(b=(int)(array[0].length*0.9);b<array[0].length;b=b+1) {
			System.out.printf("%5d",b);
		}
		System.out.println();
		for(b=(int)(array[0].length*0.9);b<array[0].length;b=b+1) {
			System.out.print("-----");
		}
		System.out.println();
		for(a=(int)(array.length*0.9);a<array.length;a=a+1) {
			for(b=(int)(array[0].length*0.9);b<array[0].length;b=b+1) {
				System.out.printf("%5d",array[a][b][0]);
			}
			System.out.println();
		}
		System.out.println();
		System.out.println();
		for(a=(int)(array.length*0.9);a<array.length;a=a+1) {
			System.out.printf("%5d",a);
		}
		System.out.println();
		for(a=(int)(array.length*0.9);a<array.length;a=a+1) {
			System.out.print("-----");
		}
		System.out.println();
		for(a=(int)(array.length*0.9);a<array.length;a=a+1) {
			for(b=(int)(array[0].length*0.9);b<array[0].length;b=b+1) {
				System.out.printf("%5d",array[a][b][1]-array[a][b][2]);
			
			}
			System.out.println();
		}
		System.out.println();
		*/
	}
}