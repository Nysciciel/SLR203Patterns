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

public class TopologyMain {

	public static void main(String[] args) throws InterruptedException {

		final ActorSystem system = ActorSystem.create("system");
		final LoggingAdapter log = Logging.getLogger(system, "main");

		int n = 5;
		Vector<Vector<Integer>>  matrix= new Vector<Vector<Integer>>();
		for(int i=0;i<n;i++){
			Vector<Integer> r=new Vector<>();
			for(int j=0;j<n;j++){
				r.add(new Integer((new Random()).nextBoolean()?1:0));
			}
			matrix.add(r);
		}

		for(int i=0;i<n;i++){
			Vector<Integer> r=matrix.get(i);
			for(int j=0;j<n;j++){
				System.out.print(r.get(j));
			}
			System.out.println();
		}


		ArrayList<ActorRef> actorsList = new ArrayList<ActorRef>();

		for (int i = 0; i<n;i++){
			actorsList.add(system.actorOf(Actor.createActor(), "Actor"+String.valueOf(i+1)));
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
