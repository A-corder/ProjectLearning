package com.example.youtubeapi;

import java.util.ArrayList;
import java.util.List;

public class PlaylistUtils {

    public static ArrayList<ArrayList<Object>> processAndSort(List<List<Object>> videoData, int time, int optimizationCondition) {
        int N = videoData.size();
        int maxtime = time;
        
        setArray list = new setArray(N, (int) ((maxtime + 1) * 1.026+20));
        
        for (List<Object> video : videoData) {
            String title = (String) video.get(0);
            int videoTime = (int) video.get(1);
            int viewCount = (int) video.get(2);
            String videoId = (String) video.get(3);
            list.add(title, videoTime, viewCount, videoId);
        }
        
		list.resetting((int)((maxtime+1)*1.026));
		N=list.recount();
		list.rearray(N,(int)((maxtime+1)*1.026+20));
		switch(optimizationCondition) {
			case 1:
				list.sort1(maxtime);						//再生回数ソート
				list.arrayupdate(maxtime, N, optimizationCondition);
				break;
			case 2:
				list.sort2(maxtime);						//曲数多いソート
				list.arrayupdate(maxtime, N, optimizationCondition);
				break;
			case 3:
				list.sort3(maxtime);						//曲数少ないソート
				list.arrayupdate(maxtime, N, optimizationCondition);
				break;
			case 4:
				list.sort4(maxtime);						//時間ピッタリソート
				list.arrayupdate(maxtime, N, optimizationCondition);
				break;
		}
		list.delete();			//不必要な曲を削除
		//list.print();			//出力
		
		return list.Arraylist;
	}
}


class setArray{
	
	ArrayList<ArrayList<Object>> Arraylist = new ArrayList<ArrayList<Object>>();	//100曲を保存するArraList<ArrayList<Object>>
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
	
