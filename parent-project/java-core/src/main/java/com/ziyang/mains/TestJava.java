package com.ziyang.mains;

import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.IntStream;

public class TestJava {

	public static void main(String[] args) {
//		ConcurrentHashMap<String, Integer> m = new ConcurrentHashMap<>();
//		for (int i = 0; i < 200; i++) {
//			m.put("" + i, i);
//		}
		
		ConcurrentHashMap<String, Integer> m = new ConcurrentHashMap<>(8, 0.9f, 1);

		IntStream.range(0, 200).forEach(i -> {
			m.put("" + i, i);
		});

		m.forEach(4, (k, v) -> {
			System.out.println(
					"Thread - " + Thread.currentThread().getName() + " -, printing: key = " + k + ", value = " + v);
		});
	}

}
