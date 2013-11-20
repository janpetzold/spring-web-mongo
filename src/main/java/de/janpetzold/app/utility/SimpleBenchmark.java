package de.janpetzold.app.utility;

import java.util.Date;

public class SimpleBenchmark {
	private long startTime = 0;
	
	public SimpleBenchmark() {
		this.startTime = new Date().getTime();
	}
	
	public void stop(String operation) {
		long totalTime = new Date().getTime() - this.startTime;
		
		// TODO: Use logging
		System.out.println("Operation " + operation + " took " + totalTime + " milliseconds.");
	}
}
