package world.soapboxrace.cli;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import world.soapboxrace.debug.UdpDebug;

public class Sender implements Runnable {

	private CarProtocol carProtocol;

	public Sender() {
		ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
		executor.scheduleWithFixedDelay(this, 50L, 5000L, TimeUnit.MILLISECONDS);
	}

	@Override
	public void run() {
		Car playerCar = Board.getPlayerCar();
		if (carProtocol == null) {
			carProtocol = new CarProtocol();
		}
		carProtocol.updateCarProtocol(playerCar);
		System.out.println("car        : " + playerCar.getPlayerId() + "-> [" + playerCar.getX() + "]/[" + playerCar.getY() + "]");
		try {
			byte[] serialize = carProtocol.serialize();
			CarProtocol carProtocol2 = new CarProtocol();
			carProtocol2.deserialize(serialize);
			System.out.println("carProtocol: " + carProtocol2.getPlayerId() + "-> [" + carProtocol2.getX() + "]/[" + carProtocol2.getY() + "]");
			System.out.println(UdpDebug.byteArrayToHexString(serialize));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
