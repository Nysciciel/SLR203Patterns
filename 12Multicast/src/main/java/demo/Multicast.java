package demo;

import java.util.ArrayList;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.event.Logging;
import akka.event.LoggingAdapter;

public class Multicast {

	public static void main(String[] args) {

		final ActorSystem system = ActorSystem.create("system");
		final LoggingAdapter log = Logging.getLogger(system, "main");

		final ActorRef multicaster = system.actorOf(Multicaster.createActor(), "multicaster");
		final ActorRef rec1 = system.actorOf(Receiver.createActor(), "rec1");
		final ActorRef rec2 = system.actorOf(Receiver.createActor(), "rec2");
		final ActorRef rec3 = system.actorOf(Receiver.createActor(), "rec3");

		GroupCreate grp1 = new GroupCreate("group1", rec1, rec2);
		GroupCreate grp2 = new GroupCreate("group2", rec2, rec3);
		GroupMessage hello = new GroupMessage("group1", "hello");
		GroupMessage world = new GroupMessage("group2", "world");


		multicaster.tell(grp1, ActorRef.noSender());
		multicaster.tell(grp2, ActorRef.noSender());
		multicaster.tell(hello, ActorRef.noSender());
		multicaster.tell(world, ActorRef.noSender());

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
