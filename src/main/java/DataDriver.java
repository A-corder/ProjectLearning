package com.example.youtubeapi;

public class DataDriver {
	public static void main(String[] args)throws Exception{
		System.out.println("processSearchWordで「classic」と入力した場合の検索結果を表示します");
		Data.processSearchWord("classic");
		System.out.println();
		System.out.println("processSearchWordで「jpop」と入力した場合の検索結果を表示します");
		Data.processSearchWord("jpop");
	}

}
