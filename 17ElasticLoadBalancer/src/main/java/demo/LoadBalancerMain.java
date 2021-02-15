package demo;

import java.util.ArrayList;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import java.time.Duration;
import akka.actor.Props;

public class LoadBalancerMain {

	public static void main(String[] args) throws InterruptedException {

		final ActorSystem system = ActorSystem.create("system");
		final LoggingAdapter log = Logging.getLogger(system, "main");

		final ActorRef loadBalancer = system.actorOf(LoadBalancer.createActor(system, 2), "LoadBalancer");

		loadBalancer.tell(new Task("t1"), ActorRef.noSender());
		loadBalancer.tell(new Task("t2"), ActorRef.noSender());
		loadBalancer.tell(new Task("t3"), ActorRef.noSender());
		loadBalancer.tell(new Done(), ActorRef.noSender());

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
