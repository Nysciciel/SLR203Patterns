package demo;

import java.util.ArrayList;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import java.time.Duration;
import akka.actor.Props;

public class Convergecast {

	public static void main(String[] args) {

		final ActorSystem system = ActorSystem.create("system");
		final LoggingAdapter log = Logging.getLogger(system, "main");

		final ActorRef d = system.actorOf(Receiver.createActor(), "d");
		final ActorRef merger = system.actorOf(Merger.createActor(d), "merger");
		final ActorRef a = system.actorOf(Client.createActor(merger, system), "a");
		final ActorRef b = system.actorOf(Client.createActor(merger, system), "b");
		final ActorRef c = system.actorOf(Client.createActor(merger, system), "c");


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
