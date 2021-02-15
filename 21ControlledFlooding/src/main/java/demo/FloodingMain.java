package demo;

import java.util.ArrayList;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import java.time.Duration;
import akka.actor.Props;
import java.util.Random;
import java.util.Vector;

public class FloodingMain {

	public static ActorRef createNetwork(Vector<Vector<Integer>> matrix, String prefix, ActorSystem system) throws InterruptedException {

		int n = matrix.size();
		ArrayList<ActorRef> actorsList = new ArrayList<ActorRef>();

		for (int i = 0; i<n;i++){
			actorsList.add(system.actorOf(Actor.createActor(), "Actor"+prefix+String.valueOf(i+1)));
		}

		for (int j = 0; j<n;j++){
			ActorRef act = actorsList.get(j);
			ArrayList<ActorRef> knownList = new ArrayList<ActorRef>();
			for (int i = 0; i<n;i++){
				if (matrix.get(j).get(i)==1){
					knownList.add(actorsList.get(i));
				}
			}
			act.tell(new KnowActors(knownList), ActorRef.noSender());
			Thread.sleep(1);
		}
		return actorsList.get(0);
	}

	public static void main(String[] args) throws InterruptedException {

		final ActorSystem system = ActorSystem.create("system");
		final LoggingAdapter log = Logging.getLogger(system, "main");

		int n = 5;
		Vector<Vector<Integer>> matrix= new Vector<Vector<Integer>>();

		for(int i=0;i<n;i++){
			Vector<Integer> r=new Vector<>();
			for(int j=0;j<n;j++){
				r.add(new Integer(0));
			}
			matrix.add(r);
		}

		matrix.get(0).set(1,1);
		matrix.get(0).set(2,1);
		matrix.get(1).set(3,1);
		matrix.get(2).set(3,1);
		matrix.get(3).set(4,1);

		for(int i=0;i<n;i++){
			Vector<Integer> r=matrix.get(i);
			for(int j=0;j<n;j++){
				System.out.print(r.get(j));
			}
			System.out.println();
		}

		ActorRef root = createNetwork(matrix, "", system);


		Thread.sleep(3);
		log.info("\n\n\n");
		root.tell(new Message(new ArrayList<ActorRef>()),ActorRef.noSender());

		Thread.sleep(3);
		log.info("\n\n\n");
		matrix.get(4).set(1,1);
		root = createNetwork(matrix, "_", system);
		root.tell(new Message(new ArrayList<ActorRef>()),ActorRef.noSender());

		try {
			waitBeforeTerminate();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			system.terminate();
		}
	}

	public static void waitBeforeTerminate() throws InterruptedException {
		Thread.sleep(5000);
	}

	public static void sleepFor(int sec) {
		try {
			Thread.sleep(sec * 1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
