package world.soapboxrace.cli;

import java.nio.ByteBuffer;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import world.soapboxrace.cli.netty.Main;

public class Sender implements Runnable {

	private CarProtocol carProtocol;
	private static SenderState senderState = SenderState.DISCONNECTED;

	public Sender() {
		ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
		executor.scheduleWithFixedDelay(this, 1000L, 1000L, TimeUnit.MILLISECONDS);
	}

	@Override
	public void run() {
		Car playerCar = Board.getPlayerCar();
		if (carProtocol == null) {
			carProtocol = new CarProtocol();
		}
		carProtocol.updateCarProtocol(playerCar);
		switch (senderState) {
		case HELLO:
			sendHello();
			break;
		case INFO:
			sendInfo();
			break;
		case POS:
			sendPos();
			break;
		case DISCONNECTED:
		default:
			break;
		}
	}

	private void sendHello() {
		byte[] hello = { 0x00, 0x00, 0x06 };
		Main.send(hello);
	}

	private void sendInfo() {
		byte[] serialize = carProtocol.serialize();
		ByteBuffer byteBuff = ByteBuffer.allocate(serialize.length + 3);
		byteBuff.put((byte) 0x00);
		byteBuff.put((byte) 0x00);
		byteBuff.put((byte) 0x07);
		byteBuff.put(serialize);
		Main.send(byteBuff.array());
	}

	private void sendPos() {
		ByteBuffer byteBuff = ByteBuffer.allocate(4 + 3);
		byteBuff.put((byte) 0x00);
		byteBuff.put((byte) 0x00);
		byteBuff.put((byte) 0x07);
		byteBuff.putShort(carProtocol.getX());
		byteBuff.putShort(carProtocol.getY());
		Main.send(byteBuff.array());
	}

	public static SenderState getSenderState() {
		return senderState;
	}

	public static void setSenderState(SenderState senderState) {
		Sender.senderState = senderState;
	}

}
