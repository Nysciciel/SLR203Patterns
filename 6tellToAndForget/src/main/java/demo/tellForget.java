package demo;

import java.util.ArrayList;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.event.Logging;
import akka.event.LoggingAdapter;

public class tellForget {

	public static void main(String[] args) {

		final ActorSystem system = ActorSystem.create("system");
		final LoggingAdapter log = Logging.getLogger(system, "main");

		final ActorRef a = system.actorOf(Sender.createActor(), "a");
		final ActorRef transmitter = system.actorOf(Transmitter.createActor(), "transmitter");
		final ActorRef b = system.actorOf(Target.createActor(), "b");

		String s = new String("start");
		MyMessage refsMessage = new MyMessage(transmitter, b);
		MyMessage startMessage = new MyMessage(s);
		
		a.tell(refsMessage, ActorRef.noSender());
		a.tell(startMessage, ActorRef.noSender());

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