	public void resetting(int maxtime) {	//maxtime*1.026+20よりも長い曲は削除
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
			j=gettime(i);
			if(j==gettime(i)) {
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
			for(j=gettime(i)+1;j<array[0].length;j=j+1) {
				if(i==0){
					array[i][j][0]=0;
					array[i][j][1]=-1;
					array[i][j][2]=-1;
				}else if(array[i-1][j-gettime(i)][0]!=0&&array[i-1][j][0]!=0){
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
				}else if(array[i-1][j-gettime(i)][0]!=0){
					array[i][j][0]=array[i-1][j-gettime(i)][0]+1;
					array[i][j][1]=j-gettime(i);
					array[i][j][2]=j-gettime(i);
				}else if(array[i-1][j][0]!=0) {
					array[i][j][0]=array[i-1][j][0];
					array[i][j][1]=j;
					array[i][j][2]=j;
				}else {
					array[i][j][0]=array[i-1][j][0];
					array[i][j][1]=j;
					array[i][j][2]=j;
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
					if(j==0) {
						array[i][j][0]=0;
					}
				}else {
					array[i][j][0]=array[i-1][j][0];
					array[i][j][1]=j;
					array[i][j][2]=j;
				}
			}
			j=gettime(i);
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
				}else if(array[i-1][j-gettime(i)][0]!=100&&array[i-1][j][0]!=100){
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
				}else if(array[i-1][j-gettime(i)][0]!=100){
					array[i][j][0]=array[i-1][j-gettime(i)][0]+1;
					array[i][j][1]=j-gettime(i);
					array[i][j][2]=j-gettime(i);
				}else if(array[i-1][j][0]!=100) {
					array[i][j][0]=array[i-1][j][0];
					array[i][j][1]=j;
					array[i][j][2]=j;
				}else {
					array[i][j][0]=array[i-1][j][0];
					array[i][j][1]=j;
					array[i][j][2]=j;
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
			j=gettime(i);
			if(j==gettime(i)) {
				if(i==0){
					array[i][j][0]=getcount(i);
					array[i][j][1]=-1;
					array[i][j][2]=-1;
				}else {
					if(array[i-1][j-gettime(i)][0]+getcount(i)>array[i-1][j][0]) {
						array[i][j][0]=array[i-1][j-gettime(i)][0]+getcount(i);
						array[i][j][1]=j-gettime(i);
						array[i][j][2]=j-gettime(i);
					}
					else if(array[i-1][j-gettime(i)][0]+getcount(i)==array[i-1][j][0]){
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
					array[i][j][0]=0;
					array[i][j][1]=-1;
					array[i][j][2]=-1;
				}else if(array[i-1][j-gettime(i)][0]!=0&&array[i-1][j][0]!=0){
					if(array[i-1][j-gettime(i)][0]+getcount(i)>array[i-1][j][0]) {
						array[i][j][0]=array[i-1][j-gettime(i)][0]+getcount(i);
						array[i][j][1]=j-gettime(i);
						array[i][j][2]=j-gettime(i);
					}
					else if(array[i-1][j-gettime(i)][0]+getcount(i)==array[i-1][j][0]){
						array[i][j][0]=array[i-1][j][0];
						array[i][j][1]=j-gettime(i);
						array[i][j][2]=j;
					}
					else {
						array[i][j][0]=array[i-1][j][0];
						array[i][j][1]=j;
						array[i][j][2]=j;
					}
				}else if(array[i-1][j-gettime(i)][0]!=0){
					array[i][j][0]=array[i-1][j-gettime(i)][0]+getcount(i);
					array[i][j][1]=j-gettime(i);
					array[i][j][2]=j-gettime(i);
				}else if(array[i-1][j][0]!=0) {
					array[i][j][0]=array[i-1][j][0];
					array[i][j][1]=j;
					array[i][j][2]=j;
				}else {
					array[i][j][0]=array[i-1][j][0];
					array[i][j][1]=j;
					array[i][j][2]=j;
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
		long left,right;
		if(i==0){
			System.out.println("ROOT");
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
				left=fillcount(i-1,(int)array[i][j][1],sum+getcount(i),maxtime);
				right=fillcount(i-1,(int)array[i][j][2],sum,maxtime);
				System.out.println(i+" BRANCH "+left+" "+right);
				System.out.println("LEFT :"+(i-1)+" "+array[i][j][1]+" "+sum+getcount(i));
				System.out.println("RIGHT:"+(i-1)+" "+array[i][j][2]+" "+sum);
				if(left<right) {
					System.out.println("RIGHT");
					System.out.println();
					return searchcount(i-1,(int)array[i][j][2],sum,maxtime);
				}
				else{
					System.out.println("LEFT");
					System.out.println();
					return searchcount(i-1,(int)array[i][j][1],sum+getcount(i),maxtime);
				}
				
			}
		}
	}
	
	public long fillcount(int i,int j,int sum,int maxtime) {
		long left,right;
		if(i==0){
			if(array[i][j][0]==0||array[i][j][0]==100) {
				return sum;
			}else {
				return sum+getcount(i);
			}
		}else {
			if(array[i][j][1]==array[i][j][2]){
				if(array[i][j][1]==j){
					return fillcount(i-1,(int)array[i][j][1],sum,maxtime);
				}
				else {
					return fillcount(i-1,(int)array[i][j][1],sum+getcount(i),maxtime);
				}
			}
			else{
				left=fillcount(i-1,(int)array[i][j][1],sum+getcount(i),maxtime);
				right=fillcount(i-1,(int)array[i][j][2],sum,maxtime);
				if(left<right) {
					array[i][j][1]=array[i][j][2];
					return fillcount(i-1,(int)array[i][j][2],sum,maxtime);
				}
				else{
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
		int time=0,count=0;
		for (ArrayList<Object> obj: Arraylist){
			time=time+(Integer)obj.get(1);
			count=count+(Integer)obj.get(2);
	    }
		System.out.println("総時間："+time);
		System.out.println("評価値："+count);
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
				j=(int)((maxtime+1)*1.026+20)-1;	
				while(trace!=-1) {
					trace=(int)array[i][j][1];
					if(j!=trace){
						if(i!=0){
							maxarray[i]=1;
						}else if(array[i][j][0]!=0){
							maxarray[i]=1;
						}
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
					trace=(int)array[i][j][1];
					if(j!=trace){
						if(i!=0){
							maxarray[i]=1;
						}else if(array[i][j][0]!=0){
							maxarray[i]=1;
						}
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
					if(j!=trace){
						if(i!=0){
							maxarray[i]=1;
						}else if(j!=0&&array[i][j][0]!=100||j==0&&array[i][j][0]!=0){
							maxarray[i]=1;
						}
					}
					j=trace;
					i=i-1;
				}
				break;
				
			case 4:
				j=maxtime;
				fillcount(i,j,0,maxtime);
				while(trace!=-1) {
					trace=(int)array[i][j][1];
					if(j!=trace){
						if(i!=0){
							maxarray[i]=1;
						}else if(array[i][j][0]!=0){
							maxarray[i]=1;
						}
					}
					j=trace;
					i=i-1;
				}
				break;
		}
		/*
		int a,b;
		for(b=0;b<array[0].length;b=b+1) {
			System.out.printf("%5d",b);
		}
		System.out.println();
		for(b=0;b<array[0].length;b=b+1) {
			System.out.print("-----");
		}
		System.out.println();
		
		for(a=0;a<array.length;a=a+1) {
			for(b=0;b<array[0].length/5;b=b+1) {
				System.out.printf("%5d",array[a][b][0]);
			}
			System.out.println();
		}
		/*
		System.out.println();
		System.out.println();
		for(a=0;a<array.length;a=a+1) {
			System.out.printf("%3d",a);
		}
		System.out.println();
		for(a=0;a<array.length;a=a+1) {
			System.out.print("---");
		}
		System.out.println();
		for(a=0;a<array.length;a=a+1) {
			for(b=0;b<array[0].length;b=b+1) {
				System.out.printf("%3d",array[a][b][1]-array[a][b][2]);
			
			}
			System.out.println();
		}
		System.out.println();
		*/
	}
}
