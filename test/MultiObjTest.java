package test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.StringTokenizer;

import sa.SimulatedAnnealingMO;
import solution.IntegerSolution;
import solution.Solution;
import acceptance.DefaultMOAcceptance;
import cooling.DecrementalCooling;

class MyIntegerSolutionMO extends IntegerSolution {
	// private Random r=new Random();
	private int TASK_NUM = 100; // Number of tasks present in the system
	private static int SERV_NUM = 32; // Number of servers
	private static int SERV_ON_RACK = 8; // Number of servers in a rack
	private static int RACK_NUM = (SERV_NUM / SERV_ON_RACK)
			+ (SERV_NUM % SERV_ON_RACK == 0 ? 0 : 1); // Number of racks
	private static int RACK_ON_POD = 2; // Number of racks in a pod
	private static int POD_NUM = (RACK_NUM / RACK_ON_POD)
			+ (RACK_NUM % RACK_ON_POD == 0 ? 0 : 1); // Number of pods
	private static double CPU_CAPACITY = 120E6; // CPU computational power
												// [instruction/second] (taken
												// by
												// http://en.wikipedia.org/wiki/Instructions_per_second
												// )
	private double P_S_IDLE = 0; // Fixed amount of power used by CPUs [W]
	private double P_S_PEAK = 300; // Peak power of a generic CPU [W]
	private double P_TS_PEAK = 200; // Peak power of a top of rack switch [W]
	private double P_TS_IDLE = 0.80 * P_TS_PEAK; // Fixed amount of power used
													// by top of rack switches
													// [W]
	private double P_AGG_PEAK = 2500; // Peak power of an aggregation switch [W]
	private double P_AGG_IDLE = 0.80 * P_AGG_PEAK; // Idle aggregation switch
													// power consumption [W]

	private double SERVER_LINK_CAPACITY = 1E9; // Single link Server - ToR
												// switch capacity
												// [bits/sec]
	private double RACK_LINK_CAPACITY = 20e9;
	// int weight[]={1,2,3,4,5,7,8,9,10};
	int[] instructions;
	double[] bandwidth;

	MyIntegerSolutionMO(int task, int firstServer, int lastServer,
			String instrFileName, String bwFileName) throws IOException {
		super(task, firstServer, lastServer);

		SERV_NUM = lastServer - firstServer + 1;
		RACK_NUM = (SERV_NUM / SERV_ON_RACK)
				+ (SERV_NUM % SERV_ON_RACK == 0 ? 0 : 1);
		POD_NUM = (RACK_NUM / RACK_ON_POD)
				+ (RACK_NUM % RACK_ON_POD == 0 ? 0 : 1);
		try {
			instructions = readTaskVector(instrFileName);
			TASK_NUM = super.size = instructions.length;
			bandwidth = readBWVector(bwFileName);
		} catch (IOException ioe) {
			ioe.printStackTrace();
			instructions = null;
			bandwidth = null;
		}

	}

	MyIntegerSolutionMO(int task, int firstServer, int lastServer)
			throws IOException {
		this(task, firstServer, lastServer,
				"/home/peppone/workspace/JMetalMinimum/gen_task.dat",
				"/home/peppone/workspace/JMetalMinimum/gen_bw.dat");
	}

	MyIntegerSolutionMO(MyIntegerSolutionMO s) {
		super(s);
		TASK_NUM = s.TASK_NUM;
		SERV_NUM = s.SERV_NUM;
		SERV_ON_RACK = s.SERV_ON_RACK;
		RACK_NUM = s.RACK_NUM; // Number of racks
		RACK_ON_POD = s.RACK_ON_POD;

		P_S_IDLE = s.P_S_IDLE;
		P_S_PEAK = s.P_S_PEAK;
		P_TS_PEAK = s.P_TS_PEAK;
		P_TS_IDLE = s.P_TS_IDLE;
		P_AGG_PEAK = s.P_AGG_PEAK;
		P_AGG_IDLE = s.P_AGG_IDLE;

		SERVER_LINK_CAPACITY = s.SERVER_LINK_CAPACITY;
		RACK_LINK_CAPACITY = s.RACK_LINK_CAPACITY;
		instructions = s.instructions;
		bandwidth = s.bandwidth;

	}

