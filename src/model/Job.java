package model;

public class Job {

	private String jobName;
	private String account;
	private int executionTimeHour;
	private int executionTimeMinute;
	private int executionTimeSecond;
	private int memory;
	private String memoryUnit;
	
	public String getMemoryUnit() {
		return memoryUnit;
	}
	public void setMemoryUnit(String memoryUnit) {
		this.memoryUnit = memoryUnit;
	}
	public String getJobName() {
		return jobName;
	}
	public void setJobName(String jobName) {
		this.jobName = jobName;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public int getExecutionTimeHour() {
		return executionTimeHour;
	}
	public void setExecutionTimeHour(int executionTimeHour) {
		this.executionTimeHour = executionTimeHour;
	}
	public int getExecutionTimeMinute() {
		return executionTimeMinute;
	}
	public void setExecutionTimeMinute(int executionTimeMinute) {
		this.executionTimeMinute = executionTimeMinute;
	}
	public int getExecutionTimeSecond() {
		return executionTimeSecond;
	}
	public void setExecutionTimeSecond(int executionTimeSecond) {
		this.executionTimeSecond = executionTimeSecond;
	}
	public int getMemory() {
		return memory;
	}
	public void setMemory(int memory) {
		this.memory = memory;
	}

}
