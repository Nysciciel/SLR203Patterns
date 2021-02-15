package demo;

import java.util.ArrayList;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import java.time.Duration;
import akka.actor.Props;

public class LoadRobin {

	public static void main(String[] args) throws InterruptedException {

		final ActorSystem system = ActorSystem.create("system");
		final LoggingAdapter log = Logging.getLogger(system, "main");

		final ActorRef loadbalancer = system.actorOf(Topic.createActor(), "loadbalancer");
		final ActorRef a = system.actorOf(Publisher.createActor(), "a");
		final ActorRef b = system.actorOf(Subscriber.createActor(), "b");
		final ActorRef c = system.actorOf(Subscriber.createActor(), "c");

		loadbalancer.tell(new Subscribe(), b);
		loadbalancer.tell(new Subscribe(), c);
		loadbalancer.tell(new Publication("m1"), a);
		loadbalancer.tell(new Publication("m2"), a);
		loadbalancer.tell(new Publication("m3"), a);
		loadbalancer.tell(new Unsubscribe(), c);
		loadbalancer.tell(new Publication("m4"), a);

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