	public static int[] readTaskVector(String filename) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(
				new File(filename)));
		StringTokenizer tokenizer = new StringTokenizer(br.readLine());
		int limit = tokenizer.countTokens();
		tokenizer.nextToken();
		tokenizer.nextToken();
		tokenizer.nextToken(); // remove first three tokens;
		// String values[]=br.readLine().split(" ");
		int result[] = new int[limit - 4];
		// limit-4 because first three and last token are discarded
		for (int i = 0; i < limit - 4; ++i) {
			result[i] = Integer.parseInt(tokenizer.nextToken());
		}
		br.close();
		return result;
	}

	public static double[] readBWVector(String filename) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(
				new File(filename)));
		StringTokenizer tokenizer = new StringTokenizer(br.readLine());
		int limit = tokenizer.countTokens();
		tokenizer.nextToken();
		tokenizer.nextToken();
		tokenizer.nextToken(); // remove first three tokens;
		// String values[]=br.readLine().split(" ");
		double result[] = new double[limit - 4];
		// limit-4 because first three and last token are discarded
		for (int i = 0; i < limit - 4; ++i) {
			result[i] = Double.parseDouble(tokenizer.nextToken());
		}
		br.close();
		return result;
	}

	public MyIntegerSolutionMO findMostFarVertex() {
		MyIntegerSolutionMO mostFarVertex = new MyIntegerSolutionMO(this);
		Integer[] vertexVar = new Integer[this.size];
		for (int i = 0; i < size; ++i) {
			int candidate = getVariables(i);
			if (candidate - this.getMinValue(i) < Math.abs(candidate
					- this.getMaxValue(i))) {
				vertexVar[i] = getMaxValue(i);

			} else
				vertexVar[i] = getMinValue(i);

		}
		mostFarVertex.setVariables(vertexVar);
		return mostFarVertex;
	}

	public MyIntegerSolutionMO getRandomVertex() {
		Random rand = new Random();
		MyIntegerSolutionMO randomVertex = new MyIntegerSolutionMO(this);
		Integer[] vertexVar = new Integer[this.size];
		for (int i = 0; i < size; ++i) {
			vertexVar[i] = rand.nextInt(this.getMaxValue(i) - getMinValue(i))
					+ getMinValue(i);

		}

		randomVertex.setVariables(vertexVar);
		return randomVertex;
	}

	@Override
	public Comparable[] fitness() {
		Double[] fitnessF = new Double[2];
		fitnessF[0] = maxCompletionTime();
		fitnessF[1] = powerConsumption();
		return fitnessF;
	}


	public Double minIdleServers() {
		Integer[] allocation = (Integer[]) this.getVariables();
		int[] allocation_resources = new int[SERV_NUM];
		double max = 0;
		for (int i = 0; i < allocation.length; ++i) {
			allocation_resources[allocation[i]] += 1;
		}
		for (int i = 0; i < SERV_NUM; ++i) {
			if (allocation_resources[i] == 0)
				max++;
		}
		return new Double(-max);
	}

	public Double powerConsumption() {
		int task_per_server[] = new int[SERV_NUM];
		int task_per_rack[] = new int[RACK_NUM];
		int task_per_pod[] = new int[POD_NUM];
		double instruction_per_server[] = new double[SERV_NUM];
		double bandwidth_per_server[] = new double[SERV_NUM];
		double bandwidth_per_rack[] = new double[RACK_NUM];
		double bandwidth_per_pod[] = new double[POD_NUM];
		// Variable[] var = solution.getDecisionVariables();

		for (int i = 0; i < TASK_NUM; ++i) {
			int server_index = variables[i];
			task_per_server[server_index]++;
			task_per_rack[server_index / SERV_ON_RACK]++;
			task_per_pod[(server_index / SERV_ON_RACK) / RACK_ON_POD]++;
			instruction_per_server[server_index] += instructions[i];
			bandwidth_per_server[server_index] += bandwidth[i];
		}

		// Fitness function 2;
		double server_power_consumption = 0;
		double switch_power_consumption = 0;

		for (int i = 0; i < SERV_NUM; ++i) {
			server_power_consumption += (task_per_server[i] == 0 ? P_S_IDLE
					: P_S_PEAK);

			bandwidth_per_rack[i / SERV_ON_RACK] += (bandwidth_per_server[i] <= SERVER_LINK_CAPACITY ? bandwidth_per_server[i]
					: SERVER_LINK_CAPACITY);
		}
		for (int i = 0; i < RACK_NUM; ++i) {
			bandwidth_per_pod[i / RACK_ON_POD] += (bandwidth_per_rack[i] <= RACK_LINK_CAPACITY ? bandwidth_per_rack[i]
					: RACK_LINK_CAPACITY);
		}

		double tor_switch_power_consumption = 0;
		for (int i = 0; i < RACK_NUM; ++i) {
			if (task_per_rack[i] != 0) {
				tor_switch_power_consumption += P_TS_IDLE
						+ (P_TS_PEAK - P_TS_IDLE)
						* (bandwidth_per_rack[i] / (SERV_ON_RACK * SERVER_LINK_CAPACITY));
			}
		}

		double agg_switch_power_consumption = 0;
		for (int i = 0; i < POD_NUM; ++i) {
			if (task_per_pod[i] != 0) {
				agg_switch_power_consumption += 2
						* P_AGG_IDLE
						+ (P_AGG_PEAK - P_AGG_IDLE)
						* (bandwidth_per_pod[i] / (RACK_ON_POD * RACK_LINK_CAPACITY));
			}
		}

		switch_power_consumption = tor_switch_power_consumption
				+ agg_switch_power_consumption;
		return new Double(switch_power_consumption + server_power_consumption);
	}

	public Double maxCompletionTime() {
		int instructions[] = new int[SERV_NUM];
		for (int i = 0; i < this.variables.length; ++i) {
			instructions[variables[i]] += this.instructions[i];
		}
		int max = -1;
		for (int i = 0; i < instructions.length; ++i) {
			if (max < instructions[i])
				max = instructions[i];
		}
		return new Double(max);
	}

	public MyIntegerSolutionMO firstStrategy() {
		MyIntegerSolutionMO sol = getAnotherNeighbour();
		if (this.overallConstraintViolation() > 0)
			sol = this.getFirstFeasibleSolution();
		return sol;

	}

	public MyIntegerSolutionMO secondStrategy() {
		return secondStrategy(0);
	}

	private MyIntegerSolutionMO secondStrategy(int counter) {
		if (counter > size)
			return this;
		MyIntegerSolutionMO sol = this;
		if (this.overallConstraintViolation() > 0) {

			sol = sol.meanSolution(findMostFarVertex());
		}
		if (sol.overallConstraintViolation() > 0) {
			counter++;
			sol = sol.secondStrategy(counter);
		}
		return sol;

	}

	public MyIntegerSolutionMO thirdStrategy() {
		return thirdStrategy(0);

	}

	private MyIntegerSolutionMO thirdStrategy(int counter) {
		if (counter > size)
			return this;
		MyIntegerSolutionMO sol = this;
		MyIntegerSolutionMO old = this;
		if (this.overallConstraintViolation() > 0) {

			sol = sol.meanSolution(getRandomVertex());
		}
		if (sol.overallConstraintViolation() > 0) {
			counter++;
			if (old.overallConstraintViolation() < sol
					.overallConstraintViolation())
				sol = old;
			sol = sol.thirdStrategy(counter);
		}
		return sol;

	}

	@Override
	public Solution nextNeighbour() {
		Random r = new Random();
		int index = r.nextInt(TASK_NUM);
		MyIntegerSolutionMO duplicate = null;
		duplicate = new MyIntegerSolutionMO(this);
		int iteration = 0;
		double bestConstraintViolation = Double.MAX_VALUE;
		double neighbourViolation = 0;
		MyIntegerSolutionMO bestNeighbour = null;

		// ////
		duplicate = duplicate.firstStrategy();
		// duplicate = duplicate.secondStrategy();
		// duplicate = duplicate.thirdStrategy();
		return duplicate;

		// /////
		/*
		 * do{ iteration++; int
		 * perturbedValue=r.nextInt(getMaxValue(0)-getMinValue(0)+1);
		 * while(duplicate
		 * .getVariables(index)==perturbedValue)perturbedValue=r.nextInt
		 * ((getMaxValue(0)-getMinValue(0)+1));
		 * duplicate.setVariable(perturbedValue, index);
		 * neighbourViolation=duplicate.overallConstraintViolation();
		 * if(neighbourViolation>0) {
		 * if(bestConstraintViolation>neighbourViolation){
		 * bestNeighbour=duplicate; } }
		 * }while(neighbourViolation>0&&iteration<10); if(iteration==10){
		 * System.err.println(
		 * "Constrained violating solution was found, generating new random solution"
		 * ); MyIntegerSolutionMO reset=new MyIntegerSolutionMO(this);
		 * reset.setVariables(randomStartingPoint()); return reset; }
		 * 
		 * 
		 * 
		 * else return duplicate;
		 */

	}

	public Integer[] randomStartingPoint() {
		Integer[] allocation = new Integer[TASK_NUM];
		Random r = new Random();
		for (int i = 0; i < TASK_NUM; ++i) {
			allocation[i] = r.nextInt(SERV_NUM);
		}

		return allocation;
	}

	public String print() {
		String res = "";
		for (int i = 0; i < TASK_NUM; ++i) {
			res += " " + variables[i];
		}
		return res;
	}

	public int sgn(double x) {
		return x > 0 ? 1 : 0;
	}

	public MyIntegerSolutionMO meanSolution(MyIntegerSolutionMO sol) {
		if (sol.getSize() != this.size)
			return null;
		MyIntegerSolutionMO mean = new MyIntegerSolutionMO(this);
		for (int i = 0; i < size; ++i) {
			mean.setVariable((sol.getVariables(i) + getVariables(i)) / 2, i);
		}
		return mean;
	}

	public Double overallConstraintViolation() {
		double bandwidth_per_server[] = new double[SERV_NUM];
		double bandwidth_per_rack[] = new double[RACK_NUM];
		double bandwidth_per_pod[] = new double[POD_NUM];

		// Fitness function 3;
		// System.out.println(var.length);
		for (int i = 0; i < variables.length; ++i) {
			int server_index = variables[i];
			bandwidth_per_server[server_index] += bandwidth[i];
		}
		for (int i = 0; i < SERV_NUM; ++i) {

			bandwidth_per_rack[i / SERV_ON_RACK] += bandwidth_per_server[i];
		}
		for (int i = 0; i < RACK_NUM; ++i) {
			bandwidth_per_pod[i / RACK_ON_POD] += bandwidth_per_rack[i];
		}
		double server_constraint = 0;
		for (int i = 0; i < SERV_NUM; ++i) {
			server_constraint += (bandwidth_per_server[i] - SERVER_LINK_CAPACITY)
					* sgn(bandwidth_per_server[i] - SERVER_LINK_CAPACITY);
		}
		double rack_constraint = 0;
		for (int i = 0; i < RACK_NUM; ++i) {
			rack_constraint += (bandwidth_per_rack[i] - RACK_LINK_CAPACITY)
					* sgn(bandwidth_per_rack[i] - RACK_LINK_CAPACITY);
		}
		int number_violated_constraint = 0;
		if (server_constraint > 0)
			number_violated_constraint++;
		if (rack_constraint > 0)
			number_violated_constraint++;

		return (rack_constraint + server_constraint);

	}

	public MyIntegerSolutionMO getFirstFeasibleSolution() {
		int iteration = 1;
		MyIntegerSolutionMO explore = (MyIntegerSolutionMO) this
				.getAnotherNeighbour();
		MyIntegerSolutionMO candidate = this.overallConstraintViolation() < explore
				.overallConstraintViolation() ? this : explore;
		while (explore.overallConstraintViolation() > 0 && iteration < size) {
			iteration++;
			explore = explore.getAnotherNeighbour();
			if (explore.overallConstraintViolation() > candidate
					.overallConstraintViolation()) {
				candidate = explore;
			}
		}
		if (explore.overallConstraintViolation() > 0) {
			// System.err.println("Max evaluation number expired, new solution is generated");
			explore.setVariables(randomStartingPoint());

		} /*
		 * else {
		 * System.err.println("Iteration needed to found a feasible solution " +
		 * iteration); System.out .println(("Fun " + explore.fitness()[0] + " "
		 * + Math.abs(((Double) explore.fitness()[1]).intValue()) + "  " +
		 * ((MyIntegerSolutionMO) explore).print()) + " " +
		 * ((MyIntegerSolutionMO) explore) .overallConstraintViolation()); }
		 */
		// System.err.println("Candidate: "+candidate.overallConstraintViolation()+"  Found: "+explore.overallConstraintViolation());
		// System.err.println(explore.overallConstraintViolation()<candidate.overallConstraintViolation()?"Random":"Found");
		return explore.overallConstraintViolation() < candidate
				.overallConstraintViolation() ? explore : candidate;
	}

	public MyIntegerSolutionMO getAnotherNeighbour() {
		MyIntegerSolutionMO neighbour = new MyIntegerSolutionMO(this);
		Random r = new Random();
		int index = r.nextInt(TASK_NUM);
		int perturbedValue = r.nextInt(getMaxValue(0) - getMinValue(0) + 1);
		while (neighbour.getVariables(index) == perturbedValue)
			perturbedValue = r.nextInt((getMaxValue(0) - getMinValue(0) + 1));
		neighbour.setVariable(perturbedValue, index);
		return neighbour;

	}

}

public class MultiObjTest {

	public static ArrayList<Integer[]> variableFromFile(String file)
			throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(new File(file)));
		ArrayList<Integer[]> solutions = new ArrayList<Integer[]>();
		String line;
		Integer[] actualSolution;
		while ((line = br.readLine()) != null) {
			String[] numbers = line.split(" ");
			actualSolution = new Integer[numbers.length];
			for (int i = 0; i < numbers.length; ++i) {
				actualSolution[i] = new Integer(Integer.parseInt(numbers[i]));

			}
			solutions.add(actualSolution);
		}
		br.close();
		return solutions;
	}

	public static void main(String[] args) throws IOException {
		// MyIntegerSolutionMO sol=new MyIntegerSolutionMO();
		// sol.setMaxValue(3);
		// sol.setMinValue(0);
		if (args.length < 2) {
			System.err
					.println("Usage: program_name #task #server [temperature]");
		}
		int task = Integer.parseInt(args[0]);
		int server = Integer.parseInt(args[1]);
		double temperature;
		if (args.length > 2) {
			temperature = Double.parseDouble(args[2]);

		} else
			temperature = 10000;
		ArrayList<Integer[]> startingPoint = variableFromFile("/home/peppone/workspace/JMetalMinimum/VAR");
		while (!startingPoint.isEmpty()) {
			SimulatedAnnealingMO mo = new SimulatedAnnealingMO(
					//new MyIntegerSolutionMO(task, 0, server - 1),
					new MyIntegerSolutionMO(task, 0, server - 1).setVariables(startingPoint.get(0)),temperature,
					new DecrementalCooling(), new DefaultMOAcceptance());
			startingPoint.remove(0);
			mo.setTemperature(Integer.MAX_VALUE);
			long start = System.nanoTime();
			ArrayList<Solution> sol = mo.execute();
			long end = System.nanoTime();
			ArrayList<MyIntegerSolutionMO> unf = new ArrayList<MyIntegerSolutionMO>();
			ArrayList<MyIntegerSolutionMO> fea = new ArrayList<MyIntegerSolutionMO>();
			for (Solution i : sol) {
				// System.err.println("FOUND "+((MyIntegerSolutionMO)i).print());
				if (((MyIntegerSolutionMO) i).overallConstraintViolation() > 0) {
					unf.add((MyIntegerSolutionMO) i);
				} else {
					fea.add((MyIntegerSolutionMO) i);
				}
			}
			ArrayList<MyIntegerSolutionMO> toprint;
			if (fea.size() == 0) {
				System.out
						.println(unf.size() + " Unfeasible solution(s) found");
				toprint = unf;
			} else {
				System.out.println(fea.size() + " Feasible solution(s) and "
						+ unf.size() + " unfeasible solutions found");
				toprint = fea;
			}
			for (Solution i : toprint) {
				System.out.println(("Fun " + i.fitness()[0] + " "
						+ Math.abs(((Double) i.fitness()[1]).doubleValue())
						+ "  " + ((MyIntegerSolutionMO) i).print())
						+ " "
						+ ((MyIntegerSolutionMO) i)
								.overallConstraintViolation());
			}
			System.out.println(((double) end - start) / 1000000000);
		}
		// //////
		/*
		 * while(i<50){ ++i; MyIntegerSolutionMO prova=new
		 * MyIntegerSolutionMO(task,0,server-1);
		 * prova.setVariables(prova.randomStartingPoint());
		 * System.out.println("Punto originale   "+prova.print());
		 * MyIntegerSolutionMO vertex=prova.findMostFarVertex();
		 * System.out.println("Vertice calcolato "+ vertex.print());
		 * System.out.println("Mediano calcolato "+
		 * prova.meanSolution(vertex).print()); System.out.println(); }
		 */
	}
}
